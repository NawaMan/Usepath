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

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;

/**
 * Hold the object from the usepath.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
public abstract class UsableHolder<T> implements Supplier<T> {
	
	private final UsePath usepath;
	private final String  objectName;
	
	/** Constructs a UsableHolder */
	protected UsableHolder(UsePath usePath, String objectName) {
		this.usepath    = requireNonNull(usePath,    "`usePath` must not be null.");
		this.objectName = requireNonNull(objectName, "`objectName` must not be null.");
	}
	
	/** Returns the object name */
	public final String objectName() {
		return this.objectName;
	}
	
	/** Returns the usepath that load this object */
	public final UsePath getUsepath() {
		return this.usepath;
	}
	
	/** Returns the Object */
	public abstract T get();
	
}
