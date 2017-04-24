package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Model {
	public HashMap<String, Device> devices_map;
	
	public Model(){
		devices_map = new HashMap<String, Device>();
		loadHashMap();
		
	}
	
	public void addDevice(String name, String ip, String username, String password){
		Device device = new Device(name, ip, username, password);
		devices_map.put(device.name, device);
		saveHashMap();
		System.out.println("Device added.");
	}
	
	public Device getDevice(String name){
		Device d = devices_map.get(name);
		return d;
		
	}
	
	public void deleteAllDevices(){
		devices_map.clear();
		saveHashMap();
	}
	
	
	public static Map<String, String> queryToMap(String query){
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) {
			String pair[] = param.split("=");
			if (pair.length>1) {
				result.put(pair[0], pair[1]);
			}else{
				result.put(pair[0], "");
			}
		}
		return result;
	}
	
	
	public void saveHashMap() {
		try {
			FileOutputStream fosStud = new FileOutputStream("model.ser");
			ObjectOutputStream oosStud = new ObjectOutputStream(fosStud);

			oosStud.writeObject(this.devices_map);
			oosStud.close();

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	@SuppressWarnings({ "unchecked" })
	//Loading the students map into a file
	public void loadHashMap() {
		try {
			FileInputStream fisStud = new FileInputStream("model.ser");
			ObjectInputStream oisStud = new ObjectInputStream(fisStud);

			devices_map = (HashMap) oisStud.readObject();
			oisStud.close();
			fisStud.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException c) {
			System.out.println("Class not found");
			c.printStackTrace();
			return;
		}

	}
}
