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

/**
 * Hold the object.
 * 
 * @author Nawapunth Manusitthipol (https://github.com/NawaMan)
 **/
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
