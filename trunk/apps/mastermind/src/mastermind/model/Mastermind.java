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

package mastermind.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import mastermind.Difficulty;
import mastermind.Status;
import mastermind.model.db.MastermindDb;

/****
 	* Model Mastermind
****/
public final class Mastermind
{
	private Score _lastScore;
	private ArrayList<Combination> _combinations;
	private Combination _currentCombo;
	private Combination _winningCombo;
	private Date _startTime;
	private byte _chancesCount;
	private byte _codeLength;
	private byte _colorsCount;
	private boolean _allowDoubleColorsInCode;
	private Difficulty _difficulty;
	private Status _status;
	private MastermindDb _db;
	
	public Mastermind(MastermindDb db)
	{
		this._db = db;
		this._combinations = new ArrayList<Combination>();
		this.restart(Difficulty.MEDIUM);
	}

	
	public Score getLastScore()
	{
		return this._lastScore;
	}
	public void setLastScore(Score score)
	{
		this._lastScore = score;
	}
	public MastermindDb getDb()
	{
		return this._db;
	}
	public Combination getWinningCombination()
	{
		return this._winningCombo;
	}
	public byte getColorsCount()
	{
		return this._colorsCount;
	}
	public Difficulty getDifficulty()
	{
		return this._difficulty;
	}
	public boolean getAllowDoubleColors()
	{
		return this._allowDoubleColorsInCode;
	}
	public Combination getCurrentCombo()
	{
		return this._currentCombo;
	}
	public Status getStatus()
	{
		return this._status;
	}
	public long getElapsedMs()
	{
		Date mNow = new Date();
		return mNow.getTime() - this._startTime.getTime();
	}
	public String getElapsedTime(boolean showMin, boolean showSec, boolean showMs)
	{
		DecimalFormat secFormatter = new DecimalFormat("00");
		DecimalFormat msFormatter = new DecimalFormat("000");
		int min = (int) (this.getElapsedMs() / 1000 / 60);
		int sec = (int) (this.getElapsedMs() / 1000 % 60);
		int ms = (int) (this.getElapsedMs() % 1000);
		return (showMin ? (Integer.toString(min)) : "") + (showSec ? (":" + secFormatter.format(sec)) : "") + (showMs ? (":" + msFormatter.format(ms)) : "");
	}
	public byte getChancesCount()
	{
		return this._chancesCount;
	}
	public byte getCommittedCombos()
	{
		return (byte)this._combinations.size();
	}
	public byte getCodeLength()
	{
		return this._codeLength;
	}
	public Combination getCombination(byte index)
	{
		if(index < (byte)this._combinations.size() && index > -1)
			return this._combinations.get(index);
		return null;
	}
	public String isAllowDoubleColors()
	{
		return (this._allowDoubleColorsInCode ? "yes" : "no");
	}
	public void commitCurrentCombo()
	{
		this._addCombination(this._currentCombo);
		
		if(Combination.analyzeResults(this._winningCombo.compareTo(this._combinations.get(this.getCommittedCombos() - 1)), this.getCodeLength()))
			this.setStatus(Status.WON);
		else
			this._currentCombo = Combination.factory(this._codeLength, (byte)1);
	}
	public void restart(Difficulty difficulty)
	{
		this._adjustDifficulty(difficulty);
		this._combinations.clear();
		this._winningCombo = Combination.getRandom(this._codeLength, this._colorsCount, this._allowDoubleColorsInCode);
		this._currentCombo = Combination.factory(this._codeLength, (byte)1);
		this._startTime = new Date();
		this.setStatus(Status.PLAYING);
	}
	public void updateCurrentCombo(byte pegId)
	{
		this._currentCombo.cyclePeg(pegId, this._colorsCount);
	}
	public void setStatus(Status status)
	{
		this._status = status;
	}
	
	private boolean _isFull()
	{
		if(this._combinations.size() < this._chancesCount)
			return false;
		return true;
	}
	private void _addCombination(Combination combination)
	{
		this._combinations.add(combination);

		if(this._isFull())
			this.setStatus(Status.FULL);
	}	
	private void _adjustDifficulty(Difficulty difficulty)
	{
		this._difficulty = difficulty;
		
		switch(difficulty)
		{
			case EASY:
				this._codeLength = 3;
				this._chancesCount = 14;
				this._colorsCount = 5;
				this._allowDoubleColorsInCode = false;
				break;
			case MEDIUM:
				this._codeLength = 4;
				this._chancesCount = 12;
				this._colorsCount = 6;
				this._allowDoubleColorsInCode = false;
				break;
			case HARD:
				this._codeLength = 4;
				this._chancesCount = 10;
				this._colorsCount = 7;
				this._allowDoubleColorsInCode = true;
				break;
			case IMPOSSIBLE:
				this._codeLength = 5;
				this._chancesCount = 8;
				this._colorsCount = 8;
				this._allowDoubleColorsInCode = true;
				break;
		}
	}
}
