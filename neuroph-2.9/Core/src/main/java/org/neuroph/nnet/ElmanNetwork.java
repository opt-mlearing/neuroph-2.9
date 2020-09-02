package org.neuroph.nnet;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.comp.layer.InputLayer;
import org.neuroph.nnet.comp.neuron.BiasNeuron;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.ConnectionFactory;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;

/**
 * <p>
 * Elman神经网络是由Jeffrey L.Elman于1990年提出，是一种反馈神经网络.
 * Elman网络由4层组成:
 * (1)输入层：信号传输作用;
 * (2)隐藏层：
 * (3)承接层，也称上下文单元或状态层，承接层从隐含层接收反馈信息，用来记忆隐含层层神经元前一时刻的输出值，承接层神经元的输出经延迟与出储存，
 * 再输入到隐含层。这样就使其对历史数据具有敏感性，增加了网络自身处理动态信息的能力;
 * (4)输出层：仅起线性加权作用.
 * </p>
 * <p>
 * 输入层由一个输入神经元和一组上下文神经元单元组成，隐藏层前一时间步的神经元作为上下文神经元的输入，
 * 在隐藏层中每一个神经元都有一个上下文神经元。由于前一时间步的状态作为输入的一部分。因此，Elman神经网络具有一定的内存，上下文单元代表内存.
 * </p>
 * Under development: Learning rule BackProp Through Time required
 *
 * @author zoran
 */
public class ElmanNetwork extends NeuralNetwork {

    public ElmanNetwork(int inputNeuronsCount, int hiddenNeuronsCount, int contextNeuronsCount, int outputNeuronsCount) {
        createNetwork(inputNeuronsCount, hiddenNeuronsCount, contextNeuronsCount, outputNeuronsCount);
    }
    // three layers: input, hidden, output
    // as mlp add context layer
    // elman connect output of hidden layer to input of context layer
    // output of context to input of hidden layer 


    private void createNetwork(int inputNeuronsCount, int hiddenNeuronsCount, int contextNeuronsCount, int outputNeuronsCount) {

        // create input layer && 输入层
        InputLayer inputLayer = new InputLayer(inputNeuronsCount);
        inputLayer.addNeuron(new BiasNeuron());
        addLayer(inputLayer);

        NeuronProperties neuronProperties = new NeuronProperties();
        // neuronProperties.setProperty("useBias", true) && 设置隐藏层的激活函数.
        neuronProperties.setProperty("transferFunction", TransferFunctionType.SIGMOID);
        Layer hiddenLayer = new Layer(hiddenNeuronsCount, neuronProperties);
        hiddenLayer.addNeuron(new BiasNeuron());
        addLayer(hiddenLayer);

        ConnectionFactory.fullConnect(inputLayer, hiddenLayer);

        Layer contextLayer = new Layer(contextNeuronsCount, neuronProperties);
        addLayer(contextLayer); // we might also need bias for context neurons?

        Layer outputLayer = new Layer(outputNeuronsCount, neuronProperties);
        addLayer(outputLayer);

        ConnectionFactory.fullConnect(hiddenLayer, outputLayer);

        ConnectionFactory.forwardConnect(hiddenLayer, contextLayer); // forward or full connectivity?
        ConnectionFactory.fullConnect(contextLayer, hiddenLayer);


        // set input and output cells for network
        NeuralNetworkFactory.setDefaultIO(this);

        // set learnng rule
        this.setLearningRule(new BackPropagation());

    }

}
