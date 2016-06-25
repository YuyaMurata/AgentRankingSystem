/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.property;

/**
 *
 * @author kaeru
 */
public class RewriteProperty {
    public static void main(String[] args){
        String number = "5";
        if(args.length == 1)
            number = args[0];
        
        Property prop = new Property(false);
        Integer beforeNumAgents = Integer.valueOf(prop.getValue("agent", "number.rank.agent"));
        Integer beforeNumQueues  = Integer.valueOf(prop.getValue("queue", "number.queue"));
        
        
        System.out.println("Change Properties :");
                
        prop.setValue("agent", "number.rank.agent", number);
        System.out.println("agent : number.rank.agent ("+beforeNumAgents+" -> "+prop.getValue("agent", "number.rank.agent")+")");
             
        prop.setValue("queue", "number.queue", number);
        System.out.println("queue : number.queue ("+beforeNumQueues+" -> "+prop.getValue("queue", "number.queue")+")");
        
        //Store
        prop.storePropeties();
    }
}
