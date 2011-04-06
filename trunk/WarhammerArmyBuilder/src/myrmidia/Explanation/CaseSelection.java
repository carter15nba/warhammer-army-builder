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

import java.util.Collection;
import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CBRQuery;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;
import jcolibri.method.retrieve.RetrievalResult;
import myrmidia.CBR.Resources.ArmySimilarity;
import myrmidia.CBR.Resources.SimilarityMeasure;
import myrmidia.Warhammer.Case;
import myrmidia.Warhammer.Case.Races;

/**
 *
 * @author Glenn Rune Strandbråten
 */
public class CaseSelection implements Explanation{
    private Collection<RetrievalResult> retrievalResults;
    private SimilarityMeasure similarityMeasure;
    private CBRQuery cbrq;
    private int k;
    private static final String TAB ="  ";
    private static final String HAVE_SIM = " have a similarity of ";

    public CaseSelection(){}

    public CaseSelection(Collection<RetrievalResult> ret, SimilarityMeasure sim,
            int k, CBRQuery cbrq){
        this.retrievalResults = ret;
        this.similarityMeasure = sim;
        this.k = k;
        this.cbrq = cbrq;
    }

    public String generateExplanation() {
        return k+" cases were found as the most similar cases based on your "
                + "query and the following similarities: "+getSimilarities();
    }

    private String getSimilarities(){
        String simil = "";
        int count = 0;
        for (RetrievalResult retrievalResult : retrievalResults) {
            Case query = (Case) cbrq.getDescription();
            Case _case = (Case) retrievalResult.get_case().getSolution();
            Races qr = query.getArmy().getPlayerRace();
            Races cr = _case.getArmy().getPlayerRace();
            Races qor = query.getOpponent();
            Races cor = _case.getOpponent();
            int i = 0;
            if(qr==cr)
                i=1;
            count++;
            simil+="\nFound case: "+count;
            simil += "\n"+TAB+"The query player race "+qr+HAVE_SIM+
                    i+" with the case player race "+cr;
            i = 0;
            if(qor==cor)
                i=1;
            simil +="\n"+TAB+"The query opponent race "+qor+HAVE_SIM+
                    i+" with the case opponent "+cor;
            int qArmyPoints = query.getArmy().getArmyPoints();
            int cArmyPoints = _case.getArmy().getArmyPoints();
            ArmySimilarity armySim = similarityMeasure.getArmySimilarityFunction();
            double pointSim = armySim.computeArmyPointSimilarity(cArmyPoints, qArmyPoints);
            simil +="\n"+TAB+"The query army points "+qArmyPoints+HAVE_SIM+
                    pointSim+" with the case army points "+cArmyPoints +
                    "; based on a interval("+armySim.getInterval()+") "
                    + "calculation or perfect similarity if either points use "
                    + "the wildcard value: 0";
        }
        return simil;
    }

    /**
     * @param retrievalResults the retrievalResults to set
     */
    public void setRetrievalResults(Collection<RetrievalResult> retrievalResults) {
        this.retrievalResults = retrievalResults;
    }

    /**
     * @param similarityMeasure the similarityMeasure to set
     */
    public void setSimilarityMeasure(SimilarityMeasure similarityMeasure) {
        this.similarityMeasure = similarityMeasure;
    }

    /**
     * @param k the k to set
     */
    public void setK(int k) {
        this.k = k;
    }

    /**
     * @param cbrq the cbrq to set
     */
    public void setCbrq(CBRQuery cbrq) {
        this.cbrq = cbrq;
    }
}
