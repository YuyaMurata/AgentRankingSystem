/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.extension;

import com.ibm.agent.soliddb.extension.Extension;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import rda.agent.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class AgentIntaractionExtension implements Extension{
    private static AgentIntaractionExtension extention = new AgentIntaractionExtension();
    private AgentIntaractionThread thread;
    private ConcurrentLinkedQueue queue;
    
    public static AgentIntaractionExtension getInstance(){
        return extention;
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
        thread.stop();
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
            //for(int i=0; i < numQueue; i++) mq[i] = new MessageQueue(i);
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
	
        //AgentIntaraction Thread
        this.queue = new ConcurrentLinkedQueue<>();
        thread = new AgentIntaractionThread(this.queue);
        //thread.start();
    }
    
    public void communicateAgent(MessageObject msg){
        try{
        this.queue.offer(msg);
        System.out.println(msg.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
