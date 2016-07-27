/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.client.AgentConnection;
import rda.log.AgentLogPrint;
import rda.manager.LoggerManager;
import rda.manager.TestCaseManager;
import rda.stream.DataStream;
import rda.stream.TimeToDataStream;

import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;

/**
 *
 * @author kaeru
 */
public class RankingSystemManager {
    private static RankingSystemManager manager = new RankingSystemManager();
    private RankingSystemManager(){}
    
    public static RankingSystemManager getInstance(){
        return manager;
    }
    
    public void launchSystem(){
        System.out.println(">>Launch System");
        
        dataSettings(preDataMap(), preProfMap());
        loggerSettings(preLoggerMap());
        agentSettings(preUserAgentMap(), preRankAgentMap(), 8);
    }
    
    public void setDataStreamGenerator(){
        streamSettings();
        //Thread Type
        //streamSettings(preStreamMap());
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
        //AgentClient ObjectPool Initialize
        AgentConnection agconn = AgentConnection.getInstance();
        agconn.setPoolSize(poolsize);
        
        //RankAgent Initialize
        System.out.println("< Initialise RankAgents >");
        RankAgentManager rank = RankAgentManager.getInstance();
        rank.initRankAgent(rankAgentParam);
        rank.createNumberOfAgents((Integer)rankAgentParam.get("AMOUNT_OF_AGENTS"));
        
        if(rank.getReserveMode()){
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
        
        if(user.getReserveMode()){
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
    
    //DataStream Generator
    private TimeToDataStream timeStream;
    private void streamSettings(){
        this.timeStream = new TimeToDataStream(UserAgentManager.getInstance());
    }
    
    public TimeToDataStream timeDataStream(){
        return timeStream;
    }
    
    //Thread Type DataStream Generator
    private DataStream stream;
    private void streamSettings(Map streamMap){
        this.stream = new DataStream(UserAgentManager.getInstance(), streamMap);
    }
    
    public DataStream dataStream(){
        return stream;
    }
    
    //UserAgent Parameter
    private Map preUserAgentMap(){
        AgentLogPrint.printPropertySettings("UserAgent", props.get("user"));
        return props.get("user");
    }
    
    //RankAgent Parameter
    private Map preRankAgentMap(){
        AgentLogPrint.printPropertySettings("RankAgent", props.get("rank"));
        return props.get("rank");
    }
    
    private Map preProfMap(){
        AgentLogPrint.printPropertySettings("UserProfile", props.get("profile"));
        return props.get("profile");
    }
    
    private Map preDataMap(){
        AgentLogPrint.printPropertySettings("Data", props.get("datagen"));
        return props.get("datagen");
    }
    
    private Map preStreamMap(){
        AgentLogPrint.printPropertySettings("Stream", props.get("stream"));
        return props.get("stream");
    }
    
    private Map preLoggerMap(){
        AgentLogPrint.printPropertySettings("Logger", props.get("logger"));
        return props.get("logger");
    }
    
    private HashMap<String, Map> props;
    public void setPropMap(Map map){
        this.props = (HashMap<String, Map>)map;
    }
}
