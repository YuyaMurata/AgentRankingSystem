/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.window;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import rda.data.test.DataTemplate;

/**
 *
 * @author kaeru
 */
public class Window{
    private String destID;
    private Integer size;
    private WindowController manager;
    private List win = new CopyOnWriteArrayList();

    public Window(WindowController manager, String id, Integer limit) {
        this.destID = id;
        this.size = limit;
        this.manager = manager;
        
        //this.destID = AgentMessageQueueManager.getInstance().getIDManager().getDestID(originID);
    }
    
    public String getKeyID(){
        return destID;
    }
    
    public String getDestID(){
        return this.destID;
    }
  
    public void pack(Object obj){
        DataTemplate data = (DataTemplate) obj; 
        if(data.sentinel != -1) win.add(data.getData());
        
        if((win.size() >= size) || (data.sentinel == -1)) manager.addExecutable(this);
    }
    
    public List unpack(){
        return this.win;
    }
}