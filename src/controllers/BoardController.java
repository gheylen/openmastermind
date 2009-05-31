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

package controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import core.Mastermind;
import core.Score;
import enums.MastermindStatus;
import ui.ScoreFrame;
import ui.BoardPanel;

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
			if(_mastermind.getStatus() != MastermindStatus.PLAYING)
				return;
			
			int mTriggeredXLoc = _view.getLocationCombination((byte)(_mastermind.getCommittedCombos()));
			if(e.getX() < mTriggeredXLoc || e.getX() > mTriggeredXLoc + _view.getWidthPeg())
				return;
			
			if(e.getButton() == MouseEvent.BUTTON3)
			{
				_mastermind.commitCurrentCombo();
				
				if(_mastermind.getStatus() == MastermindStatus.WON)
				{
					_mastermind.setStatus(MastermindStatus.SHOWING_SCORE);
					
					Score mScore = new Score(_mastermind.getDifficulty(), null);
					mScore.calcScore(_mastermind.getElapsedMs(), _mastermind.getCommittedCombos(), _mastermind.getChancesCount());
					
					_mastermind.setLastScore(mScore);
					
					ScoreFrame frame = new ScoreFrame(_mastermind);
					frame.setVisible(true);
				}
				
				_view.repaint();
			}
			else if(e.getButton() == MouseEvent.BUTTON1)
				for(byte i = 1; i < _mastermind.getCodeLength() + 1; i++)
				{
					int mTriggeredYLoc = _view.getLocationPeg(i);
					if(e.getY() > mTriggeredYLoc && e.getY() < mTriggeredYLoc + _view.getHeightPeg())
					{
						_mastermind.updateCurrentCombo((byte)(i - 1));
						_view.repaint();
						return;
					}
				}
			
		}
		public void mouseReleased(MouseEvent e) { }
	}
}
