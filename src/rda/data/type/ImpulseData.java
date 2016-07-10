package rda.data.type;

import rda.data.Data;
import rda.data.DataType;
import rda.data.test.TestData;

public class ImpulseData implements DataType{
    private static final Long impulse = 1000000L;
    private static final Long burst = 10L;
    
    private static Long count;
    private final Data data;
    private final String name;
    
    private Long term, period, volume;

    public ImpulseData(long time, long period, long volume, int numberOfUser, int valueOfUser, int datamode, long seed) {
        this.name = "ImpulseType";
        this.data = new Data();
        
        this.term = time;
        this.volume = (time+1) * time * volume / 2 / (time / burst + 1) + 1;
        this.period = period;
        
        //initialise
        data.init(numberOfUser, valueOfUser, datamode, seed);
        count = -1L;
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString(){
        Long n = (term / burst + 1) * 1000 / period;
        Long result = n * volume;
        
        return name + " DataN_" + result;
    }

    @Override
    public TestData nextData(Long time) {
        if((time % burst != 0) && (count == -1)) count = volume.longValue()-1;
        count++;
        
        TestData test = (TestData) data.getData();
        
        if(count == volume.longValue()) test = (TestData) data.getPoison();
        if(count > volume.longValue()) {
            test = null;
            count = -1L;
        }
        
        return test;
    }

    @Override
    public String toString(Long time) {
        if(time == -1L) return toString();
        if((time % burst) == 0) return String.valueOf(volume);
        else return String.valueOf(0);
    }
}