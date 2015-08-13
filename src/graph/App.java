package graph;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.algorithm.ConnectedComponents;
import java.util.Stack; 

public class App {

	private final static int NODES = 40;

	public static void main(String[] args) {
		//Generating a random graph 
		Graph graph = new SingleGraph("Random");
		Generator gen = new RandomGenerator(2);
		gen.addSink(graph);
		gen.begin();
		for(int i = 0; i <= NODES; i++)
			gen.nextEvents();
		gen.end();

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


		graph.display();
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

		/*String css =
					"node{fill-color:grey;} "
					+"node.important {fill-color:red;size:15px;} "
					+"node.parte1 {fill-color:black;size:15px;}";*/
		graph.addAttribute("ui.stylesheet", css);
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
	}
}
