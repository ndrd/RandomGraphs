
package lab.graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JFrame;


class Plot extends JFrame{
    private final int BOUNDS = 4;
    private List<NodeProcess> procs;
    private int dis;

    public Plot(List<NodeProcess> procs){
    	this.procs = procs;
    	this.dis = 750/procs.size();
    }
    
    void drawArrow(Graphics g1, int x1, int y1, int x2, int y2) {
    	Graphics2D g = (Graphics2D) g1.create();
    		
    	double dx = x2 - x1, dy = y2 - y1;
    	double angle = Math.atan2(dy, dx);
    	int len = (int) Math.sqrt(dx*dx + dy*dy);
    	AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
    	at.concatenate(AffineTransform.getRotateInstance(angle));
    	g.transform(at);
    		
    	//Plot horizontal arrow starting in (0, 0)
    	g.drawLine(0, 0, len, 0);
    	g.fillPolygon(new int[] {len, len-BOUNDS, len-BOUNDS, len}, 
    				  new int[] {0, -BOUNDS, BOUNDS, 0}, 4);
    }
    	
    	public void paintComponent(Graphics g) {
    		for (int x = 15; x < 200; x += 16)
    			drawArrow(g, x, x, x, 150);
    		drawArrow(g, 30, 300, 300, 190);
    	}
    	
    	public void paint(Graphics g){
    		Graphics2D go=(Graphics2D)g; 
    		go.setPaint(Color.black);
    		for(int i=1;i<=procs.size();i++){
    			go.drawLine(50,dis*i,1150,dis*i);
    		}
    		int i = 1;
    		int j = 1;

            try {
                for(NodeProcess n : procs){
                    j = 1;
                    for(LamportClock.TimeStamp v : n.getClock().getTS()){
                        go.setPaint(Color.black); 
                        go.fillOval(60*j,dis*i-3,5,5);
                        go.drawString("p"+i+"e"+j,60*j,dis*i-5);
                        if(v.nPrcss != (n.getUid())){
                            go.setPaint(Color.blue);
                            drawArrow(go,60*v.nEvnt+2,dis*v.nPrcss,60*j+2,dis*i);
                        }
                        j++;
                    }
                    i++;
                }
            } catch (Exception e) {

            }
    		
    	}
 }