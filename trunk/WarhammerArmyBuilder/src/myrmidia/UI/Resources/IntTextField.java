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

import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 *
 * @author Glenn Rune Strandbråten
 */
public class IntTextField extends JTextField{
    private static final int NUMBER_LIMIT = 5;

    public IntTextField() {
        super("", NUMBER_LIMIT);
    }

    @Override
    protected Document createDefaultModel(){
        return new IntTextDocument();
    }

    @Override
    public boolean isValid(){
        try{
            Integer.parseInt(getText());
            return true;
        }
        catch(NumberFormatException nfe){
            return false;
        }
        catch(NullPointerException e){
            return false;
        }
    }

    public int getValue(){
        try{
            return Integer.parseInt(getText());
        }
        catch(NumberFormatException nfe){
            return 0;
        }
    }


}
