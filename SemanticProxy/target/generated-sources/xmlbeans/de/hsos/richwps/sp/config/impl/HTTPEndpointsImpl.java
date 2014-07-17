/*
 * XML Type:  HTTPEndpoints
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.HTTPEndpoints
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config.impl;
/**
 * An XML HTTPEndpoints(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public class HTTPEndpointsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements de.hsos.richwps.sp.config.HTTPEndpoints
{
    private static final long serialVersionUID = 1L;
    
    public HTTPEndpointsImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName HOST$0 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Host");
    private static final javax.xml.namespace.QName APPLICATION$2 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Application");
    private static final javax.xml.namespace.QName RESOURCES$4 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Resources");
    private static final javax.xml.namespace.QName VOCABULARY$6 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Vocabulary");
    private static final javax.xml.namespace.QName WPSLIST$8 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "WPSList");
    private static final javax.xml.namespace.QName PROCESSLIST$10 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "ProcessList");
    private static final javax.xml.namespace.QName SEARCH$12 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Search");
    
    
    /**
     * Gets the "Host" element
     */
    public java.lang.String getHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOST$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Host" element
     */
    public org.apache.xmlbeans.XmlAnyURI xgetHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlAnyURI target = null;
            target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_element_user(HOST$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Host" element
     */
    public void setHost(java.lang.String host)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(HOST$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(HOST$0);
            }
            target.setStringValue(host);
        }
    }
    
    /**
     * Sets (as xml) the "Host" element
     */
    public void xsetHost(org.apache.xmlbeans.XmlAnyURI host)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlAnyURI target = null;
            target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_element_user(HOST$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().add_element_user(HOST$0);
            }
            target.set(host);
        }
    }
    
    /**
     * Gets the "Application" element
     */
    public java.lang.String getApplication()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(APPLICATION$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Application" element
     */
    public org.apache.xmlbeans.XmlString xgetApplication()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(APPLICATION$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Application" element
     */
    public void setApplication(java.lang.String application)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(APPLICATION$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(APPLICATION$2);
            }
            target.setStringValue(application);
        }
    }
    
    /**
     * Sets (as xml) the "Application" element
     */
    public void xsetApplication(org.apache.xmlbeans.XmlString application)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(APPLICATION$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(APPLICATION$2);
            }
            target.set(application);
        }
    }
    
    /**
     * Gets the "Resources" element
     */
    public java.lang.String getResources()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESOURCES$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Resources" element
     */
    public org.apache.xmlbeans.XmlString xgetResources()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESOURCES$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Resources" element
     */
    public void setResources(java.lang.String resources)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(RESOURCES$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(RESOURCES$4);
            }
            target.setStringValue(resources);
        }
    }
    
    /**
     * Sets (as xml) the "Resources" element
     */
    public void xsetResources(org.apache.xmlbeans.XmlString resources)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(RESOURCES$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(RESOURCES$4);
            }
            target.set(resources);
        }
    }
    
    /**
     * Gets the "Vocabulary" element
     */
    public java.lang.String getVocabulary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VOCABULARY$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Vocabulary" element
     */
    public org.apache.xmlbeans.XmlString xgetVocabulary()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VOCABULARY$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Vocabulary" element
     */
    public void setVocabulary(java.lang.String vocabulary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(VOCABULARY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(VOCABULARY$6);
            }
            target.setStringValue(vocabulary);
        }
    }
    
    /**
     * Sets (as xml) the "Vocabulary" element
     */
    public void xsetVocabulary(org.apache.xmlbeans.XmlString vocabulary)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(VOCABULARY$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(VOCABULARY$6);
            }
            target.set(vocabulary);
        }
    }
    
    /**
     * Gets the "WPSList" element
     */
    public java.lang.String getWPSList()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WPSLIST$8, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "WPSList" element
     */
    public org.apache.xmlbeans.XmlString xgetWPSList()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WPSLIST$8, 0);
            return target;
        }
    }
    
    /**
     * Sets the "WPSList" element
     */
    public void setWPSList(java.lang.String wpsList)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WPSLIST$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WPSLIST$8);
            }
            target.setStringValue(wpsList);
        }
    }
    
    /**
     * Sets (as xml) the "WPSList" element
     */
    public void xsetWPSList(org.apache.xmlbeans.XmlString wpsList)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WPSLIST$8, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WPSLIST$8);
            }
            target.set(wpsList);
        }
    }
    
    /**
     * Gets the "ProcessList" element
     */
    public java.lang.String getProcessList()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESSLIST$10, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ProcessList" element
     */
    public org.apache.xmlbeans.XmlString xgetProcessList()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESSLIST$10, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ProcessList" element
     */
    public void setProcessList(java.lang.String processList)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESSLIST$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROCESSLIST$10);
            }
            target.setStringValue(processList);
        }
    }
    
    /**
     * Sets (as xml) the "ProcessList" element
     */
    public void xsetProcessList(org.apache.xmlbeans.XmlString processList)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESSLIST$10, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROCESSLIST$10);
            }
            target.set(processList);
        }
    }
    
    /**
     * Gets the "Search" element
     */
    public java.lang.String getSearch()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEARCH$12, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "Search" element
     */
    public org.apache.xmlbeans.XmlString xgetSearch()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SEARCH$12, 0);
            return target;
        }
    }
    
    /**
     * Sets the "Search" element
     */
    public void setSearch(java.lang.String search)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(SEARCH$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(SEARCH$12);
            }
            target.setStringValue(search);
        }
    }
    
    /**
     * Sets (as xml) the "Search" element
     */
    public void xsetSearch(org.apache.xmlbeans.XmlString search)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(SEARCH$12, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(SEARCH$12);
            }
            target.set(search);
        }
    }
}
