/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import rda.agent.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public class MessageObjectTest {
    public static void main(String[] args) {
        MessageObject msg = new MessageObject("Test", "Target", 1, 0);
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
        System.out.println(">MSG_Late="+msg.latency());
    }
}
