package movies.spring.data.neo4j.services;

import java.util.*;

import movies.spring.data.neo4j.domain.*;
import movies.spring.data.neo4j.repositories.DeployRepository;
import movies.spring.data.neo4j.repositories.MovieRepository;
import movies.spring.data.neo4j.repositories.PodRepository;
import movies.spring.data.neo4j.repositories.VirtualMachineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

    private final static Logger LOG = LoggerFactory.getLogger(MovieService.class);

	private final MovieRepository movieRepository;

	private final PodRepository podRepository;

	private final VirtualMachineRepository virtualMachineRepository;

	private final DeployRepository deployRepository;

	public MovieService(MovieRepository movieRepository, PodRepository podRepository,
						VirtualMachineRepository virtualMachineRepository,
						DeployRepository deployRepository) {
		this.podRepository = podRepository;
		this.movieRepository = movieRepository;
		this.virtualMachineRepository = virtualMachineRepository;
		this.deployRepository = deployRepository;
	}

	private Map<String, Object> toD3Format(Collection<Movie> movies) {
		List<Map<String, Object>> nodes = new ArrayList<>();
		List<Map<String, Object>> rels = new ArrayList<>();
		int i = 0;
		Iterator<Movie> result = movies.iterator();
		while (result.hasNext()) {
			Movie movie = result.next();
			nodes.add(map("title", movie.getTitle(), "label", "movie"));
			int target = i;
			i++;
			for (Role role : movie.getRoles()) {
				Map<String, Object> actor = map("title", role.getPerson().getName(), "label", "actor");
				int source = nodes.indexOf(actor);
				if (source == -1) {
					nodes.add(actor);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
		}
		return map("nodes", nodes, "links", rels);
	}

	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}

    @Transactional(readOnly = true)
    public Movie findByTitle(String title) {
        Movie result = movieRepository.findByTitle(title);
        return result;
    }

    @Transactional(readOnly = true)
    public Collection<Movie> findByTitleLike(String title) {
        Collection<Movie> result = movieRepository.findByTitleLike(title);
        return result;
    }

	@Transactional(readOnly = true)
	public Map<String, Object>  graph(int limit) {
		Collection<Movie> result = movieRepository.graph(limit);
		return toD3Format(result);
	}

	@Transactional(readOnly = true)
	public String testCreatePod(){
		Pod pod = new Pod("jichao-test-pod");
		Pod newPod = podRepository.save(pod);
		System.out.println("============ID:" + newPod.getId());
		return "" + newPod.getId();
	}

	@Transactional(readOnly = true)
	public String testCreateVirtualMachine(){
		VirtualMachine vm = new VirtualMachine("jichao-test-virtual-machine");
		VirtualMachine newVM = virtualMachineRepository.save(vm);
		System.out.println("============ID:" + newVM.getId() + "=====" + newVM);
		return "" + newVM.getId();
	}

	public String saveDeploy(){
		VirtualMachine vm = new VirtualMachine("deploy-jc-vm");
		Pod pod = new Pod("deploy-jc-pod");
		Deploy deploy = new Deploy(pod,"deploy-test",vm);
		Deploy newDeploy = deployRepository.save(deploy);
		System.out.println("========Deploy ID:" + newDeploy.getId() + "=====");
		System.out.println("========POD ID:" + newDeploy.getPod().getId() + "=====");
		System.out.println("========VM ID:" + newDeploy.getVirtualMachine().getId() + "=====");
		return "" + deploy.getId();
	}

}
