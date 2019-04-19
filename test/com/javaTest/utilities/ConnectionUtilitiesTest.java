package com.javaTest.utilities;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.javaTask.entity.Connection;
import com.javaTask.exceptions.IOExc;
import com.javaTask.exceptions.NoItemsExc;
import com.javaTask.exceptions.NoSuchFileExc;
import com.javaTask.utilities.ConnectionUtilities;

class ConnectionUtilitiesTest {
	private static File testFile = new File(System.getProperty("user.dir") 
			+ File.separator + "log" + File.separator + "connectionsTest.txt");
	private Connection con = new Connection(1555400000000l, 111111111, "111.111.11.1");

	@Test
	void testWriteLineToFile() {

		try {
			testFile.delete();
			ConnectionUtilities.writeLineToFile(con.printConnection(), testFile);
		} catch (NoSuchFileExc | IOExc e) {
			e.printStackTrace();
		}

		assertTrue(testFile.exists());
	}

	@Test
	void testReadFromFile() {
		List<Connection> testCon = null;

		try {
			testCon = ConnectionUtilities.readFromFile(testFile);
		} catch (NoSuchFileExc e) {
			e.printStackTrace();
		}

		assertTrue(testCon != null);
		assertTrue(testCon.get(0).equals(con));
	}

	@Test
	void testRemoveOldRecords() {
		Connection con2 = new Connection(1455400000000l, 222222222, "222.222.222.2");
		List<Connection> testCon = null;

		try {
			ConnectionUtilities.writeLineToFile(con2.printConnection(), testFile);
			testFile = ConnectionUtilities.removeOldRecords(testFile);
			testCon = ConnectionUtilities.readFromFile(testFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue(testCon != null);
		assertFalse(testCon.get(0).equals(con2));
	}

	@Test
	void testSearchThroughSuccess() {
		List<Connection> testResCon = null;

		try {
			testResCon = ConnectionUtilities.searchThrough(testFile, 1505400000000l, 1605400000000l);
		} catch (NoSuchFileExc | NoItemsExc e) {
			e.printStackTrace();
		}

		assertTrue(testResCon != null);
		assertTrue(testResCon.get(0).equals(con));
	}

	@Test
	void testSearchThroughFail() {
		assertThrows(NoItemsExc.class, () -> ConnectionUtilities.searchThrough(testFile, 1605400000000l, 1705400000000l));
	}

}
