/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rda.result;

import com.ibm.ws.xs.jdk5.java.util.Arrays;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import rda.property.SetProperty;

/**
 *
 * @author kaeru
 */
public class ResultsDataForming implements SetProperty{
    private static final Integer digit = LOG_PERIOD.toString().length()-1;
    
    public static void main(String[] args) 
            throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException{
        String folderName = "user10";
        if(args.length == 1)
            folderName = args[0];
        
        String path = LOG_DIR+folderName;
        HashMap<String, File> map = getFileList(path);

        System.out.println(path+createCSVFileName());
        
        //Create Summary
        try (CSVWriter csvSummary = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+"/Summary-"+createCSVFileName()+".csv")))) {
            csvWriteSummary(map, csvSummary);           
            csvSummary.flush();
        }
        
        //Create LogAggregate(Length, Event, CPU, Map)
        try (CSVWriter csvSystem = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+"/System-"+createCSVFileName()+".csv")))) {
            csvWriteMQandCPU(map, csvSystem);
            csvSystem.flush();
        }
    }
    
    //UserN folder - getfile
    public static HashMap<String, File> getFileList(String path){
        File file = new File(path);
        File[] fileList = file.listFiles();
        HashMap<String, File> map = new HashMap<>();
        String key = "";
        
        for(File list : fileList){
            System.out.println(list.toString());
            
            if (list.toString().contains(LOG_MQL)) key = LOG_MQL;
            else if (list.toString().contains(LOG_MQE)) key = LOG_MQE;
            else if (list.toString().contains(LOG_CPU+".csv")) key = LOG_CPU;
            else if (list.toString().contains(LOG_RESULTS)) key = LOG_RESULTS;
            else key = "test";
            
            map.put(key, list);
        }
        
        return map;
    }
    
    //Commons Output File Name
    public static String createCSVFileName() {
        String fileName = LOG_ALL;
        fileName = fileName +"_p["
                + TIME_RUN + "s," 
                + TIME_PERIOD + "ms,"
                + "u"  + NUMBER_OF_USERS + ","
                + "ag" + NUMBER_OF_RANK_AGENTS + ","
                + "s"  + NUMBER_OF_SERVER + ","
                + "st" + SERVER_THREAD + ","
                //+ "t"  + DATA_TYPE.getName() + ","
                + "lp" + LOG_PERIOD +","
                + "L"  + QUEUE_LENGTH + ","
                + "ws" + WINDOW_SIZE + ","
                + "w(" + AGENT_WAIT + "," + QUEUE_WAIT +")"
                +"]";
        
        return fileName;
    }
    
        
    //Results (title, field, data ,result) -> CSV Summary
    private static List<String> rootAGIDList = new ArrayList<>();
    private static List<String> agIDList = new ArrayList<>();
    public static void csvWriteSummary(HashMap<String, File> map, CSVWriter csv) 
                throws UnsupportedEncodingException, FileNotFoundException, IOException{
        try (CSVReader csvResultsReader = new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_RESULTS)), "UTF-8"))) {
            List<String[]> titleList = new ArrayList<String[]>(){{add(new String[]{"<Setting Parameter>"});}};
            List<String[]> resultsList = new ArrayList<String[]>(){{add(new String[]{"<Results>"});}};
            List<String[]> fieldsList = new ArrayList<String[]>(){{add(new String[]{"<Fields>"});}};
            List<String[]> dataList = new ArrayList<String[]>(){{add(new String[]{"<Data>"});}};
            
            String[] line;
            while((line =csvResultsReader.readNext()) != null){
                if(line.length < 1) continue;
                switch(line[1].replace(" ", "")){
                    case "title":
                        line[0] = ""; line[1]=" ";
                        titleList.add(line);
                        break;
                    case "result":
                        line[0] = ""; line[1]=" ";
                        resultsList.add(line);
                        break;
                    case "field":
                        line[0] = ""; line[1]=" ";
                        fieldsList.add(line);
                        break;
                    case "data":
                        line[0] = ""; line[1]=" ";
                        dataList.add(line);    
                        break; 
                }
            }
            
            for(String[] title  : titleList)   csv.writeNext(title);
            for(String[] result : resultsList) csv.writeNext(result);
            for(String[] field  : fieldsList)  csv.writeNext(field);
            for(String[] data   : dataList)    csv.writeNext(data);
            
            String[] field = fieldsList.get(1);
            rootAGIDList = Arrays.asList(field).subList(3, field.length);
            field = fieldsList.get(2);
            agIDList = Arrays.asList(field).subList(3, field.length);
        }
    }
    
    //Aggregate Log(MQLength, Events, CPU) -> CSV AgentSystem
    public static void csvWriteMQandCPU(HashMap<String, File> map, CSVWriter csv)
            throws FileNotFoundException, UnsupportedEncodingException, IOException, ParseException{
    
        //Info Time
        List<String> timeList = dataToTime(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8")));
        
        //CPU (vmstat.csv)
        List<List<String>> dataCPUList = dataToCPU(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_CPU)), "UTF-8")), timeList);
        
        //Info NumberOfAgents
        List<String> agentList = dataToNumAgents(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8")));
        
        //Info Agents Map
        List<List<String>> agentMapList = dataToAgentMap(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQE)), "UTF-8")), timeList);
            
        //Info_MQLength.csv
        List<List<String>> dataMQLList = dataToMQL(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQL)), "UTF-8")));
            
        //Info_MQEvent.csv
        List<List<String>> dataMQEList = dataToMQE(new CSVReader(new InputStreamReader(new FileInputStream(map.get(LOG_MQE)), "UTF-8")), timeList);
        
        //Check DataLists
        System.out.println("MQL:"+dataMQLList.size());
        System.out.println("MQE:"+dataMQEList.size());
        System.out.println("CPU:"+dataCPUList.size());
            
        //List -> CSV
        //Field
        List<String> csvList = new ArrayList<>();
        //Time Field
        csvList.add("Time"); 
        //CPU Field
        csvList.add("CPU:us"); csvList.add("CPU:sy");
        //Num Agents Field
        csvList.add("Numeber Of Agents");
        csvList.add("");
        //AgentMap Field
        for(String agID : rootAGIDList) csvList.add(agID+"Map");
        csvList.add("");
        //MQ Length Field
        for(String agID : agIDList) csvList.add(agID+"Length");
        csvList.add("");
        //MQ Event Field
        for(String agID : agIDList) csvList.add(agID+"Event");
        //Write Fields
        csv.writeNext(csvList.toArray(new String[csvList.size()]));
        
        //Data
        for(int i = 0; i < timeList.size(); i++){
            //CSV a row Initialise 
            csvList = new ArrayList<>();
                
            //Time
            csvList.add(timeList.get(i));
                
            //CPU
            for(String cpu : dataCPUList.get(i))
                csvList.add(cpu);
            
            //Number Of Agent
            csvList.add(agentList.get(i));
            
            //Column Space
            csvList.add("");
            
            //AgentMap
            for(String agmap : agentMapList.get(i))
                csvList.add(agmap);
            
            //Column Space
            csvList.add("");
            
            //MQLength
            for(String len : dataMQLList.get(i))
                csvList.add(len);
                
            //Column Space
            csvList.add("");
                
            //MQEvent
            for(String event : dataMQEList.get(i))
                csvList.add(event);
  
            //CSV a row Write
            csv.writeNext(csvList.toArray(new String[csvList.size()]));
        }                   
    }
    
    /*
     * Create AggregateLog elements
     * csv_Data - List
     */
    //Data To TimeList
    public static List dataToTime(CSVReader csvMQLReader) throws IOException{
        CSVReader reader = csvMQLReader;
        List<String> timeList = new ArrayList<>();
        
        //Data
        String[] line;
        while((line = reader.readNext()) != null){
            if(line.length < 1) continue;
            switch(line[1].replace(" ", "")){
                case "data":
                    timeList.add(line[0].substring(0, line[0].length()-digit));
                    break;
            }
        }
        
        return timeList;
    }
    
    //Data To Number Of Agents
    public static List dataToNumAgents(CSVReader csvMQLReader) throws IOException{
        CSVReader reader = csvMQLReader;
        List<String> agentList = new ArrayList<>();
        
        //Data
        String[] line;
        while((line = reader.readNext()) != null){
            if(line.length < 1) continue;
            switch(line[1].replace(" ", "")){
                case "field":
                    agentList.add(String.valueOf(line.length-3));
                    break;
            }
        }
        
        return agentList;
    }
    
    //Data To MessageQueue Length
    public static List dataToMQL(CSVReader csvMQLReader) throws IOException{
        CSVReader reader = csvMQLReader;
        List<List<String>> dataMQLList = new ArrayList<>();
        
        //Data
        Integer numOfAgents = 0;
        String[] line, field = null, data;
        Map dataToFieldMap = new TreeMap();
        while((line = reader.readNext()) != null){
            if(line.length < 1) continue;
            switch(line[1].replace(" ", "")){
                case "field":
                    field = line;
                    numOfAgents = line.length-3;
                    break;
                case "data":
                    data = line;
                    for(int i=3; i < data.length; i++)
                        dataToFieldMap.put(field[i], data[i]);
                    break;
            }
                
            if(!dataToFieldMap.isEmpty()){
                List<String> mql = new ArrayList<>();
                for(String agID : agIDList)
                    if(dataToFieldMap.get(agID) == null) mql.add("0");
                    else mql.add((String) dataToFieldMap.get(agID));
                dataMQLList.add(mql);
                dataToFieldMap.clear();
            }
                        
        }
        
        return dataMQLList;
    }
    
    //Data To MessageQueue Event
    public static List dataToMQE(CSVReader csvMQEReader, List<String> timeList) throws IOException{
        CSVReader reader = csvMQEReader;
        List<List<String>> dataMQEList = new ArrayList<>();
        
        //Data
        String[] line;
        HashMap<String, HashMap<String, Integer>> eventMap = new HashMap<>();
        while((line = reader.readNext()) != null){
            if(line.length < 1) continue;
            if(line[1].contains("field")) continue;
                
            String subLine = line[0].substring(0, line[0].length()-digit);
            
            if(eventMap.get(subLine) == null)
                eventMap.put(subLine, new HashMap<String, Integer>());
            
            if(eventMap.get(subLine) != null){
                HashMap<String, Integer> mqMap = eventMap.get(subLine);
                if(mqMap.get(line[3]) != null)
                    mqMap.put(line[3], mqMap.get(line[3])+1);
                else
                    mqMap.put(line[3], 1);
            }
        }

        for(String time : timeList){
            List<String> event = new ArrayList<>();
            if(eventMap.get(time) != null){
                HashMap mqMap = eventMap.get(time);
                for(String agID : agIDList)
                    if(mqMap.get(agID) != null){
                        event.add(((Integer)mqMap.get(agID)).toString());
                    }
                    else event.add("0");
            } else 
                for(String agID : agIDList) event.add("0");
            dataMQEList.add(event);
        }
        
        return dataMQEList;
    }
    
    //Data To CPU Availability
    public static List dataToCPU(CSVReader csvCPUReader, List timeList) throws IOException{
        CSVReader reader = csvCPUReader;
        List<List<String>> dataCPUList = new ArrayList<>();
        
        //Data
        String[] line;
        int nextime = 0;
        while((line = reader.readNext()) != null){
            List<String> cpu = new ArrayList<>();
            if(line.length < 1 || timeList.size() == nextime) continue;
            String time = line[0].substring(0, line[0].length()-digit);
            if(time.compareTo((String)timeList.get(nextime)) >= 0){
                cpu.add(line[1]);
                cpu.add(line[2]);
                dataCPUList.add(cpu);
                nextime++;
            }
        }
        
        while(timeList.size() - dataCPUList.size() > 0)
            dataCPUList.add(dataCPUList.get(dataCPUList.size()-1));
        
        return dataCPUList;
    }
    
    //Data To AgentMap (MQEvents)
    private static HashMap<String, String> agentTreeMap = new HashMap<>();
    public static List dataToAgentMap(CSVReader csvMQEReader, List<String> timeList) throws IOException{
        CSVReader reader = csvMQEReader;    
        List<List<String>> agentMapList = new ArrayList<>();
            
        //Data
        HashMap<String, HashMap<String, Integer>> eventMap = new HashMap<>();
        String[] line;
        while((line =reader.readNext()) != null){
            if(line.length < 1) continue;
            if(line[1].contains("field")) continue;
            
            agentTreeMap.put(line[4], line[3]);
            String regID = searchParent(line[3]);
            
            String subLine = line[0].substring(0, line[0].length()-digit);
            
            if(eventMap.get(subLine) == null)
                eventMap.put(subLine, new HashMap<String, Integer>());
            
            if(eventMap.get(subLine) != null){
                HashMap<String, Integer> mqMap = eventMap.get(subLine);
                if(mqMap.get(regID) != null)
                    mqMap.put(regID, mqMap.get(regID)+1);
                else
                    mqMap.put(regID, 1);
            }
        }
        
        for(String time : timeList){
            //agentMapList.add(setListDistAgent(eventMap, time));
            agentMapList.add(setTotalListDistAgent(eventMap, time));
        }
        
        return agentMapList;
    }
    
    //Tool Search Root AgentID
    private static String searchParent(String pid){
        if(agentTreeMap.get(pid) == null){
            return pid;
        }else{
            return searchParent(agentTreeMap.get(pid));
        }
    }
    
    private static Boolean flg = true;
    private static List<String> setListDistAgent(HashMap eventMap, String time){
        List<String> agmap = new ArrayList<>();
            
        if(eventMap.get(time) != null){
            HashMap mqMap = (HashMap) eventMap.get(time);
            for(String agID : rootAGIDList)
                if(mqMap.get(agID) != null){
                    agmap.add(((Integer)mqMap.get(agID)).toString());
                }
                else agmap.add("0");
        } else{
            if(flg){
                for(String agID : rootAGIDList) agmap.add("1");
                flg = false;
            }
            else for(String agID : rootAGIDList) agmap.add("0");
        }
        
        return agmap;
    }
    
    private static HashMap<String, Integer> totalMap = new HashMap();
    private static List<String> setTotalListDistAgent(HashMap eventMap, String time){
        //Init Total Map
        if(flg){
            for(String agID : rootAGIDList) totalMap.put(agID, 1);
            flg = false;
        }
        
        List<String> agmap = new ArrayList<>();
            
        if(eventMap.get(time) != null){
            HashMap mqMap = (HashMap) eventMap.get(time);
            for(String agID : rootAGIDList)
                if(mqMap.get(agID) != null){
                    Integer total = totalMap.get(agID) + (Integer) mqMap.get(agID);
                    totalMap.put(agID, total);
                }
        }
        
        for(String agID : rootAGIDList) agmap.add(((Integer)totalMap.get(agID)).toString());
        
        return agmap;
    }
}
