package graph;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.algorithm.ConnectedComponents;
import java.util.Stack; 

public class App {

	private final static int NODES = 100;

	public static void main(String[] args) {
		//Generating a random graph 
		Graph graph = new SingleGraph("Random");
		Generator gen = new RandomGenerator(2);
		gen.addSink(graph);
		gen.begin();
		for(int i = 0; i <= NODES; i++)
			gen.nextEvents();
		gen.end();
		
		int [][] matx = new int[NODES][NODES];
		
		
		//Stack <? extends Node> st = new Stack();
		

		ConnectedComponents cc = new ConnectedComponents();
        cc.init(graph);
        String css = randomCSS(cc.getConnectedComponentsCount());
       	setCSS(graph, css);

        
        Iterator<ConnectedComponents.ConnectedComponent> components = cc.iterator();
		 Iterator <Node> nodes;
		 Node nodo;
 		int i = 0;

 		while (components.hasNext()) {
 			String cluster = "cluster"+i;
 				System.out.println(cluster);

 			for (Node n : components.next().getEachNode()) {
 				System.out.println("aa");
				n.addAttribute("ui.style", "fill-color: rgb(0,100,255);");

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
		//return "#" + alph[a] + alph[a] + alph[b] + alph[b] + alph[c] + alph[c]; 
		return "lime";
	}
	
	static void setCSS(Graph graph, String css){
		        System.out.println(css);

		/*String css =
					"node{fill-color:grey;} "
					+"node.important {fill-color:red;size:15px;} "
					+"node.parte1 {fill-color:black;size:15px;}";*/
		graph.addAttribute("ui.stylesheet", css);
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
	}
}
