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

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import mastermind.Status;
import mastermind.model.Mastermind;
import mastermind.model.Score;
import mastermind.view.BoardPanel;
import mastermind.view.ScoreFrame;

/****
	* Board Business Logic
****/
public class BoardController
{
	private Mastermind _mastermind;
	private BoardPanel _view;
	
	public BoardController(Mastermind mastermind, BoardPanel view)
	{
		this._mastermind = mastermind;
		this._view = view;
		
		this._view.addClickListener(new CyclePegsListener());
	}
	
	class CyclePegsListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e) { }
		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mousePressed(MouseEvent e)
		{
			//Game should be in the PLAYING status when there is interacting with the mouse
			if(_mastermind.getStatus() != Status.PLAYING)
				return;
			
			//Check whether the current combination is being clicked. Return early if it isn't
			int mTriggeredXLoc = _view.getLocationCombination((byte)(_mastermind.getCommittedCombos()));
			if(e.getX() < mTriggeredXLoc || e.getX() > mTriggeredXLoc + _view.getWidthPeg())
				return;
			
			//Check whether the user wants to cycle through or commit the current combination
			if(e.getButton() == MouseEvent.BUTTON3)
			{
					_mastermind.commitCurrentCombo();
					_view.repaint();
					
					if(_mastermind.getStatus() == Status.WON)
					{
						_mastermind.setStatus(Status.SHOWING_SCORE);
						
						Score mScore = new Score(_mastermind.getDifficulty(), null);
						mScore.calcScore(_mastermind.getElapsedMs(), _mastermind.getCommittedCombos(), _mastermind.getChancesCount());
						
						_mastermind.setLastScore(mScore);
						
						ScoreFrame frame = new ScoreFrame(_mastermind);
						frame.setVisible(true);
					}
			}
			else if(e.getButton() == MouseEvent.BUTTON1)
				for(byte i = 1; i < _mastermind.getCodeLength() + 1; i++)
				{
					//Find out which peg was clicked upon
					int mTriggeredYLoc = _view.getLocationPeg(i);
					if(e.getY() > mTriggeredYLoc && e.getY() < mTriggeredYLoc + _view.getHeightPeg())
					{
						_mastermind.updateCurrentCombo((byte)(i - 1));
						_view.repaint();
					}
				}
		}
		public void mouseReleased(MouseEvent e) { }
	}
}
