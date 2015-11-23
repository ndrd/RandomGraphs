package lab.graph;

import java.util.ArrayList;

public class LamportClock {

	private ArrayList<TimeStamp> stamps;
	private static TimeStamp st;

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

	public static TimeStamp stamp(int prcss, int evnt) {
		st.nPrcss = prcss;
		st.nEvnt = evnt;
		return st;
	}

	public ArrayList<TimeStamp> getTS() {
		return stamps;
	}


}