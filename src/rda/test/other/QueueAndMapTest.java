/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.other;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author kaeru
 */
public class QueueAndMapTest {
    public static void main(String[] args) {
        Queue q = new ArrayDeque();
        Map map = new HashMap();
        
        List l = new ArrayList();
        l.add("Test");
        map.put("test", l);
        
        q.add(map.get("test"));
        map.remove("test");
        
        for(Object m : q)
            System.out.println("Fore-Each:"+(List)m);
        
        System.out.println("Check:"+map.get("test")+" -QS="+q.poll()+" -List="+l);
        
    }
}
