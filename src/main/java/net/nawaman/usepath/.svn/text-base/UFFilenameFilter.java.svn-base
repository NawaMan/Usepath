package net.nawaman.usepath;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Filter the Usable. This class allows file to be filtered by its name before isMatch(...) function is called.
 **/
abstract public class UFFilenameFilter implements UsableFilter {
	
	/** Accept all file FilenameFilter */
	static public FilenameFilter ACCEPT_ALL_FILENAMEFILTER = new AcceptAllFilenameFilter();
	
	/** Accept no file FilenameFilter */
	static public FilenameFilter ACCEPT_NONE_FILENAMEFILTER = new AcceptNoneFilenameFilter();

	/** Construct a UFFilenameFilter */
	public UFFilenameFilter(FilenameFilter pFilenameFilter) {
		
		// Accept none if null is given
		if(pFilenameFilter == null) pFilenameFilter = ACCEPT_NONE_FILENAMEFILTER;
		
		this.FilenameFilter = pFilenameFilter;
	}

	FilenameFilter FilenameFilter;

	
	
	// Utility classes -------------------------------------------------------------------------------------------------  
	
	/** Accept all FileFilter */
	static public final class AcceptAllFilenameFilter implements FilenameFilter{
	    /**{@inheritDoc}*/ @Override
	    public boolean accept(File dir, String name) {
	    	// Accept true
	    	return true;
	    }
	};
	
	/** Accept None FileFilter */
	static public final class AcceptNoneFilenameFilter implements FilenameFilter {
	    /**{@inheritDoc}*/ @Override
	    public boolean accept(File dir, String name) {
	    	// Accept none
	    	return false;
	    }
	};
}
