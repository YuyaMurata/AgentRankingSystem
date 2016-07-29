/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import rda.window.Window;
import rda.window.WindowController;

import rdarank.agent.rank.manager.RankAgentManager;

/**
 *
 * @author kaeru
 */
public class AgentIntaractionExtension implements Extension{
    private static AgentIntaractionExtension extention = new AgentIntaractionExtension();
    private AgentIntaractionThread thread;
    
    private WindowController windowCTRL  = new WindowController(1000, 100L, 1);
    
    public static AgentIntaractionExtension getInstance(){
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
        // リージョン名のセット
        this.regionName = regionName;

        if (serverRole == Extension.ROLE_PRIMARY) {
            // ロールがエージェント実行環境のプライマリであるかをチェック
            // し，そうであればサービスを開始する．
            // ただし，サンプルの構成では，かならずプライマリである．
            startService();
        }
    }

    @Override
    public void roleChanged(int serverRole) throws Exception {
        if (serverRole == Extension.ROLE_PRIMARY) {
            // ロールがエージェント実行環境のプライマリであるかをチェック
            // し，そうであればサービスを開始する．
            // ただし，サンプルの構成では，かならずプライマリである．
            startService();
        }
    }

    @Override
    public void shutdown() {
    }
    
    
    @Override
    public void start(int serverRole, String regionName) throws Exception {
        // リージョン名のセット
        this.regionName = regionName;

        if (serverRole == Extension.ROLE_PRIMARY) {
            // ロールがエージェント実行環境のプライマリであるかをチェック
            // し，そうであればサービスを開始する．
            // ただし，サンプルの構成では，かならずプライマリである．
            startService();
        }
    }
    
    private void startService(){
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
        try{
            //AgentIntaractionManager Extension AgentKey
            extensionAgentKey = new AgentKey("intaractionmanageragent", new Object[]{"intaractionmanageragent"});
            
            //CreateAgent
            if(am.exists(extensionAgentKey)){
                System.out.println("IntaractionManagerAgent already exists");
            }else {
                am.createAgent(extensionAgentKey);
            }
        } catch (AgentException ex) {
        }
        
        //AgentIntaraction Thread
        thread = new AgentIntaractionThread();
        thread.start();
    }
    
    public WindowController getWindowController() {
        return this.windowCTRL;
    }
    
    public void transport(Object obj){
        //Transport OtherServer Extension
        // ***
        
        try{
            //Window
            Window window = (Window) obj;
        
            //Get Destination ID
            String agID = window.getDestID();
                
            //Get MessageQueue
            MessageQueue mq = (MessageQueue)RankAgentManager.getInstance().getMQMap().get(agID);
            
            //Translation Window To Message
            MessageObject msg = new MessageObject(agID, window.unpack());
            
            mq.put(msg);
            
        }catch(MessageQueueEvent mqev){
            mqev.printEvent();
        } catch (Exception e){
            e.printStackTrace();
        }    
    }
    
    public void communicateAgent(DataTemplate data){
        if(data == null) return ;
        
        windowCTRL.pack(data);
    }
}