package controllers;

import java.util.Iterator;
import java.util.Map;

import javax.print.attribute.standard.RequestingUserName;

import model.Device;
import model.Model;
import run.Server;
import spark.Request;
import spark.Response;
import spark.Route;
import ssh.SSHManager;

public class DevicesControllers {
	
	public static Route addDevice = (Request request, Response response) -> {
		
		Map<String,String> map = Model.queryToMap(request.params(":query"));
		Server.model.addDevice(map.get("name"), map.get("ip"), map.get("username"), map.get("password"));
		Device d = Server.model.getDevice(map.get("name"));
		if(d != null){
			return "Added Device:" + d.name + "\n" + d.ip + "\n" + d.username + "\n" + d.password;
		}
		else{
			return "problem";
		}
	};
	
	
	public static Route listDevices = (Request request, Response response) -> {
		
		StringBuilder str = new StringBuilder();
		int count = 1;
		
	    Iterator it = Server.model.devices_map.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        Device d = (Device) pair.getValue();
	        str.append(count + ". " + "Name: " + d.name + " Ip: " + d.ip + "\n");
	        
	        count++;
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    return str.toString();
	    
	};


	public static Route deleteAllDevices = (Request request, Response response) -> {
		
		Server.model.deleteAllDevices();
		return "deleted.";
	    
	};


	public static Route sshTest = (Request request, Response response) -> {
		
		Device d = Server.model.getDevice(request.params(":name"));
		String str = SSHManager.testSendCommand(d,"cd Desktop");
		str = SSHManager.testSendCommand(d,"ls");
		
		return str;
		
		
	    
	};
	

}
