package rda.window;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import rda.agent.queue.MessageObject;

public class WindowController{
    private ExecutorService aliveThread;
    private Map<String, Window> windowMap = new HashMap<>();
    private Queue executableQueue = new ConcurrentLinkedQueue<>();
    private Integer size;
    private Long aliveTime;
        
    public WindowController(int limit, Long aliveTime, int poolsize) {
        this.size = limit;
        //this.aliveThread = Executors.newFixedThreadPool(poolsize);
        this.aliveTime = aliveTime;
    }
        
    public void pack(MessageObject msg){
        if(windowMap.get(msg.destID) == null){
            Window window = new Window(this, msg.destID, size);
            windowMap.put(msg.destID, window);
            //aliveThread.execute(new WindowAliveThread(this, window, aliveTime));
        }
        
        windowMap.get(msg.destID).pack(msg);
    }
    
    public void addExecutable(Window window){
        if(!executableQueue.contains(window)){
            executableQueue.add(window);
            windowMap.remove(window.getOrigID());
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
        /*try {
            aliveThread.shutdown();
            if(!aliveThread.awaitTermination(0, TimeUnit.SECONDS))
                aliveThread.shutdownNow();
        } catch (InterruptedException ex) {
            aliveThread.shutdownNow();
        }*/
    }
}