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

package lib.collection;

/**
 * Deletes a popped node (In contrast to CycleBag)
 */
public final class QueueBag extends AbstractBag
{
	private boolean _fifo;
	
	public QueueBag(boolean fifo)
	{
		this._fifo = fifo;
	}
	
	public Object pop()
	{
		if(super.getSize() == 0)
			return null;
		
		Object mNode = super.pop((this._fifo ? 0 : super.getSize() - 1));
		super.drop((this._fifo ? 0 : super.getSize() - 1));
		return mNode;
	}
}
