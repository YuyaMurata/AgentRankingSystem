/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.manager.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import rda.extension.manager.SystemManagerExtension;

/**
 *
 * @author kaeru
 */
public class LaunchSystemHandler extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        
        System.out.println("Launch System Handler 1");
        
        //Launch System
        SystemManagerExtension extension = SystemManagerExtension.getInstance();
        
        try{
            extension.startRankingSystem();
            
            return "SystemManager Launch RankingSystem !";
        }catch(Exception e){
            return e;
        }
        
    }
    
}
