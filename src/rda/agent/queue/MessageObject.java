package rda.agent.queue;

import java.util.List;
import rda.agent.template.MessageTemplate;

public class MessageObject extends MessageTemplate{
    public String id;
    public List data;
    
    public MessageObject(String destID, Object data) {
        // TODO 自動生成されたコンストラクター・スタブ
        super();
        this.id = destID;
        this.data = (List)data;
    }
    
    @Override
    public String toString() {
        return id + ": datasize=" + data;
    }
}