package rda.extension.intaraction;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.soliddb.extension.Extension;

import java.util.Properties;

import rda.agent.queue.MessageObject;
import rda.agent.queue.MessageQueue;
import rda.agent.queue.MessageQueueEvent;
import rda.data.test.DataTemplate;
import rda.window.WindowController;

import rdarank.agent.rank.manager.RankAgentManager;

public class AgentIntaractionExtension implements Extension {

    private static AgentIntaractionExtension extention = new AgentIntaractionExtension();

    private WindowController windowCTRL = new WindowController(1000, 10L, 1);

    public static AgentIntaractionExtension getInstance() {
        return extention;
    }

    AgentKey extensionAgentKey;

    public AgentIntaractionExtension() {
    }

    // リージョン名
    String regionName;

    @Override
    public void initialize(String serverTypeName, Properties params) throws Exception {
    }

    @Override
    public void primaryChanged() throws Exception {
    }

    @Override
    public void regionChanged(int serverRole, String regionName) throws Exception {
        this.regionName = regionName;

        if (serverRole == Extension.ROLE_PRIMARY) {
            startService();
        }
    }

    @Override
    public void roleChanged(int serverRole) throws Exception {
        if (serverRole == Extension.ROLE_PRIMARY) {
            startService();
        }
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void start(int serverRole, String regionName) throws Exception {
        this.regionName = regionName;

        if (serverRole == Extension.ROLE_PRIMARY) {
            startService();
        }
    }

    private void startService() {
        System.out.println("> Start AgentIntaraction Extension");

        System.out.println("       *****             ********* ");
        System.out.println("      *******            ********* ");
        System.out.println("     ***   ***              ***    ");
        System.out.println("    ***     ***             ***    ");
        System.out.println("   *************            ***    ");
        System.out.println("  ***************           ***    ");
        System.out.println(" ***           ***       ********* ");
        System.out.println("***             ***      ********* ");

        //Set Agent
        AgentManager am = AgentManager.getAgentManager();
        try {
            //AgentIntaractionManager Extension AgentKey
            extensionAgentKey = new AgentKey("intaractionmanageragent", new Object[]{"intaractionmanageragent"});

            //CreateAgent
            if (am.exists(extensionAgentKey)) {
                System.out.println("IntaractionManagerAgent already exists");
            } else {
                am.createAgent(extensionAgentKey);
            }
        } catch (AgentException ex) {
        }

    }

    public WindowController getWindowController() {
        return this.windowCTRL;
    }

    public Boolean transport(MessageObject msg) {
        //Transport OtherServer Extension
        // ***
        //Get MessageQueue
        MessageQueue mq = (MessageQueue) RankAgentManager.getInstance().getMQMap().get(msg.getID());

        if (mq == null) {
            System.out.println("Not Found Destination !");
            return true;
        }

        try {
            mq.put(msg);
        } catch (MessageQueueEvent mqev) {
            mqev.printEvent();
            return false;
        }

        return true;
    }

    public void startConnectionAgents() {
        System.out.println("rda.extension.intaraction.AgentIntaractionExtension.startConnectionAgents() >> " + "start intaractions!");
        //AgentIntaraction Thread
        AgentIntaractionThread thread = new AgentIntaractionThread();
        thread.start();
    }

    public void communicateAgent(DataTemplate data) {
        if (data == null) {
            return;
        }

        windowCTRL.pack(RankAgentManager.getInstance(), data);
    }
}
