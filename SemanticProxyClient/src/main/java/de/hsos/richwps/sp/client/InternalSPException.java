/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

/**
 * Exception used for errors on the server side
 *
 * @author fbensman
 */
public class InternalSPException extends Exception {

    public InternalSPException(String msg) {
        super(msg);
    }
}
