/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.restlogic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

/**
 * Contains methods to read vocabulary files
 * @author fbensman
 */
public class VocabReader {
    
    
    /**
     * Reads the content of the vocabulary file as raw text
     * @return Content of file
     * @throws Exception 
     */
    public static String readPlainText() throws Exception{
        try{
            File vocab = new File("."+File.separator+"vocab.rdf");
            System.out.println(vocab.getAbsolutePath());
            FileInputStream fis = new FileInputStream(vocab);
            byte[] raw = new byte[(int)vocab.length()];
            fis.read(raw);
            fis.close();
            String str = new String(raw, Charset.forName("UTF-8"));
            return str;
        }catch(IllegalCharsetNameException e){
            throw new Exception("Cannot load vacabulary, "+e.toString()+" "+e.getMessage());
        }
        catch(IllegalArgumentException e){
            throw new Exception("Cannot load vacabulary, "+e.toString()+" "+e.getMessage());
        }
        catch(FileNotFoundException e){
            throw new Exception("Cannot load vacabulary, "+e.toString()+" "+e.getMessage());
        }
        catch(IOException e){
            throw new Exception("Cannot load vacabulary, "+e.toString()+" "+e.getMessage());
        }
        
    }
}