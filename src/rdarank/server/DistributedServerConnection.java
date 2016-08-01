/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.server;

import java.util.ArrayList;
import java.util.List;
import rda.agent.client.DistributedAgentConnection;

/**
 *
 * @author kaeru
 */
public class DistributedServerConnection {
    private static final DistributedServerConnection instance = new DistributedServerConnection();
    
    public static DistributedServerConnection getInstance(){
        return instance;
    }
    
    List serverList = new ArrayList();
    public void setServerList(String servers, Integer poolsize){
        for(String host: servers.split(","))
            serverList.add(new DistributedAgentConnection(poolsize, new String[]{host, "rdarank", "agent"}));
    }
    
    public DistributedAgentConnection getConnection(int index){
        return (DistributedAgentConnection) serverList.get(index);
    }
}
