package com.javaTask.applicationRunner;

import java.io.File;
import java.util.logging.Logger;

import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.IntExc;
import com.javaTask.exceptions.NoSuchFileExc;
import com.javaTask.servise.ConnectionService;
import com.javaTask.utilities.ConnectionUtilities;

public class AppRunner {

	private static Logger log = Logger.getLogger(AppRunner.class.getName());
	private static File file = new File(
			System.getProperty("user.dir") + File.separator + "log" + File.separator + "connections.txt");

	public static void main(String[] args) {

		Runnable r = () -> {
			log.info("about to begin looping in thread " + Thread.currentThread().getName());

			for (int i = 0; i < 3; i++) {
				log.info("filling out file in thread " + Thread.currentThread().getName());
				try {
					populateFile(file);
				} catch (NoSuchFileExc | IOExc | IntExc e) {
					e.printStackTrace();
				}
				log.info("finished filling out file in thread " + Thread.currentThread().getName());

			}

			log.info("complete operation in thread " + Thread.currentThread().getName());
		};

		Thread a = new Thread(r, "A");
		a.start();

		Thread b = new Thread(r, "B");
		b.start();

		Thread c = new Thread(r, "C");
		c.start();
	}

	private static synchronized void populateFile(File file) throws NoSuchFileExc, IOExc, IntExc {
		for (int i = 0; i < 10; i++) {
			ConnectionUtilities.writeLineToFile(ConnectionService.createConnection().printConnection(), file);
		}
		
		try {
			Thread.sleep(1000 * 60 * 3);
		} catch (InterruptedException e) {
			throw new IntExc("The thread " + Thread.currentThread().getName() + " was interrupted");
		}
	}
}
