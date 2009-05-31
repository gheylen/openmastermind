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

package lib.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.sun.rowset.CachedRowSetImpl;

public abstract class SqliteDb implements Database
{
	private String _db;
	private Connection _connection;
	
	public SqliteDb(String db)
	{
		this._db = db;
	}
	
	public SqliteError open()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			this._connection = DriverManager.getConnection("jdbc:sqlite:" + this._db);
		}
		catch (ClassNotFoundException e1)
		{
			return SqliteError.DRIVER_NOT_FOUND;
		}
		catch (SQLException e)
		{
			return SqliteError.ACCESSIBILITY_FAILURE;
		}
		
		return SqliteError.NONE;
	}
	public SqliteError close()
	{
	    try
		{
			this._connection.close();
		}
		catch (SQLException e)
		{
			return SqliteError.ACCESSIBILITY_FAILURE;
		}
		
		return SqliteError.NONE;
	}
	public CachedRowSetImpl resultQuery(String query, boolean lazyConnection)
	{
		if(lazyConnection)
			this.open();
		
		try
		{
			if(this._connection.isClosed())
				return null;
		}
		catch (SQLException e)
		{
			return null;
		}
		
		Statement mStat;
		ResultSet mResults;
		CachedRowSetImpl mValues;
		try
		{
			mStat = this._connection.createStatement();
			
			mResults = mStat.executeQuery(query);
			mValues = new CachedRowSetImpl();
			mValues.populate(mResults);
			mStat.close();
			mResults.close();
		}
		catch (SQLException e)
		{
			return null;
		}
		
		if(lazyConnection)
			this.close();
		
		return mValues;		
	}
	public void DdlQuery(String query, boolean lazyConnection)
	{
		if(lazyConnection)
			this.open();
		
		try
		{
			if(this._connection.isClosed())
				return;
		}
		catch (SQLException e)
		{
			return;
		}
		
		try
		{
			Statement mStat = this._connection.createStatement();
			mStat.executeUpdate(query);
			mStat.close();
		}
		catch (SQLException e)
		{
			return;
		}
		
		if(lazyConnection)
			this.close();
	}
}