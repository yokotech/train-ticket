package neo4jserver.services;

import java.util.*;

import neo4jserver.domain.*;
import neo4jserver.repositories.DeployRepository;
import neo4jserver.repositories.PodRepository;
import neo4jserver.repositories.VirtualMachineRepository;
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

	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}

    @Transactional(readOnly = true)
	public Pod findByPodId(String id){
		Long idLong = Long.parseLong(id);
		Optional<Pod> podOptional = podRepository.findById(idLong);
		return podOptional.get();
	}

	@Transactional(readOnly = true)
	public VirtualMachine findByVMId(String id){
		Long idLong = Long.parseLong(id);
		Optional<VirtualMachine> vmOptional = virtualMachineRepository.findById(idLong);
		return vmOptional.get();
	}



	@Transactional(readOnly = true)
	public Pod testCreatePod(){
		Pod pod = new Pod("jichao-test-pod",5);
		Pod newPod = podRepository.save(pod);
		System.out.println("============ID:" + newPod.getId());
		return newPod;
	}

	@Transactional(readOnly = true)
	public VirtualMachine testCreateVirtualMachine(){
		VirtualMachine vm = new VirtualMachine("jichao-test-virtual-machine",1024,2.5);
		VirtualMachine newVM = virtualMachineRepository.save(vm);
		System.out.println("============ID:" + newVM.getId() + "=====" + newVM);
		return newVM;
	}

	@Transactional(readOnly = true)
	public Deploy saveDeploy(){
		VirtualMachine vm = new VirtualMachine("1-vm1",1024,2.5);

		Pod pod = new Pod("1-pod1",5);
		pod.addLabel("iqwhrqwjels");

		VirtualMachine vm2 = new VirtualMachine("1-vm2",1024,2.5);
		vm2.addLabel("safjkashrfjkq3hkdklja");

		vm = virtualMachineRepository.save(vm);
		vm2 = virtualMachineRepository.save(vm2);
		podRepository.save(pod);

		for(String str: vm2.getLabels()){
			System.out.println("VM2标签：" + str);
		}

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
		retMap.put("nodeList",nodeList);
		retMap.put("edgeList",edgeList);

		for(Map<String ,Object> tempMap: nodeList){
			String fullClassName = (String)tempMap.get("className");
			System.out.println("=====" + fullClassName);
			Object temp = MapToObjUtil.toGraphNodeBean(fullClassName, tempMap);
			System.out.println("ResultClass" + temp.getClass().toString());
		}

		return retMap;
	}

}
