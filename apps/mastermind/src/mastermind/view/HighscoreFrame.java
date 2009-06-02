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

package mastermind.view;

import gheylenlib.component.DraggableFrame;
import gheylenlib.component.ImageButton;
import gheylenlib.jar.ResourceLocater;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import mastermind.Difficulty;
import mastermind.controller.HighscoreController;
import mastermind.model.Mastermind;
import mastermind.model.Score;
import java.awt.Dimension;

public class HighscoreFrame extends DraggableFrame
{
	private JPanel _pnlContent;
	private JPanel _pnlScoresValues;
	private JPanel _pnlScoresNames;
	private ImageButton _btnOk;
	private ImageButton _btnDiffImpossible;
	private ImageButton _btnDiffHard;
	private ImageButton _btnDiffMedium;
	private ImageButton _btnDiffEasy;
	private JPanel _pnDiffs;
	private JPanel _pnlScores;
	private JPanel _pnlControls;
	private JLabel[] _lblNames;
	private JLabel[] _lblValues;
	private Timer _uxTimer;
	private JPanel _pnlSpacer0;
	private JPanel _pnlSpacer1;
	
	public HighscoreFrame(Mastermind mastermind)
	{
		this._init();
		this.setEverythingInvisible();
		new HighscoreController(mastermind, this);
		this.setMiddleScreen();
	}

	public void setScoresTo(ArrayList<Score> scores)
	{
		for(int i = 0; i < 10; i++)
		{
			this._lblNames[i].setText("Anonymous");
			this._lblValues[i].setText("0");
		}
		for(int i = 0; i < scores.size(); i++)
		{
			this._lblNames[i].setText(scores.get(i).getName());
			this._lblValues[i].setText(String.valueOf(scores.get(i).getScore()));
		}
	}
	public void addChangeDifficultyListener(ActionListener e)
	{
		this._btnDiffEasy.addActionListener(e);
		this._btnDiffMedium.addActionListener(e);
		this._btnDiffHard.addActionListener(e);
		this._btnDiffImpossible.addActionListener(e);
	}
	public void addAndStartUxTimer(ActionListener e)
	{
		this._uxTimer = new Timer(666, e);
		this.startUxTimer();
	}
	public void startUxTimer()
	{
		this._uxTimer.start();
	}
	public void showNextRow()
	{
		for(int i = 0; i < 10; i++)
		{
			if(!this._lblNames[i].isVisible())
			{
				this._lblNames[i].setVisible(true);
				this._lblValues[i].setVisible(true);
				break;
			}
			
			if(i == 9)
				this._uxTimer.stop();
		}		
	}
	public void addOkListener(ActionListener closeListener)
	{
		this._btnOk.addActionListener(closeListener);
	}
	public void setEverythingInvisible()
	{
		for(int i = 0; i < 10; i++)
		{
			this._lblNames[i].setVisible(false);
			this._lblValues[i].setVisible(false);
		}
	}
	public void adjustButtons(Difficulty difficulty)
	{
		this._btnDiffEasy.setActive(false);
		this._btnDiffMedium.setActive(false);
		this._btnDiffHard.setActive(false);
		this._btnDiffImpossible.setActive(false);
		
		switch(difficulty)
		{
			case EASY:
				this._btnDiffEasy.setActive(true); break;
			case MEDIUM:
				this._btnDiffMedium.setActive(true); break;
			case HARD:
				this._btnDiffHard.setActive(true); break;
			case IMPOSSIBLE:
				this._btnDiffImpossible.setActive(true); break;
		}
	}
	
