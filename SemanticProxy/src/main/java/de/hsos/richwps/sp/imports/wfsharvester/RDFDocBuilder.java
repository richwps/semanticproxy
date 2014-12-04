/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wfsharvester;


import de.hsos.richwps.sp.imports.wpsharvester.*;
import de.hsos.richwps.sp.restlogic.Vocabulary;
import java.io.StringWriter;
import java.util.ArrayList;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LiteralImpl;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFWriter;
import org.openrdf.rio.Rio;
import org.openrdf.model.vocabulary.XMLSchema;

/**
 * Stores a list of resources for later conversion into rdf documents
 *
 * @author fbensman
 */
public class RDFDocBuilder {

    private ArrayList<RDFResource> resList = null;

    public RDFDocBuilder() {
        resList = new ArrayList<RDFResource>();
    }

    public void addResource(RDFResource r) {
        resList.add(r);
    }

    public void removeResource(RDFResource r) {
        resList.remove(r);
    }

    /**
     * Generates an XML/RDF representation of the resources in one XML document
     *
     * @return
     * @throws RDFException
     */
    public String toXMLRDF() throws RDFException {
        ArrayList<Statement> stmts = new ArrayList<Statement>();
        for (int i = 0; i < resList.size(); i++) {
            RDFResource rdfRes = resList.get(i);
            for (int j = 0; j < rdfRes.getFields().length; j++) {
                LiteralExpression lexp = rdfRes.getFields()[j];
                Resource subj = new URIImpl(rdfRes.getRdfID().rdfID);
                org.openrdf.model.URI pred = new URIImpl(lexp.predicate);
                org.openrdf.model.Literal obj = new LiteralImpl(lexp.literal);
                stmts.add(new StatementImpl(subj, pred, obj));
            }
            for (int j = 0; j < rdfRes.getResources().length; j++) {
                ResourceExpression rexp = rdfRes.getResources()[j];
                Resource subj = new URIImpl(rdfRes.getRdfID().rdfID);
                org.openrdf.model.URI pred = new URIImpl(rexp.predicate);
                org.openrdf.model.URI obj = new URIImpl(rexp.rdfID.rdfID);
                stmts.add(new StatementImpl(subj, pred, obj));
            }
        }
        StringWriter sw = new StringWriter();
        RDFWriter writer = Rio.createWriter(RDFFormat.RDFXML, sw);
        try {
            writer.startRDF();
            writer.handleNamespace("rdf", org.openrdf.model.vocabulary.RDF.NAMESPACE);
            writer.handleNamespace("rdfs", org.openrdf.model.vocabulary.RDFS.NAMESPACE);
            writer.handleNamespace("xsd", XMLSchema.NAMESPACE);
            writer.handleNamespace("rwps", Vocabulary.VOC);
            
            for (int i = 0; i < stmts.size(); i++) {
                writer.handleStatement(stmts.get(i));
            }
            writer.endRDF();
            return sw.toString();
        } catch (RDFHandlerException e) {
            String rdfResourcesText = "";
            for (RDFResource r : resList) {
                rdfResourcesText += r.getRdfID().rdfID + "\n";
            }
            throw new RDFException("Faild generating xml document for " + rdfResourcesText);
        }


    }
}
