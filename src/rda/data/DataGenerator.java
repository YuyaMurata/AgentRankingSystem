package rda.data;

import rda.agent.queue.MessageObject;


public class DataGenerator {
    private DataType type;
    
    public DataGenerator(DataType type) {
        this.type = type;
    }
    
    public MessageObject generate(Long time){
        return type.nextData(time);
    }
    
    public String getName(){
        return type.getName();
    }
    
    @Override
    public String toString(){
        return type.toString();
    }
    
    public String toString(Long time){
        return type.toString(time);
    }
}