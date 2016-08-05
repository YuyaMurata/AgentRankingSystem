/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.user.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.client.DistributedAgentConnection;

import rda.agent.queue.MessageQueue;
import rda.agent.queue.QueueObserver;
import rda.manager.AgentManager;
import rda.manager.IDManager;
import rda.manager.LoggerManager;
import rda.window.WindowController;

import rdarank.agent.user.creator.CreateUserAgent;
import rdarank.agent.user.creator.CreateUserAgentEx;
import rdarank.agent.user.logger.UserAgentLogPrinter;
import rdarank.server.DistributedServerConnection;

/**
 *
 * @author kaeru
 */
public class UserAgentManager extends AgentManager{
    private static UserAgentManager manager = new UserAgentManager();
    private static Boolean runnable = true;
    
    private IDManager userID;
    private WindowController windowCTRL;
    
    private Integer queueLength;
    private Long queuewait, agentwait;
    private Integer agentMode, reserveMode;
    
    private List<QueueObserver> observes;
    
    private UserAgentManager() {
    }
    
    public static UserAgentManager getInstance(){
        return manager;
    }
    
    @Override
    public void initAgent(Map param){
        this.queueLength = (Integer)param.get("QUEUE_LENGTH");
        this.queuewait = (Long)param.get("QUEUE_WAIT");
        this.agentwait = (Long)param.get("AGENT_WAIT");
        this.reserveMode = (Integer)param.get("RESERVE_MODE");
        
        this.observes = new ArrayList();
        
        //Init IDManager
        this.userID = new IDManager(param);
        
        //Init AgentCloning Mode
        //this.agentMode = (Integer)userAgentMapParam.get("AGENT_MODE");
        this.agentMode = 0;
        
        //Init WindowController
        this.windowCTRL = new WindowController((Integer)param.get("WINDOW_SIZE"),
                                          (Long)param.get("ALIVE_TIME"),
                                          (Integer)param.get("POOLSIZE"));
    }
    
    @Override
    public void setLogger(){
        //Init LogPrinter
        UserAgentLogPrinter log = new UserAgentLogPrinter("useragent");
        LoggerManager.getInstance().setLogPrinter(log);
    }
    
    @Override
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
            CreateUserAgentEx userAgent = new CreateUserAgentEx();
            userAgent.create(agID, queueLength, queuewait, agentwait);
        } else {
            System.out.println(">> Get Reserve Agent = "+agID);
        }
        
        return agID;
    }
    
    //Agentの複数生成 e.g.("R#", 10)
    public void initNumberOfAgents(Integer numOfAgents){
        for(int i=0; i < numOfAgents; i++){
            String agentID = userID.genID(); //SerialID gen
            userID.initRegID(agentID);
        }
    }
    
    //Agentの単生成 e.g.("R#01")
    public void createAgent(String agID){
        Object agent = null;
        
        CreateUserAgentEx userAgent = new CreateUserAgentEx();
        userAgent.create(agID, queueLength, queuewait, agentwait);
    }
    
    //Agentの複製 e.g.("R#01_Clone")
    @Override
    public String createCloneAgent(String originalID, Object originalState){
        String agID = createAgent();
        ((MessageQueue)getAgent(agID)).setOriginalQueue(originalState);
        
        return agID;
    }
    
    //MessageQueueの実行管理
    @Override
    public Boolean getState(){
        return runnable;
    }
    
    //全てのMessageQueueを終了する
    @Override
    public void doShutdown(){
        runnable = false;
        windowCTRL.close();
    }
    
    //Logger用にMQの監視オブジェクトを登録
    @Override
    public void add(QueueObserver observe) {
        observes.add(observe);
    }
    
    @Override
    public List<QueueObserver> getObserver(){
        return observes;
    }
    
    //ManagerにMessageQueueを登録
    private static Map messageQueueMap = new HashMap();
    @Override
    public void register(MessageQueue mq){
        messageQueueMap.put(mq.getID(), mq);
        mq.start();
    }
    
    @Override
    public Object getAgent(String agID){
        return messageQueueMap.get(agID);
    }
    
    @Override
    public Map getMQMap(){
        return messageQueueMap;
    }
    
    @Override
    public Integer getNumAgents(){
        return messageQueueMap.size() - userID.getNumReserves();
    }
    
    //AgentCloning Mode Select
    @Override
    public Boolean getAutoMode(){
        return this.agentMode == 1;
    }
    
    @Override
    public Boolean getReserveMode(){
        return reserveMode == 1;
    }

    @Override
    public WindowController getWindowController() {
        return this.windowCTRL;
    }

    private DistributedServerConnection sconn;
    @Override
    public void setServerList(Map serverMap) {
        System.out.println("Server Settings UserAgent");
        sconn = new DistributedServerConnection();
        sconn.setServerList((String) serverMap.get("SERVER_LIST_USER"), (Integer) serverMap.get("POOLSIZE"));
    }

    @Override
    public DistributedAgentConnection getConnection(String id) {
        DistributedAgentConnection agcon = sconn.getConnection(0);
        System.out.println("UserAgentManager Get Connection : "+ agcon.toString());
        return agcon;
    }   
}
