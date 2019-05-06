package movies.spring.data.neo4j.controller;

import java.util.*;

import movies.spring.data.neo4j.domain.Deploy;
import movies.spring.data.neo4j.domain.Pod;
import movies.spring.data.neo4j.domain.VirtualMachine;
import movies.spring.data.neo4j.services.MovieService;
import movies.spring.data.neo4j.utils.Neo4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mark Angrish
 * @author Michael J. Simons
 */
@RestController
@RequestMapping("/")
public class MovieController {

	@Autowired
	private Neo4jUtil neo4jUtil;

	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

    @GetMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return movieService.graph(limit == null ? 100 : limit);
	}

	@GetMapping("/getPod/{id}")
	public Pod getPod(@PathVariable String id){
		return movieService.findByPodId(id);
	}

	@GetMapping("/addPod")
	public Pod addPod(){
		return movieService.testCreatePod();
	}

	@GetMapping("/getVirtualMachine/{id}")
	public VirtualMachine getVirtualMachine(@PathVariable String id){
		return movieService.findByVMId(id);
	}

	@GetMapping("/addVirtualMachine")
	public VirtualMachine addVirtualMachine(){
		return movieService.testCreateVirtualMachine();
	}

	@GetMapping("/addDeploy")
	public Deploy addDeploy(){
		return movieService.saveDeploy();
	}

	@GetMapping("/getShortPath")
	public Map<String, Object> getShortPath(){
		Map<String, Object> retMap = new HashMap<>();
		//cql语句
		String cql = "match l=shortestPath(({name:'1-vm1'})-[*]-({name:'1-vm2'})) return l";
		//待返回的值，与cql return后的值顺序对应
		Set<Map<String ,Object>> nodeList = new HashSet<>();
		Set<Map<String ,Object>> edgeList = new HashSet<>();
		neo4jUtil.getPathList(cql,nodeList,edgeList);
		System.out.println("=======");
		retMap.put("nodeList",nodeList);
		retMap.put("edgeList",edgeList);
		return retMap;
	}

}
