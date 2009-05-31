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

package lib.collections;

public abstract class AbstractBag implements Bag
{
	private long _size;
	protected Object[][] _data;
	
	protected AbstractBag()
	{
		this._size = 0;
		this._data = new Object[0][10];
	}
	
	public Object pop(long node)
	{
		if(node >= this.getSize())
			return null;
		
		//Get location in memory
		int mIndexArr = this._getIndexArray(node);
		int mIndexSubArr = this._getIndexSubArray(node);
		
		return this._data[mIndexArr][mIndexSubArr];
	}
	public void put(Object node)
	{
		int mIndexArr = this._getIndexArray(this.getSize());
		int mIndexSubArr = this._getIndexSubArray(this.getSize());
		
		if(mIndexSubArr == 0)
			this._increaseArrayAmount();
	
		this._data[mIndexArr][mIndexSubArr] = node;
		
		this._size++;
	}
	public long getSize()
	{
		return this._size;
	}
	
	private int _getIndexArray(long node)
	{
		return (int) (node / 10);
	}
	private int _getIndexSubArray(long node)
	{
		return (int) (node % 10);
	}
	private void _increaseArrayAmount()
	{
		int mCap = this._data.length;
		Object[][] mBufData = new Object[mCap + 1][10];
		
		for(int i = 0; i < mCap; i++)
			for(int j = 0; j < 10; j++)
				mBufData[i][j] = this._data[i][j];
		
		this._data = mBufData;
	}
}
