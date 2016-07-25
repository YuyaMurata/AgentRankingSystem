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
public class DataGenerateHandler extends MessageHandler{
    private static Long time = 0L;
    
    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        
        //DataGenerate
        SystemManagerExtension extension = SystemManagerExtension.getInstance();
        extension.dataGenerate(time++);
        
        return 0L;
    }
    
}
