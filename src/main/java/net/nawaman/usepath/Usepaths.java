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
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
	
	/** UsepathInfo Name => UsepathInfo */
	final Map<String, Usepath> usepaths = new HashMap<String, Usepath>();
	
	/** Factory Name => Factory */
	HashMap<String, UsableFactory> UsableFactories = new HashMap<String, UsableFactory>();
	
	// Discovering -----------------------------------------------------------------------------------------------------
	
	/** Registre a usepath from string */
	abstract public void registerUsepath(String UPath);
	
	/** Returns the number of usepaths */
	final public int getUsepathCount() {
		return this.usepaths.size();
	}
	
	/** Returns the use path as string */
	final public String[] getUsepaths() {
		Vector<String> UPs = new Vector<String>(this.usepaths.keySet());
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
		
		for(String UPNs : this.usepaths.keySet()) {
			UP = this.usepaths.get(UPNs);
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
