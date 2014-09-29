/*
 * XML Type:  WPSServer
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.WPSServer
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config.impl;
/**
 * An XML WPSServer(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public class WPSServerImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements de.hsos.richwps.sp.config.WPSServer
{
    private static final long serialVersionUID = 1L;
    
    public WPSServerImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TARGETURL$0 = 
        new javax.xml.namespace.QName("", "targetURL");
    
    
    /**
     * Gets the "targetURL" attribute
     */
    public java.lang.String getTargetURL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TARGETURL$0);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "targetURL" attribute
     */
    public org.apache.xmlbeans.XmlAnyURI xgetTargetURL()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlAnyURI target = null;
            target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_attribute_user(TARGETURL$0);
            return target;
        }
    }
    
    /**
     * Sets the "targetURL" attribute
     */
    public void setTargetURL(java.lang.String targetURL)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TARGETURL$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TARGETURL$0);
            }
            target.setStringValue(targetURL);
        }
    }
    
    /**
     * Sets (as xml) the "targetURL" attribute
     */
    public void xsetTargetURL(org.apache.xmlbeans.XmlAnyURI targetURL)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlAnyURI target = null;
            target = (org.apache.xmlbeans.XmlAnyURI)get_store().find_attribute_user(TARGETURL$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlAnyURI)get_store().add_attribute_user(TARGETURL$0);
            }
            target.set(targetURL);
        }
    }
}
