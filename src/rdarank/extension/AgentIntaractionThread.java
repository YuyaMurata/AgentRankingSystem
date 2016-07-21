/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.extension;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kaeru
 */
public class AgentIntaractionThread implements Runnable{
    private static final String name = "AgentIntaraction Thread";
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    
    private static final AgentIntaractionExtension extension = AgentIntaractionExtension.getInstance();
    
    public void start(){
        System.out.println("> "+name + " : Start !");
        schedule.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
    }
    
    public void stop(){
        try {
            schedule.shutdown();
            if(!schedule.awaitTermination(0, TimeUnit.SECONDS))
                schedule.shutdownNow();
        } catch (InterruptedException ex) {
            schedule.shutdownNow();
        }
        
        System.out.println("> " + name +" : Stop !");
    }
    
    @Override
    public void run() {
       //if(extension.getStatus() != null){
       //    System.out.println("> IDList : "+extension.getStatus().get("id"));
       //    System.out.println("> DataList : "+extension.getStatus().get("id"));
       //}
    }
    
}
