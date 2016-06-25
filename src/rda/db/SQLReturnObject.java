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

/**
 *
 * @author kaeru
 */
public class SQLReturnObject {
    private Map results = new HashMap();
    public void setResultSet(Map res){
        this.results.putAll(res);
    }
    
    public Map toMapList(){
        StringBuilder place = new StringBuilder("AgentTransaction");
        List field = new ArrayList(results.keySet());
        List data = new ArrayList(results.values());
        Map map = new HashMap();
        
        field.add("Total");
        Long total = 0L;
        for(Long n : (List<Long>)data)
            total = total + n;
        data.add(total);
        
        for(int i=0; i < field.size(); i++)
            place.append(",{}");
        
        map.put("Place", place.toString());
        map.put("Field", field);
        map.put("Data", data);
        
        return map;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder();
        StringBuilder sbval = new StringBuilder();
        Long total= 0L;
        for(Object id : results.keySet()){
            sb.append(id);
            sb.append(",");
            
            Long value = (Long)results.get(id);
            sbval.append(value);
            sbval.append(",");
            
            total = total + value;
        }
        
        sb.append("Total");
        sbval.append(total);
        
        sb.append("\n");
        sb.append(sbval);
        
        return sb.toString();
    }
    
    //log
    public Map toMapList(Long time){
        StringBuilder place = new StringBuilder("AgentLifeTime");
        List field = new ArrayList(results.keySet());
        List data = new ArrayList(results.values());
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
    
    public String toString(Long time){
        StringBuilder sb = new StringBuilder();
        StringBuilder sbval = new StringBuilder();
        Long total= 0L;
        for(Object id : results.keySet()){
            sb.append(id);
            sb.append(",");
            
            Long value = (Long)results.get(id);
            sbval.append(value-time);
            sbval.append(",");
        }
        
        sb.append("\n");
        sb.append(sbval);
        
        return sb.toString();
    }
}
