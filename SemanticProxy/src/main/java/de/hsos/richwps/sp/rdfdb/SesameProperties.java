/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.rdfdb;

import org.openrdf.repository.Repository;

/**
 * Class for storage of db access handle and related information, implement
 * singleton
 *
 * @author fbensman
 */
class SesameProperties {

    /**
     * Constant for default repository directory
     */
    public static final String DEFAULT_REPOSITORY_DIR = ".";
    private static SesameProperties instance = null;
    private Repository repository = null;

    private SesameProperties() {
    }

    /**
     * Singleton method for this class
     *
     * @return
     */
    public static SesameProperties getInstance() {
        if (instance == null) {
            instance = new SesameProperties();
        }
        return instance;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
