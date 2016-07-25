/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.manager;

import com.ibm.agent.soliddb.extension.Extension;
import java.util.Properties;

/**
 *
 * @author kaeru
 */
public class SystemManagerExtension  implements Extension{
    private static SystemManagerExtension extention = new SystemManagerExtension();
    
    
    public static SystemManagerExtension getInstance(){
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
    }
}
