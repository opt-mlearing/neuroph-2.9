/**
 * Copyright 2013 Neuroph Project http://neuroph.sourceforge.net
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.neuroph.core.learning.error;

import java.io.Serializable;

import com.sun.tools.javac.util.Assert;

/**
 * 绝对距离误差.
 *
 * @author Nevena Milenkovic
 */
public final class MeanAbsoluteError implements ErrorFunction, Serializable {

    /**
     * transient 短暂，临时的意思，对于transient修饰的成员变量，在实例化的序列化处理过程中会被忽略.
     * 因此，transient变量不会贯穿对象的序列化和反序列化，生命周期仅存在于调用者的内存中而不会写到磁盘持久化.
     */
    private transient double totalError;

    /**
     * Number of patterns - n
     */
    private transient int patternCount;

    public MeanAbsoluteError() {
        reset();
    }

    @Override
    public double[] addPatternError(double[] predictedOutput, double[] targetOutput) {
        // suggest add assert.
        assert predictedOutput.length == targetOutput.length;
        double[] patternError = new double[targetOutput.length];

        for (int i = 0; i < predictedOutput.length; i++) {
            patternError[i] = predictedOutput[i] - targetOutput[i];
            totalError += Math.abs(patternError[i]);
        }

        patternCount++;
        return patternError;
    }

    @Override
    public void reset() {
        totalError = 0d;
        patternCount = 0;
    }

    @Override
    public double getTotalError() {
        return totalError / ((double) patternCount);
    }

}