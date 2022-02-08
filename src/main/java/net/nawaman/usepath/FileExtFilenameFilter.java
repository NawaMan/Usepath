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

import java.io.File;
import java.io.FilenameFilter;
import java.util.Objects;

/**
 * Accepts the file with the extension.
 * 
 * This filter will reject all non-file object.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 */
abstract public class FileExtFilenameFilter implements FileExtFilter, FilenameFilter {
	
	/**
	 * Accepts the file with the extension.
	 * 
	 * @param dir  is the parent directory of the file.
	 * @param name is the name of the file with no extension in it.
	 * @param ext  is the extension of the file with no name nor dot. If the file
	 *             has not extension. "" is given.
	 **/
	public abstract boolean accept(File dir, String name, String ext);
	
	/** {@inheritDoc} */
	@Override
	public boolean accept(File dir, String name) {
		// Extract the file name and its extension
		int index = name.lastIndexOf('.');
		var ext   = "";
		if (index > 0) {
			ext  = name.substring(index + 1);
			name = name.substring(0, index);
		}
		return this.accept(dir, name, ext);
	}
	
	// Simple implementation ---------------------------------------------------------------------------------------
	
	/** Simple implementation */
	public static class Simple extends FileExtFilenameFilter {
		
		private final FileExtFilter fileExtFilter;
		
		/** Constructs a simple implementation */
		public Simple(FileExtFilter fileExtFilter) {
			this.fileExtFilter = Objects.requireNonNullElseGet(fileExtFilter, AcceptNoneFileExtFilter::new);
		}
		
		/** {@inheritDoc} */
		@Override
		public boolean accept(File dir, String name, String ext) {
			return this.fileExtFilter.accept(dir, name, ext);
		}
	}
	
}