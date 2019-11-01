package com.gfg.liszt.debuff_acidbasesimulator;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.Switch;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * @author L. List
 */

public class Solution implements Parcelable {
    private List<Component> comps;
    private double[] dic;
    int n;
    final double kw=Math.pow(10, -14);
    double cu = 0, l, V;
    private double cn = 0, h;
    List<Integer> Entered = new ArrayList<>();

    Solution(){
        n=0;
    }
    Solution(@NotNull User u, boolean sw){
    comps = new ArrayList<>();
    comps.add(new Component(u));
    FillSlot(u.slot);
    cu = u.cu;
    cn = u.cn;
    n = u.n;
    dic = new double[n+3];
    V = u.V;
    h = cu*n+1;
    comps.get(0).acid = !sw;
    if (u.ent){
        u.ion = "A";
        u.SetAcid(!sw);
    }
    comps.get(0).ion = u.ion;
}

    void AddComp(@NotNull User u, boolean sw){
        if (u.slot<4){
            int ind = 0;
            try{
                if (!Entered.contains(u.slot)){
                    // adding a new component
                    comps.add(new Component(u));
                    ind = comps.size()-1;
                    FillSlot(u.slot);
                } else{
                    // overwriting the component
                    cn -= comps.get(Entered.indexOf(u.slot)).GetCn();
                    comps.set(Entered.indexOf(u.slot), new Component(u));
                    ind = Entered.indexOf(u.slot);
                }
            } catch(Exception e){
                // error
                System.out.println(e.getMessage());
            }
            h += u.cu*u.n+1;
            n += u.n;
            cu += u.cu;
            cn += u.cn;
            dic = new double[n+3];
            comps.get(ind).V = V;
            comps.get(ind).acid = !sw;
            for(int i=0; i<Slot(); i++){
                comps.get(i).SetCn(cn);
            }
            if (u.ent){
                u.ion = "A";
            }
            u.SetAcid(!sw);
            comps.get(ind).ion = u.ion;
        }
    }

    // inputs entering another slot
    int Slot(){
        for (int i = 0; i<4; i++) if (!Entered.contains(i)) return(i);
        return(4);
    }
    private void FillSlot(int i){
        Entered.add(i);
    }

    List<Component> GetComps(){
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
        for (Component C: comps) {
            try {
                C.TitComp(v);
            } catch (Exception e) {
                // component error
            }
        }
        try {
            cn = (cn * V + l * v) / (V + v);
        } catch(Exception e){
            cn = cn * V / (V + v);
            System.out.println(e.getMessage());
        }
        V += v;
        System.out.println("Solution constitution updated successfully.");
    }

    // functions used to manipulate the provided data and generate
    // the building blocks of the polynomial
    @Contract(pure = true)
    private double ConstantsA(int i, @NonNull Component c){
        double out = 1;
        for (int j=0; j<c.n-i+1; j++){
            out *= c.K[j];
        }
        return(out);
    }
    @Contract(pure = true)
    double ConstantsB(int i, @NonNull Component c){
        double out = 1;
        for (int j=0; j<(i+1); j++){
            out *= c.K[j];
        }
        return(out);
    }
    @Contract(pure = true)
    private void SetUpA(@NonNull Component c){
        double[] out = new double[c.n+2];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<c.n ; i++){
            out[i+1] += c.cu * (c.n-i) * (ConstantsA(i, c));
        }
        c.up = out;
    }
    @Contract(pure = true)
    private void SetDownA(@NonNull Component c){
        double[] out = new double[c.n+1];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<(c.n+1); i++){
            out[c.n-i] += ConstantsB(i, c);
        }
        c.down = out;
    }
    private void SetUpB(@NonNull Component c){
        double[] out = new double[c.n+2];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=1; i<(c.n+1); i++){
            out[i+1] -= i * c.cu * (ConstantsB(i, c)) / Math.pow(c.kw, i);
        }
        c.up = out;
    }
    private void SetDownB(@NonNull Component c){
        double[] out = new double[c.n+1];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<(c.n+1); i++){
            out[i] += ConstantsB(i, c) / Math.pow(c.kw, i);
        }
        c.down = out;
    }

    SpannableStringBuilder MainFunction(@NonNull Poly p){
        System.out.println("Initializing simulation.");

        // setting up the polynomial, beginning with the permanent part
        dic = new double[n+3];
        for (int i = 0; i<dic.length; i++) dic[i] = 0;
        dic[0] = kw;
        dic[1] = -cn;
        dic[2] = -1;

        // setting up the fractions describing the Components
        for (Component C: comps) {
            try {
                if (C.acid){
                    SetUpA(C);
                    SetDownA(C);
                } else{
                    SetUpB(C);
                    SetDownB(C);
                }
                System.out.println("Component application successful.");
            } catch(Exception e){
                // component error
            }
        }

        // getting rid of the denominators
        for (Component C: comps) {
            try {
                dic = p.Multiply(dic, C.down);
            }catch(Exception e){
                // component error
            }
        }
        for (int i = 0; i<4; i++){
            for (int j = 0; j<4; j++){
                if (i != j){
                    try {
                        comps.get(i).up = p.Multiply(comps.get(i).up, comps.get(j).down);
                    }catch(Exception e){
                        // there is no Component at this index
                    }
                }
            }
        }

        // generating the final polynomial
        for (Component C: comps){
            try{
                dic = p.Add(C.up, dic);
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
        // the concentrations of the Component at comps index 0 are to be displayed
        SpannableStringBuilder out = new SpannableStringBuilder(String.valueOf((double)Math.round(h * 1000d) / 1000d));
        out.setSpan(new StyleSpan(Typeface.BOLD), 0, out.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return(out);
    }

    // The code for passing Solution objects' data between activities
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeList(comps);
        out.writeDoubleArray(dic);
        out.writeInt(n);
        out.writeDouble(cu);
        out.writeDouble(l);
        out.writeDouble(V);
        out.writeDouble(cn);
        out.writeList(Entered);
    }

    public static final Parcelable.Creator<Solution> CREATOR = new Parcelable.Creator<Solution>() {
        public Solution createFromParcel(Parcel in) {
            return new Solution(in);
        }

        public Solution[] newArray(int size) {
            return new Solution[size];
        }
    };

    private Solution(@NotNull Parcel in) {
        comps = new ArrayList<>();
        in.readList(comps, Component.class.getClassLoader());
        dic = in.createDoubleArray();
        n = in.readInt();
        cu = in.readDouble();
        l = in.readDouble();
        V = in.readDouble();
        cn = in.readDouble();
        Entered = new ArrayList<>();
        in.readList(Entered, Integer.class.getClassLoader());
    }
}
