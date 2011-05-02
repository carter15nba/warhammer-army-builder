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
import myrmidia.Exceptions.NoExplanationException;
import myrmidia.Warhammer.Case;


/**
 * Singleton class to create and manage all explanations the system should convey
 * @author Glenn Rune Strandbråten
 * @version 1.0
 */
public class ExplanationEngine{

    private List<CaseExplanation> caseExplanations;
    private List<AdaptionExplanation> adaptionExplanations;
    private List<ExchangeRaceExplanation> exchangeRaceExplanations;

    /** Default constructor */
    private ExplanationEngine(){
        caseExplanations = new ArrayList<CaseExplanation>();
        adaptionExplanations = new ArrayList<AdaptionExplanation>();
        exchangeRaceExplanations = new ArrayList<ExchangeRaceExplanation>();
    }

    /**
     * Method to aquire the singleton instance
     * @return The ExplanationEngine singleton
     */
    public static ExplanationEngine getInstance(){
        return ExplanationEngineHolder.INSTANCE;
    }

    private static class ExplanationEngineHolder {
        private static final ExplanationEngine INSTANCE = new ExplanationEngine();
    }

    /**
     * Method to add a case explanation to the explantion enginge
     * @param caseExpl
     */
    public void addCaseExplanation(CaseExplanation caseExpl){
        caseExplanations.add(caseExpl);
    }

    /**
     * Method to aquire the case explanation by the cases unique ID.
     * @param caseID int The caseID of the explanation to get
     * @return CaseExplanation The explanation of the requested case
     * @throws NoExplanationException - if no case were found with the supplied
     * ID
     */
    public CaseExplanation getCaseExplanationByID(int caseID)
            throws NoExplanationException{
        for (CaseExplanation ce : caseExplanations) {
            if(ce.getCaseID()==caseID)
                return ce;
        }
        throw new NoExplanationException(caseID,this.getClass(),CaseExplanation.class);
    }

    /**
     * Method to aquire a CaseExplanation from a given position within the list
     * @param pos int The position of the explanation
     * @return The requested CaseExplanation
     * @throws NoExplanationException - if no Explanationwere found at the
     * supplied position
     */
    public CaseExplanation getCaseExplanationByPosition(int pos)
            throws NoExplanationException{
        if(caseExplanations.size()>=pos||pos<0)
            throw new NoExplanationException("No case explanation were found at the supplied position");
        else
            return caseExplanations.get(pos);
    }

    /**
     * Method to get the current (latest) CaseExplanatio object.
     * @return <ul><li>The current CaseExplanation object</li>
     * <li>A new empty CaseExplanation object if no explanations exists</li></ul>
     */
    public CaseExplanation getCurrentCaseExplanation() {
        if(caseExplanations.isEmpty())
            return new CaseExplanation();
        int index = caseExplanations.size()-1;          
        return caseExplanations.get(index);
    }

    /**
     * Method which generates the transparency explanations for all cases
     * in the list
     * @return String the transparency explanation
     */
    public String generateTransparencyExplanations() {
        String ret="\nGenerating transparency (selection) explanations!\n\n";
        for (CaseExplanation ce : caseExplanations) {
            ret+=ce.generateExplanation()+"\n\n";
        }
        return ret;
    }

    /**
     * Method to generate the transparency explanations from the case in the
     * supplied position
     * @param index int The position of the explanation to be generated
     * @return String the transparency explanation
     */
    public String generateTransparencyExplanation(int index) {
        String ret = "";
        if(index>=0&&index<caseExplanations.size()){
            ret = "Generating the transparency (selection) explanation for the desired case!\n\n";
            ret += caseExplanations.get(index).generateExplanation();
        }
        return ret;
    }

    /**
     * Method which sorts the transparency explanations to match the orderng
     * in the sorted collection of RetrievalResults
     * @param topKRR Collecttion with the top k-NearestNeighbour cases
     */
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

    /**
     * Method to aquire the current AdaptionExplanation
     * @return <ul><li>The current AdaptionExplanation object</li>
     * <li>A new empty AdaptionExplanation object if no explanations exists</li></ul>
     */
    public AdaptionExplanation getCurrentAdaptionExplanation(){
        if(adaptionExplanations.isEmpty())
            return new AdaptionExplanation();
        int index = adaptionExplanations.size()-1;
        return adaptionExplanations.get(index);
    }

