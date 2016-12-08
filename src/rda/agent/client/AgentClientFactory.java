package rda.agent.client;

import com.ibm.agent.exa.client.AgentClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class AgentClientFactory extends BasePooledObjectFactory<AgentClient>{
    private final String _catalog, _dataSource, _serverType;
    private final String _user, _password;
    
    public AgentClientFactory(String catalog, String dataSource, String serverType) {
        _catalog = catalog;
        _dataSource = dataSource; 
        _serverType = serverType;
        
        //Default Setting
        _user = "cetaadmin";
        _password = "cetaadmin";
    }
    
    @Override
    public AgentClient create() throws Exception {
        return new AgentClient(_catalog, _user, _password, null, _dataSource, _serverType);
    }

    @Override
    public PooledObject<AgentClient> wrap(AgentClient t) {
        return new DefaultPooledObject<>(t);
    }
    
}
