package rda.agent.reader;

import java.io.Serializable;

public class AgentInfo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 4117317861801053417L;
	
    public AgentInfo() {
        // TODO 自動生成されたコンストラクター・スタブ
    }
	
    /*
    * <attribute name="AgentID" type="string" primarykey="true" maxlength="32"/>
    * <attribute name="Condition" type="condition" singleentity="true"/>
    * <attribute name="Data" type="long" />
    * <attribute name="ConnectionCount" type="long" />
    * <attribute name="Log" type="log" />
    */

    String id;
    long data;
    long count;

    public AgentInfo(String id, long data, long count) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.id = id;
        this.data = data;
        this.count = count;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append(id);
        sb.append(",");
        sb.append(data);
        sb.append(",");
        sb.append(count);

        return sb.toString();
    }
        
    public String getID(){
        return this.id;
    }
        
    public Long getData(){
        return this.data;
    }
    
    public Long getCount(){
        return this.count;
    }
}
