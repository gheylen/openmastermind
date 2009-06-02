/*  openMastermind Copyright (c) 2008 Glenn Heylen

	This file is part of openMastermind.

    openMastermind is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    openMastermind is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with openMastermind.  If not, see <http://www.gnu.org/licenses/>.*/

package gheylenlib.jar;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

/**
 * This can be used to obtain resources (such as images) in a JAR file.
 * 
 * Note: Should set the root class first. (e.g.: "ResourceLocater.setRootClass(<someclass>.getClass());")
 * Note: Resources must be in the same source folder as the root class defined.
 * Note: Resources can only be read.
 * Note: Is suppressing unchecked warnings! (Class is a raw type. Might be fixed in the future)
 */
@SuppressWarnings("unchecked")
public final class ResourceLocater
{   
	private static Class clas;
	
	static
	{
		clas = ResourceLocater.class.getClass();
	}
	
	/**
	 * 
	 * @param c
	 */
	public static void setRootClass(Class c)
	{
		clas = c;
	}
		
	/**
	 * Note: This is the 'abstract' method of the ResourceLocater: should not be called directly.
	 * 
	 * @param path
	 * @return
	 */
	public static URL getResource(String path)
	{
		return clas.getResource(path);
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
    public static Image getImage(String path)
	{
    	return Toolkit.getDefaultToolkit().getImage(getResource(path));
    }
}