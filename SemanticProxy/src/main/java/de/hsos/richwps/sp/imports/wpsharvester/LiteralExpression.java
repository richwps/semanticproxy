/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wpsharvester;
/**
 * Class to store literal properties of an rdf resource
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
