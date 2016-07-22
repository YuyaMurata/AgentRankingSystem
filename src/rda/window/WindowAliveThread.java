/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.window;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author kaeru
 */
public class WindowAliveThread implements Runnable{
    private static final String name = "Window Alive Thread";
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    
    private WindowController ctrl;
    private Long aliveTime;
    public WindowAliveThread(WindowController ctrl, Long aliveTime) {
        this.ctrl = ctrl;
        this.aliveTime = aliveTime;
    }
    
    public void start(){
        System.out.println("> "+name + " : Start !");
        schedule.scheduleAtFixedRate(this, 0, aliveTime, TimeUnit.MILLISECONDS);
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
        try{
            Collection collect = ctrl.getWindows();
            if(collect == null) return;
        
            for(Window win : (Collection<Window>)collect){
                ctrl.addExecutable(win);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
