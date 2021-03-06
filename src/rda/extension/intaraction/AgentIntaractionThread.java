package rda.extension.intaraction;

import com.ibm.agent.exa.client.AgentClient;
import rda.agent.client.DistributedAgentConnection;
import rda.agent.queue.MessageObject;
import rda.extension.manager.SystemManagerExtension;
import rda.manager.AgentManager;
import rda.window.Window;
import rdarank.agent.rank.manager.RankAgentManager;

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
                    Thread.sleep(1L);
                } catch (InterruptedException ex) {
                }
            } else {
                //Translation Window To Message
                MessageObject msg = new MessageObject(window.getDestID(), window.unpack());
                
                //local
                if (manager.getDeployPattern() != 1) {
                    if (extension.transport(msg))
                        extension.getWindowController().remove();
                    else extension.getWindowController().returnExecutable(window);
                //dist deploy
                }else {
                    DistributedAgentConnection agcon = agent.getConnection(msg.rankID());
                    AgentClient client = agcon.getClient();
                    
                    //System.out.print(">> Server <"+agcon.toString()+"> ");
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
