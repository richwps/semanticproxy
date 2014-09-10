/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.fileimporter;

import de.hsos.richwps.sp.imports.IImportSource;
import de.hsos.richwps.sp.imports.ImportException;
import java.io.File;

/**
 *
 * @author fbensman
 */
public class FileImporter implements IImportSource{

    private File[] wpsFiles = null;
    private File[] processFiles = null;
    private int wpsIdx = 0;
    private int processIdx = 0;
    private String oldHost = null;
    private String newHost = null;
    
    public FileImporter(File[] wpsFiles, File[] processFiles, String oldHost, String newHost){
        this.wpsFiles = wpsFiles;
        this.processFiles = processFiles;
        this.oldHost = oldHost;
        this.newHost = newHost;
    }
    
    
    @Override
    public String getNextWPS() throws ImportException {
        if(wpsIdx < wpsFiles.length){
            String content = null;
            try{
                content = TextFileReader.readPlainText(wpsFiles[wpsIdx]);
            }catch(Exception ex){
                throw new ImportException("Cannot read file: "+wpsFiles[wpsIdx].getAbsolutePath(),ex);
            }
            if(oldHost != null && newHost != null){
                content = content.replace(oldHost, newHost);
            }
            wpsIdx++;
            return content;
        }
        return null;
    }
    

    @Override
    public String getNextProcess() throws ImportException {
        if(processIdx < processFiles.length){
            String content = null;
            try{
                content = TextFileReader.readPlainText(processFiles[processIdx]);
            }catch(Exception ex){
                throw new ImportException("Cannot read file: "+processFiles[processIdx].getAbsolutePath(),ex);
            }
            if(oldHost != null && newHost != null){
                content = content.replace(oldHost, newHost);
            }
            processIdx++;
            return content;
        }
        return null;
    }

    @Override
    public void reset() {
        wpsIdx = 0;
        processIdx = 0;
    }

    @Override
    public String getInfo() {
        return "File importer";
    }
    
}
