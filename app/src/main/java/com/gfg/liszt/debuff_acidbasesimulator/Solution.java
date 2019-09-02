package com.gfg.liszt.debuff_acidbasesimulator;

import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author L. List
 * @date 4.7.2018.
 */
public class Solution {
    private Component[] comps = new Component[4];
    private double[] dic;
    int n;
    final double kw=Math.pow(10,-14);
    double l=1.0, V, cu = 0;
    private double cn = 0, h = 0;

    public Solution(){
        n=0;
    }
    public Solution(@NotNull User u){
    comps[0] = new Component(u, this);
    cu = u.cu;
    cn = u.cn;
    n = u.n;
    dic = new double[n+3];
    h = cu*n+1;
    V = u.V;
} // collecting inputs

    public void AddComp(@NotNull User u){
        comps[u.citt] = new Component(u, this);
        h += u.cu*u.n+1;
        n += u.n;
        cu += u.cu;
        cn += u.cn;
        dic = new double[n+3];
        comps[u.citt].V = V;
        System.out.println(u.citt);
        for(int i=0; i<u.citt; i++){
            comps[i].SetCn(cn);
        }
        try{
            System.out.println(comps[0].K[1]);
        } catch(Exception e) {
            System.out.println("krepalo");
        }
    }
    public Component[] GetComps(){
        return(comps);
    }
    public double GetCn(){
        return(cn);
    }
    public double[] GetDic(){
        return(dic);
    }

    // titrates the Solution against an ideal base (as default; could be an acid) of volume v & concentration l
    public void tit(double v, Switch s){
        if (l != 0) {
            if (s.isChecked()) {
                l = -Math.abs(l);
            } else {
                l = Math.abs(l);
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
    private double csts(int i, Component c){
        double out = 1;
        for (int j=0;j<i;j++){
            out *= c.K[c.n-j];
        }
        return(out);
    }
    @Contract(pure = true)
    private double cstsb(int i, @NonNull Component c){
        double out = 1;
        for (int j=c.n;j>(c.n-i);j--){
            out *= c.K[j];
        }
        return(out);
    }
    public double cstsc(int i, Component c){
        double out = 1;
        for (int j=0;j<i+1;j++){
            out *= c.K[j];
        }
        return(out);
    }
    public double[] up(int exp, double co, @NonNull Component c){
        double[] out = new double[c.n+3];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0;i<n;i++){
            out[i+exp] += co*(c.n-i)/(csts(i, c));
        }
        return(out);
    }
    public double[] upb(int exp, double co, @NonNull Component c){
        double[] out = new double[c.n+3];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0;i<(c.n+1);i++){
            out[i+exp] += co/(cstsb(i, c));

        }
        return(out);
    }
    public double[] upc(int exp, double co, @NonNull Component c){
        double[] out = new double[c.n+3];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=1;i<c.n;i++){
            out[i+1+exp] += i*co*(cstsc(i, c))/Math.pow(c.kw, i);
        }
        return(out);
    }
    public double[] upd(int exp, double co, @NonNull Component c){
        double[] out = new double[c.n+3];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0;i<(c.n+1);i++){
            out[i+exp] += co*(cstsc(i, c))/Math.pow(c.kw, i);
        }
        return(out);
    }

    // a method saving the concs calculated @ checking of mass & charge conservations


    // the most important function implementing all the math and functions involved in displaying of the Solution's properties
    public String mainfunc(@NonNull Poly p, TextView[] txts){
        System.out.println("Initializing simulation.");
        dic = new double[n+3];
        for (int i = 0; i<dic.length; i++){
            dic[i] = 0;
        }

        // major redistribution required
        for (Component el: comps) {
            try {
                System.out.println("pokusaj");
                System.out.println(up(1, el.cu, el)[0]); // tu na drugoj komponenti baca error!
                el.brojnik = up(1, el.cu, el);
                System.out.println("tu nije greska");
                el.nazivnik = upb(0, 1, el);
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
                dic = p.multiply(dic, el.nazivnik);
            }catch(Exception e){
                System.out.println("Preparing poly1: EComp");
            }
        }
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                if (i!=j){
                    try {
                        comps[i].brojnik = p.multiply(comps[i].brojnik, comps[j].nazivnik);
                    }catch(Exception e){
                        System.out.println("Preparing poly2: EComp");
                    }
                }
            }
        }
        for (int i = 0; i<4; i++){
            try{
                dic = p.add(comps[i].brojnik, dic);
            }catch(Exception e){
                System.out.println("Preparing poly3: EComp");
            }
        }

        p.p = dic;
        try {
            h = -Math.log10(p.solve(this));
        } catch(Exception e){
            h = -100;
            System.out.println("nekaj grdo ne stima");
        }
        comps[0].ConcPrt(txts);
        return(String.valueOf((double)Math.round(h * 1000d) / 1000d));
    }
}
