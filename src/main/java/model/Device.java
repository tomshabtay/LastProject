package model;

import java.io.Serializable;

public class Device implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String name;
	public String ip;
	public String username;
	public String password;
	
	public Device(String name, String ip, String username, String password){
		this.name = name;
		this.ip = ip;
		this.password = password;
		this.username = username;
	}
	
}
