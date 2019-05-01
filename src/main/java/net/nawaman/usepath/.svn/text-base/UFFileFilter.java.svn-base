package net.nawaman.usepath;

import java.io.File;
import java.io.FileFilter;

/**
 * Filter the Usable by its file.
 * 
 * Filter the Usable. This class allows file to be filtered before isMatch(...) function is called.
 **/
public class UFFileFilter implements UsableFilter {
	
	/** Accept all file FileFilter */
	static public FileFilter ACCEPT_ALL_FILEFILTER = new AcceptAllFileFilter();
	
	/** Accept no file FileFilter */
	static public FileFilter ACCEPT_NONE_FILEFILTER = new AcceptNoneFileFilter();
	
	/** Construct a UFFileFilter */
	public UFFileFilter(FileFilter pFileFilter) {
		
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
		
		this.FileFilter = pFileFilter;
	}

	FileFilter FileFilter;

	/** Filters if the object with the given full name is match with the given name for the usable being search */
	public boolean isMatch(Usepath UPath, String FullName, String Name) {
		return this.FileFilter.accept(new File(FullName));
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