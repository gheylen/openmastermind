/*  openMastermind Copyright (c) 2008 Glenn Heylen and Bert Lemmens

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

package core;

import java.util.HashSet;
import java.util.Random;
import lib.collections.CycleBag;
import enums.CombResultCode;

/****
	* Model Combination
****/
public class Combination
{
	private byte[] _pegs;
	
	public Combination(byte... pegs)
	{
		this._pegs = pegs;
	}
	
	public byte getLength()
	{
		return (byte) this._pegs.length;
	}
	public byte getPeg(byte id)
	{		
		return this._pegs[id];
	}
	public void setPeg(byte id, byte value)
	{		
		this._pegs[id] = value;
	}

	public CycleBag compareTo(Combination combination)
	{
		if(combination == null)
			return null;
		
		if(this.getLength() != combination.getLength())
			return null;
		
		CycleBag mResults = new CycleBag();
		HashSet<Byte> mFlaggedComboData = new HashSet<Byte>();
		HashSet<Byte> mFlaggedWinningData = new HashSet<Byte>();
		
		//Anything correctly placed?
		for(byte i = 0; i < this.getLength(); i++)
			if(this.getPeg(i) == combination.getPeg(i))
			{
				mResults.put(CombResultCode.CORRECT);
				mFlaggedComboData.add(i);
				mFlaggedWinningData.add(i);
			}
		
		//Anything in the code, but not on the right spot?
		for(byte i = 0; i < this.getLength(); i++)
		{
			if(mFlaggedComboData.contains(i))
				continue;
			
			for(byte j = 0; j < this.getLength(); j++)
				if(combination.getPeg(i) == this.getPeg(j))
				{
					if(mFlaggedWinningData.contains(j))
						continue;
					
					mResults.put(CombResultCode.FOUND);
					mFlaggedWinningData.add(j);
					break;
				}
		}
		
		return mResults;
	}
	
	public static Combination getFilled(byte length, byte value)
	{
		byte[] mPegs = new byte[length];
		for(byte i = 0; i < length; i++)
			mPegs[i] = value;
		
		return new Combination(mPegs);
	}
	public static boolean analyzeResults(CycleBag results, byte expectedLength)
	{
		if(results.getSize() != expectedLength)
			return false;
		
		for(byte i = 0; i < results.getSize(); i++)
			if(results.pop() != CombResultCode.CORRECT)
				return false;
		
		return true;
	}
	public static Combination getRandom(byte length, byte colorsCount, boolean allowDoubles)
	{
		Random mRandom = new Random();
		HashSet<Byte> mFlaggedColors = new HashSet<Byte>(length);
		byte[] mPegs = new byte[length];
		byte mPeg;
		
		for(int i = 0; i < length; i ++)
		{
			do
			{
				mPeg = (byte)mRandom.nextInt(colorsCount);
			} while((!allowDoubles ? (mFlaggedColors.contains(mPeg)) : false));
			mFlaggedColors.add(mPeg);
			mPegs[i] = (byte)(mPeg + 1);
		}
		mFlaggedColors.clear();
		
		return new Combination(mPegs);
	}
}