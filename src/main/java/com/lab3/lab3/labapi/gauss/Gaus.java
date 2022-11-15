package com.lab3.lab3.labapi.gauss;

import com.lab3.lab3.labapi.Expression;
import com.lab3.lab3.labapi.Matrix;
import com.lab3.lab3.labapi.exceptions.GaussException;

public class Gaus {
    
    public static double[] decide(Expression expr) throws GaussException{
        int n = expr.getMatrixOfCoefficents().getVerticalLength();
        Matrix a = expr.getMatrixOfCoefficents();
        Matrix y = expr.getResponseMatrix();
        double max;
        int k, index;
        final double eps = 0.00001;  // точность
        double[] x = new double[n];
        k = 0;
        while (k < n) 
        {
            // Поиск строки с максимальным a[i][k]
            max = Math.abs(a.getElement(k, k));
            index = k;
            for (int i = k + 1; i < n; i++) 
            {
                if (Math.abs(a.getElement(i, k)) > max)
                {
                    max = Math.abs(a.getElement(i, k));
                    index = i;
                }
            }
            // Перестановка строк
            if (max < eps) 
            {
                // нет ненулевых диагональных элементов
                throw new GaussException("нет ненулевых диагональных элементов.");
            
            }
            for (int j = 0; j < n; j++) 
            {
                double temp = a.getElement(k, j);
                a.setElement(k, j, a.getElement(index, j));
                a.setElement(index, j, temp);
            }
            double temp = y.getElement(0, k);
            y.setElement(0, k, y.getElement(0, index));
            y.setElement(0, index, temp);
            // Нормализация уравнений
            for (int i = k; i < n; i++) 
            {
                double _temp = a.getElement(i, k);
                if (Math.abs(temp) < eps) continue; // для нулевого коэффициента пропустить
                for (int j = 0; j < n; j++) 
                    a.setElement(i, j, a.getElement(i, j)/ _temp); 
                y.setElement(0, i, a.getElement(0, i)/ _temp);
                if (i == k)  continue; // уравнение не вычитать само из себя
                for (int j = 0; j < n; j++){
                    a.setElement(i, j, a.getElement(i, j)- a.getElement(k, j));
                }
                y.setElement(0, i, y.getElement(0, i) - y.getElement(0, k)) ;
                // y[i] = y[i] - y[k];
            }
            k++;
        }
        // обратная подстановка
        for (k = n - 1; k >= 0; k--)
        {
            x[k] = y.getElement(0, k);
            for (int i = 0; i < k; i++){
                y.setElement(0,i,y.getElement(0, i) - a.getElement(i, k) * x[k]);
            }
            
        }
        return x;
        }
    }

