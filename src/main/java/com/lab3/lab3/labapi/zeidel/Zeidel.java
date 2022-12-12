package com.lab3.lab3.labapi.zeidel;

import com.lab3.lab3.labapi.Expression;
import com.lab3.lab3.labapi.Matrix;

public class Zeidel {

    public static double[] solve(Expression expr){
        
        int n = expr.getMatrixOfCoefficents().getVerticalLength();
        Matrix A = expr.getMatrixOfCoefficents();
        Matrix B = expr.getResponseMatrix();
        double[] x = new double[n];
        double g = 0;
        double s =0;
        final double eps = 0.001;
        int i = 0;
        for( i = 0; i < n; i++ ){
            x[i] = B.getElement(0, i);
        } 

        do{
            for( i = 0; i < n; i++){
                g = B.getElement(0, i);
                for(int j = 0; j < n; j++ ){
                    g += A.getElement(i, j) * x[i]; 
                }
                s += (x[i] - g) * (x[i] - g);
                x[i] = g;
            }
        }while(Math.sqrt(s) >= eps * (1 - Matrix.thirdNorm(A)) / Matrix.thirdNorm(A));
        
        return x;
        
    }
}
