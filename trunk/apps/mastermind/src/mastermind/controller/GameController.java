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

package mastermind.controller;

import gheylenlib.component.ImageButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mastermind.model.Game;
import mastermind.model.game.Difficulty;
import mastermind.model.game.Status;
import mastermind.view.GameFrame;

/****
	* Mastermind Business Logic
****/
public class GameController
{
	private Game _mastermind;
	private GameFrame _view;
	
	public GameController(Game mastermind, GameFrame view)
	{
		this._mastermind = mastermind;
		this._view = view;
		
		this._view.addExitEvent(new ExitListener());
		this._view.addChangeDifficultyEvent(new ChangeDifficultyListener());
	}
	
	class ExitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
	class ChangeDifficultyListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(_mastermind.getStatus() == Status.SHOWING_SCORE)
				return;
			
			if(((ImageButton)e.getSource()).getUniqueName() == "easy")
			{
				_view.setSizeFor(Difficulty.EASY);
				_mastermind.restart(Difficulty.EASY);
			}
			else if(((ImageButton)e.getSource()).getUniqueName() == "medium")
			{
				_view.setSizeFor(Difficulty.MEDIUM);
				_mastermind.restart(Difficulty.MEDIUM);
			}
			else if(((ImageButton)e.getSource()).getUniqueName() == "hard")
			{
				_view.setSizeFor(Difficulty.HARD);
				_mastermind.restart(Difficulty.HARD);
			}
			else if (((ImageButton)e.getSource()).getUniqueName() == "impossible")
			{
				_view.setSizeFor(Difficulty.IMPOSSIBLE);
				_mastermind.restart(Difficulty.IMPOSSIBLE);
			}
			
			_view.repaint();
		}
	}
}
