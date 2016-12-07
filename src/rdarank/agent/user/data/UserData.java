package rdarank.agent.user.data;

import java.util.HashMap;
import java.util.Map;
import rda.data.test.DataTemplate;

public class UserData extends DataTemplate{
    private Map data = new HashMap();
    
    public UserData(String userID, String rankID){
        super(userID, rankID);
    }
    
    @Override
    public void setData(Object data) {
        this.data.put(id, data);
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public String toString() {
        return id + ","+data + " -> " + toID;
    }
    
}
