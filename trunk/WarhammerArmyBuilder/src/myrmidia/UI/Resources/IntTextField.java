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
 * Class used to make a JTextField which only accepts Integers
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class IntTextField extends JTextField{
    private static final int NUMBER_LIMIT = 5;

    /** Default constructor */
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

    /**
     * Method to get the integer value of the text in the text field
     * @return The integer value of the text field or 0 if a NumberFormatException
     * where thrown
     */
    public int getValue(){
        try{
            return Integer.parseInt(getText());
        }
        catch(NumberFormatException nfe){
            return 0;
        }
    }
}