package com.gfg.liszt.debuff_acidbasesimulator;

import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

/**
 * @author L. List
 */

public class Component extends Solution{
    boolean acid;
    int n;
    double[] K;
    double V, cu;
    private double cn;
    private double[] Concentrations;
    double[] up, down;
    String ion;

    Component(@NotNull User u, @NotNull Solution s) {
        super();
        cu = u.cu;
        cn = 0;
        try{
            cn = u.cn+s.GetComps()[0].cn;
        } catch (Exception e){
            cn = u.cn;
        }
        n = u.n;
        K = u.K;
        V = u.V;
    }

    public double GetCn(){
        return(cn);
    }
    void SetCn(double c) {
        cn = c;
    }
    void SetConcentrations(double[] c){
        Concentrations = c;
    }

    // pulling the new Solution constitution
    void TitComp(double v, double l){
        cu = cu*V/(V+v);
        try {
            cn = (cn * V + l * v) / (V + v);
        } catch(Exception e){
            cn = cn * V/ (V + v);
        }
        V += v;
        System.out.println("Solution constitution updated successfully.");
    }

    // a function setting the viewable concentration values
    void PrintConcentrations(TextView[] Texts){
        DecimalFormat format = new DecimalFormat("0.###E0");
        for (int i=n; i>-1; i--){
            Texts[(n-i)*3+1].setText(String.valueOf(format.format(Concentrations[n-i])));
        }
        Texts[22].setText(String.valueOf(format.format(Math.abs(cn))));
        if(cn<0){
            Texts[21].setText("c(X-) = ");
        } else{
            Texts[21].setText("c(X+) = ");
        }
        Texts[23].setText(String.valueOf((double)Math.round(V * 100.00d) / 100.00d));
    }
}
