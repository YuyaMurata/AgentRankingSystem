package rda.property;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Property {
    public Property(Boolean display) {
        // TODO 自動生成されたコンストラクター・スタブ
        loadPropertyALL();
        if(display) getValueALL();
    }

    private static final Properties server = new Properties();
    private static final Properties queue = new Properties();
    private static final Properties agent = new Properties();
    private static final Properties log = new Properties();

    //Load Property
    private void load(Properties prop, String filename){
        System.out.println("* Load " + filename);

        try{
            prop.load(getClass().getResourceAsStream(filename));
        }catch(IOException e){
        }
    }

    //Server Property
    private void loadServerProperty(){
        String filename = "/server.properties";
        load(server, filename);
    }

    //Queue Property
    private void loadQueueProperty(){
        String filename = "/queue.properties";
        load(queue, filename);
    }

    //Agent Property
    private void loadAgentProperty(){
        String filename = "/agent.properties";
        load(agent, filename);
    }
    
    //Agent Property
    private void loadLogProperty(){
        String filename = "/log.properties";
        load(log, filename);
    }

    //Get Value
    public String getValue(String prop, String key){
        switch (prop) {
            case "server": return server.getProperty(key);
            case "queue" : return queue.getProperty(key);
            case "agent" : return agent.getProperty(key);
            case "log"   : return log.getProperty(key);
            default      : return null;
        }
    }
    
    public void setValue(String prop, String key, String value){
        switch (prop) {
            case "server": server.setProperty(key, value);
            case "queue" : queue.setProperty(key, value);
            case "agent" : agent.setProperty(key, value);
            case "log"   : log.setProperty(key, value);
            default      : ;
        }
    }
    
    public void storePropeties(){
        try {
            
            String path; //= getClass().getResource("/server.properties").getPath();
            //server.store(new FileOutputStream(path), null);
            
            path = getClass().getResource("/queue.properties").getPath();
            queue.store(new FileOutputStream(path), null);
            
            path = getClass().getResource("/agent.properties").getPath();
            agent.store(new FileOutputStream(path), null);
        } catch (IOException e) {
        }
    }
    
    private void loadPropertyALL(){
        System.out.println("*************** -Load Property File- ***************");
        
        loadServerProperty();
        loadQueueProperty();
        loadAgentProperty();
        loadLogProperty();
    }
    
    private void getValueALL(){
        System.out.println("*************** -Get Property Value- ***************");
        for(Object key: server.keySet())
            System.out.println("* ServerProperty Value : "+(String)key+"="+getValue("server", (String)key));
        System.out.println("* ------------------------------------------------ *");
        
        for(Object key: queue.keySet())
            System.out.println("* QueueProperty Value  : "+(String)key+"="+getValue("queue", (String)key));
        System.out.println("* ------------------------------------------------ *");
        
        for(Object key: agent.keySet())
            System.out.println("* AgentProperty Value  : "+(String)key+"="+getValue("agent", (String)key));
        System.out.println("* ------------------------------------------------ *");
        
        for(Object key: log.keySet())
            System.out.println("* LogProperty Value  : "+(String)key+"="+getValue("log", (String)key));
        System.out.println("****************************************************");
    }
    
    public static void main(String args[]){
        Property prop = new Property(true);
    }
}
