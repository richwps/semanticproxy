/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wfsharvester;

import de.hsos.richwps.sp.imports.IWFSImportSource;
import de.hsos.richwps.sp.imports.ImportException;
import de.hsos.richwps.sp.imports.wpsharvester.WPSHarvester;
import de.hsos.richwps.sp.types.IDGenerator;
import de.hsos.richwps.sp.types.EIDType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author fbensman
 */
public class WFSHarvester implements IWFSImportSource {

    private int wfsIdx = 0;
    private String newHost = null;
    private BasicWFSClient wfsClient = null;
    private URL targetURL = null;

    public WFSHarvester(URL targetURL) {
        this.targetURL = targetURL;
        wfsClient = new BasicWFSClient(targetURL, "1.0.0");
    }

    @Override
    public String getNextWFS() throws ImportException {

        if (wfsIdx < 1) {

            String version = null;
            String[] featureTypeNames = null;
            
            //make calls
            try {
                wfsClient.getCapabilities();
                NamedNodeMap nodeMap = wfsClient.capabilities.getFirstChild().getAttributes();
                Node node = nodeMap.getNamedItem("version");
                version = node.getNodeValue();
                featureTypeNames = wfsClient.getFeatureTypeNames();
            } catch (Exception ex) {
                throw new ImportException("Cannot connect to WFS at: " + targetURL, ex);
            }

            //build objects
            RDFID id = new RDFID( IDGenerator.getInstance().generateID(EIDType.WFS).toString() );
            PostWFS wfs = new PostWFS(id);
            wfs.setVersion(version);
            wfs.setEndpoint(targetURL);

            ArrayList<PostFeatureType> featureTypeList = new ArrayList<PostFeatureType>();
            for (int i = 0; i < featureTypeNames.length; i++) {             
                id = new RDFID( IDGenerator.getInstance().generateID(EIDType.FEATURETYPE).toString() );
                PostFeatureType ft = new PostFeatureType(id);
                ft.setName(featureTypeNames[i]);
                ft.setWfs(wfs);
                featureTypeList.add(ft);
            }
            wfs.setFeatureTypeList(featureTypeList);

            //Parse objects
            RDFDocBuilder builder = new RDFDocBuilder();

            RDFResource rdfRes = wfs.toRDFResource();
            builder.addResource(rdfRes);
            for(PostFeatureType ft : featureTypeList)
                builder.addResource(ft.toRDFResource());
            
            String xmlRDF = null;
            try {
                xmlRDF = builder.toXMLRDF();
            } catch (Exception ex) {
                wfsIdx++;
                throw new ImportException("Unable to create RDF statements", ex);
            }
            wfsIdx++;
            Logger.getLogger(WPSHarvester.class).info("Requested target WFS " + targetURL + " successfully");
            return xmlRDF;
        }
        return null;
    }

    @Override
    public void reset() {
        wfsIdx = 0;
    }

    @Override
    public String getInfo() {
        return "WFS harvester";
    }
}
