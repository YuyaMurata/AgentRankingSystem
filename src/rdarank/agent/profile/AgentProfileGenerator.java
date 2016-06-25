/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.profile;

import java.util.HashMap;
import org.apache.commons.math3.random.RandomDataGenerator;

/**
 *
 * @author kaeru
 */
public class AgentProfileGenerator {
    private static final AgentProfileGenerator profgen = new AgentProfileGenerator();
    private AgentProfileGenerator(){}
    private static final RandomDataGenerator rand = new RandomDataGenerator();
    private static Integer mu, sigma;
    
    static {
        mu = 100/2;
        sigma = 100/10;
    }
    
    public static AgentProfileGenerator getInstance(){
        return profgen;
    }
    
    public void initProfile(Integer numberOfAgents, Long seed){
        //Random Parameter
        rand.reSeed(Integer.MAX_VALUE);
        mu = 100/2;
        sigma = 100/10;
    }
    
    private Integer getFlatAge(){
        Integer age = (int) rand.nextInt(1, 100);
        return age;
    }
    
    //AgentのProfileを生成
    public HashMap genAgentProfile(String id) {
        //Store Profile
        HashMap prof = new HashMap<String, String>();
	
        //ID
        prof.put("UserID", id);
        
        //Name
        prof.put("Name", "Name-" + id);
        
        //Sex
        if(rand.nextInt(0, 1) == 0) prof.put("Sex", "M");  
        else prof.put("Sex", "F");
        
        //Age
        prof.put("Age", getFlatAge().toString());
        
        //Address
        prof.put("Address", "Address-" + id);
       
        return prof;
    }
}
