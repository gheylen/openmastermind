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

package lib.mvc.view;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton
{
	private String _uniqueName;
	private ImageIcon _icoBuf;
	
	public ImageButton(Image img)
	{
		this(img, null);
	}
	public ImageButton(Image img, String uniqueName)
	{
		this._uniqueName = uniqueName;
		this.setBorder(null);
		this._icoBuf = new ImageIcon(img);
		this.setIcon(this._icoBuf);
		this.setPreferredSize(new Dimension(this.getIcon().getIconWidth(), this.getIcon().getIconHeight()));
	}
	
	public void setActive(boolean pressed)
	{
		if(pressed)
		{
			this._icoBuf = (ImageIcon) super.getIcon();
			super.setIcon(super.getRolloverIcon());
		}
		else
			super.setIcon(this._icoBuf);
	}
	public void setPressedIcon(Image img)
	{
		super.setPressedIcon(new ImageIcon(img));
	}
	public void setRolloverIcon(Image img)
	{
		super.setRolloverIcon(new ImageIcon(img));
	}
	public String getUniqueName()
	{
		return this._uniqueName;
	}
}
