/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.rank.message;

import com.ibm.agent.exa.Message;

/**
 *
 * @author kaeru
 */
public class QueryRankingMessage extends Message{
    //User Data
    public Long userData;

    public void setParams(Long userData){
        this.userData = userData;
    }
    
}
