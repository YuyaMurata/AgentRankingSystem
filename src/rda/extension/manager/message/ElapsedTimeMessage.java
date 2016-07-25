/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.manager.message;

import com.ibm.agent.exa.Message;

/**
 *
 * @author kaeru
 */
public class ElapsedTimeMessage  extends Message{
    //New Communication ID
    public Long time;

    public void setParams(Long time){
        this.time = time;
    }
    
}
