package no.hin.student.y2013.grp2it.simuleringsmotor;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilMonitor {
	Path xmlInputPath = null;
	Path xmlOutputPath = null;
	
	public FilMonitor()
	{
//		xmlInputPath = Paths.get(".\\input\\");
		xmlInputPath = Paths.get("/home/gruppe2/new/");
		
//		xmlOutputPath = Paths.get(".\\output\\");
		xmlOutputPath = Paths.get("/home/gruppe2/done/");
	}

	/*
	 * start prosessen som sjekker etter nye filer.. 
	 */
	public void doRun()
	{
		for ( ;; )
		{
			try {
				Thread.currentThread().sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			checkForNewFiles();
		}
	}
	
	/*
	 * Se etter nye filer, og kall SimuleringsMotor ved nye filer
	 */
	public void checkForNewFiles()
	{
		System.out.printf("Ser etter nye filer..\n");
		
		try ( DirectoryStream<Path> stream = Files.newDirectoryStream(xmlInputPath)) {
		    for (Path file: stream) {
//		        System.out.println(file.getFileName());
		        
		        if ( runSimuleringsMotor(file) )
		        {
		        	File tmpFile = new File(xmlOutputPath.toFile().getPath() + "/" + file.toFile().getName()); 
		        	
//		        	System.err.printf("Filename: %s\n", tmpFile.getAbsolutePath(), tmpFile.getName());
//		        	System.exit(2);
		        	
		        	file.toFile().renameTo(tmpFile);
		        }
		    }
		} catch (IOException | DirectoryIteratorException x) {
		    // IOException can never be thrown by the iteration.
		    // In this snippet, it can only be thrown by newDirectoryStream.
		    System.err.println(x);
		}		
		
		System.out.printf("Ferdig å se etter nye filer..\n");
	}
	
	/*
	 * Start simulering
	 */
	public boolean runSimuleringsMotor(Path file)
	{
		System.out.println("Kjører simulering..");
		SimuleringsMotor.simMotor = new SimuleringsMotor();
		SimuleringsMotor.simMotor.setFile(file.toFile());
		SimuleringsMotor.simMotor.doRun();
		
		SimuleringsMotor.simMotor = null;
		return true;
	}
	
	/*
	 * Les inn filen til en String
	 */
	public String readFile(Path file)
	{
		StringBuilder sb = new StringBuilder();
		
		
		
		return sb.toString();
	}
}
