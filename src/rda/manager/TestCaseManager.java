package rda.manager;

import java.util.Map;
import rda.data.DataGenerator;
import rda.data.profile.ProfileGenerator;
import rda.data.type.FlatData;
import rda.data.type.ImpulseData;
import rda.data.type.MountData;

public class TestCaseManager{
    private static TestCaseManager manager = new TestCaseManager();
    public ProfileGenerator profgen;
    public DataGenerator datagen;
    
    
    private TestCaseManager(){}
    public static TestCaseManager getInstance(){
        return manager;
    }
    
    public void initTestCase(Map dataParam, Map profParam){
        initProfile(profParam);
        initData(dataParam);
    }
    
    private void initProfile(Map profParam){
        profgen = ProfileGenerator.getInstance();
        profgen.initProfile(
                (Integer) profParam.get("AMOUNT_USERS"),
                (Integer) profParam.get("MODE"),
                (String)  profParam.get("RULE"),
                (Long)    profParam.get("SEED")
        );
    }
    
    private Long count;
    public void debugTestGenerateCounts(Long count){
        this.count = count;
    }
    
    public Long checkTestGenerateCounts(){
        return count;
    }
    
    private void initData(Map dataParam){
        FlatData ftype = new FlatData(
                (Long)    dataParam.get("TIME_RUN"), 
                (Long)    dataParam.get("TIME_PERIOD"), 
                (Integer) dataParam.get("DATA_VOLUME"), 
                (Integer) dataParam.get("AMOUNT_USER"), 
                (Integer) dataParam.get("AGENT_DEFAULT_VALUE"),
                (Integer) dataParam.get("MODE"),
                (Long)    dataParam.get("SEED")
        );
        
        MountData mtype = new MountData(
                (Long)    dataParam.get("TIME_RUN"), 
                (Long)    dataParam.get("TIME_PERIOD"), 
                (Integer) dataParam.get("DATA_VOLUME"), 
                (Integer) dataParam.get("AMOUNT_USER"), 
                (Integer) dataParam.get("AGENT_DEFAULT_VALUE"),
                (Integer) dataParam.get("MODE"),
                (Long)    dataParam.get("SEED")
        );
        
        ImpulseData itype = new ImpulseData(
                (Long)    dataParam.get("TIME_RUN"), 
                (Long)    dataParam.get("TIME_PERIOD"), 
                (Integer) dataParam.get("DATA_VOLUME"), 
                (Integer) dataParam.get("AMOUNT_USER"), 
                (Integer) dataParam.get("AGENT_DEFAULT_VALUE"),
                (Integer) dataParam.get("MODE"),
                (Long)    dataParam.get("SEED")
        );
        
        switch((Integer) dataParam.get("SELECT_TYPE")){
            case 0 : datagen = new DataGenerator(ftype); break;
            case 1 : datagen = new DataGenerator(mtype); break;
            case 2 : datagen = new DataGenerator(itype); break;
        }
    }
}