package net.nawaman.usepath;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** File filter by its extension */
public interface FileExtFilter {
	
	/** Accept all file FilenameFilter */
	static public FileExtFilter ACCEPT_ALL_FILEEXTFILTER = new AcceptAllFileExtFilter();
	
	/** Accept no file FilenameFilter */
	static public FileExtFilter ACCEPT_NONE_FILEEXTFILTER = new AcceptNoneFileExtFilter();
	
	
	
	/** Accepts the file with the extension */
	public boolean accept(File Dir, String Name, String Ext);

	
	
	
	// Simple Implementation -------------------------------------------------------------------------------------------  
	
	/** Accept all FileFilter */
	static public final class AcceptAllFileExtFilter implements FileExtFilter {
	    /**{@inheritDoc}*/ @Override
	    public boolean accept(File Dir, String Name, String Ext) {
	    	// Accept true
	    	return true;
	    }
	};
	
	/** Accept None FileFilter */
	static public final class AcceptNoneFileExtFilter implements FileExtFilter {
	    /**{@inheritDoc}*/ @Override
	    public boolean accept(File Dir, String Name, String Ext) {
	    	// Accept none
	    	return false;
	    }
	};
  
	/** Filter the extension using the given extension list */
	static public final class FEFExtList implements FileExtFilter {
		public FEFExtList(String ... Exts) {
			Accepts = new HashSet<String>(Arrays.asList(Exts));
		}
		private final HashSet<String> Accepts;
		
	    /**{@inheritDoc}*/ @Override
	    public boolean accept(File Dir, String Name, String Ext) {
	    	return Accepts.contains(Ext);
	    }
	};
  
	
	/** Filter the extension using the given regular expression */
	static public final class FEFRegExp implements FileExtFilter {
		public FEFRegExp(Pattern pExtPattern) {
			this.ExtPattern = pExtPattern;
		}
		final Pattern ExtPattern;
	    /**{@inheritDoc}*/ @Override
	    public boolean accept(File Dir, String Name, String Ext) {
	    	// Accept true
	    	Matcher M = ExtPattern.matcher(Ext);
			return M.find();
	    }
	};
} 