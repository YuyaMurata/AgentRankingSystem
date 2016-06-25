/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data;

import org.apache.commons.math3.random.RandomDataGenerator;
import rda.agent.queue.MessageObject;
import rda.data.profile.ProfileGenerator;

/**
 *
 * @author kaeru
 */
public class Data{
    private static int count = -1;
    private static final RandomDataGenerator rand = new RandomDataGenerator();
    private int numOfUser, value, mode;
    
    private ProfileGenerator prof = ProfileGenerator.getInstance();

    public Data() {}

    //Set All UserID
    public void init(int n, int value, int mode, long seed){
        this.numOfUser = n;
        this.value = value;
        this.mode = mode;
        
        rand.reSeed(seed);
    }
        
    private Integer idNo(){
        switch(mode){
            case 0 : return idSequentialNo();
            case 1 : return idRandomNo();
        }
        return null;
    }
    
    private Integer idSequentialNo(){
        count++;
        if(count == numOfUser) count = 0;
        return count;
    }
    
    private Integer idRandomNo(){
        return rand.nextInt(0, numOfUser-1);
    }

    //Get Data userID = Call % NUMBER_USER_AGENTS
    public MessageObject getData(){
        String uid = prof.getUser(idNo());
        return new MessageObject(uid , value, (String)prof.getProf(uid).get("TargetID"));
    }
    
    public MessageObject getPoison(){
        String uid = prof.getUser(idNo());
        return new MessageObject(uid, -1, (String)prof.getProf(uid).get("TargetID"));
    }
    
}