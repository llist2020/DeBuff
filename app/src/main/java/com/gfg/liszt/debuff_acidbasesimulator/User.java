package com.gfg.liszt.debuff_acidbasesimulator;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

/**
 * @author L. List
 * @date 5.7.2018.
 */

public class User {
    TextView[] txts;
    double cu, cn;
    int n, itt, citt=0;
    double[] K;
    boolean ent=false;
    double V = 10;
    // constructors
    User(double icu,double icn,int in, int i, int ci){
        cu = icu;
        cn = icn;
        n = in;
        K = new double[n+1];
        K[0] = 1;
        itt = i;
        citt = ci;
        ent = true;
    }
    User (int a, TextView[] Nvws){
        switch(a){
            case 0:
                cu=0;
                cn=0;
                n=0;
                K=new double[] {1};
                ent=true;
                break;
            case 1:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, 3);
                break;
            case 2:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -3.17);
                break;
            case 3:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -9.21);
                break;
            case 4:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -4.756);
                break;
            case 5:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -7.04);
                K[2] = Math.pow(10, -11.96);
                break;
            case 6:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -6.3);
                K[2] = Math.pow(10, -10.32);
                break;
            case 7:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, 9);
                K[2] = Math.pow(10, -1.96);
                break;
            case 8:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -1.2676);
                K[2] = Math.pow(10, -4.2676);
                break;
            case 9:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -2.124);
                K[2] = Math.pow(10, -7.2);
                K[3] = Math.pow(10, -11.89);
                break;
            case 10:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -2.19);
                K[2] = Math.pow(10, -6.94);
                K[3] = Math.pow(10, -11.5);
                break;
            case 11:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -9.24);
                K[2] = Math.pow(10, -12.4);
                K[3] = Math.pow(10, -13.3);
                break;
            case 12:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -3.13);
                K[2] = Math.pow(10, -4.76);
                K[3] = Math.pow(10, -6.395);
                break;
            case 13:
                n = 4;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -1.99);
                K[2] = Math.pow(10, -2.67);
                K[3] = Math.pow(10, -6.16);
                K[4] = Math.pow(10, -10.26);
                break;
        }
        txts = Nvws;
        itt=0;
    }

    public void Outs(TextView[] t, @NonNull Component c){
        txts = t;
        for (int i=0; i<3*(c.n+1); i++){
            txts[i].setVisibility(View.VISIBLE);
        }
        for (int i=c.n; i>-1; i--){
            txts[(c.n-i)*3].setText(ConcAssign(i, c));
        }
    }
    // defines species' tags
    public String ConcAssign(int i, Component c){
        String out = "c(";
        if (i!=0){
            out += "H";
            if (i!=1){
                out += String.valueOf(i);
            }
        }
        out += "A";
        if (c.n-i!=0){
            if (c.n-i!=1){
                out += String.valueOf(c.n-i);
            }
            out += "-";
        }
        out += ") = ";
        return(out);
    }
}