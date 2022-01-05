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
public class UsePathFileInFolder extends UsePath {
	
	private final File folder;
	
	public UsePathFileInFolder(File folder) {
		this.folder = folder;
		if((this.folder == null) || !this.folder.exists() || !this.folder.isDirectory() || !this.folder.canRead()) {
			var errMessage   = "Not a readable folder " + ((this.folder != null)?"("+folder+")":"");
			var fileNotFound = new FileNotFoundException(errMessage);
			throw new RuntimeException(fileNotFound);
		}
	}
	
	public UsePathFileInFolder(String folderPath) {
		this(new File(folderPath));
	}
	
	/** Returns the folder */
	public File folder() {
		return folder;
	}
	
	/**{@inheritDoc}*/ @Override
	public String name() {
		return folder.getAbsolutePath();
	}

	/**{@inheritDoc}*/ @Override
	public UsableStorage newUsableStorage(UsableFilter usableFilter, String name) {
		if(usableFilter == null)
			return null;
		
		var parts    = name.split("/");
		var pathName = "";
		var fileName = "";
		if(parts.length == 1) {
			fileName = parts[0];
		} else {
			fileName = parts[parts.length - 1];
			pathName = name.substring(0, name.length() - fileName.length());
		}
		
		var path = folder;
		if(pathName.length() != 0) {
			path = new File(path.getAbsolutePath() + File.separator + pathName);
			if(!path.exists())
				return null;
		}
		
		File[] files = files(usableFilter, path);
		
		for(int i = 0; i < files.length; i++) {
			var file = files[i];
			if(!usableFilter.isMatch(this, file.getAbsolutePath(), name))
				continue;
			
			// Match
			try { return new UsableStorageFile(file); }
			catch (IOException IOE) {}
		}
		
		return null;
	}
	
	private File[] files(UsableFilter usableFilter, File path) {
		if(usableFilter instanceof FileUsableFilter)
			return path.listFiles(((FileUsableFilter)usableFilter).filter());
		
		if(usableFilter instanceof FilenameUsableFilter)
			return path.listFiles(((FilenameUsableFilter)usableFilter).filter());
		
		return path.listFiles();
	}

}
