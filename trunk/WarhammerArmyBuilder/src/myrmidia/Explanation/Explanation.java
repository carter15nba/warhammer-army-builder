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

/**
 * Interface to be implemented by all explanation classes, the interface
 * ensures that all explanation classes have the same unifrom mwthod to
 * generate explanations.
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public interface Explanation {
    
    /**
     * Interface method to create a natural language explanation based on the
     * data contained within the object.
     * @return A String representation of the explanation
     */
    public String generateExplanation();
}
