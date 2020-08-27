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

package org.neuroph.core.events;

import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;

/**
 * This class holds information about the source and type of some neural network event.
 * @author Zoran Sevarac <sevarac@gmail.com>
 */
public class NeuralNetworkEvent extends java.util.EventObject {

    Type eventType;

    public NeuralNetworkEvent(NeuralNetwork source, Type eventType) {
        super(source);
        this.eventType = eventType;
    }

    public NeuralNetworkEvent(Layer source, Type eventType) {
        super(source);
        this.eventType = eventType;
    }

    public NeuralNetworkEvent(Neuron source, Type eventType) {
        super(source);
        this.eventType = eventType;
    }

    public NeuralNetworkEvent.Type getEventType() {
        return eventType;
    }

    /**
     * Types of neural network events
     */
    public static enum Type {
        CALCULATED,          // 神经网络当前层触发计事件类型.
        LAYER_ADDED,         // 增加神经网络当前层时触发的事件类型.
        LAYER_REMOVED,       // 删除神经网络当前层时触发的事件类型.
        NEURON_ADDED,        // 增加神经元触发的事件类型;
        NEURON_REMOVED,      // 删除神经元触发的事件类型;
        CONNECTION_ADDED,    // 增加一对神经元之间连接触发的事件类型;
        CONNECTION_REMOVED;  // 删除一对神经元之间连接触发的事件类型;
    }


}