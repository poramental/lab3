package com.lab3.lab3.labapi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lab3.lab3.labapi.exceptions.ExpressionParseException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Expression {
    
    private Matrix responseMatrix;
    private Matrix matrixOfCoefficents;

    public Expression(Matrix responseMatrix, Matrix matrixOfUnknows) {
        this.responseMatrix = responseMatrix;
        this.matrixOfCoefficents = matrixOfUnknows;
    }

    public Expression parseExpression(String _exp) throws ExpressionParseException{
        String[] exprLines =  _exp.split("\n");
        int countOfLines = 0;
        for(String line : exprLines){
           
            
            if(Objects.equals(line, "")) continue;
            else countOfLines++;
        }
        
        Matrix matrixOfCoefficents = new Matrix(countOfLines,countOfLines);
        Matrix matrixOfResp = new Matrix(1,countOfLines);
        List<Double> listOfCoef = new ArrayList<>();
        List<String> listOfRes = new ArrayList<>();
        for (String line : exprLines){
            Matcher m = Pattern.compile("(-?\\d+)(\\.\\d+)*x|x[1-3]").matcher(line);
            while (m.find()) {
               
                String _element = m.group().strip().replaceAll("x.*", "");

                if(Objects.equals(_element, "")) _element = "1";
                double element = Double.parseDouble(_element);
                
                
                listOfCoef.add(element);
                
            }
            
        }
        if(listOfCoef.size() != Math.pow(countOfLines, 2)) {
            throw new ExpressionParseException("Не правильно задано уравнение 😡.");
        }
        for (String line : exprLines){
            Matcher m = Pattern.compile("=.*(-?\\d+)(\\.\\d+)*").matcher(line);
            while (m.find()) {
               
                String element = m.group().replaceAll("=","" ).replaceAll(" ", "");
                
                listOfRes.add(element);
            }
        }
        
        int i = 0;
        int j = 0;
        for(double coef : listOfCoef){
            matrixOfCoefficents.setElement(i ,j , coef); 
            if(j == countOfLines - 1){
                j = 0;
                i++;
                continue;
            }
            j++;
        }
        
        i = 0;
        for(String res : listOfRes){
            matrixOfResp.setElement(0, i, Double.parseDouble(res));
            i++;
        }
        this.responseMatrix = matrixOfResp;
        this.matrixOfCoefficents = matrixOfCoefficents;
        return this;

        
    }

    public void show(){
        this.matrixOfCoefficents.displayMatrix();
        this.responseMatrix.displayMatrix();
    }
        
}
