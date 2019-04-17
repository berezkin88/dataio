package com.javaTask.applicationRunner;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import com.javaTask.entity.Connection;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.NoItemsExc;
import com.javaTask.exceptions.NoSuchFileExc;
import com.javaTask.servise.ConnectionService;
import com.javaTask.utilities.ConnectionUtilities;


public class AppRunner {
	private static Logger log = Logger.getLogger(AppRunner.class.getName());
	private static File file = new File("C:\\Users\\Alexander\\eclipse-workspace\\dataio\\log\\connections.txt");

	public static void main(String[] args) {
		try {
			populateFile(file);
		} catch (NoSuchFileExc | IOExc e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void populateFile(File file) throws NoSuchFileExc, IOExc {
		for (int i = 0; i < 10; i++) {
			ConnectionUtilities.writeLineToFile(ConnectionService.createConnection().printConnection(), file);
		}
	}
}
