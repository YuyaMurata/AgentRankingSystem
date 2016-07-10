package rda.agent.queue;

import rda.log.AgentLogPrint;
import rda.window.Window;


public class MessageQueueEvent extends Exception{
    /**
    *
    */
    private final String name, clonename, original;
    public MessageQueueEvent(String name, String clonename, String original) {
        this.name = name;
        this.clonename = clonename;
        this.original = original;
    }

    public void printEvent(){
        //if(AgentMessageQueueManager.getInstance().getAutoMode() == 1){
            //System.out.println(">MQEvents:"+name+"-msg="+msgpack.toString());
            AgentLogPrint.printAgentLoad(original, name, clonename);
        //}
    }
    
    public static void printState(String state, String origin, String clone){
        AgentLogPrint.printAgentState(state, origin, clone);
    }
}