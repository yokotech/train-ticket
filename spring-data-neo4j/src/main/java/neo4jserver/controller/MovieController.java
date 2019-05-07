package neo4jserver.controller;

import java.util.*;
import neo4jserver.domain.*;
import neo4jserver.services.MovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MovieController {

	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
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
		return movieService.getShortPath();
	}

}
