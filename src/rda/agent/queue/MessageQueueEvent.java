package rda.agent.queue;

import rda.log.AgentLogPrint;

public class MessageQueueEvent extends Exception{
    private String name, clonename, original;
    
    public MessageQueueEvent(String name, String originalID, String clonename) {
        this.name = name;
        this.clonename = clonename;
        this.original = originalID;
    }

    public void printEvent(){
        //if(AgentMessageQueueManager.getInstance().getAutoMode() == 1){
            //System.out.println(">MQEvents:"+name+"-msg="+msgpack.toString());
            AgentLogPrint.printAgentLoad(original, name, clonename);
        //}
    }
    
    public static void printState(String state, String originalID, String cloneID, Integer numAgents){
        AgentLogPrint.printAgentState(state, originalID, cloneID, numAgents);
    }
}