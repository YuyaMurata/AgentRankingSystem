/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.main;

import rdarank.io.TimerInOut;
import rdarank.manager.LaunchSettingsMap;
import rdarank.manager.RankingSystemManager;

/**
 *
 * @author kaeru
 */
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
