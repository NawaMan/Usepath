package net.nawaman.usepath;


// Resposibility
// 1. Provide a list of storage name (in the form of file name)
// 2. Provide the storage object
/**
 * A usepath that holds Usable Objects
 **/
abstract public class Usepath {
	
	Usepaths UPs;
	
	/** Returns the name of this Usepath */
	abstract public String getName();
	
	/** Returns Usepaths that holds this Usepath */
	final protected Usepaths getUsepaths() {
		return this.UPs;
	}

	/** Creates a Storage for the matched Usable */
	abstract public UsableStorage newUsableStorage(UsableFilter UFilter, String Name);
	
}