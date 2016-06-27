/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.other;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author kaeru
 */
public class BlovkingQueueTest {
    public static boolean endflg = false;
    public static void main(String[] args) {
        BlockingQueue q = new ArrayBlockingQueue(3);
        Thread p = new Procedure(q);
        Thread c = new Consumer(q);
        
        p.start();
        c.start();
        
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
        }
        
        endflg = true;
    }
}

class Procedure extends Thread{
    private final BlockingQueue q;
    private Long time;
    public Procedure(BlockingQueue q) {
        this.q = q;
        this.time = 0L;
    }
    
    @Override
    public void run(){
        while(!BlovkingQueueTest.endflg){
            System.out.println("Procedure:"+q.size() + " time="+time);
            q.offer(time++);
        
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }
}

class Consumer extends Thread{
    private final BlockingQueue q;
    public Consumer(BlockingQueue q) {
        this.q = q;
    }
    
    @Override
    public void run(){
        while(!BlovkingQueueTest.endflg){
            System.out.println("Consumer:"+q.size() + " value="+q.poll());
        
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
            }
        }
    }
}
