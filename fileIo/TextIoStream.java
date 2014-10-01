package fileIo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class creates an input-output stream for a file. If the file already
 * exists, it can append or rewrite the file. If the file does not exist, it
 * will create one.
 * 
 * Much of the basic file IO including choice of readers and writers, their 
 * syntax, and exception handling, was based on Example 4 with permission from
 * the following web page: 
 * http://www.javapractices.com/topic/TopicAction.do?Id=42
 * 		Copyright (c) 2002-2009, Hirondelle Systems
 * 		All rights reserved.
 * 
 * @author Natalie Rawle
 */

public class TextIoStream {

	private final File file;
	
	// error messages
	private final String ERROR_NULL_FILE = "File should not be null.";
	private final String ERROR_FILE_NOT_FOUND = "Could not create or find file: %1$s";
	private final String ERROR_NOT_A_FILE = "Should not be a directory: %1$s";
	private final String ERROR_UNWRITABLE = "File cannot be written: %1$s";

	public TextIoStream(String fileName) throws IOException {
		file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
	}

	public String getFileName() {
		return file.getName();
	}

	/**
	 * @return a List of Strings containing the lines of text from the file.
	 * Each String is a separate line from the file.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public List<String> readFromFile()
			throws FileNotFoundException, IOException {
		BufferedReader inputStream = new BufferedReader(new FileReader(file));
		List<String> lines = new ArrayList<String>();

		try {
			String line = inputStream.readLine();
			while (line != null) {
				lines.add(line);
				line = inputStream.readLine();
			}
		} finally {
			inputStream.close();
		}

		return lines;
	}

	/**
	 * This method erases the old data from the file and refills the file using
	 * the contents from a list of strings. Each string in the list will become
	 * a new line within the file.
	 * 
	 * @param textForFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public <T> void rewriteFile(List<T> textForFile) 
			throws FileNotFoundException, IOException {
		checkValidityOfFile();
		
		BufferedWriter outputStream = new BufferedWriter(new FileWriter(file));
		
		try {
			for (int i = 0; i < textForFile.size(); ++i) {
				String lineOfText = textForFile.get(i).toString();
				outputStream.write(lineOfText);
				outputStream.newLine();
			}
		} finally {
			outputStream.close();	
		}
	}

	/**
	 * This method appends a list of strings to the end of a file. Each string
	 * in the list will become a new line within the file. One blank line will
	 * be added at the end of the file.
	 * 
	 * @param textForFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public <T> void appendFile(List<T> textForFile)
			throws FileNotFoundException, IOException {
		checkValidityOfFile();
		
		BufferedWriter outputStream = 
				new BufferedWriter(new FileWriter(file,true));
		
		try {
			for (int i = 0; i < textForFile.size(); ++i) {
				String lineOfText = textForFile.get(i).toString();
				outputStream.append(lineOfText);
				outputStream.newLine();
			}
		} finally {
			outputStream.close();	
		}
	}

	/**
	 * This method appends a string to the end of the file and creates a new
	 * line after appending.
	 * 
	 * @param textForFile
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public <T> void appendFile(T textForFile)
			throws FileNotFoundException, IOException {
		checkValidityOfFile();
		BufferedWriter outputStream = 
				new BufferedWriter(new FileWriter(file,true));
		try {
			outputStream.append(textForFile.toString());
			outputStream.newLine();
		} finally {
			outputStream.close();	
		}
	}

	/**
	 * This method checks whether or not it is safe to read from or write to
	 * the file.
	 * 
	 * @throws IllegalArgumentException
	 * @throws FileNotFoundException
	 */
	private void checkValidityOfFile() 
			throws IllegalArgumentException, FileNotFoundException {
		String message;
		if (file == null) {
			message = String.format(ERROR_NULL_FILE);
			throw new IllegalArgumentException(message);
		}
		if (!file.exists()) {
			message = String.format(ERROR_FILE_NOT_FOUND, file);
			throw new FileNotFoundException (message);
		}
		if (!file.isFile()) {
			message = String.format(ERROR_NOT_A_FILE, file);
			throw new IllegalArgumentException(message);
		}
		if (!file.canWrite()) {
			message = String.format(ERROR_UNWRITABLE, file);
			throw new IllegalArgumentException(message);
		}
	}
}
