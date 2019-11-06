package com.gfg.liszt.debuff_acidbasesimulator;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author L. List
 */
class Poly implements Serializable {
    double[] p;

    Poly(double[] poo){
        p = poo;
    }

    // returns the value of the polynomial at x
    private double Cal(double x){
        double out = 0;
        for (int i = 0; i<this.p.length; i++){
            // the coefficient's index is equal to exponent of x next to it
            out += p[i]*Math.pow(x, i);
        }
        return(out);
    }

    // reviews the offered polynomial root (H+ concentration)
    private boolean Check(double h, @NonNull Solution s) {
        if (h<0){
            // negative concentration - impossible
            return(false);
        }
        boolean mcons = true;
        double outa = 0, outb = 0;
        List<Component> comps = s.GetComps();

        for (int j = 0; j<s.Entered.size(); j++) {
            try {
                Component el = comps.get(j);
                double[] c = new double[el.n+1];
                double out = 0;
                if (el.acid) {
                    for (int i = 0; i < el.n + 1; i++) {
                        out += el.ConstantsB(i, el) / (Math.pow(h, i));
                    }
                } else{
                    for (int i = 0; i < el.n + 1; i++) {
                        out += el.ConstantsB(i, el) * (Math.pow((h / el.kw), i));
                    }
                }

                // calculates the concentration of the not dissociated specie
                c[0] = el.cu / out;
                out = 0;
                if (el.acid) {
                    // defines concentrations' values
                    for (int i = 1; i < el.n + 1; i++) {
                        c[i] = c[i - 1] * el.K[i] / h;
                    }

                    // calculates overall charge (acid)
                    for (int i = 0; i < c.length; i++) {
                        outa += c[i] * i;
                    }
                } else{
                    // defines concentrations' values
                    for (int i = 1; i < el.n + 1; i++) {
                        c[i] = c[i - 1] * el.K[i] * h / el.kw;
                    }
                    // calculates overall charge (base)
                    for (int i = 0; i < c.length; i++) {
                        outb += c[i] * i;
                    }
                }

                // calculates overall concentration
                for (double CurrC: c) {
                    out += CurrC;
                }

                // the law of mass conservation
                mcons = (((double) Math.round(out * 10d) / 10d) == ((double) Math.round(el.cu * 10d) / 10d) ||
                        ((double) Math.round(out * 100d) / 100d) == ((double) Math.round(el.cu * 100d) / 100d)) && mcons;

                if (mcons) el.SetConcentrations(c);
            } catch (Exception e) {
                // there is no Component at this index
            }
        }

        // the law of charge conservation
        boolean chcons = (((double) Math.round((outa + s.kw / h) * 10d) / 10d) == ((double) Math.round((outb + s.GetCn() + h) * 10d) / 10d) ||
                ((double) Math.round((outa + s.kw / h) * 100d) / 100d) == ((double) Math.round((outb + s.GetCn() + h) * 100d) / 100d));

        return(mcons && chcons);
    }

    // returns a legitimate root, implementing Newton's method with given initial x
    double Solve(@NotNull Solution s) {

        /*System.out.println("poly:");
        for (double el: p){
            System.out.println(el);
        }*/

        double x = s.cu*s.n+1, cache = x*2;
        final double dx = 6;
        // in case the lower border value is a root
        if (Check(dx, s)) {
            return (dx);
        }

        // generating the derived polynomial P_der = d(p(x))/d(x)
        double[] der = new double [p.length-1];
        for (int i = 0; i<der.length; i++){
            der[i] = p[i+1];
            der[i] *= i+1;
        }
        Poly P_der = new Poly(der);

        // implementing the Newton's method
        x -= Cal(x)/P_der.Cal(x);
        while (true){

            x -= Cal(x)/P_der.Cal(x);

            if ((Math.abs(x-cache) < Math.pow(10, Math.log(Math.abs(x)) / 2.3 - dx)) || (x==cache)){
                if (Check(x, s)){
                    // the root is stored at x now and is to be returned
                    return(x);
                } else{
                    if (x>0){
                        x = Math.pow(10, -15);
                    } else{
                        // this outcome is not prefered
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

    // functions for adding and multiplying polynomials, used at the genesis
    // of the Poly p
    double[] Add(@NonNull double[] a, @NonNull double[] b){
        if (a.length<b.length){
            double[] out = b.clone();
            for (int i=0; i<a.length; i++){
                out[i] += a[i];
            }
            return(out);
        } else{
            double[] out = a.clone();
            for (int i=0; i<b.length; i++){
                out[i] += b[i];
            }
            return(out);
        }
    }
    double[] Multiply(@NotNull double[] a, @NonNull double[] b){
        double[] out = new double[a.length+b.length-1];
        double[] outs;
        int l = out.length;
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i = 0; i<a.length; i++){
            for (int j = 0; j<b.length; j++){
                out[i+j] += a[i]*b[j];
            }
        }
        for (int i = out.length-1; i>0; i--){
            if (Double.toString(out[i]).equals("0.0")) l = i;
            else break;
        }
        outs = new double[l];
        System.arraycopy(out, 0, outs, 0, l);
        return(outs);
    }
}
