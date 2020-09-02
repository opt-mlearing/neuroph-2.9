package org.neuroph.nnet.learning;

import org.junit.Before;
import org.junit.Test;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.nnet.ElmanNetwork;

/**
 * @author caogaoli@163.com
 * @data 2020.09.01
 */
public class ElmanNetworkTest {

    private DataSet dataSet;

    @Before
    public void setUp() {
        dataSet = new DataSet(2, 1);
        dataSet.addRow(new DataSetRow(new double[]{0, 0}, new double[]{0}));
        dataSet.addRow(new DataSetRow(new double[]{0, 1}, new double[]{1}));
        dataSet.addRow(new DataSetRow(new double[]{1, 0}, new double[]{1}));
        dataSet.addRow(new DataSetRow(new double[]{1, 1}, new double[]{0}));
    }

    @Test
    public void tesGetElmanNetWorkInstance() {
        // be careful, contextNeuronsCount=hiddenNeuronsCount+1;
        ElmanNetwork elmanNetwork =
                new ElmanNetwork(2, 5, 6, 1);
        elmanNetwork.learn(dataSet);
    }

}
