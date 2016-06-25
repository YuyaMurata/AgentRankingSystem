/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import rdarank.agent.template.AgentType;
import rda.clone.AgentCloning;
import rda.manager.AgentMessageQueueManager;
import rda.window.Window;

/**
 *
 * @author 悠也
 */
public class MessageQueue extends MessageQueueProcess{
    private BlockingQueue<Object> queue;
    private String name;
    private AgentType agent;
    private Integer size;
    private long getwait, putwait;
    
    public MessageQueue(String name, Integer size, Long queuewait, Long agentwait){
        this.name = name;
        this.size = size;
        this.getwait = agentwait;
        this.putwait = queuewait;
        this.queue = new LinkedBlockingDeque<>(size+1);
        
        //Message Queue Length @RECORDS
        QueueObserver observe = new QueueObserver(name, queue);
        register(observe);
    }
    
    private void register(QueueObserver observe){
        AgentMessageQueueManager.getInstance().add(observe);
    }
    
    @Override
    public Object get(){
        Object obj = null;
        try {
            obj = queue.poll(getwait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
        }
        
        return obj;
    }
    
    @Override
    public void put(Object msgpack) throws MessageQueueEvent{
        boolean success = false;
        try {
            success = queue.offer(msgpack, putwait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
        }
        
        if(!success) eventClone(msgpack);
        
        if(isClone() && ((queue.size() + orgQueue.size()) == 0)) eventDelete();
    }
    
    //Load Balancer Cloning updgrade
    public void eventClone(Object msgpack) throws MessageQueueEvent{
        String agID = AgentCloning.cloning(((Window)msgpack).getOrigID(), queue);
        throw new MessageQueueEvent(name, agID, msgpack);
    }
    
    //Load Balancer Cloning degrade
    public void eventDelete() {
        AgentCloning.delete(originalID, name);
    }
    
    //Only AgnetClone
    private LinkedBlockingDeque orgQueue;
    private String originalID;
    public void setOriginalQueue(String originalID, Object originalState){
        this.originalID = originalID;
        this.orgQueue =  (LinkedBlockingDeque) originalState;
        
        this.checkClone = true;
        
        //Work Stealing
        Object obj;
        int i= orgQueue.size() / 2;
        while((obj = orgQueue.pollFirst()) != null)
            try {
                i--;
                if(i <= 0) break;
                put(obj);
            } catch (MessageQueueEvent ex) {
            }
    }
    
    private Boolean checkClone = false;
    private Boolean isClone(){
        return checkClone;
    }
    
    //MessageQueue Process Overrides
    @Override
    public Boolean getRunnable() {
        return AgentMessageQueueManager.getInstance().getState();
    }

    @Override
    public void setAgentType(AgentType type) {
        this.agent = type;
    }

    @Override
    public AgentType getAgentType() {
        return this.agent;
    }
    
    public String getID(){
        return name;
    }
}