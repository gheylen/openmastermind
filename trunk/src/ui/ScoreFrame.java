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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Dimension;
import lib.component.DraggableFrame;
import lib.component.ImageButton;
import lib.component.ImagePanel;
import lib.jar.ResourceLocater;
import javax.swing.JLabel;
import controller.ScoreController;
import core.Mastermind;

public class ScoreFrame extends DraggableFrame
{
	private Mastermind _mastermind;
	private JPanel uxPanelSouth;
	private ImageButton _btnOk;
	private Timer _scoreTimer;
	private Timer _uxTimer;
	private ImageButton _btnHighscore;
	private ImagePanel _pnlLogo;
	private JPanel _pnlResultRow0;
	private JPanel _pnlResultRow1;
	private JLabel _lblDoubleColors;
	private JLabel _pnlDoubleColors;
	private JPanel _pnlResultRow2;
	private JLabel _lblDiffColors;
	private JLabel _lblDiffColorsValue;
	private JPanel _pnlResultRow3;
	private JLabel _lblCodeLength;
	private JLabel _lblCodeLengthValue;
	private JPanel _pnlResultRow4;
	private JLabel _lblSteps;
	private JLabel _lblStepsValue;
	private JPanel _pnlResultRow5;
	private JPanel _pnlResultRow7;
	private JLabel _lblScoreVal;
	private JLabel _lblScore;
	private JLabel _lblTimeElapsedVal;
	private JLabel _lblTimeElapsed;
	private JLabel _lblDiffColorVal;
	private JLabel _lblDifColor;
	private JPanel _pnlSpacer0;
	private JPanel _contentPanel;
	private JPanel _pnlScores;
	private JPanel _pnlSpacer1;
	
	public ScoreFrame(Mastermind mastermind)
	{
		this._mastermind = mastermind;
		this._init();
		this.setMiddleScreen();
		new ScoreController(this._mastermind, this);
	}
	
	public void addCloseListener(ActionListener e)
	{
		this._btnOk.addActionListener(e);
	}
	public void addHishScoreListener(ActionListener e)
	{
		this._btnHighscore.addActionListener(e);
	}
	public void addAndStartScoreTimer(ActionListener e)
	{
		this._scoreTimer = new Timer(10, e);
		this._scoreTimer.start();
	}
	public void addAndStartUxTimer(ActionListener e)
	{
		this._uxTimer = new Timer(666, e);
		this._uxTimer.start();
	}
	public int getCurrentTotalScore()
	{
		return Integer.parseInt(this._lblScoreVal.getText());
	}
	public void setCurrentTotalScore(int score)
	{
		this._lblScoreVal.setText(Integer.toString(score));
	}
	public void stopTimerScore()
	{
		this._scoreTimer.stop();
	}
	public void showNextRow()
	{
		if(!this._pnlResultRow0.isVisible())
			this._pnlResultRow0.setVisible(true);
		else if(!this._pnlResultRow1.isVisible())
			this._pnlResultRow1.setVisible(true);
		else if(!this._pnlResultRow2.isVisible())
			this._pnlResultRow2.setVisible(true);
		else if(!this._pnlResultRow3.isVisible())
			this._pnlResultRow3.setVisible(true);
		else if(!this._pnlResultRow4.isVisible())
			this._pnlResultRow4.setVisible(true);
		else if(!this._pnlResultRow5.isVisible())
			this._pnlResultRow5.setVisible(true);
		else
			this._uxTimer.stop();
	}
	
	private void _init()
	{
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(190, 300));
		this.setBackground(new Color(153, 102, 51));
		this.setResizable(false);
		this.setUndecorated(true);
		this.setTitle("openMastermind Results");
		
