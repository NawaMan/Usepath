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
public class UFFileExtFilter extends UFFilenameFilter {
	
	/** Construct a UFFileExtFilter */
	public UFFileExtFilter(FileExtFilter pFileExtFilter) {
		super(new FileExtFilenameFilter.Simple(pFileExtFilter));
	}
	
	/**
	 * Filters if the object with the given full name is match with the given name
	 * for the usable being search
	 */
	public boolean isMatch(UsePath UPath, String FullName, String Name) {
		if (Name == null)
			return false;
		File F = new File(FullName);
		if (!this.filter().accept(F.getParentFile(), F.getName()))
			return false;
		
		int NLength = Name.length();
		// int StartIndex = FullName.lastIndexOf(File.separator);
		int EndIndex;
		if ((EndIndex = FullName.lastIndexOf('.')) == -1)
			EndIndex = FullName.length();
		
		// Try as no dot and with dot
		if ((Name.indexOf('.') != -1) && Name.equals(FullName.substring(EndIndex - NLength)))
			return true;
		if ((Name.indexOf('.') != -1) && Name.equals(FullName.substring(FullName.length() - NLength)))
			return true;
		if (Name.equals(FullName.substring(EndIndex - NLength, EndIndex)))
			return true;
		if (Name.equals(FullName.substring(FullName.length() - NLength, EndIndex)))
			return true;
		return false;
	}
}
