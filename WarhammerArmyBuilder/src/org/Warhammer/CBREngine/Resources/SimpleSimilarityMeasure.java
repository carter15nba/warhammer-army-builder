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
 *
 * @author Glenn Rune Strandbråten
 * @version 
 */
public class SimpleSimilarityMeasure {
    private NNConfig nnConfig = null;

    public void setNNConfig(NNConfig conf){
        nnConfig = conf;
    }
    public NNConfig getSimilarityConfig(){
        if(nnConfig==null)
            setupSimilarityConfig();
        return nnConfig;
    }
    private void setupSimilarityConfig(){
        //TODO: Fix dynamic (user selected) weigths.
        nnConfig = new NNConfig();
        Attribute attr;
        LocalSimilarityFunction func;
        //Player race
        attr = new Attribute("army",org.Warhammer.Warhammer.Case.class);
        func = localSimilarityFactory("Army", 0);
        nnConfig.addMapping(attr, func);
        nnConfig.setWeight(attr, 0.10);

        //Opponent race
        attr = new Attribute("opponent",org.Warhammer.Warhammer.Case.class);
        func = localSimilarityFactory("Equal", 0);
        nnConfig.addMapping(attr, func);
        nnConfig.setWeight(attr, 0.8);
        //Points
        attr = new Attribute("armyPoints",org.Warhammer.Warhammer.Army.class);
        func = localSimilarityFactory("Interval", 500);
        nnConfig.addMapping(attr, func);
        nnConfig.setWeight(attr, 0.8);
        //Outcome
        attr = new Attribute("outcome",org.Warhammer.Warhammer.Case.class);
        func = localSimilarityFactory("Enum", 0);
        nnConfig.addMapping(attr, func);
        nnConfig.setWeight(attr, 0.8);
    }
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
