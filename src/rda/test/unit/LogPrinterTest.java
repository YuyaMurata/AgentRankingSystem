/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.log.AgentSystemLogger;

/**
 *
 * @author kaeru
 */
public class LogPrinterTest {
    public static void main(String[] args) {
        AgentSystemLogger log = AgentSystemLogger.getInstance();
        
        Map<String, Integer> map = new HashMap();
        map.put("A", 1); map.put("B", 2);  map.put("C", 3);
        
        log.print(log.titleMarker, "Test {},{},{}", map.keySet().toArray(new Object[map.keySet().size()]));
    }
}
