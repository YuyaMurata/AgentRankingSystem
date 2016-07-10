package rda;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.impl.HPAEntity;
import com.ibm.agent.exa.TxID;

/**
 * Generated code for aggregatecondition.
 *
 * <p>entity type="aggregatecondition tablename="aggregatecondition <br>
 * attribute name="AgentID" type="STRING" primarykey="true" relationto="AgentID" <br>
 * attribute name="Conditions" type="STRING" <br>
 * attribute name="LastAccessTime" type="TIMESTAMP" <br>
**/
public class Aggregatecondition extends HPAEntity {
    /**
    * Primary key size
    **/
    public static final int PKINDEXSIZE = 1;

    /**
    * Primary key index of AgentID
    **/
    public static final int PKINDEX_AGENTID = 0;

    /**
    * Column index of AgentID
    **/
    public static final int AGENTID = 0;

    /**
    * Column index of Conditions
    **/
    public static final int CONDITIONS = 1;

    /**
    * Column index of LastAccessTime
    **/
    public static final int LASTACCESSTIME = 2;

    /**
     * This constructor is used by the runtime.
     * An application should not create an instance with this constructor
    **/
    public Aggregatecondition() {
        super();
    }

    /**
     * Get the version string
    **/
    public String getVersion() {
        return "rda1.0";
    }

    /**
     * Get a value of AgentID. 
     * The setter method of AgentID is not generated because this attribute is a primarykey. 
     * @return AgentID
     **/
    public final String getAgentID(TxID tx) {
        // generated code
        return getString(tx,0);
    }

    /**
     * @return Conditions
     **/
    public final String getConditions(TxID tx) {
        // generated code
        return getString(tx,1);
    }

    /**
     * Set a value to Conditions. 
     * @param tx a transaction context
     * @param value a value to be set to Conditions
     **/
    public final void  setConditions(TxID tx, String value) throws AgentException {
        // generated code
        if (value != null && value.length() > 32) {
            throw new AgentException("Conditions > maxlength(32)");
        }
        setString(tx,1,value);
    }

    /**
     * @return LastAccessTime
     **/
    public final java.sql.Timestamp getLastAccessTime(TxID tx) {
        // generated code
        return getTimestamp(tx,2);
    }

    /**
     * Set a value to LastAccessTime. 
     * @param tx a transaction context
     * @param value a value to be set to LastAccessTime
     **/
    public final void  setLastAccessTime(TxID tx, java.sql.Timestamp value) throws AgentException {
        // generated code
        setTimestamp(tx,2,value);
    }

}
