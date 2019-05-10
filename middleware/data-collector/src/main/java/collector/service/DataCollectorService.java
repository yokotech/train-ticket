package collector.service;

import collector.domain.apinode.NodeList;
import collector.domain.apipod.PodList;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DataCollectorService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    String masterIP = "http://10.141.212.24:8080";


    public NodeList getNodeList(){
        String list = restTemplate.getForObject( masterIP + "/api/v1/nodes", String.class);
        Gson gson = new Gson();
        NodeList nodeList = gson.fromJson(list,NodeList.class);
        return nodeList;
    }

    public PodList getPodList(){
        String list = restTemplate.getForObject(masterIP + "/api/v1/pods", String.class);
        Gson gson = new Gson();
        PodList podList = gson.fromJson(list, PodList.class);
        return podList;
    }

}
