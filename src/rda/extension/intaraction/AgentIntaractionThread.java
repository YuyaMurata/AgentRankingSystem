/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.intaraction;

import com.ibm.agent.exa.client.AgentClient;
import rda.agent.client.AgentConnection;
import rda.agent.queue.MessageObject;
import rda.extension.manager.SystemManagerExtension;
import rda.manager.IDManager;
import rda.window.Window;
import rdarank.agent.rank.manager.RankAgentManager;

/**
 *
 * @author kaeru
 */
public class AgentIntaractionThread extends Thread {

    private static final String name = "AgentIntaraction Thread";
    private static final AgentIntaractionExtension extension = AgentIntaractionExtension.getInstance();

    @Override
    public void run() {
        System.out.println(name + " Start !");
        Transport trans = new Transport();
        
        while (SystemManagerExtension.getInstance().getState()) {
            try{
            Window window = extension.getWindowController().get();
            if (window == null) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException ex) {
                }
            } else {
                //Translation Window To Message
                MessageObject msg = new MessageObject(window.getDestID(), window.unpack());
                
                //local
                if (SystemManagerExtension.getInstance().getDeployPattern() == 0) {
                    if (extension.transport(msg)) {
                        extension.getWindowController().remove();
                    }
                //dist deploy
                }else if(SystemManagerExtension.getInstance().getDeployPattern() == 1){
                    System.out.println("Transport Window ! wsize=" + ((Window) window).getSize());
                    //AgentClient client = RankAgentManager.getInstance().getConnection("TEST").getConnection();  
                    //trans.sendMessage(client, msg);
                    extension.getWindowController().remove();
                }
            }
            }catch(Exception e){e.printStackTrace();}
        }

        extension.getWindowController().close();
        System.out.println(name + " Stop !");

    }
}
