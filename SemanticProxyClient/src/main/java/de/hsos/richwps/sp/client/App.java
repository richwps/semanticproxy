/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import de.hsos.richwps.sp.client.rdf.RDFID;
import de.hsos.richwps.sp.client.wps.gettypes.Process;
import de.hsos.richwps.sp.client.wps.gettypes.WPS;
import de.hsos.richwps.sp.client.wps.gettypes.Input;
import de.hsos.richwps.sp.client.wps.gettypes.Network;
import de.hsos.richwps.sp.client.wps.SPClient;
import de.hsos.richwps.sp.client.wps.gettypes.InAndOutputForm;
import de.hsos.richwps.sp.client.wps.gettypes.Output;
import de.hsos.richwps.sp.client.wps.posttypes.PostWPS;
import de.hsos.richwps.sp.client.wps.Vocabulary;
import de.hsos.richwps.sp.client.wps.posttypes.PostInput;
import de.hsos.richwps.sp.client.wps.posttypes.PostLiteralData;
import de.hsos.richwps.sp.client.wps.posttypes.PostOutput;
import de.hsos.richwps.sp.client.wps.posttypes.PostProcess;
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
                Process[] processes = wpss[i].getProcesses();
                System.out.println("   -> Processes:");
                for (int j = 0; j < processes.length; j++) {
                    Process proc = processes[j];
                    System.out.println("      -> Identifier: " + proc.getIdentifier());
                    System.out.println("      -> Title:      " + proc.getTitle());
                    System.out.println("      -> Abstract:   " + proc.getAbstract());
                    System.out.println("      -> Version:    " + proc.getProcessVersion());
                    System.out.println("      -> Inputs:");
                    Input[] inputs = processes[j].getInputs();
                    for (int k = 0; k < inputs.length; k++) {
                        Input in = inputs[k];
                        System.out.println("         -> Identifier: " + in.getIdentifier());
                        System.out.println("         -> Title:      " + in.getTitle());
                        System.out.println("         -> Abstract:   " + in.getAbstract());
                        System.out.println("         -> MinOccurs:  " + in.getMinOccurs());
                        System.out.println("         -> MaxOccurs:  " + in.getMaxOccurs());
                        if (in.getMetadata() != null) {
                            System.out.println("         -> Metadata:   " + in.getMetadata().toString());
                        } else {
                            System.out.println("         -> Metadata:   Not set");
                        }
                        InAndOutputForm inf = in.getInputFormChoice();
                        if (inf.getDataType() == InAndOutputForm.LITERAL_TYPE) {
                            System.out.println("         -> Type:       Literal");
                        } else if (inf.getDataType() == InAndOutputForm.COMPLEX_TYPE) {
                            System.out.println("         -> Type:       Complex");
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
                        if (out.getMetadata() != null) {
                            System.out.println("         -> Metadata:   " + out.getMetadata().toString());
                        } else {
                            System.out.println("         -> Metadata:   Not set");
                        }
                        InAndOutputForm inf = out.getOutputFormChoice();
                        if (inf.getDataType() == InAndOutputForm.LITERAL_TYPE) {
                            System.out.println("         -> Type:       Literal");
                        } else if (inf.getDataType() == InAndOutputForm.COMPLEX_TYPE) {
                            System.out.println("         -> Type:       Complex");
                        } else {
                            System.out.println("         -> Type:       BoundingBox");
                        }
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
            PostWPS wps = new PostWPS(new URL("http://www.adder.de/wps"), new RDFID("http://localhost:4567/semanticproxy/resources/wps/AdderWPS"));
            spClient.postWPS(wps);
            System.out.println("Done.\n--");

            //Post process
            System.out.println("Post a process");
            PostProcess process = new PostProcess(new RDFID("http://localhost:4567/semanticproxy/resources/process/add"));
            process.setIdentifier("calc.proc.add");
            process.setTitle("Invoke addition");
            process.setBstract("Adds two numbers");
            process.setProcessVersion("0.1");
            PostInput in1 = new PostInput(new RDFID("http://localhost:4567/semanticproxy/resources/input/firstaddend"));
            in1.setIdentifier("in.addend1");
            in1.setTitle("Addend 1");
            in1.setBstract("First addend");
            in1.setMinOcc(1);
            in1.setMaxOcc(1);
            PostLiteralData litData1 = new PostLiteralData(new RDFID("http://localhost:4567/semanticproxy/resources/literal_data/addenddata1"));
            in1.setPostInputFormChoice(litData1);
            PostInput in2 = new PostInput(new RDFID("http://localhost:4567/semanticproxy/resources/input/secondaddend"));
            in2.setIdentifier("in.addend2");
            in2.setTitle("Addend 2");
            in2.setBstract("2nd addend");
            in2.setMinOcc(1);
            in2.setMaxOcc(1);
            PostLiteralData litData2 = new PostLiteralData(new RDFID("http://localhost:4567/semanticproxy/resources/literal_data/addenddata2"));
            in2.setPostInputFormChoice(litData2);
            ArrayList<PostInput> tmpList = new ArrayList<PostInput>();
            tmpList.add(in1);
            tmpList.add(in2);
            process.setInputs(tmpList);
            PostOutput out1 = new PostOutput(new RDFID("http://localhost:4567/semanticproxy/resources/output/sum"));
            out1.setIdentifier("out.sum");
            out1.setTitle("Sum");
            out1.setBstract("The sum");
            PostLiteralData litData3 = new PostLiteralData(new RDFID("http://localhost:4567/semanticproxy/resources/literal_data/sumdata"));
            out1.setPostOutputFormChoice(litData3);
            ArrayList<PostOutput> tmpList2 = new ArrayList<PostOutput>();
            tmpList2.add(out1);
            process.setOutputs(tmpList2);
            process.setWps(wps);
            spClient.postProcess(process);
            System.out.println("Done.\n--");

            //Delete a WPS (processes are removed along)
            System.out.println("Delete a WPS");
            spClient.deleteWPS(wps.getRdfId());
            System.out.println("Done.\n--");

            //repost wps and process
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
