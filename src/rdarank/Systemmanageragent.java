package rdarank;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.impl.HPAEntity;
import com.ibm.agent.exa.TxID;

/**
 * Generated code for systemmanageragent.
 *
 * <p>entity type="systemmanageragent tablename="systemmanageragent <br>
 * attribute name="ID" type="STRING" primarykey="true" <br>
**/
public class Systemmanageragent extends HPAEntity {
    /**
    * Primary key size
    **/
    public static final int PKINDEXSIZE = 1;

    /**
    * Primary key index of ID
    **/
    public static final int PKINDEX_ID = 0;

    /**
    * Column index of ID
    **/
    public static final int ID = 0;

    /**
     * This constructor is used by the runtime.
     * An application should not create an instance with this constructor
    **/
    public Systemmanageragent() {
        super();
    }

    /**
     * Get the version string
    **/
    public String getVersion() {
        return "rdarank1.0";
    }

    /**
     * Get a value of ID. 
     * The setter method of ID is not generated because this attribute is a primarykey. 
     * @return ID
     **/
    public final String getID(TxID tx) {
        // generated code
        return getString(tx,0);
    }

}
