package rda.agent.queue;

import java.util.List;
import rda.agent.template.MessageTemplate;

public class MessageObject extends MessageTemplate{
    public String id;
    public Object data;
    
    public MessageObject(String destID, Object data) {
        // TODO 自動生成されたコンストラクター・スタブ
        super();
        this.data = data;
    }
    
    @Override
    public String toString() {
        return id + ": datasize=" + ((List)data).size();
    }
}