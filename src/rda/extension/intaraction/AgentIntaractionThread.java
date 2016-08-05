/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.intaraction;

import com.ibm.agent.exa.client.AgentClient;
import rda.agent.client.AgentConnection;
import rda.agent.client.DistributedAgentConnection;
import rda.agent.queue.MessageObject;
import rda.extension.manager.SystemManagerExtension;
import rda.manager.AgentManager;
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

    private SystemManagerExtension manager;
    private AgentManager agent;
    public AgentIntaractionThread() {
        this.manager = SystemManagerExtension.getInstance();
        this.agent = RankAgentManager.getInstance();
    }
    
    @Override
    public void run() {
        System.out.println(name + " Start !");
        Transport trans = new Transport();
        
        while (manager.getState()) {
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
                if (manager.getDeployPattern() == 0) {
                    if (extension.transport(msg)) {
                        extension.getWindowController().remove();
                    }
                //dist deploy
                }else if(manager.getDeployPattern() == 1){
                    DistributedAgentConnection agcon = agent.getConnection(msg.rankID());
                    AgentClient client = agcon.getClient();
                    
                    System.out.print(">> Server <"+agcon.toString()+"> ");
                    trans.sendMessage(client, msg);
                    extension.getWindowController().remove();
                    
                    agcon.returnConnection(client);
                }
            }
            }catch(Exception e){e.printStackTrace();}
        }

        extension.getWindowController().close();
        System.out.println(name + " Stop !");

    }
}
