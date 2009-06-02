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

package lib.collection.bag;

/**
 * Cycles through the bag without deleting the nodes on pop. (Similar to a queue, but leaves nodes untouched on pop)
 */
public final class CycleBag extends Abstract
{
	private int _curId;
	
	public CycleBag()
	{
		this._curId = 0;
	}
	
	public Object pop()
	{
		Object mNode = super.pop(this._curId);
		
		//Update cycling index
		if(_curId + 1 >= this.getSize())
			this._curId = 0;
		else
			this._curId++;
		
		return mNode;
	}
}
