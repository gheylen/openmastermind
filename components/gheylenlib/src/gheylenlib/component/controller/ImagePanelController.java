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

package gheylenlib.component.controller;

import gheylenlib.component.ImagePanel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ImagePanelController
{
	private ImagePanel _view;
	
	public ImagePanelController(ImagePanel view)
	{
		this._view = view;
		this._view.addHoverListener(new HoverListener());
	}
	
	class HoverListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e) { }
		public void mouseEntered(MouseEvent e)
		{
			_view.emulateHover(true);
			_view.repaint();
		}
		public void mouseExited(MouseEvent e)
		{
			_view.emulateHover(false);
			_view.repaint();
		}
		public void mousePressed(MouseEvent e) { } 
		public void mouseReleased(MouseEvent e) { }
	}
}
