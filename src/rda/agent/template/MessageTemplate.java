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
    private long timestamp;
    private int length;
    
    public MessageTemplate() {
        // TODO 自動生成されたコンストラクター・スタブ
        this.timestamp = System.currentTimeMillis();
    }

    @Override
    public abstract String toString();
    
    public Long latency(){
        return System.currentTimeMillis() - timestamp;
    }
    
    public void setLength(int length){
        this.length = length;
    }
    
    public int getLength(){
        return length;
    }
}
