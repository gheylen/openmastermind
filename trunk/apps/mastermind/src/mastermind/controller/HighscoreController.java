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
import mastermind.view.HighscoreFrame;


public class HighscoreController
{
	private Game _mastermind;
	private HighscoreFrame _view;
	
	public HighscoreController(Game mastermind, HighscoreFrame view)
	{
		this._mastermind = mastermind;
		this._view = view;
		
		view.addAndStartUxTimer(new UxTickListener());
		view.addOkListener(new CloseListener());
		view.addChangeDifficultyListener(new ChangeDifficultyListener());
	}
	

	class UxTickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			_view.showNextRow();
		}
	}
	class CloseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{			
			_view.dispose();
		}
	}
	class ChangeDifficultyListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{			
			if(((ImageButton)e.getSource()).getUniqueName() == "easy")
			{
				_view.setScoresTo(_mastermind.getDb().getHighscores(Difficulty.EASY));
				_view.adjustButtons(Difficulty.EASY);
			}
			else if(((ImageButton)e.getSource()).getUniqueName() == "medium")
			{
				_view.setScoresTo(_mastermind.getDb().getHighscores(Difficulty.MEDIUM));
				_view.adjustButtons(Difficulty.MEDIUM);
			}
			else if(((ImageButton)e.getSource()).getUniqueName() == "hard")
			{
				_view.setScoresTo(_mastermind.getDb().getHighscores(Difficulty.HARD));
				_view.adjustButtons(Difficulty.HARD);
			}
			else if (((ImageButton)e.getSource()).getUniqueName() == "impossible")
			{
				_view.setScoresTo(_mastermind.getDb().getHighscores(Difficulty.IMPOSSIBLE));
				_view.adjustButtons(Difficulty.IMPOSSIBLE);
			}
			
			_view.setEverythingInvisible();
			_view.startUxTimer();
			_view.repaint();
		}
	}
}
