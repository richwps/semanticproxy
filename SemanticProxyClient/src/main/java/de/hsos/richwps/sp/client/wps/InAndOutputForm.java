/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client.wps;

/**
 *
 * @author fbensman
 */
public abstract class InAndOutputForm {
    
    public static final int LITERAL_TYPE = 0;
    public static final int BOUNDING_BOX_TYPE = 1;
    public static final int COMPLEX_TYPE = 2;
    
    public abstract int getDataType();
    
}
