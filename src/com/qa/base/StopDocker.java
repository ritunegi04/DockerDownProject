package com.qa.base;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import org.junit.Assert;

public class StopDocker {
	
	public static void main(String[] arg) throws InterruptedException,IOException
	{
		Runtime runtime=Runtime.getRuntime();
		FileReader fr;
		BufferedReader br;
		boolean flag=false;
		Calendar cal=Calendar.getInstance();
		cal.add(Calendar.SECOND, 300);
		long stopNow=cal.getTimeInMillis();		
			File file=new File("DockerStop.bat");
			runtime.exec("cmd /c start "+file);
			File f; 
			Thread.sleep(45000);
			while(System.currentTimeMillis()<stopNow)
			{
				if(flag==true)
					break;
			f=new File("..\\performance-testing-framework\\DockerDown.txt");
			if(!f.exists())
			{
				continue;
			}
			fr = new FileReader(f);
			br=new BufferedReader(fr);
			String currLine=br.readLine();
			
			while(currLine!=null && !flag)
			{
				if(currLine.contains("Removing network performance-testing-framework_default"))
					{
					flag=true;
					break;
					}
				
				currLine=br.readLine();
			}
			br.close();
			}
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("..\\performance-testing-framework\\DockerDown.txt", 
					true)));
		try
		{
	Assert.assertTrue(flag);
		out.println("Docker services stopped successfully");
		}
		catch(AssertionError e)
		{
		out.println("Issue Occurred while stopping docker services");
		}
	finally
	{
		out.close();
		runtime.exec("taskkill /f /im cmd.exe");
	}
	}


}
