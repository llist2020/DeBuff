package com.gfg.liszt.debuff_acidbasesimulator;

import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.Contract;

import androidx.annotation.NonNull;

/**
 * @author L. List
 */

class User {
    TextView[] Texts;
    double cu, cn;
    int n, itt, citt = 0;
    double[] K;
    boolean ent, acid;
    String a;
    String ion;
    double V = 10;
    int[] Valid = new int[] {0, 0, 0, 0};

    User(int number, double OvC, double CatC, double Vol, String IonName, boolean Manual){
        n = number;
        cu = OvC;
        cn = CatC;
        K = new double[n+1];
        K[0] = 1;
        V = Vol;
        ent = Manual;
        if (ent) ion = "A";
        else ion = IonName;
        SetAcid(true);
    }
    User(int in, double icu, double icn, User u1){
        cu = icu;
        cn = icn;
        n = in;
        K = new double[n+1];
        System.arraycopy(u1.K, 0, K, 0, n+1);
        K[0] = 1;
        V = u1.V;
        itt = u1.itt;
        citt = u1.citt;
        ent = u1.ent;
        if (ent) ion = "A";
        else ion = u1.ion;
        SetAcid(true);
    }
    User (int a, TextView[] Nvws){
        ion = "A";
        SetAcid(true);
        ent = true;
        switch(a){
            case 0:
                K = new double[] {1};
                ent = false;
                break;
            case 1:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, 3);
                ion = "Cl";
                break;
            case 2:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -3.17);
                ion = "F";
                break;
            case 3:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -9.21);
                ion = "CN";
                break;
            case 4:
                n = 1;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -4.756);
                ion = "CH3COO";
                break;
            case 5:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -7.04);
                K[2] = Math.pow(10, -11.96);
                ion = "S";
                break;
            case 6:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -6.3);
                K[2] = Math.pow(10, -10.32);
                ion = "CO3";
                break;
            case 7:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, 9);
                K[2] = Math.pow(10, -1.96);
                ion = "SO4";
                break;
            case 8:
                n = 2;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -1.2676);
                K[2] = Math.pow(10, -4.2676);
                ion = "C2O4";
                break;
            case 9:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -2.124);
                K[2] = Math.pow(10, -7.2);
                K[3] = Math.pow(10, -11.89);
                ion = "PO4";
                break;
            case 10:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -2.19);
                K[2] = Math.pow(10, -6.94);
                K[3] = Math.pow(10, -11.5);
                ion = "AsO4";
                break;
            case 11:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -9.24);
                K[2] = Math.pow(10, -12.4);
                K[3] = Math.pow(10, -13.3);
                ion = "BO3";
                break;
            case 12:
                n = 3;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -3.13);
                K[2] = Math.pow(10, -4.76);
                K[3] = Math.pow(10, -6.395);
                ion = "C6H5O7";
                break;
            case 13:
                n = 4;
                K = new double[n+1];
                K[0] = 1;
                K[1] = Math.pow(10, -1.99);
                K[2] = Math.pow(10, -2.67);
                K[3] = Math.pow(10, -6.16);
                K[4] = Math.pow(10, -10.26);
                ion = "EDTA";
                break;
        }
        ent = !ent;
        Texts = Nvws;
        itt=0;
    }

    // setting the type of the Component which's data is being collected
    void SetAcid(boolean i){
        acid = i;
        if (acid){
            a = "acid";
            if (ion.equals("A") || ion.equals("B")) ion = "A";
        } else{
            a = "base";
            if (ion.equals("A") || ion.equals("B")) ion = "B";
        }
    }

    // a simple function used at data collection
    boolean AllInputsValid(){
        for (int el: Valid) if (el == 0) return (false);
        return(true);
    }

    // defines species' tags and displaying the right number of fields
    // needed for the concentration printout
    @Contract(pure = true)
    private String AssignConcentrations(int i, @NonNull Component c){
        String out = "c(";
        if (c.acid) {
            if (i < c.n && c.n != 1) out += "[";
            if (i != 0) {
                out += "H";
                if (i != 1) {
                    out += i;
                }
            }
            out += c.ion;
            if (i != c.n) {
                if (c.n != 1) out += "]";
                if (i != c.n - 1) {
                    out += (c.n - i);
                }
                out += "-";
            }
        } else{
            if (i != 0 && i != c.n){
                out += "["+c.ion;
                if (i != 1) out += "(OH)"+i;
                else out += "(OH)";
                out += "]";
                if (i != c.n - 1) out += (c.n-i);
                out += "+";
            } else{
                out += c.ion;
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