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

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Class used as the default document for the IntTextField class
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class IntTextDocument extends PlainDocument{

    @Override
    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
        if(str == null)
            return;
        String oldString = getText(0, getLength());
        String newString = oldString.substring(0, offs) + str +
                oldString.substring(offs);
        try{
            Integer.parseInt(newString + "0");
            super.insertString(offs, str, a);
        }
        catch(NumberFormatException nfe){}
    }
}