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

package gheylenlib.component;

import gheylenlib.component.controller.ImagePanelController;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel
{
	private Image _img;
	private Image _imgHover;
	private double _fitFactorHeight;
	private double _fitFactorWidth;
	private boolean _hover;
	
	public ImagePanel(Image image)
	{
		this.setLayout(null);
		this.setImage(image);
		this.setFit(this._img.getWidth(null), this._img.getHeight(null));
		this._hover = false;
		
		//Pointer remains on the event thread!
		new ImagePanelController(this);
	}
	
	public void setImage(Image img)
	{
		this._img = new ImageIcon(img).getImage();
	}
	public void setFitWidth(double width)
	{
		this._fitFactorHeight = this._fitFactorWidth = width / (double)this._img.getWidth(null);
		this._adjustToFactors();
	}
	public void setFitHeight(double height)
	{
		this._fitFactorWidth = this._fitFactorHeight = height / (double)this._img.getHeight(null);
		this._adjustToFactors();
	}
	public void setFit(double width, double height)
	{
		this._fitFactorWidth = width / (double)this._img.getWidth(null);
		this._fitFactorHeight = height / (double)this._img.getHeight(null);
		this._adjustToFactors();
	}
	public void setImgHover(Image image)
	{
		this._imgHover = new ImageIcon(image).getImage();
	}
	public void emulateHover(boolean hover)
	{
		if(this._imgHover == null)
			hover = false;
		
		this._hover = hover;
	}
	public void addHoverListener(MouseListener e)
	{
		this.addMouseListener(e);
	}

	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //Scaling might brick the image a bit
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		AffineTransform transform = AffineTransform.getScaleInstance(this._fitFactorWidth, this._fitFactorHeight);
		g2.transform(transform);
        g.drawImage((this._hover ? this._imgHover : this._img), 0, 0, null);
	}

	private void _adjustToFactors()
	{
		this.setPreferredSize(new Dimension((int)(this._img.getWidth(null) * this._fitFactorWidth), (int)(this._img.getHeight(null) * this._fitFactorHeight)));
	}
}