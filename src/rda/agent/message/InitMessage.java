package rda.agent.message;

import com.ibm.agent.exa.Message;

/**
 * エージェント初期化を行うメッセージ．
 * 初期化のためのデータをパラメータに持つ
 */
public class InitMessage extends Message {
	
    // 集約条件
    public String condition;
	
    // パラメータをセット
    public void setParams(String condition) {
        this.condition = condition;
    }
}
