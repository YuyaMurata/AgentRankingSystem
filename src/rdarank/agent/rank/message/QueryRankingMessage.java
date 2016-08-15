/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.rank.message;

import com.ibm.agent.exa.Message;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class QueryRankingMessage extends Message{
    public String type;
    public String id;
    
    public void setParams(String type, String id){
        this.type = type;
        this.id = id;
    }
}
