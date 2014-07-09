/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.types;

import java.net.URL;

/**
 * Stores a pracess together with a rank.
 *
 * @author fbensman
 */
public class RankedProcess implements Comparable<RankedProcess> {

    public URL process = null;
    public int rank = 0;

    /**
     * Ctor
     *
     * @param aProcess
     * @param aRank
     */
    public RankedProcess(URL aProcess, int aRank) {
        process = aProcess;
        rank = aRank;
    }

    /**
     * Compares this object with another RankedProcess object by their ranks.
     *
     * @param o
     * @return -1 this < o; 0 this = 0; 1 this > o
     */
    @Override
    public int compareTo(RankedProcess o) {
        if (rank < o.rank) {
            return -1;
        } else if (rank == o.rank) {
            return 0;
        } else {
            return 1;
        }
    }
}