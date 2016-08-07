/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.all.logger;

import com.ibm.agent.exa.client.AgentClient;
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
        for(DistributedAgentConnection agcon : UserAgentManager.getInstance().getServer().getConnectionList()){
            AgentClient client = agcon.getClient();
            SQLReturnObject obj = lifeTimeDB.query(client);
            agcon.returnConnection(client);
            
            Map map = obj.toMap(agcon.toString()+" - LifeTime", start);
            System.out.println("> AgentLifeTime:\n"+mapToString(map));
            AgentLogPrint.printAgentTransaction(map);
        }
    }
    
    //RankAgent Database LifeTime
    private void printAgentTransaction(){
        for(DistributedAgentConnection agcon : RankAgentManager.getInstance().getServer().getConnectionList()){
            AgentClient client = agcon.getClient();
            SQLReturnObject obj = transactionDB.query(client);
            agcon.returnConnection(client);
            
            Map map = obj.toMap(agcon.toString()+" - Transaction", start);
            System.out.println("> AgentTransaction:\n"+mapToString(map));
            AgentLogPrint.printAgentTransaction(map);
        }
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
