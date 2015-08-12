package graph;

import java.util.Iterator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import java.util.Stack; 

public class App {

	public static void main(String[] args) {
		//Generating a random graph 
		Graph graph = new SingleGraph("Random");
		Generator gen = new RandomGenerator(2);
		gen.addSink(graph);
		gen.begin();
		for(int i = 0; i <= 100; i++)
			gen.nextEvents();
		gen.end();
		
		setCSS(graph);
		int [][] matx = new int[100][100];
		
		Iterator <? extends Node> nodes = graph.getNodeIterator();
		Node nodo;
		
		//Stack <? extends Node> st = new Stack();
		
		while(nodes.hasNext()){
			nodo = nodes.next();
			if(nodo.getIndex() > 50)
				nodo.addAttribute("ui.class", "important");
		
		}
		while(nodes.hasNext()){
			nodo = nodes.next();
			Iterator<? extends Node> neighbors = nodo.getNeighborNodeIterator();
			Node neighbor;
			while(neighbors.hasNext()){
				neighbor = neighbors.next();
				
			}
		}
		graph.display();
	}

	static void setCSS(Graph graph){
		String css =
					"node{fill-color:grey;} "
					+"node.important {fill-color:red;size:15px;}";
		graph.addAttribute("ui.stylesheet", css);
		graph.addAttribute("ui.quality");
		graph.addAttribute("ui.antialias");
	}
}
