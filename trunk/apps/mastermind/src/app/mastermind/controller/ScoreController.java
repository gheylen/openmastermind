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

package app.mastermind.controller;

import gheylenlib.component.ImageButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import app.mastermind.Difficulty;
import app.mastermind.Status;
import app.mastermind.model.Mastermind;
import app.mastermind.model.Score;
import app.mastermind.view.HighscoreFrame;
import app.mastermind.view.ScoreFrame;


public class ScoreController
{
	private Mastermind _mastermind;
	private ScoreFrame _view;
	
	public ScoreController(Mastermind mastermind, ScoreFrame view)
	{
		this._mastermind = mastermind;
		this._view = view;
		
		view.addAndStartScoreTimer(new ScoreTickListener());
		view.addAndStartUxTimer(new UxTickListener());
		view.addCloseListener(new ScoreActionListener());
		view.addHishScoreListener(new ScoreActionListener());
	}
	
	class ScoreActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(((ImageButton)e.getSource()).getUniqueName() == "ok")
			{
				_mastermind.setStatus(Status.WON);
				_view.dispose();
			}
			else
			{
				Difficulty difficulty = _mastermind.getDifficulty();
				
				Score mScore = _mastermind.getLastScore();
				
				String mName;
				if(_mastermind.getLastScore().getName() == null)
				{
					mName = JOptionPane.showInputDialog(_view, "What is your name?", "Enter your name", JOptionPane.QUESTION_MESSAGE);
					if(mName != null)
						mScore.setName(mName);
				}
				
				_mastermind.getDb().addScore(mScore);
				HighscoreFrame frame = new HighscoreFrame(_mastermind);
				frame.setScoresTo(_mastermind.getDb().getHighscores(difficulty));
				frame.adjustButtons(difficulty);
				frame.setVisible(true);
			}
		}
	}
	class UxTickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			_view.showNextRow();
		}
	}
	class ScoreTickListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int score = _view.getCurrentTotalScore();
			
			_view.setCurrentTotalScore(score + 2);
			
			if(_mastermind.getLastScore() != null)
				if(score > _mastermind.getLastScore().getScore())
					_view.stopTimerScore();
		}
	}
}
