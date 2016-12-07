package rda.manager;

import java.util.Map;
import rda.agent.template.AgentLogPrinterTemplate;
import rda.log.LogSchedule;

public class LoggerManager {
    private static final LoggerManager manager = new LoggerManager();
    private LogSchedule log;
    private LoggerManager(){}
    
    public static LoggerManager getInstance(){
        return manager;
    }
    
    public void initLoggerManager(Map loggerMap){
        this.log = new LogSchedule(loggerMap);
    }
    
    public void setLogPrinter(AgentLogPrinterTemplate logPrinter){
        log.setLogPrinter(logPrinter);
    }
    
    public void startLogger(){
        log.start();
    }
    
    public void stopLogger(){
        log.stop();
    }
    
    public void printTestcaseData(Long time){
        String n = TestCaseManager.getInstance().datagen.toString(time);
        System.out.println("> TestcaseGenData:"+n);
        //AgentLogPrint.printTestcaseData(time.toString(), n);
    }
}