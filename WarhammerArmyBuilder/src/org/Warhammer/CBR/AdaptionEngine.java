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

package org.Warhammer.CBR;

import java.util.Collection;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.method.retrieve.RetrievalResult;
import org.Warhammer.Warhammer.Army;
import org.Warhammer.Warhammer.Case;
import org.Warhammer.Warhammer.RuleSet;
import org.Warhammer.Warhammer.RuleSet.Messages;

/**
 * Class to perform the case adaption (testing stages only)
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class AdaptionEngine {


    public Case adaptCase(Case _case, CBRQuery query, Collection<RetrievalResult> retrieval){
        Case adaptedCase = Case.copy(_case);
        Case queryCase = (Case) query.getDescription();
        adaptedCase.setOpponent(queryCase.getOpponent());
        adaptedCase.setOutcome(Case.Outcomes.Unknown);
        adaptedCase.setArmy(adaptArmy(_case, queryCase, retrieval));
        return adaptedCase;
    }

    private Army adaptArmy(Case _case, Case queryCase, Collection<RetrievalResult> retrieval){
        Army adaptedArmy = Army.copy(_case.getArmy());
        adaptedArmy.setArmyPoints(queryCase.getArmy().getArmyPoints());
        RuleSet rs = new RuleSet();
        Messages[] ms = rs.isFollowingArmyDispositionRules(adaptedArmy);

        return adaptedArmy;
    }
}