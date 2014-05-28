/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

/**
 * Small class to store information about a validation
 * @author fbensman
 */
public class ValidationResult {
    public boolean result = false;
        public String message = null;
        public ValidationResult(boolean result, String msg){
            this.result = result;
            this.message=msg;
        }
}
