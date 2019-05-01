package net.nawaman.usepath;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class USFile implements UsableStorage {
	
	public USFile(String TheFileName) throws FileNotFoundException {
		this(new java.io.File(TheFileName));
	}
	
	public USFile(java.io.File theFile) throws FileNotFoundException {
		if(theFile == null) throw new NullPointerException();
		this.TheFile = theFile;
		
		if(!this.TheFile.exists() || !this.TheFile.canRead())
			throw new FileNotFoundException("The goven file is not found or not a readable file.");
	}
	
	File TheFile;
	
	/**{@inheritDoc}*/ @Override
	public String getName() {
		return this.TheFile.getName();
	}
	
	/** Returns the file of this storage */
	public File getTheFile() {
		return this.TheFile;
	}
	
	/**{@inheritDoc}*/ @Override
	synchronized public void save(byte[] Data) throws IOException {
		OutputStream OS = null;
		try {
			OS = new FileOutputStream(this.TheFile);
			OS.write(Data);
		} finally {
			if(OS != null) {
				OS.flush();
				OS.close();
			}
		}
		return;
	}
	
	/** Loads data output the storage */
	synchronized public byte[] load() throws IOException {
		InputStream IS = null;
		try {
			IS = new FileInputStream(this.TheFile);
			long Length = this.TheFile.length();
			
			if (Length > Integer.MAX_VALUE)
				throw new IOException("The file is too large: " + this.TheFile.getAbsolutePath());
						
			byte[] Data = new byte[(int)Length];
			IS.read(Data);
			
			return Data;
		} finally {
			if(IS != null) IS.close();
		}
	}
	
	/** Checks if the storage iswritable */
	public boolean isWritable() {
		return this.TheFile.canWrite();
	}

}
