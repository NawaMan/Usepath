package net.nawaman.usepath;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Vector;

// FullName		=>	jar:file://<jvm>/lib/rt.jar::java.io.File	
// Name		 	=>	java.io.File
// ShortName	=>	File

/**
 * Usepaths helps to looks for thing usable in a similar fashion to Classpath of Java.
 * 
 * It has ability to include all class path of Java runtime.
 **/
abstract public class Usepaths {
	
	/** UsepathInfo Name => UsepathInfo */
	final protected HashMap<String, Usepath> Usepaths = new HashMap<String, Usepath>();
	
	/** Factory Name => Factory */
	HashMap<String, UsableFactory> UsableFactories = new HashMap<String, UsableFactory>();
	
	// Discovering -----------------------------------------------------------------------------------------------------
	
	/** Registre a usepath from string */
	abstract public void registerUsepath(String UPath);
	
	/** Returns the number of usepaths */
	final public int getUsepathCount() {
		return this.Usepaths.size();
	}
	
	/** Returns the use path as string */
	final public String[] getUsepaths() {
		Vector<String> UPs = new Vector<String>(this.Usepaths.keySet());
		Collections.sort(UPs);
		return UPs.toArray(new String[UPs.size()]);
	}

	// Look up ---------------------------------------------------------------------------------------------------------
	
	/** Object Cache */
	HashMap<String, UsableHolder<? extends Object>> Caches = new HashMap<String, UsableHolder<? extends Object>>();
	
	/** Returns the UsableHolder form the Cache */
	final protected UsableHolder<? extends Object> getUsableHolderFromCache(String Name) {
		return this.Caches.get(Name);
	}
	
	final protected void addUsableHolderToChache(String Name, UsableHolder<? extends Object> UH, boolean IsForceReplace) {
		if(!IsForceReplace && this.Caches.containsKey(Name)) return;
		
		if(Name == null) {
			if(UH == null) return;
			Name = UH.Name;
		}
		
		if(UH == null) this.Caches.remove(Name);
		else           this.Caches.put(Name, UH);
	}
	
	HashMap<String, UsableHolder<? extends Object>> UsableHolders = new HashMap<String, UsableHolder<? extends Object>>();
	
	public UsableHolder<? extends Object> getUsableHolder(String Name) {
		// Find in the cache
		UsableHolder<? extends Object> UH = this.UsableHolders.get(Name);
		if(UH != null) return UH;
		
		// Search
		int UFC = this.getUsableFilterCount();
		for(int i = 0; i < UFC; i++) {
			UsableFilter UF = this.getUsableFilter(i);
			UH = this.searchUsableHolder(UF, Name);
			if(UH != null) {
				this.UsableHolders.put(Name, UH);
				return UH;
			}
		}
		
		return null;
	}
	
	/** Search and a UsableHolder */
	public UsableHolder<? extends Object> searchUsableHolder(UsableFilter UFilter, String Name) {
		UsableStorage                  US = null;
		Usepath                        UP = null;
		UsableHolder<? extends Object> UH = null;
		
		for(String UPNs : this.Usepaths.keySet()) {
			UP = this.Usepaths.get(UPNs);
			if(UP == null) continue;
			US = UP.newUsableStorage(UFilter, Name);
			if(US == null) continue;
			break;
		}
		if(US == null) return null;
	
		for(String UFNs : this.UsableFactories.keySet()) {
			UsableFactory UF = this.UsableFactories.get(UFNs);
			if(UF == null) continue;
			UH = UF.getUsableHolder(UP, US);
			if(UH != null) return UH;
		}
		
		return null;
	}
	
	// Factory ---------------------------------------------------------------------------------------------------------
	
	/** Register a UsableFactory */
	protected boolean registerFactory(UsableFactory UFactory) {
		if(this.UsableFactories.get(UFactory.getName()) != null) return false;
		this.UsableFactories.put(UFactory.getName(), UFactory);
		return true;
	}
	
	/** Returns a registed UsableFactory */
	public UsableFactory getUsableFactory(String FactoryName) {
		return this.UsableFactories.get(FactoryName);
	}

	// Filter ----------------------------------------------------------------------------------------------------------
	
	/** Returns the number of Usage Filter this Usepath uses */
	abstract public int getUsableFilterCount();
	
	/** Returns the number of Usage Filter this Usepath uses at the index I */
	abstract public UsableFilter getUsableFilter(int I);
	
	// Utilities -------------------------------------------------------------------------------------------------------
	
	/** Gets all class path */
	protected String[] getClassPath() {
		String CPath = System.getProperty("java.class.path");
		return CPath.split(File.pathSeparator);
	}
	
	/** Register all system classpath as usepath */
	protected void registerAllClassPath() {
		String[] CPs = this.getClassPath();
		for(String CP : CPs)
			this.registerUsepath(CP);
	}
	
}
