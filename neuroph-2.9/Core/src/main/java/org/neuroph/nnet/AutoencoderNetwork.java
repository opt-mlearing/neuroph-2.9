package org.neuroph.nnet;

/**
 * Auto Encoder Neural Network
 */
public class AutoencoderNetwork extends MultiLayerPerception {

    public AutoencoderNetwork(int inputsCount, int hiddenCount) {
        super(inputsCount, hiddenCount, inputsCount);
    }

}
