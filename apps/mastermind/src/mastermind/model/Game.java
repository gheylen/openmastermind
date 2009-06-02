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
import mastermind.model.game.Difficulty;
import mastermind.model.game.Status;

public final class Game
{
	private Score _lastScore;
	private ArrayList<Combination> _combinations;
	private Combination _currentCombo;
	private Combination _winningCombo;
	private Date _startTime;
	private Difficulty _difficulty;
	private Status _status;
	private Database _db;
	
	public Game(Database db)
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
	public Database getDb()
	{
		return this._db;
	}
	public Combination getWinningCombination()
	{
		return this._winningCombo;
	}
	public byte getColorsCount()
	{
		byte mColors;
		
		switch(this._difficulty)
		{
			case EASY:
				mColors = 5;
				break;
			case HARD:
				mColors = 7;
				break;
			case IMPOSSIBLE:
				mColors = 8;
				break;
			default:
				mColors = 6;
				break;
		}
		
		return mColors;
	}
	public Difficulty getDifficulty()
	{
		return this._difficulty;
	}
	public boolean getAllowDoubleColors()
	{
		boolean mDoubleColorsInCode;
		
		switch(this._difficulty)
		{
			case EASY:
				mDoubleColorsInCode = false;
				break;
			case HARD:
				mDoubleColorsInCode = true;
				break;
			case IMPOSSIBLE:
				mDoubleColorsInCode = true;
				break;
			default:
				mDoubleColorsInCode = false;
				break;
		}
		
		return mDoubleColorsInCode;
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
		byte mChances;
		
		switch(this._difficulty)
		{
			case EASY:
				mChances = 14;
				break;
			case HARD:
				mChances = 10;
				break;
			case IMPOSSIBLE:
				mChances = 8;
				break;
			default:
				mChances = 12;
				break;
		}
		
		return mChances;
	}
	public byte getCommittedCombos()
	{
		return (byte)this._combinations.size();
	}
	public byte getCodeLength()
	{
		byte mCodeLength;
		
		switch(this._difficulty)
		{
			case EASY:
				mCodeLength = 3;
				break;
			case HARD:
				mCodeLength = 4;
				break;
			case IMPOSSIBLE:
				mCodeLength = 5;
				break;
			default:
				mCodeLength = 4;
				break;
		}
		
		return mCodeLength;
	}
	public Combination getCombination(byte index)
	{
		if(index < (byte)this._combinations.size() && index > -1)
			return this._combinations.get(index);
		return null;
	}

	public void commitCurrentCombo()
	{
		this._addCombination(this._currentCombo);
		
		if(Combination.analyzeResults(this._winningCombo.compareTo(this._combinations.get(this.getCommittedCombos() - 1)), this.getCodeLength()))
			this.setStatus(Status.WON);
		else
			this._currentCombo = Combination.factory(this.getCodeLength(), (byte)1);
	}
	public void restart(Difficulty difficulty)
	{
		this._difficulty = difficulty;
		this._combinations.clear();
		this._winningCombo = Combination.getRandom(this.getCodeLength(), this.getColorsCount(), this.getAllowDoubleColors());
		this._currentCombo = Combination.factory(this.getCodeLength(), (byte)1);
		this._startTime = new Date();
		this.setStatus(Status.PLAYING);
	}
	public void updateCurrentCombo(byte pegId)
	{
		this._currentCombo.cyclePeg(pegId, this.getColorsCount());
	}
	public void setStatus(Status status)
	{
		this._status = status;
	}
	
	private void _isFull()
	{
		if(this._combinations.size() >= this.getChancesCount())
			this.setStatus(Status.FULL);
	}
	private void _addCombination(Combination combination)
	{
		this._combinations.add(combination);
		this._isFull();
	}	
}
