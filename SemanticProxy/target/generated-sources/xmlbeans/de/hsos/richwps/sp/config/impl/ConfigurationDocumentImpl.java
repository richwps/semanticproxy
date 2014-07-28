/*
 * An XML document type.
 * Localname: Configuration
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.ConfigurationDocument
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config.impl;
/**
 * A document containing one Configuration(@http://www.hsos.de/richwps/sp/config) element.
 *
 * This is a complex type.
 */
public class ConfigurationDocumentImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements de.hsos.richwps.sp.config.ConfigurationDocument
{
    private static final long serialVersionUID = 1L;
    
    public ConfigurationDocumentImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName CONFIGURATION$0 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Configuration");
    
    
    /**
     * Gets the "Configuration" element
     */
    public de.hsos.richwps.sp.config.ConfigurationDocument.Configuration getConfiguration()
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.ConfigurationDocument.Configuration target = null;
            target = (de.hsos.richwps.sp.config.ConfigurationDocument.Configuration)get_store().find_element_user(CONFIGURATION$0, 0);
            if (target == null)
            {
                return null;
            }
            return target;
        }
    }
    
    /**
     * Sets the "Configuration" element
     */
    public void setConfiguration(de.hsos.richwps.sp.config.ConfigurationDocument.Configuration configuration)
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.ConfigurationDocument.Configuration target = null;
            target = (de.hsos.richwps.sp.config.ConfigurationDocument.Configuration)get_store().find_element_user(CONFIGURATION$0, 0);
            if (target == null)
            {
                target = (de.hsos.richwps.sp.config.ConfigurationDocument.Configuration)get_store().add_element_user(CONFIGURATION$0);
            }
            target.set(configuration);
        }
    }
    
    /**
     * Appends and returns a new empty "Configuration" element
     */
    public de.hsos.richwps.sp.config.ConfigurationDocument.Configuration addNewConfiguration()
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.ConfigurationDocument.Configuration target = null;
            target = (de.hsos.richwps.sp.config.ConfigurationDocument.Configuration)get_store().add_element_user(CONFIGURATION$0);
            return target;
        }
    }
    /**
     * An XML Configuration(@http://www.hsos.de/richwps/sp/config).
     *
     * This is a complex type.
     */
    public static class ConfigurationImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements de.hsos.richwps.sp.config.ConfigurationDocument.Configuration
    {
        private static final long serialVersionUID = 1L;
        
        public ConfigurationImpl(org.apache.xmlbeans.SchemaType sType)
        {
            super(sType);
        }
        
        private static final javax.xml.namespace.QName RDFDIRECTORY$0 = 
            new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "RDFDirectory");
        private static final javax.xml.namespace.QName STARTCLEAN$2 = 
            new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "StartClean");
        private static final javax.xml.namespace.QName OWNER$4 = 
            new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Owner");
        private static final javax.xml.namespace.QName DOMAIN$6 = 
            new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Domain");
        private static final javax.xml.namespace.QName PRELOADFILES$8 = 
            new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "PreloadFiles");
        private static final javax.xml.namespace.QName HTTPENDPOINTS$10 = 
            new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "HTTPEndpoints");
        private static final javax.xml.namespace.QName RDFNAMINGENDPOINTS$12 = 
            new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "RDFNamingEndpoints");
        private static final javax.xml.namespace.QName PORT$14 = 
            new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Port");
        
        
        /**
         * Gets the "RDFDirectory" element
         */
        public java.lang.String getRDFDirectory()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RDFDIRECTORY$0, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "RDFDirectory" element
         */
        public org.apache.xmlbeans.XmlString xgetRDFDirectory()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RDFDIRECTORY$0, 0);
                return target;
            }
        }
        
        /**
         * Sets the "RDFDirectory" element
         */
        public void setRDFDirectory(java.lang.String rdfDirectory)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RDFDIRECTORY$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RDFDIRECTORY$0);
                }
                target.setStringValue(rdfDirectory);
            }
        }
        
        /**
         * Sets (as xml) the "RDFDirectory" element
         */
        public void xsetRDFDirectory(org.apache.xmlbeans.XmlString rdfDirectory)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RDFDIRECTORY$0, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RDFDIRECTORY$0);
                }
                target.set(rdfDirectory);
            }
        }
        
        /**
         * Gets the "StartClean" element
         */
        public boolean getStartClean()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STARTCLEAN$2, 0);
                if (target == null)
                {
                    return false;
                }
                return target.getBooleanValue();
            }
        }
        
        /**
         * Gets (as xml) the "StartClean" element
         */
        public org.apache.xmlbeans.XmlBoolean xgetStartClean()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(STARTCLEAN$2, 0);
                return target;
            }
        }
        
        /**
         * Sets the "StartClean" element
         */
        public void setStartClean(boolean startClean)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(STARTCLEAN$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(STARTCLEAN$2);
                }
                target.setBooleanValue(startClean);
            }
        }
        
        /**
         * Sets (as xml) the "StartClean" element
         */
        public void xsetStartClean(org.apache.xmlbeans.XmlBoolean startClean)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlBoolean target = null;
                target = (org.apache.xmlbeans.XmlBoolean)get_store().find_element_user(STARTCLEAN$2, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlBoolean)get_store().add_element_user(STARTCLEAN$2);
                }
                target.set(startClean);
            }
        }
        
        /**
         * Gets the "Owner" element
         */
        public java.lang.String getOwner()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OWNER$4, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Owner" element
         */
        public org.apache.xmlbeans.XmlString xgetOwner()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OWNER$4, 0);
                return target;
            }
        }
        
        /**
         * Sets the "Owner" element
         */
        public void setOwner(java.lang.String owner)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OWNER$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OWNER$4);
                }
                target.setStringValue(owner);
            }
        }
        
        /**
         * Sets (as xml) the "Owner" element
         */
        public void xsetOwner(org.apache.xmlbeans.XmlString owner)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OWNER$4, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OWNER$4);
                }
                target.set(owner);
            }
        }
        
        /**
         * Gets the "Domain" element
         */
        public java.lang.String getDomain()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DOMAIN$6, 0);
                if (target == null)
                {
                    return null;
                }
                return target.getStringValue();
            }
        }
        
        /**
         * Gets (as xml) the "Domain" element
         */
        public org.apache.xmlbeans.XmlString xgetDomain()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DOMAIN$6, 0);
                return target;
            }
        }
        
        /**
         * Sets the "Domain" element
         */
        public void setDomain(java.lang.String domain)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(DOMAIN$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(DOMAIN$6);
                }
                target.setStringValue(domain);
            }
        }
        
        /**
         * Sets (as xml) the "Domain" element
         */
        public void xsetDomain(org.apache.xmlbeans.XmlString domain)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlString target = null;
                target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(DOMAIN$6, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(DOMAIN$6);
                }
                target.set(domain);
            }
        }
        
        /**
         * Gets the "PreloadFiles" element
         */
        public de.hsos.richwps.sp.config.PreloadFiles getPreloadFiles()
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.PreloadFiles target = null;
                target = (de.hsos.richwps.sp.config.PreloadFiles)get_store().find_element_user(PRELOADFILES$8, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "PreloadFiles" element
         */
        public void setPreloadFiles(de.hsos.richwps.sp.config.PreloadFiles preloadFiles)
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.PreloadFiles target = null;
                target = (de.hsos.richwps.sp.config.PreloadFiles)get_store().find_element_user(PRELOADFILES$8, 0);
                if (target == null)
                {
                    target = (de.hsos.richwps.sp.config.PreloadFiles)get_store().add_element_user(PRELOADFILES$8);
                }
                target.set(preloadFiles);
            }
        }
        
        /**
         * Appends and returns a new empty "PreloadFiles" element
         */
        public de.hsos.richwps.sp.config.PreloadFiles addNewPreloadFiles()
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.PreloadFiles target = null;
                target = (de.hsos.richwps.sp.config.PreloadFiles)get_store().add_element_user(PRELOADFILES$8);
                return target;
            }
        }
        
        /**
         * Gets the "HTTPEndpoints" element
         */
        public de.hsos.richwps.sp.config.HTTPEndpoints getHTTPEndpoints()
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.HTTPEndpoints target = null;
                target = (de.hsos.richwps.sp.config.HTTPEndpoints)get_store().find_element_user(HTTPENDPOINTS$10, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "HTTPEndpoints" element
         */
        public void setHTTPEndpoints(de.hsos.richwps.sp.config.HTTPEndpoints httpEndpoints)
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.HTTPEndpoints target = null;
                target = (de.hsos.richwps.sp.config.HTTPEndpoints)get_store().find_element_user(HTTPENDPOINTS$10, 0);
                if (target == null)
                {
                    target = (de.hsos.richwps.sp.config.HTTPEndpoints)get_store().add_element_user(HTTPENDPOINTS$10);
                }
                target.set(httpEndpoints);
            }
        }
        
        /**
         * Appends and returns a new empty "HTTPEndpoints" element
         */
        public de.hsos.richwps.sp.config.HTTPEndpoints addNewHTTPEndpoints()
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.HTTPEndpoints target = null;
                target = (de.hsos.richwps.sp.config.HTTPEndpoints)get_store().add_element_user(HTTPENDPOINTS$10);
                return target;
            }
        }
        
        /**
         * Gets the "RDFNamingEndpoints" element
         */
        public de.hsos.richwps.sp.config.RDFNamingEndpoints getRDFNamingEndpoints()
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.RDFNamingEndpoints target = null;
                target = (de.hsos.richwps.sp.config.RDFNamingEndpoints)get_store().find_element_user(RDFNAMINGENDPOINTS$12, 0);
                if (target == null)
                {
                    return null;
                }
                return target;
            }
        }
        
        /**
         * Sets the "RDFNamingEndpoints" element
         */
        public void setRDFNamingEndpoints(de.hsos.richwps.sp.config.RDFNamingEndpoints rdfNamingEndpoints)
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.RDFNamingEndpoints target = null;
                target = (de.hsos.richwps.sp.config.RDFNamingEndpoints)get_store().find_element_user(RDFNAMINGENDPOINTS$12, 0);
                if (target == null)
                {
                    target = (de.hsos.richwps.sp.config.RDFNamingEndpoints)get_store().add_element_user(RDFNAMINGENDPOINTS$12);
                }
                target.set(rdfNamingEndpoints);
            }
        }
        
        /**
         * Appends and returns a new empty "RDFNamingEndpoints" element
         */
        public de.hsos.richwps.sp.config.RDFNamingEndpoints addNewRDFNamingEndpoints()
        {
            synchronized (monitor())
            {
                check_orphaned();
                de.hsos.richwps.sp.config.RDFNamingEndpoints target = null;
                target = (de.hsos.richwps.sp.config.RDFNamingEndpoints)get_store().add_element_user(RDFNAMINGENDPOINTS$12);
                return target;
            }
        }
        
        /**
         * Gets the "Port" element
         */
        public int getPort()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PORT$14, 0);
                if (target == null)
                {
                    return 0;
                }
                return target.getIntValue();
            }
        }
        
        /**
         * Gets (as xml) the "Port" element
         */
        public org.apache.xmlbeans.XmlInt xgetPort()
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PORT$14, 0);
                return target;
            }
        }
        
        /**
         * Sets the "Port" element
         */
        public void setPort(int port)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.SimpleValue target = null;
                target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PORT$14, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PORT$14);
                }
                target.setIntValue(port);
            }
        }
        
        /**
         * Sets (as xml) the "Port" element
         */
        public void xsetPort(org.apache.xmlbeans.XmlInt port)
        {
            synchronized (monitor())
            {
                check_orphaned();
                org.apache.xmlbeans.XmlInt target = null;
                target = (org.apache.xmlbeans.XmlInt)get_store().find_element_user(PORT$14, 0);
                if (target == null)
                {
                    target = (org.apache.xmlbeans.XmlInt)get_store().add_element_user(PORT$14);
                }
                target.set(port);
            }
        }
    }
}
