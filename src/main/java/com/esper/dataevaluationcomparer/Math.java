/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esper.dataevaluationcomparer;

import java.util.List;

/**
 *
 * @author tomas
 */
public class Math {
    public static double listIntAverage(List<Integer> list){
        if (list == null || list.isEmpty())
            return 0.0;
        long sum = 0;
        int n = list.size();
        for (int i = 0; i < n; i++)
            sum += list.get(i);
        return ((double) sum) / n;
    }
    
    public static double listDoubleAverage(List<Double> list){
        if (list == null || list.isEmpty())
            return 0.0;
        double sum = 0;
        int n = list.size();
        for (int i = 0; i < n; i++)
            sum += list.get(i);
        return sum / n;
    }
}
