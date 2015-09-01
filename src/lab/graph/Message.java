package lab.graph;

public class Message {
	public Integer finalDestUID = null;
	public Integer TTL = 10;
	
	public Message(Integer finalDestUID){
		this.finalDestUID = finalDestUID;
	}
}
