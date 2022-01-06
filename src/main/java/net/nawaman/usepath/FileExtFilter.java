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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * File filter by its extension.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public interface FileExtFilter {
	
	/** Accept all file FilenameFilter */
	public static FileExtFilter ACCEPT_ALL_FILEEXTFILTER = new AcceptAllFileExtFilter();
	
	/** Accept no file FilenameFilter */
	public static FileExtFilter ACCEPT_NONE_FILEEXTFILTER = new AcceptNoneFileExtFilter();
	
	/** Accepts the file with the extension */
	public boolean accept(File dir, String name, String ext);
	
	// Simple Implementation
	// -------------------------------------------------------------------------------------------
	
	/** Accept all FileFilter */
	public static final class AcceptAllFileExtFilter implements FileExtFilter {
		/** {@inheritDoc} */
		@Override
		public boolean accept(File dir, String name, String ext) {
			// Accept true
			return true;
		}
	}
	
	/** Accept None FileFilter */
	public static final class AcceptNoneFileExtFilter implements FileExtFilter {
		/** {@inheritDoc} */
		@Override
		public boolean accept(File dir, String name, String ext) {
			// Accept none
			return false;
		}
	}
	
	/** Filter the extension using the given extension list */
	public static final class FEFExtList implements FileExtFilter {
		
		private final Set<String> accepts;
		
		public FEFExtList(String... exts) {
			accepts = new HashSet<String>(Arrays.asList(exts));
		}
		
		/** {@inheritDoc} */
		@Override
		public boolean accept(File dir, String name, String ext) {
			return accepts.contains(ext);
		}
	}
	
	/** Filter the extension using the given regular expression */
	static public final class FEFRegExp implements FileExtFilter {
		
		private final Pattern extPattern;
		
		public FEFRegExp(Pattern extPattern) {
			this.extPattern = extPattern;
		}
		
		/** {@inheritDoc} */
		@Override
		public boolean accept(File dir, String name, String ext) {
			// Accept true
			var matcher = extPattern.matcher(ext);
			return matcher.find();
		}
	}
	
}