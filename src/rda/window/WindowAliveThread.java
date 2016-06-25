/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.window;

/**
 *
 * @author kaeru
 */
public class WindowAliveThread implements Runnable{
    private WindowController manager;
    private Window window;
    private Long time;
    public WindowAliveThread(WindowController manager, Window window, Long time) {
        this.manager = manager;
        this.window = window;
        this.time = time;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(time);
            manager.addExecutable(window);
        } catch (InterruptedException ex) {
        }
    }
}
