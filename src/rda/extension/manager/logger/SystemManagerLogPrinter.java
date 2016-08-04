/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.extension.manager.logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import rda.agent.template.AgentLogPrinterTemplate;
import rda.log.AgentLogPrint;
import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;

/**
 *
 * @author kaeru
 */
public class SystemManagerLogPrinter extends AgentLogPrinterTemplate{
    private String agenttype;
    
    public SystemManagerLogPrinter(String agenttype) {
        this.agenttype = agenttype;
    }
    
    //UserAgent MessageQueue Status
    private void printUserAgentObserver(){
        UserAgentManager manager = UserAgentManager.getInstance();
        StringBuilder place = new StringBuilder("MessageQueue");
        List field = new ArrayList();
        List data = new ArrayList();
        Map map = new HashMap();
        
        for(int i=0; i < manager.getObserver().size(); i++){
            place.append(",{}");
            field.add(manager.getObserver().get(i).getName());
            data.add(manager.getObserver().get(i).notifyState().longValue());
        }
        
        map.put("Place", place.toString());
        map.put("Field", field);
        map.put("Data", data);
        
        //MessageQueue Observer QueueLength
        System.out.println("> UserAgent : QueueLength \n"+mapToString(map));
        //AgentLogPrint.printMessageQueueLog(map);
        
        //Agent Inner QueueLength
        //SQLReturnObject obj = db.query();
        //Map map = obj.toMap("Length", 2);
        //System.out.println("> QueueLength:\n"+obj.toString(map));
    }
    
    //RankAgent MessageQueue Status
     private void printRankAgentObserver(){
        RankAgentManager manager = RankAgentManager.getInstance();
        StringBuilder place = new StringBuilder("MessageQueue");
        List field = new ArrayList();
        List data = new ArrayList();
        Map map = new HashMap();
        
        for(int i=0; i < manager.getObserver().size(); i++){
            place.append(",{}");
            field.add(manager.getObserver().get(i).getName());
            data.add(manager.getObserver().get(i).notifyState().longValue());
        }
        
        map.put("Place", place.toString());
        map.put("Field", field);
        map.put("Data", data);
        
        //MessageQueue Observer QueueLength
        System.out.println("> RankAgent : QueueLength\n"+mapToString(map));
        //AgentLogPrint.printMessageQueueLog(map);
        
        //Agent Inner QueueLength
        //SQLReturnObject obj = db.query();
        //Map map = obj.toMap("Length", 2);
        //System.out.println("> QueueLength:\n"+obj.toString(map));
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
        System.out.println(agenttype+" - Log Printer : ");
        
        System.out.println("> useragent : ");
        printUserAgentObserver();
        
        //System.out.println("> rankagent : ");
        //printRankAgentObserver();
    }
    
}
