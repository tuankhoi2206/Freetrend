package fv.components;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * FileWrapper which stores content in a tmp-file.
 * 
 * This is just an example and should be replaced by your own implementation
 * depending on your real-storage.
 * 
 * @author wiki
 */
public class FileWrapper {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FileWrapper.class);

	private String name;

	private String type;

	private String path;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Retrieves content from tmp-file.
	 */
	public byte[] getContent() throws IOException {
		File file = new File(path);
		byte[] content = new byte[(int) file.length()];
		FileInputStream inputStream = new FileInputStream(file);
		inputStream.read(content);
		inputStream.close();
		return content;
	}

	public File getFile() {
		return new File(path);
	}

	/**
	 * Stores content in a tmp-file.
	 * 
	 * The current milliseconds are used as a "semi-unique" prefix for the
	 * temporary file. This works usually fine for demonstration purposes
	 * without much concurrency. Please use a synchronized "real" unique
	 * sequence for production use.
	 */
	public synchronized void setContent(byte[] content) throws IOException {
		File tmpFile = File.createTempFile(String.valueOf(System
				.currentTimeMillis()), null);
		// Play nicely and delete files when the application stops...
		tmpFile.deleteOnExit();
		path = tmpFile.getCanonicalPath();
		if (logger.isDebugEnabled()){
			logger.debug(path);
		}
		FileOutputStream outputStream = new FileOutputStream(tmpFile);
		outputStream.write(content);
		outputStream.close();
	}

	public void deleteCurrentFile() {
		File f = new File(path);
		if (f.exists()) {
			f.delete();
			if (logger.isDebugEnabled()){
				logger.debug("deleteCurrentFile(): Current file was deleted");
			}
		}
	}

	public long getSize() {
		return new File(path).length();
	}

	@Override
	public String toString() {
		return name + " (" + type + ")" + " [" + getSize() / 1024 + " KB]";
	}
}
