package utils;

import java.io.BufferedReader;  
import java.io.IOException; 
import java.io.InputStreamReader;


public class Comandos {

	public String ExecuteCmd(String cmdArgs) throws IOException{
		Runtime runtime = Runtime.getRuntime();  
		  
	    //String cmds[] = {"cmd", "/c", "tasklist"};
		String cmdsArgs[] = cmdArgs.split(" ");
		String cmds[] = new String[2+cmdsArgs.length];
		cmds[0]="/bin/bash";cmds[1]="-c";
		
		for(int i=0;i<cmdsArgs.length;i++){
			cmds[i+2] = cmdsArgs[i];
		}
		
	    Process proc = runtime.exec(cmds);  

	    BufferedReader bufferedreader = new BufferedReader(
	    									new InputStreamReader(
	    											proc.getInputStream()
	    									)
	    								);  

	    String line;
	    String result="";

	    while ((line = bufferedreader.readLine()) != null) {

	        result += line+"\r\n";       

	    }  
	    return result;
	}
	
	public String getUsuarioLogado(){
		return System.getProperty("user.name");
	}
	
	public String getPastaUsuario(){
		return System.getProperty("user.home");
	}
    
}
