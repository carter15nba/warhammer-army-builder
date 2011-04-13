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
import java.util.Collection;
import java.util.List;
import jcolibri.method.retrieve.RetrievalResult;
import myrmidia.Warhammer.Case;


/**
 *
 * @author Glenn Rune Strandbråten
 * @version 0.1
 */
public class ExplanationEngine{

    private List<CaseExplanation> caseExplanations;
    private List<AdaptionExplanation> adaptionExplanations;

    private ExplanationEngine(){
        caseExplanations = new ArrayList<CaseExplanation>();
        adaptionExplanations = new ArrayList<AdaptionExplanation>();
    }

    public static ExplanationEngine getInstance(){
        return ExplanationEngineHolder.INSTANCE;
    }

    private static class ExplanationEngineHolder {
        private static final ExplanationEngine INSTANCE = new ExplanationEngine();
    }

    public void addCaseExplanation(CaseExplanation caseExpl){
        caseExplanations.add(caseExpl);
    }

    public CaseExplanation getCaseExplanationByID(int caseID){
        for (CaseExplanation ce : caseExplanations) {
            if(ce.getCaseID()==caseID)
                return ce;
        }
        return new CaseExplanation();
    }

    public CaseExplanation getCaseExplanationByPosition(int pos){
        if(caseExplanations.size()>=pos){
            return new CaseExplanation();
        }
        else
            return caseExplanations.get(pos);
    }

    public CaseExplanation getCurrentCaseExplanation(){
        int index = caseExplanations.size()-1;
        if(index<0)
            return new CaseExplanation();
        return caseExplanations.get(index);
    }

    public String generateTransparencyExplanations() {
        String ret="\nGenerating transparency explanations!\n\n";
        for (CaseExplanation ce : caseExplanations) {
            ret+=ce.generateExplanation()+"\n\n";
        }
        return ret;
    }

    public String generateTransparencyExplanation(int index) {
        String ret = "\nGenerating the transparency for the desired case!\n\n";
        if(index>0&&index<caseExplanations.size()){
            ret = caseExplanations.get(index).generateExplanation();
        }
        return ret;
    }

    public void orderTransparencyExplanations(Collection<RetrievalResult> topKRR){
        List<CaseExplanation> tmp = new ArrayList<CaseExplanation>();
        for (RetrievalResult retrievalResult : topKRR) {
            Case _case = (Case) retrievalResult.get_case().getSolution();
            for (CaseExplanation expl : caseExplanations) {
                if(_case.getID()==expl.getCaseID())
                    tmp.add(expl);
            }
        }
        caseExplanations = tmp;
    }

    public AdaptionExplanation getCurrentAdaptionExplanation(){
        int index = adaptionExplanations.size()-1;
        if(index<0)
            return new AdaptionExplanation();
        return adaptionExplanations.get(index);
    }

    public void clearAllExplanationComponents(){
        caseExplanations.clear();
        adaptionExplanations.clear();
    }

    public void addAdaptionExplanation(AdaptionExplanation adaptionExplanation) {
        adaptionExplanations.add(adaptionExplanation);
    }

    public AdaptionExplanation getAdaptionExplanationByID(int caseID){
        for (AdaptionExplanation ae : adaptionExplanations) {
            if(ae.getCaseID()==caseID)
                return ae;
        }
        return new AdaptionExplanation();
    }

    public AdaptionExplanation getAdaptionExplanationByPosition(int pos){
        if(adaptionExplanations.size()>=pos){
            return new AdaptionExplanation();
        }
        else
            return adaptionExplanations.get(pos);
    }

    public String generateJustificationExplanations() {
        String ret="\nGenerating justification explanations!\n\n";
        for (AdaptionExplanation ae : adaptionExplanations) {
            ret+=ae.generateExplanation()+"\n\n";
        }
        return ret;
    }

    public String generateJustificationExplanation(int index) {
        String ret = "\nGenerating the justification for the desired case!\n\n";
        if(index>0&&index<adaptionExplanations.size()){
            ret = adaptionExplanations.get(index).generateExplanation();
        }
        return ret;
    }
}
