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

import java.util.Optional;

// Resposibility
// 1. Provide a list of storage name (in the form of file name)
// 2. Provide the storage object
/**
 * A usepath that holds Usable Objects.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public abstract class Usepath {
	
	private final Usepaths usepaths;
	
	protected Usepath() {
		this(null);
	}
	
	protected Usepath(Usepaths usepaths) {
		this.usepaths = usepaths;
	}

	/** Returns the name of this Usepath */
	public abstract String name();
	
	/** Returns source usepaths that holds this Usepath */
	protected final Optional<Usepaths> source() {
		return Optional.ofNullable(this.usepaths);
	}

	/** Creates a Storage for the matched Usable */
	public abstract UsableStorage newUsableStorage(UsableFilter usableFilter, String name);
	
}
