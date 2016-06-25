package rdarank.agent.queue;

import java.io.Serializable;

public class MessageObject implements  Serializable{

    public String id, destID;
    public int data;
    private Long time, arrive;

    public MessageObject(String id, int data, String destID) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.id = id;
        this.data = data;
        this.destID = destID;
        this.time = System.currentTimeMillis();
    }
	
    @Override
    public String toString(){
        return id+","+data+"->"+destID;
    }
    
    public Long latency(){
        arrive = System.currentTimeMillis();
        return arrive - time;
    }
}
