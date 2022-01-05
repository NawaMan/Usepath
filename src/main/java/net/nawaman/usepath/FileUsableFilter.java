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
	static public FileFilter ACCEPT_ALL_FILEFILTER = new AcceptAllFileFilter();
	
	/** Accept no file FileFilter */
	static public FileFilter ACCEPT_NONE_FILEFILTER = new AcceptNoneFileFilter();
	
	/** Construct a UFFileFilter */
	public FileUsableFilter(FileFilter pFileFilter) {
		
		// Accept none if null is given
		if(pFileFilter == null) {
			pFileFilter = new FileFilter() {
			    /**{@inheritDoc}*/ @Override
			    public boolean accept(File pathname) {
			    	// Accept none
			    	return false;
			    }
			};
		}
		
		this.fileFilter = pFileFilter;
	}

	private final FileFilter fileFilter;
	
	public FileFilter filter() {
		return fileFilter;
	}
	
	/** Filters if the object with the given full name is match with the given name for the usable being search */
	public boolean isMatch(UsePath UPath, String FullName, String Name) {
		return this.fileFilter.accept(new File(FullName));
	}
	
	// Utility classes -------------------------------------------------------------------------------------------------  
	
	/** Accept all FileFilter */
	static public final class AcceptAllFileFilter implements FileFilter{
	    /**{@inheritDoc}*/ @Override
	    public boolean accept(File PathName) {
	    	// Accept true
	    	return true;
	    }
	};
	
	/** Accept None FileFilter */
	static public final class AcceptNoneFileFilter implements FileFilter{
	    /**{@inheritDoc}*/ @Override
	    public boolean accept(File PathName) {
	    	// Accept none
	    	return false;
	    }
	};
	
}