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

import java.util.ArrayList;
import java.util.Set;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.exception.NoApplicableSimilarityFunctionException;
import org.Warhammer.CBR.Resources.UnitSimilarity;
import org.Warhammer.Util.CollectionControl;
import org.Warhammer.Warhammer.*;
import org.Warhammer.Warhammer.RuleSet.Messages;
import org.apache.derby.iapi.services.i18n.MessageService;

/**
 * Class to perform the case adaption (testing stages only)
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class AdaptionEngine {


    public Case adaptCase(Case _case, CBRQuery query){
        Case adaptedCase = naiveAdaption(_case, query);
        return refineAdaption(adaptedCase, query);
    }

    private Case naiveAdaption(Case _case, CBRQuery query){
        Case adaptedCase = Case.copy(_case);
        Case queryCase = (Case) query.getDescription();
        adaptedCase.setOpponent(queryCase.getOpponent());
        adaptedCase.setOutcome(Case.Outcomes.Unknown);
        adaptedCase.setArmy(adaptArmyFromQuery(_case, queryCase));
        return adaptedCase;
    }
    private Case refineAdaption(Case adaptedCase, CBRQuery query){
        adaptedCase.setArmy(refineArmy(adaptedCase.getArmy()));
        //TODO: whatever goes here
        return adaptedCase;
    }

    private Army adaptArmyFromQuery(Case _case, Case queryCase){
        Army adaptedArmy = Army.copy(_case.getArmy());
        adaptedArmy.setArmyPoints(queryCase.getArmy().getArmyPoints());
        Set<ArmyUnit> queryUnits = queryCase.getArmy().getArmyUnits();
        boolean[] isAdapted = new boolean[queryUnits.size()];
        int pos=0;
        //Find and replace units in the case with query units
        //(Adapt present units e.g.: queried units already present in the case).
        for(ArmyUnit queryUnit : queryUnits){
            Unit query = queryUnit.getUnit();
            for(ArmyUnit adaptedUnit : adaptedArmy.getArmyUnits()){
                Unit adapted = adaptedUnit.getUnit();
                if(query.getName().equals(adapted.getName())){
                    adaptedUnit.setNumberOfUnits(queryUnit.getNumberOfUnits());
                    //Replace the equipment/utility if and only if the queried
                    //unit have specified equipment/utility, otherwise keep the
                    //case equipment/utility.
                    if(!queryUnit.getEquipment().isEmpty())
                        adaptedUnit.setEquipment(queryUnit.getEquipment());
                    if(!queryUnit.getUtility().isEmpty())
                        adaptedUnit.setUtility(queryUnit.getUtility());
                    isAdapted[pos] = true;
                }
            }
            pos++;
        }
        //If units in query not adapted, find possible replacement candidates
        //and perform the adaption.
        pos = 0;
        for(ArmyUnit queryUnit : queryUnits){
            if(isAdapted[pos++])
                continue;
            Unit query = queryUnit.getUnit();
            UnitSimilarity unitSimilarity = new UnitSimilarity();
            ArrayList<Double> similarity = new ArrayList<Double>();
            try{
                //Calculate the similarity between all units in the army and the
                //current query unit.
                for (ArmyUnit adaptedUnit : adaptedArmy.getArmyUnits()) {
                    boolean calculate = true;
                    //Do not calculate the similarity on any unit that is
                    //present in the query.
                    for(ArmyUnit isQuery : queryUnits){
                        if(adaptedUnit.getUnit().getName().equals(isQuery.getUnit().getName())){
                            calculate = false;
                            break;
                        }
                    }
                    double sim = 0;
                    if(calculate)
                        sim = unitSimilarity.compute(query, adaptedUnit.getUnit());
                    similarity.add(sim);
                }
                //Find most similar
                int index = -1;
                double highest = -1;
                for(int i = 0; i < similarity.size(); i++){
                    double sim = similarity.get(i);
                    if(highest<sim){
                        highest = sim;
                        index = i;
                    }
                }
                //Adapt most similar
                ArmyUnit armyUnit = (ArmyUnit) CollectionControl.getItemAt(adaptedArmy.getArmyUnits(), index);
                System.out.println("   changed unit: "+armyUnit.getUnit().getName()+", to unit: "+query.getName());
                armyUnit.setUnit(query);
                armyUnit.setUtility(queryUnit.getUtility());
                armyUnit.setEquipment(queryUnit.getEquipment());
                armyUnit.setNumberOfUnits(queryUnit.getNumberOfUnits());
            }
            catch(NoApplicableSimilarityFunctionException e){}
        }
        return adaptedArmy;
    }

    private Army refineArmy(Army army){
        RuleSet rule = new RuleSet();
        Messages[] messages = rule.isFollowingArmyDispositionRules(army);
        while(messages[0]!=Messages.OK){
            //



            messages = rule.isFollowingArmyDispositionRules(army);
        }

        return army;
    }
}