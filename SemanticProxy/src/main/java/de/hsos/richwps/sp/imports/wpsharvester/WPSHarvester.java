/*
 * To change this template, choose Tools | Templates
 * and open the template out the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;

import de.hsos.richwps.sp.imports.IImportSource;
import de.hsos.richwps.sp.imports.ImportException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import net.opengis.ows.x11.MetadataType; //da true Metadata net.opengis.ows.x11.MetadataType


import net.opengis.wps.x100.CapabilitiesDocument;
import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.ProcessDescriptionType;

import org.n52.wps.client.WPSClientException;
import org.n52.wps.client.WPSClientSession;

/**
 *
 * @author fbensman
 */
public class WPSHarvester implements IImportSource {

    private URL targetURL = null;
    private CapabilitiesDocument capabilities = null;
    private int wpsIdx = 0;
    private int processIdx = 0;

    public WPSHarvester(URL targetURL,
            URL wpsBaseURL,
            URL processBaseURL,
            URL inputBaseURL,
            URL outputBaseURL,
            URL literalDataBaseURL,
            URL complexDataBaseURL,
            URL boundingBoxDataBaseURL) {
        this.targetURL = targetURL;
        RDFIDBuilder.init(wpsBaseURL, 
                processBaseURL, 
                inputBaseURL, 
                outputBaseURL, 
                literalDataBaseURL, 
                complexDataBaseURL, 
                boundingBoxDataBaseURL);
    }

    public String getNextWPS() throws ImportException {
        if (wpsIdx >= 1) {
            return null;
        }
        if (capabilities == null) {
            try {
                capabilities = requestCapabilities(targetURL.toString());
            } catch (WPSClientException wpse) {
                wpsIdx++;
                throw new ImportException("", wpse);
            }
        }

        RDFDocBuilder builder = new RDFDocBuilder();
        RDFID wpsID = RDFIDBuilder.createID().withWpsURL(targetURL).forWPS();
        PostWPS wps = new PostWPS(targetURL, wpsID);
        RDFResource rdfRes = wps.toRDFResource();
        builder.addResource(rdfRes);
        String xmlRDF = null;
        try {
            xmlRDF = builder.toXMLRDF();
        } catch (Exception ex) {
            wpsIdx++;
            throw new ImportException("", ex);
        }
        wpsIdx++;
        return xmlRDF;
    }

