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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

/**
 *
 * @author Usuario
 */
public class DomainAnalyzer implements Analyzer{

    Hashtable<String,Boolean> whitelist;
    Hashtable<String,Boolean> blacklist;
    public DomainAnalyzer() throws FileNotFoundException, IOException{
        whitelist = new Hashtable<String,Boolean>();
        blacklist = new Hashtable<String,Boolean>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("FakeAnalyzer/Implementations/dominios_ok");
        BufferedReader okFile = new BufferedReader(new InputStreamReader(input));
        String line = okFile.readLine();

        while (line != null) {
            whitelist.put(line,new Boolean(true));
            line = okFile.readLine();
        }
        
        InputStream input2 = classLoader.getResourceAsStream("FakeAnalyzer/Implementations/dominios_ko");
        BufferedReader koFile = new BufferedReader(new InputStreamReader(input2));
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
