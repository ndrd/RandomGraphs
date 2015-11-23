package lab.graph;

import java.util.ArrayList;

public class LamportClock {

	private ArrayList<TimeStamp> stamps;



	class TimeStamp {
		int nPrcss;
		int nEvnt;

		public TimeStamp (int nPrcss, int nEvnt) {
			this.nPrcss = nPrcss;
			this.nEvnt = nEvnt;
		}

	}

	public LamportClock() {
		this.stamps = new ArrayList<TimeStamp>();
	}

	public void add(TimeStamp ts) {
		stamps.add(ts);
	}

	public TimeStamp stamp(int prcss, int evnt) {
		return new TimeStamp(prcss, evnt);
	}

	public ArrayList<TimeStamp> getTS() {
		return stamps;
	}


}