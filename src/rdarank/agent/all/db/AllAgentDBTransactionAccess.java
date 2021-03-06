package rdarank.agent.all.db;

import com.ibm.agent.exa.AgentManager;
import com.ibm.agent.exa.client.AgentClient;
import com.ibm.agent.exa.client.AgentExecutor;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import rda.db.SQLReturnObject;

public class AllAgentDBTransactionAccess implements AgentExecutor, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1826433740843048L;

    public AllAgentDBTransactionAccess() {
    }

    @Override
    /**
     * 各リージョンのAgentDataのコレクションを返す
     */
    public Object complete(Collection<Object> results) {
        return results;
    }

    @Override
    /**
     * エージェント実行環境のサーバにて，そのサーバのリージョンに対応する レプリカ用solidDBにJDBCで接続して，AgentDataを得る
     */
    public Object execute() {
        Connection con = null;
        PreparedStatement stmt = null;
        try {
            // このサーバのリージョン名を取得する
            AgentManager agentManager = AgentManager.getAgentManager();
            String regionName = agentManager.getRegionName();

            // JDBCドライバの接続パラメータにリージョン名を指定する
            Properties props = new Properties();
            props.put("region", regionName);

            // JDBC接続を得る
            con = DriverManager.getConnection("jdbc:ceta:rdarank", props);

            // AgentDataを得るSQLを生成し，検索を行う．
            stmt = con.prepareStatement("select * from ranktable");
            ResultSet rs = stmt.executeQuery();

            //IDとDataを取得
            List<Map> result = new ArrayList<>();
            Map log = new HashMap();
            while (rs.next()) {
                if(log.get(rs.getString(2)) != null)
                    log.put(rs.getString(2), Math.max((Long)log.get(rs.getString(2)), rs.getLong(4)));
                else 
                    log.put(rs.getString(2), rs.getLong(4));
            }

            result.add(log);

            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public SQLReturnObject query(AgentClient client) {
        try {
            AllAgentDBTransactionAccess executor = new AllAgentDBTransactionAccess();

            Object ret = client.execute(executor);
            Collection<Object> col = (Collection<Object>) ret;

            SQLReturnObject sqlResults = new SQLReturnObject();
            List<Map> results = new ArrayList<>();

            for (Object o : col) {
                int i = 0;
                for (Map m : (List<Map>) o) {
                    if (results.size() <= i) {
                        results.add(i, new HashMap());
                    }
                    results.get(i).putAll(m);
                    i++;
                }
            }

            sqlResults.setResultSet(results);
            
            return sqlResults;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
