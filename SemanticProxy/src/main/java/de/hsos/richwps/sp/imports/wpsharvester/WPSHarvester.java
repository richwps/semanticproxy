/*
 * To change this template, choose Tools | Templates
 * and open the template out the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;

import de.hsos.richwps.sp.imports.IWPSImportSource;
import de.hsos.richwps.sp.imports.ImportException;
import de.hsos.richwps.sp.types.EIDType;
import de.hsos.richwps.sp.types.IDGenerator;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import net.opengis.ows.x11.MetadataType; //da true Metadata net.opengis.ows.x11.MetadataType


import net.opengis.wps.x100.CapabilitiesDocument;
import net.opengis.wps.x100.InputDescriptionType;
import net.opengis.wps.x100.OutputDescriptionType;
import net.opengis.wps.x100.ProcessDescriptionType;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.ClientProperties;

import org.n52.wps.client.WPSClientException;
import org.n52.wps.client.WPSClientSession;

/**
 *
 * @author fbensman
 */
public class WPSHarvester implements IWPSImportSource {

    private URL targetURL = null;
    private CapabilitiesDocument capabilities = null;
    private int wpsIdx = 0;
    private int processIdx = 0;
    //ID of the WPS behind the target URL
    private RDFID wpsID = null;

    public WPSHarvester(URL targetURL) {
        this.targetURL = targetURL;
    }

    @Override
    public String getNextWPS() throws ImportException {
        if (wpsIdx >= 1) {
            return null;
        }
        if (capabilities == null) {
            try {
                //requests capabilities from WPS server
                capabilities = requestCapabilities(targetURL.toString());
            } catch (WPSClientException wpse) {
                wpsIdx++;
                throw new ImportException("Unable to contact WPS at " + targetURL, wpse);
            }
        }
        // checks if WPS supports RichWPS
        URL richWPSURL = null;
        try {
            richWPSURL = new URL(targetURL.toString().replace("WebProcessingService", "RichWPS"));
            //richWPSURL = new URL(targetURL.toString().replace("WebProcessingService", "WPS-T"));
        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(WPSHarvester.class.getName()).log(Level.SEVERE, null, ex);
        }
        boolean isRichWPS = checkURL(richWPSURL.toString());

        RDFDocBuilder builder = new RDFDocBuilder();
        wpsID = new RDFID(IDGenerator.getInstance().generateID(EIDType.WPS).toString());
        PostWPS wps = new PostWPS(wpsID);
        wps.setEndpoint(targetURL);
        if (isRichWPS) {
            wps.setRichWPSEndpoint(richWPSURL);
        }
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
        Logger.getLogger(WPSHarvester.class).info("Requested target WPS " + targetURL + " successfully");
        return xmlRDF;
    }

