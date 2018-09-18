package com.hungsiro.challengers.advanced_ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataStoreAndLoad {
    public static void main(String[] args){
        Map<String,String> m1 = new HashMap<String, String>();
        m1.put("key1","value1");
        m1.put("key2","value2");

        Map<String,String> m2 = new HashMap<String, String>();
        m2.put("key1","value1");

        List<Map<String,String>> list = new ArrayList<Map<String, String>>();
        list.add(m1);
        list.add(m2);

        System.out.println(store(list));
        System.out.println(load(store(list)));
    }




    public static String store(List<Map<String,String>> input){
        List<String> textEle = new ArrayList<String>();
        for(Map<String,String> map: input){
            textEle.add(convertMapToString(map,";"));
        }
        return String.join("\\n",textEle);
    }

    public  static List<Map<String,String>> load(String text){
        List<String> mapStrings = Arrays.asList(text.split("\\\\n"));
        List<Map<String,String>> result = new ArrayList<Map<String, String>>();
        for(String mapString : mapStrings){
            result.add(convertStringToMap(mapString,";"));
        }
        return result;
    }

    private  static String convertMapToString(Map<String,String> map,String delimiter){
        if(map.keySet().isEmpty()){return "";}
        StringBuilder builder = new StringBuilder();
       for(Map.Entry<String,String> entry : map.entrySet()){
           builder.append(entry.getKey()).append("=").append(entry.getValue()).append(delimiter);
       }
       String result =  builder.toString();
       return removeLast(result,delimiter);
    }

    private static Map<String,String> convertStringToMap(String text,String delimiter){
        Map<String,String> map = new HashMap<String, String>();
        if(text != null && text.length() > 0){
            String[] strings = text.split(delimiter);
            for(int i = 0; i < strings.length; i++){
                String[] keyValues = strings[i].split("=");
                if(keyValues.length == 2){
                    map.put(keyValues[0],keyValues[1]);
                }
            }
        }
        return map;

    }

    private static String removeLast(String str,String delemiter) {
        if (str != null && str.length() > delemiter.length() ) {
            str = str.substring(0, str.length() - delemiter.length());
        }
        return str;
    }

}
