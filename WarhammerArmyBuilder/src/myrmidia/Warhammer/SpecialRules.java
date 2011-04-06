/*
 *  Copyright (C) 2011 Glenn Rune
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

package myrmidia.Warhammer;

import jcolibri.cbrcore.Attribute;

/**
 * Class representing a special rule
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class SpecialRules implements jcolibri.cbrcore.CaseComponent{
    private int id=0;
    private String rule="";
    public Attribute getIdAttribute() {
         return new Attribute("id", this.getClass());
    }

    /**
     * Default constructor
     */
    public SpecialRules() {
    }

    /**
     * Constructor which sets the special rule
     * @param rule The Special rule to set.
     */
    public SpecialRules(String rule) {
        this.rule = rule;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
                                                                                                                    
    /**
     * @param id the idx to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the rule
     */
    public String getRule() {
        return rule;
    }

    /**
     * @param rule the rule to set
     */
    public void setRule(String rule) {
        this.rule = rule;
    }
}