	private void _init()
	{
		this.setTitle("OpenMastermind Highscores");
		this.setSize(250, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this._pnlContent = new JPanel();
		this._pnlContent.setLayout(new BorderLayout(0, 0));
		this._pnlContent.setBackground(new Color(153, 102, 51));
		this._pnlContent.setBorder(new EmptyBorder(5, 5, 5, 5));
		this._pnlContent.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		this.setContentPane(_pnlContent);
		{
			this._pnDiffs = new JPanel();
			this._pnDiffs.setPreferredSize(new Dimension(10, 80));
			this._pnDiffs.setBackground(new Color(153, 102, 51));
			this._pnlContent.add(this._pnDiffs, BorderLayout.NORTH);
			{
				this._btnDiffEasy = new ImageButton(ResourceLocater.getImage("/mastermind/res/img/diffEasy.png"), "easy");
				this._btnDiffEasy.setHoverIcon(ResourceLocater.getImage("/mastermind/res/img/diffEasyHover.png"));
				this._btnDiffEasy.setPressedIcon(ResourceLocater.getImage("/mastermind/res/img/diffEasyClick.png"));
				this._pnDiffs.add(this._btnDiffEasy);
			}
			{
				this._btnDiffMedium = new ImageButton(ResourceLocater.getImage("/mastermind/res/img/diffMedium.png"), "medium");
				this._btnDiffMedium.setHoverIcon(ResourceLocater.getImage("/mastermind/res/img/diffMediumHover.png"));
				this._btnDiffMedium.setPressedIcon(ResourceLocater.getImage("/mastermind/res/img/diffMediumClick.png"));
				this._pnDiffs.add(this._btnDiffMedium);
			}
			{
				this._btnDiffHard = new ImageButton(ResourceLocater.getImage("/mastermind/res/img/diffHard.png"), "hard");
				this._btnDiffHard.setHoverIcon(ResourceLocater.getImage("/mastermind/res/img/diffHardHover.png"));
				this._btnDiffHard.setPressedIcon(ResourceLocater.getImage("/mastermind/res/img/diffHardClick.png"));
				this._pnDiffs.add(this._btnDiffHard);
			}
			{
				this._btnDiffImpossible = new ImageButton(ResourceLocater.getImage("/mastermind/res/img/diffImpossible.png"), "impossible");
				this._btnDiffImpossible.setHoverIcon(ResourceLocater.getImage("/mastermind/res/img/diffImpossibleHover.png"));
				this._btnDiffImpossible.setPressedIcon(ResourceLocater.getImage("/mastermind/res/img/diffImpossibleClick.png"));
				this._pnDiffs.add(this._btnDiffImpossible);
			}
		}
		{
			this._pnlScores = new JPanel();
			this._pnlScores.setLayout(new GridLayout(1, 0, 0, 0));
			this._pnlScores.setBackground(new Color(153, 102, 51));
			this._pnlContent.add(this._pnlScores, BorderLayout.CENTER);
			{
				this._pnlScoresNames = new JPanel();
				this._pnlScoresNames.setLayout(new GridLayout(10, 0, 0, 0));
				this._pnlScoresNames.setBackground(new Color(153, 102, 51));
				
				this._pnlScores.add(this._pnlScoresNames);
				
				this._lblNames = new JLabel[10];
				for(int i = 0; i < 10; i++)
				{
					this._lblNames[i] = new JLabel("Anonymous");
					this._pnlScoresNames.add(this._lblNames[i]);
					this._lblNames[i].setHorizontalAlignment(SwingConstants.CENTER);
				}
			}
			{
				this._pnlScoresValues = new JPanel();
				this._pnlScoresValues.setLayout(new GridLayout(10, 0, 0, 0));
				this._pnlScoresValues.setBackground(new Color(153, 102, 51));
				
				this._pnlScores.add(this._pnlScoresValues);
				
				this._lblValues = new JLabel[10];
				for(int i = 0; i < 10; i++)
				{
					this._lblValues[i] = new JLabel(String.valueOf(0));
					this._lblValues[i].setHorizontalAlignment(SwingConstants.CENTER);
					this._pnlScoresValues.add(this._lblValues[i]);
				}
			}
		}
		{
			this._pnlControls = new JPanel();
			this._pnlControls.setBackground(new Color(153, 102, 51));
			this._pnlContent.add(this._pnlControls, BorderLayout.SOUTH);
			{
				this._btnOk = new ImageButton(ResourceLocater.getImage("/mastermind/res/img/ok.png"));
				this._btnOk.setHoverIcon(ResourceLocater.getImage("/mastermind/res/img/okHover.png"));
				this._btnOk.setPressedIcon(ResourceLocater.getImage("/mastermind/res/img/okClick.png"));
				this._pnlControls.add(this._btnOk);
			}
		}
		{
			this._pnlSpacer0 = new JPanel();
			this._pnlSpacer0.setBackground(new Color(153, 102, 51));
			this._pnlContent.add(this._pnlSpacer0, BorderLayout.WEST);
		}
		{
			this._pnlSpacer1 = new JPanel();
			this._pnlSpacer1.setBackground(new Color(153, 102, 51));
			this._pnlContent.add(this._pnlSpacer1, BorderLayout.EAST);
		}
		this.setUndecorated(true);
	}
}
