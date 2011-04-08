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

package myrmidia.Util;

import java.util.Collection;

/**
 * Class to get the specified item from any collection
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class CollectionControl {

    /**
     * This method returns the Object at the specified location in the specified
     * collection. For most uses must the returned object be cast to the correct
     * type in order to be of any use.
     * @param collection Collection The collection to have the desired object.
     * @param index int The index in the list the desired object resides.
     * @return Object the object at the specified index
     * @throws IndexOutOfBoundsException if the index is higer than the size of
     * the collection, or lower than 0.
     */
    @SuppressWarnings("unchecked")
    public static Object getItemAt(Collection collection, int index)throws IndexOutOfBoundsException{
        if(index >= collection.size())
            throw new IndexOutOfBoundsException();
        if(index<0)
            throw new IndexOutOfBoundsException();
        Object[] obj = new Object[collection.size()];
        obj = collection.toArray(obj);
        return obj[index];
    }
}