package lab.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class App{	 
	
    public static void main(String args[]) {
    	
    	List<NodeProcess> procs = new ArrayList<NodeProcess>(0);
    	
    	//NodeProcess(<UID>,<Set of neighbors>, <Set of recepients>)
    	Set<Integer> recepients =  new HashSet<Integer>(Arrays.asList(0, 1, 2, 3));
    	
    	//Creating ring network
    	procs.add(new NodeProcess(0, new HashSet<Integer>(Arrays.asList(1)), recepients));
    	procs.add(new NodeProcess(1, new HashSet<Integer>(Arrays.asList(2)), recepients));
    	procs.add(new NodeProcess(2, new HashSet<Integer>(Arrays.asList(3)), recepients));
    	procs.add(new NodeProcess(3, new HashSet<Integer>(Arrays.asList(0)), recepients));
    	
    	//Run distributed system
    	for(NodeProcess proc : procs) proc.start();
    	
    	//Wait until all processes are finished
    	boolean isAlive = true;
    	while(isAlive){
    		isAlive = false;
    		for(NodeProcess proc : procs) isAlive = isAlive ? true : proc.isAlive();
    	}
    	
    	//Print finished states
    	for(NodeProcess proc : procs)System.out.println("Proc " + proc.getUid() + " finished with code " + proc.getExitState());
    }
}
