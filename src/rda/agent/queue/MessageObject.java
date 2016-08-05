package rda.agent.queue;

import java.io.Serializable;
import java.util.List;

import rda.manager.IDManager;
import rdarank.agent.rank.manager.RankAgentManager;

public class MessageObject implements Serializable {
    public Long lateTime;
    public String id;
    public List data;
    
    public MessageObject(String destID, Object data) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.lateTime = System.currentTimeMillis();
        this.id = destID;
        this.data = (List)data;
    }
    
    /*
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
    */
    //Only RankAgent
    public String getID(){
        IDManager rankID = RankAgentManager.getInstance().getIDManager();
        String[] idarr = this.id.split(",");
        return rankID.getDestID(idarr[0], idarr[1]);
    }
    
    public Long latency(){
        return System.currentTimeMillis() - lateTime;
    }
    
    @Override
    public String toString() {
        return id + ": datasize=" + data;
    }
}