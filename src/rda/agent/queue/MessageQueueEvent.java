package rda.agent.queue;

import rda.log.AgentLogPrint;
import rda.window.Window;


public class MessageQueueEvent extends Exception{
    /**
    *
    */
    private final String name, clonename;
    private final Window msgpack;
    public MessageQueueEvent(String name, String clonename, Object msgpack) {
        super(name);
        this.name = name;
        this.clonename = clonename;
        this.msgpack = (Window)msgpack;
    }

    public void printEvent(){
        //if(AgentMessageQueueManager.getInstance().getAutoMode() == 1){
            //System.out.println(">MQEvents:"+name+"-msg="+msgpack.toString());
            AgentLogPrint.printAgentLoad(msgpack.getOrigID(), name, clonename);
        //}
    }
    
    public static void printState(String state, String origin, String clone){
        AgentLogPrint.printAgentState(state, origin, clone);
    }
}