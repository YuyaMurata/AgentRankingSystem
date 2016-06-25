/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rda.db.DBAccess;
import rda.db.SQLReturnObject;
import rda.log.AgentLogPrint;
import rda.log.LogSchedule;

/**
 *
 * @author kaeru
 */
public class LoggerManager {
    private static final LoggerManager manager = new LoggerManager();
    private DBAccess db;
    private LogSchedule log;
    private LoggerManager(){}
    
    public static LoggerManager getInstance(){
        return manager;
    }
    
    public void initLoggerManager(Map loggerMap){
        this.db = new DBAccess();
        this.log = new LogSchedule(loggerMap);
    }
    
    public void startLogger(){
        log.start();
    }
    
    public void stopLogger(){
        log.stop();
    }
    
    private Map latencyMap = new ConcurrentHashMap();
    public void putMSGLatency(String agID, Long latency){
        latencyMap.put(agID, latency);
    }
    
    public void printTestcaseData(Long time){
        String n = TestCaseManager.getInstance().datagen.toString(time);
        System.out.println("> TestcaseGenData:"+n);
        AgentLogPrint.printTestcaseData(time.toString(), n);
    }
    
    public void printQueueObserever(){
        String observe = AgentMessageQueueManager.getInstance().observerToString();
        //System.out.println("> QueueObserver:\n"+observe);
        AgentLogPrint.printMessageQueueLog(AgentMessageQueueManager.getInstance().observerToMap());
    }
    
    public void printAgentDBTranData(){
        SQLReturnObject obj = db.query("select * from useragent");
        System.out.println("> DataTransaction:\n"+obj.toString());
        AgentLogPrint.printAgentTransaction(obj.toMapList());
    }
    
    public void printAgentDBLifeData(Long time){
        SQLReturnObject obj = db.query("select * from log");
        System.out.println("> AgentLifeTime:\n"+obj.toString(time));
        AgentLogPrint.printAgentTransaction(obj.toMapList(time));
    }
    
    public void printMessageLatency(){
        //System.out.println("> MessageLatency:\n"+latencyToString());
        AgentLogPrint.printMessageLatency(latencyToMap());
    }
    
    public void printAgentTranTotal(){
        SQLReturnObject obj = db.query("select * from useragent");
        Map map = new HashMap();
        List field = (List) obj.toMapList().get("Field");
        List data = (List) obj.toMapList().get("Data");
        map.put(field.get(field.size()-1), data.get(data.size()-1));
        AgentLogPrint.printResults("", map);
    }
    
    public Map latencyToMap(){
        StringBuilder place = new StringBuilder("MessageLatency");
        List field = new ArrayList(latencyMap.keySet());
        List data = new ArrayList(latencyMap.values());
        Map map = new HashMap();
        
        for(int i=0; i < field.size(); i++)
            place.append(",{}");
        
        map.put("Place", place.toString());
        map.put("Field", field);
        map.put("Data", data);
        
        return map;
    }
    
    public String latencyToString(){
        if(latencyMap.size() < 1) return "";
        
        StringBuilder sb = new StringBuilder();
        StringBuilder sblat = new StringBuilder();
        Long avg = 0L;
        for(Object agID : latencyMap.keySet()){
            sb.append(agID);
            sb.append(",");
            
            Long latency = (Long)latencyMap.get(agID);
            
            sblat.append(latency);
            sblat.append(",");
            
            avg = avg + latency;
        }
        avg = avg / latencyMap.size();
        
        sb.append("Avg.");
        sblat.append(avg);
        
        sb.append("\n");
        sb.append(sblat);
        
        return sb.toString();
    }
}
