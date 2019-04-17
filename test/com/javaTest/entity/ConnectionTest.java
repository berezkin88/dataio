package com.javaTest.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.javaTask.entity.Connection;

class ConnectionTest {

	@Test
	void testPrintConnection() {
		Connection test = new Connection(1455452813316l, 585073018, "172.217.44.2");
		
		assertEquals("1455452813316 585073018 172.217.44.2\n", test.printConnection());
	}

}
