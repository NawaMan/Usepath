package net.nawaman.usepath;

/** Hold the object */
abstract public class UsableHolder<T> {
	
	Usepath Usepath;
	String  Name;
	
	/** Constructs a UsableHolder */
	protected UsableHolder(Usepath pUsepath, String pName) {
		if(pUsepath   == null) throw new NullPointerException();
		if(pName      == null) throw new NullPointerException();
		
		this.Usepath = pUsepath;
		this.Name    = pName;
	}
	
	/** Returns the object name */
	final public String getName() {
		return this.Name;
	}

	/** Returns the usepath that load this object */
	final public Usepath getUsepath() {
		return this.Usepath;
	}
	
	/** Returns the Object */
	abstract public T get();

}
