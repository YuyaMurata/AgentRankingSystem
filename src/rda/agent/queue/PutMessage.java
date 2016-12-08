package rda.agent.queue;

import com.ibm.agent.exa.Message;
import java.util.List;

public class PutMessage extends Message {
    
    public List msgPack;
	
    public void setParams(List msgPack) {
        this.msgPack = msgPack;
    }
    
    public String toString(){
        List<Integer> list = (List)msgPack;
        StringBuilder sb = new StringBuilder();
        for(Integer value : list){
            sb.append(",");
            sb.append(value);
        }
        
        return sb.toString();
    }
}
