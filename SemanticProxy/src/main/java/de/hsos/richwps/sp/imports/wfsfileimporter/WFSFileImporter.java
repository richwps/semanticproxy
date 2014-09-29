/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp.imports.wfsfileimporter;

import de.hsos.richwps.sp.App;
import de.hsos.richwps.sp.InputFile;
import de.hsos.richwps.sp.imports.IWFSImportSource;
import de.hsos.richwps.sp.imports.ImportException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author fbensman
 */
public class WFSFileImporter implements IWFSImportSource{

   
    
    
   private ArrayList<InputFile> wfsFiles = null;
    private int wfsIdx = 0;
    private String newHost = null;
    
    public WFSFileImporter(InputFile[] files, String newHost){
        this.wfsFiles = new ArrayList<>();
        for(InputFile f : files){
            if(f.isWFS())
                wfsFiles.add(f);
        }
        this.newHost = newHost;
    }
    
    
    @Override
    public String getNextWFS() throws ImportException {
        if(wfsIdx < wfsFiles.size()){
            InputFile f = wfsFiles.get(wfsIdx);
            String content = null;
            try{
                content = TextFileReader.readPlainText(f.getFile());
            }catch(Exception ex){
                throw new ImportException("Cannot read file: "+f.getFile().getAbsolutePath(),ex);
            }
            if(f.getReplacableHost() != null && newHost != null){
                content = content.replace(f.getReplacableHost(), newHost);
            }
            wfsIdx++;
            Logger.getLogger(App.class).info("Read file "+f.getFile().getAbsolutePath()+" successfully");
            return content;
        }
        return null;
    }
    

  

    @Override
    public void reset() {
        wfsIdx = 0;
    }

    @Override
    public String getInfo() {
        return "WFS file importer";
    }
    
    
}
