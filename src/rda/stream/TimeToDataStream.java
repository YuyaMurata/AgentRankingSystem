/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.stream;

import java.util.Map;
import rda.agent.queue.MessageObject;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.MessageQueueEvent;
import rda.agent.template.AgentType;
import rda.data.test.TestData;
import rda.manager.AgentManager;
import rda.manager.TestCaseManager;
import rda.window.Window;

/**
 * Distirubutee Environment  -- X
 * @author kaeru
 */
public class TimeToDataStream {
    private static final String name = "DataStream";
    private Long total=0L;
    
    private TestCaseManager tcmanager;
    private AgentManager manager;

    public TimeToDataStream(AgentManager manager) {
        this.manager = manager;
        this.tcmanager  = TestCaseManager.getInstance();
    }
    
    //MQを通さない場合かつHashMapを利用しない
    private AgentType agArr[];
    public void setAgentTypeList(AgentType agArr[]){
        this.agArr = agArr;
    }
    
    public void stream(Long t){
        Map mqMap = manager.getMQMap();
        TestData data;
        Window window = null;

        while(((data = tcmanager.datagen.generate(t)) != null) && manager.getState()){
            try {
                manager.getWindowController().pack(data);
                
                if((window = manager.getWindowController().get()) == null) continue;
                
                //System.out.println(">> TimeToDataStream >> Get WindowCTRL after that Send Data");
                
                //Get Destination ID
                String agID = window.getDestID();
                
                //Translation Window To Message
                MessageObject msg = new MessageObject(agID, window.unpack());
                
                //Get MessageQueue
                MessageQueue mq = (MessageQueue)mqMap.get(agID);
                //Message Sender
                if(mq != null) {
                    if(mq.put(msg)) manager.getWindowController().remove();
                    else manager.getWindowController().returnExecutable(window);
                }
                //MessageQueueを通さないSender
                //Integer hash =Integer.valueOf(agID.split("#")[1]) % agArr.length;
                //AgentType agent = agArr[hash];//(AgentType) manager.getAgent(agID);
                //agent.sendMessage(msg);
                
                //System.out.println("DataStream Check : "+agID +" = "+agent.getID());
                
                //total = total+window.unpack().size();
                
                //manager.getWindowController().remove();
                
                //System.out.println(">> TimeToDataStream >> Finished Send Data");
            } catch (MessageQueueEvent mqev) {
                mqev.printEvent();
                manager.getWindowController().returnExecutable(window);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}