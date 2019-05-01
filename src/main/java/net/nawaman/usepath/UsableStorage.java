package net.nawaman.usepath;

import java.io.IOException;

/**
 * Takes care the load/save of the object
 **/
abstract public interface UsableStorage {

	/** Returns the name of the Storage */
	public String getName();

	/** Saves to data into the storaget (replace existing data) */
	public void save(byte[] Data) throws IOException ;
	
	/** Loads data output the storage */
	public byte[] load() throws IOException ;
	
	/** Checks if the storage iswritable */
	public boolean isWritable();
	
}
