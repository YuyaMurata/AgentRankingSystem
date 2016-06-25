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
import rda.agent.queue.MessageQueue;
import rda.agent.queue.QueueObserver;
import rda.agent.rank.creator.CreateRankAgent;
import rda.clone.AgentCloning;

/**
 *
 * @author 悠也
 */
public class AgentMessageQueueManager {
    private static AgentMessageQueueManager manager = new AgentMessageQueueManager();
    private Boolean runnable;
    private Integer queueLength;
    private Long queuewait, agentwait;
    private Integer agentMode, reserveMode;
    private IDManager id;
    
    //Singleton
    private void AgentMessageQueueManager(){}
    
    public static AgentMessageQueueManager getInstance(){
        return manager;
    }
    
    public void initAgentMessageQueueManager(Map agentMQParam){
        this.queueLength = (Integer)agentMQParam.get("QUEUE_LENGTH");
        this.queuewait = (Long)agentMQParam.get("QUEUE_WAIT");
        this.agentwait = (Long)agentMQParam.get("AGENT_WAIT");
        this.agentMode = (Integer)agentMQParam.get("AGENT_MODE");
        this.reserveMode = (Integer)agentMQParam.get("RESERVE_MODE");
        this.runnable = true;
        
        AgentCloning.setAutoMode(agentMode);
    }
    
    //IDManager setter, getter
    public void setIDManager(IDManager id){
        this.id = id;
    }
    
    public IDManager getIDManager(){
        return id;
    }
    
    //Agentの複数生成 e.g.("R#", 10)
    public void createNumberOfAgents(Integer numOfAgents){
        for(int i=0; i < numOfAgents; i++){
            String agentID = createAgent();
            id.initRegID(agentID);
        }
    }
    
    //Agentの単生成 e.g.("R#01")
    public String createAgent(){
        String agID;
        Object agent = null;
        
        if((agID = id.getReserveID()) == null){
            agID = id.genID();
            CreateRankAgent rankAgent = new CreateRankAgent();
            agent = rankAgent.create(agID, queueLength, queuewait, agentwait);
            register((MessageQueue)agent);
        } else {
            agent = getAgent(agID);
            System.out.println(">> Get Reserve Agent = "+agID);
        }
        
        return agID;
    }
    
    //Agentの複製 e.g.("R#01_Clone")
    public String createCloneAgent(String originalID, Object originalState){
        String agID;
        Object agent = null;
        
        if((agID = id.getReserveID()) == null){
            agID = id.genID();
            CreateRankAgent rankAgent = new CreateRankAgent();
            agent = rankAgent.create(agID, queueLength, queuewait, agentwait);
            register((MessageQueue)agent);
        } else {
            agent = getAgent(agID);
            System.out.println(">> Get Reserve Agent = "+agID);
        }
        
        ((MessageQueue)agent).setOriginalQueue(originalID, originalState);
        
        return agID;
    }
    
    public void registerAgentID(String primalID, String referID){
        id.regID(primalID, referID);
    }
    
    public void reserveAgent(String reserveID){
        id.setReserveID(reserveID);
    }
    
    public void deleteAgentID(String primalID, String referID){
        id.deleteID(primalID, referID);
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
        return messageQueueMap.size() - id.getNumReserves();
    }
    
    public Integer getAutoMode(){
        return agentMode;
    }
    
    public Integer getReserveMode(){
        return reserveMode;
    }
    
    public Map observerToMap(){
        StringBuilder place = new StringBuilder("MessageQueue");
        List field = new ArrayList();
        List data = new ArrayList();
        Map map = new HashMap();
        
        for(int i=0; i < observeList.size(); i++){
            place.append(",{}");
            field.add(observeList.get(i).getName());
            data.add(observeList.get(i).notifyState());
        }
        
        map.put("Place", place.toString());
        map.put("Field", field);
        map.put("Data", data);
        
        return map;
    }
    
    public String observerToString(){
        StringBuilder sb = new StringBuilder();
        StringBuilder sbsize = new StringBuilder();
        for(int i=0; i < observeList.size(); i++){
            sb.append(observeList.get(i).getName());
            sb.append(",");
            
            sbsize.append(observeList.get(i).notifyState());
            sbsize.append(",");
        }
        
        sb.deleteCharAt(sb.length()-1);
        sbsize.deleteCharAt(sbsize.length()-1);
        
        sb.append("\n");
        sb.append(sbsize);
        
        return sb.toString();
    }
}