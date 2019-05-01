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