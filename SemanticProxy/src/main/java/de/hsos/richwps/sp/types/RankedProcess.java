/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.types;

import java.net.URL;

/**
 *
 * @author fbensman
 */
public class RankedProcess implements Comparable<RankedProcess>{
        
        public URL process = null;
        public int rank = 0;
        
        public RankedProcess(URL aProcess, int aRank){
            process = aProcess;
            rank = aRank;
        }

    @Override
    public int compareTo(RankedProcess o) {
        if(rank < o.rank)
            return -1;
        else if(rank == o.rank)
            return 0;
        else
            return 1;
    }
    
}