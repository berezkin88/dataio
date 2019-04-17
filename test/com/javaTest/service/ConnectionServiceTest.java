package com.javaTest.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.javaTask.servise.ConnectionService;

class ConnectionServiceTest {
	
	@Test
	void testCreateConnection() {
		assertTrue(ConnectionService.createConnection() != null);
	}

}
