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
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Usepath for file in folder
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public class UPFileInFolder extends Usepath {
	
	public UPFileInFolder(File Folder) {
		this.Folder = Folder;
		if((this.Folder == null) || !this.Folder.exists() || !this.Folder.isDirectory() || !this.Folder.canRead())
			throw new RuntimeException(
					new FileNotFoundException(
						"Not a readable folder "+((this.Folder != null)?"("+Folder+")":"")+""));
	}
	
	public UPFileInFolder(String FolderPath) {
		this(new File(FolderPath));
	}
	
	File Folder;
	
	/** Returns the folder */
	public File getFolder() {
		return this.Folder;
	}
	
	/**{@inheritDoc}*/ @Override
	public String name() {
		return this.Folder.getAbsolutePath();
	}

	/**{@inheritDoc}*/ @Override
	public UsableStorage newUsableStorage(UsableFilter UFilter, String Name) {
		if(UFilter == null) return null;
		
		String[] Parts = Name.split("/");
		
		String PathName = "";
		String FileName = "";
		if(Parts.length == 1) FileName = Parts[0];
		else {
			FileName = Parts[Parts.length - 1];
			PathName = Name.substring(0, Name.length() - FileName.length());
		}
		
		File Path = this.Folder;
		if(PathName.length() != 0) {
			Path = new File(Path.getAbsolutePath() + File.separator + PathName);
			if(!Path.exists()) return null;
		}
		
		File[] Fs = null;
		
		if(     UFilter instanceof UFFileFilter)     Fs = Path.listFiles(((UFFileFilter)    UFilter).FileFilter);
		else if(UFilter instanceof UFFilenameFilter) Fs = Path.listFiles(((UFFilenameFilter)UFilter).FilenameFilter);
		else                                         Fs = Path.listFiles();
		
		for(int i = 0; i < Fs.length; i++) {
			File F = Fs[i];
			if(!UFilter.isMatch(this, F.getAbsolutePath(), Name))
				continue;
			
			// Match
			try { return new USFile(F); }
			catch (IOException IOE) {}
		}
		
		return null;
	}

}
