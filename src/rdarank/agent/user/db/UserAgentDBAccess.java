/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.agent.user.db;

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
import rda.agent.client.AgentConnection;
import rda.db.DBAccess;
import rda.db.SQLReturnObject;

/**
 *
 * @author kaeru
 */
public class UserAgentDBAccess implements AgentExecutor, Serializable {
    /**
    * 
    */
    private static final long serialVersionUID = -884826433740843048L;

    public UserAgentDBAccess() {
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
    * エージェント実行環境のサーバにて，そのサーバのリージョンに対応する
    * レプリカ用solidDBにJDBCで接続して，AgentDataを得る
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
            stmt = con.prepareStatement("select * from useragent");
            ResultSet rs = stmt.executeQuery();
            
            //IDとDataを取得
            List<Map> result = new ArrayList<>();
            Map tran = new HashMap();
            Map late = new HashMap();
            Map length = new HashMap();
            while(rs.next()){
                tran.put(rs.getString(1), rs.getLong(2));
                late.put(rs.getString(1), rs.getLong(5));
                length.put(rs.getString(1), rs.getLong(6));
            }
            result.add(tran);
            result.add(late);
            result.add(length);
                
                return result;
                
        } catch(Exception e) {
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
    
    public SQLReturnObject query() {        
        try {
            AgentConnection con = AgentConnection.getInstance();
            AgentClient client = con.getConnection();
            
            DBAccess executor = new DBAccess();
            
            Object ret = client.execute(executor);
            Collection<Object> col = (Collection<Object>)ret;
            
            SQLReturnObject sqlResults = new SQLReturnObject();
            List<Map> results = new ArrayList<>();
            for(Object o : col) {
                int i=0;
                for(Map m : (List<Map>)o){
                    if(results.size() <= i) results.add(i, new HashMap());
                    results.get(i).putAll(m);
                    i++;
                }
            }
            
            sqlResults.setResultSet(results);
            
            con.returnConnection(client);
            
            return sqlResults;
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
