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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import core.Combination;
import core.Mastermind;
import java.awt.event.MouseListener;
import lib.collection.CycleBag;
import lib.jar.ResourceLocater;
import enums.CombResultCode;
import enums.MastermindStatus;

public class BoardPanel extends JPanel
{
	private Mastermind _mastermind;
	private int _borderSpace;
	private byte _amountResultsHorizontal;
	
	public BoardPanel(Mastermind mastermind)
	{
		this.setBackground(new Color(153, 102, 51));
		this._mastermind = mastermind;
		this._borderSpace = 20;
		this._amountResultsHorizontal = 2;
	}
	
	public int getLocationPeg(byte id)
	{
		return ((this.getHeightPeg() + this._getSizeBetweenPegs()) * id) + (this._borderSpace / 2);
	}
	public int getLocationCombination(byte id)
	{
		return ((this.getWidthPeg() + this._getSizeBetweenCombinations()) * id) + (this._borderSpace / 2);
	}
	public int getWidthPeg()
	{
		return (int)((this.getSize().getWidth() - this._borderSpace - this._getTotalSizeBetweenCombinations()) / (this._mastermind.getChancesCount() + 1));
	}
	public int getHeightPeg()
	{
		return (int)((this.getSize().getHeight() - this._borderSpace - this._getTotalSizeBetweenPegs()) / (this._mastermind.getCodeLength() + 1));
	}
	public void addClickListener(MouseListener e)
	{
		this.addMouseListener(e);
	}
	
	/****
		* The drawing process contains 4 steps:
		*   (1)Improving the drawing quality manually;
		*   (2)Checking which combination should be drawn next (current, null, submitted);
		*   (3)Draw the combination buffered (including its results);
		*   (4)Checking whether the game has ended or not (draw secret code or not);
	****/
    public void paintComponent(Graphics g)
    {
	    super.paintComponent(g);
	    
	    //(1) Draw with better quality..
	    Graphics2D g2 = (Graphics2D)g;
	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    	    
	    boolean mCurrCodeDrawn = false;
	    for(byte i = 0; i < this._mastermind.getChancesCount(); i++)
	    {
	    	//(2) Buffer correct combination to draw
	    	Combination mBufferedComb;
	    	if(i < this._mastermind.getCommittedCombos())
	    		mBufferedComb = this._mastermind.getCombination(i);
    		else if(!mCurrCodeDrawn && this._mastermind.getStatus() == MastermindStatus.PLAYING)
			{
    			mBufferedComb = this._mastermind.getCurrentCombo();
    			mCurrCodeDrawn = true;
			}
    		else
    			mBufferedComb = Combination.factory(this._mastermind.getCodeLength(), (byte)0);
     		
	    	//(3) Draw combination as a whole
	    	this._drawCombination(g, mBufferedComb, i, mCurrCodeDrawn);
	    }
	    
	    //(4) Draw hidden code
	    if(this._mastermind.getStatus() == MastermindStatus.PLAYING)
	    	this._drawSecretCodeCover(g);
	    else
	    	for(byte l = 0; l < this._mastermind.getCodeLength(); l++) 	
	    		this._drawPeg(g, this._mastermind.getWinningCombination().getPeg(l), this._mastermind.getChancesCount(), (byte)(l + 1));
    }
    
