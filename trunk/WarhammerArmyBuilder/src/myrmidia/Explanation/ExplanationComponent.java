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

package myrmidia.Explanation;

import java.util.List;

/**
 * Interface class used to help generate explanations
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public interface ExplanationComponent<E> {

    /**
     * Set the component of this part of the explanation. The object must
     * be of the same generic type as the one defined in the implementing
     * class
     * @param component The component to set
     */
    public void setComponent(E component);

    /**
     * Adds an action that describes what changes were made to the component
     * and why. There can be many actions to one component
     * @param action The action to add
     */
    public void addAction(Action action);

    /**
     * Method to get the generic component
     * @return E the generic object component
     */
    public E getComponent();

    /**
     * Method to get the list of associated actions from the component
     * @return List with the actions associated with the component
     */
    public List<Action> getActions();

    /**
     * Method to merge actions (delete redundant non-needed actions)
     * before this object is used to create an explanation
     */
    public void mergeActions();
}
