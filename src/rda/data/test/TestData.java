package rda.data.test;

public class TestData extends DataTemplate{
    
    public int data;

    public TestData(String id, String toID) {
        super(id, toID);
    }
    
    @Override
    public void setData(Object data) {
        this.data = (int) data;
    }

    @Override
    public String toString() {
        return "ID="+id+" Dstination="+toID+" value="+data;
    }

    @Override
    public Object getData() {
        return data;
    }
    
}
