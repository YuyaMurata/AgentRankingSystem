/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.agent.template;

/**
 *
 * @author kaeru
 */
public abstract class AgentType {
    public abstract void sendMessage(Object data); 
    public abstract String getID();
}
