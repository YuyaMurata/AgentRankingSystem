package rda.agent.queue;

import rda.agent.template.MessageTemplate;

public class MessageObject extends MessageTemplate{
    public int data;
    
    public MessageObject(String id, String destID, Object data, int sntinel) {
        // TODO 自動生成されたコンストラクター・スタブ
        super(id, destID, sntinel);
        this.data = (int)data;
    }
    
    @Override
    public void setData(Object data) {
        this.data = (int)data;
    }
    
    @Override
    public String toString() {
        return id + "=>" + toID +" ("+data+")" + "-- state:"+sentinel;
    }
}