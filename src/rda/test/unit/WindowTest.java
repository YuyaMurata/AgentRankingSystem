/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import java.util.ArrayList;
import java.util.List;
import rda.agent.queue.MessageObject;
import rda.window.Window;
import rda.window.WindowController;

/**
 *
 * @author kaeru
 */
public class WindowTest {
    public static void main(String[] args) {
        List msgList = new ArrayList();
        for(int i=0; i < 100; i++){
            String id = "id="+(i%10);
            String toID = "did="+(i%3);
            msgList.add(new MessageObject(id, toID, 1, (i%30)-1));
        }
        
        /*//TestPrint
        for(MessageObject msg : (List<MessageObject>)msgList)
            System.out.println("msg:"+msg.toString());
        */        

        //Input Window
        WindowController window = new WindowController(5, 10L, 8);
        Window msgpack;
        for(MessageObject msg : (List<MessageObject>)msgList){
            window.pack(msg);
            if((msgpack = window.get()) == null) continue;
            
            //Get Destination ID
            //String agID = ((MessageObject)msgpack.unpack().get(0)).toID;
            
            //TestPrint
            //System.out.println("DestinationID = "+agID);
            System.out.println("WindowSize = "+msgpack.unpack().size());
        
            window.remove();
        }
        
    }
}
