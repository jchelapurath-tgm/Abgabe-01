package Test;

import java.sql.*;
import java.util.ArrayList;

public class Test {
	
	static ArrayList<Flughafen> flughafen= new ArrayList<Flughafen>();
	
	public static void main (String[] args) {
		//Commentar
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/list_3ahit", "root", "Chelj20101999");

			Statement myStat = myConn.createStatement();
			ResultSet myRs = myStat.executeQuery("select * from airports");
			while(myRs.next()){
				flughafen.add(new Flughafen(myRs.getString("country"), myRs.getString("city"), myRs.getString("airportcode"), myRs.getString("name")));
			}
			System.out.println("Fertig");
			System.out.println(flughafen.toString());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
