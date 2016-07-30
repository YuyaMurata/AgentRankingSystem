/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.rank.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rda.agent.queue.MessageQueue;
import rda.agent.queue.QueueObserver;
import rda.manager.AgentManager;
import rda.manager.IDManager;
import rda.manager.LoggerManager;
import rda.window.WindowController;

import rdarank.agent.rank.creator.CreateRankAgentEx;
import rdarank.agent.rank.logger.RankAgentLogPrinter;

/**
 *
 * @author kaeru
 */
public class RankAgentManager extends AgentManager{
    private static RankAgentManager manager = new RankAgentManager();
    private static Boolean runnable = true;
    
    private IDManager rankID;
    private WindowController windowCTRL;
    
    private Integer queueLength;
    private Long queuewait, agentwait;
    private Integer agentMode, reserveMode;
    
    private List<QueueObserver> observes;
    
    private RankAgentManager() {
    }
    
    public static RankAgentManager getInstance(){
        return manager;
    }
    
    public void initRankAgent(Map rankAgentMapParam){
        this.queueLength = (Integer)rankAgentMapParam.get("QUEUE_LENGTH");
        this.queuewait = (Long)rankAgentMapParam.get("QUEUE_WAIT");
        this.agentwait = (Long)rankAgentMapParam.get("AGENT_WAIT");
        this.reserveMode = (Integer)rankAgentMapParam.get("RESERVE_MODE");
        
        this.observes = new ArrayList();
        
        //Init IDManager
        this.rankID = new IDManager(rankAgentMapParam);
        
        //Init AgentCloning Mode
        //this.agentMode = (Integer)rankAgentMapParam.get("AGENT_MODE");
        this.agentMode = 0;
        
        //Init WindowController
        this.windowCTRL = new WindowController((Integer)rankAgentMapParam.get("WINDOW_SIZE"),
                                          (Long)rankAgentMapParam.get("ALIVE_TIME"),
                                          (Integer)rankAgentMapParam.get("POOLSIZE"));
        
        System.out.println("Finish Initialize RankAgentManager !");
    }
    
    public void setLogger(){
        //Init LogPrinter
        RankAgentLogPrinter log = new RankAgentLogPrinter("rankagent");
        LoggerManager.getInstance().setLogPrinter(log);
    }
    
    @Override
    public IDManager getIDManager(){
        return rankID;
    }
    
    //Agentの複数生成 e.g.("R#", 10)
    public void createNumberOfAgents(Integer numOfAgents){
        for(int i=0; i < numOfAgents; i++){
            String agentID = createAgent();
            rankID.initRegID(agentID);
        }
    }
    
    //Agentの単生成 e.g.("R#01")
    public String createAgent(){
        String agID;
        Object agent = null;
        
        if((agID = rankID.getReserveID()) == null){
            agID = rankID.genID();
            CreateRankAgentEx rankAgent = new CreateRankAgentEx();
            rankAgent.create(agID, queueLength, queuewait, agentwait);
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
    @Override
    public Boolean getState(){
        return runnable;
    }
    
    //全てのMessageQueueを終了する
    public void doShutdown(){
        runnable = false;
        windowCTRL.close();
    }
    
    //Logger用にMQの監視オブジェクトを登録
    @Override
    public void add(QueueObserver observe) {
        observes.add(observe);
    }
    
    public List<QueueObserver> getObserver(){
        return observes;
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
        return messageQueueMap.size() - rankID.getNumReserves();
    }
    
    //AgentCloning Mode Select
    @Override
    public Boolean getAutoMode(){
        return this.agentMode == 1;
    }
    
    public Boolean getReserveMode(){
        return reserveMode == 1;
    }

    @Override
    public WindowController getWindowController() {
        return this.windowCTRL;
    }
}