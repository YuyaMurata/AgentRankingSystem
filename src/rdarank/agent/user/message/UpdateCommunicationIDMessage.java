/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.user.message;

import com.ibm.agent.exa.Message;

/**
 *
 * @author kaeru
 */
public class UpdateCommunicationIDMessage extends Message{
    //New Communication ID
    public String communicationID;

    public void setParams(String communicationID){
        this.communicationID = communicationID;
    }
}
