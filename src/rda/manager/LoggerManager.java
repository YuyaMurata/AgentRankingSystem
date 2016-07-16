/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rda.agent.template.AgentLogPrinterTemplate;
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
    
    public void setLogPrinter(AgentLogPrinterTemplate logPrinter){
        log.setLogPrinter(logPrinter);
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
    
    public void printAgentDBData(){
        SQLReturnObject obj = db.query("select * from aggregateagent");
        
        Map map = obj.toMap("Transaction", 0);
        System.out.println("> DataTransaction:\n"+obj.toString(map));
        AgentLogPrint.printAgentTransaction(map);
        
        map = obj.toMap("Latency", 1);
        System.out.println("> MessageLatency:\n"+obj.toString(map));
        AgentLogPrint.printMessageLatency(map);
        
        //Agent Inner QueueLength
        //map = obj.toMap("Length", 2);
        //System.out.println("> QueueLength:\n"+obj.toString(map));
        
        //MessageQueue Observer QueueLength
        AgentMessageQueueManager agent = AgentMessageQueueManager.getInstance();
        System.out.println("> QueueLength:\n"+agent.observerToString());
        AgentLogPrint.printMessageQueueLog(agent.observerToMap());
    }
    
    public void printAgentDBLifeData(Long time){
        SQLReturnObject obj = db.query("select * from log");
        
        Map map = obj.toMap("Time", time);
        System.out.println("> AgentLifeTime:\n"+obj.toString(map));
        AgentLogPrint.printAgentTransaction(map);
    }
    
    public void printAgentTranTotal(){
        SQLReturnObject obj = db.query("select * from aggregateagent");
        
        Map map = obj.toMap("Transaction", 0);
        
        List field = (List) map.get("Field");
        List data = (List) map.get("Data");
        
        Map resultMap = new HashMap();
        resultMap.put(field.get(field.size()-1), data.get(data.size()-1));
        AgentLogPrint.printResults("", resultMap);
    }
}