    public String getNextProcess() throws ImportException {
        //get process information
        if (capabilities == null) {
            try {
                capabilities = requestCapabilities(targetURL.toString());
            } catch (WPSClientException wpse) {
                processIdx++;
                throw new ImportException("", wpse);
            }
        }
        //determine next process
        if (processIdx >= capabilities.getCapabilities().getProcessOfferings().sizeOfProcessArray()) {
            return null; //if there is no further process
        }

        //issue DescribeProcessRequest
        String processIdentifier = capabilities.getCapabilities().getProcessOfferings().getProcessArray(processIdx).getIdentifier().getStringValue();
        ProcessDescriptionType pdType;
        try {
            pdType = requestDescribeProcess(targetURL.toString(), processIdentifier);
        } catch (Exception ex) {
            processIdx++;
            throw new ImportException("On DescribeProcess of "+processIdentifier, ex);
        }

        //Define process
        //
        RDFID processID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).forProcess();
        PostProcess process = new PostProcess(processID);
        process.setIdentifier(processIdentifier);
        //Abstract
        if (pdType.isSetAbstract()) {
            process.setBstract(pdType.getAbstract().getStringValue());
        }
        //Version
        process.setProcessVersion(pdType.getProcessVersion());
        //Title
        process.setTitle(pdType.getTitle().getStringValue());
        //Status supported
        if (pdType.isSetStatusSupported()) {
            process.setUseStatusSupported(true);
            process.setStatusSupported(pdType.getStatusSupported());
        }
        //Store supported
        if (pdType.isSetStoreSupported()) {
            process.setUseStoreSupported(true);
            process.setStoreSupported(pdType.getStoreSupported());
        }
        //Parent WPS
        RDFID wpsRDFID = RDFIDBuilder.createID().withWpsURL(targetURL).forWPS();
        PostWPS wps = new PostWPS(targetURL, wpsRDFID);
        process.setWps(wps);
        //WSDL
        if (pdType.isSetWSDL()) {
            try {
                URL href = new URL(pdType.getWSDL().getHref());
                process.setWsdl(href);
            } catch (MalformedURLException murle) {
                processIdx++;
                throw new ImportException("", murle);
            }
        }
        //Profiles
        {
            String[] strArr = pdType.getProfileArray();
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < strArr.length; i++) {
                list.add(strArr[i]);
            }
            process.setProfileList(list);
        }
        //Metadata
        {
            MetadataType[] metaArr = pdType.getMetadataArray();
            ArrayList<URL> list = new ArrayList<>();

            for (int i = 0; i < metaArr.length; i++) {
                System.out.println("Test output metadata about: " + metaArr[i].getAbout());
                System.out.println("Test output metadata title: " + metaArr[i].getTitle());
                try {
                    list.add(new URL(metaArr[i].getHref()));
                } catch (MalformedURLException murle) {
                    processIdx++;
                    throw new ImportException("Error on reading href attribute from metadata; href=" + metaArr[i].getHref(), murle);
                }
            }
            process.setMetadataList(list);
        }

        //input list
        {
            ProcessDescriptionType.DataInputs inputs = pdType.getDataInputs();
            InputDescriptionType[] idTypeArr = inputs.getInputArray();
            ArrayList<PostInput> list = new ArrayList<>();
            for (int i = 0; i < idTypeArr.length; i++) {
                InputDescriptionType in = idTypeArr[i];
                RDFID inputID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).withInputIdentifier(in.getIdentifier().getStringValue()).forInput();
                PostInput input = new PostInput(inputID);
                //Identifier
                input.setIdentifier(in.getIdentifier().getStringValue());
                //Title
                input.setTitle(in.getTitle().getStringValue());
                //Abstract
                if (in.isSetAbstract()) {
                    input.setBstract(in.getAbstract().getStringValue());
                }
                //Occs
                input.setMinOcc(in.getMinOccurs().intValue());
                input.setMaxOcc(in.getMaxOccurs().intValue());

                //Metadata
                {
                    MetadataType[] metaArr = in.getMetadataArray();
                    ArrayList<URL> metaDataList = new ArrayList<>();

                    for (int j = 0; j < metaArr.length; j++) {
                        System.out.println("Test output metadata about: " + metaArr[j].getAbout());
                        System.out.println("Test output metadata title: " + metaArr[j].getTitle());
                        try {
                            metaDataList.add(new URL(metaArr[j].getHref()));
                        } catch (MalformedURLException murle) {
                            processIdx++;
                            throw new ImportException("Error on reading href attribute from metadata; href=" + metaArr[j].getHref(), murle);
                        }
                    }
                    input.setMetadataList(metaDataList);
                }

                //Datatype (Literal, Complex or BoundingBox)
                if (in.isSetLiteralData()) {
                    RDFID literalDataRDFID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).withInputIdentifier(in.getIdentifier().getStringValue()).forLiteral();
                    input.setPostInputFormChoice(new PostLiteralData(literalDataRDFID));
                } else if (in.isSetComplexData()) {
                    RDFID complexDataRDFID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).withInputIdentifier(in.getIdentifier().getStringValue()).forComplex();
                    input.setPostInputFormChoice(new PostComplexData(complexDataRDFID));
                } else if (in.isSetBoundingBoxData()) {
                    RDFID boundingBoxDataRDFID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).withInputIdentifier(in.getIdentifier().getStringValue()).forBoundingBox();
                    input.setPostInputFormChoice(new PostBoundingBoxData(boundingBoxDataRDFID));
                } else {
                    processIdx++;
                    throw new ImportException("No data type set for input" + input.getIdentifier());
                }

                list.add(input);
            }
            process.setInputs(list);
        }


        //output list
        {
            ProcessDescriptionType.ProcessOutputs outputs = pdType.getProcessOutputs();
            OutputDescriptionType[] idTypeArr = outputs.getOutputArray();
            ArrayList<PostOutput> list = new ArrayList<>();
            for (int i = 0; i < idTypeArr.length; i++) {
                OutputDescriptionType out = idTypeArr[i];
                RDFID outputRDFID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).withOutputIdentifier(out.getIdentifier().getStringValue()).forOutput();
                PostOutput output = new PostOutput(outputRDFID);
                //Identifier
                output.setIdentifier(out.getIdentifier().getStringValue());
                //Title
                output.setTitle(out.getTitle().getStringValue());
                //Abstract
                if (out.isSetAbstract()) {
                    output.setBstract(out.getAbstract().getStringValue());
                }
                //Metadata
                {
                    MetadataType[] metaArr = out.getMetadataArray();
                    ArrayList<URL> metaDataList = new ArrayList<>();

                    for (int j = 0; j < metaArr.length; j++) {
                        System.out.println("Test output metadata about: " + metaArr[j].getAbout());
                        System.out.println("Test output metadata title: " + metaArr[j].getTitle());
                        try {
                            metaDataList.add(new URL(metaArr[j].getHref()));
                        } catch (MalformedURLException murle) {
                            processIdx++;
                            throw new ImportException("Error on reading href attribute from metadata; href=" + metaArr[j].getHref(), murle);
                        }
                    }
                    output.setMetadataList(metaDataList);
                }
                //Occs
                if (out.isSetLiteralOutput()) {
                    RDFID literalDataRDFID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).withOutputIdentifier(out.getIdentifier().getStringValue()).forLiteral();
                    output.setPostOutputFormChoice(new PostLiteralData(literalDataRDFID));
                } else if (out.isSetComplexOutput()) {
                    RDFID complexDataRDFID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).withOutputIdentifier(out.getIdentifier().getStringValue()).forComplex();
                    output.setPostOutputFormChoice(new PostComplexData(complexDataRDFID));
                } else if (out.isSetBoundingBoxOutput()) {
                    RDFID boundingBoxDataRDFID = RDFIDBuilder.createID().withWpsURL(targetURL).withProcessIdentifier(processIdentifier).withInputIdentifier(out.getIdentifier().getStringValue()).forBoundingBox();
                    output.setPostOutputFormChoice(new PostBoundingBoxData(boundingBoxDataRDFID));
                } else {
                    processIdx++;
                    throw new ImportException("No data type set for output" + output.getIdentifier());
                }

                list.add(output);
            }
            process.setOutputs(list);
        }


        RDFDocBuilder builder = new RDFDocBuilder();
        RDFResource rdfRes = process.toRDFResource();
        builder.addResource(rdfRes);
        for (PostInput in : process.getInputs()) {
            builder.addResource(in.toRDFResource());
            PostInAndOutputForm piaof = in.getPostInputFormChoice();
            if (piaof.getDataType() == PostInAndOutputForm.LITERAL_TYPE) {
                RDFResource r = ((PostLiteralData) piaof).toRDFResource();
                builder.addResource(r);
            } else if (piaof.getDataType() == PostInAndOutputForm.COMPLEX_TYPE) {
                RDFResource r = ((PostComplexData) piaof).toRDFResource();
                builder.addResource(r);
            } else {
                RDFResource r = ((PostBoundingBoxData) piaof).toRDFResource();
                builder.addResource(r);
            }
        }
        for (PostOutput out : process.getOutputs()) {
            builder.addResource(out.toRDFResource());
            PostInAndOutputForm piaof = out.getPostOutputFormChoice();
            if (piaof.getDataType() == PostInAndOutputForm.LITERAL_TYPE) {
                RDFResource r = ((PostLiteralData) piaof).toRDFResource();
                builder.addResource(r);
            } else if (piaof.getDataType() == PostInAndOutputForm.COMPLEX_TYPE) {
                RDFResource r = ((PostComplexData) piaof).toRDFResource();
                builder.addResource(r);
            } else {
                RDFResource r = ((PostBoundingBoxData) piaof).toRDFResource();
                builder.addResource(r);
            }
        }

        String xmlRDF = null;
        try {
            xmlRDF = builder.toXMLRDF();
        } catch (Exception ex) {
            processIdx++;
            throw new ImportException("", ex);
        }

        processIdx++;
        return xmlRDF;
    }

    public void reset() {
        capabilities = null;
        wpsIdx = 0;
        processIdx = 0;
    }

    private CapabilitiesDocument requestCapabilities(String url) throws WPSClientException {
        WPSClientSession wpsClient = WPSClientSession.getInstance();
        wpsClient.connect(url);
        return wpsClient.getWPSCaps(url);
    }

    private ProcessDescriptionType requestDescribeProcess(String url, String processID) throws IOException {
        WPSClientSession wpsClient = WPSClientSession.getInstance();
        ProcessDescriptionType processDescription = wpsClient.getProcessDescription(url, processID);
        return processDescription;
    }

    @Override
    public String getInfo() {
        return "WPS Harvester, target WPS at "+targetURL;
    }
}
