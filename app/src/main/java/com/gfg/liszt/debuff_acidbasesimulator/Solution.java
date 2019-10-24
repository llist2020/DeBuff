package com.gfg.liszt.debuff_acidbasesimulator;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.Switch;
import android.widget.TextView;

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
    comps.add(0, new Component(u, this));
    FillSlot(0);
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
    // privremeno nula
}

    void AddComp(@NotNull User u, boolean sw, int ind){
        if(FillSlot(ind)){
            comps.add(ind, new Component(u, this));
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
    int Slot(){ return(Entered.size()); }
    private boolean FillSlot(int i){
        if (Math.abs(i)<4){
            Entered.add(i);
            return(true);
        } else return(false);
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
        for (Component el: comps) {
            try {
                el.TitComp(v, l);
            } catch (Exception e) {
                // there is no Component at this index
            }
        }
        try {
            cn = (cn * V + l * v) / (V + v);
        } catch(Exception e){
            cn = cn * V / (V + v);
        }
        V += v;
    }

    // functions used to manipulate the provided data and generate
    // the building blocks of the polynomial
    @Contract(pure = true)
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

    SpannableStringBuilder MainFunction(@NonNull Poly p, TextView[] t){
        System.out.println("Initializing simulation.");
        // setting up the polynomial, beginning with the permanent part
        dic = new double[n+3];
        for (int i = 0; i<dic.length; i++) dic[i] = 0;
        dic[0] = kw;
        dic[1] = -cn;
        dic[2] = -1;

        // setting up the fractions describing the Components
        for (int i = 0; i<4; i++) {
            try {
                if (comps.get(i).acid){
                    comps.get(i).up = UpA(comps.get(i).cu, comps.get(i));
                    comps.get(i).down = DownA(1, comps.get(i));
                } else{
                    comps.get(i).up = UpB(comps.get(i).cu, comps.get(i));
                    comps.get(i).down = DownB(1, comps.get(i));
                }
                System.out.println("Component application successful.");
            } catch(Exception e){
                // there is no Component at this index
            }
        }

        // getting rid of the denominators
        for (int i = 0; i<4; i++) {
            try {
                dic = p.Multiply(dic, comps.get(i).down);
            }catch(Exception e){
                // there is no Component at this index
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
        for (int i = 0; i<4; i++){
            try{
                dic = p.Add(comps.get(i).up, dic);
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
        // the concentrations of the Component at index 0 are to be displayed
        comps.get(0).PrintConcentrations(t);
        SpannableStringBuilder out = new SpannableStringBuilder(String.valueOf((double)Math.round(h * 1000d) / 1000d));
        out.setSpan(new StyleSpan(Typeface.BOLD), 0, out.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return(out);
    }

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
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

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Solution> CREATOR = new Parcelable.Creator<Solution>() {
        public Solution createFromParcel(Parcel in) {
            return new Solution(in);
        }

        public Solution[] newArray(int size) {
            return new Solution[size];
        }
    };

    // constructor that takes a Parcel and gives you an object populated with it's values
    private Solution(Parcel in) {
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
