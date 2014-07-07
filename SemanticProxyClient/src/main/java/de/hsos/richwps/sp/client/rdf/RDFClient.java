/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.rdf;

import de.hsos.richwps.sp.client.CommunicationException;
import de.hsos.richwps.sp.client.HTTPClient;
import de.hsos.richwps.sp.client.InternalSPException;
import de.hsos.richwps.sp.client.LRUCache;
import de.hsos.richwps.sp.client.RDFException;
import de.hsos.richwps.sp.client.BadRequestException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.openrdf.model.Statement;
import org.openrdf.model.Value;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;
import org.openrdf.rio.helpers.StatementCollector;

/**
 * Client to interact with the SemanticProxy on RDF level
 *
 * @author fbensman
 */
public class RDFClient {

    /**
     * Client for handling http level calls, uses HTTP client
     */
    private HTTPClient httpClient = null;
    /**
     * Cache for RDF resources
     */
    private LRUCache<String, RDFResource> cache = null;

    public RDFClient() {
        httpClient = new HTTPClient();
        cache = new LRUCache<String, RDFResource>();
    }

    public HTTPClient getHttpClient() {
        return httpClient;
    }

    /**
     * Retrieves a resource from the SemanticProxy and parses it into an
     * RDFResource object
     *
     * @param rdfID RDF id of the resource to retrieve
     * @return The resource as an RDFResource object
     * @throws Exception
     */
    public RDFResource retrieveResource(RDFID rdfID) throws BadRequestException, InternalSPException, CommunicationException, RDFException{
        try {
            RDFResource tmp = cache.get(rdfID.rdfID);
            if (tmp != null) {
                return tmp;
            }

            String body = httpClient.getRawRDF(rdfID.rdfID);
            ArrayList<Statement> stmtList = null;
            try{
                stmtList = decomposeIntoStatements(body, rdfID);
            }catch(Exception e){
                throw new RDFException("Unexpected error when parsing resource "+rdfID.rdfID+ ". Original message was: "+ e.getMessage());
            }
            ArrayList<LiteralExpression> litExList = new ArrayList<LiteralExpression>();
            ArrayList<ResourceExpression> resExList = new ArrayList<ResourceExpression>();
            RDFResource res = new RDFResource(rdfID);

            for (int i = 0; i < stmtList.size(); i++) {
                Statement stmt = stmtList.get(i);
                String predicate = stmt.getPredicate().stringValue();
                Value obj = stmt.getObject();

                if (obj instanceof org.openrdf.model.Literal) {
                    LiteralExpression exp = new LiteralExpression(predicate, obj.stringValue());
                    litExList.add(exp);
                } else if (obj instanceof org.openrdf.model.URI) {
                    ResourceExpression exp = new ResourceExpression(predicate, new RDFID(obj.stringValue()));
                    resExList.add(exp);
                } else {
                    throw new RDFException("Unexpected object type in resoure "+rdfID.rdfID);
                }
            }
            res.setFields(litExList.toArray(new LiteralExpression[litExList.size()]));
            res.setResources(resExList.toArray(new ResourceExpression[resExList.size()]));

            cache.put(res.getRdfID().rdfID, res);

            return res;
        }catch(BadRequestException e){
            throw e;
        }catch(InternalSPException e){
            throw e;
        }catch(CommunicationException e){
            throw e;
        }

    }

    /**
     * Decompoeses an rdf string into separate statements
     *
     * @param rdfXml
     * @return
     * @throws Exception
     */
    private static ArrayList<Statement> decomposeIntoStatements(String rdfXml, RDFID id) throws Exception {
        RDFParser rdfParser = Rio.createParser(RDFFormat.RDFXML);
        ArrayList<Statement> inputList = new ArrayList<Statement>();
        rdfParser.setRDFHandler(new StatementCollector(inputList));
        //rdfParser.parse(new StringReader(rdfXml), URIConfiguration.RESOURCES_URI);
        rdfParser.parse(new StringReader(rdfXml), id.rdfID);
        
        return inputList;
    }
    
    
    
    public RDFID[] getSearchResults(String keyword, URL searchEndpoint) throws BadRequestException, InternalSPException, CommunicationException, MalformedURLException{
        
        String xml = httpClient.getRawSearchResults(keyword, searchEndpoint);
        try{
            SubjectList list = SubjectList.fromXML(xml);
            RDFID[] rdfArr = new RDFID[list.size()];
            for(int i=0; i<list.size();i++){
                rdfArr[i]= new RDFID(list.get(i).toString());
            }
            return rdfArr;
        }catch(MalformedURLException e){
            throw new MalformedURLException("Parsing result list failed: "+e.getMessage());
        }
    }
    
    
    public void postRDF(RDFResource[] rdfRess, URL endpoint) throws RDFException, BadRequestException, InternalSPException, CommunicationException{
        RDFDocBuilder builder = new RDFDocBuilder();
        for(int i=0; i<rdfRess.length;i++){
            builder.addResource(rdfRess[i]);
        }
        String xmlrdf = builder.toXMLRDF();
        httpClient.postRDFDoc(xmlrdf, endpoint);
    }
    
}
