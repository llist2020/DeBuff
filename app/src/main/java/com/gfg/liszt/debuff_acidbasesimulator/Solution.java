package com.gfg.liszt.debuff_acidbasesimulator;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.Switch;
import android.widget.TextView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

/**
 * @author L. List
 */

public class Solution {
    private Component[] comps = new Component[4];
    private double[] dic;
    int n;
    final double kw=Math.pow(10, -14);
    double cu = 0, l, V;
    private double cn = 0, h;

    Solution(){
        n=0;
    }
    Solution(@NotNull User u, boolean sw){
    comps[0] = new Component(u, this);
    cu = u.cu;
    cn = u.cn;
    n = u.n;
    dic = new double[n+3];
    h = cu*n+1;
    V = u.V;
    comps[0].acid = !sw;
    if (u.ent){
        u.ion = "A";
        u.SetAcid(!sw);
    }
    comps[0].ion = u.ion;
}

    void AddComp(@NotNull User u, boolean sw){
        comps[u.Slot()] = new Component(u, this);
        h += u.cu*u.n+1;
        n += u.n;
        cu += u.cu;
        cn += u.cn;
        dic = new double[n+3];
        comps[u.Slot()].V = V;
        comps[u.Slot()].acid = !sw;
        for(int i=0; i<u.Slot(); i++){
            comps[i].SetCn(cn);
        }
        if (u.ent){
            u.ion = "A";
        }
        u.SetAcid(!sw);
        comps[u.Slot()].ion = u.ion;
    }

    Component[] GetComps(){
        return(comps);
    }
    public double GetCn(){
        return(cn);
    }
    double[] GetDic(){
        return(dic);
    }

    // updates the data describing the (ideal) acid/base content
    void Titrate(double v, Switch s){
        if (l != 0) {
            if (s.isChecked()) {
                l = Math.abs(l);
            } else {
                l = -Math.abs(l);
            }
        }
        for (Component el: comps) {
            try {
                el.TitComp(v, l);
            } catch (Exception e) {
                // there is no Component at this index
            }
        }
        try {
            cn = (cn * V + l * v) / (V + v);
        } catch(Exception e){
            cn = cn * V / (V + v);
        }
        V += v;
    }

    // functions used to manipulate the provided data and generate
    // the building blocks of the polynomial
    @Contract(pure = true)
    private double cstsa(int i, @NonNull Component c){
        double out = 1;
        for (int j=0; j<c.n-i+1; j++){
            out *= c.K[j];
        }
        return(out);
    }
    @Contract(pure = true)
    double cstsb(int i, @NonNull Component c){
        double out = 1;
        for (int j=0; j<(i+1); j++){
            out *= c.K[j];
        }
        return(out);
    }
    @Contract(pure = true)
    private double[] UpA(double co, @NonNull Component c){
        double[] out = new double[c.n+2];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<c.n ; i++){
            out[i+1] += co * (c.n-i) * (cstsa(i, c));
        }
        return(out);
    }
    @Contract(pure = true)
    private double[] DownA(double co, @NonNull Component c){
        double[] out = new double[c.n+1];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<(c.n+1); i++){
            out[c.n-i] += co * (cstsb(i, c));
        }
        return(out);
    }
    private double[] UpB(double co, @NonNull Component c){
        double[] out = new double[c.n+2];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=1; i<(c.n+1); i++){
            out[i+1] -= i * co * (cstsb(i, c)) / Math.pow(c.kw, i);
        }
        return(out);
    }
    private double[] DownB(double co, @NonNull Component c){
        double[] out = new double[c.n+1];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<(c.n+1); i++){
            out[i] += co * (cstsb(i, c)) / Math.pow(c.kw, i);
        }
        return(out);
    }

    SpannableStringBuilder MainFunction(@NonNull Poly p, TextView[] t){
        System.out.println("Initializing simulation.");
        // setting up the polynomial, begining with the permanent part
        dic = new double[n+3];
        for (int i = 0; i<dic.length; i++) dic[i] = 0;
        dic[0] = kw;
        dic[1] = -cn;
        dic[2] = -1;

        // setting up the fractions describing the Components
        for (Component component: comps) {
            try {
                if (component.acid){
                    component.up = UpA(component.cu, component);
                    component.down = DownA(1, component);
                } else{
                    component.up = UpB(component.cu, component);
                    component.down = DownB(1, component);
                }
                System.out.println("Component application successful.");
            } catch(Exception e){
                // there is no Component at this index
            }
        }

        // getting rid of the denominators
        for (Component component: comps){
            try {
                dic = p.Multiply(dic, component.down);
            }catch(Exception e){
                // there is no Component at this index
            }
        }
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                if (i != j){
                    try {
                        comps[i].up = p.Multiply(comps[i].up, comps[j].down);
                    }catch(Exception e){
                        // there is no Component at this index
                    }
                }
            }
        }

        // generating the final polynomial
        for (int i = 0; i<4; i++){
            try{
                dic = p.Add(comps[i].up, dic);
            }catch(Exception e){
                // there is no Component at this index
            }
        }

        // the freshly generated polynomial is being transposed to the Poly format
        // in order to find the positive root that satisfies the laws of conservation
        p.p = dic;
        try {
            h = -Math.log10(p.Solve(this));
        } catch(Exception e){
            h = -100;
            System.out.println(e.getMessage());
        }

        // finally, the pH is returned
        // by the way, conncentrations of the Component at index 0 are to be displayed
        comps[0].PrintConcentrations(t);
        SpannableStringBuilder out = new SpannableStringBuilder(String.valueOf((double)Math.round(h * 1000d) / 1000d));
        out.setSpan(new StyleSpan(Typeface.BOLD), 0, out.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return(out);
    }
}
