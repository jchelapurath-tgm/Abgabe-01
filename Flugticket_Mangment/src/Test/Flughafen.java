package Test;

import java.util.Hashtable;

public class Flughafen {
	
	String Land;
	String name;
	String FlullLand;
	Hashtable<String,String> airportcode = new Hashtable<String, String>();
	Hashtable<String, Hashtable> flughafen1 = new Hashtable<String, Hashtable>();
	

	public Flughafen(String Land, Hashtable<String,String> s, String name, String FullLand) {
		this.airportcode = s;
		flughafen1.put(Land, s);
		this.name = name;
		this.FlullLand = FullLand;
	}

	public String getLand() {
		return Land;
	}
	
	public String getFlullLand() {
		return FlullLand;
	}


	public void setFlullLand(String flullLand) {
		FlullLand = flullLand;
	}


	public void setLand(String land) {
		Land = land;
	}


	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Flughafen [Land=" + Land + ", Stadt=" + Stadt + ", airportcode=" + airportcode + ", name=" + name + "]" + "\n";
	}

	public void setName(String name) {
		this.name = name;
	}

}
