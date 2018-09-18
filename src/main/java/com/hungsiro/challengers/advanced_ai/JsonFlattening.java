package com.hungsiro.challengers.advanced_ai;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ValueNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
/**
 * QUESTION 1 : JSON REFORMAT
 * Read Json as Tree structure and traverse the tree and keep track of
 * how deep you are to figure out dot notation property names.
 *
 * */


public class JsonFlattening {
    public static void main(String[] args){
        String json = "{ \"a\" : 1, \"b\" : { \"c\" : 2, \"d\" : [3,4] } }";
        System.out.println(justDoIt(json));
    }

    @Test
    public void testFlattentJson(){
        String json = "{ \"a\" : 1, \"b\" : { \"c\" : 2, \"d\" : [3,4] } }";
        String expectedOutput = "{\"a\"=1, \"b.c\"=2, \"b.d\"=[3,4]}";
        assertEquals(expectedOutput,justDoIt(json).toString());
    }

    public static Map<String,String> justDoIt(String json){
        Map<String, String> jsonElements = new LinkedHashMap<String, String>();
        try {
            jsonFlattentToString("", new ObjectMapper().readTree(json), jsonElements);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonElements;
    }

    private  static void jsonFlattentToString(String currentPath, JsonNode jsonNode,
                                                                        Map<String, String> map) {
        if (jsonNode.isObject()) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Iterator<Map.Entry<String, JsonNode>> iter = objectNode.fields();
            String pathPrefix = currentPath.isEmpty() ? "" : currentPath + ".";

            while (iter.hasNext()) {
                Map.Entry<String, JsonNode> entry = iter.next();
                jsonFlattentToString(pathPrefix + entry.getKey(), entry.getValue(), map);
            }
        } else if (jsonNode.isArray()) {
            ArrayNode arrayNode = (ArrayNode) jsonNode;
            map.put("\"" +currentPath +"\"", arrayNode.toString());

            // Incase you want to flatten even array elements.
            /*for (int i = 0; i < arrayNode.size(); i++) {
                jsonFlattentToString(currentPath + "[" + i + "]", arrayNode.get(i), map);
            }*/
        } else if (jsonNode.isValueNode()) {
            ValueNode valueNode = (ValueNode) jsonNode;
            map.put("\"" +currentPath +"\"", valueNode.asText());
        }
    }
}
