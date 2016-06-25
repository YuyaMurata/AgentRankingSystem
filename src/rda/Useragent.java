package rda;

import com.ibm.agent.exa.AgentException;
import com.ibm.agent.exa.impl.HPAEntity;
import com.ibm.agent.exa.TxID;
import com.ibm.agent.exa.entity.EntitySet;
import com.ibm.agent.exa.entity.EntityKey;
import com.ibm.agent.exa.entity.Entity;
import java.util.Iterator;

/**
 * Generated code for useragent.
 *
 * <p>entity type="useragent tablename="useragent <br>
 * attribute name="UserID" type="STRING" primarykey="true" <br>
 * attribute name="Profile" type="profile" <br>
 * attribute name="Data" type="LONG" <br>
 * attribute name="ConnectionCount" type="LONG" <br>
 * attribute name="Log" type="log" <br>
**/
public class Useragent extends HPAEntity {
    /**
    * Primary key size
    **/
    public static final int PKINDEXSIZE = 1;

    /**
    * Primary key index of UserID
    **/
    public static final int PKINDEX_USERID = 0;

    /**
    * Column index of UserID
    **/
    public static final int USERID = 0;

    /**
    * Column index of Profile
    **/
    public static final int PROFILE = 1;

    /**
    * Column index of Data
    **/
    public static final int DATA = 2;

    /**
    * Column index of ConnectionCount
    **/
    public static final int CONNECTIONCOUNT = 3;

    /**
    * Column index of Log
    **/
    public static final int LOG = 4;

    /**
     * This constructor is used by the runtime.
     * An application should not create an instance with this constructor
    **/
    public Useragent() {
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
     * Get a set of Profile. 
     * Entity type of this entity set is profile.
     * A returned entity set has a single entity.
     * The setter method of Profile is not generated because this attribute is a EntitySet. 
     * @return an entity set containing Profile
     * @throws AgentException
     **/
    public final EntitySet getProfileSet(TxID tx) throws AgentException {
        // generated code
        return getEntitySet(tx,1);
    }

    /**
     * Get a value of Profile. 
     * @param tx a transaction context
     * @return Profile
     * @throws AgentException
     **/
    public final Profile getProfile(TxID tx) throws AgentException {
        // generated code
        EntitySet es = getEntitySet(tx,1);
        if (es == null) return null;
        return (Profile)es.getSingleEntity();
    }

    /**
     * Create a value of Profile. 
     * @param tx a transaction context
     * @return Profile
     **/
    public final Profile createProfile(TxID tx) throws AgentException {
        // generated code
        EntitySet es = getEntitySet(tx,1);
        Profile entity = (Profile)es.createEntity(tx,new Object[1]);
        return entity;
    }

    /**
     * @return Data
     **/
    public final long getData(TxID tx) {
        // generated code
        return getLong(tx,2);
    }

    /**
     * Set a value to Data. 
     * @param tx a transaction context
     * @param value a value to be set to Data
     **/
    public final void  setData(TxID tx, long value) throws AgentException {
        // generated code
        setLong(tx,2,value);
    }

    /**
     * @return ConnectionCount
     **/
    public final long getConnectionCount(TxID tx) {
        // generated code
        return getLong(tx,3);
    }

    /**
     * Set a value to ConnectionCount. 
     * @param tx a transaction context
     * @param value a value to be set to ConnectionCount
     **/
    public final void  setConnectionCount(TxID tx, long value) throws AgentException {
        // generated code
        setLong(tx,3,value);
    }

    /**
     * Get a set of Log. 
     * Entity type of this entity set is log.
     * The setter method of Log is not generated because this attribute is a EntitySet. 
     * @return an entity set containing Log
     * @throws AgentException
     **/
    public final EntitySet getLog(TxID tx) throws AgentException {
        // generated code
        return getEntitySet(tx,4);
    }

    /**
     * Get a value of Log. 
     * @param tx a transaction context
     * @param AccessID
     * @return Log
     * @throws AgentException
     **/
    public final Log getLog(TxID tx,String AccessID) throws AgentException {
        // generated code
        EntitySet es = getEntitySet(tx,4);
        Entity parent = es.getParent();
        Object[] primaryKeys = new Object[]{parent.getObject(tx,0),AccessID};
        EntityKey ek = new EntityKey("log", primaryKeys);
        Log entity = (Log)es.getEntity(ek);
        return entity;
    }

    /**
     * Create a value of Log. 
     * @param tx a transaction context
     * @param AccessID
     * @return Log
     **/
    public final Log createLog(TxID tx,String AccessID) throws AgentException {
        // generated code
        if (AccessID.length() > 16) {
            throw new AgentException("AccessID > maxlength(16)");
        }
        EntitySet es = getEntitySet(tx,4);
        Object[] primaryKeys = new Object[]{null,AccessID};
        Log entity = (Log)es.createEntity(tx,primaryKeys);
        return entity;
    }

    /**
     * Get an iterator of Log. 
     * @param tx a transaction context
     **/
    public final Iterator<Entity> getLogIterator(TxID tx) throws AgentException {
        // generated code
        EntitySet es = getEntitySet(tx,4);
        return es.iterator(tx);
    }

}
