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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //String domain = cadenaUrl.replaceAll(".*\\.(?=.*\\.)", "");
        
        Pattern pattern = Pattern.compile("(https?://)([^:^/]*)(:\\d*)?(.*)?");
        Matcher matcher = pattern.matcher(cadenaUrl);
        matcher.find();

        String protocol = matcher.group(1);
        String domain = matcher.group(2);
        String[] results = domain.split("\\.");
        if (results.length>2){
            domain = results[results.length-2]+"."+results[results.length-1];
        }
            
           
        
        double result = 0.;
        if (whitelist.get(domain)!=null){
             result = 100.;
        }
        else if (blacklist.get(domain)!=null){
             result = 0.;
        }
        else result = 50.;
        return result;
    }
    
    
}
