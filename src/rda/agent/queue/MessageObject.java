package rda.agent.queue;

import java.util.List;
import rda.agent.template.MessageTemplate;
import rda.manager.IDManager;
import rdarank.agent.rank.manager.RankAgentManager;

public class MessageObject extends MessageTemplate{
    public String id;
    public List data;
    
    public MessageObject(String destID, Object data) {
        // TODO 自動生成されたコンストラクター・スタブ
        super();
        this.id = destID;
        this.data = (List)data;
    }
    
    //Only RankAgent
    public String getID(){
        IDManager rankID = RankAgentManager.getInstance().getIDManager();
        String[] idarr = this.id.split(",");
        System.out.println(id);
        return rankID.getDestID(idarr[0], idarr[1]);
    }
    
    @Override
    public String toString() {
        return id + ": datasize=" + data;
    }
}