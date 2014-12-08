/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import de.hsos.richwps.sp.client.ows.EUOM;
import de.hsos.richwps.sp.client.ows.gettypes.Process;
import de.hsos.richwps.sp.client.ows.gettypes.WPS;
import de.hsos.richwps.sp.client.ows.gettypes.Input;
import de.hsos.richwps.sp.client.ows.gettypes.Network;
import de.hsos.richwps.sp.client.ows.SPClient;
import de.hsos.richwps.sp.client.ows.gettypes.InAndOutputForm;
import de.hsos.richwps.sp.client.ows.gettypes.Output;
import de.hsos.richwps.sp.client.ows.posttypes.PostWPS;
import de.hsos.richwps.sp.client.ows.Vocabulary;
import de.hsos.richwps.sp.client.ows.gettypes.ComplexData;
import de.hsos.richwps.sp.client.ows.gettypes.ComplexDataCombination;
import de.hsos.richwps.sp.client.ows.gettypes.FeatureType;
import de.hsos.richwps.sp.client.ows.gettypes.QoSTarget;
import de.hsos.richwps.sp.client.ows.gettypes.WFS;
import de.hsos.richwps.sp.client.ows.posttypes.PostInput;
import de.hsos.richwps.sp.client.ows.posttypes.PostLiteralData;
import de.hsos.richwps.sp.client.ows.posttypes.PostOutput;
import de.hsos.richwps.sp.client.ows.posttypes.PostProcess;
import de.hsos.richwps.sp.client.ows.posttypes.PostQoSTarget;
import java.net.URL;
import java.util.ArrayList;

/**
 * Main class contains client setup and some operations with it
 *
 * @author fbensman
 */
public class App {

