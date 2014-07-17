/*
 * XML Type:  PreloadFiles
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.PreloadFiles
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config.impl;
/**
 * An XML PreloadFiles(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public class PreloadFilesImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements de.hsos.richwps.sp.config.PreloadFiles
{
    private static final long serialVersionUID = 1L;
    
    public PreloadFilesImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName WPS$0 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "WPS");
    private static final javax.xml.namespace.QName PROCESS$2 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Process");
    private static final javax.xml.namespace.QName REPLACEABLEHOST$4 = 
        new javax.xml.namespace.QName("", "ReplaceableHost");
    
    
    /**
     * Gets array of all "WPS" elements
     */
    public java.lang.String[] getWPSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(WPS$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "WPS" element
     */
    public java.lang.String getWPSArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WPS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "WPS" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetWPSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(WPS$0, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "WPS" element
     */
    public org.apache.xmlbeans.XmlString xgetWPSArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WPS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Returns number of "WPS" element
     */
    public int sizeOfWPSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(WPS$0);
        }
    }
    
    /**
     * Sets array of all "WPS" element
     */
    public void setWPSArray(java.lang.String[] wpsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(wpsArray, WPS$0);
        }
    }
    
    /**
     * Sets ith "WPS" element
     */
    public void setWPSArray(int i, java.lang.String wps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WPS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(wps);
        }
    }
    
    /**
     * Sets (as xml) array of all "WPS" element
     */
    public void xsetWPSArray(org.apache.xmlbeans.XmlString[]wpsArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(wpsArray, WPS$0);
        }
    }
    
    /**
     * Sets (as xml) ith "WPS" element
     */
    public void xsetWPSArray(int i, org.apache.xmlbeans.XmlString wps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WPS$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(wps);
        }
    }
    
    /**
     * Inserts the value as the ith "WPS" element
     */
    public void insertWPS(int i, java.lang.String wps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(WPS$0, i);
            target.setStringValue(wps);
        }
    }
    
    /**
     * Appends the value as the last "WPS" element
     */
    public void addWPS(java.lang.String wps)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WPS$0);
            target.setStringValue(wps);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "WPS" element
     */
    public org.apache.xmlbeans.XmlString insertNewWPS(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(WPS$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "WPS" element
     */
    public org.apache.xmlbeans.XmlString addNewWPS()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WPS$0);
            return target;
        }
    }
    
    /**
     * Removes the ith "WPS" element
     */
    public void removeWPS(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(WPS$0, i);
        }
    }
    
    /**
     * Gets array of all "Process" elements
     */
    public java.lang.String[] getProcessArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PROCESS$2, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "Process" element
     */
    public java.lang.String getProcessArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "Process" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetProcessArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(PROCESS$2, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "Process" element
     */
    public org.apache.xmlbeans.XmlString xgetProcessArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
        }
    }
    
    /**
     * Returns number of "Process" element
     */
    public int sizeOfProcessArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().count_elements(PROCESS$2);
        }
    }
    
    /**
     * Sets array of all "Process" element
     */
    public void setProcessArray(java.lang.String[] processArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(processArray, PROCESS$2);
        }
    }
    
    /**
     * Sets ith "Process" element
     */
    public void setProcessArray(int i, java.lang.String process)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(process);
        }
    }
    
    /**
     * Sets (as xml) array of all "Process" element
     */
    public void xsetProcessArray(org.apache.xmlbeans.XmlString[]processArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(processArray, PROCESS$2);
        }
    }
    
    /**
     * Sets (as xml) ith "Process" element
     */
    public void xsetProcessArray(int i, org.apache.xmlbeans.XmlString process)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESS$2, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(process);
        }
    }
    
    /**
     * Inserts the value as the ith "Process" element
     */
    public void insertProcess(int i, java.lang.String process)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(PROCESS$2, i);
            target.setStringValue(process);
        }
    }
    
    /**
     * Appends the value as the last "Process" element
     */
    public void addProcess(java.lang.String process)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROCESS$2);
            target.setStringValue(process);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "Process" element
     */
    public org.apache.xmlbeans.XmlString insertNewProcess(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(PROCESS$2, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "Process" element
     */
    public org.apache.xmlbeans.XmlString addNewProcess()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROCESS$2);
            return target;
        }
    }
    
    /**
     * Removes the ith "Process" element
     */
    public void removeProcess(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_element(PROCESS$2, i);
        }
    }
    
    /**
     * Gets the "ReplaceableHost" attribute
     */
    public java.lang.String getReplaceableHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REPLACEABLEHOST$4);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ReplaceableHost" attribute
     */
    public org.apache.xmlbeans.XmlString xgetReplaceableHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(REPLACEABLEHOST$4);
            return target;
        }
    }
    
    /**
     * True if has "ReplaceableHost" attribute
     */
    public boolean isSetReplaceableHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            return get_store().find_attribute_user(REPLACEABLEHOST$4) != null;
        }
    }
    
    /**
     * Sets the "ReplaceableHost" attribute
     */
    public void setReplaceableHost(java.lang.String replaceableHost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(REPLACEABLEHOST$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(REPLACEABLEHOST$4);
            }
            target.setStringValue(replaceableHost);
        }
    }
    
    /**
     * Sets (as xml) the "ReplaceableHost" attribute
     */
    public void xsetReplaceableHost(org.apache.xmlbeans.XmlString replaceableHost)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(REPLACEABLEHOST$4);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(REPLACEABLEHOST$4);
            }
            target.set(replaceableHost);
        }
    }
    
    /**
     * Unsets the "ReplaceableHost" attribute
     */
    public void unsetReplaceableHost()
    {
        synchronized (monitor())
        {
            check_orphaned();
            get_store().remove_attribute(REPLACEABLEHOST$4);
        }
    }
}
