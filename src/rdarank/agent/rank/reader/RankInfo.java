package rdarank.agent.rank.reader;

import java.io.Serializable;

public class RankInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4117317861801053417L;
	
	public RankInfo() {
		// TODO 自動生成されたコンストラクター・スタブ
	}
	
	/*
	 * <entity type="rankagent">
         * <attribute name="RankID" type="string" primarykey="true" maxlength="16"/>
	 * <attribute name="TotalUsers" type="long" />
	 * <attribute name="ConnectionCount" type="long" />
         * </entity>
	*/

	String rankID;
	long totalUsers;
        long count;

	public RankInfo(String rankID, long totalUsers, long count) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.rankID = rankID;
                this.totalUsers = totalUsers;
                this.count = count;
	}

        @Override
	public String toString(){
		StringBuilder sb = new StringBuilder();

		sb.append(rankID);
		sb.append(",");
                sb.append(totalUsers);
                sb.append(",");
                sb.append(count);

		return sb.toString();
	}
        
        public String getID(){
            return this.rankID;
        }
        public Long getUsers(){
            return this.totalUsers;
        }
        public Long getCount(){
            return this.count;
        }

}
