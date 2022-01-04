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
import java.io.FilenameFilter;

/** Accepts the file with the extension. - This filter will reject all non-file object. */
abstract public class FileExtFilenameFilter implements FileExtFilter, FilenameFilter {
	
	/**
	 * Accepts the file with the extension.
	 * 
	 * @param Dir  is the parent directory of the file.
	 * @param Name is the name of the file with no extension in it.
	 * @param Ext  is the extension of the file with no name nor dot. If the file has not extension. "" is given.
	 **/
	public abstract boolean accept(File Dir, String Name, String Ext);
	
    /**{@inheritDoc}*/ @Override
    public boolean accept(File Dir, String Name) {
    	// Extract the file name and its extension 
    	int    Index = Name.lastIndexOf('.');
    	String Ext   = "";
    	if(Index > 0) {
    		Ext  = Name.substring(Index + 1);
    		Name = Name.substring(0, Index);
    	}
    	return this.accept(Dir, Name, Ext);
    }
    
    // Simple implementation ---------------------------------------------------------------------------------------
    
    /** Simple implementation */
    static public class Simple extends FileExtFilenameFilter {
    	
    	/** Constructs a simple implementation */
    	public Simple(FileExtFilter pFileExtFilter) {
    		if(pFileExtFilter == null) pFileExtFilter = new AcceptNoneFileExtFilter();
    		this.FileExtFilter = pFileExtFilter;
    	}
    	
    	FileExtFilter FileExtFilter;
    	
	    /**{@inheritDoc}*/ @Override
		public boolean accept(File Dir, String Name, String Ext) {
	    	return this.FileExtFilter.accept(Dir, Name, Ext);
	    }
    } 
    
}