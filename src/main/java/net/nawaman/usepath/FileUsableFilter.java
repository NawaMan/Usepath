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

import static java.util.Objects.requireNonNullElse;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter the Usable by its file.
 * 
 * Filter the Usable. This class allows file to be filtered before isMatch(...) function is called.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public class FileUsableFilter implements UsableFilter {
	
	/** Accept all file FileFilter */
	public static final FileFilter ACCEPT_ALL_FILEFILTER = new AcceptAllFileFilter();
	
	/** Accept no file FileFilter */
	public static final FileFilter ACCEPT_NONE_FILEFILTER = new AcceptNoneFileFilter();
	
	private final FileFilter fileFilter;
	
	/** Construct a UFFileFilter */
	public FileUsableFilter(FileFilter fileFilter) {
		// Accept none if null is given
		this.fileFilter = requireNonNullElse(fileFilter, ACCEPT_NONE_FILEFILTER);
	}
	
	public FileFilter filter() {
		return fileFilter;
	}
	
	/**
	 * Filters if the object with the given full name is match with the given name
	 * for the usable being search
	 */
	public boolean isMatch(UsePath usePath, String fullName, String name) {
		return fileFilter.accept(new File(fullName));
	}
	
	// Utility classes -------------------------------------------------------------------------------------------------
	
	/** Accept all FileFilter */
	public static final class AcceptAllFileFilter implements FileFilter {
		/** {@inheritDoc} */
		@Override
		public boolean accept(File PathName) {
			// Accept true
			return true;
		}
	}
	
	/** Accept None FileFilter */
	public static final class AcceptNoneFileFilter implements FileFilter {
		/** {@inheritDoc} */
		@Override
		public boolean accept(File PathName) {
			// Accept none
			return false;
		}
	}
	
}