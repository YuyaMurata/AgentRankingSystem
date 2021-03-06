package rda.agent.queue;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import rda.manager.IDManager;
import rdarank.agent.rank.manager.RankAgentManager;

public class MessageObject implements Externalizable {
    public long lateTime;
    public String id;
    public List data;

    public MessageObject() {
    }
    
    public MessageObject(String destID, Object data) {
        this.lateTime = System.currentTimeMillis();
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
        this.lateTime = (long)in.readObject();
        this.id = (String) in.readObject();
        this.data = (List) in.readObject();
    }
    
    //Only RankAgent
    public String getID(){
        IDManager rankID = RankAgentManager.getInstance().getIDManager();
        String[] idarr = this.id.split(",");
        return rankID.getDestID(idarr[0], idarr[1]);
    }
    
    public String rankID(){
        String[] idarr = this.id.split(",");
        return idarr[0];
    }
    
    public Long latency(){
        return System.currentTimeMillis() - lateTime;
    }
    
    @Override
    public String toString() {
        return id + ": datasize=" + data;
    }
}