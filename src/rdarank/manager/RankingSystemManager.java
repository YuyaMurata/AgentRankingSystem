/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rda.agent.client.DistributedAgentConnection;

import rdarank.server.DistributedServerConnection;
import rda.manager.TestCaseManager;
import rda.stream.DataStream;
import rda.stream.TimeToDataStream;
import rdarank.agent.all.logger.SystemLogPrinter;

import rdarank.agent.rank.manager.RankAgentManager;
import rdarank.agent.user.manager.UserAgentManager;

/**
 *
 * @author kaeru
 */
public class RankingSystemManager {

    private static RankingSystemManager manager = new RankingSystemManager();

    private RankingSystemManager() {
    }

    public static RankingSystemManager getInstance() {
        return manager;
    }

    //Monitor 
    public void launchMonitor() {
        System.out.println(">>Launch Monitor");
        serverSettings(preServerMap());
        dataSettings(preDataMap(), preProfMap());
        
        //使用時に注意 launchSystemを実行した環境では使用不可
        RankAgentManager rank = RankAgentManager.getInstance();
        rank.initID(preRankAgentMap());
        UserAgentManager user = UserAgentManager.getInstance();
        user.initID(preUserAgentMap());
        
        System.out.println(">>> Finished Set Monitor");
    }

    public void launchSystem() {
        System.out.println(">>Launch System");

        serverSettings(preServerMap());
        //変更予定
        //managerLoggerSettings(preLoggerMap(), new SystemManagerLogPrinter("systemmanager"));
        dataSettings(preDataMap(), preProfMap());
        agentSettings(preUserAgentMap(), preRankAgentMap(), preServerMap());
    }

    public void launchDistSystem() {
        System.out.println(">>Launch System");

        serverSettings(preServerMap());
        //変更予定
        //managerLoggerSettings(preLoggerMap(), new SystemManagerLogPrinter("systemmanager"));
        dataSettings(preDataMap(), preProfMap());
        agentDistSettings(preUserAgentMap(), preRankAgentMap(), preServerMap());
    }

    public void setDataStreamGenerator() {
        streamSettings();
        //Thread Type
        //streamSettings(preStreamMap());
    }

    public void shutdownSystem() {
        //UserAgent Shutdown
        UserAgentManager user = UserAgentManager.getInstance();
        user.doShutdown();

        //RankAgent Shutdown
        RankAgentManager rank = RankAgentManager.getInstance();
        rank.doShutdown();

        System.out.println(">> Shutdown System...");
    }

    private void agentSettings(Map userAgentParam, Map rankAgentParam, Map serverParam) {
        //RankAgent Initialize
        System.out.println("< Initialise RankAgents >");
        RankAgentManager rank = RankAgentManager.getInstance();
        rank.initAgent(rankAgentParam);
        rank.createNumberOfAgents((Integer) rankAgentParam.get("AMOUNT_OF_AGENTS"));

        if (rank.getReserveMode()) {
            List<String> reserveID = new ArrayList<>();
            for (int i = 0; i < (Integer) rankAgentParam.get("AMOUNT_RESERVE_AGENT"); i++) {
                String agentID = rank.createAgent();
                reserveID.add(agentID);
            }
            for (String id : reserveID) {
                rank.getIDManager().setReserveID(id);
            }
        }

        System.out.println(">>> Finished Set RankAgents & IDs");

        //UserAgent Initialize
        //UserAgent Attributed Initialize
        TestCaseManager tcManager = TestCaseManager.getInstance();
        tcManager.profgen.addUserProfileToAgent();
        tcManager.profgen.addUserAgentCommunication();

        System.out.println("< Initialise UserAgents >");
        UserAgentManager user = UserAgentManager.getInstance();
        user.initAgent(userAgentParam);
        user.createNumberOfAgents((Integer) userAgentParam.get("AMOUNT_OF_AGENTS"));

        if (user.getReserveMode()) {
            List<String> reserveID = new ArrayList<>();
            for (int i = 0; i < (Integer) userAgentParam.get("AMOUNT_RESERVE_AGENT"); i++) {
                String agentID = user.createAgent();
                reserveID.add(agentID);
            }
            for (String id : reserveID) {
                user.getIDManager().setReserveID(id);
            }
        }

        System.out.println(">>> Finished Set UserAgents & IDs");
    }