    public String getNextProcess() throws ImportException {
        //get process information
        if (capabilities == null) {
            try {
                capabilities = requestCapabilities(targetURL.toString());
            } catch (WPSClientException wpse) {
                processIdx++;
                throw new ImportException("Unable to contact WPS at " + targetURL + " for requesting capabilities", wpse);
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
            throw new ImportException("On DescribeProcess of " + processIdentifier, ex);
        }

        //Define process
        //
        RDFID processID = new RDFID(IDGenerator.getInstance().generateID(EIDType.PROCESS).toString());
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
        PostWPS wps = new PostWPS(wpsID);
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
                    throw new ImportException("Error on reading href attribute from metadata; href=" + metaArr[i].getHref() + "of process " + processIdentifier, murle);
                }
            }
            process.setMetadataList(list);
        }

        //input list
        {
            ProcessDescriptionType.DataInputs inputs = pdType.getDataInputs();
            if(inputs != null){ 
                InputDescriptionType[] idTypeArr = inputs.getInputArray();
                ArrayList<PostInput> list = new ArrayList<>();
                for (int i = 0; i < idTypeArr.length; i++) {
                    InputDescriptionType in = idTypeArr[i];
                    RDFID inputID = new RDFID(IDGenerator.getInstance().generateID(EIDType.INPUT).toString());
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
                                throw new ImportException("Error on reading href attribute from metadata; href=" + metaArr[j].getHref() + " of process " + processIdentifier, murle);
                            }
                        }
                        input.setMetadataList(metaDataList);
                    }

                    //Datatype (Literal, Complex or BoundingBox)
                    if (in.isSetLiteralData()) {
                        RDFID literalDataRDFID = new RDFID(IDGenerator.getInstance().generateID(EIDType.LITERAL).toString());
                        input.setPostInputFormChoice(new PostLiteralData(literalDataRDFID));
                    } else if (in.isSetComplexData()) {
                        RDFID complexDataRDFID = new RDFID(IDGenerator.getInstance().generateID(EIDType.COMPLEX).toString());
                        PostComplexData complexData = new PostComplexData(complexDataRDFID);
                        //MaximumMegabytes
                        if (in.getComplexData().isSetMaximumMegabytes()) {
                            complexData.setMaxMegaBytes(in.getComplexData().getMaximumMegabytes());
                        }
                        //Default data combination
                        {
                            PostComplexDataCombination defaultCDC = new PostComplexDataCombination(new RDFID(IDGenerator.getInstance().generateID(EIDType.COMPLEXCOMBINATION).toString()));
                            //Encoding
                            if (in.getComplexData().getDefault().getFormat().isSetEncoding()) {
                                String encoding = in.getComplexData().getDefault().getFormat().getEncoding();
                                defaultCDC.setEncoding(encoding);
                            }
                            //MimeType
                            String mimeType = in.getComplexData().getDefault().getFormat().getMimeType();
                            defaultCDC.setMimeType(mimeType);
                            //Schema
                            if (in.getComplexData().getDefault().getFormat().isSetSchema()) {
                                String schema = in.getComplexData().getDefault().getFormat().getSchema();
                                defaultCDC.setSchema(schema);
                            }
                            complexData.setDefaultFormat(defaultCDC);
                        }
                        //Supported data combinations
                        {
                            ArrayList<PostComplexDataCombination> supportedList = new ArrayList<>();
                            for (int j = 0; j < in.getComplexData().getSupported().getFormatArray().length; j++) {
                                PostComplexDataCombination supportedCDC = new PostComplexDataCombination(new RDFID(IDGenerator.getInstance().generateID(EIDType.COMPLEXCOMBINATION).toString()));
                                //Encoding
                                if (in.getComplexData().getDefault().getFormat().isSetEncoding()) {
                                    String encoding = in.getComplexData().getSupported().getFormatArray(j).getEncoding();
                                    supportedCDC.setEncoding(encoding);
                                }
                                //MimeType
                                String mimeType = in.getComplexData().getSupported().getFormatArray(j).getMimeType();
                                supportedCDC.setMimeType(mimeType);
                                //Schema
                                if (in.getComplexData().getDefault().getFormat().isSetSchema()) {
                                    String schema = in.getComplexData().getSupported().getFormatArray(j).getSchema();
                                    supportedCDC.setSchema(schema);
                                }
                                supportedList.add(supportedCDC);
                            }
                            complexData.setSupportedFormats(supportedList);
                        }
                        input.setPostInputFormChoice(complexData);


                    } else if (in.isSetBoundingBoxData()) {
                        RDFID boundingBoxDataRDFID = new RDFID(IDGenerator.getInstance().generateID(EIDType.BOUNDINGBOX).toString());
                        input.setPostInputFormChoice(new PostBoundingBoxData(boundingBoxDataRDFID));
                    } else {
                        processIdx++;
                        throw new ImportException("No data type set for input" + input.getIdentifier() + " of process" + processIdentifier);
                    }

                    list.add(input);
                }
                process.setInputs(list);
            }
        }


        //output list
        {
            ProcessDescriptionType.ProcessOutputs outputs = pdType.getProcessOutputs();
            if(outputs != null){
                OutputDescriptionType[] idTypeArr = outputs.getOutputArray();
                ArrayList<PostOutput> list = new ArrayList<>();
                for (int i = 0; i < idTypeArr.length; i++) {
                    OutputDescriptionType out = idTypeArr[i];
                    RDFID outputRDFID = new RDFID(IDGenerator.getInstance().generateID(EIDType.OUTPUT).toString());
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
                                throw new ImportException("Error on reading href attribute from metadata; href=" + metaArr[j].getHref() + " of process " + processIdentifier, murle);
                            }
                        }
                        output.setMetadataList(metaDataList);
                    }
                    //Occs
                    if (out.isSetLiteralOutput()) {
                        RDFID literalDataRDFID = new RDFID(IDGenerator.getInstance().generateID(EIDType.LITERAL).toString());
                        output.setPostOutputFormChoice(new PostLiteralData(literalDataRDFID));
                    } else if (out.isSetComplexOutput()) {
                        RDFID complexDataRDFID = new RDFID(IDGenerator.getInstance().generateID(EIDType.COMPLEX).toString());

                        PostComplexData complexData = new PostComplexData(complexDataRDFID);
                        //Default data combination
                        {
                            PostComplexDataCombination defaultCDC = new PostComplexDataCombination(new RDFID(IDGenerator.getInstance().generateID(EIDType.COMPLEXCOMBINATION).toString()));
                            //Encoding
                            if (out.getComplexOutput().getDefault().getFormat().isSetEncoding()) {
                                String encoding = out.getComplexOutput().getDefault().getFormat().getEncoding();
                                defaultCDC.setEncoding(encoding);
                            }
                            //MimeType
                            String mimeType = out.getComplexOutput().getDefault().getFormat().getMimeType();
                            defaultCDC.setMimeType(mimeType);
                            //Schema
                            if (out.getComplexOutput().getDefault().getFormat().isSetSchema()) {
                                String schema = out.getComplexOutput().getDefault().getFormat().getSchema();
                                defaultCDC.setSchema(schema);
                            }
                            complexData.setDefaultFormat(defaultCDC);
                        }
                        //Supported data combinations
                        {
                            ArrayList<PostComplexDataCombination> supportedList = new ArrayList<>();
                            for (int j = 0; j < out.getComplexOutput().getSupported().getFormatArray().length; j++) {
                                PostComplexDataCombination supportedCDC = new PostComplexDataCombination(new RDFID(IDGenerator.getInstance().generateID(EIDType.COMPLEXCOMBINATION).toString()));
                                //Encoding
                                if (out.getComplexOutput().getDefault().getFormat().isSetEncoding()) {
                                    String encoding = out.getComplexOutput().getSupported().getFormatArray(j).getEncoding();
                                    supportedCDC.setEncoding(encoding);
                                }
                                //MimeType
                                String mimeType = out.getComplexOutput().getSupported().getFormatArray(j).getMimeType();
                                supportedCDC.setMimeType(mimeType);
                                //Schema
                                if (out.getComplexOutput().getDefault().getFormat().isSetSchema()) {
                                    String schema = out.getComplexOutput().getSupported().getFormatArray(j).getSchema();
                                    supportedCDC.setSchema(schema);
                                }
                                supportedList.add(supportedCDC);
                            }
                            complexData.setSupportedFormats(supportedList);
                        }
                        output.setPostOutputFormChoice(complexData);

                    } else if (out.isSetBoundingBoxOutput()) {
                        RDFID boundingBoxDataRDFID = new RDFID(IDGenerator.getInstance().generateID(EIDType.BOUNDINGBOX).toString());
                        output.setPostOutputFormChoice(new PostBoundingBoxData(boundingBoxDataRDFID));
                    } else {
                        processIdx++;
                        throw new ImportException("No data type set for output" + output.getIdentifier() + " of process " + processIdentifier);
                    }

                    list.add(output);
                }
                process.setOutputs(list);
            }
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
                PostComplexData complex = (PostComplexData) piaof;
                RDFResource r = complex.toRDFResource();
                builder.addResource(r);
                r = complex.getDefaultFormat().toRDFResource();
                builder.addResource(r);
                for(PostComplexDataCombination pcdc : complex.getSupportedFormats()){
                    r = pcdc.toRDFResource();
                    builder.addResource(r);
                }
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
                PostComplexData complex = (PostComplexData) piaof;
                RDFResource r = complex.toRDFResource();
                builder.addResource(r);
                r = complex.getDefaultFormat().toRDFResource();
                builder.addResource(r);
                for(PostComplexDataCombination pcdc : complex.getSupportedFormats()){
                    r = pcdc.toRDFResource();
                    builder.addResource(r);
                }
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
            throw new ImportException("Unable to build XMLRDF doc for process " + processIdentifier, ex);
        }
        Logger.getLogger(WPSHarvester.class).info("Request process information for process " + processIdentifier + " of WPS " + targetURL + " successfully");
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
        if (wpsClient.connect(url)) {
            CapabilitiesDocument capsDoc = wpsClient.getWPSCaps(url);
            WPSClientSession.reset();
            return capsDoc;
        }
        throw new WPSClientException("Cannot connect to server at " + url);
    }

    private ProcessDescriptionType requestDescribeProcess(String url, String processID) throws IOException, WPSClientException {
        WPSClientSession wpsClient = WPSClientSession.getInstance();
        if (wpsClient.connect(url)) {
            ProcessDescriptionType processDescription = wpsClient.getProcessDescription(url, processID);
            WPSClientSession.reset();
            return processDescription;
        }
        throw new WPSClientException("Cannot connect to server at " + url);
    }

    /**
     * Issues a GET request to the given URL to test whether the server is
     * listening on it
     *
     * @param url The URL of the resource
     * @return true if URL available
     */
    private boolean checkURL(String url) {
        try {
            //System.out.println("uri: " + url);
            Client client = ClientBuilder.newClient();
            client.property(ClientProperties.CONNECT_TIMEOUT, 5000);
            client.property(ClientProperties.READ_TIMEOUT, 5000);
            WebTarget webTarget = client.target(url);

            Invocation.Builder invocationBuilder = webTarget.request();
            invocationBuilder.accept("application/xml+rdf");

            Response response = invocationBuilder.get();
            if (response.getStatus() == 405) {
                return true;
            } else if (response.getStatus() == 200) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public String getInfo() {
        return "WPS Harvester, target WPS at " + targetURL;
    }
}
