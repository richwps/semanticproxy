/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            System.out.println(net.getOwner());
            System.out.println(net.getDomain());
            WPS[] wpss = net.getWPSs();
            for (int i = 0; i < wpss.length; i++) {
                System.out.println(wpss[i].getEndpoint());
                Process[] processes = wpss[i].getProcesses();
                for (int j = 0; j < processes.length; j++) {
                    System.out.println(processes[j].getAbstract());
                    System.out.println(processes[j].getIdentifier());
                    System.out.println(processes[j].getProcessVersion());
                    System.out.println(processes[j].getTitle());
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
