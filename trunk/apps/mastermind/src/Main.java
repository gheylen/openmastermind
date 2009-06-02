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

import gheylenlib.jar.ResourceLocater;
import java.io.File;
import java.sql.SQLException;
import app.mastermind.controller.BoardController;
import app.mastermind.controller.MastermindController;
import app.mastermind.model.Mastermind;
import app.mastermind.model.db.MastermindDb;
import app.mastermind.view.BoardPanel;
import app.mastermind.view.MastermindFrame;

public class Main
{
	public void run()
	{
		//Initialize Database abstraction layers
		MastermindDb db;
		if(!new File("mastermind.db").exists())
			db = MastermindDb.factory();
		else
			db = new MastermindDb();
		
		//Models
		Mastermind model = new Mastermind(db);
		
		//Views
        MastermindFrame viewMastermind = new MastermindFrame();
        BoardPanel viewBoard = new BoardPanel(model);
        
        //Controllers (Pointer remains on the event thread)
        new MastermindController(model, viewMastermind);
        new BoardController(model, viewBoard);
        
        //Start EDT thread async
        viewMastermind.setPanelBoard(viewBoard);
        viewMastermind.setVisible(true);		
	}
	
	public static void main(String[] args) throws SQLException
	{
		Main main = new Main();
		
		//Links everything correctly when exported to a JAR folder
		ResourceLocater.setRootClass(main.getClass());
		
		main.run();
	}
}
