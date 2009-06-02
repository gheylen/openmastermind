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

import java.io.File;
import java.sql.SQLException;
import lib.jar.ResourceLocater;
import controller.BoardController;
import controller.MastermindController;
import core.Mastermind;
import core.db.MastermindDb;
import ui.MastermindFrame;
import ui.BoardPanel;

public class Main
{
	public void run()
	{
		//Database abstraction layers
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
        
        //Controllers
        MastermindController controllerMastermind = new MastermindController(model, viewMastermind);
        BoardController controllerBoard = new BoardController(model, viewBoard);
        
        //Start EDT thread async
        viewMastermind.setPanelBoard(viewBoard);
        viewMastermind.setVisible(true);		
	}
	
	public static void main(String[] args) throws SQLException
	{
		Main main = new Main();
		ResourceLocater.setClass(main.getClass());
		main.run();
	}
}
