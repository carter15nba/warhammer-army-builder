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

package org.Warhammer.CBREngine.Resources;

import jcolibri.cbrcore.Attribute;
import jcolibri.method.retrieve.NNretrieval.NNConfig;
import jcolibri.method.retrieve.NNretrieval.similarity.LocalSimilarityFunction;

/**
 * Class used to assign similarity calculation objects to the different parts
 * of the Case object so that the similarity of the parts can be calculated by
 * the optimized functions.
 * @author Glenn Rune Strandbråten
 * @version 0.4
 */
public class SimilarityMeasure {
    private NNConfig nnConfig = null;

    /**
     * Method to set the NNConfig
     * @param conf the NNConfig to set.
     */
    public void setNNConfig(NNConfig conf){
        nnConfig = conf;
    }

    /**
     * Method to get the NN similarity configuration.
     * @return the NN config.
     */
    public NNConfig getSimilarityConfig(){
        if(nnConfig==null)
            setupSimilarityConfig();
        return nnConfig;
    }

    /**
     * Method to setup the similarity config by delegating the different similarity
     * calculation functions to their appropriate elements in the case and assigning
     * weigths to the similarity functions.
     */
    private void setupSimilarityConfig(){
        //TODO: Fix dynamic (user selected) weigths.
        nnConfig = new NNConfig();
        Attribute attr;
        LocalSimilarityFunction func;
        //Player race
        attr = new Attribute("army",org.Warhammer.Warhammer.Case.class);
        func = localSimilarityFactory("Army", 0);
        nnConfig.addMapping(attr, func);
        nnConfig.setWeight(attr, 0.3);

        //Opponent race
        attr = new Attribute("opponent",org.Warhammer.Warhammer.Case.class);
        func = localSimilarityFactory("Equal", 0);
        nnConfig.addMapping(attr, func);
        nnConfig.setWeight(attr, 1.0);
        //Points
        attr = new Attribute("armyPoints",org.Warhammer.Warhammer.Army.class);
        func = localSimilarityFactory("Interval", 500);
        nnConfig.addMapping(attr, func);
        nnConfig.setWeight(attr, 1.0);
        //Outcome
        attr = new Attribute("outcome",org.Warhammer.Warhammer.Case.class);
        func = localSimilarityFactory("Enum", 0);
        nnConfig.addMapping(attr, func);
        nnConfig.setWeight(attr, 1.0);
    }

    /**
     * Local similarity factory used to create the similarity function objects
     * used by different components of the case.
     * @param name String the name of the similarity function to create
     * @param param integer A value used to configure similarity functions.
     * @return The LocalSimilarityFunction object that were created or null, if
     * the name specified local similarity function could not be found.
     */
    private LocalSimilarityFunction localSimilarityFactory(String name, int param){
        if(name.equals("Interval"))
            return new Interval(param);
        else if(name.equals("Army"))
            return new ArmySimilarity();
        else if(name.equals("Enum"))
            return new jcolibri.method.retrieve.NNretrieval.similarity.local.EnumDistance();
        else if(name.equals("Equal"))
            return new jcolibri.method.retrieve.NNretrieval.similarity.local.Equal();
        return null;
    }
}
