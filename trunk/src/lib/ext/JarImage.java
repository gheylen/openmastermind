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

package lib.ext;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

public final class JarImage
{   
	private static Class clas;
	
	static
	{
		clas = JarImage.class.getClass();
	}
	
	public static void setClass(Class c)
	{
		clas = c;
	}
	
    public static Image getImage(String pathName)
	{
    	URL url = clas.getResource(pathName); 
    	return Toolkit.getDefaultToolkit().getImage(url);
     }
}