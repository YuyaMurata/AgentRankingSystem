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
import rda.agent.client.AgentConnection;
import rda.agent.queue.MessageQueue;
import rda.log.AgentLogPrint;
import rda.property.SetProperty;
import rda.stream.DataStream;

/**
 *
 * @author 悠也
 */
public class SystemManager implements SetProperty{
    private static SystemManager manager = new SystemManager();
    private SystemManager(){}
    
    public static SystemManager getInstance(){
        return manager;
    }
    
    public void launchSystem(){
        System.out.println(">>Launch System");
        
        agentSettings(NAME_RULE, NUMBER_OF_RANK_AGENTS, preAgentMap(), preIDMap(), POOLSIZE);
        dataSettings(NUMBER_OF_USERS, preDataMap(), preProfMap());
        loggerSettings(preLoggerMap());
        streamSettings(preStreamMap());
    }
    
    public void shutdownSystem(){
        AgentMessageQueueManager agManager = AgentMessageQueueManager.getInstance();
        agManager.doShutdown();
        
        System.out.println(">> Shutdown System...");
    }
    
    private void agentSettings(String rule, Integer numberOfAgents, Map agentParam, Map idParam, Integer poolsize){
        AgentConnection agconn = AgentConnection.getInstance();
        agconn.setPoolSize(poolsize);
        
        AgentMessageQueueManager agManager = AgentMessageQueueManager.getInstance();
        IDManager idManager = new IDManager(idParam);
        
        agManager.initAgentMessageQueueManager(agentParam);
        agManager.setIDManager(idManager);
        agManager.createNumberOfAgents(numberOfAgents);
        
        if(agManager.getReserveMode() == 1){
            List<String> reserveID = new ArrayList<>();
            for(int i=0; i < (Integer)agentParam.get("AMOUNT_RESERVE_AGENT"); i++){
                String agentID =  agManager.createAgent();
                reserveID.add(agentID);
            }
            for(String id : reserveID) agManager.reserveAgent(id);
        }
        
        System.out.println(">>> Finished Set Agents & IDs");
    }
    
    private void dataSettings(Integer numberOfUsers, Map dataParam, Map profParam){
        TestCaseManager tcManager = TestCaseManager.getInstance();
        tcManager.initTestCase(dataParam, profParam);
        
        System.out.println(">>> Finished Set TestCase");
    }
    
    private void loggerSettings(Map loggerMap){
        LoggerManager.getInstance().initLoggerManager(loggerMap);
    }
    
    public void startLogger(){
        LoggerManager.getInstance().startLogger();
    }
    
    public void stopLogger(){
        LoggerManager.getInstance().stopLogger();
    }
    
    private DataStream stream;
    private void streamSettings(Map streamMap){
        this.stream = new DataStream(streamMap);
    }
    
    public DataStream dataStream(){
        return stream;
    }
    
    private Map preAgentMap(){
        Map map = new HashMap();
        map.put("AMOUNT_OF_AGENTS", NUMBER_OF_RANK_AGENTS);
        map.put("QUEUE_LENGTH", QUEUE_LENGTH);
        map.put("QUEUE_WAIT", QUEUE_WAIT);
        map.put("AGENT_WAIT", AGENT_WAIT);
        map.put("AGENT_MODE", AGENT_MODE_AUTONOMY);
        map.put("RESERVE_MODE", AGENT_MODE_RESERVE);
        map.put("AMOUNT_RESERVE_AGENT", NUMBER_OF_RESERVE);
        map.put("POOLSIZE", POOLSIZE);
        AgentLogPrint.printPropertySettings("Agent", map);
        
        return map;
    }
    
    private Map preIDMap(){
        Map map = new HashMap();
        map.put("RULE", NAME_RULE);
        map.put("SEED", ID_SEED);
        AgentLogPrint.printPropertySettings("ID", map);
        
        return map;
    }
    
    private Map preProfMap(){
        Map map = new HashMap();
        map.put("AMOUNT_USER", NUMBER_OF_USERS);
        map.put("MODE", DATA_MODE_PROFILE);
        map.put("SEED", PROF_SEED);
        AgentLogPrint.printPropertySettings("UserProfile", map);
        
        return map;
    }
    
    private Map preDataMap(){
        Map map = new HashMap();
        map.put("TIME_RUN", TIME_RUN);
        map.put("TIME_PERIOD", TIME_PERIOD); 
        map.put("DATA_VOLUME", DATA_VOLUME); 
        map.put("AMOUNT_USER", NUMBER_OF_USERS); 
        map.put("AGENT_DEFAULT_VALUE", AGENT_DEFAULT_VALUE);
        map.put("SELECT_TYPE", DATA_SELECT_TYPE);
        map.put("MODE", DATA_MODE_GENERATE);
        map.put("SEED", DATA_SEED);
        AgentLogPrint.printPropertySettings("Data", map);
        
        return map;
    }
    
    private Map preStreamMap(){
        Map map = new HashMap();
        map.put("TIME_RUN", TIME_RUN);
        map.put("TIME_PERIOD", TIME_PERIOD);
        map.put("ALIVE_TIME", TIME_WAIT);
        map.put("WINDOW_SIZE", WINDOW_SIZE);
        map.put("POOLSIZE", POOLSIZE);
        AgentLogPrint.printPropertySettings("Stream", map);
        
        return map;
    }
    
    private Map preLoggerMap(){
        Map map = new HashMap();
        map.put("TIME_RUN", TIME_RUN);
        map.put("TIME_PERIOD", LOG_PERIOD);
        AgentLogPrint.printPropertySettings("Logger", map);
        
        return map;
    }
}