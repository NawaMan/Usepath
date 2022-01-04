/*----------------------------------------------------------------------------------------------------------------------
 * Copyright (C) 2008-2021 Nawapunth Manusitthipol. Implements with and for Java 11 JDK.
 *----------------------------------------------------------------------------------------------------------------------
 * LICENSE:
 * 
 * This file is part of Nawa's SimpleCompiler.
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

// Resposibility
// 1. Provide a list of storage name (in the form of file name)
// 2. Provide the storage object
/**
 * A usepath that holds Usable Objects.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
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