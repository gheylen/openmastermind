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

import gheylenlib.component.controller.DraggableFrameController;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;

public abstract class DraggableFrame extends JFrame
{
	private Point _lastClick;
	
	protected DraggableFrame()
	{
		//Pointer remains on the event thread!
		new DraggableFrameController(this);
	}
	
	public void addDragListener(MouseMotionListener e)
	{
		this.addMouseMotionListener(e);
	}
	public void addClickListener(MouseListener e)
	{
		this.addMouseListener(e);
	}
	public void setLastClickPoint(Point point)
	{
		this._lastClick = point;
	}
	public Point getLastClickPoint()
	{
		return this._lastClick;
	}
	protected void setMiddleScreen()
	{
		Toolkit mToolkit = Toolkit.getDefaultToolkit();
		this.setLocation((mToolkit.getScreenSize().width - this.getSize().width) / 2, (mToolkit.getScreenSize().height - this.getSize().height) / 2);
	}
}
