package com.gfg.liszt.debuff_acidbasesimulator;

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
    final double kw=Math.pow(10,-14);
    double l=1.0, V, cu = 0;
    private double cn = 0, h = 0;

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
} // collecting inputs

    void AddComp(@NotNull User u, boolean sw){
        // KAJ AK JE CITT VECI OD 4
        comps[u.citt] = new Component(u, this);
        h += u.cu*u.n+1;
        n += u.n;
        cu += u.cu;
        cn += u.cn;
        dic = new double[n+3];
        comps[u.citt].V = V;
        comps[u.citt].acid = !sw;
        for(int i=0; i<u.citt; i++){
            comps[i].SetCn(cn);
        }
        if (u.ent){
            u.ion = "A";
        }
        u.SetAcid(!sw);
        comps[u.citt].ion = u.ion;
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

    // titrates the Solution against an ideal base (as default; could be an acid) of volume v & concentration l
    void tit(double v, Switch s){
        if (l != 0) {
            if (s.isChecked()) {
                l = Math.abs(l);
            } else {
                l = -Math.abs(l);
            }
        }
        for (Component el: comps) {
            try {
                el.titComp(v, l);
            } catch (Exception e) {
                System.out.println("EComp"); //empty component
            }
        }
        try {
            cn = (cn * V + l * v) / (V + v);
        } catch(Exception e){
            cn = cn * V/ (V + v);
        }
        V += v;
    }

    // some math functions following
    @org.jetbrains.annotations.Contract(pure = true)
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

    // the most important function implementing all the math and functions involved in displaying of the Solution's properties
    String MainFunction(@NonNull Poly p, TextView[] t){
        System.out.println("Initializing simulation.");
        dic = new double[n+3];
        for (int i = 0; i<dic.length; i++){
            dic[i] = 0;
        }

        // major redistribution required
        for (Component el: comps) {
            try {
                if (el.acid){
                    el.up = UpA(el.cu, el);
                    el.down = DownA(1, el);
                } else{
                    el.up = UpB(el.cu, el);
                    el.down = DownB(1, el);
                }
                System.out.println("Component application successful.");
            } catch(Exception e){
                System.out.println("Collecting data: EComp"); //empty component
            }
        }
        dic[0] = kw;
        dic[1] = -cn;
        dic[2] = -1;
        for (Component el: comps){
            try {
                dic = p.Multiply(dic, el.down);
            }catch(Exception e){
                System.out.println("Preparing poly1: EComp");
            }
        }
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                if (i!=j){
                    try {
                        comps[i].up = p.Multiply(comps[i].up, comps[j].down);
                    }catch(Exception e){
                        if(j==0) {
                            System.out.println("Preparing poly2: EComp");
                        }
                    }
                }
            }
        }
        for (int i = 0; i<4; i++){
            try{
                dic = p.Add(comps[i].up, dic);
            }catch(Exception e){
                System.out.println("Preparing poly3: EComp");
            }
        }

        p.p = dic;
        try {
            h = -Math.log10(p.Solve(this));
        } catch(Exception e){
            h = -100;
            System.out.println(e.getMessage());
        }
        comps[0].PrintConcentrations(t);
        return(String.valueOf((double)Math.round(h * 1000d) / 1000d));
    }
}
