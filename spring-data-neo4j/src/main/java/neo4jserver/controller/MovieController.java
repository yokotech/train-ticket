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

	@GetMapping("/pod/{id}")
	public Pod getPod(@PathVariable String id){
		return movieService.findByPodId(id);
	}

	@PostMapping("/pod")
	public Pod postPod(@RequestBody Pod pod){
		return movieService.postPod(pod);
	}

	@GetMapping("/virtualMachine/{id}")
	public VirtualMachine getVirtualMachine(@PathVariable String id){
		return movieService.findByVMId(id);
	}

	@PostMapping("/virtualMachine")
	public VirtualMachine addVirtualMachine(@RequestBody VirtualMachine vm){
		return movieService.postVirtualMachine(vm);
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
