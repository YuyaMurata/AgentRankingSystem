package rda.extension.manager.handler;

import com.ibm.agent.exa.Message;
import com.ibm.agent.exa.MessageHandler;
import rda.extension.manager.SystemManagerExtension;

public class DataGenerateHandler extends MessageHandler{
    private static Long time = 0L;
    
    @Override
    public Object onMessage(Message msg) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
        
        System.out.println("Execute Elapsed Time : "+time+" [sec]");
        
        //DataGenerate
        SystemManagerExtension extension = SystemManagerExtension.getInstance();
        extension.dataGenerate(time++);
        
        return "Execute DataGenerate !";
    }
    
}
