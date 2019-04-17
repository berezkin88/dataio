package com.javaTask.exceptions;

import java.io.FileNotFoundException;

public class NoSuchFileExc extends FileNotFoundException {

	public NoSuchFileExc(String s) {
		super(s);
	}

}
