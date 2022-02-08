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
import java.io.FilenameFilter;

/**
 * Filter the Usable. This class allows file to be filtered by its name before isMatch(...) function is called.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
abstract public class FilenameUsableFilter implements UsableFilter {
	
	/** Accept all file FilenameFilter */
	public static final FilenameFilter ACCEPT_ALL_FILENAMEFILTER = new AcceptAllFilenameFilter();
	
	/** Accept no file FilenameFilter */
	public static final FilenameFilter ACCEPT_NONE_FILENAMEFILTER = new AcceptNoneFilenameFilter();
	
	private final FilenameFilter fileNameFilter;
	
	/** Construct a UFFilenameFilter */
	public FilenameUsableFilter(FilenameFilter filenameFilter) {
		this.fileNameFilter = requireNonNullElse(filenameFilter, ACCEPT_NONE_FILENAMEFILTER);
	}
	
	public FilenameFilter filter() {
		return fileNameFilter;
	}
	
	// Utility classes -------------------------------------------------------------------------------------------------
	
	/** Accept all FileFilter */
	public static final class AcceptAllFilenameFilter implements FilenameFilter {
		/** {@inheritDoc} */
		@Override
		public boolean accept(File dir, String name) {
			// Accept true
			return true;
		}
	}
	
	/** Accept None FileFilter */
	public static final class AcceptNoneFilenameFilter implements FilenameFilter {
		/** {@inheritDoc} */
		@Override
		public boolean accept(File dir, String name) {
			// Accept none
			return false;
		}
	}
	
}
