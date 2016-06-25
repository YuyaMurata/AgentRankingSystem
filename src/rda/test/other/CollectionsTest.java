/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.other;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public class CollectionsTest {
    public static void main(String[] args) {
        Map map = new HashMap();
        map.put("A", 1);map.put("B", 2);map.put("C", 3);
        List list = new ArrayList(map.keySet());
        
        System.out.println(list.toArray(new Object[list.size()]));
        System.out.println(new ArrayList(map.values()));
        
        Long time = System.currentTimeMillis();
        Timestamp timest = new Timestamp(time);
        System.out.println("Time="+time.toString()+" stamp="+timest);
    }
}
