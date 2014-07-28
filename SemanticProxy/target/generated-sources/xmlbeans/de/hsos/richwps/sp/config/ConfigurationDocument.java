/*
 * An XML document type.
 * Localname: Configuration
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.ConfigurationDocument
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config;


/**
 * A document containing one Configuration(@http://www.hsos.de/richwps/sp/config) element.
 *
 * This is a complex type.
 */
public interface ConfigurationDocument extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(ConfigurationDocument.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4C120769099B3A62A73A92D1B3E5F014").resolveHandle("configurationa60edoctype");
    
    /**
     * Gets the "Configuration" element
     */
    de.hsos.richwps.sp.config.ConfigurationDocument.Configuration getConfiguration();
    
    /**
     * Sets the "Configuration" element
     */
    void setConfiguration(de.hsos.richwps.sp.config.ConfigurationDocument.Configuration configuration);
    
    /**
     * Appends and returns a new empty "Configuration" element
     */
    de.hsos.richwps.sp.config.ConfigurationDocument.Configuration addNewConfiguration();
    
    /**
     * An XML Configuration(@http://www.hsos.de/richwps/sp/config).
     *
     * This is a complex type.
     */
    public interface Configuration extends org.apache.xmlbeans.XmlObject
    {
        public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
            org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(Configuration.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s4C120769099B3A62A73A92D1B3E5F014").resolveHandle("configurationd80celemtype");
        
        /**
         * Gets the "RDFDirectory" element
         */
        java.lang.String getRDFDirectory();
        
        /**
         * Gets (as xml) the "RDFDirectory" element
         */
        org.apache.xmlbeans.XmlString xgetRDFDirectory();
        
        /**
         * Sets the "RDFDirectory" element
         */
        void setRDFDirectory(java.lang.String rdfDirectory);
        
        /**
         * Sets (as xml) the "RDFDirectory" element
         */
        void xsetRDFDirectory(org.apache.xmlbeans.XmlString rdfDirectory);
        
        /**
         * Gets the "StartClean" element
         */
        boolean getStartClean();
        
        /**
         * Gets (as xml) the "StartClean" element
         */
        org.apache.xmlbeans.XmlBoolean xgetStartClean();
        
        /**
         * Sets the "StartClean" element
         */
        void setStartClean(boolean startClean);
        
        /**
         * Sets (as xml) the "StartClean" element
         */
        void xsetStartClean(org.apache.xmlbeans.XmlBoolean startClean);
        
        /**
         * Gets the "Owner" element
         */
        java.lang.String getOwner();
        
        /**
         * Gets (as xml) the "Owner" element
         */
        org.apache.xmlbeans.XmlString xgetOwner();
        
        /**
         * Sets the "Owner" element
         */
        void setOwner(java.lang.String owner);
        
        /**
         * Sets (as xml) the "Owner" element
         */
        void xsetOwner(org.apache.xmlbeans.XmlString owner);
        
        /**
         * Gets the "Domain" element
         */
        java.lang.String getDomain();
        
        /**
         * Gets (as xml) the "Domain" element
         */
        org.apache.xmlbeans.XmlString xgetDomain();
        
        /**
         * Sets the "Domain" element
         */
        void setDomain(java.lang.String domain);
        
        /**
         * Sets (as xml) the "Domain" element
         */
        void xsetDomain(org.apache.xmlbeans.XmlString domain);
        
        /**
         * Gets the "PreloadFiles" element
         */
        de.hsos.richwps.sp.config.PreloadFiles getPreloadFiles();
        
        /**
         * Sets the "PreloadFiles" element
         */
        void setPreloadFiles(de.hsos.richwps.sp.config.PreloadFiles preloadFiles);
        
        /**
         * Appends and returns a new empty "PreloadFiles" element
         */
        de.hsos.richwps.sp.config.PreloadFiles addNewPreloadFiles();
        
        /**
         * Gets the "HTTPEndpoints" element
         */
        de.hsos.richwps.sp.config.HTTPEndpoints getHTTPEndpoints();
        
        /**
         * Sets the "HTTPEndpoints" element
         */
        void setHTTPEndpoints(de.hsos.richwps.sp.config.HTTPEndpoints httpEndpoints);
        
        /**
         * Appends and returns a new empty "HTTPEndpoints" element
         */
        de.hsos.richwps.sp.config.HTTPEndpoints addNewHTTPEndpoints();
        
        /**
         * Gets the "RDFNamingEndpoints" element
         */
        de.hsos.richwps.sp.config.RDFNamingEndpoints getRDFNamingEndpoints();
        
        /**
         * Sets the "RDFNamingEndpoints" element
         */
        void setRDFNamingEndpoints(de.hsos.richwps.sp.config.RDFNamingEndpoints rdfNamingEndpoints);
        
        /**
         * Appends and returns a new empty "RDFNamingEndpoints" element
         */
        de.hsos.richwps.sp.config.RDFNamingEndpoints addNewRDFNamingEndpoints();
        
        /**
         * Gets the "Port" element
         */
        int getPort();
        
        /**
         * Gets (as xml) the "Port" element
         */
        org.apache.xmlbeans.XmlInt xgetPort();
        
        /**
         * Sets the "Port" element
         */
        void setPort(int port);
        
        /**
         * Sets (as xml) the "Port" element
         */
        void xsetPort(org.apache.xmlbeans.XmlInt port);
        
        /**
         * A factory class with static methods for creating instances
         * of this type.
         */
        
        public static final class Factory
        {
            public static de.hsos.richwps.sp.config.ConfigurationDocument.Configuration newInstance() {
              return (de.hsos.richwps.sp.config.ConfigurationDocument.Configuration) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
            
            public static de.hsos.richwps.sp.config.ConfigurationDocument.Configuration newInstance(org.apache.xmlbeans.XmlOptions options) {
              return (de.hsos.richwps.sp.config.ConfigurationDocument.Configuration) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
            
            private Factory() { } // No instance of this class allowed
        }
    }
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static de.hsos.richwps.sp.config.ConfigurationDocument newInstance() {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.ConfigurationDocument parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.ConfigurationDocument) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
