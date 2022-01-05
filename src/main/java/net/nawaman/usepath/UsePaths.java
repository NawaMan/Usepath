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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import net.nawaman.usepath.utils.IStream;

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
public abstract class UsePaths {
	
	/** Usepath Name => Usepath */
	private final Map<String, UsePath> usepaths = new ConcurrentHashMap<String, UsePath>();
	
	/** Factory Name => Factory */
	private final Map<String, UsableFactory> usableFactories = new ConcurrentHashMap<String, UsableFactory>();
	
	/** UsableHolder Name => UsableHolder */
	private final Map<String, UsableHolder<? extends Object>> usableHolders = new ConcurrentHashMap<String, UsableHolder<? extends Object>>();
	
	// Discovering -----------------------------------------------------------------------------------------------------
	
	/** Register a usepath from string */
	abstract public void registerUsepath(String UPath);
	
	/** Register a usepath */
	void registerUsepath(String name, UsePath usepath) {
		usepaths.put(name, usepath);
	}
	
	/** Returns the use path as string */
	public final IStream<String> usepaths() {
		return IStream.forSupplier(() -> usepaths.keySet().stream().sorted());
	}
	
	// Look up ---------------------------------------------------------------------------------------------------------
	
	@SuppressWarnings("unchecked")
	public <T> UsableHolder<T> getUsableHolder(String name) {
		var holder = usableHolders.computeIfAbsent(name, __ -> {
			return usableFilters()
					.map   (usableFilter -> searchUsableHolder(usableFilter, name))
					.filter(usableHolder -> usableHolder != null)
					.findFirst()
					.orElse(null);
		});
		return (UsableHolder<T>)holder;
	}
	
	/** Search and a UsableHolder */
	@SuppressWarnings("unchecked")
	public <T> UsableHolder<T> searchUsableHolder(UsableFilter usableFilter, String name) {
		UsableStorage useableStorage = null;
		UsePath       usePath        = null;
		
		for(var usePathName : usepaths.keySet()) {
			usePath = usepaths.get(usePathName);
			if(usePath == null)
				continue;
			
			useableStorage = usePath.newUsableStorage(usableFilter, name);
			if(useableStorage == null)
				continue;
			
			break;
		}
		if(useableStorage == null)
			return null;
		
		for(String usePathName : usableFactories.keySet()) {
			var usableFactory = usableFactories.get(usePathName);
			if(usableFactory == null)
				continue;
			
			var usableHolder = usableFactory.getUsableHolder(usePath, useableStorage);
			if(usableHolder != null)
				return (UsableHolder<T>)usableHolder;
		}
		
		return null;
	}
	
	// Factory ---------------------------------------------------------------------------------------------------------
	
	/** Register a UsableFactory */
	protected boolean registerFactory(UsableFactory usableFactory) {
		var factoryName = usableFactory.getName();
		var factory     = usableFactories.putIfAbsent(factoryName, usableFactory);
		return factory == null;
	}
	
	/** Returns a registered UsableFactory */
	public UsableFactory getUsableFactory(String factoryName) {
		return usableFactories.get(factoryName);
	}
	
	// Filter ----------------------------------------------------------------------------------------------------------
	
	/** @return  the usable filters that this UsePath uses. */
	abstract public IStream<UsableFilter> usableFilters();
	
	// Utilities -------------------------------------------------------------------------------------------------------
	
	/** Gets all class path */
	protected IStream<String> getClassPaths() {
		var classPath  = System.getProperty("java.class.path");
		var classPaths = classPath.split(File.pathSeparator);
		return IStream.forSupplier(() -> Stream.of(classPaths));
	}
	
	/** Register all system classpath as usepath */
	protected void registerAllClassPath() {
		var classPaths = this.getClassPaths();
		classPaths.forEach(this::registerUsepath);
	}
	
}
