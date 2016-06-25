package rda;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.impl.HPAEntity;
import com.ibm.agent.exa.TxID;

/**
 * Generated code for log.
 *
 * <p>entity type="log tablename="log <br>
 * attribute name="UserID" type="STRING" primarykey="true" relationto="UserID" <br>
 * attribute name="AccessID" type="STRING" primarykey="true" <br>
 * attribute name="CurrentTime" type="LONG" <br>
 * attribute name="LastAccessTime" type="TIMESTAMP" <br>
**/
public class Log extends HPAEntity {
    /**
    * Primary key size
    **/
    public static final int PKINDEXSIZE = 2;

    /**
    * Primary key index of UserID
    **/
    public static final int PKINDEX_USERID = 0;

    /**
    * Primary key index of AccessID
    **/
    public static final int PKINDEX_ACCESSID = 1;

    /**
    * Column index of UserID
    **/
    public static final int USERID = 0;

    /**
    * Column index of AccessID
    **/
    public static final int ACCESSID = 1;

    /**
    * Column index of CurrentTime
    **/
    public static final int CURRENTTIME = 2;

    /**
    * Column index of LastAccessTime
    **/
    public static final int LASTACCESSTIME = 3;

    /**
     * This constructor is used by the runtime.
     * An application should not create an instance with this constructor
    **/
    public Log() {
        super();
    }

    /**
     * Get the version string
    **/
    public String getVersion() {
        return "rda1.0";
    }

    /**
     * Get a value of UserID. 
     * The setter method of UserID is not generated because this attribute is a primarykey. 
     * @return UserID
     **/
    public final String getUserID(TxID tx) {
        // generated code
        return getString(tx,0);
    }

    /**
     * Get a value of AccessID. 
     * The setter method of AccessID is not generated because this attribute is a primarykey. 
     * @return AccessID
     **/
    public final String getAccessID(TxID tx) {
        // generated code
        return getString(tx,1);
    }

    /**
     * @return CurrentTime
     **/
    public final long getCurrentTime(TxID tx) {
        // generated code
        return getLong(tx,2);
    }

    /**
     * Set a value to CurrentTime. 
     * @param tx a transaction context
     * @param value a value to be set to CurrentTime
     **/
    public final void  setCurrentTime(TxID tx, long value) throws AgentException {
        // generated code
        setLong(tx,2,value);
    }

    /**
     * @return LastAccessTime
     **/
    public final java.sql.Timestamp getLastAccessTime(TxID tx) {
        // generated code
        return getTimestamp(tx,3);
    }

    /**
     * Set a value to LastAccessTime. 
     * @param tx a transaction context
     * @param value a value to be set to LastAccessTime
     **/
    public final void  setLastAccessTime(TxID tx, java.sql.Timestamp value) throws AgentException {
        // generated code
        setTimestamp(tx,3,value);
    }

}
