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

package org.neuroph.util;

/**
 * Contains neural network types and labels.
 * 神经网络类型.
 */
public enum NeuralNetworkType {
    // 自适应线性神经网络.
    ADALINE("Adaline"),
    // 感知机.
    PERCEPTION("Perception"),
    // 多层感知机.
    MULTI_LAYER_PERCEPTION("Multi Layer Perception"),
    HOPFIELD("Hopfield"),
    KOHONEN("Kohonen"),
    NEURO_FUZZY_REASONER("Neuro Fuzzy Reasoner"),
    SUPERVISED_HEBBIAN_NET("Supervised Hebbian network"),
    UNSUPERVISED_HEBBIAN_NET("Unsupervised Hebbian network"),
    // 竞争神经网络.
    COMPETITIVE("Competitive"),
    MAXNET("Maxnet"),
    INSTAR("Instar"),
    OUTSTAR("Outstar"),
    RBF_NETWORK("RBF Network"),
    BAM("BAM"),
    // 波兹曼机.
    BOLTZMAN("Boltzman"),
    COUNTERPROPAGATION("CounterPropagation"),
    INSTAR_OUTSTAR("InstarOutstar"),
    PCA_NETWORK("PCANetwork"),
    RECOMMENDER("Recommender"),
    RECTIFIER("Rectifier");

    private String typeLabel;

    private NeuralNetworkType(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getTypeLabel() {
        return typeLabel;
    }
}
