/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.intaraction;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rda.agent.queue.MessageObject;

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
        Object window = extension.getWindowController().get();
        if(window == null) return ;
        
        System.out.println("Transport Window !");
        extension.transport(window);
        
        extension.getWindowController().remove();
    }
}
