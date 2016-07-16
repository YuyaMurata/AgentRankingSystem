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

import rda.agent.queue.MessageQueue;
import rda.agent.queue.QueueObserver;
import rda.clone.AgentCloning;
import rda.manager.IDManager;
import rda.manager.LoggerManager;
import rda.window.WindowController;
import rdarank.agent.user.creator.CreateUserAgent;
import rdarank.agent.user.log.UserAgentLogPrinter;

/**
 *
 * @author kaeru
 */
public class UserAgentManager {
    private static UserAgentManager manager = new UserAgentManager();
    private static Boolean runnable = true;
    
    private IDManager userID;
    
    private Integer queueLength;
    private Long queuewait, agentwait;
    private Integer agentMode, reserveMode;
    
    private UserAgentManager() {
    }
    
    public static UserAgentManager getInstance(){
        return manager;
    }
    
    public void initUserAgent(Map userAgentMapParam){
        this.queueLength = (Integer)userAgentMapParam.get("QUEUE_LENGTH");
        this.queuewait = (Long)userAgentMapParam.get("QUEUE_WAIT");
        this.agentwait = (Long)userAgentMapParam.get("AGENT_WAIT");
        this.agentMode = (Integer)userAgentMapParam.get("AGENT_MODE");
        this.reserveMode = (Integer)userAgentMapParam.get("RESERVE_MODE");
        
        //Init IDManager
        this.userID = new IDManager(userAgentMapParam);
        
        //Init LogPrinter
        UserAgentLogPrinter log = new UserAgentLogPrinter("useragent");
        LoggerManager.getInstance().setLogPrinter(log);
        
        AgentCloning.setAutoMode(0);
    }
    
    public IDManager getIDManager(){
        return userID;
    }
    
    //Agentの複数生成 e.g.("R#", 10)
    public void createNumberOfAgents(Integer numOfAgents){
        for(int i=0; i < numOfAgents; i++){
            String agentID = createAgent();
            userID.initRegID(agentID);
        }
    }
    
    //Agentの単生成 e.g.("R#01")
    public String createAgent(){
        String agID;
        Object agent = null;
        
        if((agID = userID.getReserveID()) == null){
            agID = userID.genID();
            CreateUserAgent userAgent = new CreateUserAgent();
            userAgent.create(agID, queueLength, queuewait, agentwait);
        } else {
            System.out.println(">> Get Reserve Agent = "+agID);
        }
        
        return agID;
    }
    
    //Agentの複製 e.g.("R#01_Clone")
    public String createCloneAgent(String originalID, Object originalState){
        String agID = createAgent();
        ((MessageQueue)getAgent(agID)).setOriginalQueue(originalState);
        
        return agID;
    }
    
    //MessageQueueの実行管理
    public Boolean getState(){
        return runnable;
    }
    
    //全てのMessageQueueを終了する
    public void doShutdown(){
        runnable = false;
    }
    
    //Logger用にMQの監視オブジェクトを登録
    private static List<QueueObserver> observeList = new ArrayList<>();
    public void add(Object observe){
       observeList.add((QueueObserver) observe);
    }
    
    public List<QueueObserver> getObserver(){
        return observeList;
    }
    
    //ManagerにMessageQueueを登録
    private static Map messageQueueMap = new HashMap();
    public void register(MessageQueue mq){
        messageQueueMap.put(mq.getID(), mq);
        mq.start();
    }
    
    public Object getAgent(String agID){
        return messageQueueMap.get(agID);
    }
    
    public Map getMQMap(){
        return messageQueueMap;
    }
    
    public Integer getNumAgents(){
        return messageQueueMap.size() - userID.getNumReserves();
    }
    
    public Integer getAutoMode(){
        return agentMode;
    }
    
    public Integer getReserveMode(){
        return reserveMode;
    }
}
