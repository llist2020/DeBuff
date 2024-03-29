package com.gfg.liszt.debuff_acidbasesimulator;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.view.View;
import android.widget.TextView;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author L. List
 */

class User {
    double cu, cn;
    int n, itt;
    double[] K;
    boolean ent, acid;
    String a;
    String ion = "A";
    double V = 10;
    int[] Valid;
    int slot, indicator = 2;

    User(int number, double OvC, double CatC, double Vol, String IonName, int Slot){
        n = number;
        cu = OvC;
        cn = CatC;
        K = new double[n+1];
        K[0] = 1;
        V = Vol;
        ResetValid();
        ent = true;
        if (!IonName.matches("Mjau")) ion = IonName;
        //SetAcid(true);
        slot = Slot;
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
        ResetValid();
        ent = u1.ent;
        if (!ent) ion = u1.ion;
        SetAcid(u1.acid);
        slot = u1.slot;
    }
    User (int a, int Slot){
        SetAcid(a<14);
        ent = false;
        List<Double> k = new ArrayList<>();
        k.add(1.0);
        switch(a){
            case 0:
                ent = true;
                break;
            case 1:
                n = 1;
                k.add(Math.pow(10, 3));
                ion = "Cl";
                break;
            case 2:
                n = 1;
                k.add(Math.pow(10, -3.17));
                ion = "F";
                break;
            case 3:
                n = 1;
                k.add(Math.pow(10, -9.21));
                ion = "CN";
                break;
            case 4:
                n = 1;
                k.add(Math.pow(10, -4.756));
                ion = "AcO";
                break;
            case 5:
                n = 2;
                k.add(Math.pow(10, -7.04));
                k.add(Math.pow(10, -11.96));
                ion = "S";
                break;
            case 6:
                n = 2;
                k.add(Math.pow(10, -6.3));
                k.add(Math.pow(10, -10.32));
                ion = "CO3";
                break;
            case 7:
                n = 2;
                k.add(Math.pow(10, 9));
                k.add(Math.pow(10, -1.96));
                ion = "SO4";
                break;
            case 8:
                n = 2;
                k.add(Math.pow(10, -1.2676));
                k.add(Math.pow(10, -4.2676));
                ion = "C2O4";
                break;
            case 9:
                n = 3;
                k.add(Math.pow(10, -2.124));
                k.add(Math.pow(10, -7.2));
                k.add(Math.pow(10, -11.89));
                ion = "PO4";
                break;
            case 10:
                n = 3;
                k.add(Math.pow(10, -2.19));
                k.add(Math.pow(10, -6.94));
                k.add(Math.pow(10, -11.5));
                ion = "AsO4";
                break;
            case 11:
                n = 3;
                k.add(Math.pow(10, -9.24));
                k.add(Math.pow(10, -12.4));
                k.add(Math.pow(10, -13.3));
                ion = "BO3";
                break;
            case 12:
                n = 3;
                k.add(Math.pow(10, -3.13));
                k.add(Math.pow(10, -4.76));
                k.add(Math.pow(10, -6.395));
                ion = "C6H5O7";
                break;
            case 13:
                n = 4;
                k.add(Math.pow(10, -1.99));
                k.add(Math.pow(10, -2.67));
                k.add(Math.pow(10, -6.16));
                k.add(Math.pow(10, -10.26));
                ion = "EDTA";
                break;
            case 14:
                n = 1;
                k.add(Math.pow(10, -4.75));
                ion = "NH4";
                break;
            case 15:
                n = 1;
                k.add(Math.pow(10, -3.36));
                ion = "CH3NH3";
                break;
        }
        K = new double[n+1];
        for (int i = 0; i<K.length; i++) K[i] = k.get(i);
        itt=0;
        slot = Slot;
        ResetValid();
    }

    // setting the type of the Component
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

    // returns true if all inputs are valid - in the right format and make sense
    boolean AllInputsValid(ArrayList<Integer> IgnList){
        for (int t = 0; t<Valid.length; t++) if ((Valid[t] == 0) && !IgnList.contains(t)) return (false);
        return(true);
    }

