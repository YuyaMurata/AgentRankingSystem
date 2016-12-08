package rda.window;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import rda.data.test.DataTemplate;

public class Window{
    private String destID;
    private Integer size;
    private WindowController manager;
    private List win = new CopyOnWriteArrayList();

    public Window(WindowController manager, String id, Integer limit) {
        this.destID = id;
        this.size = limit;
        this.manager = manager;
    }
    
    public String getDestID(){
        return destID;
    }
    
    public void setDestID(String id){
        destID = id;
    }
  
    public void pack(Object obj){
        DataTemplate data = (DataTemplate) obj; 
        win.add(data.getData());
        
        if(win.size() >= size) manager.addExecutable(this);
    }
    
    public List unpack(){
        return this.win;
    }
    
    public Integer getSize(){
        return this.win.size();
    }
}