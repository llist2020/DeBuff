package com.gfg.liszt.debuff_acidbasesimulator;

import android.graphics.Color;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.widget.Switch;

import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;

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
                    cn -= comps.get(Entered.indexOf(u.slot)).getCn();
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

    List<Component> getComps(){
        return(comps);
    }
    public double getCn(){
        return(cn);
    }
    double[] getDic(){
        return(dic);
    }
    Component getComponentByBtnId(int id){
        User u2 = new User(0,0);
        return(comps.get(Entered.indexOf(u2.RButt(id))));
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
    }

    ArrayList<Entry> GenerateTitrationGraphData(Switch s, double titV){
        ArrayList<Entry> lineEntries = new ArrayList<>();
        Poly p2;
        if (l != 0) {
            if (s.isChecked()) {
                // titrirano bazom

                l = Math.abs(l);
            } else {
                // titrirano kiselinom

                l = -Math.abs(l);
            }
        }
        double v = Math.abs(titV/(30*n));

        for (int i = 0; i<30*n; i++){
            Titrate(v, s);
            p2 = new Poly(getDic());
            MainFunction(p2);
            lineEntries.add(new Entry(Float.parseFloat(String.valueOf(i*v)), (float)h));
        }
        Titrate(-30*n*v, s);
        return(lineEntries);
    }

    ArrayList<Entry> GenerateAutoTitrationGraphData(Switch s){
        ArrayList<Entry> lineEntries = new ArrayList<>();
        double cnCache = cn;
        Poly p2;
        double[] EqL = GetEq();
        double Eq = EqL[0] - EqL[1];
        if (l != 0) {
            if (s.isChecked()) {
                // titrirano bazom

                cn = EqL[1];
                l = Math.abs(l);
            } else {
                // titrirano kiselinom

                cn = EqL[0];
                l = -Math.abs(l);
            }
        }
        double v = Math.abs(V*Eq/l/(30*(n-0.5)));

        for (int i = 0; i<30*n; i++){
            Titrate(v, s);
            p2 = new Poly(getDic());
            MainFunction(p2);
            lineEntries.add(new Entry(Float.parseFloat(String.valueOf(i*v)), (float)h));
        }
        Titrate(-30*n*v, s);
        cn = cnCache;
        return(lineEntries);
    }

    void GenerateEquivalencePtsTags(@NotNull XAxis x, Context context){
        x.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(R.color.plav);
        colors.add(R.color.kulboja);
        colors.add(R.color.rot);
        colors.add(R.color.pale_green);
        colors.add(R.color.dabadeedabadaa);

        double EqPtVol0;
        boolean OverTitrated;
        int a;
        String s;
        LimitLine ll1;
        ArrayList<Integer> cl = new ArrayList<>();
        cl.add(R.color.f0);
        cl.add(R.color.f1);
        cl.add(R.color.f2);
        cl.add(R.color.f3);
        cl.add(R.color.f4);

        for (Component C: comps){
            a = C.acid ? -1 : 1;
            OverTitrated = a*cn + C.cu*C.n < 0;
            if (!OverTitrated){
                EqPtVol0 = a*(-C.cu*V+cn*V)/l;
                double EqPtVol = Math.abs(C.cu*V/l);
                for (int i = 0; i<C.n; i++){
                    s = "";
                    if (i != C.n - 1) {
                        s+=(("(pKa")+(i + 1)+("+")+("pKa")+(i + 2)+(")/2"));
                    }
                    if (i == 0 && Math.abs(EqPtVol0)>0){
                        ll1 = new LimitLine((float)EqPtVol0, s);
                    } else if (Math.abs(cn)>0) {
                        ll1 = new LimitLine((float)(i*EqPtVol+EqPtVol0), s);
                    } else continue;
                    ll1.setLineColor(context.getResources().getColor(cl.get(comps.indexOf(C))));
                    ll1.setLineWidth(4f);
                    ll1.enableDashedLine(10f, 10f, 0f);
                    if (i%2==0){
                        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                    } else{
                        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                    }
                    ll1.setTextSize(10f);
                    ll1.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
                    ll1.setLineColor(colors.get(comps.indexOf(C)));

                    x.addLimitLine(ll1);
                }
            }
        }
    }

    double[] GetEq(){
        double[] fin = new double[] {0, 0};
        for (Component comp: comps){
            int a = comp.acid ? 0 : 1;
            fin[a] += Math.pow(-1, a)*comp.n*comp.cu;
        }
        return(fin);
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
    private void setUpA(@NonNull Component c){
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
    private void setDownA(@NonNull Component c){
        double[] out = new double[c.n+1];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<(c.n+1); i++){
            out[c.n-i] += ConstantsB(i, c);
        }
        c.down = out;
    }
    private void setUpB(@NonNull Component c){
        double[] out = new double[c.n+2];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=1; i<(c.n+1); i++){
            out[i+1] -= i * c.cu * (ConstantsB(i, c)) / Math.pow(c.kw, i);
        }
        c.up = out;
    }
    private void setDownB(@NonNull Component c){
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
                    setUpA(C);
                    setDownA(C);
                } else{
                    setUpB(C);
                    setDownB(C);
                }
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
        out.setSpan(new StyleSpan(Typeface.BOLD), 0,
                out.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
