package rda.agent.user.logger;

import java.io.Serializable;

public class LogInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7199604951543935620L;
	
	public LogInfo() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	/*
	 * <attribute name="UserID" type="string" primarykey="true" relationto="UserID" maxlength="16"/>
	 * <attribute name="AccessID" type="string" primarykey="true" maxlength="16"/>
	 * <attribute name="LastAccessTime" type="timestamp" />
	 */
	String userID;
	long connectCount;
	String accessLog;
	String accessTime;
	
	public LogInfo(String userID, long connectCount, String accessLog, String accessTime) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.userID = userID;
		this.connectCount = connectCount;
		this.accessLog = accessLog;
		this.accessTime = accessTime;
	}

        @Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append(userID);
		sb.append(",");
		sb.append(connectCount);
		sb.append("\n");
		sb.append("Log{");
		sb.append(accessLog);
		sb.append("}");
		sb.append("\n");
		sb.append("Time{");
		sb.append(accessTime);
		sb.append("}");

		return sb.toString();
	}
}
