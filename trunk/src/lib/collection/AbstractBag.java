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

import java.util.Arrays;

public abstract class AbstractBag implements Bag
{
	private int _amountNodes;
	protected Object[] _data;
	
	protected AbstractBag()
	{
		this._amountNodes = 0;
		this._data = new Object[3];
	}
	
	public Object pop(int nodeId)
	{
		//Make sure there is something to be popped
		if(nodeId >= this.getSize())
			return null;
		
		//Give what was asked
		return this._data[nodeId];
	}
	public void push(Object node)
	{
		//Ensure the available capacity
		this._updateCap(this._amountNodes + 1);
		
		//Add another node to the bag
		this._data[this._amountNodes++] = node;
	}
	public int getSize()
	{
		return this._amountNodes;
	}
	public boolean drop(int nodeId)
	{
		//Make sure there is something to drop
		if(nodeId >= this.getSize() || this.pop(nodeId) == null)
			return false;
		
		//Update the capacity of the bag: might be too big or too small
		this._updateCap(this.getSize() - 1);
		
		//Move the nodes to each other (leave no unused space between them)
		int mAmountNodesMoved = this.getSize() - nodeId - 1;
		if (mAmountNodesMoved > 0)
			System.arraycopy(this._data, nodeId + 1, this._data, nodeId, mAmountNodesMoved);
		
		//Set the last irrelevant node to null to have it cleaned up by gc
		this._data[--this._amountNodes] = null;
		
		return true;
	}
	public boolean contains(Object node)
	{
		for(int i = 0; i < this.getSize(); i++)
			if(node.equals(this.pop(i)))
				return true;
		return false;
	}
	
	private void _updateCap(int cap)
	{
		//Buffer some variables
		int mOldCap = this._data.length;
		int mNewCap = ((mOldCap / 3) * 2 + 1);
		
		//Shrink the bag if there are too many nulls
		if (cap < mNewCap)
			this._data = Arrays.copyOf(this._data, mNewCap);
		//Expand the bag if there isn't enough room to hold all the nodes
		else if(cap > mOldCap)
		{
			mNewCap = (mOldCap * 3) /2 + 1;
			if (mNewCap < cap)
				mNewCap = cap;
			this._data = Arrays.copyOf(this._data, mNewCap);
		}
	}
}
