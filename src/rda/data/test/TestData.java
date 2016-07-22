/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.data.test;

/**
 *
 * @author kaeru
 */
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