    private void ResetValid(){
         Valid = new int[] {0, 0, 0, 0};
    }

    // define species' tags and display the right number of fields
    //  (required for the concentration printout)
    @Contract(pure = true)
    static SpannableStringBuilder AssignConcentrations(int i, @NonNull Component c, boolean span){
        int sups = 0, supe = 0;
        int subs = 0, sube = 0;
        String s = "";
        SpannableStringBuilder outIon = new SpannableStringBuilder(c.ion);
        SpannableStringBuilder out;
        for (int j = 0; j<c.ion.length(); j++){
            try{
                Integer.parseInt(String.valueOf(c.ion.charAt(j)));
                outIon.setSpan(new SubscriptSpan(), j, j+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                outIon.setSpan(new RelativeSizeSpan(0.8f), j, j+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } catch(Exception e){
                // not a digit
            }
        }
        if (c.acid) {
            if (!span && (c.n != 1) && (i < c.n)) s += "[";
            if (i != 0) {
                s += "H";
                if (i != 1) {
                    subs = s.length();
                    s += i;
                    sube = s.length();
                }
            }
            out = new SpannableStringBuilder(s);
            s = "";
            out.append(outIon);
            if (i != c.n) {
                if (!span && (c.n != 1)) s += "]";
                sups = s.length() + out.length();
                if (i != c.n - 1) {
                    s += (c.n - i);
                }
                s += "-";
                supe = s.length() + out.length();
            }
        } else{
            if (i != 0 && i != c.n){
                if (!span) s += "[";
                out = new SpannableStringBuilder(s);
                s = "";
                out.append(outIon);
                if (i != 1){
                    s += "(OH)"+i;
                    subs = s.length() - 1 + out.length();
                    sube = s.length() + out.length();
                }
                else s += "(OH)";
                if (!span) s += "]";
                sups = s.length() + out.length();
                if (i != c.n - 1) s += (c.n-i);
                s += "+";
                supe = s.length() + out.length();
            } else{
                out = new SpannableStringBuilder(s);
                s = "";
                out.append(outIon);
                if (i == 0){
                    sups = s.length() + out.length();
                    if (c.n != 1) s += c.n;
                    s += "+";
                    supe = s.length() + out.length();
                }
                else{
                    s += "(OH)";
                    if (c.n != 1){
                        subs = s.length() + out.length();
                        s += c.n;
                        sube = s.length() + out.length();
                    }
                }
            }
        }
        out.append(s);
        try{
            out.setSpan(new SuperscriptSpan(), sups, supe, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            out.setSpan(new RelativeSizeSpan(0.8f), sups, supe, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch(Exception e){
            // no supers
        }
        try{
            out.setSpan(new SubscriptSpan(), subs, sube, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            out.setSpan(new RelativeSizeSpan(0.8f), subs, sube, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch(Exception e){
            // no subs
        }
        return(out);
    }

    // sets up the results' declarations
    static void PrepareOutputs(TextView[] t, @NonNull Component c){
        for (int i=0; i<3*(c.n+1); i++){
            t[i].setVisibility(View.VISIBLE);
        }
        for (int i=c.n; i>-1; i--){
            SpannableStringBuilder out = new SpannableStringBuilder("c(");
            out.append(AssignConcentrations(i, c, true));
            out.append(") = ");
            out.setSpan(new StyleSpan(Typeface.ITALIC), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            t[(c.n-i)*3].setText(out, TextView.BufferType.SPANNABLE);
        }
    }

    // returns the slot number by the corresponding radio button's id
    @Contract(pure = true)
    static int RButt(int id) {
        switch (id) {
            case R.id.rBtn0:
                return (0);
            case R.id.rBtn1:
                return (1);
            case R.id.rBtn2:
                return (2);
            case R.id.rBtn3:
                return (3);
            default:
                return(5555);
        }
    }
}