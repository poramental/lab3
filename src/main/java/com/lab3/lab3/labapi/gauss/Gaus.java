package com.lab3.lab3.labapi.gauss;

import com.lab3.lab3.labapi.Expression;
import com.lab3.lab3.labapi.Matrix;
import com.lab3.lab3.labapi.exceptions.GaussException;

public class Gaus {
    
    public static double[] decide(Expression expr) throws GaussException{
        int n = expr.getMatrixOfCoefficents().getVerticalLength();
        Matrix a = expr.getMatrixOfCoefficents();
        Matrix y = expr.getResponseMatrix();
        int k;
        double[] x = new double[n];
        double s = 0;
        k = 0;
        while (k < n) 
        {
            y.setElement(0,k,y.getElement(0,k) / a.getElement(k, k));

            for(int j = k + 1; j < n; j++){
                    a.setElement(k, j, a.getElement(k, j) / a.getElement(k, k) );
                System.out.println("ds");
                }
        
            
            for(int i = k + 1; i < n ; i++ ){
                y.setElement(0, i, y.getElement(0,i) - a.getElement(i, k)*y.getElement(0, k));
                for(int j = k + 1;j < n; j++ ){
                    a.setElement(i, j, a.getElement(i, j) - a.getElement(i, k)*a.getElement(k, j));

                }

            }

            k++;
        }

        for(int i = 0; i < y.getHorizontalLength(); i++){
            x[i] = y.getElement(0, i);
        }

        for(int i = n - 1; i != 0;  i--){
            for(int j = k + 1; j < n;j++){
                s = a.getElement(i, j)*x[k];                
            }
           x[i] = y.getElement(0,i) - s;
            
        }

        return x;

    }

}
