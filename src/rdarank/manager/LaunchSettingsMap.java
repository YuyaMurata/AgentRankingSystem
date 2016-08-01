/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.manager;

import java.util.HashMap;
import java.util.Map;
import rda.log.AgentLogPrint;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class LaunchSettingsMap implements SetProperty{
    private Map prop;
    public LaunchSettingsMap() {
        prop = new HashMap();
    }
    
    public Map getPropMap(){
        //UserAgentManager
        prop.put("user", preUserAgentMap());
        
        //RankAgentManager
        prop.put("rank", preRankAgentMap());
        
        //Profile
        prop.put("profile", preProfMap());
        
        //DataGenerate
        prop.put("datagen", preDataMap());
        
        //Stream
        prop.put("stream", preStreamMap());
        
        //Logger
        prop.put("logger", preLoggerMap());
        
        //Manager Extension
        prop.put("manager", preManagerMap());
        
        //Server
        prop.put("server", preServerMap());
        
        return prop;
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
        AgentLogPrint.printPropertySettings("UserAgent", map);
        
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
        AgentLogPrint.printPropertySettings("RankAgent", map);
        
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
    
    private Map preManagerMap(){
        Map map = new HashMap();
        map.put("DEPLOY_PATTERN", 0); // 0 is local
        AgentLogPrint.printPropertySettings("Manager", map);
        
        return map;
    }
    
    private Map preServerMap(){
        Map map = new HashMap();
        map.put("SERVER_LIST", SERVER_LIST);
        map.put("SERVER_THREAD", SERVER_THREAD);
        map.put("POOLSIZE", SERVER_POOLSIZE);
        AgentLogPrint.printPropertySettings("Server", map);
        
        return map;
    }
}
