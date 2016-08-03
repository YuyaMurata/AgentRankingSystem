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

        while (SystemManagerExtension.getInstance().getState()) {
            Window window = extension.getWindowController().get();
            if (window == null) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException ex) {
                }
            } else {
                //System.out.println("Transport Window ! wsize=" + ((Window) window).getSize());
                //Translation Window To Message
                IDManager rankID = RankAgentManager.getInstance().getIDManager();
                String[] id = window.getDestID().split(",");
                MessageObject msg = new MessageObject(rankID.getDestID(id[0], id[1]), window.unpack());

                //local
                if (extension.transport(msg)) {
                    extension.getWindowController().remove();
                }

                //dist deploy
                if (SystemManagerExtension.getInstance().getDeployPattern() == 1) {
                    AgentClient client = null;//AgentConnection.getInstance().getConnection();
                    Transport trans = new Transport();
                    trans.sendMessage(client, msg);
                }
            }
        }

        extension.getWindowController().close();
        System.out.println(name + " Stop !");

    }
}
