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
    
    private static final javax.xml.namespace.QName FILE$0 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "File");
    private static final javax.xml.namespace.QName WPS$2 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "WPS");
    private static final javax.xml.namespace.QName PROCESS$4 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "Process");
    
    
    /**
     * Gets array of all "File" elements
     */
    public java.lang.String[] getFileArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(FILE$0, targetList);
            java.lang.String[] result = new java.lang.String[targetList.size()];
            for (int i = 0, len = targetList.size() ; i < len ; i++)
                result[i] = ((org.apache.xmlbeans.SimpleValue)targetList.get(i)).getStringValue();
            return result;
        }
    }
    
    /**
     * Gets ith "File" element
     */
    public java.lang.String getFileArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) array of all "File" elements
     */
    public org.apache.xmlbeans.XmlString[] xgetFileArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(FILE$0, targetList);
            org.apache.xmlbeans.XmlString[] result = new org.apache.xmlbeans.XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
        }
    }
    
    /**
     * Gets (as xml) ith "File" element
     */
    public org.apache.xmlbeans.XmlString xgetFileArray(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            return (org.apache.xmlbeans.XmlString)target;
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
    public void setFileArray(java.lang.String[] fileArray)
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
    public void setFileArray(int i, java.lang.String file)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(FILE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.setStringValue(file);
        }
    }
    
    /**
     * Sets (as xml) array of all "File" element
     */
    public void xsetFileArray(org.apache.xmlbeans.XmlString[]fileArray)
    {
        synchronized (monitor())
        {
            check_orphaned();
            arraySetterHelper(fileArray, FILE$0);
        }
    }
    
    /**
     * Sets (as xml) ith "File" element
     */
    public void xsetFileArray(int i, org.apache.xmlbeans.XmlString file)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(FILE$0, i);
            if (target == null)
            {
                throw new IndexOutOfBoundsException();
            }
            target.set(file);
        }
    }
    
    /**
     * Inserts the value as the ith "File" element
     */
    public void insertFile(int i, java.lang.String file)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = 
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(FILE$0, i);
            target.setStringValue(file);
        }
    }
    
    /**
     * Appends the value as the last "File" element
     */
    public void addFile(java.lang.String file)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(FILE$0);
            target.setStringValue(file);
        }
    }
    
    /**
     * Inserts and returns a new empty value (as xml) as the ith "File" element
     */
    public org.apache.xmlbeans.XmlString insertNewFile(int i)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(FILE$0, i);
            return target;
        }
    }
    
    /**
     * Appends and returns a new empty value (as xml) as the last "File" element
     */
    public org.apache.xmlbeans.XmlString addNewFile()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(FILE$0);
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
     * Gets array of all "WPS" elements
     */
    public java.lang.String[] getWPSArray()
    {
        synchronized (monitor())
        {
            check_orphaned();
            java.util.List targetList = new java.util.ArrayList();
            get_store().find_all_element_users(WPS$2, targetList);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WPS$2, i);
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
            get_store().find_all_element_users(WPS$2, targetList);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WPS$2, i);
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
            return get_store().count_elements(WPS$2);
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
            arraySetterHelper(wpsArray, WPS$2);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WPS$2, i);
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
            arraySetterHelper(wpsArray, WPS$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WPS$2, i);
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
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(WPS$2, i);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WPS$2);
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
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(WPS$2, i);
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
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WPS$2);
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
            get_store().remove_element(WPS$2, i);
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
            get_store().find_all_element_users(PROCESS$4, targetList);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESS$4, i);
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
            get_store().find_all_element_users(PROCESS$4, targetList);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESS$4, i);
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
            return get_store().count_elements(PROCESS$4);
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
            arraySetterHelper(processArray, PROCESS$4);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESS$4, i);
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
            arraySetterHelper(processArray, PROCESS$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESS$4, i);
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
                (org.apache.xmlbeans.SimpleValue)get_store().insert_element_user(PROCESS$4, i);
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
            target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROCESS$4);
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
            target = (org.apache.xmlbeans.XmlString)get_store().insert_element_user(PROCESS$4, i);
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
            target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROCESS$4);
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
            get_store().remove_element(PROCESS$4, i);
        }
    }
}
