/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.user.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import com.ibm.agent.exa.TxID;
import rdarank.Useragent;
import rdarank.agent.user.message.UpdateCommunicationIDMessage;

/**
 *
 * @author kaeru
 */
public class UpdateCommunicationIDHandler  extends MessageHandler{

    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        // TODO 自動生成されたメソッド・スタブ
        UpdateCommunicationIDMessage updateMsg = (UpdateCommunicationIDMessage)msg;
        
        // マスターエンティティを取得
        Useragent agent = (Useragent)getEntity();
        
        // トランザクションIDを取得
        TxID tx = getTx();
        
        agent.setCommunicationID(tx, updateMsg.communicationID);
        
        return 0L;
    }
    
}
