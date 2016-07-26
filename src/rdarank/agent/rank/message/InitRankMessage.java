package rdarank.agent.rank.message;

import com.ibm.agent.exa.Message;

/**
 * エージェント初期化を行うメッセージ．
 * 初期化のためのデータをパラメータに持つ
 */
public class InitRankMessage extends Message {
	String mes;
        
	// パラメータをセット
	public void setParams(String mes) {
            this.mes = mes;
	}
}
