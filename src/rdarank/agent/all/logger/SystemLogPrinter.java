/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.all.logger;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import rda.agent.template.AgentLogPrinterTemplate;
import rda.db.SQLReturnObject;
import rda.log.AgentLogPrint;
import rdarank.agent.all.db.AllAgentDBAccess;

/**
 *
 * @author kaeru
 */
public class SystemLogPrinter  extends AgentLogPrinterTemplate{
    private AllAgentDBAccess db;
    private Long start;

    public SystemLogPrinter(Long start) {
        this.db = new AllAgentDBAccess();
        this.start = start;
    }
    
    //RankAgent Database Transaction
    private void printAgentLifeTime(Long start){
        SQLReturnObject obj = db.query();
        
        Map map = obj.toMap("LifeTime", start);
        System.out.println("> AgentLifeTime:\n"+mapToString(map));
        AgentLogPrint.printAgentTransaction(map);
    }
    
    //String Print Out
    private String mapToString(Map map){
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
    
    @Override
    public void printer() {
        printAgentLifeTime(start);
    }
}
