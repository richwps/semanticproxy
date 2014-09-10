/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.fileimporter;

import de.hsos.richwps.sp.InputFile;
import de.hsos.richwps.sp.imports.IImportSource;
import de.hsos.richwps.sp.imports.ImportException;
import java.util.ArrayList;

/**
 *
 * @author fbensman
 */
public class FileImporter implements IImportSource{

    private ArrayList<InputFile> wpsFiles = null;
    private ArrayList<InputFile> processFiles = null;
    private int wpsIdx = 0;
    private int processIdx = 0;
    private String newHost = null;
    
    public FileImporter(InputFile[] files, String newHost){
        this.wpsFiles = new ArrayList<>();
        this.processFiles = new ArrayList<>();
        for(InputFile f : files){
            if(f.isWPS())
                wpsFiles.add(f);
            else 
                processFiles.add(f);
        }
        this.newHost = newHost;
    }
    
    
    @Override
    public String getNextWPS() throws ImportException {
        if(wpsIdx < wpsFiles.size()){
            InputFile f = wpsFiles.get(wpsIdx);
            String content = null;
            try{
                content = TextFileReader.readPlainText(f.getFile());
            }catch(Exception ex){
                throw new ImportException("Cannot read file: "+f.getFile().getAbsolutePath(),ex);
            }
            if(f.getReplacableHost() != null && newHost != null){
                content = content.replace(f.getReplacableHost(), newHost);
            }
            wpsIdx++;
            return content;
        }
        return null;
    }
    

    @Override
    public String getNextProcess() throws ImportException {
        if(processIdx < processFiles.size()){
            InputFile f = processFiles.get(processIdx);
            String content = null;
            try{
                content = TextFileReader.readPlainText(f.getFile());
            }catch(Exception ex){
                throw new ImportException("Cannot read file: "+f.getFile().getAbsolutePath(),ex);
            }
            if(f.getReplacableHost() != null && newHost != null){
                content = content.replace(f.getReplacableHost(), newHost);
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
