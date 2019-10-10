package com.gfg.liszt.debuff_acidbasesimulator;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
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
        SpannableStringBuilder out;
        if(cn<0){
            out = new SpannableStringBuilder("c(X-) = ");
        } else{
            out = new SpannableStringBuilder("c(X+) = ");
        }

        try{
            out.setSpan(new SuperscriptSpan(), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            out.setSpan(new RelativeSizeSpan(0.75f), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            out.setSpan(new StyleSpan(Typeface.ITALIC), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch(Exception e){
            // no supers / other error
        }
        Texts[21].setText(out);
        Texts[23].setText(String.valueOf((double)Math.round(V * 100.00d) / 100.00d));
    }
}
