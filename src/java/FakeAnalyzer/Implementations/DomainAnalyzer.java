/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FakeAnalyzer.Implementations;

import FakeAnalyzer.Interfaces.Analyzer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author Usuario
 */
public class DomainAnalyzer implements Analyzer{

    Hashtable<String,Boolean> whitelist;
    Hashtable<String,Boolean> blacklist;
    public DomainAnalyzer() throws FileNotFoundException, IOException{
        BufferedReader okFile = new BufferedReader(new FileReader("dominios_ok"));
        String line = okFile.readLine();

        while (line != null) {
            whitelist.put(line,new Boolean(true));
            line = okFile.readLine();
        }
        
        BufferedReader koFile = new BufferedReader(new FileReader("dominios_ko"));
        line = koFile.readLine();

        while (line != null) {
            blacklist.put(line,new Boolean(true));
            line = koFile.readLine();
        }
        
    }
    
    @Override
    public double calcular(String cadenaUrl) {
        double result = whitelist.size()+blacklist.size();
        return result;
    }
    
    
}
