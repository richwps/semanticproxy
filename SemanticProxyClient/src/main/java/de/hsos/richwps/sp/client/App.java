/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import de.hsos.richwps.sp.client.wps.Process;
import de.hsos.richwps.sp.client.wps.WPS;
import de.hsos.richwps.sp.client.wps.Input;
import de.hsos.richwps.sp.client.wps.Network;
import de.hsos.richwps.sp.client.wps.SPClient;
import de.hsos.richwps.sp.client.wps.InAndOutputForm;
import de.hsos.richwps.sp.client.wps.Output;

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
            //client setup
            SPClient spClient = SPClient.getInstance();
            spClient.setRootURL("http://localhost:4567/semanticproxy/resources");

            //Testing/Demonstration
            boolean res = spClient.testConnection();
            System.out.println("Connection ok: " + res);
            Network net = spClient.getNetwork();
            
            System.out.println("Network:");
            System.out.println("-> Network owner: "+net.getOwner());
            System.out.println("-> Domain: "+net.getDomain());
            
            WPS[] wpss = net.getWPSs();
            for (int i = 0; i < wpss.length; i++) {
                System.out.println("-> WPS:");
                System.out.println("   -> WPS endpoint: "+ wpss[i].getEndpoint());
                Process[] processes = wpss[i].getProcesses();
                System.out.println("   -> Processes:");
                for (int j = 0; j < processes.length; j++) {
                    Process proc = processes[j];
                    System.out.println("      -> Identifier: "+proc.getIdentifier());
                    System.out.println("      -> Title:      "+proc.getTitle());
                    System.out.println("      -> Abstract:   "+proc.getAbstract());
                    System.out.println("      -> Version:    "+proc.getProcessVersion());
                    System.out.println("      -> Inputs:");
                    Input[] inputs = processes[j].getInputs();
                    for(int k=0; k<inputs.length;k++){
                        Input in = inputs[k];
                        System.out.println("         -> Identifier: "+in.getIdentifier());
                        System.out.println("         -> Title:      "+in.getTitle());
                        System.out.println("         -> Abstract:   "+in.getAbstract());
                        System.out.println("         -> MinOccurs:  "+in.getMinOccurs());
                        System.out.println("         -> MaxOccurs:  "+in.getMaxOccurs());
                        if(in.getMetadata()!=null)
                            System.out.println("         -> Metadata:   "+in.getMetadata().toString());
                        else
                            System.out.println("         -> Metadata:   Not set");
                        InAndOutputForm inf = in.getInputFormChoice();
                        if(inf.getDataType() == InAndOutputForm.LITERAL_TYPE)
                            System.out.println("         -> Type:       Literal");
                        else if(inf.getDataType() == InAndOutputForm.COMPLEX_TYPE)
                            System.out.println("         -> Type:       Complex");
                        else
                            System.out.println("         -> Type:       BoundingBox");
                        System.out.println("         --");
                    }
                    
                    System.out.println("      -> Outputs:");
                    Output[] outputs = processes[j].getOutputs();
                    for(int k=0; k<outputs.length;k++){
                        Output out = outputs[k];
                        System.out.println("         -> Identifier: "+out.getIdentifier());
                        System.out.println("         -> Title:      "+out.getTitle());
                        System.out.println("         -> Abstract:   "+out.getAbstract());
                        if(out.getMetadata()!=null)
                            System.out.println("         -> Metadata:   "+out.getMetadata().toString());
                        else
                            System.out.println("         -> Metadata:   Not set");
                        InAndOutputForm inf = out.getOutputFormChoice();
                        if(inf.getDataType() == InAndOutputForm.LITERAL_TYPE)
                            System.out.println("         -> Type:       Literal");
                        else if(inf.getDataType() == InAndOutputForm.COMPLEX_TYPE)
                            System.out.println("         -> Type:       Complex");
                        else
                            System.out.println("         -> Type:       BoundingBox");
                        System.out.println("         --");
                    }
                    
                    System.out.println("   --");
                }
            }

            System.out.println("-------------------------");

            //Repeating requests to test cache
//            net = spClient.getNetwork();
//            System.out.println(net.getOwner());
//            System.out.println(net.getDomain());
//            wpss = net.getWPSs();
//            for(int i=0; i<wpss.length;i++){
//                System.out.println(wpss[i].getEndpoint());
//                Process[] processes = wpss[i].getProcesses();
//                for(int j=0; j<processes.length;j++){
//                    System.out.println(processes[j].getAbstract());
//                    System.out.println(processes[j].getIdentifier());
//                    System.out.println(processes[j].getProcessVersion());
//                    System.out.println(processes[j].getTitle());
//                }
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Semantic Proxy Client has stopped");
    }
}
