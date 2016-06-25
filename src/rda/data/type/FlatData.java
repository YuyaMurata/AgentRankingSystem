/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data.type;

import rda.agent.queue.MessageObject;
import rda.data.Data;
import rda.data.DataType;

/**
 *
 * @author kaeru
 */
public class FlatData  implements DataType{
    private final String name;
    private final Data data;

    private static Long count;
    
    private Long term, period;
    private Long volume;
    
    public FlatData(Long time, Long period, int volume, int numberOfUser, int valueOfUser, int datamode, long seed) {
        this.name = "FlatType";
        this.data = new Data();
        
        this.term = time;
        this.period = period;
        this.volume = (time+1)*volume/2;
        
        //initialise
        data.init(numberOfUser, valueOfUser, datamode, seed);
        
        count = -1L;
    }
    
    @Override
    public MessageObject nextData(Long time) {
        if((time == 0) && (count == -1)) count = volume.longValue()-1; 
        
        count++;
        MessageObject msg = data.getData();
                
        if(count == volume.longValue()) msg = data.getPoison();
        if(count > volume.longValue()) {
            msg = null;
            count = -1L;
        }
        
        return msg;
    }

    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString(){
        Long n = term * 1000 / period;
        Long result = n * volume;
        
        return name + " DataN_" + result;
    }

    @Override
    public String toString(Long time) {
        if(time == -1L) return toString();
        return String.valueOf(volume);
    }
}
