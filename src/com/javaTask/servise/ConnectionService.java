package com.javaTask.servise;

import java.util.Random;

import com.javaTask.entity.Connection;

public class ConnectionService {

	public static Connection createConnection() {
		return new Connection(System.currentTimeMillis(), randomPort(), randomIP());
	}

	private static String randomIP() {
		return new Random().nextInt(256) + "." + new Random().nextInt(256) + "." + new Random().nextInt(256) + "."
				+ new Random().nextInt(24);
	}

	private static int randomPort() {
		return 100000000 + new Random().nextInt(1000000000 - 100000000);
	}
}
