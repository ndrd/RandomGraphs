	package lab.graph;

public class Message {
	public Integer finalDestUID = null;
	public Integer TTL = 10;
	private Integer evnt;
	private Integer orgn;
	
	public Message(Integer finalDestUID ){
		this.finalDestUID = finalDestUID;
	}

	public Message(Integer finalDestUID, Integer evnt, Integer orgn) {
		this.finalDestUID = finalDestUID;
		this.evnt = evnt;
		this.orgn = orgn;
	}

	public Integer getOrigin() {
		return this.orgn;
	}

	public Integer getDest() {
		return this.finalDestUID;
	}

	public Integer getEvent() {
		return this.evnt;
	}
}
