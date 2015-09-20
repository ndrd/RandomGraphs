package lab.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.algorithm.ConnectedComponents;
import java.util.Iterator;
import java.util.Stack; 


public class App{	 
	
    private final static int NODES = 40;

    public static void main(String args[]) {

        List<NodeProcess> procs = new ArrayList<NodeProcess>(0);

    	Graph g = getRandomGraph();
        Iterator<? extends Node> neighbors;
        Set<Integer> semi_recipients = new HashSet<Integer>();

        for(Node n :g ) {
            neighbors = n.getNeighborNodeIterator();
            
            while(neighbors.hasNext()) {
                semi_recipients.add(neighbors.next().getIndex());
            }

            procs.add(new NodeProcess(n.getIndex(), semi_recipients, semi_recipients));
        }

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

    /*

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
    */

    static Graph getRandomGraph() {
        Graph graph = new SingleGraph("Random");
        Generator gen = new RandomGenerator(2);
        gen.addSink(graph);
        gen.begin();
        for(int i = 0; i <= NODES; i++)
            gen.nextEvents();
        gen.end();
        return graph;
    }

    static void colourGraph(Graph graph) {
        String css = randomCSS(NODES);
        setCSS(graph, css);

        Iterator <? extends Node> nodes = graph.getNodeIterator();
        Node nodo;
       
        int i = 0;
        Stack <Node> st = new Stack<Node>();

       
        while(nodes.hasNext()){

            st.push(nodes.next());
            
            String cs = "cluster"+i;

            while (!st.empty()) {
                Node tmp = st.pop();
                
                if (!tmp.hasAttribute("visited")) {
                    tmp.addAttribute("visited", "true");
                    tmp.addAttribute("ui.class", cs);
                    Iterator<? extends Node> neighbors = tmp.getNeighborNodeIterator();
                    Node neighbor;
                    
                    while(neighbors.hasNext()){
                        st.push(neighbors.next());
                    }

                }

            }
            i++;

        }

    }

    static String randomCSS(int elems) {
        String css = "";
        for (int i = 0; i < elems ; i++ ) {
            css += "node.cluster"+i + " {fill-color:" + colorAleatorio()+ "; size:" + (16+i) + "px;} ";
        }
        return css;
    }

    static String colorAleatorio() {
        String [] alph = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        int a = Math.round((int)(Math.random() * 16));
        int b = Math.round((int)(Math.random() * 16));
        int c = Math.round((int)(Math.random() * 16));
        return "#" + alph[a] + alph[a] + alph[b] + alph[b] + alph[c] + alph[c]; 
        //return "lime";
    }
    
    static void setCSS(Graph graph, String css){
        graph.addAttribute("ui.stylesheet", css);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
    }
}
