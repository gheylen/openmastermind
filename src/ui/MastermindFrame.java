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

package ui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import lib.ext.JarImage;
import lib.mvc.view.DraggableFrame;
import lib.mvc.view.ImageButton;
import lib.mvc.view.ImagePanel;
import javax.swing.border.MatteBorder;
import enums.Difficulty;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

public class MastermindFrame extends DraggableFrame
{
	private JPanel _contentPanel;
	private JPanel _headPanel;
	private ImagePanel _logoPanel;
	private JPanel _controlPanel;
	private ImageButton _exitButton;
	private ImageButton _diffEasyButton;
	private ImageButton _diffMediumButton;
	private ImageButton _diffHardButton;
	private ImageButton _diffImpossibleButton;
	
	public MastermindFrame()
	{		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setUndecorated(true);
		this.setTitle("OpenMastermind");
		
		this._contentPanel = new JPanel();
		this._contentPanel.setLayout(new BorderLayout(0, 0));
		this._contentPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		this.setContentPane(this._contentPanel);
		
		this._headPanel = new JPanel();
		this._headPanel.setBackground(new Color(153, 102, 51));
		this._headPanel.setLayout(new BorderLayout(0, 0));
		this._headPanel.setPreferredSize(new Dimension(0, 110));
		this._contentPanel.add(this._headPanel , BorderLayout.NORTH);
		
		this._logoPanel = new ImagePanel(JarImage.getImage("/img/headerBig.png"));
		this._logoPanel.setImgHover(JarImage.getImage("/img/headerBigHover.png"));
		this._logoPanel.setFitHeight(110);
		this._headPanel.add(this._logoPanel, BorderLayout.WEST);
		
		this._controlPanel = new JPanel();
		_controlPanel.setBackground(new Color(153, 102, 51));
		this._controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		this._headPanel.add(this._controlPanel, BorderLayout.CENTER);
		
		this._diffEasyButton = new ImageButton(JarImage.getImage("/img/diffEasy.png"), "easy");
		this._diffEasyButton.setPressedIcon(JarImage.getImage("/img/diffEasyClick.png"));
		this._diffEasyButton.setRolloverIcon(JarImage.getImage("/img/diffEasyHover.png"));
		this._controlPanel.add(this._diffEasyButton);
		
		this._diffMediumButton = new ImageButton(JarImage.getImage("/img/diffMedium.png"), "medium");
		this._diffMediumButton.setPressedIcon(JarImage.getImage("/img/diffMediumClick.png"));
		this._diffMediumButton.setRolloverIcon(JarImage.getImage("/img/diffMediumHover.png"));
		this._controlPanel.add(this._diffMediumButton);
		
		this._diffHardButton = new ImageButton(JarImage.getImage("/img/diffHard.png"), "hard");
		this._diffHardButton.setPressedIcon(JarImage.getImage("/img/diffHardClick.png"));
		this._diffHardButton.setRolloverIcon(JarImage.getImage("/img/diffHardHover.png"));
		this._controlPanel.add(this._diffHardButton);
		
		this._diffImpossibleButton = new ImageButton(JarImage.getImage("/img/diffImpossible.png"), "impossible");
		this._diffImpossibleButton.setPressedIcon(JarImage.getImage("/img/diffImpossibleClick.png"));
		this._diffImpossibleButton.setRolloverIcon(JarImage.getImage("/img/diffImpossibleHover.png"));
		this._controlPanel.add(this._diffImpossibleButton);
		
		this._exitButton = new ImageButton(JarImage.getImage("/img/exit.png"));
		this._exitButton.setPressedIcon(JarImage.getImage("/img/exitClick.png"));
		this._exitButton.setRolloverIcon(JarImage.getImage("/img/exitHover.png"));
		this._controlPanel.add(this._exitButton);
		
		this.setMiddleScreen();
		this.setSizeFor(Difficulty.MEDIUM);
	}
	
	public void addExitEvent(ActionListener e)
	{
		this._exitButton.addActionListener(e);
	}
	public void addChangeDifficultyEvent(ActionListener e)
	{
		this._diffEasyButton.addActionListener(e);
		this._diffMediumButton.addActionListener(e);
		this._diffHardButton.addActionListener(e);
		this._diffImpossibleButton.addActionListener(e);
	}
	public void setPanelBoard(BoardPanel board)
	{
		this._contentPanel.add(board, BorderLayout.CENTER);
	}
	public void setSizeFor(Difficulty difficulty)
	{
		this._adjustSize(difficulty);
		this._adjustLogo(difficulty);
		this._adjustButtons(difficulty);
	}
	
	private void _adjustLogo(Difficulty difficulty)
	{
		switch(difficulty)
		{
			case EASY:
				this._logoPanel.setFitHeight(115); break;
			case MEDIUM:
				this._logoPanel.setFitHeight(105); break;
			case HARD:
				this._logoPanel.setFitHeight(95); break;
			case IMPOSSIBLE:
				this._logoPanel.setFitHeight(85); break;
		}
	}
	private void _adjustSize(Difficulty difficulty)
	{
		switch(difficulty)
		{
			case EASY:
				this.setSize(680, 250); break;
			case MEDIUM:
				this.setSize(600, 280); break;
			case HARD:
				this.setSize(500, 280); break;
			case IMPOSSIBLE:
				this.setSize(410, 310); break;
		}
	}
	private void _adjustButtons(Difficulty difficulty)
	{
		this._diffEasyButton.setActive(false);
		this._diffMediumButton.setActive(false);
		this._diffHardButton.setActive(false);
		this._diffImpossibleButton.setActive(false);
		
		switch(difficulty)
		{
			case EASY:
				this._diffEasyButton.setActive(true); break;
			case MEDIUM:
				this._diffMediumButton.setActive(true); break;
			case HARD:
				this._diffHardButton.setActive(true); break;
			case IMPOSSIBLE:
				this._diffImpossibleButton.setActive(true); break;
		}
	}
}
