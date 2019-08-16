package com.gfg.liszt.debuff_acidbasesimulator;

import android.widget.Switch;
import android.widget.TextView;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author L. List
 * @date 4.7.2018.
 */
public class Solution {
    private Component[] comps = new Component[4];
    double[] K;
    private double[] dic;
    int n;
    final double kw=Math.pow(10,-14);
    double l=1.0, V, cu = 0;
    private double cn = 0, h = 0;

    public Solution(){
        n=0;
    }
    public Solution(@NotNull User u){
    comps[0] = new Component(u);
    cu = u.cu;
    cn = u.cn;
    n = u.n;
    K = u.K;
    dic = new double[n+3];
    h = cu*n+1;
    V = u.V;
} // collecting inputs

    public void AddComp(@NotNull User u){
        comps[u.citt] = new Component(u);
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
    private double cstsb(int i, Component c){
        double out = 1;
        for (int j=c.n;j>(c.n-i);j--){
            out *= c.K[j];
        }
        return(out);
    }
    public double cstsc(int i, Component c){
        double out = 1;
        for (int j=0;j<i;j++){
            out *= c.K[j+1];
        }
        return(out);
    }
    void up(int exp, double co, Component c){
        for (int i=0;i<n;i++){
            try{
                dic[i+exp] += co*(c.n-i)/(csts(i, c));

            } catch (Exception ex){
                dic[i+exp] = co*(c.n-i)/(csts(i, c));
            }
        }
    }
    void upb(int exp, double co, Component c){
        for (int i=0;i<(c.n+1);i++){
            try{
                dic[i+exp] += co/(cstsb(i, c));
            } catch (Exception ex){
                dic[i+exp] = co/(cstsb(i, c));
            }
        }
    }

    // a method saving the concs calculated @ checking of mass & charge conservations


    // the most important function implementing all the math and functions involved in displaying of the Solution's properties
    public String mainfunc(Poly p, TextView[] txts){
        System.out.println("Initializing simulation.");
        dic = new double[comps[0].n+3];
        // major distribution required
        /*for (Component el: comps) {
            try {
                up(1, -el.cu, el);
                upb(2, 1, el);
                upb(0, -kw, el);
                upb(1, cn, el);
                System.out.println("Component application successful.");
            } catch(Exception e){
                System.out.println("EComp"); //empty component
            }
            //el.ConcPrt(txts);
        }*/
        up(1, -comps[0].cu, comps[0]);
        upb(2, 1, comps[0]);
        upb(0, -kw, comps[0]);
        upb(1, cn, comps[0]);

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
