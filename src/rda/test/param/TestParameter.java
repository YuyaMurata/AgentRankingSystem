/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.test.param;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author kaeru
 */
public abstract class TestParameter {
    //Experiment Condition
    public static final int NUMBER_OF_USER = 100000;
    public static final int NUMBER_OF_AGENTS = 10;
    public static final int NUMBER_DATA = 100000;
    public static final Long TIME_RUN = 100L;
    public static final Long TIME_PERIOD = 1000L;
    public static final Long TIME_WAIT = 10L;
    public static final int VOLUME = 10000;
    public static final int VALUE = 1;
    public static final int NUMBER_OF_QUEUE = 1;
    public static final int QUEUE_LENGTH = 1000;
    public static final int SELECT_DATA_TYPE = 0;
    public static final int WINDOW_SIZE = 100;
    public static final int MODE = 0;
    public static final int PROFILE_MODE = 0;
    public static final Long SEED = Long.MAX_VALUE;
    public static final String NAME_RULE = "TEST#";
    
    public static final String AGENT_TYPE = "aggregateagent";
    
    public static final Map profParam = 
        new HashMap<Object, Object>() {{
        put("AMOUNT_USER", NUMBER_OF_USER);
        put("MODE", PROFILE_MODE);
        put("SEED", SEED);
    }};
    
    public static final Map dataParam = 
        new HashMap<Object, Object>() {{
        put("TIME_RUN", TIME_RUN); 
        put("TIME_PERIOD", TIME_PERIOD); 
        put("DATA_VOLUME", VOLUME); 
        put("AMOUNT_USER", NUMBER_OF_USER);
        put("AGENT_DEFAULT_VALUE", VALUE);
        put("MODE", MODE);
        put("SEED", SEED);
        put("SELECT_TYPE", SELECT_DATA_TYPE);
    }};
    
    public static final Map agentMQParam = 
        new HashMap() {{
        put("QUEUE_LENGTH", QUEUE_LENGTH);
        put("QUEUE_WAIT", TIME_WAIT);
        put("AGENT_WAIT", TIME_WAIT);
        put("AGENT_MODE", MODE);
        put("RESERVE_MODE", MODE);
        put("AMOUNT_RESERVE_AGENT", 0);
    }};
    
    public static final Map idParam =
        new HashMap<Object, Object>(){{
        put("RULE", NAME_RULE);
        put("SEED", SEED);
    }};
}
