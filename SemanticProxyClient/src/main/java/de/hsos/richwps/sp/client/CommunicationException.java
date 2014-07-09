/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

/**
 * Exception used for error appearing during low level communication with
 * SemanticProxy
 *
 * @author fbensman
 */
public class CommunicationException extends Exception {

    public CommunicationException(String msg) {
        super(msg);
    }
}
