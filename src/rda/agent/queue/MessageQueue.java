package rda.agent.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import rda.agent.template.AgentType;
import rda.clone.AgentCloning;
import rda.manager.AgentManager;
import rda.manager.IDManager;

public class MessageQueue extends MessageQueueProcess{
    private AgentManager manager;
    private BlockingQueue<Object> queue;
    private String name;
    private AgentType agent;
    private Integer size;
    private long getwait, putwait;
    
    public MessageQueue(AgentManager manager, String name, Integer size, Long queuewait, Long agentwait){
        this.manager = manager;
        
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
        manager.add(observe);
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
    public Boolean put(Object msgpack) throws MessageQueueEvent{
        boolean success = false;
        try {
            success = queue.offer(msgpack, putwait, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
        }
        
        if(manager.getAutoMode()){
            //AgentCloning
            if(!success) eventClone();
            //AgentDelete
            if(isClone() && ((queue.size() + orgQueue.size()) == 0)) eventDelete();
        }
        
        return success;
    }
    
    //Load Balancer Cloning updgrade
    public void eventClone()  throws MessageQueueEvent{
        IDManager id = manager.getIDManager();
        String cloneID = AgentCloning.cloning(manager, name, queue);
        MessageQueueEvent.printState("cloning", id.getOrigID(name), cloneID, manager.getNumAgents());
        
        throw new MessageQueueEvent(name, id.getOrigID(name), cloneID);
    }
    
    //Load Balancer Cloning degrade
    public void eventDelete() {
        IDManager id = manager.getIDManager();
        MessageQueueEvent.printState("delete", name, id.getOrigID(name), manager.getNumAgents());
        String deleteID = AgentCloning.delete(manager, name);
    }
    
    //Only AgnetClone
    private LinkedBlockingDeque orgQueue;
    public void setOriginalQueue(Object originalState){
        this.orgQueue =  (LinkedBlockingDeque) originalState;
        
        this.checkClone = true;
        
        //Work Stealing
        Object obj;
        int i = size / 2;
        while((obj = orgQueue.pollFirst()) != null){
            i--;
            if(i <= 0) break;
            try {
                put(obj);
            } catch (MessageQueueEvent mqev) {
                mqev.printEvent();
            }
        }
    }
    
    private Boolean checkClone = false;
    private Boolean isClone(){
        return checkClone;
    }
    
    //MessageQueue Process Overrides
    @Override
    public Boolean getRunnable() {
        return manager.getState();
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
    
    public Integer getQueueLenght(){
        return queue.size();
    }
}