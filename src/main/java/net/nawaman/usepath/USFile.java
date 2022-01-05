/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (C) 2008-2021 Nawapunth Manusitthipol. Implements with and for Java 11 JDK.
 *----------------------------------------------------------------------------------------------------------------------
 * LICENSE:
 * 
 * This file is part of Nawa's Usepath.
 * 
 * The project is a free software; you can redistribute it and/or modify it under the SIMILAR terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the License, or any later version.
 * You are only required to inform me about your modification and redistribution as or as part of commercial software
 * package. You can inform me via nawa<at>nawaman<dot>net.
 * 
 * The project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * ---------------------------------------------------------------------------------------------------------------------
 */
package net.nawaman.usepath;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Usable storage in a file.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 */
public class USFile implements UsableStorage {
	
	private final File file;
	
	public USFile(String fileName) throws FileNotFoundException {
		this(new java.io.File(fileName));
	}
	
	public USFile(java.io.File file) throws FileNotFoundException {
		this.file = requireNonNull(file, "`file` cannot be null.");
		
		if(!this.file.exists() || !this.file.canRead())
			throw new FileNotFoundException("The goven file is not found or not a readable file.");
	}
	
	/**{@inheritDoc}*/ @Override
	public String name() {
		return file.getName();
	}
	
	/** Returns the file of this storage */
	public File file() {
		return file;
	}
	
	/**{@inheritDoc}*/ @Override
	public synchronized void save(byte[] Data) throws IOException {
		try (var outputStream = new FileOutputStream(file)) {
			outputStream.write(Data);
		}
	}
	
	/** Loads data output the storage */
	synchronized public byte[] load() throws IOException {
		try (var inputStream = new FileInputStream(file)) {
			long Length = file.length();
			
			if (Length > Integer.MAX_VALUE)
				throw new IOException("The file is too large: " + file.getAbsolutePath());
						
			var Data = new byte[(int)Length];
			inputStream.read(Data);
			
			return Data;
		}
	}
	
	/** Checks if the storage isWritable */
	public boolean isWritable() {
		return file.canWrite();
	}

}
