package rda.data.type;

import rda.agent.queue.MessageObject;
import rda.data.Data;
import rda.data.DataType;

public class MountData implements DataType{
    private final String name;
    private final Data data;

    private static Long count;
    
    private Long term, period;
    private Integer volume;
    
    public MountData(Long time, Long period, int volume, int numberOfUser, int valueOfUser, int datamode, long seed) {
        this.name = "MountType";
        this.data = new Data();
        
        this.term = time;
        this.period = period;
        this.volume = volume;
        
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
        Long n = (term+1) * 1000 / period;
        Long result = n * (n-1) / 2 * volume;
        
        return name + " DataN_" + result;
    }

    @Override
    public MessageObject nextData(Long time) {
        count++;
        
        MessageObject msg = data.getData();
        
        if(count == (time * volume)) msg = data.getPoison();
        if(count > (time * volume)) {
            msg = null;
            count = -1L;
        }
        
        return msg;
    }

    @Override
    public String toString(Long time) {
        if(time == -1L) return toString();
        return String.valueOf(time * volume);
    }
}