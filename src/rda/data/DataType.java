package rda.data;

import rda.data.test.TestData;

public interface DataType{
    public abstract TestData nextData(Long time);
    public abstract String getName();
    
    @Override
    public abstract String toString();
    
    public abstract String toString(Long time);
}