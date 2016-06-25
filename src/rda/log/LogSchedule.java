/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rda.manager.LoggerManager;
import rda.manager.TestCaseManager;

/**
 *
 * @author kaeru
 */
public class LogSchedule implements Runnable{
    private static final String name = "LogSchedule";
    private final ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    private Long time, term, start, stop;
    private long delay, period;

    
    public LogSchedule(Map loggerMap) {
        this.period = (Long)loggerMap.get("TIME_PERIOD");
        this.term = (Long)loggerMap.get("TIME_RUN") * (1000 / period);
    }
    
    public void start(){
        System.out.println("> "+name + " : Start !");
        time = 0L;
        
        schedule.scheduleAtFixedRate(this, delay, period, TimeUnit.MILLISECONDS);
        start = System.currentTimeMillis();
        loggerTime("StratTime", String.valueOf(start));
    }
    
    private void logging(Long time){
        long t = time % (1000/period);
        try{
            if(t == 0) LoggerManager.getInstance().printTestcaseData(time);
            LoggerManager.getInstance().printQueueObserever();
            if(t == 0) LoggerManager.getInstance().printAgentDBTranData();
            if(t == 0) LoggerManager.getInstance().printMessageLatency();
            //System.out.println(">> TestGenerate Counts = "+TestCaseManager.getInstance().checkTestGenerateCounts());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        if(term > time){
            logging(time);
            time++;
        }
    }
    
    public void stop(){
        
        System.out.println("> " + name +" : Stop !");
        
        try {
            schedule.shutdown();
            if(!schedule.awaitTermination(0, TimeUnit.SECONDS))
                schedule.shutdownNow();
        } catch (InterruptedException ex) {
            schedule.shutdownNow();
        }
        
        stop = System.currentTimeMillis();
        
        logging(time);
        LoggerManager.getInstance().printTestcaseData(-1L);
        
        loggerTime("StopTime", String.valueOf(stop));
        
        LoggerManager.getInstance().printAgentDBLifeData(start);
        LoggerManager.getInstance().printAgentTranTotal();
        loggerTime("TransactionTime", String.valueOf(stop-start));
    }
    
    private void loggerTime(String key, String value){
        Map map = new HashMap();
        map.put(key, value);
        AgentLogPrint.printResults("", map);
    }
}
