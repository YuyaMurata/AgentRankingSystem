/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.template;

import java.io.Serializable;

/**
 *
 * @author kaeru
 */
public abstract class MessageTemplate implements Serializable{
    public String id, toID; 
    public int sentinel;
    private long timestamp;
    
    public MessageTemplate(String id, String toID, int sentinel) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.id = id;
        this.sentinel = sentinel;
        this.toID = toID;
        this.timestamp = System.currentTimeMillis();
    }
    
    public abstract void setData(Object data);
    
    @Override
    public abstract String toString();
    
    public Long latency(){
        return System.currentTimeMillis() - timestamp;
    }
}
