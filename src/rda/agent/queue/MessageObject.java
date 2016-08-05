package rda.agent.queue;

import com.ibm.agent.exa.AgentKey;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
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
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(lateTime);
        out.writeObject(id);
        out.writeObject(data);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.lateTime = (Long)in.readObject();
        this.id = (String) in.readObject();
        this.data = (List) in.readObject();
    }
    
    //Only RankAgent
    public String getID(){
        IDManager rankID = RankAgentManager.getInstance().getIDManager();
        String[] idarr = this.id.split(",");
        return rankID.getDestID(idarr[0], idarr[1]);
    }
    
    @Override
    public String toString() {
        return id + ": datasize=" + data;
    }
}