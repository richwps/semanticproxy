/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;

/**
 * Provides methods to read text files
 * @author fbensman
 */
public class TextFileReader {
    
    
    /**
     * Reads a text file
     * @param file
     * @return
     * @throws Exception 
     */
     public static String readPlainText(File file) throws Exception{
        try{
            FileInputStream fis = new FileInputStream(file);
            byte[] raw = new byte[(int)file.length()];
            fis.read(raw);
            fis.close();
            String str = new String(raw, Charset.forName("UTF-8"));
            return str;
        }catch(IllegalCharsetNameException e){
            throw new Exception("Cannot load file " + file.getName()+", "+e.toString()+" "+e.getMessage());
        }
        catch(IllegalArgumentException e){
            throw new Exception("Cannot load file " + file.getName()+", "+e.toString()+" "+e.getMessage());
        }
        catch(FileNotFoundException e){
            throw new Exception("Cannot load file " + file.getName()+", "+e.toString()+" "+e.getMessage());
        }
        catch(IOException e){
            throw new Exception("Cannot load file " + file.getName()+", "+e.toString()+" "+e.getMessage());
        }
    }
}
