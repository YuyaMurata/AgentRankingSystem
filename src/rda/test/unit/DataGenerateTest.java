/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import java.util.HashMap;
import java.util.Map;
import rda.agent.queue.MessageObject;
import rda.manager.TestCaseManager;
import rda.test.manager.UnitTestManager;
import rda.test.param.TestParameter;

/**
 *
 * @author kaeru
 */
public class DataGenerateTest extends TestParameter{
    private static Map validate = new HashMap();
    
    public static void main(String[] args) {
        UnitTestManager test = UnitTestManager.getInstance();
        test.prepareManager();
        test.createPseudoAgents();
        
        TestCaseManager tcmanager = TestCaseManager.getInstance();
        tcmanager.initTestCase(dataParam, profParam);
        
        //Data Generate-Test
        MessageObject msg;
        Long msgcnt = 0L;
        long total = 0L;
        long term = TIME_RUN;
        for(long time=0; time < term+1; time++)
        while((msg = tcmanager.datagen.generate(time)) != null){
            //System.out.println("Message:"+msgcnt+":["+msg.toString()+"]");
            msgcnt++;
            
            //Validation (ID & Message Data)
            if(validate.get(msg.destID) == null) validate.put(msg.destID, 0);
            else if((Integer)validate.get(msg.destID) != -1){
                Integer msgValue = (Integer)validate.get(msg.destID) + msg.data;
                validate.put(msg.destID, msgValue);
            }
            
            if(msg.data != -1) total++;
        }
        
        //Output Results
        System.out.println(">>Validation Results:{");
        for(Object id : validate.keySet()){
            System.out.println("    id="+id+"-"+validate.get(id));
        }
        System.out.println("}");
        
        System.out.println("Total:"+total+" - "+tcmanager.datagen.toString());
    }
}