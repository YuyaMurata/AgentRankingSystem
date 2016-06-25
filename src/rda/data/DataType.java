/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data;

import rda.agent.queue.MessageObject;

/**
 *
 * @author kaeru
 */
public interface DataType{
    public abstract MessageObject nextData(Long time);
    public abstract String getName();
    
    @Override
    public abstract String toString();
    
    public abstract String toString(Long time);
}