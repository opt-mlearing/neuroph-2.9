/**
 * Copyright 2010 Neuroph Project http://neuroph.sourceforge.net
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neuroph.nnet.comp.neuron;

import org.neuroph.core.Connection;
import org.neuroph.core.Neuron;

/**
 * 神经元只有一个固定偏置的输入，默认为1.
 * Neuron with constant high output (1), used as bias
 *
 * @author Zoran Sevarac <sevarac@gmail.com>
 * @see Neuron
 */
public class BiasNeuron extends Neuron {

    /**
     * The class fingerprint that is set to indicate serialization compatibility
     * with a previous version of the class
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates an instance of BiasedNeuron.
     */
    public BiasNeuron() {
        super();
        this.output = 1;
    }

    @Override
    public void calculate() {
        this.output = 1;
    }

    @Override
    public void addInputConnection(Connection connection) {

    }

    @Override
    public void addInputConnection(Neuron fromNeuron, double weightVal) {

    }

    @Override
    public void addInputConnection(Neuron fromNeuron) {

    }

}
