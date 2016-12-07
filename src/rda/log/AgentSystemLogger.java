package rda.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class AgentSystemLogger {
    //Logger SLF4J
    /**
    * AgentSystem_info.log element = Message Queue Length per sec
    * Consle Output Step Time per sec
    * vmstat.log CPU Avairability per sec
    */
    private static final Logger logger = LoggerFactory.getLogger(AgentSystemLogger.class);
    private static final AgentSystemLogger agentSystemLogger = new AgentSystemLogger();
    
    public Marker titleMarker = MarkerFactory.getMarker("title");
    public Marker resultMarker = MarkerFactory.getMarker("result");
    public Marker fieldMarker = MarkerFactory.getMarker("field");
    public Marker dataMarker = MarkerFactory.getMarker("data");
    public Marker stateMarker = MarkerFactory.getMarker("state");
    
    private AgentSystemLogger() {
    }
    
    public static AgentSystemLogger getInstance(){
        return agentSystemLogger;
    }
    
    public void print(Marker marker, String str, Object[] arr){
        //Console Out
        logger.info(marker, str, arr);
    }
    
    public void printMQEvent(Marker marker, String str, Object[] arr){
        //File Out
        logger.trace(marker, str, arr);
        
        //Console Out
        //print(marker, str, arr); 
    }
    
    public void printMQLength(Marker marker, String str, Object[] arr){
        //File Out
        logger.debug(marker, str, arr);
    }
    
    public void printResults(Marker marker, String str, Object[] arr){
        //File & System Out
        logger.warn(marker, str, arr);
    }
}
