/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.all.logger;

import com.ibm.agent.exa.client.AgentClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import rda.agent.client.DistributedAgentConnection;
import rda.agent.template.AgentLogPrinterTemplate;
import rda.db.SQLReturnObject;
import rda.log.AgentLogPrint;
import rdarank.agent.all.db.AllAgentDBLifetimeAccess;
import rdarank.agent.all.db.AllAgentDBTransactionAccess;
import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;

/**
 *
 * @author kaeru
 */
public class SystemLogPrinter  extends AgentLogPrinterTemplate{
    private AllAgentDBLifetimeAccess lifeTimeDB;
    private AllAgentDBTransactionAccess transactionDB;
    private Long start;

    public SystemLogPrinter(Long start) {
        this.lifeTimeDB = new AllAgentDBLifetimeAccess();
        this.transactionDB = new AllAgentDBTransactionAccess();
        this.start = start;
    }
    
    //UserAgent Database LifeTime
    private void printAgentLifeTime(Long start){
        //Initalize
        Map allMap = new HashMap();
        allMap.put("Field", new ArrayList<>());
        allMap.put("Data", new ArrayList<>());
        
        //Get AgentData
        for(DistributedAgentConnection agcon : UserAgentManager.getInstance().getServer().getConnectionList()){
            AgentClient client = agcon.getClient();
            SQLReturnObject obj = lifeTimeDB.query(client);
            agcon.returnConnection(client);
            
            Map map = obj.toMap(agcon.toString()+" - LifeTime", start);
            meargeMap(allMap, map);
        }
        
        System.out.println("> AgentLifeTime:\n"+mapToString(allMap));
        AgentLogPrint.printAgentTransaction(allMap);
    }
    
    //RankAgent Database LifeTime
    private void printAgentTransaction(){
        //Initalize
        Map<String, List> allMap = new HashMap();
        allMap.put("Field", new ArrayList<String>());
        allMap.put("Data", new ArrayList<String>());
        
        for(DistributedAgentConnection agcon : RankAgentManager.getInstance().getServer().getConnectionList()){
            AgentClient client = agcon.getClient();
            SQLReturnObject obj = transactionDB.query(client);
            agcon.returnConnection(client);
            
            Map map = obj.toMap(agcon.toString()+" - Transaction", 0);
            meargeMap(allMap, map);
        }
        
        //Totalの再集計
        List<String> field = allMap.get("Field");
        List<Long> data = allMap.get("Data");
        List<String> newField = new ArrayList<>();
        List<Long> newData = new ArrayList<>();
        Long total = 0L;
        for(int i=0; i <  field.size(); i++){
            if(field.get(i).contains("Total")){
                total = total + data.get(i);
            }else{
                newField.add(field.get(i));
                newData.add(data.get(i));
            }
        }
        newField.add("TOTAL");
        newData.add(total);
        allMap.put("Field",newField);
        allMap.put("Data", newData);
        
        
        System.out.println("> AgentTransaction:\n"+mapToString(allMap));
        AgentLogPrint.printAgentTransaction(allMap);
    }
    
    private Map meargeMap(Map mearge, Map map){
        for(Object key : map.keySet()){
            if(((String)key).contains("Place")) continue;
            
            List list = (List) mearge.get(key);
            list.addAll((List)map.get(key));
            mearge.put(key, list);
        }
        
        return mearge;
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
        printAgentTransaction();
    }
}
