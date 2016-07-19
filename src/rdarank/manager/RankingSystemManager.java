/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.manager;

import rdarank.agent.user.manager.UserAgentManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.client.AgentConnection;
import rda.log.AgentLogPrint;
import rda.manager.LoggerManager;
import rda.manager.TestCaseManager;
import rda.property.SetProperty;
import rda.stream.DataStream;
import rdarank.agent.rank.manager.RankAgentManager;

/**
 *
 * @author kaeru
 */
public class RankingSystemManager  implements SetProperty{
    private static RankingSystemManager manager = new RankingSystemManager();
    private RankingSystemManager(){}
    
    public static RankingSystemManager getInstance(){
        return manager;
    }
    
    public void launchSystem(){
        System.out.println(">>Launch System");
        
        dataSettings(preDataMap(), preProfMap());
        loggerSettings(preLoggerMap());
        agentSettings(preUserAgentMap(), preRankAgentMap(), POOLSIZE);
        streamSettings(preStreamMap());
    }
    
    public void shutdownSystem(){
        //UserAgent Shutdown
        UserAgentManager user = UserAgentManager.getInstance();
        user.doShutdown();
        
        //RankAgent Shutdown
        RankAgentManager rank = RankAgentManager.getInstance();
        rank.doShutdown();
        
        System.out.println(">> Shutdown System...");
    }
    
    private void agentSettings(Map userAgentParam, Map rankAgentParam, Integer poolsize){
        AgentConnection agconn = AgentConnection.getInstance();
        agconn.setPoolSize(poolsize);
        
        //RankAgent Initialize
        System.out.println("< Initialise RankAgents >");
        RankAgentManager rank = RankAgentManager.getInstance();
        rank.initRankAgent(rankAgentParam);
        rank.createNumberOfAgents((Integer)rankAgentParam.get("AMOUNT_OF_AGENTS"));
        
        if(rank.getReserveMode() == 1){
            List<String> reserveID = new ArrayList<>();
            for(int i=0; i < (Integer)rankAgentParam.get("AMOUNT_RESERVE_AGENT"); i++){
                String agentID =  rank.createAgent();
                reserveID.add(agentID);
            }
            for(String id : reserveID) rank.getIDManager().setReserveID(id);
        }
        
        System.out.println(">>> Finished Set RankAgents & IDs");
        
        //UserAgent Initialize
        //UserAgent Attributed Initialize
        TestCaseManager tcManager = TestCaseManager.getInstance();
        tcManager.profgen.addUserProfileToAgent();
        tcManager.profgen.addUserAgentCommunication();
        
        System.out.println("< Initialise UserAgents >");
        UserAgentManager user = UserAgentManager.getInstance();
        user.initUserAgent(userAgentParam);
        user.createNumberOfAgents((Integer)userAgentParam.get("AMOUNT_OF_AGENTS"));
        
        if(user.getReserveMode() == 1){
            List<String> reserveID = new ArrayList<>();
            for(int i=0; i < (Integer)userAgentParam.get("AMOUNT_RESERVE_AGENT"); i++){
                String agentID =  user.createAgent();
                reserveID.add(agentID);
            }
            for(String id : reserveID) user.getIDManager().setReserveID(id);
        }
        
        System.out.println(">>> Finished Set UserAgents & IDs");
    }
    
    private void dataSettings(Map dataParam, Map profParam){
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
        this.stream = new DataStream(UserAgentManager.getInstance(), streamMap);
    }
    
    public DataStream dataStream(){
        return stream;
    }
    
    //UserAgent Parameter
    private Map preUserAgentMap(){
        Map map = new HashMap();
        map.put("AMOUNT_OF_AGENTS", NUMBER_OF_USERS);
        map.put("QUEUE_LENGTH", QUEUE_LENGTH);
        map.put("QUEUE_WAIT", QUEUE_WAIT);
        map.put("AGENT_WAIT", AGENT_WAIT);
        map.put("AGENT_MODE", AGENT_MODE_AUTONOMY);
        map.put("RESERVE_MODE", AGENT_MODE_RESERVE);
        map.put("AMOUNT_RESERVE_AGENT", NUMBER_OF_RESERVE);
        map.put("POOLSIZE", POOLSIZE);
        map.put("RULE", NAME_RULE_USER);
        map.put("SEED", ID_SEED);
        map.put("ALIVE_TIME", TIME_WAIT);
        map.put("WINDOW_SIZE", WINDOW_SIZE);
        map.put("POOLSIZE", POOLSIZE);
        AgentLogPrint.printPropertySettings("Agent", map);
        
        return map;
    }
    
    //RanklAgent Parameter
    private Map preRankAgentMap(){
        Map map = new HashMap();
        map.put("AMOUNT_OF_AGENTS", NUMBER_OF_RANK_AGENTS);
        map.put("QUEUE_LENGTH", QUEUE_LENGTH);
        map.put("QUEUE_WAIT", QUEUE_WAIT);
        map.put("AGENT_WAIT", AGENT_WAIT);
        map.put("AGENT_MODE", AGENT_MODE_AUTONOMY);
        map.put("RESERVE_MODE", AGENT_MODE_RESERVE);
        map.put("AMOUNT_RESERVE_AGENT", NUMBER_OF_RESERVE);
        map.put("POOLSIZE", POOLSIZE);
        map.put("RULE", NAME_RULE_RANK);
        map.put("SEED", ID_SEED);
        map.put("ALIVE_TIME", TIME_WAIT);
        map.put("WINDOW_SIZE", WINDOW_SIZE);
        map.put("POOLSIZE", POOLSIZE);
        AgentLogPrint.printPropertySettings("Agent", map);
        
        return map;
    }
    
    private Map preProfMap(){
        Map map = new HashMap();
        map.put("AMOUNT_USERS", NUMBER_OF_USERS);
        map.put("MODE", DATA_MODE_PROFILE);
        map.put("SEED", PROF_SEED);
        map.put("RULE", NAME_RULE_USER);
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
