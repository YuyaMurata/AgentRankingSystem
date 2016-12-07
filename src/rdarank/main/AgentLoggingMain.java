package rdarank.main;

import rdarank.io.TimerInOut;
import rdarank.manager.LaunchSettingsMap;
import rdarank.manager.RankingSystemManager;

public class AgentLoggingMain {
    public static void main(String[] args) {
        Long startTime = TimerInOut.readStartTime();
        RankingSystemManager manager = RankingSystemManager.getInstance();
        
        //SetProperty To Map
        LaunchSettingsMap settings = new LaunchSettingsMap();
        
        //Init MonitorSystem Manager
        manager.setPropMap(settings.getPropMap());
        manager.launchMonitor();
        
        manager.loggerMonitorStart(startTime);
        manager.loggerMonitorStop();
    }
}
