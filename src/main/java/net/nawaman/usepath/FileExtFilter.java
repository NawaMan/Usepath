/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (C) 2008-2021 Nawapunth Manusitthipol. Implements with and for Java 11 JDK.
 *----------------------------------------------------------------------------------------------------------------------
 * LICENSE:
 * 
 * This file is part of Nawa's SimpleCompiler.
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * File filter by its extension.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public interface FileExtFilter {
	
	/** Accept all file FilenameFilter */
	static public FileExtFilter ACCEPT_ALL_FILEEXTFILTER = new AcceptAllFileExtFilter();
	
	/** Accept no file FilenameFilter */
	static public FileExtFilter ACCEPT_NONE_FILEEXTFILTER = new AcceptNoneFileExtFilter();
	
	/** Accepts the file with the extension */
	public boolean accept(File Dir, String Name, String Ext);
	
	// Simple Implementation
	// -------------------------------------------------------------------------------------------
	
	/** Accept all FileFilter */
	static public final class AcceptAllFileExtFilter implements FileExtFilter {
		/** {@inheritDoc} */
		@Override
		public boolean accept(File Dir, String Name, String Ext) {
			// Accept true
			return true;
		}
	};
	
	/** Accept None FileFilter */
	static public final class AcceptNoneFileExtFilter implements FileExtFilter {
		/** {@inheritDoc} */
		@Override
		public boolean accept(File Dir, String Name, String Ext) {
			// Accept none
			return false;
		}
	};
	
	/** Filter the extension using the given extension list */
	static public final class FEFExtList implements FileExtFilter {
		public FEFExtList(String... Exts) {
			Accepts = new HashSet<String>(Arrays.asList(Exts));
		}
		
		private final HashSet<String> Accepts;
		
		/** {@inheritDoc} */
		@Override
		public boolean accept(File Dir, String Name, String Ext) {
			return Accepts.contains(Ext);
		}
	};
	
	/** Filter the extension using the given regular expression */
	static public final class FEFRegExp implements FileExtFilter {
		public FEFRegExp(Pattern pExtPattern) {
			this.ExtPattern = pExtPattern;
		}
		
		final Pattern ExtPattern;
		
		/** {@inheritDoc} */
		@Override
		public boolean accept(File Dir, String Name, String Ext) {
			// Accept true
			Matcher M = ExtPattern.matcher(Ext);
			return M.find();
		}
	};
}