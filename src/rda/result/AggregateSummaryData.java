package rda.result;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import rda.property.SetProperty;

public class AggregateSummaryData implements SetProperty{
    public static void main(String[] args) throws FileNotFoundException, IOException {
        String path = "C:\\Users\\kaeru\\Desktop\\logs";
        HashMap<String, File> folderMap = getFileList(path);
        
        try (CSVWriter csvAggregate = new CSVWriter(new OutputStreamWriter(new FileOutputStream(path+"\\"+"Aggregate_Summary.csv")))) {
            setCsvFields(folderMap, csvAggregate);
            
            setCSVDatas(folderMap, csvAggregate);
            
            csvAggregate.flush();
        }
    }
    
    public static HashMap<String, File> getFileList(String path){
        File file = new File(path);
        File[] fileList = file.listFiles();
        HashMap<String, File> map = new LinkedHashMap<>();
        String key = "";
        
        for(File list : fileList){
            System.out.println(list.toString());
            
            if (list.toString().contains(LOG_MQL)) key = LOG_MQL;
            else if (list.toString().contains(LOG_MQE)) key = LOG_MQE;
            else if (list.toString().contains(LOG_CPU+".csv")) key = LOG_CPU;
            else if (list.toString().contains(LOG_RESULTS)) key = LOG_RESULTS;
            else key = String.valueOf(list.toString().hashCode());
            
            map.put(key, list);
        }
        
        return map;
    }
    
    //folder path -> field name
    public static void setCsvFields(HashMap map, CSVWriter csv){
        List<String> fields = new ArrayList<>(Arrays.asList("LogName"));
        
        for(Object key : map.keySet()) {
            String path = ((File)map.get(key)).toString();
            String[] field = path.split("\\\\");
            fields.add(field[field.length-1]);
        }
        
        System.out.println("Field:"+fields.size());
        csv.writeNext((String[])fields.toArray(new String[fields.size()]));
    }
    
    public static void setCSVDatas(HashMap map, CSVWriter csv) throws UnsupportedEncodingException, FileNotFoundException, IOException{
        List<String> total = new ArrayList<>(Arrays.asList("Total"));
        List<String> time = new ArrayList<>(Arrays.asList("Time"));
        List<String> throughput = new ArrayList<>(Arrays.asList("Throughput"));
        
        for(Object key : map.keySet()){
            File file = (File) map.get(key);
            for(File f : file.listFiles())
                if(f.toString().contains("Summary")){
                    try( CSVReader csvSummary = new CSVReader(new InputStreamReader(new FileInputStream(f.getPath()), "UTF-8"))){
                        String[] row;
                        while((row = csvSummary.readNext()) != null){
                            if(row[0].contains("Time") && !row[0].contains("Results"))
                                time.add(row[1]);
                            if(row[0].contains("Total"))
                                total.add(row[1]);           
                        }
                    }
                }
        }
        
        System.out.println("Total:"+total.size());
        System.out.println("Time:"+time.size());
        
        for(int i=1; i < total.size(); i++){
            Long t = Long.valueOf(total.get(i)) / (Long.valueOf(time.get(i)) / 1000);
            throughput.add(t.toString());
        }
        
        System.out.println(total);
        System.out.println(time);
        System.out.println(throughput);
        
        csv.writeNext((String[]) total.toArray(new String[total.size()]));
        csv.writeNext((String[]) time.toArray(new String[time.size()]));
        csv.writeNext((String[]) throughput.toArray(new String[throughput.size()]));
        
    }
}
