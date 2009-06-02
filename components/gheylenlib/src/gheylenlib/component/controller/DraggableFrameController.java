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

import gheylenlib.component.DraggableFrame;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DraggableFrameController
{
	private DraggableFrame _view;
	
	public DraggableFrameController(DraggableFrame view)
	{
		this._view = view;
		this._view.addClickListener(new ClickListener());
		this._view.addDragListener(new DragListener());
	}
	
	class ClickListener implements MouseListener
	{
		public void mouseClicked(MouseEvent e) { }
		public void mouseEntered(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mousePressed(MouseEvent e)
		{
			_view.setLastClickPoint(e.getPoint());
		} 
		public void mouseReleased(MouseEvent e) { }
	}
	class DragListener implements MouseMotionListener
	{
		public void mouseDragged(MouseEvent e)
		{
			 Point mFrameLocation = _view.getLocation();
			_view.setLocation(mFrameLocation.x + e.getX() - _view.getLastClickPoint().x, mFrameLocation.y + e.getY() - _view.getLastClickPoint().y);	
		}
		public void mouseMoved(MouseEvent arg0) { }
	}
}
