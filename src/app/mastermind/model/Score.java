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

package app.mastermind.model;

import app.mastermind.Difficulty;

public class Score
{
	private int _score;
	private Difficulty _difficulty;
	private String _name;
	
	public Score(Difficulty difficulty, String name)
	{
		this._difficulty = difficulty;
		this._name = name;
		this._score = 0;
	}
	public Difficulty getDifficulty()
	{
		return this._difficulty;
	}
	public int getScore()
	{
		return this._score;
	}
	public void setName(String name)
	{
		this._name = name;
	}
	public void setScore(int score)
	{
		this._score = score;		
	}
	public String getName()
	{
		return this._name;
	}
	public void calcScore(long timeElapsed, byte changesUsed, byte maxChanges)
	{
		this.setScore((int) ((maxChanges + 2 - changesUsed) * 10000 / (timeElapsed / 1000)));
	}
}