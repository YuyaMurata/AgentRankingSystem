/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.stream;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rda.agent.queue.MessageObject;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.MessageQueueEvent;
import rda.manager.AgentMessageQueueManager;
import rda.manager.TestCaseManager;
import rda.window.Window;
import rda.window.WindowController;

/**
 *
 * @author kaeru
 */
public class DataStream implements Runnable{
    private static final String name = "DataStream";
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    private Long time, term, total=0L;
    private long delay, period;
    private Boolean runnable;
    private WindowController window;
    private static TestCaseManager tcmanager = TestCaseManager.getInstance();

    public DataStream(Map streamMap) {
        this.term = (Long)streamMap.get("TIME_RUN");
        this.period = (Long)streamMap.get("TIME_PERIOD");
        
        window = new WindowController((Integer)streamMap.get("WINDOW_SIZE"),
                                          (Long)streamMap.get("ALIVE_TIME"),
                                          (Integer)streamMap.get("POOLSIZE"));
    }
    
    public void start(){
        System.out.println("> "+name + " : Start !");
        runnable = true;
        time = 0L;
        schedule.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
    }
    
    private void stream(Long t){
        Map mqMap = AgentMessageQueueManager.getInstance().getMQMap();
        MessageObject msg;
        Window msgPack;

        while(((msg = tcmanager.datagen.generate(t)) != null) && runnable){
            try {
                window.pack(msg);
                
                if((msgPack = window.get()) == null) continue;
                
                //Get Destination ID
                String agID = msgPack.getDestID();
                
                //Get MessageQueue
                MessageQueue mq = (MessageQueue)mqMap.get(agID);
            
                //MessageSender      
                mq.put(msgPack);
                
                total = total+msgPack.unpack().size();
                
                window.remove();
            } catch (MessageQueueEvent mqev) {
                    mqev.printEvent();
            } catch (Exception e){
                    e.printStackTrace();
            }    
        }
    }
    
    @Override
    public void run() {
        if(term+1 > time){
            System.out.println("-Time Period = "+time);
            stream(time);
            tcmanager.debugTestGenerateCounts(total);
            time++;
        }
    }
    
    public void stop(){
        try {
            Thread.sleep(term*1000);
        } catch (InterruptedException ex) {
        }
        
        runnable = false;
        
        System.out.println("> " + name +" : Stop !");
        
        try {
            schedule.shutdown();
            if(!schedule.awaitTermination(0, TimeUnit.SECONDS))
                schedule.shutdownNow();
        } catch (InterruptedException ex) {
            schedule.shutdownNow();
        }
        window.close();
        
        tcmanager.debugTestGenerateCounts(total);
    }
}