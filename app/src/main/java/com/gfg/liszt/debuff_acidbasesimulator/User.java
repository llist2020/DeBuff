package com.gfg.liszt.debuff_acidbasesimulator;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;

/**
 * @author L. List
 * @date 5.7.2018.
 */

class User {
    TextView[] Texts;
    double cu, cn;
    int n, itt, citt=0;
    double[] K;
    boolean ent, allclr = true, acid;
    String a;
    double V = 10;

    // constructors
    User(int in, double icu, double icn, double iV, boolean ient){
        cu = icu;
        cn = icn;
        n = in;
        K = new double[n+1];
        K[0] = 1;
        V = iV;
        ent = ient;
        SetAcid(true);
    }
    User(int in, double icu, double icn, User u1){
        cu = icu;
        cn = icn;
        n = in;
        K = new double[n+1];
        System.out.println("debug");
        System.out.println(K.length);
        System.out.println(u1.K.length);
        System.arraycopy(u1.K, 0, K, 0, n+1);
        K[0] = 1;
        V = u1.V;
        itt = u1.itt;
        citt = u1.citt;
        ent = u1.ent;
        SetAcid(true);
    }
    User (int a, TextView[] Nvws){
        SetAcid(true);
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
            case 2:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -3.17);
            case 3:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -9.21);
            case 4:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -4.756);
            case 5:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -7.04);
                K[2] = Math.pow(10, -11.96);
            case 6:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -6.3);
                K[2] = Math.pow(10, -10.32);
            case 7:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, 9);
                K[2] = Math.pow(10, -1.96);
            case 8:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -1.2676);
                K[2] = Math.pow(10, -4.2676);
            case 9:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -2.124);
                K[2] = Math.pow(10, -7.2);
                K[3] = Math.pow(10, -11.89);
            case 10:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -2.19);
                K[2] = Math.pow(10, -6.94);
                K[3] = Math.pow(10, -11.5);
            case 11:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -9.24);
                K[2] = Math.pow(10, -12.4);
                K[3] = Math.pow(10, -13.3);
            case 12:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -3.13);
                K[2] = Math.pow(10, -4.76);
                K[3] = Math.pow(10, -6.395);
            case 13:
                n = 4;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -1.99);
                K[2] = Math.pow(10, -2.67);
                K[3] = Math.pow(10, -6.16);
                K[4] = Math.pow(10, -10.26);
            default:
                ent = false;
        }
        Texts = Nvws;
        itt=0;
    }

    void SetAcid(boolean i){
        acid = i;
        if (acid) a = "acid";
        else a = "base";
    }
    // defines species' tags
    private String AssignConcentrations(int i, @NonNull Component c){
        String out = "c(";
        if (c.acid) {
            if (i != 0) {
                out += "H";
                if (i != 1) {
                    out += String.valueOf(i);
                }
            }
            out += "A";
            if (c.n - i != 0) {
                if (c.n - i != 1) {
                    out += String.valueOf(c.n - i);
                }
                out += "-";
            }
        } else{
            if (i != 0 && i != c.n){
                out += "[B";
                if (i != 1) out += "(OH)"+i;
                else if (i == 1) out += "(OH)";
                out += "]";
                if ((c.n-i) != 1) out += (c.n-i);
                out += "+";
            } else{
                out += "B";
                if (i == 0){
                    if (c.n != 1) out += c.n;
                    out += "+";
                }
                else{
                    out += "(OH)";
                    if (c.n != 1) out += c.n;
                }
            }
        }
        out += ") = ";
        return(out);
    }
    void PrepareOutputs(TextView[] t, @NonNull Component c){
        Texts = t;
        for (int i=0; i<3*(c.n+1); i++){
            Texts[i].setVisibility(View.VISIBLE);
        }
        for (int i=c.n; i>-1; i--){
            Texts[(c.n-i)*3].setText(AssignConcentrations(i, c));
        }
    }
}