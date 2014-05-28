/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

/**
 *
 * @author fbensman
 */
public class SPClient {
    
    private RDFClient rdfClient = null;
    
    private static SPClient instance = null;
    private String rootURI = "http://localhost:4567/semanticproxy/resources";
    
    private SPClient() {
        rdfClient = new RDFClient();
       
        
    }

    public static SPClient getInstance(){
        if(instance == null)
            instance = new SPClient();
        return instance;
    }
    
    public boolean testConnection(){
        try{
            Network network = null;
            network = getNetwork();
            if(network == null)
                return false;
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
    }
    
    
    public String getRootURI() {
        return rootURI;
    }

    public void setRootURI(String rootURI) {
        this.rootURI = rootURI;
    }

    
    
    
    
    public RDFClient getRdfClient() {
        return rdfClient;
    }
    

    
    public Network getNetwork() throws Exception{
        RDFResource res = rdfClient.retrieveResource(new RDFID(rootURI));
        return Network.createWrapper(res);
    }
    
    
    public WPS getWPS(RDFID rdfID) throws Exception{
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return WPS.createWrapper(res);
    }
    
    
    public Process getProcess(RDFID rdfID) throws Exception{
        RDFResource res = rdfClient.retrieveResource(rdfID);
        return Process.createWrapper(res);
    }
    
}
