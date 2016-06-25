/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import rda.manager.IDManager;
import rda.test.param.TestParameter;

/**
 *
 * @author kaeru
 */
public class IDGenerateTest extends TestParameter{
    public static void main(String[] args) {
        IDManager id = new IDManager(idParam);
        for(int i=0; i < NUMBER_OF_AGENTS; i++){
            String testID = id.genID();
            id.initRegID(testID);
        }
        
        System.out.println(id.toString());
    }
}
