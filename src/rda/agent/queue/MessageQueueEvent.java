package rda.agent.queue;

import rda.log.AgentLogPrint;
import rda.manager.AgentMessageQueueManager;
import rda.manager.IDManager;

public class MessageQueueEvent extends Exception{
    private static IDManager id = AgentMessageQueueManager.getInstance().getIDManager();
    private final String name, clonename, original;
    
    public MessageQueueEvent(String name, String clonename) {
        this.name = name;
        this.clonename = clonename;
        this.original = id.getOrigID(name);
    }

    public void printEvent(){
        //if(AgentMessageQueueManager.getInstance().getAutoMode() == 1){
            //System.out.println(">MQEvents:"+name+"-msg="+msgpack.toString());
            AgentLogPrint.printAgentLoad(original, name, clonename);
        //}
    }
    
    public static void printState(String state, String cloneID){
        AgentLogPrint.printAgentState(state, id.getOrigID(cloneID), cloneID);
    }
}