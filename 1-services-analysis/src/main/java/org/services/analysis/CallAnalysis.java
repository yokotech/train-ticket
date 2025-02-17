

package org.services.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class CallAnalysis {

	public static void main(String[] args) {

		HashMap<String, HashMap<String, Object>> traces = new HashMap<String, HashMap<String, Object>>();
		List<String> pListAll = new ArrayList<String>();

		String traceStr = readFile("./sample/traces-error-normal.json");
		JSONArray tracelist = new JSONArray(traceStr);

		for (int k = 0; k < tracelist.length(); k++) {
			JSONArray traceobj = (JSONArray) tracelist.get(k);

			// String call = readFile("./sample/call1x.json");
			// JSONArray spanlist = new JSONArray(call);

			List<HashMap<String, String>> serviceList = new ArrayList<HashMap<String, String>>();
			String traceId = ((JSONObject) traceobj.get(0)).getString("traceId");
			for (int j = 0; j < traceobj.length(); j++) {
				JSONObject spanobj = (JSONObject) traceobj.get(j);

				// String traceId = spanobj.getString("traceId");
				String id = spanobj.getString("id");
				String pid = "";
				if (spanobj.has("parentId")) {
					pid = spanobj.getString("parentId");
				}
				String name = spanobj.getString("name");

				HashMap<String, String> content = new HashMap<String, String>();
				content.put("spanid", id);
				content.put("parentid", pid);
				content.put("spanname", name);
				if(spanobj.has("annotations")){
					JSONArray annotations = spanobj.getJSONArray("annotations");
					for (int i = 0; i < annotations.length(); i++) {
						JSONObject anno = annotations.getJSONObject(i);
						if ("sr".equals(anno.getString("value"))) {
							JSONObject endpoint = anno.getJSONObject("endpoint");
							String service = endpoint.getString("serviceName");
							content.put("service", service);
						}
					}
					
					if (name.contains("message:")) {
						if ("message:input".equals(name)) {
							content.put("api", content.get("service") + "." + "message_received");
						}
					} else {
						JSONArray binaryAnnotations = spanobj.getJSONArray("binaryAnnotations");
						for (int i = 0; i < binaryAnnotations.length(); i++) {
							JSONObject anno = binaryAnnotations.getJSONObject(i);
							if ("error".equals(anno.getString("key"))) {
								content.put("error", anno.getString("value"));
							}
							if ("mvc.controller.class".equals(anno.getString("key"))
									&& !"BasicErrorController".equals(anno.getString("value"))) {
								String classname = anno.getString("value");
								content.put("classname", classname);
							}
							if ("mvc.controller.method".equals(anno.getString("key"))
									&& !"errorHtml".equals(anno.getString("value"))) {
								String methodname = anno.getString("value");
								content.put("methodname", methodname);
							}
						}
						content.put("api",
								content.get("service") + "." + content.get("classname") + "." + content.get("methodname"));
					}
					
					serviceList.add(content);
				}
			}

			// filter validate service api
			List<HashMap<String, String>> processList = serviceList.stream()
					.filter(elem -> !"message:output".equals(elem.get("spanname"))).collect(Collectors.toList());
			// processList.stream().forEach(n -> System.out.println(n));
			boolean failed = processList.stream().anyMatch(pl -> pl.containsKey("error"));

			// final info
			List<String> pList = processList.stream().map(pl -> {
				return pl.get("api");
			}).collect(Collectors.toList());
			pList.stream().forEach(n -> System.out.println(n));
			pListAll.addAll(pList);

			HashMap<String, Object> traceContent = new HashMap<String, Object>();
			traceContent.put("failed", failed);
			traceContent.put("list", pList);
			traces.put(traceId, traceContent);

		}
		
//		traces.forEach((key, val) -> System.out.println(val));
		
		System.out.println("---------------result-------------------");
		//all 
		double N = traces.keySet().size();
		//failed
		double NF = traces.values().stream().filter(trace->{
			return (Boolean)trace.get("failed");
		}).collect(Collectors.toList()).size();
		double NS = N - NF;
		System.out.println("Failed cases: " + NF);
		System.out.println("Success cases: " + NS);
		//method/spectrum list
		pListAll = pListAll.stream().distinct().collect(Collectors.toList());
//		methods.stream().forEach(n -> System.out.println(n));
		
		HashMap<String, Double> pListNCF = new HashMap<String, Double>();
		pListAll.stream().forEach(pl -> pListNCF.put(pl, 0.0));
		HashMap<String, Double> pListNCS = new HashMap<String, Double>();
		pListAll.stream().forEach(pl -> pListNCS.put(pl, 0.0));
		
		traces.values().stream().forEach(trace->{
			List<String> pList = (List<String>)trace.get("list");
			if((Boolean)trace.get("failed")){
				pList.stream().forEach(pl -> pListNCF.put(pl, pListNCF.get(pl)+1));
			}else{
				pList.stream().forEach(pl -> pListNCS.put(pl, pListNCS.get(pl)+1));
			}
		});
		
//		System.out.println(pListNCF);
//		System.out.println(pListNCS);
		
		//calculate Suspiciousness
		//NCF/NF // NCF/NF + NCS/NS
		//NCF // sqrt(NF*(NCF + NCS))
		HashMap<String, Double> pListSuspicious = new HashMap<String, Double>();
		pListAll.stream().forEach(pl -> {
//			double susp = (pListNCF.get(pl)/NF)  /  (pListNCF.get(pl)/NF + pListNCS.get(pl)/NS);
			double susp = (pListNCF.get(pl))  / Math.sqrt(NF*(pListNCF.get(pl) + pListNCS.get(pl)));
			pListSuspicious.put(pl, susp);
		});
//		System.out.println(pListSuspicious);
		
		Map<String, Double> result = pListSuspicious.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
		result.entrySet().stream().forEach(System.out::println);
//		System.out.println(result);
		
		
		
		
	}

	public static String readFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		String laststr = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				laststr = laststr + tempString;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;
	}
}
