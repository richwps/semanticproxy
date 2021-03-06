/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.types;

import de.hsos.richwps.sp.App;
import de.hsos.richwps.sp.types.EIDType;
import de.hsos.richwps.sp.types.EIDType;
import de.hsos.richwps.sp.types.SubjectList;
import de.hsos.richwps.sp.types.SubjectList;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 * Generates IDs for requested resources. The IDs can be used to issue PUT or
 * POST request on resources. IDs consist of the preset naming URL and
 * long-number.
 *
 * @author fbensman
 */
public class IDGenerator {

    private static IDGenerator instance = null;
    private static boolean configured = false;
    private static URL wpsNamingURL = null;
    private static URL processNamingURL = null;
    private static URL inputNamingURL = null;
    private static URL outputNamingURL = null;
    private static URL literalNamingURL = null;
    private static URL complexNamingURL = null;
    private static URL boundingboxNamingURL = null;
    private static URL complexCombinationNamingURL = null;
    private static URL wfsNamingURL = null;
    private static URL featureTypeNamingURL = null;
    private static URL qosNamingURL = null;
    private long wpsCnt = 0;
    private long processCnt = 0;
    private long inputCnt = 0;
    private long outputCnt = 0;
    private long literalCnt = 0;
    private long complexCnt = 0;
    private long boundingBoxCnt = 0;
    private long complexCombinationCnt = 0;
    private long wfsCnt = 0;
    private long featureTypeCnt = 0;
    private long qosCnt = 0;

    /**
     * Initializes the counters.
     */
    private IDGenerator() {
        long millies = System.currentTimeMillis();
        wpsCnt = millies;
        processCnt = millies;
        inputCnt = millies;
        outputCnt = millies;
        literalCnt = millies;
        complexCnt = millies;
        boundingBoxCnt = millies;
        complexCombinationCnt = millies;
        wfsCnt = millies;
        featureTypeCnt = millies;
        qosCnt = millies;
    }

    /**
     * Sets all the naming strings for id generation, can only be used once.
     *
     * @param aWpsNamingURL
     * @param aProcessNamingURL
     * @param aInputNamingURL
     * @param aOutputNamingURL
     * @param aLiteralNamingURL
     * @param aComplexNamingURL
     * @param aBoundingboxNaming
     */
    public static void configure(URL aWpsNamingURL, URL aProcessNamingURL,
            URL aInputNamingURL, URL aOutputNamingURL,
            URL aLiteralNamingURL, URL aComplexNamingURL,
            URL aBoundingboxNamingURL, URL aComplexDataCombinationURL,
            URL aWFSNamingURL, URL aFeatureTypeNamingURL,
            URL aQoSNamingURL) {
        if (!configured) {
            wpsNamingURL = aWpsNamingURL;
            processNamingURL = aProcessNamingURL;
            inputNamingURL = aInputNamingURL;
            outputNamingURL = aOutputNamingURL;
            literalNamingURL = aLiteralNamingURL;
            complexNamingURL = aComplexNamingURL;
            boundingboxNamingURL = aBoundingboxNamingURL;
            complexCombinationNamingURL = aComplexDataCombinationURL; 
            wfsNamingURL = aWFSNamingURL;
            featureTypeNamingURL = aFeatureTypeNamingURL;
            qosNamingURL = aQoSNamingURL;

            configured = true;
        }
    }

    /**
     * Gets the singleton instance.
     *
     * @return
     */
    public static IDGenerator getInstance() {
        if (!configured) {
            return null;
        }
        if (instance == null) {
            instance = new IDGenerator();
        }
        return instance;
    }

    /**
     * Generates an ID for the given type.
     *
     * @param t given type
     * @return List of one subject.
     * @throws MalformedURLException
     */
    public synchronized URL generateID(EIDType t) {
        URL retVal = null;

        try {
            if (t == EIDType.WPS) {
                retVal = new URL(wpsNamingURL + "/" + wpsCnt);
                wpsCnt++;
            } else if (t == EIDType.PROCESS) {
                retVal = new URL(processNamingURL + "/" + processCnt);
                processCnt++;
            } else if (t == EIDType.INPUT) {
                retVal = new URL(inputNamingURL + "/" + inputCnt);
                inputCnt++;
            } else if (t == EIDType.OUTPUT) {
                retVal = new URL(outputNamingURL + "/" + outputCnt);
                outputCnt++;
            } else if (t == EIDType.LITERAL) {
                retVal = new URL(literalNamingURL + "/" + literalCnt);
                literalCnt++;
            } else if (t == EIDType.COMPLEX) {
                retVal = new URL(complexNamingURL + "/" + complexCnt);
                complexCnt++;
            } else if (t == EIDType.BOUNDINGBOX) {
                retVal = new URL(boundingboxNamingURL + "/" + boundingBoxCnt);
                boundingBoxCnt++;
            } else if (t == EIDType.COMPLEXCOMBINATION) {
                retVal = new URL(complexCombinationNamingURL + "/" + complexCombinationCnt);
                complexCombinationCnt++;
            } else if (t == EIDType.WFS) {
                retVal = new URL(wfsNamingURL + "/" + wfsCnt);
                wfsCnt++;
            } else if (t == EIDType.FEATURETYPE) {
                retVal = new URL(featureTypeNamingURL + "/" + featureTypeCnt);
                featureTypeCnt++;
            } else if (t == EIDType.QOSTARGET) {
                retVal = new URL(qosNamingURL + "/" + qosCnt);
                qosCnt++;
            }
        } catch (MalformedURLException murle) {
            Logger.getLogger(IDGenerator.class).error("IDGenerator generated invalid URL", murle);
            return null;
        }
        return retVal;


    }
}
