/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ezcast.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 *
 * @author AyA
 */
public class LoggerHelper{

    private Set<String> logs;

    public LoggerHelper() {        
        logs = new HashSet<>();    
    } 
    
    public void addLog(Level lvl, String cause){
        logs.add(lvl.getName()+": "+cause);
    }
    public void reset(){
        logs.clear();
    }    
    public String getLogs(){
        StringBuilder sb=new StringBuilder();
        logs.stream().forEach(log -> sb.append(log+"\n"));
        return sb.toString();
    }

}
