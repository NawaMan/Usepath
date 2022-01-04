/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (C) 2008-2021 Nawapunth Manusitthipol. Implements with and for Java 11 JDK.
 *----------------------------------------------------------------------------------------------------------------------
 * LICENSE:
 * 
 * This file is part of Nawa's Usepath.
 * 
 * The project is a free software; you can redistribute it and/or modify it under the SIMILAR terms of the GNU General
 * Public License as published by the Free Software Foundation; either version 2 of the License, or any later version.
 * You are only required to inform me about your modification and redistribution as or as part of commercial software
 * package. You can inform me via nawa<at>nawaman<dot>net.
 * 
 * The project is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the 
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 * ---------------------------------------------------------------------------------------------------------------------
 */
package net.nawaman.usepath;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

// FullName		=>	jar:file://<jvm>/lib/rt.jar::java.io.File	
// Name		 	=>	java.io.File
// ShortName	=>	File

/**
 * Usepaths helps to looks for thing usable in a similar fashion to Classpath of Java.
 * 
 * It has ability to include all class path of Java runtime.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public abstract class Usepaths {
	
	/** Usepath Name => Usepath */
	private final Map<String, Usepath> usepaths = new ConcurrentHashMap<String, Usepath>();
	
	/** Factory Name => Factory */
	private final Map<String, UsableFactory> usableFactories = new ConcurrentHashMap<String, UsableFactory>();
	
	/** Object Cache */
	private final Map<String, UsableHolder<? extends Object>> caches = new ConcurrentHashMap<String, UsableHolder<? extends Object>>();
	
	/** UsableHolder Name => UsableHolder */
	private final Map<String, UsableHolder<? extends Object>> usableHolders = new ConcurrentHashMap<String, UsableHolder<? extends Object>>();
	
	// Discovering -----------------------------------------------------------------------------------------------------
	
	/** Registre a usepath from string */
	abstract public void registerUsepath(String UPath);
	
	/** Register a usepath from string */
	void registerUsepath(String name, Usepath usepath) {
		this.usepaths.put(name, usepath);
	}
	
	
	/** Returns the number of usepaths */
	public final int getUsepathCount() {
		return this.usepaths.size();
	}
	
	/** Returns the use path as string */
	public final String[] getUsepaths() {
		Vector<String> UPs = new Vector<String>(this.usepaths.keySet());
		Collections.sort(UPs);
		return UPs.toArray(new String[UPs.size()]);
	}

	// Look up ---------------------------------------------------------------------------------------------------------
	
	/** Returns the UsableHolder form the Cache */
	protected final UsableHolder<? extends Object> getUsableHolderFromCache(String Name) {
		return this.caches.get(Name);
	}
	
	protected final void addUsableHolderToChache(String Name, UsableHolder<? extends Object> UH, boolean IsForceReplace) {
		if(!IsForceReplace && this.caches.containsKey(Name)) return;
		
		if(Name == null) {
			if(UH == null) return;
			Name = UH.Name;
		}
		
		if(UH == null) this.caches.remove(Name);
		else           this.caches.put(Name, UH);
	}
	
	public UsableHolder<? extends Object> getUsableHolder(String Name) {
		// Find in the cache
		UsableHolder<? extends Object> UH = this.usableHolders.get(Name);
		if(UH != null) return UH;
		
		// Search
		int UFC = this.getUsableFilterCount();
		for(int i = 0; i < UFC; i++) {
			UsableFilter UF = this.getUsableFilter(i);
			UH = this.searchUsableHolder(UF, Name);
			if(UH != null) {
				this.usableHolders.put(Name, UH);
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
		
		for(String UPNs : this.usepaths.keySet()) {
			UP = this.usepaths.get(UPNs);
			if(UP == null) continue;
			US = UP.newUsableStorage(UFilter, Name);
			if(US == null) continue;
			break;
		}
		if(US == null) return null;
	
		for(String UFNs : this.usableFactories.keySet()) {
			UsableFactory UF = this.usableFactories.get(UFNs);
			if(UF == null) continue;
			UH = UF.getUsableHolder(UP, US);
			if(UH != null) return UH;
		}
		
		return null;
	}
	
	// Factory ---------------------------------------------------------------------------------------------------------
	
	/** Register a UsableFactory */
	protected boolean registerFactory(UsableFactory UFactory) {
		if(this.usableFactories.get(UFactory.getName()) != null) return false;
		this.usableFactories.put(UFactory.getName(), UFactory);
		return true;
	}
	
	/** Returns a registed UsableFactory */
	public UsableFactory getUsableFactory(String FactoryName) {
		return this.usableFactories.get(FactoryName);
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