		this._contentPanel = new JPanel();
		this._contentPanel.setBackground(new Color(153, 102, 51));
		this._contentPanel.setLayout(new BorderLayout(0, 0));
		this._contentPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		this.setContentPane(this._contentPanel);
		{
			this.uxPanelSouth = new JPanel();
			this.uxPanelSouth.setBackground(new Color(153, 102, 51));
			this.uxPanelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			this._contentPanel.add(uxPanelSouth, BorderLayout.SOUTH);
			{
				this._btnOk = new ImageButton(ResourceLocater.getImage("/res/img/ok.png"), "ok");
				this._btnOk.setHoverIcon(ResourceLocater.getImage("/res/img/okHover.png"));
				this._btnOk.setPressedIcon(ResourceLocater.getImage("/res/img/okClick.png"));
				this.uxPanelSouth.add(_btnOk);
			}
			{
				this._btnHighscore = new ImageButton(ResourceLocater.getImage("/res/img/highscores.png"));
				this._btnHighscore.setHoverIcon(ResourceLocater.getImage("/res/img/highscoresHover.png"));
				this._btnHighscore.setPressedIcon(ResourceLocater.getImage("/res/img/highscoresClick.png"));
				this.uxPanelSouth.add(_btnHighscore);
			}
		}
		{
			this._pnlScores = new JPanel();
			this._pnlScores.setLayout(new GridLayout(10, 1, 0, 0));
			this._pnlScores.setBackground(new Color(153, 102, 51));
			this._contentPanel.add(this._pnlScores, BorderLayout.CENTER);
			{
				_pnlSpacer0 = new JPanel();
				_pnlSpacer0.setBackground(new Color(153, 102, 51));
				_pnlScores.add(this._pnlSpacer0);
			}
			{
				this._pnlResultRow0 = new JPanel();
				this._pnlResultRow0.setBackground(new Color(153, 102, 51));
				this._pnlResultRow0.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
				this._pnlScores.add(this._pnlResultRow0);
				{
					this._lblTimeElapsed = new JLabel("Time elapsed: ");
					this._pnlResultRow0.add(this._lblTimeElapsed);
				}
				{
					this._lblTimeElapsedVal = new JLabel(this._mastermind.getElapsedTime(true, true, false));
					this._pnlResultRow0.add(this._lblTimeElapsedVal);
				}
			}
			{
				this._pnlResultRow1 = new JPanel();
				this._pnlResultRow1.setBackground(new Color(153, 102, 51));
				this._pnlResultRow1.setVisible(false);
				this._pnlScores.add(this._pnlResultRow1);
				{
					this._lblDoubleColors = new JLabel("Double colors: ");
					this._pnlResultRow1.add(this._lblDoubleColors);
				}
				{
					this._pnlDoubleColors = new JLabel(this._mastermind.isAllowDoubleColors());
					this._pnlResultRow1.add(this._pnlDoubleColors);
				}
			}
			{
				this._pnlResultRow2 = new JPanel();
				this._pnlResultRow2.setBackground(new Color(153, 102, 51));
				this._pnlResultRow2.setVisible(false);
				this._pnlScores.add(this._pnlResultRow2);
				{
					this._lblDiffColors = new JLabel("Amount of colors:");
					this._pnlResultRow2.add(this._lblDiffColors);
				}
				{
					this._lblDiffColorsValue = new JLabel(Byte.toString(this._mastermind.getColorsCount()));
					this._pnlResultRow2.add(this._lblDiffColorsValue);
				}
			}
			{
				this._pnlResultRow3 = new JPanel();
				this._pnlResultRow3.setBackground(new Color(153, 102, 51));
				this._pnlResultRow3.setVisible(false);
				this._pnlScores.add(this._pnlResultRow3);
				{
					this._lblCodeLength = new JLabel("Code length: ");
					this._pnlResultRow3.add(this._lblCodeLength);
				}
				{
					this._lblCodeLengthValue = new JLabel(Byte.toString(this._mastermind.getCodeLength()));
					this._pnlResultRow3.add(this._lblCodeLengthValue);
				}
			}
			{
				this._pnlResultRow4 = new JPanel();
				this._pnlResultRow4.setBackground(new Color(153, 102, 51));
				this._pnlResultRow4.setVisible(false);
				this._pnlScores.add(_pnlResultRow4);
				{
					this._lblSteps = new JLabel("Changes used:");
					this._pnlResultRow4.add(this._lblSteps);
				}
				{
					this._lblStepsValue = new JLabel(Byte.toString(this._mastermind.getCommittedCombos()));
					this._pnlResultRow4.add(this._lblStepsValue);
				}
			}
			{
				this._pnlResultRow5 = new JPanel();
				this._pnlResultRow5.setBackground(new Color(153, 102, 51));
				this._pnlResultRow5.setVisible(false);
				this._pnlScores.add(_pnlResultRow5);
				{
					this._lblDifColor = new JLabel("Overall difficulty: ");
					this._pnlResultRow5.add(this._lblDifColor);
				}
				{
					this._lblDiffColorVal = new JLabel("Medium");
					this._pnlResultRow5.add(this._lblDiffColorVal);
				}
			}
			{
				this._pnlSpacer1 = new JPanel();
				this._pnlSpacer1.setBackground(new Color(153, 102, 51));
				this._pnlScores.add(this._pnlSpacer1);
			}
			{
				this._pnlResultRow7 = new JPanel();
				this._pnlResultRow7.setBackground(new Color(153, 102, 51));
				this._pnlScores.add(this._pnlResultRow7);
				{
					this._lblScore = new JLabel("Your Score:");
					this._pnlResultRow7.add(this._lblScore);
				}
				{
					this._lblScoreVal = new JLabel("0");
					this._pnlResultRow7.add(this._lblScoreVal);
				}
			}
		}
		{
			this._pnlLogo = new ImagePanel(ResourceLocater.getImage("/res/img/headerSmall.png"));
			this._pnlLogo.setFitWidth(this.getWidth());
			this._contentPanel.add(this._pnlLogo , BorderLayout.NORTH);
		}
	}


}
