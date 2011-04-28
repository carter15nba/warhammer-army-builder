/*
 *  Copyright (C) 2011 Glenn Rune Strandbråten 
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package myrmidia.UI.Resources;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import jcolibri.exception.ExecutionException;
import myrmidia.CBR.CBREngine;

/**
 * Class to be used by as the default close operation on all windows. When any
 * window is closed, indicating that the application will terminate, this class
 * will ensure a clean exit.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class WindowCloser extends WindowAdapter{

    @Override
    public void windowClosing (WindowEvent evt){
        finalizeApplicationExecution();
    }

    /**
     * Static method to be used when a window closing event occurs, or
     * if the user selects any exit button actions.
     */
    public static void finalizeApplicationExecution(){
        try {
            CBREngine.getInstance().postCycle();
        }
        catch (ExecutionException ex) {}
        finally{
            System.exit(0);
        }
    }
}
