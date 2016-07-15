package rdarank;

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
 * attribute name="MessageLatency" type="LONG" <br>
 * attribute name="MessageQueueLength" type="LONG" <br>
 * attribute name="Log" type="userlog" <br>
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
    * Column index of MessageLatency
    **/
    public static final int MESSAGELATENCY = 4;

    /**
    * Column index of MessageQueueLength
    **/
    public static final int MESSAGEQUEUELENGTH = 5;

    /**
    * Column index of Log
    **/
    public static final int LOG = 6;

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
        return "rdarank1.0";
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
     * @return MessageLatency
     **/
    public final long getMessageLatency(TxID tx) {
        // generated code
        return getLong(tx,4);
    }

    /**
     * Set a value to MessageLatency. 
     * @param tx a transaction context
     * @param value a value to be set to MessageLatency
     **/
    public final void  setMessageLatency(TxID tx, long value) throws AgentException {
        // generated code
        setLong(tx,4,value);
    }

    /**
     * @return MessageQueueLength
     **/
    public final long getMessageQueueLength(TxID tx) {
        // generated code
        return getLong(tx,5);
    }

    /**
     * Set a value to MessageQueueLength. 
     * @param tx a transaction context
     * @param value a value to be set to MessageQueueLength
     **/
    public final void  setMessageQueueLength(TxID tx, long value) throws AgentException {
        // generated code
        setLong(tx,5,value);
    }

    /**
     * Get a set of Log. 
     * Entity type of this entity set is userlog.
     * The setter method of Log is not generated because this attribute is a EntitySet. 
     * @return an entity set containing Userlog
     * @throws AgentException
     **/
    public final EntitySet getLog(TxID tx) throws AgentException {
        // generated code
        return getEntitySet(tx,6);
    }

    /**
     * Get a value of Log. 
     * @param tx a transaction context
     * @param AccessID
     * @return Userlog
     * @throws AgentException
     **/
    public final Userlog getLog(TxID tx,String AccessID) throws AgentException {
        // generated code
        EntitySet es = getEntitySet(tx,6);
        Entity parent = es.getParent();
        Object[] primaryKeys = new Object[]{parent.getObject(tx,0),AccessID};
        EntityKey ek = new EntityKey("userlog", primaryKeys);
        Userlog entity = (Userlog)es.getEntity(ek);
        return entity;
    }

    /**
     * Create a value of Userlog. 
     * @param tx a transaction context
     * @param AccessID
     * @return Userlog
     **/
    public final Userlog createLog(TxID tx,String AccessID) throws AgentException {
        // generated code
        if (AccessID.length() > 16) {
            throw new AgentException("AccessID > maxlength(16)");
        }
        EntitySet es = getEntitySet(tx,6);
        Object[] primaryKeys = new Object[]{null,AccessID};
        Userlog entity = (Userlog)es.createEntity(tx,primaryKeys);
        return entity;
    }

    /**
     * Get an iterator of Userlog. 
     * @param tx a transaction context
     **/
    public final Iterator<Entity> getLogIterator(TxID tx) throws AgentException {
        // generated code
        EntitySet es = getEntitySet(tx,6);
        return es.iterator(tx);
    }

}
