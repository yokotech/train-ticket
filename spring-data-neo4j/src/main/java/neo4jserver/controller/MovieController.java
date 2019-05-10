package neo4jserver.controller;

import java.util.*;

import neo4jserver.domain.entities.AppService;
import neo4jserver.domain.entities.Container;
import neo4jserver.domain.entities.Pod;
import neo4jserver.domain.entities.VirtualMachine;
import neo4jserver.domain.relationships.VirtualMachineAndPod;
import neo4jserver.services.MovieService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MovieController {

	private final MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping("/container/{id}")
	public Container getContainer(@PathVariable String id){
		return movieService.findByContainerId(id);
	}

	@PostMapping("/container")
	public Container postContainer(@RequestBody Container container){
		return movieService.postContainer(container);
	}

	@GetMapping("/appService/{id}")
	public AppService getAppService(@PathVariable String id){
		return movieService.findByAppServiceId(id);
	}

	@PostMapping("/appService")
	public AppService postAppService(@RequestBody AppService appService){
		return movieService.postAppService(appService);
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
	public VirtualMachine postVirtualMachine(@RequestBody VirtualMachine vm){
		return movieService.postVirtualMachine(vm);
	}

	@GetMapping("/deploy/{id}")
	public VirtualMachineAndPod getDeploy(@PathVariable String id){
		return movieService.findByDeployId(id);
	}

	@PostMapping("/deploy")
	public VirtualMachineAndPod postDeploy(@RequestBody VirtualMachineAndPod virtualMachineAndPod){
		return movieService.postDeploy(virtualMachineAndPod);
	}

	@GetMapping("/addDeploy")
	public VirtualMachineAndPod addDeploy(){
		return movieService.saveDeploy();
	}

	@GetMapping("/getShortPath")
	public Map<String, Object> getShortPath(){
		return movieService.getShortPath();
	}

}
