/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.client;

/**
 *
 * @author fbensman
 */
public class LiteralExpression {

    public String predicate = null;
    public String literal = null;

    public LiteralExpression(String pred, String lit) {
        predicate = pred;
        literal = lit;
    }
}
