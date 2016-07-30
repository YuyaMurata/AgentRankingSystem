/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.intaraction;

import rda.extension.manager.SystemManagerExtension;
import rda.window.Window;

/**
 *
 * @author kaeru
 */
public class AgentIntaractionThread extends Thread {

    private static final String name = "AgentIntaraction Thread";
    private static final AgentIntaractionExtension extension = AgentIntaractionExtension.getInstance();

    @Override
    public void run() {
        System.out.println(name + " Start !");

        while (SystemManagerExtension.getInstance().getState()) {
            Object window = extension.getWindowController().get();
            if (window == null) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException ex) {
                }

            } else {
                //System.out.println("Transport Window ! wsize=" + ((Window) window).getSize());
                extension.transport(window);
                extension.getWindowController().remove();
            }
        }
        
        extension.getWindowController().close();
        System.out.println(name + " Stop !");

    }
}
