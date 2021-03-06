package rda.manager;

import java.text.DecimalFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;

public class IDManager {

    private String rule;
    private DecimalFormat dformat;
    private TreeMap ageMap = new TreeMap();
    private Map regAgentMap = new HashMap();
    private Random rand = new Random();
    private Integer num;

    public IDManager(Map idParam) {
        this.rule = (String) idParam.get("RULE");
        if((Long) idParam.get("SEED") != -1L) 
            rand.setSeed((Long) idParam.get("SEED"));

        StringBuilder digit = new StringBuilder();
        this.num = (Integer) idParam.get("AMOUNT_OF_AGENTS");
        for (int i = 0; i < this.num.toString().length() + 1; i++) {
            digit.append("0");
        }

        dformat = new DecimalFormat(digit.toString());
    }

    private Integer serialID = 0;

    public synchronized String genID() {
        String agID = rule + dformat.format(serialID++);
        return agID;
    }

    private static Queue<String> reserve = new ArrayDeque();

    public void setReserveID(String agID) {
        reserve.offer(agID);
    }

    public String getReserveID() {
        return reserve.poll();
    }

    public Integer getNumReserves() {
        return reserve.size();
    }

    //Register InitAgent
    public synchronized void initRegID(String id) {
        //Agent数が10以下のとき
        if (this.num <= 10) {
            List agList = new ArrayList();
            agList.add(id);
            regAgentMap.put(id, agList);
            ageMap.put(serialID * (100 / this.num), id);

            //ID Mapping
            idMap.put(id, id);
        } else //Original IDを10件に絞る
        if (serialID - 1 < 10) {
            List agList = new ArrayList();
            agList.add(id);
            regAgentMap.put(id, agList);
            ageMap.put(serialID * 10, id);

            //ID Mapping
            idMap.put(id, id);
        } else {
            List agList = (List) regAgentMap.get(ageMap.get(((serialID % 10) + 1) * 10));
            agList.add(id);

            //ID Mapping
            idMap.put(id, ageMap.get(((serialID % 10) + 1) * 10));
        }
    }

    private Map idMap = new HashMap<>();

    public synchronized void regID(String origID, String id) {
        ((List) regAgentMap.get(origID)).add(id);

        //ID Mapping
        idMap.put(id, origID);
    }

    public synchronized void deleteID(String origID, String id) {
        ((List) regAgentMap.get(origID)).remove(id);
        setReserveID(id);

        //ID Mapping
        idMap.remove(id);
    }

    public String ageToID(Integer age) {
        return (String) ageMap.ceilingEntry(age).getValue();
    }
    
    public String getOrigID(String agID) {
        return (String) idMap.get(agID);
    }

    //Random
    public String getDestID(String origID) {
        List destAgentList = (List) regAgentMap.get(origID);
        return (String) destAgentList.get(rand.nextInt(destAgentList.size()));
    }
    
    //Hash
    public String getDestID(String origID, String userID) {
        List destAgentList = (List) regAgentMap.get(origID);
        return (String) destAgentList.get(Integer.valueOf(userID.split("#")[1]) % destAgentList.size());
    }

    //Test Print
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<IDManager>\n");

        sb.append("<<Age To AgentID>>\n");
        for (Object key : ageMap.keySet()) {
            sb.append(key + ":" + ageMap.get(key) + "\n");
        }

        sb.append("<<Register Key and Lists>>\n");
        for (Object key : regAgentMap.keySet()) {
            sb.append(key + ":" + regAgentMap.get(key) + "\n");
        }

        return sb.toString();
    }
}
