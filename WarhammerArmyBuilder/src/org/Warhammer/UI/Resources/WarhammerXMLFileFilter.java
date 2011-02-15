/*
 *  Copyright (C) 2011 Glenn Rune Strandbåten
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

package org.Warhammer.UI.Resources;

/**
 *
 * @author Glenn Rune Strandbåten
 * @verion 0.1
 */
public class WarhammerXMLFileFilter extends javax.swing.filechooser.FileFilter{
    @Override
    public boolean accept(java.io.File f) {
        String name = f.getName();
        if(f.isDirectory())
            return true;
        if(name.endsWith(".xml"))
            return true;
        else
            return false;
    }
    @Override
    public String getDescription() {
        return "Warhammer race XML files (.xml)";
    }
}
