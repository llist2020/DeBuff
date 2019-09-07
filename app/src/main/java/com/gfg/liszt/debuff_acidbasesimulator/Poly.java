package com.gfg.liszt.debuff_acidbasesimulator;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

/**
 * @author L. List
 * @date 4.7.2018.
 */
public class Poly {
    double[] p;
    private final double dx = 6;
    Poly(double[] poo){
        p = poo;
    } //constructor

    // returns the value of the polynomial at x
    private double cal(double x){
        double out = 0;
        for (int i = 0; i<this.p.length; i++){
            out += p[i]*Math.pow(x, i); // the coefficient's index is equal to exponent of x next to it
        }
        return(out);
    }
    // reviews the offered polynomial root (H+ concentration)
    private boolean check(double h, @NonNull Solution s) {
        System.out.println("Checking the offered root.");
        boolean mcons = true;
        double outb = 0;
        Component[] comps = s.GetComps();

        for (Component el: comps) {
            try {
                double[] c = new double[el.n+1];
                double out = 0;
                for (int i = 0; i < el.n + 1; i++) {
                    out += el.cstsc(i, el) / (Math.pow(h, i));
                }
                c[0] = el.cu / out; // calculates conc of the HnA specie
                out = 0;
                for (int i = 1; i < el.n + 1; i++) {
                    c[i] = c[i - 1] * el.K[i] / h;
                } // defines concentrations' values
                for (int i = 0; i < c.length; i++) {
                    out += c[i];
                } // calculates overall concentration
                for (int i = 0; i < c.length; i++) {
                    outb += c[i] * i;
                } // calculates overall charge

                mcons = (((double) Math.round(out * 10d) / 10d) == ((double) Math.round(el.cu * 10d) / 10d) || ((double) Math.round(out * 100d) / 100d) == ((double) Math.round(el.cu * 100d) / 100d)) && mcons; // law of conservation of mass

                if (mcons){
                    el.setConcentrations(c);
                }
            } catch (Exception e) {
                System.out.println("Check: EComp"); //empty component
            }
        }
        boolean chcons = (((double) Math.round((outb + s.kw / h) * 10d) / 10d) == ((double) Math.round((s.GetCn() + h) * 10d) / 10d) || ((double) Math.round((outb + s.kw / h) * 100d) / 100d) == ((double) Math.round((s.GetCn() + h) * 100d) / 100d)); // law of conservation of charge

        if (mcons && chcons) {
            return (true);
        } else {
            System.out.println("Illegitimate root.");
            return (false);
        }

    }
    public double solve(Solution s) { // returns a legitimate root by Newton's method with given initial x
        System.out.println("Finding a legitimate root.");
        System.out.println(p.length);
        System.out.println("poly:");
        for (double el: p){
            System.out.println(el);
        }
        double x = s.cu*s.n+1, cache = x;
        if (check(dx, s)) {
            return (dx);
        } //just in case the lower border value is a root (the higher one cannot be a root)
        double[] der = new double [this.p.length-1];
        for (int i = 0; i<der.length; i++){
            der[i] = this.p[i+1];
            der[i] *= i+1;
        }
        Poly P_der = new Poly(der); // creates a new polynomial P_der, where P_der = d(p(x))/d(x)
        x -= cal(x)/P_der.cal(x);
        while (true){
            x -= cal(x)/P_der.cal(x);
            if ((Math.abs(x-cache)<Math.pow(10, Math.log(Math.abs(x))/2.3-dx))||(x==cache)){
                if (check(x, s)){
                    System.out.println(x);
                    return(x);
                } else{
                    if (x>0){
                        x = Math.pow(10, -15);
                    } else{
                        System.out.println("newts fucked up");
                        //return(-100);
                        x = 100;
                    }
                }
            } else{
                if (x<-10){
                    x = s.cu*s.n+1;
                }
            }
            cache = x;
        }
    }
    public double[] add(@NonNull double[] a, @NonNull double[] b){
        if (a.length<b.length){
            double[] out = b.clone();
            for (int i=0; i<a.length; i++){
                out[i] += a[i];
            }
            System.out.println("add");
            System.out.println(out.length);
            return(out);
        } else{
            double[] out = a.clone();
            for (int i=0; i<b.length; i++){
                out[i] += b[i];
            }
            System.out.println("add");
            System.out.println(out.length);
            for (double el: out){
                System.out.println(el);
            }
            return(out);
        }
    }
    public double[] multiply(@NotNull double[] a, @NonNull double[] b){
        double[] out = new double[a.length+b.length-1];
        double[] outs = out.clone();
        int l = out.length;
        System.out.println("mul");
        System.out.println("a");
        for (double el: a){
            System.out.println(el);
        }
        System.out.println("b");
        for (double el: b){
            System.out.println(el);
        }
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i = 0; i<a.length; i++){
            for (int j = 0; j<b.length; j++){
                out[i+j] += a[i]*b[j];
            }
        }
        System.out.println(out.length);
        for (double el: out){
            System.out.println(el);
        }
        for (int i = out.length-1; i>0; i--){
            if (out[i] == 0.0){
                l = i;
            } else{ break;}
        }
        outs = new double[l];
        for (int i = 0; i<l; i++){
            outs[i] = out[i];
        }
        return(outs);
    }
}