    /**
     * Method to reset and clear all the explanation components in the
     * explanation engine.
     */
    public void clearAllExplanationComponents(){
        caseExplanations.clear();
        adaptionExplanations.clear();
        exchangeRaceExplanations.clear();
    }

    /**
     * Method to add a new AdaptionExplanation to the list
     * @param adaptionExplanation
     */
    public void addAdaptionExplanation(AdaptionExplanation adaptionExplanation) {
        adaptionExplanations.add(adaptionExplanation);
    }

    /**
     * Method to aquire the adaption explanation by the cases unique ID.
     * @param caseID int The cases unique ID
     * @return The AdaptionExplanation with the supplied caseID
     * @throws NoExplanationException - if no explanation with the supplied caseID
     * exists
     */
    public AdaptionExplanation getAdaptionExplanationByID(int caseID)
            throws NoExplanationException{
        for (AdaptionExplanation ae : adaptionExplanations) {
            if(ae.getCaseID()==caseID)
                return ae;
        }
        throw new NoExplanationException(caseID, this.getClass(),AdaptionExplanation.class);
    }

    /**
     * Method to aquire the AdaptionExplanation at the supplied position in the list
     * @param pos int the position of the AdaptionExplanation in the list
     * @return The AdaptionExplanation
     * @throws NoExplanationException - if there is no AdaptionExplanation at the
     * supplied position
     */
    public AdaptionExplanation getAdaptionExplanationByPosition(int pos)
        throws NoExplanationException{
        if(adaptionExplanations.size()>=pos||pos<0)
            throw new NoExplanationException("No adaption explanation were found at position: "+pos);
        
        else
            return adaptionExplanations.get(pos);
    }

    /**
     * Method to generate the justification explanations for all the cases in
     * the list
     * @return String the justification explanation
     */
    public String generateJustificationExplanations() {
        String ret="\nGenerating justification explanations!\n\n";
        for (AdaptionExplanation ae : adaptionExplanations) {
            ret += "Case #"+ae.getCaseID()+"\n";
            ret += generateRaceExchangeExplanation(ae.getCaseID());
            ret += ae.generateExplanation()+"\n\n";
        }
        return ret;
    }

    /**
     * Method to generate the justification explanation for the case at the
     * supplied position.
     * @param index int the index of the case to generate a justification explanation for
     * @return String the justification explanation of the requested case
     */
    public String generateJustificationExplanation(int index) {
        String ret = "\nGenerating the justification for the desired case!\n\n";
        if(index>=0&&index<adaptionExplanations.size()){
            AdaptionExplanation expl = adaptionExplanations.get(index);
            ret += generateRaceExchangeExplanation(expl.getCaseID());
            ret = expl.generateExplanation();
        }
        return ret;
    }

    /**
     * Method to add a new ExchangeRaceExplanation to the explanation engine
     * @param expl The ExchangeRaceExplanation to be added to the list
     */
    public void addExchangeRaceExplanation(ExchangeRaceExplanation expl){
        exchangeRaceExplanations.add(expl);
    }

    /**
     * Method to aquire the current ExchangeRaceExplanation from the ExplanationEngine
     * @return The current ExchangeRaceExplanation
     */
    public ExchangeRaceExplanation getCurrentExchangeRaceExplanation(){
        if(exchangeRaceExplanations.isEmpty())
            return new ExchangeRaceExplanation();
        int index = exchangeRaceExplanations.size()-1;
        return exchangeRaceExplanations.get(index);
    }

    /**
     * Method to generate the justification explanation from the RaceExchangeExplanation
     * with the supplied caseID
     * @param caseID int the caseID of the RaceExchageExplanation to generate
     * the justfication explanation for
     * @return String with the justification explanation
     */
    public String generateRaceExchangeExplanation(int caseID){
        for (ExchangeRaceExplanation expl : exchangeRaceExplanations) {
            if(expl.getCaseID()==caseID)
                return expl.generateExplanation();
        }
        return "";
    }
}