    private void _drawCombination(Graphics g, Combination combination, byte combinationIndex, boolean currCodeDrawn)
    {
		//Results
		CycleBag mResults = combination.compareTo(this._mastermind.getWinningCombination());
		for(byte j = 0; j < this._mastermind.getCodeLength(); j++)
		{
			CombResultCode mResultCode;
			Image mImage;
			
			if(mResults == null || j >= mResults.getSize() || currCodeDrawn)
				mResultCode = CombResultCode.EMPTY;
			else
				mResultCode = (CombResultCode) mResults.pop();
			
			switch(mResultCode)
			{
				case FOUND:
					mImage = new ImageIcon(ResourceLocater.getImage("/img/pegWhite.png")).getImage(); break;
				case CORRECT:
					mImage = new ImageIcon(ResourceLocater.getImage("/img/pegBlack.png")).getImage(); break;
				default:
					mImage = new ImageIcon(ResourceLocater.getImage("/img/pegEmpty.png")).getImage(); break;
			}
			
			Point mPoint = this._getLocationResult(combinationIndex, j);
			g.drawImage(mImage, mPoint.x, mPoint.y, this._getWidthResult(), this._getHeightResult(), null);
		}

		//Pegs
    	for(byte k = 1; k < this._mastermind.getCodeLength() + 1; k++) 	
    		this._drawPeg(g, combination.getPeg((byte)(k - 1)), combinationIndex, k);  	
    }
    private void _drawSecretCodeCover(Graphics g)
    {
    	Image mImage;
    	
	    mImage =  new ImageIcon(ResourceLocater.getImage("/img/coverSecretCode.png")).getImage();
	    g.drawImage(mImage,
	    		this.getLocationCombination(this._mastermind.getChancesCount()), 
	    		this.getLocationPeg((byte)1),
	    		this.getWidthPeg(),
	    		-this._getSizeBetweenPegs() + (this.getHeightPeg() + this._getSizeBetweenPegs()) * this._mastermind.getCodeLength(),
	    		null);
    }
    private void _drawPeg(Graphics g, byte color, byte comb, byte peg)
    {
    	Image mImage;
    	
		switch(color)
		{
			case 1:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegBlue.png")).getImage(); break;
			case 2:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegGreen.png")).getImage(); break;
			case 3:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegRed.png")).getImage(); break;
			case 4:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegYellow.png")).getImage(); break;
			case 5:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegBlack.png")).getImage(); break;
			case 6:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegWhite.png")).getImage(); break;
			case 7:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegPurple.png")).getImage(); break;
			case 8:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegGrey.png")).getImage(); break;
			default:
				mImage = new ImageIcon(ResourceLocater.getImage("/img/pegEmpty.png")).getImage(); break;
		}
		
		g.drawImage(mImage, this.getLocationCombination(comb), this.getLocationPeg(peg), this.getWidthPeg(), this.getHeightPeg(), null);
    }
	private int _getTotalSizeBetweenCombinations()
	{
		return (int)(this._getSizeBetweenCombinations() * this._mastermind.getChancesCount());
	}
	private int _getTotalSizeBetweenPegs()
	{
		return (int)(this._getSizeBetweenPegs() * (this._mastermind.getCodeLength()));
	}
	private int _getSizeBetweenCombinations()
	{
		return (int)((this.getSize().getWidth() - this._borderSpace) / (this._mastermind.getChancesCount() + 1) * 0.4);
	}
	private int _getSizeBetweenPegs()
	{
		return (int)((this.getSize().getHeight() - this._borderSpace) / (this._mastermind.getCodeLength() + 1) * 0.3);
	}
	private int _getSizeBetweenResults()
	{
		return (int)(this.getWidthPeg() / this._amountResultsHorizontal * 0.2);
	}
	private int _getTotalSizeBetweenResults()
	{
		return this._getSizeBetweenResults() * (this._amountResultsHorizontal - 1);
	}
	private byte _getAmountResultsVertical()
	{
		return (byte) (((byte)(this._mastermind.getCodeLength() / this._amountResultsHorizontal)) + ((this._mastermind.getCodeLength() / this._amountResultsHorizontal > 0) ? 1 : 0));
	}
	private int _getWidthResult()
	{
		return (int)((this.getWidthPeg() - this._getTotalSizeBetweenResults()) / this._amountResultsHorizontal);
	}
	private int _getHeightResult()
	{
		return (int)((this.getHeightPeg() - this._getTotalSizeBetweenResults()) / this._getAmountResultsVertical());
	}
	private Point _getLocationResult(byte combination, byte result)
	{
		Point mPoint = new Point();
		
		result++; //Increasing to calculate
		
		byte vertical = (byte) ((result / this._amountResultsHorizontal) + ((result % this._amountResultsHorizontal > 0) ? 1 : 0));
		byte horizontal = (byte) ((result / vertical));
		
		vertical--; //Decreasing to adjust
		horizontal--;
		
		mPoint.x = this.getLocationCombination(combination) + ((this._getSizeBetweenResults() + this._getWidthResult()) * horizontal);
		mPoint.y = this.getLocationPeg((byte)0) + ((this._getSizeBetweenResults() + this._getHeightResult()) * vertical);
		
		return mPoint;
	}
}
