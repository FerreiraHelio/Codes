package br.com.spread.helio;

import java.io.IOException;
import java.util.Properties;

public class Configuracao {
	
	private String home, Backup;
	private Properties conf;
	
	
	public Configuracao() {
		loadConfiguration();
	}
	
	
	public void loadConfiguration() {
		
		conf = new Properties();
		try {
			conf.load(Configuracao.class.getResourceAsStream("/config/configuration.properties"));
			this.setHome(conf.getProperty("dirFiles"));
			this.setBackup(conf.getProperty("dirBackup"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return conf; 
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	public String getBackup() {
		return Backup;
	}
	public void setBackup(String backup) {
		Backup = backup;
	}

}
