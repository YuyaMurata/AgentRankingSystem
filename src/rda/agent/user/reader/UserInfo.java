package rda.agent.user.reader;

import java.io.Serializable;

public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4117317861801053417L;
	
	public UserInfo() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	/*
	 * <attribute name="UserID" type="string" primarykey="true" relationto="UserID" maxlength="16"/>
	 * <attribute name="Name" type="string" maxlength="32"/>
	 * <attribute name="Sex" type="string" maxlength="2"/>
	 * <attribute name="Age" type="string" maxlength="4"/>
	 * <attribute name="Address" type="string" maxlength="64"/>
	 * <attribute name="LastAccessTime" type="timestamp" />
	*/

	String userID;
	String name;
	String sex;
	String age;
	String address;
	long data;
        long count;

	public UserInfo(String userID, String name, String sex, String age, String address, long data, long count) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.userID = userID;
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.address = address;
		this.data = data;
                this.count = count;
	}

        @Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append(userID);
		sb.append(",");
		sb.append(name);
		sb.append(",");
		sb.append(sex);
		sb.append(",");
		sb.append(age);
		sb.append(",");
		sb.append(address);
		sb.append(",");
		sb.append(data);
                sb.append(",");
                sb.append(count);

		return sb.toString();
	}
        
        public String getID(){
            return this.userID;
        }
        public Long getData(){
            return this.data;
        }
        public Long getCount(){
            return this.count;
        }

}
