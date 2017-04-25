package ssh;

import com.jcraft.jsch.*;

import model.Device;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SSHManagerOld {
	
	private static final Logger LOGGER = Logger.getLogger(SSHManagerOld.class.getName());
	private JSch jschSSHChannel;
	private String strUserName;
	private String strConnectionIP;
	private int intConnectionPort;
	private String strPassword;
	private Session sesConnection;
	private int intTimeOut;

	private void doCommonConstructorActions(String userName, String password, String connectionIP,
			String knownHostsFileName) {
		jschSSHChannel = new JSch();

		try {
			jschSSHChannel.setKnownHosts(knownHostsFileName);
		} catch (JSchException jschX) {
			logError(jschX.getMessage());
		}

		strUserName = userName;
		strPassword = password;
		strConnectionIP = connectionIP;
	}

	public SSHManagerOld(String userName, String password, String connectionIP, String knownHostsFileName) {
		doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
		intConnectionPort = 22;
		intTimeOut = 60000;
	}

	public SSHManagerOld(String userName, String password, String connectionIP, String knownHostsFileName,
			int connectionPort) {
		doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
		intConnectionPort = connectionPort;
		intTimeOut = 60000;
	}

	public SSHManagerOld(String userName, String password, String connectionIP, String knownHostsFileName,
			int connectionPort, int timeOutMilliseconds) {
		doCommonConstructorActions(userName, password, connectionIP, knownHostsFileName);
		intConnectionPort = connectionPort;
		intTimeOut = timeOutMilliseconds;
	}

	public String connect() {
		String errorMessage = null;

		try {
			sesConnection = jschSSHChannel.getSession(strUserName, strConnectionIP, intConnectionPort);
			sesConnection.setPassword(strPassword);
			// UNCOMMENT THIS FOR TESTING PURPOSES, BUT DO NOT USE IN PRODUCTION
			sesConnection.setConfig("StrictHostKeyChecking", "no");
			sesConnection.connect(intTimeOut);
		} catch (JSchException jschX) {
			errorMessage = jschX.getMessage();
		}

		return errorMessage;
	}

	private String logError(String errorMessage) {
		if (errorMessage != null) {
			LOGGER.log(Level.SEVERE, "{0}:{1} - {2}",
					new Object[] { strConnectionIP, intConnectionPort, errorMessage });
		}

		return errorMessage;
	}

	private String logWarning(String warnMessage) {
		if (warnMessage != null) {
			LOGGER.log(Level.WARNING, "{0}:{1} - {2}",
					new Object[] { strConnectionIP, intConnectionPort, warnMessage });
		}

		return warnMessage;
	}

	public String sendCommand(String command) {
		StringBuilder outputBuffer = new StringBuilder();

		try {
			Channel channel = sesConnection.openChannel("exec");
			((ChannelExec) channel).setCommand(command);
			InputStream commandOutput = channel.getInputStream();
			channel.connect();
			int readByte = commandOutput.read();

			while (readByte != 0xffffffff) {
				outputBuffer.append((char) readByte);
				readByte = commandOutput.read();
			}
			

			channel.disconnect();
		} catch (IOException ioX) {
			logWarning(ioX.getMessage());
			return null;
		} catch (JSchException jschX) {
			logWarning(jschX.getMessage());
			return null;
		}

		return outputBuffer.toString();
	}

	public void close() {
		sesConnection.disconnect();
	}
	
	
	public static String testSendCommand(Device d,String str) {
		System.out.println("sendCommand");

		/**
		 * YOU MUST CHANGE THE FOLLOWING FILE_NAME: A FILE IN THE DIRECTORY
		 * USER: LOGIN USER NAME PASSWORD: PASSWORD FOR THAT USER HOST: IP
		 * ADDRESS OF THE SSH SERVER
		 **/

		String tmp_command = "ls";
		String command = str;
		String userName = d.username;
		String password = d.password;
		String connectionIP = d.ip;
		SSHManagerOld instance = new SSHManagerOld(userName, password, connectionIP, "");
		String errorMessage = instance.connect();
		
		
		
		if (errorMessage != null) {
			System.out.println(errorMessage);
			//fail();
		}

		String expResult = "\n";
		// call sendCommand for each command and the output
		// (without prompts) is returned

		String result = instance.sendCommand(command);

		
		if(str.equals(tmp_command))
			instance.close();
		// close only after all commands are sent

		return result;
		//assertEquals(expResult, result);
	}

	private void assertEquals(String expResult, String result) {
		// TODO Auto-generated method stub
		
	}

	private void fail() {
		// TODO Auto-generated method stub
		
	}

}