package rda.agent.user.message;

import com.ibm.agent.exa.Message;
import java.util.List;

public class UpdateUserMessage extends Message{

	public List data;

	public void setParams(List data){
		this.data = data;
	}
}
