package mastermind;
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
import mastermind.controller.BoardController;
import mastermind.controller.GameController;
import mastermind.model.Database;
import mastermind.model.Game;
import mastermind.view.BoardPanel;
import mastermind.view.GameFrame;

public class Main
{
	public void run()
	{
		//Initialize Database abstraction layers
		Database db;
		if(!new File("mastermind.db").exists())
			db = Database.factory();
		else
			db = new Database();
		
		//Models
		Game model = new Game(db);
		
		//Views
        GameFrame viewMastermind = new GameFrame();
        BoardPanel viewBoard = new BoardPanel(model);
        
        //Controllers (Pointer remains on the event thread)
        new GameController(model, viewMastermind);
        new BoardController(model, viewBoard);
        
        //Start EDT thread async
        viewMastermind.setPanelBoard(viewBoard);
        viewMastermind.setVisible(true);		
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		
		//Links everything correctly when exported to a JAR folder
		ResourceLocater.setRootClass(main.getClass());
		
		main.run();
	}
}
