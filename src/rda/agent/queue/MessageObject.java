package rda.agent.queue;

import rda.agent.template.MessageTemplate;

public class MessageObject extends MessageTemplate{
    public long data;
    
    public MessageObject(String id, int sntinel, String destID) {
        // TODO 自動生成されたコンストラクター・スタブ
        super(id, destID, sntinel);
    }
    
    @Override
    public void setData(Object data) {
        this.data = (Long)data;
    }
    
    @Override
    public String toString() {
        return id + "=>" + toID +" ("+data+")";
    }
}
