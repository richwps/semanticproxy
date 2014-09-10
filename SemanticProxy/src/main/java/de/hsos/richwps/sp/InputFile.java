/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hsos.richwps.sp;

import java.io.File;

/**
 *
 * @author fbensman
 */
public class InputFile {
    
    enum Typ{
        WPS,
        Process,
        Unspec
    }
    
    
    private File file = null;
    private Typ typ = Typ.Unspec;
    private String replacableHost = null;
    
    
    public InputFile(File file, Typ typ, String replacableHost){
        this.file=file;
        this.typ = typ;
        this.replacableHost = replacableHost;
    }
    
    public InputFile(){
        
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Typ getTyp() {
        return typ;
    }

    public void setTyp(Typ typ) {
        this.typ = typ;
    }

    public String getReplacableHost() {
        return replacableHost;
    }

    public void setReplacableHost(String replacableHost) {
        this.replacableHost = replacableHost;
    }
    
    public boolean isWPS(){
        return typ.equals(Typ.WPS);
    }
    
    public boolean isProcess(){
        return typ.equals(Typ.Process);
    }
    
    @Override
    public String toString(){
        String path = null;
        if(file != null)
            path = "Path=\""+file.getAbsolutePath()+"\"";
        else
            path = "Path=not set";
        String t = "Type="+typ.name();
        String repHost = null;
        if(replacableHost != null)
            repHost="ReplacableHost=\""+replacableHost+"\"";
        else
            repHost="ReplacableHost=not set";
        return path+" "+t+" "+repHost;
    }
    
}