    //Distributed Server
    public void agentDistSettings(Map userAgentParam, Map rankAgentParam, Map serverParam) {
        //RankAgent Initialize
        System.out.println("< Initialise RankAgents >");
        RankAgentManager rank = RankAgentManager.getInstance();
        rank.initAgent(rankAgentParam);
        rank.initNumberOfAgents((Integer) rankAgentParam.get("AMOUNT_OF_AGENTS"));

        //UserAgent Attributed Initialize
        TestCaseManager tcManager = TestCaseManager.getInstance();
        tcManager.profgen.addUserProfileToAgent();
        tcManager.profgen.addUserAgentCommunication();

        //UserAgent Initialize
        System.out.println("< Initialise UserAgents >");
        UserAgentManager user = UserAgentManager.getInstance();
        user.initAgent(userAgentParam);
        user.initNumberOfAgents((Integer) userAgentParam.get("AMOUNT_OF_AGENTS"));
    }

    private void dataSettings(Map dataParam, Map profParam) {
        TestCaseManager tcManager = TestCaseManager.getInstance();
        tcManager.initTestCase(dataParam, profParam);

        System.out.println(">>> Finished Set TestCase");
    }

    private DistributedServerConnection sconn;

    private void serverSettings(Map serverMap) {
        UserAgentManager.getInstance().setServerList(serverMap);
        RankAgentManager.getInstance().setServerList(serverMap);

        //All Server
        sconn = new DistributedServerConnection();
        sconn.setServerList(
                ((String) serverMap.get("SERVER_LIST_USER") + (String) serverMap.get("SERVER_LIST_RANK")),
                (Integer) serverMap.get("POOLSIZE")
        );
    }

    /* 変更予定
    private void managerLoggerSettings(Map loggerMap, AgentLogPrinterTemplate log){
        System.out.println("< Initialize Logger >");
        LoggerManager.getInstance().initLoggerManager(loggerMap);
        LoggerManager.getInstance().setLogPrinter(log);
    }
    
    private void loggerSettings(Map loggerMap) {
        LoggerManager.getInstance().initLoggerManager(loggerMap);
        RankAgentManager.getInstance().setLogger();
        UserAgentManager.getInstance().setLogger();
    }
    
    public void startLogger() {
        LoggerManager.getInstance().startLogger();
    }

    public void stopLogger() {
        LoggerManager.getInstance().stopLogger();
    }
     */
    //DataStream Generator
    private TimeToDataStream timeStream;

    private void streamSettings() {
        this.timeStream = new TimeToDataStream(UserAgentManager.getInstance());
    }

    public TimeToDataStream timeDataStream() {
        return timeStream;
    }

    //Thread Type DataStream Generator
    private DataStream stream;

    private void streamSettings(Map streamMap) {
        this.stream = new DataStream(UserAgentManager.getInstance(), streamMap);
    }

    public DataStream dataStream() {
        return stream;
    }

    //UserAgent Parameter
    private Map preUserAgentMap() {
        //AgentLogPrint.printPropertySettings("UserAgent", props.get("user"));
        return props.get("user");
    }

    //RankAgent Parameter
    private Map preRankAgentMap() {
        //AgentLogPrint.printPropertySettings("RankAgent", props.get("rank"));
        return props.get("rank");
    }

    private Map preProfMap() {
        //AgentLogPrint.printPropertySettings("UserProfile", props.get("profile"));
        return props.get("profile");
    }

    private Map preDataMap() {
        //AgentLogPrint.printPropertySettings("Data", props.get("datagen"));
        return props.get("datagen");
    }

    private Map preStreamMap() {
        //AgentLogPrint.printPropertySettings("Stream", props.get("stream"));
        return props.get("stream");
    }

    private Map preLoggerMap() {
        //AgentLogPrint.printPropertySettings("Logger", props.get("logger"));
        return props.get("logger");
    }

    private Map preServerMap() {
        //AgentLogPrint.printPropertySettings("Server", props.get("server"));
        return props.get("server");
    }

    private HashMap<String, Map> props;

    public void setPropMap(Map map) {
        this.props = (HashMap<String, Map>) map;
    }

    public List<DistributedAgentConnection> getServers() {
        return sconn.getConnectionList();
    }
    
    //１回のみロガー実行
    SystemLogPrinter managerLogger;
    public void loggerMonitorStart(){
        managerLogger = new SystemLogPrinter(System.currentTimeMillis());
    }
    
    public void loggerMonitorStop(){
        managerLogger.printer();
    }
}
