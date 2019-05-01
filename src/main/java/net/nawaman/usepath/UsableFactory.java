package net.nawaman.usepath;

/** Creates UsableHolder from the given Storage */
public interface UsableFactory {
	
	/** Returns the name of the Factory */
	public String getName();
	
	/** Create the UsableHolder from the given paramters */
	public UsableHolder<? extends Object> getUsableHolder(Usepath Path, UsableStorage Storage);

}
