package com.gfg.liszt.debuff_acidbasesimulator;

import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

/**
 * @author AnaKranjcev
 * @date 6.8.2019.
 */
public class Component extends Solution{
    double[] K;
    double[] conc;
    int n;
    double V, cu;
    private double cn, h;


    public Component(@NotNull User u) {
        super();
        cu = u.cu;
        cn = u.cn;
        n = u.n;
        K = u.K;
        h = cu*n+1;
        V = u.V;
    }
    public void setConcentrations(double[] c){
        conc = c;
    }
    public double GetCn(){
        return(cn);
    }
    public void SetCn(double c) {
        cn = c;
    }
    public void titComp(double v, double l){
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
    void ConcPrt(TextView[] txts){
        DecimalFormat format = new DecimalFormat("0.###E0");
        for (int i=n; i>-1; i--){
            txts[(n-i)*3+1].setText(String.valueOf(format.format(conc[n-i])));
        }
        txts[22].setText(String.valueOf(format.format(Math.abs(cn))));
        if(cn<0){
            txts[21].setText("c(X-) = ");
        } else{
            txts[21].setText("c(X+) = ");
        }
        txts[23].setText(String.valueOf((double)Math.round(V * 100.00d) / 100.00d));
    }

    //void ConcPrtList()
}
