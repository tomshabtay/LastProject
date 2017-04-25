package server;

import static spark.Spark.*;

import controllers.DevicesControllers;
import controllers.SSHControllers;
import model.Device;
import model.DeviceManager;
import spark.Request;
import spark.Response;
import spark.Route;
import ssh.SSHManager;
import view.Home;

public class Server {
	
	//The Model
	public static DeviceManager device_manager;
	
	//Stop route
	public static Route stopServer = (Request request, Response response) -> {
		stopServer();
		return "Server Stoped";
	};
	
	public static Route index = (Request request, Response response) -> {
		
		return Home.str;
	};
	
	
	
	//Constructor
	public Server() {
		device_manager = new DeviceManager();
		setRoutes();
	}

	
	public void setRoutes() {
		//Devices Managment
		get("devices/add/:query", DevicesControllers.addDevice);
		get("devices/list", DevicesControllers.listDevices);
		get("devices/delete-all", DevicesControllers.deleteAllDevices);
		
		//SSH
		get("ssh/test/:name", SSHControllers.sshTest);
		get("ssh/do/:name/:command", SSHControllers.sshRunCommand);
		//get("ssh/ses-start/:name/:", SSHControllers.sshRunCommand);

		
		//Server functions
		get("stop", stopServer);
		get("", stopServer);
		

	}

	
	public static void stopServer() {
		stop();
	}

}