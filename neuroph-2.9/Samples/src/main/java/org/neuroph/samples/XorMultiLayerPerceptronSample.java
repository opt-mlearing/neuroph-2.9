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

package org.neuroph.samples;

import java.util.Arrays;
import java.util.Random;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.learning.LearningRule;
import org.neuroph.nnet.MultiLayerPerception;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.TransferFunctionType;
import org.neuroph.util.random.WeightsRandomizer;

/**
 * This sample shows how to create, train, save and load simple Multi Layer Perceptron for the XOR problem.
 * This sample shows basics of Neuroph API.
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class XorMultiLayerPerceptronSample implements LearningEventListener {

    public static void main(String[] args) {
        new XorMultiLayerPerceptronSample().run();
    }

    /**
     * Runs this sample
     */
    public void run() {

        // create training set (logical XOR function)
        DataSet trainingSet = new DataSet(2, 1);
        trainingSet.addRow(new DataSetRow(new double[]{0, 0}, new double[]{0}));
        trainingSet.addRow(new DataSetRow(new double[]{0, 1}, new double[]{1}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 0}, new double[]{1}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 1}, new double[]{0}));

        // create multi layer perceptron
        MultiLayerPerception myMlPerceptron = new MultiLayerPerception(TransferFunctionType.SIGMOID, 2, 3, 1);
        myMlPerceptron.randomizeWeights(new WeightsRandomizer(new Random(123)));

        System.out.println(Arrays.toString(myMlPerceptron.getWeights()));

        myMlPerceptron.setLearningRule(new BackPropagation());

        myMlPerceptron.getLearningRule().setLearningRate(0.5);
        // enable batch if using MomentumBackpropagation
//        if( myMlPerceptron.getLearningRule() instanceof MomentumBackpropagation )
//        	((MomentumBackpropagation)myMlPerceptron.getLearningRule()).setBatchMode(false);

        LearningRule learningRule = myMlPerceptron.getLearningRule();
        learningRule.addListener(this);

        // learn the training set
        System.out.println("Training neural network...");
        myMlPerceptron.learn(trainingSet);

        // test perceptron
        System.out.println("Testing trained neural network");
        testNeuralNetwork(myMlPerceptron, trainingSet);

        // save trained neural network
        myMlPerceptron.save("myMlPerceptron.nnet");

        // load saved neural network
        NeuralNetwork loadedMlPerceptron = NeuralNetwork.createFromFile("myMlPerceptron.nnet");

        // test loaded neural network
        System.out.println("Testing loaded neural network");
        testNeuralNetwork(loadedMlPerceptron, trainingSet);
    }

    /**
     * Prints network output for the each element from the specified training set.
     * @param neuralNet neural network
     * @param testSet test set
     */
    public static void testNeuralNetwork(NeuralNetwork neuralNet, DataSet testSet) {

        for (DataSetRow testSetRow : testSet.getRows()) {
            neuralNet.setInput(testSetRow.getInput());
            neuralNet.calculate();
            double[] networkOutput = neuralNet.getOutput();

            System.out.print("Input: " + Arrays.toString(testSetRow.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));
        }
    }

    @Override
    public void handleLearningEvent(LearningEvent event) {
        BackPropagation bp = (BackPropagation) event.getSource();
        if (event.getEventType() != LearningEvent.Type.LEARNING_STOPPED)
            System.out.println(bp.getCurrentIteration() + ". iteration : " + bp.getTotalNetworkError());
    }

}
