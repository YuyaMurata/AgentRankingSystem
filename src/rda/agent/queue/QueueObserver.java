/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.queue;

import java.util.Queue;

/**
 *
 * @author 悠也
 */
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