/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

import com.ibm.agent.exa.Message;
import java.util.List;

/**
 * 
 * @author kaeru
 */
public class PutMessage extends Message {
    
    public List msgPack;
	
    // パラメータをセット
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
