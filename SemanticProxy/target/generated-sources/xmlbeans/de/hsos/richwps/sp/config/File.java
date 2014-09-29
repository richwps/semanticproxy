/*
 * XML Type:  File
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.File
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config;


/**
 * An XML File(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public interface File extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(File.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.s71861A4B545A157BCC3FC5169A3DFB42").resolveHandle("file6c86type");
    
    /**
     * Gets the "type" attribute
     */
    de.hsos.richwps.sp.config.ResourceType.Enum getType();
    
    /**
     * Gets (as xml) the "type" attribute
     */
    de.hsos.richwps.sp.config.ResourceType xgetType();
    
    /**
     * Sets the "type" attribute
     */
    void setType(de.hsos.richwps.sp.config.ResourceType.Enum type);
    
    /**
     * Sets (as xml) the "type" attribute
     */
    void xsetType(de.hsos.richwps.sp.config.ResourceType type);
    
    /**
     * Gets the "path" attribute
     */
    java.lang.String getPath();
    
    /**
     * Gets (as xml) the "path" attribute
     */
    org.apache.xmlbeans.XmlString xgetPath();
    
    /**
     * Sets the "path" attribute
     */
    void setPath(java.lang.String path);
    
    /**
     * Sets (as xml) the "path" attribute
     */
    void xsetPath(org.apache.xmlbeans.XmlString path);
    
    /**
     * Gets the "replaceableHost" attribute
     */
    java.lang.String getReplaceableHost();
    
    /**
     * Gets (as xml) the "replaceableHost" attribute
     */
    org.apache.xmlbeans.XmlString xgetReplaceableHost();
    
    /**
     * True if has "replaceableHost" attribute
     */
    boolean isSetReplaceableHost();
    
    /**
     * Sets the "replaceableHost" attribute
     */
    void setReplaceableHost(java.lang.String replaceableHost);
    
    /**
     * Sets (as xml) the "replaceableHost" attribute
     */
    void xsetReplaceableHost(org.apache.xmlbeans.XmlString replaceableHost);
    
    /**
     * Unsets the "replaceableHost" attribute
     */
    void unsetReplaceableHost();
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static de.hsos.richwps.sp.config.File newInstance() {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static de.hsos.richwps.sp.config.File newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static de.hsos.richwps.sp.config.File parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static de.hsos.richwps.sp.config.File parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static de.hsos.richwps.sp.config.File parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static de.hsos.richwps.sp.config.File parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static de.hsos.richwps.sp.config.File parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static de.hsos.richwps.sp.config.File parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static de.hsos.richwps.sp.config.File parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static de.hsos.richwps.sp.config.File parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static de.hsos.richwps.sp.config.File parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static de.hsos.richwps.sp.config.File parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static de.hsos.richwps.sp.config.File parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static de.hsos.richwps.sp.config.File parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static de.hsos.richwps.sp.config.File parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static de.hsos.richwps.sp.config.File parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.File parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.File parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.File) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
