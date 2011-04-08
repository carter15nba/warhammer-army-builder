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

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class ExplanationEngine implements Explanation{

    private ExplanationEngine(){
        caseExplanation = new ArrayList<CaseExplanation>();
    }
    private List<CaseExplanation> caseExplanation;
    public static ExplanationEngine getInstance(){
        return ExplanationEngineHolder.INSTANCE;
    }
    private static class ExplanationEngineHolder {
        private static final ExplanationEngine INSTANCE = new ExplanationEngine();
    }
    public void addCaseExplanation(CaseExplanation caseExpl){
        caseExplanation.add(caseExpl);
    }
    public CaseExplanation getCaseExplanationByID(int caseID){
        for (CaseExplanation ce : caseExplanation) {
            if(ce.getCaseID()==caseID)
                return ce;
        }
        return new CaseExplanation();
    }
    public CaseExplanation getCaseExplanationByPosition(int pos){
        if(caseExplanation.size()>=pos){
            return new CaseExplanation();
        }
        else
            return caseExplanation.get(pos);
    }
    public CaseExplanation getCurrentCaseExplanation(){
        int index = caseExplanation.size()-1;
        if(index<0)
            return new CaseExplanation();
        return caseExplanation.get(index);
    }
    public String generateExplanation() {
        String ret="";
        for (CaseExplanation expl : caseExplanation) {
            ret+=expl.generateExplanation()+"\n\n";
        }
        return ret;
    }
}
