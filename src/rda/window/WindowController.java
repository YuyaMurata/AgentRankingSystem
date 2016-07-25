package rda.window;

import java.util.Collection;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import rda.data.test.DataTemplate;

public class WindowController{
    private WindowAliveThread aliveThread;
    private Map<String, Window> windowMap = new ConcurrentHashMap<>();
    private Queue executableQueue = new ConcurrentLinkedQueue<>();
    private Integer size;
    
    public WindowController(int limit, Long aliveTime, int poolsize) {
        this.size = limit;
        
        //Alive Thread
        this.aliveThread = new WindowAliveThread(this, aliveTime);
        this.aliveThread.start();
    }
        
    public void pack(Object data){
        String destID = ((DataTemplate)data).toID;
        
        if(windowMap.get(destID) == null){
            Window window = new Window(this, destID, size);
            windowMap.put(destID, window);
        } 
        
        try{
            windowMap.get(destID).pack(data);
        }catch(NullPointerException e){
            Window window = new Window(this, destID, size);
            windowMap.put(destID, window);
            windowMap.get(destID).pack(data);
        }
        
    }
    
    public Collection getWindows(){
        return windowMap.values();
    }
    
    public void addExecutable(Window window){
        if(!executableQueue.contains(window)){
            executableQueue.add(window);
            windowMap.remove(window.getDestID());
        }
    }
    
    public Window get(){
        return (Window)executableQueue.peek();
    }
    
    public void remove(){
        executableQueue.poll();
    }
    
    public boolean check(String id){
        return windowMap.get(id) != null;
    }
    
    public void close(){
        aliveThread.stop();
    }
}