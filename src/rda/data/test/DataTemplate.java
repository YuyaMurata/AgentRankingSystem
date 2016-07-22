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
public abstract class DataTemplate {
    public String id, toID;
    
    public DataTemplate(String id, String toID) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.id = id;
        this.toID = toID;
    }
    
    public abstract void setData(Object data);
    
    public abstract Object getData();
    
    @Override
    public abstract String toString();
}
