package lab.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class NodeProcess extends Thread {

    public enum State {
        INACTIVE,
        RUNNING,
        FINISH_SUCCESFUL,
        RECEIVING,
        SENDING,
        ERROR,
        DIE
    }

	private Integer uid;
	private Set<Integer> neighbors;
	private MsgQueue queue;
	private Set<Integer> recepients;
    private ArrayList<String> log;
    private Integer evnts;
    private LamportClock clock;


	private State status; //0 - is running, 1 - received 5 messages, 2 - died by lifetime, 3 - died by an error 
	
	public NodeProcess  (Integer uid, Set<Integer> neighbors, Set<Integer> recepients) {
		this.uid = uid;
		
		this.neighbors = new HashSet<Integer>();
		this.neighbors.addAll(neighbors);
		
		this.recepients = new HashSet<Integer>();
		this.recepients.addAll(recepients);
		
		this.queue = MsgQueue.getInstance();
        this.status = State.INACTIVE;

        this.log =  new ArrayList<>();
        this.evnts = 0;

	}
	
	public void run(){
        System.out.println("STATUS : Process " + uid + " started");
        
        //kill process if it has no neighbors 
        if(neighbors.isEmpty()){
        	System.out.println("STATUS : Process " + uid + " nas no neighbors, stopped");
        	status = State.FINISH_SUCCESFUL;
        	return;
        }
        
        Integer received = 0;
        Integer lifetime = 100;
        
        try{
            System.out.println("STATUS [" + status + "]");
        	while(true){
        		//**************** SEND MESSAGE *****************
        		for(Integer finalDestUID : recepients) {
                    clock.add(LamportClock.stamp(uid, evnts));
                    Message mssg  = new Message(finalDestUID, uid, evnts++);
        			if(queue.send(mssg, getRandNeighbor())) {
                       // System.out.println("ESTADO : nodo " + uid + " emvia mensaje a nodo " + finalDestUID);
                        status = State.SENDING;
                        System.out.println("-------------");
                        log.add(mssg.getOrigin() + " envio un mensaje  a " + mssg.getDest() );
                        recepients.remove(finalDestUID);
        				break;
        			}
        		}
        		
        		//**************** RECEIVE MESSAGE **************
        		Message msg = queue.receive(uid);
                evnts++;
                clock.add(LamportClock.stamp(msg.getOrigin()+1, msg.getEvent() ));
        		
        		if(msg != null){
        			if(msg.finalDestUID == uid){
        				//msg reached its destination
        				received++;
        			}else{
        				if(msg.TTL > 0){
        					//scale down message TTL
        					msg.TTL--;
        					//resend same msg to the randomly selected neighbor
        					queue.send(msg, getRandNeighbor());
        				}
        				//if msg.TTL == 0 - the message has not reached its target within 10 tries and shell be forgotten
        			}
        		}
        		
        		//**************** CHECK RECEIVED ****************
        		if(received == 1){
					System.out.println("Process " + uid + " received all messages and finished");
                    status = State.RECEIVING;
					break;
				}
        		
        		//**************** CHECK LIFETIME ****************
        		lifetime--;
            	if(lifetime == 0){
            		System.out.println("Process " + uid + " lifetime finished");
                    status = State.FINISH_SUCCESFUL;
            		break;
            	}
        		
            	sleep(1);
            }
        }catch(Exception e){
        	System.err.println("Process " + uid + " died: " + e.getMessage());
            status = State.DIE;
        }
    }
	
	private Integer getRandNeighbor(){
		
		int stop = queue.getRandom(neighbors.size());
		Integer [] all = (Integer[]) neighbors.toArray(new Integer[0]);
		return all[queue.getRandom(neighbors.size())];
	}

	public State getExitState() {
		return status;
	}
	
	public Integer getUid(){
		return uid;
	}

     public Set<Integer> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(Set<Integer> neighbors) {
        System.out.println("cambiamos el conjunto de vecinos");
        this.neighbors = neighbors;
    }

    public MsgQueue getQueue() {
        return queue;
    }

    public void setQueue(MsgQueue queue) {
        System.out.println("ha cambiado la cola de mensajes");
        this.queue = queue;
    }

    public Set<Integer> getRecepients() {
        return recepients;
    }

    public void setRecepients(Set<Integer> recepients) {
        System.out.println("han cambiado los destinatarios");
        this.recepients = recepients;
    }  

    public LamportClock getClock() {
        return this.clock;
    }  

    public ArrayList<String> getLog() {
        return this.log;
    }    
}
