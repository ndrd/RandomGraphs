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
import javax.swing.JFrame;


public class App {	 
	
    private final static int NODES = 10;

    public static void main(String args[]) {

        List<NodeProcess> procs = new ArrayList<NodeProcess>(0);

    	Graph g = getRandomGraph();
        colourGraph(g);
        for (Node n : g)
            n.addAttribute("ui.label", n.getId());
      
        run(g, procs);
        ArrayList<String> s1 = new ArrayList<>();
        ArrayList<String> s2 = new ArrayList<>();

        try {
            for(NodeProcess n: procs) {
                for (LamportClock.TimeStamp ts : n.getClock().getTS()) {
                    if (ts.nPrcss != n.getUid()) {
                        System.out.println(ts.nPrcss + "/" + ts.nEvnt);
                    }
                }
                for (String i : n.getLog()) {
                    s1.add(i);
                }
            }    
        } catch (Exception e) {

        }
        
        procs = new ArrayList<NodeProcess>(0);
        run(g, procs);

        try {
            for(NodeProcess n: procs) {
                for (LamportClock.TimeStamp ts : n.getClock().getTS()) {
                    if (ts.nPrcss != n.getUid()) {
                        System.out.println(ts.nPrcss + "/" + ts.nEvnt);
                    }
                }
                for (String i : n.getLog()) {
                    s2.add(i);
                }
            }    
        } catch (Exception e) {

        }

        System.out.println(s1.size() + " - " + s2.size());

        JFrame jf=new Plot(procs);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(1200,750);
        jf.setVisible(true);



    }

    static void run(Graph g, List<NodeProcess> procs) {
        Iterator<? extends Node> neighbors;
        Set<Integer> semi_recipients = new HashSet<Integer>();

        for(Node n : g ) {
            neighbors = n.getNeighborNodeIterator();

            while(neighbors.hasNext()) {
                semi_recipients.add(neighbors.next().getIndex());
            }

            procs.add(new NodeProcess(n.getIndex(), semi_recipients, semi_recipients));
        }

        g.display();

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

    static Graph getRandomGraph() {
        Graph graph = new MultiGraph("Random");
        Generator gen = new RandomGenerator(5);
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
                    
                    while(neighbors.hasNext()) {
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
            css += "node.cluster"+i + " {fill-color:" + colorAleatorio()+ "; size:" + (6+0) + "px;} ";
        }
        return css;
    }

    static String colorAleatorio() {
        String [] alph = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        int a = Math.round((int)(Math.random() * 16));
        int b = Math.round((int)(Math.random() * 16));
        int c = Math.round((int)(Math.random() * 16));
        return "#" + alph[a] + alph[a] + alph[b] + alph[b] + alph[c] + alph[c]; 
    }
    
    static void setCSS(Graph graph, String css){
        graph.addAttribute("ui.stylesheet", css);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
    }
}
