/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.manager;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.AgentKey;
import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.soliddb.extension.Extension;
import java.util.Properties;
import rda.property.SetProperty;
import rdarank.manager.RankingSystemManager;

/**
 *
 * @author kaeru
 */
public class SystemManagerExtension  implements Extension, SetProperty{
    private static SystemManagerExtension extention = new SystemManagerExtension();
    
    public static SystemManagerExtension getInstance(){
        return extention;
    }
    
    AgentKey extensionAgentKey;
    public SystemManagerExtension() {
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
        //Only Thread Type
        //manager.dataStream().stop();
        
        manager.stopLogger();
        
        manager.shutdownSystem();
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
        System.out.println("Start SystemManager Extension");
		
        System.out.println("************ ************  **********  ************");
        System.out.println("************ ************ ************ ************");
        System.out.println("    ***      ***          ***       **      ***    ");
        System.out.println("    ***      ************  ********         ***    ");
        System.out.println("    ***      ************    ********       ***    ");
        System.out.println("    ***      ***          **       ***      ***    ");
        System.out.println("    ***      ************ ************      ***    ");
        System.out.println("    ***      ************  **********       ***    ");
        
        //Set Agent
        AgentManager am = AgentManager.getAgentManager();
        try{
            //SystemManager Extension AgentKey
            extensionAgentKey = new AgentKey("systemmanageragent", new Object[]{"systemmanageragent"});
            
            //CreateAgent
            if(am.exists(extensionAgentKey)){
                System.out.println("SystemManagerAgent already exists");
            }else {
                am.createAgent(extensionAgentKey);
            }
        } catch (AgentException ex) {
        }
    }
    
    //Rankig System Manager
    private RankingSystemManager manager;
    public void startRankingSystem(){
        manager = RankingSystemManager.getInstance();
        
        //Setting Property & Initialise Agent
        manager.launchSystem();
        
        //Data Stream Generator Initialize
        manager.setDataStreamGenerator();
        
        //Start Logger
        manager.startLogger();
    }
    
    public void dataGenerate(Long time){
        manager.timeDataStream().stream(time);
    }
}