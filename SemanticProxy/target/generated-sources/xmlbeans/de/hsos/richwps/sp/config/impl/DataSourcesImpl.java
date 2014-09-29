/*
 * XML Type:  DataSources
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.DataSources
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config.impl;
/**
 * An XML DataSources(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public class DataSourcesImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements de.hsos.richwps.sp.config.DataSources
{
    private static final long serialVersionUID = 1L;
    
    public DataSourcesImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName FILE$0 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "File");
    private static final javax.xml.namespace.QName WPSSERVER$2 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "WPSServer");
    
    
    /**
     * Gets array of all "File" elements
     */
    public de.hsos.richwps.sp.config.File[] getFileArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(FILE$0, targetList);
            de.hsos.richwps.sp.config.File[] result = new de.hsos.richwps.sp.config.File[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "File" element
     */
    public de.hsos.richwps.sp.config.File getFileArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.File target = null;
            target = (de.hsos.richwps.sp.config.File)get_store().find_element_user(FILE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "File" element
     */
    public int sizeOfFileArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(FILE$0);
        }
    }
    
    /**
     * Sets array of all "File" element
     */
    public void setFileArray(de.hsos.richwps.sp.config.File[] fileArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(fileArray, FILE$0);
        }
    }
    
    /**
     * Sets ith "File" element
     */
    public void setFileArray(int i, de.hsos.richwps.sp.config.File file)
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.File target = null;
            target = (de.hsos.richwps.sp.config.File)get_store().find_element_user(FILE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(file);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "File" element
     */
    public de.hsos.richwps.sp.config.File insertNewFile(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.File target = null;
            target = (de.hsos.richwps.sp.config.File)get_store().insert_element_user(FILE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "File" element
     */
    public de.hsos.richwps.sp.config.File addNewFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.File target = null;
            target = (de.hsos.richwps.sp.config.File)get_store().add_element_user(FILE$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "File" element
     */
    public void removeFile(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(FILE$0, i);
        }
    }
    
    /**
     * Gets array of all "WPSServer" elements
     */
    public de.hsos.richwps.sp.config.WPSServer[] getWPSServerArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(WPSSERVER$2, targetList);
            de.hsos.richwps.sp.config.WPSServer[] result = new de.hsos.richwps.sp.config.WPSServer[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets ith "WPSServer" element
     */
    public de.hsos.richwps.sp.config.WPSServer getWPSServerArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.WPSServer target = null;
            target = (de.hsos.richwps.sp.config.WPSServer)get_store().find_element_user(WPSSERVER$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target;
        }
    }
    
    /**
     * Returns number of "WPSServer" element
     */
    public int sizeOfWPSServerArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WPSSERVER$2);
        }
    }
    
    /**
     * Sets array of all "WPSServer" element
     */
    public void setWPSServerArray(de.hsos.richwps.sp.config.WPSServer[] wpsServerArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(wpsServerArray, WPSSERVER$2);
        }
    }
    
    /**
     * Sets ith "WPSServer" element
     */
    public void setWPSServerArray(int i, de.hsos.richwps.sp.config.WPSServer wpsServer)
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.WPSServer target = null;
            target = (de.hsos.richwps.sp.config.WPSServer)get_store().find_element_user(WPSSERVER$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(wpsServer);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "WPSServer" element
     */
    public de.hsos.richwps.sp.config.WPSServer insertNewWPSServer(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.WPSServer target = null;
            target = (de.hsos.richwps.sp.config.WPSServer)get_store().insert_element_user(WPSSERVER$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "WPSServer" element
     */
    public de.hsos.richwps.sp.config.WPSServer addNewWPSServer()
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.WPSServer target = null;
            target = (de.hsos.richwps.sp.config.WPSServer)get_store().add_element_user(WPSSERVER$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "WPSServer" element
     */
    public void removeWPSServer(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WPSSERVER$2, i);
        }
    }
}
