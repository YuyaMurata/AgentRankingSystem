package rdarank.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.client.DistributedAgentConnection;

public class DistributedServerConnection {
    private List serverList;
    public void setServerList(String servers, Integer poolsize){
        Map<String, Integer> server = new HashMap();
        for(String host: servers.split(","))
            server.put(host, poolsize);
        
        serverList = new ArrayList();
        for(String host : server.keySet())
            serverList.add(new DistributedAgentConnection(server.get(host), new String[]{host, "rdarank", "agent"}));
    }
    
    public DistributedAgentConnection getConnection(int index){
        return (DistributedAgentConnection) serverList.get(index);
    }
    
    public List<DistributedAgentConnection> getConnectionList(){
        return serverList;
    }
}
