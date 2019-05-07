package neo4jserver.services;

import java.util.*;
import neo4jserver.domain.*;
import neo4jserver.repositories.*;
import neo4jserver.utils.MapToObjUtil;
import neo4jserver.utils.Neo4jUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieService {

	@Autowired
	private Neo4jUtil neo4jUtil;

	private final PodRepository podRepository;

	private final VirtualMachineRepository virtualMachineRepository;

	private final DeployRepository deployRepository;


	public MovieService(PodRepository podRepository,
						VirtualMachineRepository virtualMachineRepository,
						DeployRepository deployRepository) {
		this.podRepository = podRepository;
		this.virtualMachineRepository = virtualMachineRepository;
		this.deployRepository = deployRepository;
	}

    @Transactional(readOnly = true)
	public Pod findByPodId(String id){
		Long idLong = Long.parseLong(id);
		PodResult pr = podRepository.getPodWithLabels(idLong);
		Pod pod = pr.node;
		pod.setLabels(new HashSet<>(pr.labels));
		return pod;
	}

	@Transactional(readOnly = true)
	public Pod postPod(Pod pod){
		Pod newPod = podRepository.save(pod);
		return newPod;
	}

	@Transactional(readOnly = true)
	public VirtualMachine findByVMId(String id){
		Long idLong = Long.parseLong(id);
        VirtualMachineResult vmr = virtualMachineRepository.getVitualMachineWithLabels(idLong);
        VirtualMachine vm = vmr.node;
        vm.setLabels(new HashSet<>(vmr.labels));
		return vm;
	}

	@Transactional(readOnly = true)
	public VirtualMachine postVirtualMachine(VirtualMachine vm){
		VirtualMachine newVM = virtualMachineRepository.save(vm);
		return newVM;
	}

	@Transactional(readOnly = true)
	public Deploy saveDeploy(){
		VirtualMachine vm = new VirtualMachine("1-vm1",1024,2.5);

		Pod pod = new Pod("1-pod1",5);

		VirtualMachine vm2 = new VirtualMachine("1-vm2",1024,2.5);

		vm = virtualMachineRepository.save(vm);
		vm2 = virtualMachineRepository.save(vm2);
		pod = podRepository.save(pod);

		Deploy deploy = new Deploy(pod,"1-deploy",vm);
		deploy = deployRepository.save(deploy);

		Deploy deploy2 = new Deploy(pod,"1-deploy2",vm2);
		deploy2 = deployRepository.save(deploy2);


		System.out.println("========Deploy ID:" + deploy.getId() + "=====");
		System.out.println("========Deploy ID:" + deploy2.getId() + "=====");
		return deploy;
	}

	@Transactional(readOnly = true)
	public Map<String, Object> getShortPath(){
		Map<String, Object> retMap = new HashMap<>();
		//cql语句
		String cql = "match l=shortestPath(({name:'1-vm1'})-[*]-({name:'1-vm2'})) return l";
		//待返回的值，与cql return后的值顺序对应
		Set<Map<String ,Object>> nodeList = new HashSet<>();
		Set<Map<String ,Object>> edgeList = new HashSet<>();
		neo4jUtil.getPathList(cql,nodeList,edgeList);
		System.out.println("=======");

		retMap.put("edgeList",edgeList);

		Set<GraphNode> retNodeSet = new HashSet<>();
		for(Map<String ,Object> tempMap: nodeList){
			String fullClassName = (String)tempMap.get("className");
			System.out.println("=====" + fullClassName);
			GraphNode temp = MapToObjUtil.toGraphNodeBean(fullClassName, tempMap);
			retNodeSet.add(temp);
			System.out.println("ResultClass" + temp.getClass().toString());
		}
		retMap.put("nodeList",retNodeSet);

		return retMap;
	}

}
