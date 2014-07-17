/*
 * XML Type:  RDFNamingEndpoints
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.RDFNamingEndpoints
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config;


/**
 * An XML RDFNamingEndpoints(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public interface RDFNamingEndpoints extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(RDFNamingEndpoints.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s03304F119F4A08AD48D992D6D1867513").resolveHandle("rdfnamingendpoints06c0type");
    
    /**
     * Gets the "WPSNaming" element
     */
    java.lang.String getWPSNaming();
    
    /**
     * Gets (as xml) the "WPSNaming" element
     */
    org.apache.xmlbeans.XmlString xgetWPSNaming();
    
    /**
     * Sets the "WPSNaming" element
     */
    void setWPSNaming(java.lang.String wpsNaming);
    
    /**
     * Sets (as xml) the "WPSNaming" element
     */
    void xsetWPSNaming(org.apache.xmlbeans.XmlString wpsNaming);
    
    /**
     * Gets the "ProcessNaming" element
     */
    java.lang.String getProcessNaming();
    
    /**
     * Gets (as xml) the "ProcessNaming" element
     */
    org.apache.xmlbeans.XmlString xgetProcessNaming();
    
    /**
     * Sets the "ProcessNaming" element
     */
    void setProcessNaming(java.lang.String processNaming);
    
    /**
     * Sets (as xml) the "ProcessNaming" element
     */
    void xsetProcessNaming(org.apache.xmlbeans.XmlString processNaming);
    
    /**
     * Gets the "InputNaming" element
     */
    java.lang.String getInputNaming();
    
    /**
     * Gets (as xml) the "InputNaming" element
     */
    org.apache.xmlbeans.XmlString xgetInputNaming();
    
    /**
     * Sets the "InputNaming" element
     */
    void setInputNaming(java.lang.String inputNaming);
    
    /**
     * Sets (as xml) the "InputNaming" element
     */
    void xsetInputNaming(org.apache.xmlbeans.XmlString inputNaming);
    
    /**
     * Gets the "OutputNaming" element
     */
    java.lang.String getOutputNaming();
    
    /**
     * Gets (as xml) the "OutputNaming" element
     */
    org.apache.xmlbeans.XmlString xgetOutputNaming();
    
    /**
     * Sets the "OutputNaming" element
     */
    void setOutputNaming(java.lang.String outputNaming);
    
    /**
     * Sets (as xml) the "OutputNaming" element
     */
    void xsetOutputNaming(org.apache.xmlbeans.XmlString outputNaming);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints newInstance() {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.RDFNamingEndpoints parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.RDFNamingEndpoints) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
