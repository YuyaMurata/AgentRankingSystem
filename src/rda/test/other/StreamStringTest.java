/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author kaeru
 */
public class StreamStringTest {
    public static void main(String[] args) {
        Map map = new HashMap();
        
        List field = new ArrayList();
        List data = new ArrayList();
        
        field.add("a1");field.add("a2");field.add("a3");
        data.add(0L);data.add(1L);data.add(2L);
        
        map.put("Field", field);
        map.put("Data", data);
        
        String s1 = ((List<String>)map.get("Field")).stream().collect(Collectors.joining(","));
        String s2 = ((List<Long>)map.get("Data")).stream().map(n -> n.toString()).collect(Collectors.joining(","));
        
        System.out.println("s1="+s1);
        System.out.println("s2="+s2);
    }
}
