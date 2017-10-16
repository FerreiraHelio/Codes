package br.com.spread.helio;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;



public class SeparaMain implements Runnable{

	Configuracao config;

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Iniciou");
		
		config = new Configuracao();
		config.loadConfiguration();		
		LimpaDir(efetuaBackup(separaArquivos(preparaDiretorios())));

	}
		
	
	Predicate<File> onlyFile = new Predicate<File>() {

		public boolean apply(File arg0) {
			// TODO Auto-generated method stub

			return !arg0.isDirectory();
			
			
		}

	};
	
	
	Predicate<File> onlyFileHasFolder = new Predicate<File>() {

		public boolean apply(File arg0) {
			// TODO Auto-generated method stub
			
			boolean contemPastaDest = false;
			File home = new File(Paths.get(config.getHome()).toString()+File.separator+arg0.getName().substring(0, 1));
			
			if (home.exists()) {
				contemPastaDest = true;
			}
			
			System.out.println(new StringBuilder("Diretório existe : ").append(home.getAbsolutePath()).append(" ").append(contemPastaDest));
			
			return contemPastaDest;
			
		}

	};
	
	public List<File> preparaDiretorios(){
				
		try { 
				
			File home = new File(Paths.get(config.getHome()).toString());
			File bkp = new File(Paths.get(config.getBackup()).toString());
			
			if (!bkp.exists()) {
				if (!bkp.mkdirs()) {
					throw new Exception("Erro na criacao do path de bkp.");
				}
			}
			
			if (!home.exists()) {
				throw new Exception("Diretorio de arquivos inexistente");
			}
			
			System.out.println("Diretorios Válidados!");
		}catch (Exception ex){ 
			ex.printStackTrace();
		}

		System.out.println("Listando arquivos para mover...");
		return FluentIterable.from(Arrays.asList(new File(Paths.get(config.getHome()).toString()).listFiles())).filter(onlyFile).filter(onlyFileHasFolder).toList();
	}
	
	public List<File> separaArquivos(List<File> listFiles){
		
		for(File files : Iterables.filter(listFiles, onlyFile)) {
			
			try {
				System.out.println("Separando arquivo "+files.getName());
				com.google.common.io.Files.copy(files, new File(files.getParent()+File.separator+files.getName().substring(0, 1).toLowerCase()+File.separator+files.getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Erro na separacao dos arquivos....");
				e.printStackTrace();
			}
			
		}
		
		return listFiles;
	}
	
	public List<File> efetuaBackup(List<File> listFile){
		
		for(File files : Iterables.filter(listFile, onlyFile)) {

			try {
				System.out.println("Efetuando backup de :"+files.getName());
				com.google.common.io.Files.copy(files, new File(config.getBackup()+File.separator+files.getName()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Erro no backup");
				e.printStackTrace();
			}

		}

		return listFile;
	}

	public void LimpaDir(List<File> listFile) {
		
		System.out.println("Efetuando limpeza...");
		for(File files : Iterables.filter(listFile, onlyFile)) {

			//com.google.common.io.Files.copy(files, new File(files.getAbsolutePath()+File.separator+config.getBackup()));
			files.delete();

		}		
	}

}
