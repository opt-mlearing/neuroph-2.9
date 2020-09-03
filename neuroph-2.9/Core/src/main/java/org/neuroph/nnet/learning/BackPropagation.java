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

package org.neuroph.nnet.learning;

import java.util.List;

import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.Neuron;
import org.neuroph.core.transfer.TransferFunction;

/**
 * Back Propagation learning rule for Multi Layer Perceptron neural networks.
 *
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class BackPropagation extends LMS {

    /**
     * The class fingerprint that is set to indicate serialization
     * compatibility with a previous version of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new instance of BackPropagation learning
     */
    public BackPropagation() {
        super();
    }


    /**
     * This method implements weight update procedure for the whole network
     * for the specified  output error vector.
     *
     * @param outputError output error vector
     */
    @Override
    protected void calculateWeightChanges(double[] outputError) {
        // 在backPropagation模型中，首先"计算输出层"和"输出层的前一层"之间的delta的权值.
        calculateErrorAndUpdateOutputNeurons(outputError);
        // 在多个隐藏层直接计算.
        calculateErrorAndUpdateHiddenNeurons();
    }


    /**
     * This method implements weights update procedure for the output neurons
     * Calculates delta/error and calls updateNeuronWeights to update neuron's weights
     * for each output neuron
     *
     * @param outputError error vector for output neurons
     */
    protected void calculateErrorAndUpdateOutputNeurons(double[] outputError) {
        int i = 0;
        // for all output neurons
        final List<Neuron> outputNeurons = neuralNetwork.getOutputNeurons();
        for (Neuron neuron : outputNeurons) {
            // if error is zero, just set zero error and continue to next neuron
            // 如何误差为0，则delta 权值为0.
            if (outputError[i] == 0) {
                neuron.setDelta(0);
                i++;
                continue;
            }
            // otherwise calculate and set error/delta for the current neuron.
            // 获取当前神经元使用的激活函数, 其中transferFunction.getDerivative(..), transformFunction实例对象同时持有激活函数的求导对象.
            final TransferFunction transferFunction = neuron.getTransferFunction();
            final double neuronInput = neuron.getNetInput();
            // delta = (y-d)*df(net)
            // 通过偏导计算delta.
            final double delta = outputError[i] * transferFunction.getDerivative(neuronInput);
            neuron.setDelta(delta);
            // and update weights of the current neuron
            calculateWeightChanges(neuron);
            i++;
        } // end for.
    }

    /**
     * This method implements weights adjustment for the hidden layers
     */
    protected void calculateErrorAndUpdateHiddenNeurons() {
        List<Layer> layers = neuralNetwork.getLayers();
        // 反向计算，起点就是隐藏层.
        for (int layerIdx = layers.size() - 2; layerIdx > 0; layerIdx--) {
            for (Neuron neuron : layers.get(layerIdx).getNeurons()) {
                // calculate the neuron's error (delta)
                final double delta = calculateHiddenNeuronError(neuron);
                neuron.setDelta(delta);
                // and update weights of the current neuron
                calculateWeightChanges(neuron);
            } // end
        } // for
    }

    /**
     * Calculates and returns the neuron's error (neuron's delta) for the given neuron param
     *
     * @param neuron neuron to calculate error for
     * @return neuron error (delta) for the specified neuron
     */
    protected double calculateHiddenNeuronError(Neuron neuron) {
        double deltaSum = 0d;
        for (Connection connection : neuron.getOutConnections()) {
            double delta = connection.getToNeuron().getDelta() * connection.getWeight().value;
            deltaSum += delta; // weighted delta sum from the next layer
        } // for

        TransferFunction transferFunction = neuron.getTransferFunction();
        double netInput = neuron.getNetInput();
        // does this use netInput or cached output in order to avoid double caluclation?
        double f1 = transferFunction.getDerivative(netInput);
        double delta = f1 * deltaSum;
        return delta;
    }

}
