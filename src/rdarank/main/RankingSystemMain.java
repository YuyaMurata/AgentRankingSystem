/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rdarank.main;

import rdarank.manager.RankingSystemManager;

/**
 *
 * @author kaeru
 */
public class RankingSystemMain {
    private static RankingSystemManager manager = RankingSystemManager.getInstance();
    
    public static void main(String[] args) {
        prepare();
        //execute();
        //logger();
        shutdown();
    }
    
    private static void prepare(){
        manager.launchSystem();
    }
    
    private static void logger(){
        manager.startLogger();
    }
    
    private static void execute(){
        manager.dataStream().start();
    }
    
    private static void shutdown(){
        manager.dataStream().stop();
        manager.stopLogger();
        manager.shutdownSystem();
    }
}
