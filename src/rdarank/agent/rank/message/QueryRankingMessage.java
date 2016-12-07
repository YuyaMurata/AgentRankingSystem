package rdarank.agent.rank.message;

import com.ibm.agent.exa.Message;

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
