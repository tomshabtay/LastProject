package run;

import static spark.Spark.*;

import controllers.DevicesControllers;
import model.Model;

public class Server {
	
	public static Model model;
	
	public static void main(String[] args) {
		
		model = new Model();
		
		get("devices/add/:query", DevicesControllers.addDevice);
		get("devices/list", DevicesControllers.listDevices);
		get("devices/delete-all", DevicesControllers.deleteAllDevices);
		get("devices/ssh/:name" , DevicesControllers.sshTest );
	}
	
}