package rda.data;

import rda.data.test.TestData;


public class DataGenerator {
    private DataType type;
    
    public DataGenerator(DataType type) {
        this.type = type;
    }
    
    public TestData generate(Long time){
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