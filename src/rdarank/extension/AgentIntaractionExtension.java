/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.extension;

import com.ibm.agent.soliddb.extension.Extension;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author kaeru
 */
public class AgentIntaractionExtension implements Extension{
    private static AgentIntaractionExtension extention = new AgentIntaractionExtension();
    private AgentIntaractionThread thread;
    
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
        thread = new AgentIntaractionThread();
        thread.start();
    }
    
    private List idList = new CopyOnWriteArrayList<>();
    private List dataList = new CopyOnWriteArrayList<>();
    public void communicateAgent(String id, long data){
        idList.add(id);
        dataList.add(data);
    }

    public Map getStatus() {
        if(idList.isEmpty()) return null;
        
        String s1 = ((List<String>)idList).stream()
                    .collect(Collectors.joining(","));
        
        String s2 = ((List<Long>)dataList).stream()
                    .map(n -> n.toString())
                    .collect(Collectors.joining(","));
        
        Map map = new HashMap();
        map.put("id", s1);
        map.put("data", s2);
        
        return map;
    }
}
