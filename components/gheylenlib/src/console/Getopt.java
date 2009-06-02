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

package lib.console;

import java.util.HashMap;

public final class Getopt
{
	public static HashMap<String, String> parsePars(String[] pars)
	{
		HashMap<String, String> mPars = new HashMap<String, String>();
		
		for(int i = 0; i < pars.length; i++)
			if(pars[i].startsWith("-") && (i + 1) < pars.length && !pars[i + 1].startsWith("-"))
				mPars.put(pars[i].replace("-", ""), pars[i + 1]);
		
		return mPars;
	}
}
