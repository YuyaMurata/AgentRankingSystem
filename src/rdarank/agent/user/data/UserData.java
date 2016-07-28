/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.user.data;

import java.util.HashMap;
import java.util.Map;
import rda.data.test.DataTemplate;

/**
 *
 * @author kaeru
 */
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
