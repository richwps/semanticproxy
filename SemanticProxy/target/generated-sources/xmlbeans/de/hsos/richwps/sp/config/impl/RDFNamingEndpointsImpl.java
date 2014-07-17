/*
 * XML Type:  RDFNamingEndpoints
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.RDFNamingEndpoints
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config.impl;
/**
 * An XML RDFNamingEndpoints(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public class RDFNamingEndpointsImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements de.hsos.richwps.sp.config.RDFNamingEndpoints
{
    private static final long serialVersionUID = 1L;
    
    public RDFNamingEndpointsImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName WPSNAMING$0 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "WPSNaming");
    private static final javax.xml.namespace.QName PROCESSNAMING$2 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "ProcessNaming");
    private static final javax.xml.namespace.QName INPUTNAMING$4 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "InputNaming");
    private static final javax.xml.namespace.QName OUTPUTNAMING$6 = 
        new javax.xml.namespace.QName("http://www.hsos.de/richwps/sp/config", "OutputNaming");
    
    
    /**
     * Gets the "WPSNaming" element
     */
    public java.lang.String getWPSNaming()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WPSNAMING$0, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "WPSNaming" element
     */
    public org.apache.xmlbeans.XmlString xgetWPSNaming()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WPSNAMING$0, 0);
            return target;
        }
    }
    
    /**
     * Sets the "WPSNaming" element
     */
    public void setWPSNaming(java.lang.String wpsNaming)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(WPSNAMING$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(WPSNAMING$0);
            }
            target.setStringValue(wpsNaming);
        }
    }
    
    /**
     * Sets (as xml) the "WPSNaming" element
     */
    public void xsetWPSNaming(org.apache.xmlbeans.XmlString wpsNaming)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(WPSNAMING$0, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(WPSNAMING$0);
            }
            target.set(wpsNaming);
        }
    }
    
    /**
     * Gets the "ProcessNaming" element
     */
    public java.lang.String getProcessNaming()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESSNAMING$2, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "ProcessNaming" element
     */
    public org.apache.xmlbeans.XmlString xgetProcessNaming()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESSNAMING$2, 0);
            return target;
        }
    }
    
    /**
     * Sets the "ProcessNaming" element
     */
    public void setProcessNaming(java.lang.String processNaming)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(PROCESSNAMING$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(PROCESSNAMING$2);
            }
            target.setStringValue(processNaming);
        }
    }
    
    /**
     * Sets (as xml) the "ProcessNaming" element
     */
    public void xsetProcessNaming(org.apache.xmlbeans.XmlString processNaming)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(PROCESSNAMING$2, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(PROCESSNAMING$2);
            }
            target.set(processNaming);
        }
    }
    
    /**
     * Gets the "InputNaming" element
     */
    public java.lang.String getInputNaming()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INPUTNAMING$4, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "InputNaming" element
     */
    public org.apache.xmlbeans.XmlString xgetInputNaming()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INPUTNAMING$4, 0);
            return target;
        }
    }
    
    /**
     * Sets the "InputNaming" element
     */
    public void setInputNaming(java.lang.String inputNaming)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(INPUTNAMING$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(INPUTNAMING$4);
            }
            target.setStringValue(inputNaming);
        }
    }
    
    /**
     * Sets (as xml) the "InputNaming" element
     */
    public void xsetInputNaming(org.apache.xmlbeans.XmlString inputNaming)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(INPUTNAMING$4, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(INPUTNAMING$4);
            }
            target.set(inputNaming);
        }
    }
    
    /**
     * Gets the "OutputNaming" element
     */
    public java.lang.String getOutputNaming()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OUTPUTNAMING$6, 0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "OutputNaming" element
     */
    public org.apache.xmlbeans.XmlString xgetOutputNaming()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OUTPUTNAMING$6, 0);
            return target;
        }
    }
    
    /**
     * Sets the "OutputNaming" element
     */
    public void setOutputNaming(java.lang.String outputNaming)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_element_user(OUTPUTNAMING$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_element_user(OUTPUTNAMING$6);
            }
            target.setStringValue(outputNaming);
        }
    }
    
    /**
     * Sets (as xml) the "OutputNaming" element
     */
    public void xsetOutputNaming(org.apache.xmlbeans.XmlString outputNaming)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_element_user(OUTPUTNAMING$6, 0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_element_user(OUTPUTNAMING$6);
            }
            target.set(outputNaming);
        }
    }
}
