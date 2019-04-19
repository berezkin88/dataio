package com.javaTask.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.javaTask.applicationRunner.AppRunner;
import com.javaTask.entity.Connection;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.NoItemsExc;
import com.javaTask.exceptions.NoSuchFileExc;

public final class ConnectionUtilities {
	private static final Logger log = Logger.getLogger(ConnectionUtilities.class.getName());

	public static List<Connection> readFromFile(File file) throws NoSuchFileExc {
		StringBuilder buffer = new StringBuilder();
		List<Connection> cons = new ArrayList<>();

		try (Scanner scanner = new Scanner(file)) {

			while (scanner.hasNext()) {
				buffer.append(scanner.nextLine());
				String[] str = buffer.toString().split(" ");
				cons.add(new Connection(Long.parseLong(str[0]), Integer.parseInt(str[1]), str[2]));
				buffer.delete(0, buffer.length());
			}

		} catch (FileNotFoundException e) {
			throw new NoSuchFileExc("Failed to find the file");
		}

		return cons;
	}

	public static File writeLineToFile(String str, File file) throws NoSuchFileExc, IOExc {

		try (OutputStream os = new FileOutputStream(file, true)) {
			os.write(str.getBytes());
			os.write("\n".getBytes());
		} catch (FileNotFoundException e) {
			throw new NoSuchFileExc("Failed to find the file");
		} catch (IOException e) {
			throw new IOExc("Failed to read data bytes");
		}

		return file;
	}

	public static File removeOldRecords(File file) throws NoSuchFileExc {
		List<Connection> tempLst = readFromFile(file);
		File updated = new File(System.getProperty("user.dir") 
				+ File.separator + "log" + File.separator + file.getName());
		long threeDays = System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 3);

		// filter out old records and write to file
		tempLst.stream().filter(e -> e.getTimestamp() > threeDays).forEach(e -> {

			try {
				writeLineToFile(e.printConnection(), updated);
			} catch (NoSuchFileExc | IOExc e1) {
				log.info("Exception occured while writing to the file");
			}
				
		});

		return updated;
	}

	public static List<Connection> searchThrough(File file, long from, long till) throws NoSuchFileExc, NoItemsExc {
		List<Connection> input = readFromFile(file);
		List<Connection> result = new ArrayList<>();

		input.stream().filter(e -> e.getTimestamp() > from && e.getTimestamp() < till).forEach(e -> result.add(e));

		if (result.isEmpty())
			throw new NoItemsExc("There are no matching connections found");

		return result;
	}
}
