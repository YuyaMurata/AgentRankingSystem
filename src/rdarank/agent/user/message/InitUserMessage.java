package rdarank.agent.user.message;

import com.ibm.agent.exa.Message;

/**
 * エージェント初期化を行うメッセージ．
 * 初期化のためのデータをパラメータに持つ
 */
public class InitUserMessage extends Message {
	
	// 名前
	public String name;
	// 性別
	public String sex;
	// 年齢
	public String age;
	// 住所
	public String address;
	
	// パラメータをセット
	public void setParams(String name, String sex, String age, String address) {
		this.name = name;
		this.sex = sex;
		this.age = age;
		this.address = address;
	}
}
