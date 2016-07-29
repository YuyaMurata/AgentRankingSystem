package rdarank.agent.rank.logger;

import java.io.Serializable;

public class LogInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7199604951543935620L;

    public LogInfo() {
        // TODO 自動生成されたコンストラクター・スタブ
    }

    /*
	 * <attribute name="RankID" type="string" primarykey="true" relationto="RankID" maxlength="16"/>
	 * <attribute name="AccessID" type="string" primarykey="true" maxlength="16"/>
	 * <attribute name="LastAccessTime" type="timestamp" />
     */
    String rankID;
    long connectCount;
    String accessLog;
    String accessTime;

    public LogInfo(String userID, long connectCount, String accessLog, String accessTime) {
        // TODO 自動生成されたコンストラクター・スタブ
        this.rankID = userID;
        this.connectCount = connectCount;
        this.accessLog = accessLog;
        this.accessTime = accessTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(rankID);
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
