/*
   BasicWFSReader
   Copyright (C) 2006 Open Geospatial Consortium Inc.

   This program is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/

/*
 * Author: Raj Singh <rsingh@opengeospatial.org>
 * Online version: http://www.ogcnetwork.net/system/files?file=BasicWFSReader_java.txt
 * Last modified June 25, 2006
 * Tested using JSE 1.5. JSE 1.4 should work with the JAXP 1.3 library.
 */
package de.hsos.richwps.sp.imports.wfsharvester;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BasicWFSClient {
	public Document capabilities = null;
	private URL serviceURL = null;
	private static String wfsVersion = "1.0.0";
	Document xmlDocument = null;
	DocumentBuilderFactory documentBuilderFactory;
	
	public Document getFeatureFromFile(String filePath) {
		try {
			DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
			File f = new File(filePath);
			xmlDocument = db.parse( new FileInputStream(f) );
			return xmlDocument;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Document getFeature(String wfsQuery, int maxFeatures) {
		String maxfeatures = "";
		if ( maxFeatures > 0 ) maxfeatures = " maxFeatures=\"3\"";
		
		try {
			// set up the URL connection
			HttpURLConnection httpcon = (HttpURLConnection) serviceURL.openConnection();
			httpcon.setDoInput(true);
			httpcon.setDoOutput(true);
			httpcon.setUseCaches(false);
			httpcon.setAllowUserInteraction(false);
			
			String q = "<?xml version=\"1.0\" ?>";
			q += "<wfs:GetFeature service=\"WFS\" version=\"" + wfsVersion + "\"" 
				+ maxfeatures 
				+ " xmlns:wfs=\"http://www.opengis.net/wfs\"" 
				+ " xmlns:ogc=\"http://www.opengis.net/ogc\">";
			q += wfsQuery;
			q += "</wfs:GetFeature>";

			// send POST request
			DataOutputStream dos = new DataOutputStream( httpcon.getOutputStream() );
			dos.writeBytes( q );
			dos.close();

			DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
			xmlDocument = db.parse( httpcon.getInputStream() );
			return xmlDocument;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * getFeatures
	 * @param featuretype WFS FeatureType
	 * @param maxfeatures limit of features to return (0 means no limit)
	 * @return XML document of features; null if featuretype doesn't exist or exception caught
	 */
	public Document getFeatureBasic(String featuretype, int maxfeatures) {
		if ( !hasFeatureType(featuretype) ) {
			System.err.println("in getFeature, feature type '"+featuretype+"' not found.");
			return null;
		}

		try {
			DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
			featuretype = URLEncoder.encode( featuretype, "UTF-8" );
			String req = "&request=GetFeature&TypeName=" + featuretype;
			if ( maxfeatures > 0 ) 
				req += "&MaxFeatures=" + maxfeatures;
			URL gfurl = new URL( serviceURL.toString() + req );
			xmlDocument = db.parse( gfurl.openStream() );
			return xmlDocument;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Document getFeatureBasic(String featuretype) {
		return getFeatureBasic(featuretype, 0);
	}
	
	public Document describeFeatureType(String featuretype) {
		if ( !hasFeatureType(featuretype) ) {
			System.err.println("in describeFeatureType, feature type '"+featuretype+"' not found.");
			return null;
		}
		
		try {
			DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
			featuretype = URLEncoder.encode( featuretype, "UTF-8" );
			URL dfturl = new URL( serviceURL.toString() + "&request=DescribeFeatureType&TypeName=" + featuretype );
			xmlDocument = db.parse( dfturl.openStream() );
			return xmlDocument;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] getFeatureTypeNames() {
		if ( !hasCapabilities() ) return null;
		
		Element rootel = capabilities.getDocumentElement();
		Element ftlist = (Element)rootel.getElementsByTagName("FeatureTypeList").item(0);
		NodeList fts = ftlist.getElementsByTagName("FeatureType");
		Vector ftnamesvec = new Vector(fts.getLength());

		for (int i=0; i<fts.getLength(); i++) {
			Element ft = (Element)fts.item(i);
			Element ftname = (Element)ft.getElementsByTagName("Name").item(0);
			ftnamesvec.add(ftname.getTextContent()	);
		}
		
		String[] ftnames = new String[ftnamesvec.size()];
		for (int i = 0; i < ftnames.length; i++) {
			ftnames[i] = ftnamesvec.elementAt(i).toString();
		}
		return ftnames;
	}
	
	/**
	 * Retrieve WFS capabilities via an HTTP GET request 
	 * and store in global variable 'capabilities'
	 * @return true on success; false on exception
	 */
	public boolean getCapabilities() {
		try {
			DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
			URL capurl = new URL(serviceURL.toString() + "&request=GetCapabilities");
			xmlDocument = db.parse(capurl.openStream());
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		capabilities = xmlDocument;
		return true;
	}
	
	public boolean hasFeatureType(String featuretype) {
		String[] ftnames = getFeatureTypeNames();
		for (int i = 0; i < ftnames.length; i++) {
			if ( ftnames[i].equals(featuretype) ) 
				return true;
		}
		return false;
	}
	
	public boolean hasCapabilities() {
		if ( capabilities == null ) return false;
		else return true;
	}
	
	public BasicWFSClient(URL serviceurl, String version) {
		wfsVersion = version;

		String s = "version=1.0.0&service=WFS";
		if ( serviceurl.toString().contains("?") ) {
			if ( serviceurl.toString().endsWith("?") ) ; // add nothing
			else if ( serviceurl.toString().endsWith("&") ) ; // add nothing
			else s = "&" + s;
		} else 
			s = "?" + s;
		try {
			serviceURL = new URL(serviceurl.toString() + s);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		xmlDocument = null;
		documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setNamespaceAware(false);
		documentBuilderFactory.setValidating(false);
		documentBuilderFactory.setIgnoringComments(true);
		documentBuilderFactory.setIgnoringElementContentWhitespace(true);
	}
	
	public BasicWFSClient(URL service) {
		this(service, "1.0.0");
	}
	
	public static void main(String[] args) {
		//String defaultwfs = "http://geoserv.weichand.de:8080/geoserver/wfs"; 
                String defaultwfs = "http://mdi-sh.org/geoserver_lkn/ows"; 
		String wfs = "";
		
		if ( args == null || args.length < 1 ) 
			wfs = defaultwfs;
		else 
			wfs = args[0];
		
		URL wfsurl = null;
		try {
			wfsurl = new URL(wfs);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("URL entered: " + wfs + "\nis not a valid URL.");
			System.exit(1);
		}

		BasicWFSClient b = new BasicWFSClient(wfsurl);
		b.getCapabilities();
		
		// show the raw capabilities document
		System.out.println("Capabilities document:");
		System.out.println(b.capabilities);
		
                System.out.println(b.capabilities.getFirstChild().getNodeName());
                NamedNodeMap nodeMap = b.capabilities.getFirstChild().getAttributes();
                Node node = nodeMap.getNamedItem("version");
                System.out.println(node.getNodeValue());
                
		// print just the feature type names
//		String[] names = b.getFeatureTypeNames();
//		System.out.println("feature type names: ");
//		for (int i = 0; i < names.length; i++) 
//			System.out.println(names[i]);
                
               
		
	}
}