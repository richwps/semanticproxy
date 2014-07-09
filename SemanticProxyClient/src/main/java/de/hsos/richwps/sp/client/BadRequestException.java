/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

/**
 * Exception used for error appearing due to an erroneous client request to the
 * SemanticProxy
 *
 * @author fbensman
 */
public class BadRequestException extends Exception {

    public BadRequestException(String msg) {
        super(msg);
    }
}
