/*
 * XML Type:  PreloadFiles
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.PreloadFiles
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config;


/**
 * An XML PreloadFiles(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public interface PreloadFiles extends org.apache.xmlbeans.XmlObject
{
    public static final org.apache.xmlbeans.SchemaType type = (org.apache.xmlbeans.SchemaType)
        org.apache.xmlbeans.XmlBeans.typeSystemForClassLoader(PreloadFiles.class.getClassLoader(), "schemaorg_apache_xmlbeans.system.sC4987F4EACB0C561DB70AF05FAEFC74D").resolveHandle("preloadfiles6054type");
    
    /**
     * Gets array of all "File" elements
     */
    java.lang.String[] getFileArray();
    
    /**
     * Gets ith "File" element
     */
    java.lang.String getFileArray(int i);
    
    /**
     * Gets (as xml) array of all "File" elements
     */
    org.apache.xmlbeans.XmlString[] xgetFileArray();
    
    /**
     * Gets (as xml) ith "File" element
     */
    org.apache.xmlbeans.XmlString xgetFileArray(int i);
    
    /**
     * Returns number of "File" element
     */
    int sizeOfFileArray();
    
    /**
     * Sets array of all "File" element
     */
    void setFileArray(java.lang.String[] fileArray);
    
    /**
     * Sets ith "File" element
     */
    void setFileArray(int i, java.lang.String file);
    
    /**
     * Sets (as xml) array of all "File" element
     */
    void xsetFileArray(org.apache.xmlbeans.XmlString[] fileArray);
    
    /**
     * Sets (as xml) ith "File" element
     */
    void xsetFileArray(int i, org.apache.xmlbeans.XmlString file);
    
    /**
     * Inserts the value as the ith "File" element
     */
    void insertFile(int i, java.lang.String file);
    
    /**
     * Appends the value as the last "File" element
     */
    void addFile(java.lang.String file);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "File" element
     */
    org.apache.xmlbeans.XmlString insertNewFile(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "File" element
     */
    org.apache.xmlbeans.XmlString addNewFile();
    
    /**
     * Removes the ith "File" element
     */
    void removeFile(int i);
    
    /**
     * Gets array of all "WPS" elements
     */
    java.lang.String[] getWPSArray();
    
    /**
     * Gets ith "WPS" element
     */
    java.lang.String getWPSArray(int i);
    
    /**
     * Gets (as xml) array of all "WPS" elements
     */
    org.apache.xmlbeans.XmlString[] xgetWPSArray();
    
    /**
     * Gets (as xml) ith "WPS" element
     */
    org.apache.xmlbeans.XmlString xgetWPSArray(int i);
    
    /**
     * Returns number of "WPS" element
     */
    int sizeOfWPSArray();
    
    /**
     * Sets array of all "WPS" element
     */
    void setWPSArray(java.lang.String[] wpsArray);
    
    /**
     * Sets ith "WPS" element
     */
    void setWPSArray(int i, java.lang.String wps);
    
    /**
     * Sets (as xml) array of all "WPS" element
     */
    void xsetWPSArray(org.apache.xmlbeans.XmlString[] wpsArray);
    
    /**
     * Sets (as xml) ith "WPS" element
     */
    void xsetWPSArray(int i, org.apache.xmlbeans.XmlString wps);
    
    /**
     * Inserts the value as the ith "WPS" element
     */
    void insertWPS(int i, java.lang.String wps);
    
    /**
     * Appends the value as the last "WPS" element
     */
    void addWPS(java.lang.String wps);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "WPS" element
     */
    org.apache.xmlbeans.XmlString insertNewWPS(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "WPS" element
     */
    org.apache.xmlbeans.XmlString addNewWPS();
    
    /**
     * Removes the ith "WPS" element
     */
    void removeWPS(int i);
    
    /**
     * Gets array of all "Process" elements
     */
    java.lang.String[] getProcessArray();
    
    /**
     * Gets ith "Process" element
     */
    java.lang.String getProcessArray(int i);
    
    /**
     * Gets (as xml) array of all "Process" elements
     */
    org.apache.xmlbeans.XmlString[] xgetProcessArray();
    
    /**
     * Gets (as xml) ith "Process" element
     */
    org.apache.xmlbeans.XmlString xgetProcessArray(int i);
    
    /**
     * Returns number of "Process" element
     */
    int sizeOfProcessArray();
    
    /**
     * Sets array of all "Process" element
     */
    void setProcessArray(java.lang.String[] processArray);
    
    /**
     * Sets ith "Process" element
     */
    void setProcessArray(int i, java.lang.String process);
    
    /**
     * Sets (as xml) array of all "Process" element
     */
    void xsetProcessArray(org.apache.xmlbeans.XmlString[] processArray);
    
    /**
     * Sets (as xml) ith "Process" element
     */
    void xsetProcessArray(int i, org.apache.xmlbeans.XmlString process);
    
    /**
     * Inserts the value as the ith "Process" element
     */
    void insertProcess(int i, java.lang.String process);
    
    /**
     * Appends the value as the last "Process" element
     */
    void addProcess(java.lang.String process);
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Process" element
     */
    org.apache.xmlbeans.XmlString insertNewProcess(int i);
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Process" element
     */
    org.apache.xmlbeans.XmlString addNewProcess();
    
    /**
     * Removes the ith "Process" element
     */
    void removeProcess(int i);
    
    /**
     * A factory class with static methods for creating instances
     * of this type.
     */
    
    public static final class Factory
    {
        public static de.hsos.richwps.sp.config.PreloadFiles newInstance() {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** @param xmlAsString the string value to parse */
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.lang.String xmlAsString) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, null ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.lang.String xmlAsString, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xmlAsString, type, options ); }
        
        /** @param file the file from which to load an xml document */
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.io.File file) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, null ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.io.File file, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( file, type, options ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, null ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( u, type, options ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(javax.xml.stream.XMLStreamReader sr) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, null ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(javax.xml.stream.XMLStreamReader sr, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( sr, type, options ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        public static de.hsos.richwps.sp.config.PreloadFiles parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.PreloadFiles parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static de.hsos.richwps.sp.config.PreloadFiles parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (de.hsos.richwps.sp.config.PreloadFiles) org.apache.xmlbeans.XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** @deprecated {@link org.apache.xmlbeans.xml.stream.XMLInputStream} */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return org.apache.xmlbeans.XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}