    /**
     * Main routine, args args are not regarded. This class connects to the db
     * and sets up the web frontent, also initial data is loaded.
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Semantic Proxy Client is starting...");
        try {
            Vocabulary.init(new URL("http://localhost:4567/semanticproxy/resources/vocab"));

            //client setup
            SPClient spClient = SPClient.getInstance();
            spClient.setRootURL("http://localhost:4567/semanticproxy/resources");
            spClient.setSearchURL("http://localhost:4567/semanticproxy/search");
            spClient.setWpsListURL("http://localhost:4567/semanticproxy/resources/wpss");
            spClient.setProcessListURL("http://localhost:4567/semanticproxy/resources/processes");


            //Testing/Demonstration
            Network net = spClient.getNetwork();

            System.out.println("Network:");
            System.out.println("-> Network owner: " + net.getOwner());
            System.out.println("-> Domain: " + net.getDomain());

            WPS[] wpss = net.getWPSs();
            for (int i = 0; i < wpss.length; i++) {
                System.out.println("-> WPS:");
                System.out.println("   -> WPS endpoint: " + wpss[i].getEndpoint());
                if(wpss[i].getRichWPSEndpoint()!=null)
                    System.out.println("   -> RichWPS endpoint: " + wpss[i].getRichWPSEndpoint());
                else
                    System.out.println("   -> RichWPS endpoint: not set");
                Process[] processes = wpss[i].getProcesses();
                System.out.println("   -> Processes:");
                for (int j = 0; j < processes.length; j++) {
                    Process proc = processes[j];
                    System.out.println("      -> Hosting WPS: " + proc.getWPS().getEndpoint());
                    System.out.println("      -> Identifier: " + proc.getIdentifier());
                    System.out.println("      -> Title:      " + proc.getTitle());
                    System.out.println("      -> Abstract:   " + proc.getAbstract());
                    System.out.println("      -> Version:    " + proc.getProcessVersion());
                    {
                        URL[] metadata = proc.getMetadata();
                        if (metadata.length==0) {
                            System.out.println("         -> Metadata:   Not set");
                        } else {
                            for(URL md : metadata)
                                System.out.println("         -> Metadata:   "+md.toString());
                        }
                    }
                    System.out.println("      -> Inputs:");
                    Input[] inputs = processes[j].getInputs();
                    for (int k = 0; k < inputs.length; k++) {
                        Input in = inputs[k];
                        System.out.println("         -> Identifier: " + in.getIdentifier());
                        System.out.println("         -> Title:      " + in.getTitle());
                        System.out.println("         -> Abstract:   " + in.getAbstract());
                        System.out.println("         -> MinOccurs:  " + in.getMinOccurs());
                        System.out.println("         -> MaxOccurs:  " + in.getMaxOccurs());
                        {
                            URL[] metadata = in.getMetadata();
                            if (metadata.length==0) {
                                System.out.println("         -> Metadata:   Not set");
                            } else {
                                for(URL md : metadata)
                                    System.out.println("         -> Metadata:   "+md.toString());
                            }
                        }
                        InAndOutputForm inf = in.getInputFormChoice();
                        if (inf.getDataType() == InAndOutputForm.LITERAL_TYPE) {
                            System.out.println("         -> Type:       Literal");
                        } else if (inf.getDataType() == InAndOutputForm.COMPLEX_TYPE) {
                            System.out.println("         -> Type:       Complex");
                            ComplexData complex = (ComplexData)inf;
                            if(complex.getMaximumMegabytes() == null)
                                System.out.println("           -> MMBytes:    not set");
                            else
                                System.out.println("           -> MMBytes:    "+complex.getMaximumMegabytes());
                            System.out.println("           -> Def format: "+complex.getDefaultFormat().getEncoding()+" "+complex.getDefaultFormat().getMimeType()+" "+complex.getDefaultFormat().getSchema());
                            for(ComplexDataCombination cdc : complex.getSupportedFormats())
                                System.out.println("           -> Sup format: "+cdc.getEncoding()+" "+cdc.getMimeType()+" "+cdc.getSchema());
                            
                        } else if (inf.getDataType() == InAndOutputForm.BOUNDING_BOX_TYPE){
                            System.out.println("         -> Type:       BoundingBox");
                        }
                        else
                            System.out.println("         -> Type:       No appropriate type");
                        System.out.println("         --");
                    }

                    System.out.println("      -> Outputs:");
                    Output[] outputs = processes[j].getOutputs();
                    for (int k = 0; k < outputs.length; k++) {
                        Output out = outputs[k];
                        System.out.println("         -> Identifier: " + out.getIdentifier());
                        System.out.println("         -> Title:      " + out.getTitle());
                        System.out.println("         -> Abstract:   " + out.getAbstract());
                        {
                            URL[] metadata = out.getMetadata();
                            if (metadata.length==0) {
                                System.out.println("         -> Metadata:   Not set");
                            } else {
                                for(URL md : metadata)
                                    System.out.println("         -> Metadata:   "+md.toString());
                            }
                        }
                        InAndOutputForm inf = out.getOutputFormChoice();
                        if (inf.getDataType() == InAndOutputForm.LITERAL_TYPE) {
                            System.out.println("         -> Type:       Literal");
                        } else if (inf.getDataType() == InAndOutputForm.COMPLEX_TYPE) {
                            System.out.println("         -> Type:       Complex");
                            System.out.println("         -> Type:       Complex");
                            ComplexData complex = (ComplexData)inf;
                            if(complex.getMaximumMegabytes() == null)
                                System.out.println("           -> MMBytes:    not set");
                            else
                                System.out.println("           -> MMBytes:    "+complex.getMaximumMegabytes());
                        } else {
                            System.out.println("         -> Type:       BoundingBox");
                        }
                        System.out.println("         --");
                    }
                    
                    System.out.println("      -> QoS targets:");
                    QoSTarget[] targets = processes[j].getQoSTargets();
                    for (int k = 0; k < targets.length; k++) {
                        QoSTarget target = targets[k];
                        System.out.println("         -> Title:      " + target.getTitle());
                        System.out.println("         -> Abstract:   " + target.getAbstract());
                        System.out.println("         -> Ideal:      " + target.getIdeal());
                        System.out.println("         -> UOM:        " + target.getUOM());
                        System.out.println("         -> Max:        " + target.getMax());
                        System.out.println("         -> Min:        " + target.getMin());
                        System.out.println("         -> Variance:   " + target.getVariance());
                        System.out.println("         --");
                    }
                    

                    System.out.println("   --");
                }
            }

            System.out.println("-------------------------");


            String keyword = "topo";
            System.out.println("Search for <<" + keyword + ">");
            Process[] procArr = spClient.searchProcessByKeyword(keyword);
            for (int i = 0; i < procArr.length; i++) {
                System.out.println(i + 1 + ". " + procArr[i].getIdentifier());
            }
            System.out.println("--");

            //Post wps
            System.out.println("Post a wps");
            PostWPS wps = new PostWPS();
            wps.setEndpoint(new URL("http://www.adder.de/wps"));
            wps.setRichWPSEndpoint(new URL("http://www.adder.de/wps-t"));
            spClient.postWPS(wps);
            System.out.println("Done.\n--");

            //Post process
            System.out.println("Post a process");
            PostProcess process = new PostProcess();
            process.setIdentifier("calc.proc.add");
            process.setTitle("Invoke addition");
            process.setBstract("Adds two numbers");
            process.setProcessVersion("0.1");
            PostInput in1 = new PostInput();
            in1.setIdentifier("in.addend1");
            in1.setTitle("Addend 1");
            in1.setBstract("First addend");
            in1.setMinOcc(1);
            in1.setMaxOcc(1);
            PostLiteralData litData1 = new PostLiteralData();
            in1.setPostInputFormChoice(litData1);
            PostInput in2 = new PostInput();
            in2.setIdentifier("in.addend2");
            in2.setTitle("Addend 2");
            in2.setBstract("2nd addend");
            in2.setMinOcc(1);
            in2.setMaxOcc(1);
            PostLiteralData litData2 = new PostLiteralData();
            in2.setPostInputFormChoice(litData2);
            ArrayList<PostInput> tmpList = new ArrayList<PostInput>();
            tmpList.add(in1);
            tmpList.add(in2);
            process.setInputs(tmpList);
            PostOutput out1 = new PostOutput();
            out1.setIdentifier("out.sum");
            out1.setTitle("Sum");
            out1.setBstract("The sum");
            PostLiteralData litData3 = new PostLiteralData();
            out1.setPostOutputFormChoice(litData3);
            ArrayList<PostOutput> tmpList2 = new ArrayList<PostOutput>();
            tmpList2.add(out1);
            process.setOutputs(tmpList2);
            PostQoSTarget t = new PostQoSTarget();
            t.setTitle("Antwortzeit");
            t.setBstract("Zeit die vergeht bis ein Dienst auf eine Anfrage antwortet");
            t.setIdeal(1.0);
            t.setUOM(EUOM.SECONDS);
            t.setMax(2.0);
            t.setMin(0.0);
            t.setVariance(0.5);
            ArrayList<PostQoSTarget> tmpList3 = new ArrayList<PostQoSTarget>();
            tmpList3.add(t);
            process.setQosTargets(tmpList3);
            process.setWps(wps);
            spClient.postProcess(process);
            System.out.println("Done.\n--");

            //Delete a WPS (processes are removed along)
            System.out.println("Delete a WPS");
            spClient.deleteWPS(wps.getRdfId());
            System.out.println("Done.\n--");

            //repost wps and process and update process
            System.out.println("Repost wps and process and update process");
            spClient.postWPS(wps);
            spClient.postProcess(process);
            process.setBstract("This is a newer version of the process");
            spClient.updateProcess(process);
            System.out.println("Done.\n--");

            //Delete a WPS (processes are removed along) once more
            System.out.println("Delete a WPS");
            spClient.deleteWPS(wps.getRdfId());
            System.out.println("Done.\n--");
            
            
            
            //get WFSs
            System.out.println("Get WFSs");
            WFS[] wfss = net.getWFSs();
            for (int i = 0; i < wfss.length; i++) {
                System.out.println("-> WFS:");
                System.out.println("   -> WFS endpoint: " + wfss[i].getEndpoint());
                System.out.println("   -> WFS version: " + wfss[i].getWFSVersion());
                FeatureType[] featureTypes = wfss[i].getFeatureTypes();
                System.out.println("   -> FeatureTypes:");
                for (int j = 0; j < featureTypes.length; j++) {
                    FeatureType feat = featureTypes[j];
                    System.out.println("      -> Name: " + feat.getName());
                }
            }
            
            
            
            
        } catch (BadRequestException e) {
            System.err.println("Caught exception: " + e.getMessage());
            e.printStackTrace();
        } catch (RDFException e) {
            System.err.println("Caught exception: " + e.getMessage());
            e.printStackTrace();
        } catch (CommunicationException e) {
            System.err.println("Caught exception: " + e.getMessage());
            e.printStackTrace();
        } catch (InternalSPException e) {
            System.err.println("Caught exception: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected exception: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("*** Semantic Proxy Client has stopped");
    }
}
