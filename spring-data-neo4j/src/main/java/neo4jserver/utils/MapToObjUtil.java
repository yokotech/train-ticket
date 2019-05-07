package neo4jserver.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import neo4jserver.domain.GraphNode;
import neo4jserver.domain.Pod;
import neo4jserver.domain.VirtualMachine;

import java.util.Map;

public class MapToObjUtil {

    public static final GraphNode toGraphNodeBean(String className, Map<String, ? extends Object> map){
        int index = className.lastIndexOf(".");
        String rawClassName = className.substring(index+1);
        switch (rawClassName){
            case "Pod":
                return getPodByMap(map);
            case "VirtualMachine":
                return getVirtualMachineByMap(map);
            case "GraphNode":
                return getGraphNode(map);
            default:
                return null;
        }
    }

    public static Pod getPodByMap(Map<String, ? extends Object> map){
        Pod pod = new Pod();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(map);
            pod = gson.fromJson(jsonElement, Pod.class);
            System.out.println(jsonElement.toString());
            System.out.println("转换完成" + pod.getClassName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return pod;
    }

    public static VirtualMachine getVirtualMachineByMap(Map<String, ? extends Object> map){
        VirtualMachine virtualMachine = new VirtualMachine();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(map);
            System.out.println(jsonElement.toString());
            virtualMachine = gson.fromJson(jsonElement, VirtualMachine.class);
            System.out.println("转换完成" + virtualMachine.getClassName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return virtualMachine;
    }

    public static GraphNode getGraphNode(Map<String, ? extends Object> map){
        GraphNode graphNode = new GraphNode();
        try{
            Gson gson = new Gson();
            JsonElement jsonElement = gson.toJsonTree(map);
            graphNode = gson.fromJson(jsonElement, GraphNode.class);
            System.out.println("转换完成" + graphNode.getClassName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return graphNode;
    }


}
