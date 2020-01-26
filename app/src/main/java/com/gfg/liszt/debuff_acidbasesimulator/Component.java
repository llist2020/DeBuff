package com.gfg.liszt.debuff_acidbasesimulator;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SuperscriptSpan;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

/**
 * @author L. List
 */

public class Component extends Solution implements Parcelable {
    boolean acid, titrant;
    int n;
    double[] K;
    double V, cu, l, ul, vl;
    private double cn;
    private double[] Concentrations;
    double[] up, down;
    String ion;

    Component(@NotNull User u) {
        super();
        cu = u.cu;
        cn = u.cn;
        n = u.n;
        K = u.K;
        V = u.V;
        titrant = false;
    }

    void ConvertToTitrant(){
        vl = V;
        ul = cu;
        l = cn;
        cu = 0;
        cn = 0;
    }

    public double getCn(){
        return(cn);
    }
    double[] GetConcentrations(){
        return(Concentrations);
    }
    void SetCn(double c) {
        cn = c;
    }
    void SetConcentrations(double[] c){
        Concentrations = c;
    }

    // pulling the new Solution constitution
    void TitComp(double v){
        cu = cu*V/(V+v);
        cn = cn * V/ (V + v);
        V += v;
    }

    void SetTitrantConcentration(double new_l){
        l *= new_l/ul;
        ul = new_l;
    }

    // a function setting the viewable concentration values
    void PrintConcentrations(TextView[] Texts, Double Cn){
        DecimalFormat format = new DecimalFormat("0.###E0");
        for (int i=n; i>-1; i--){
            Texts[(n-i)*3+1].setText(String.valueOf(format.format(Concentrations[n-i])));
        }
        Texts[22].setText(String.valueOf(format.format(Math.abs(Cn))));
        SpannableStringBuilder out;
        if (Cn<0){
            out = new SpannableStringBuilder("c(X-) = ");
        } else{
            out = new SpannableStringBuilder("c(X+) = ");
        }

        try{
            out.setSpan(new SuperscriptSpan(), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            out.setSpan(new RelativeSizeSpan(0.75f), 3, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            out.setSpan(new StyleSpan(Typeface.ITALIC), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch(Exception e){
            // no supers / other error
        }
        Texts[21].setText(out);
        Texts[23].setText(String.valueOf((double)Math.round(V * 100.00d) / 100.00d));
    }

    // 99.9% of the time you can just ignore this
    @Override
    public int describeContents() {
        return 0;
    }

    // write your object's data to the passed-in Parcel
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(acid ? 1 : 0);
        out.writeInt(titrant ? 1 : 0);
        out.writeInt(n);
        out.writeDoubleArray(K);
        out.writeDouble(V);
        out.writeDouble(cu);
        out.writeDouble(cn);
        out.writeDouble(l);
        out.writeDouble(ul);
        out.writeDouble(vl);
        out.writeDoubleArray(Concentrations);
        out.writeDoubleArray(up);
        out.writeDoubleArray(down);
        out.writeString(ion);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Component> CREATOR = new Parcelable.Creator<Component>() {
        public Component createFromParcel(Parcel in) {
            return new Component(in);
        }

        public Component[] newArray(int size) {
            return new Component[size];
        }
    };

    // constructor that takes a Parcel and gives you an object populated with it's values
    private Component(@NotNull Parcel in) {
        acid = in.readInt() == 1;
        titrant = in.readInt() == 1;
        n = in.readInt();
        K = in.createDoubleArray();
        V = in.readDouble();
        cu = in.readDouble();
        cn = in.readDouble();
        l = in.readDouble();
        ul = in.readDouble();
        vl = in.readDouble();
        Concentrations = in.createDoubleArray();
        up = in.createDoubleArray();
        down = in.createDoubleArray();
        ion = in.readString();
    }
}
