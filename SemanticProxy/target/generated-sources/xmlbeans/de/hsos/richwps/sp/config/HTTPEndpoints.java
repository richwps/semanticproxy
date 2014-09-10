/*
 * XML Type:  HTTPEndpoints
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.HTTPEndpoints
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config;


/**
 * An XML HTTPEndpoints(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public interface HTTPEndpoints extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(HTTPEndpoints.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sB7B8345B58BA017C1A9B1BA334B1E47E").resolveHandle("httpendpointsc2fetype");
    
    /**
     * Gets the "Host" element
     */
    java.lang.String getHost();
    
    /**
     * Gets (as xml) the "Host" element
     */
    org.apache.xmlbeans.XmlAnyURI xgetHost();
    
    /**
     * Sets the "Host" element
     */
    void setHost(java.lang.String host);
    
    /**
     * Sets (as xml) the "Host" element
     */
    void xsetHost(org.apache.xmlbeans.XmlAnyURI host);
    
    /**
     * Gets the "Application" element
     */
    java.lang.String getApplication();
    
    /**
     * Gets (as xml) the "Application" element
     */
    org.apache.xmlbeans.XmlString xgetApplication();
    
    /**
     * Sets the "Application" element
     */
    void setApplication(java.lang.String application);
    
    /**
     * Sets (as xml) the "Application" element
     */
    void xsetApplication(org.apache.xmlbeans.XmlString application);
    
    /**
     * Gets the "Resources" element
     */
    java.lang.String getResources();
    
    /**
     * Gets (as xml) the "Resources" element
     */
    org.apache.xmlbeans.XmlString xgetResources();
    
    /**
     * Sets the "Resources" element
     */
    void setResources(java.lang.String resources);
    
    /**
     * Sets (as xml) the "Resources" element
     */
    void xsetResources(org.apache.xmlbeans.XmlString resources);
    
    /**
     * Gets the "Vocabulary" element
     */
    java.lang.String getVocabulary();
    
    /**
     * Gets (as xml) the "Vocabulary" element
     */
    org.apache.xmlbeans.XmlString xgetVocabulary();
    
    /**
     * Sets the "Vocabulary" element
     */
    void setVocabulary(java.lang.String vocabulary);
    
    /**
     * Sets (as xml) the "Vocabulary" element
     */
    void xsetVocabulary(org.apache.xmlbeans.XmlString vocabulary);
    
    /**
     * Gets the "WPSList" element
     */
    java.lang.String getWPSList();
    
    /**
     * Gets (as xml) the "WPSList" element
     */
    org.apache.xmlbeans.XmlString xgetWPSList();
    
    /**
     * Sets the "WPSList" element
     */
    void setWPSList(java.lang.String wpsList);
    
    /**
     * Sets (as xml) the "WPSList" element
     */
    void xsetWPSList(org.apache.xmlbeans.XmlString wpsList);
    
    /**
     * Gets the "ProcessList" element
     */
    java.lang.String getProcessList();
    
    /**
     * Gets (as xml) the "ProcessList" element
     */
    org.apache.xmlbeans.XmlString xgetProcessList();
    
    /**
     * Sets the "ProcessList" element
     */
    void setProcessList(java.lang.String processList);
    
    /**
     * Sets (as xml) the "ProcessList" element
     */
    void xsetProcessList(org.apache.xmlbeans.XmlString processList);
    
    /**
     * Gets the "Search" element
     */
    java.lang.String getSearch();
    
    /**
     * Gets (as xml) the "Search" element
     */
    org.apache.xmlbeans.XmlString xgetSearch();
    
    /**
     * Sets the "Search" element
     */
    void setSearch(java.lang.String search);
    
    /**
     * Sets (as xml) the "Search" element
     */
    void xsetSearch(org.apache.xmlbeans.XmlString search);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static de.hsos.richwps.sp.config.HTTPEndpoints newInstance() {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.HTTPEndpoints parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.HTTPEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
