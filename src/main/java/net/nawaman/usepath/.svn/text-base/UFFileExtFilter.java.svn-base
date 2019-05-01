package net.nawaman.usepath;

import java.io.File;

/**
 * UsableFilter from its file filter.
 * 
 * Filter the Usable. This class allows file to be filtered by its extension before isMatch(...) function is called.
 **/
public class UFFileExtFilter extends UFFilenameFilter {
	
	/** Construct a UFFileExtFilter */
	public UFFileExtFilter(FileExtFilter pFileExtFilter) {
		super(new FileExtFilenameFilter.Simple(pFileExtFilter));
	}

	/** Filters if the object with the given full name is match with the given name for the usable being search */
	public boolean isMatch(Usepath UPath, String FullName, String Name) {
		if(Name == null) return false;
		File F = new File(FullName);
		if(!this.FilenameFilter.accept(F.getParentFile(), F.getName()))
			return false;
		
		int NLength = Name.length();
		//int StartIndex = FullName.lastIndexOf(File.separator);
		int EndIndex;
		if((EndIndex = FullName.lastIndexOf('.')) == -1)
			EndIndex = FullName.length();
		
		// Try as no dot and with dot
		if((Name.indexOf('.') != -1) && Name.equals(FullName.substring(EndIndex          - NLength)))           return true;
		if((Name.indexOf('.') != -1) && Name.equals(FullName.substring(FullName.length() - NLength)))           return true;
		if(                             Name.equals(FullName.substring(EndIndex          - NLength, EndIndex))) return true;
		if(                             Name.equals(FullName.substring(FullName.length() - NLength, EndIndex))) return true;
		return false;
	}
}
