package rda.agent.user.message;

import com.ibm.agent.exa.Message;
import java.util.List;

public class UpdateUserMessage extends Message{

    //Compound Data
    public List data;

    public void setParams(List data){
        this.data = data;
    }
}
