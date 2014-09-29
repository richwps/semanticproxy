/*
 * XML Type:  File
 * Namespace: http://www.hsos.de/richwps/sp/config
 * Java type: de.hsos.richwps.sp.config.File
 *
 * Automatically generated - do not modify.
 */
package de.hsos.richwps.sp.config.impl;
/**
 * An XML File(@http://www.hsos.de/richwps/sp/config).
 *
 * This is a complex type.
 */
public class FileImpl extends org.apache.xmlbeans.impl.values.XmlComplexContentImpl implements de.hsos.richwps.sp.config.File
{
    private static final long serialVersionUID = 1L;
    
    public FileImpl(org.apache.xmlbeans.SchemaType sType)
    {
        super(sType);
    }
    
    private static final javax.xml.namespace.QName TYPE$0 = 
        new javax.xml.namespace.QName("", "type");
    private static final javax.xml.namespace.QName PATH$2 = 
        new javax.xml.namespace.QName("", "path");
    private static final javax.xml.namespace.QName REPLACEABLEHOST$4 = 
        new javax.xml.namespace.QName("", "replaceableHost");
    
    
    /**
     * Gets the "type" attribute
     */
    public de.hsos.richwps.sp.config.ResourceType.Enum getType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TYPE$0);
            if (target == null)
            {
                return null;
            }
            return (de.hsos.richwps.sp.config.ResourceType.Enum)target.getEnumValue();
        }
    }
    
    /**
     * Gets (as xml) the "type" attribute
     */
    public de.hsos.richwps.sp.config.ResourceType xgetType()
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.ResourceType target = null;
            target = (de.hsos.richwps.sp.config.ResourceType)get_store().find_attribute_user(TYPE$0);
            return target;
        }
    }
    
    /**
     * Sets the "type" attribute
     */
    public void setType(de.hsos.richwps.sp.config.ResourceType.Enum type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(TYPE$0);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(TYPE$0);
            }
            target.setEnumValue(type);
        }
    }
    
    /**
     * Sets (as xml) the "type" attribute
     */
    public void xsetType(de.hsos.richwps.sp.config.ResourceType type)
    {
        synchronized (monitor())
        {
            check_orphaned();
            de.hsos.richwps.sp.config.ResourceType target = null;
            target = (de.hsos.richwps.sp.config.ResourceType)get_store().find_attribute_user(TYPE$0);
            if (target == null)
            {
                target = (de.hsos.richwps.sp.config.ResourceType)get_store().add_attribute_user(TYPE$0);
            }
            target.set(type);
        }
    }
    
    /**
     * Gets the "path" attribute
     */
    public java.lang.String getPath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PATH$2);
            if (target == null)
            {
                return null;
            }
            return target.getStringValue();
        }
    }
    
    /**
     * Gets (as xml) the "path" attribute
     */
    public org.apache.xmlbeans.XmlString xgetPath()
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PATH$2);
            return target;
        }
    }
    
    /**
     * Sets the "path" attribute
     */
    public void setPath(java.lang.String path)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.SimpleValue target = null;
            target = (org.apache.xmlbeans.SimpleValue)get_store().find_attribute_user(PATH$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.SimpleValue)get_store().add_attribute_user(PATH$2);
            }
            target.setStringValue(path);
        }
    }
    
    /**
     * Sets (as xml) the "path" attribute
     */
    public void xsetPath(org.apache.xmlbeans.XmlString path)
    {
        synchronized (monitor())
        {
            check_orphaned();
            org.apache.xmlbeans.XmlString target = null;
            target = (org.apache.xmlbeans.XmlString)get_store().find_attribute_user(PATH$2);
            if (target == null)
            {
                target = (org.apache.xmlbeans.XmlString)get_store().add_attribute_user(PATH$2);
            }
            target.set(path);
        }
    }
    
    /**
     * Gets the "replaceableHost" attribute
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
     * Gets (as xml) the "replaceableHost" attribute
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
     * True if has "replaceableHost" attribute
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
     * Sets the "replaceableHost" attribute
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
     * Sets (as xml) the "replaceableHost" attribute
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
     * Unsets the "replaceableHost" attribute
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
