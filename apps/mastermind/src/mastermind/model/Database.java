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

import gheylenlib.db.adapter.Sqlite;
import java.sql.SQLException;
import java.util.ArrayList;
import mastermind.model.game.Difficulty;
import com.sun.rowset.CachedRowSetImpl;

public class Database extends Sqlite
{
	public Database()
	{
		super("mastermind.db");
	}
	
	public static Database factory()
	{
		Database mDb = new Database();
		mDb.DdlQuery("CREATE TABLE highscore(name TEXT, score INT, difficulty TEXT);", true);
		return mDb;
	}
	
	public void addScore(Score score)
	{
		String mQuery = "INSERT INTO highscore VALUES ('";
		mQuery += score.getName() + "', ";
		mQuery += score.getScore() + ", '";
		mQuery += score.getDifficulty();
		mQuery += "');";
		
		super.DdlQuery(mQuery, true);
		
		//this.purgeScores();
	}	
	public ArrayList<Score> getHighscores(Difficulty difficulty)
	{
		ArrayList<Score> mScores = new ArrayList<Score>();
		
		String mQuery = "SELECT * FROM highscore WHERE difficulty = '" + difficulty.toString() + "' ORDER BY score DESC LIMIT 10;";
		CachedRowSetImpl mResults = super.resultQuery(mQuery, true);
		try
		{
			while(mResults.next())
			{
				Score mScore = new Score(Difficulty.valueOf(mResults.getString(3)), mResults.getString(1));
				mScore.setScore(mResults.getInt(2));
				mScores.add(mScore);
			}
		}
		catch (SQLException e)
		{
			return null;
		}
		
		return mScores;
	}
	/**
	 * Will delete everything except the top 10 scores
	 * 
	 * @deprecated Bugged: Different difficulties are not supported (#7)
	 */
	public void purgeScores()
	{
		String mQuery = "DELETE FROM highscore WHERE score NOT IN (SELECT score FROM highscore ORDER BY score DESC LIMIT 10);";
		super.DdlQuery(mQuery, true);
	}
}