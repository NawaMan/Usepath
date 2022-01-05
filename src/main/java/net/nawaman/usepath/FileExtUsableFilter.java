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

/**
 * UsableFilter from its file filter.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public class FileExtUsableFilter extends FilenameUsableFilter {
	
	/** Construct a FileExtUsableFilter */
	public FileExtUsableFilter(FileExtFilter fileExtFilter) {
		super(new FileExtFilenameFilter.Simple(fileExtFilter));
	}
	
	/**
	 * Filters if the object with the given full name is match with the given name for the usable being search
	 */
	public boolean isMatch(UsePath usePath, String fullName, String name) {
		if (name == null)
			return false;
		
		var file = new File(fullName);
		if (!filter().accept(file.getParentFile(), file.getName()))
			return false;
		
		int nameLength = name.length();
		int endIndex;
		if ((endIndex = fullName.lastIndexOf('.')) == -1) {
			endIndex = fullName.length();
		}
		
		// Try as no dot and with dot
		int dotIndex = name.indexOf('.');
		if ((dotIndex != -1) && name.equals(fullName.substring(endIndex - nameLength)))
			return true;
		
		if ((dotIndex != -1) && name.equals(fullName.substring(fullName.length() - nameLength)))
			return true;
		
		if (name.equals(fullName.substring(endIndex - nameLength, endIndex)))
			return true;
		
		if (name.equals(fullName.substring(fullName.length() - nameLength, endIndex)))
			return true;
		
		return false;
	}
}
