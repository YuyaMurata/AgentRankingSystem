/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author kaeru
 */
public class SQLReturnObject {
    private List<Map> results = new ArrayList<>();
    
    public void setResultSet(List res){
        this.results.addAll(res);
    }
    
    public Map toMap(String str, int index){
        StringBuilder place = new StringBuilder(str);
        List field = new ArrayList(results.get(index).keySet());
        List data = new ArrayList(results.get(index).values());
        Map map = new HashMap();
        
        if(!str.contains("Latency")){
            field.add("Total");
            Long total = 0L;
            for(Long n : (List<Long>)data)
                total = total + n;
            data.add(total);
        }
        
        for(int i=0; i < field.size(); i++)
            place.append(",{}");
        
        map.put("Place", place.toString());
        map.put("Field", field);
        map.put("Data", data);
        
        return map;
    }
    
    //log
    public Map toMap(String str, Long time){
        StringBuilder place = new StringBuilder("AgentLifeTime");
        List field = new ArrayList(results.get(0).keySet());
        List data = new ArrayList(results.get(0).values());
        Map map = new HashMap();
        
        for(int i=0; i < data.size(); i++)
            data.set(i, (Long)data.get(i) - time);
        
        for(int i=0; i < field.size(); i++)
            place.append(",{}");
        
        map.put("Place", place.toString());
        map.put("Field", field);
        map.put("Data", data);
        
        return map;
    }
    
    public String toString(Map map){
        StringBuilder sb = new StringBuilder();
        //sb.append((String)map.get("Place"));
        //sb.append("\n");
        
        String s1 = ((List<String>)map.get("Field")).stream()
                        .collect(Collectors.joining(","));
        sb.append(s1);
        sb.append("\n");
        
        String s2 = ((List<Long>)map.get("Data")).stream()
                        .map( n -> n.toString())
                        .collect(Collectors.joining(","));
        
        sb.append(s2);
        
        return sb.toString();
    }
}
