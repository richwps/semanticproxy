/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

/**
 *
 * @author fbensman
 */
public class RDFClient {
    
    private HTTPClient httpClient = null;
    private LRUCache<String, RDFResource> cache = null;
    
    
    public RDFClient(){
        httpClient = new HTTPClient();
        cache = new LRUCache<String, RDFResource>();
    }

    public HTTPClient getHttpClient() {
        return httpClient;
    }
    
    
    
    
    
    public RDFResource retrieveResource(RDFID rdfID) throws Exception{
        try{
            RDFResource tmp = cache.get(rdfID.rdfID);
            if(tmp != null){
                return tmp;
            }
            
            String body = httpClient.getRawRDF(rdfID.rdfID);
            ArrayList<Statement> stmtList = decomposeIntoStatements(body);
            ArrayList<LiteralExpression>litExList = new ArrayList<LiteralExpression>();
            ArrayList<ResourceExpression>resExList = new ArrayList<ResourceExpression>();
            RDFResource res = new RDFResource(rdfID);
            
            for(int i=0; i< stmtList.size();i++){                
                Statement stmt = stmtList.get(i);
                String predicate = stmt.getPredicate().stringValue();
                Value obj = stmt.getObject();
                
                if(obj instanceof org.openrdf.model.Literal){
                    LiteralExpression exp = new LiteralExpression(predicate, obj.stringValue());
                    litExList.add(exp);
                }
                else if(obj instanceof org.openrdf.model.URI){
                    ResourceExpression exp = new ResourceExpression(predicate,new RDFID(obj.stringValue()));
                    resExList.add(exp);
                }
                else{
                    throw new Exception("Error, unexpected object type");
                }
            }
            res.setFields(litExList.toArray(new LiteralExpression[litExList.size()]));
            res.setResources(resExList.toArray(new ResourceExpression[resExList.size()]));
            
            cache.put(res.getRdfID().rdfID, res);
            
            return res;
        }catch(Exception e){
            throw e;
        }
        
    }
    
    
    /**
     * Decompoeses an rdf string into separate statements
     * @param rdfXml
     * @return
     * @throws Exception 
     */
    private static ArrayList<Statement> decomposeIntoStatements(String rdfXml) throws Exception{
        RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
        ArrayList<Statement> inputList = new ArrayList<Statement>();
        rdfParser.setRDFHandler(new StatementCollector(inputList));
        try{
            rdfParser.parse(new StringReader(rdfXml), URIConfiguration.RESOURCES_URI);
        }catch(IOException e){
            throw new Exception("Error, cannot parse rdf"+ e.getMessage());
        }
        return inputList;
    }
    
}
