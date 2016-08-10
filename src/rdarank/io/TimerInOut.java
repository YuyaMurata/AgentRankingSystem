/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author kaeru
 */
public class TimerInOut {
    private static File file = new File("temp.dat");
        
    public static void startTimeOut(Long start){
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(start);
            pw.close();
        } catch (IOException ex) {
        }
    }
    
    public static Long readStartTime(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            return Long.valueOf(br.readLine());
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        
        return null;
    }
}
