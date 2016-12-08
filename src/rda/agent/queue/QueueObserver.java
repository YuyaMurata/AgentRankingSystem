package rda.agent.queue;

import java.util.Queue;

public class QueueObserver {
    private String name;
    private final Queue queue;

    public QueueObserver(String name, Queue queue) {
        this.name = name;
        this.queue = queue;
    }
    
    public String getName(){
        return name;
    }
    
    public Integer notifyState(){
        return queue.size();
    }
}