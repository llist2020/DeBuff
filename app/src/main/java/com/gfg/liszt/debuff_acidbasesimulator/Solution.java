package com.gfg.liszt.debuff_acidbasesimulator;

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
import com.github.mikephil.charting.data.LineDataSet;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * @author L. List
 */

public class Solution implements Parcelable {
    private List<Component> comps;
    private double[] dic;
    int n;
    private int TitInd;
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

    void AddComp(@NotNull User u, boolean sw, boolean initial){
        if (u.slot < 4  || (!initial && NoTitrantAssigned())){
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
                if (!initial){
                    // adding a titrant component
                    TitInd = ind;
                    comps.get(ind).ConvertToTitrant(u);
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
            for(int i=0; i<Entered.size(); i++){
                comps.get(i).setCn(cn);
            }
            if (u.ent){
                u.ion = "A";
            }
            u.SetAcid(!sw);
            comps.get(ind).ion = u.ion;
            comps.get(ind).titrant = !initial;
        }
    }

    // method returns the first free slot
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
    Component getComponentByBtnId(int id){
        return(comps.get(Entered.indexOf(User.RButt(id))));
    }
    Component getTitrant(){
        return(comps.get(TitInd));
    }
    int getTitrantSlot(){
        return(Entered.get(TitInd));
    }

    // updates the data describing the acid/base content - simulation of titration
    void Titrate(double v, Switch s, boolean custom){
        if (l != 0 && !custom) {
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
        if (custom) {
            comps.get(TitInd).cu += comps.get(TitInd).ul * v / (V + v);
            l = comps.get(TitInd).l;
        }
        // JEL OVO NUZNO TU TRY??
        try {
            cn = (cn * V + l * v) / (V + v);
        } catch(Exception e){
            cn = cn * V / (V + v);
        }
        // protection against errors induced by calculation
        if (Math.abs(cn)<Math.pow(10, -14)) cn = 0;
        V += v;
    }

    ArrayList<LineDataSet> GenerateTitrationGraphData(Switch s, double titV, int indicator, Context context, boolean custom){
        ArrayList<ArrayList<Entry>> lineEntries = new ArrayList<>();
        lineEntries.add(new ArrayList<Entry>());
        lineEntries.add(new ArrayList<Entry>());

        ArrayList<LineDataSet> out = new ArrayList<>();
        out.add(new LineDataSet(lineEntries.get(0), "pH - V [ml]"));
        out.add(new LineDataSet(lineEntries.get(1), null));

        double IndShift = AssignIndicator(out, indicator, context);

        double v = 0;
        MainFunction();
        int cache = h<IndShift ? 0 : 1;
        if (l != 0) {
            if (s.isChecked()) l = Math.abs(l);
            else l = -Math.abs(l);
        }
        if (n!=0) v = Math.abs(titV/(30*n));

        for (int i = 0; i<30*n; i++){
            Titrate(v, s, custom);
            MainFunction();
            if (cache != (h<IndShift ? 0 : 1)){
                Titrate(-99*v/100, s, custom);

                // generates points - stores in lineEntries
                for (int j = 0; j<98; j++){
                    MainFunction();
                    lineEntries.get(h<IndShift ? 0 : 1).add(new Entry(Float.parseFloat(String.valueOf((i-0.99+j/100.0)*v)), (float)h));
                    Titrate(v/100, s, custom);
                }
                MainFunction();
                lineEntries.get(0).add(new Entry(Float.parseFloat(String.valueOf(i*v)), (float)h));
                lineEntries.get(1).add(new Entry(Float.parseFloat(String.valueOf(i*v)), (float)h));
                Titrate(v/100, s, custom);
                MainFunction();
                lineEntries.get(cache==0 ? 1 : 0).add(new Entry(Float.parseFloat(String.valueOf(i*v)), (float)h));
            }
            cache = h<IndShift ? 0 : 1;
            lineEntries.get(cache).add(new Entry(Float.parseFloat(String.valueOf(i*v)), (float)h));
            lineEntries.get(cache).add(new Entry(Float.parseFloat(String.valueOf(i*v)), (float)h));
        }
        Titrate(-30*n*v, s, custom);

        out.get(0).setValues(lineEntries.get(0));
        out.get(1).setValues(lineEntries.get(1));

        return(out);
    }

    ArrayList<Entry> GenerateAutoTitrationGraphData(Switch s, boolean custom){
        ArrayList<Entry> lineEntries = new ArrayList<>();
        double cnCache = cn;
        double[] EqL = fetchEq();
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
            Titrate(v, s, custom);
            MainFunction();
            lineEntries.add(new Entry(Float.parseFloat(String.valueOf(i*v)), (float)h));
        }
        Titrate(-30*n*v, s, custom);
        cn = cnCache;
        return(lineEntries);
    }

    // Shows equivalence points in the titration diagram
    void GenerateEquivalencePtsTags(@NotNull XAxis x, Context context){
        x.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines

        double EqPtVol0, EqPtVol;
        boolean OverTitrated;
        int CurrEqPt;
        String s, tag;
        LimitLine ll1;
        ArrayList<Integer> cl = new ArrayList<>();
        cl.add(R.color.kulboja);
        cl.add(R.color.plav);
        cl.add(R.color.dabadeedabadaa);
        cl.add(R.color.pale_green);
        cl.add(R.color.rot);

        for (Component C: comps){
            tag = (C.acid) ? "a" : "b";

            if (Math.abs(cn)>Math.pow(10, -15)) CurrEqPt = (int) Math.floor(cn / C.cu) * (l<Math.pow(10, -15) ? -1 : 1);
            else CurrEqPt = 0;

            OverTitrated = CurrEqPt > C.n;
            if (!OverTitrated){
                EqPtVol0 = (C.cu * (l<Math.pow(10, -15) ? -1 : 1) - cn) * V / l;
                EqPtVol = Math.abs(C.cu*V/l);

                for (int i = CurrEqPt; i<C.n+1; i++){
                    if (i>0){
                        s = "";
                        if (i != C.n) {
                            s += (("(pK") + tag + i + ("+") + ("pK") + tag + (i + 1) + (")/2"));
                        }
                        ll1 = new LimitLine((float) (EqPtVol0 + EqPtVol * (i-CurrEqPt-1)), s);

                        ll1.setLineColor(context.getResources().getColor(cl.get(comps.indexOf(C))));
                        ll1.setLineWidth(4f);
                        ll1.enableDashedLine(10f, 10f, 0f);
                        if (i % 2 == 0) {
                            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
                        } else {
                            ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
                        }

                        ll1.setTextSize(10f);
                        ll1.setTypeface(Typeface.create(Typeface.SERIF, Typeface.NORMAL));
                        ll1.setLineColor(cl.get(comps.indexOf(C)));

                        x.addLimitLine(ll1);
                    }
                }
            }
        }
    }

    // an aiding method in setting the diagram properties
    double[] fetchEq(){
        double[] fin = new double[] {0, 0};
        for (Component comp: comps){
            int a = comp.acid ? 0 : 1;
            fin[a] += Math.pow(-1, a)*comp.n*comp.cu;
        }
        return(fin);
    }

    // showing indicator colors in the diagram
    private static double AssignIndicator(ArrayList<LineDataSet> DataSets, int IndicatorCode, Context context){
        switch (IndicatorCode){
            case 0: // phenolphthalein
                DataSets.get(0).setFillColor(ContextCompat.getColor(context, R.color.transparent));
                DataSets.get(1).setFillColor(ContextCompat.getColor(context, R.color.phenolphtalein));
                DataSets.get(0).setFillAlpha(100);
                DataSets.get(1).setFillAlpha(25);
                return(8.2);
            case 1: // methyl orange
                DataSets.get(0).setFillColor(ContextCompat.getColor(context, R.color.f1));
                DataSets.get(1).setFillColor(ContextCompat.getColor(context, R.color.f3));
                DataSets.get(0).setFillAlpha(25);
                DataSets.get(1).setFillAlpha(25);
                return(3.5);
            default: // no indicator
                DataSets.get(0).setFillColor(ContextCompat.getColor(context, R.color.colorPrimary));
                DataSets.get(1).setFillColor(ContextCompat.getColor(context, R.color.colorPrimary));
                DataSets.get(0).setFillAlpha(25);
                DataSets.get(1).setFillAlpha(25);
                return(100);
        }
    }

    @Contract(pure = true)
    boolean NoTitrantAssigned(){
        for (Component c: comps){
            if (c.titrant) return(false);
        }
        return(true);
    }

    void UpdateTitrant(double new_l, double new_ul, double new_vl){
        comps.get(TitInd).l =  new_l;
        comps.get(TitInd).ul =  new_ul;
        comps.get(TitInd).vl =  new_vl;
    }

    // methods used to manipulate the provided data and generate
    //  the building blocks of the polynomial
    @Contract(pure = true)
    private static double ConstantsA(int i, @NonNull Component c){
        double out = 1;
        for (int j=0; j<c.n-i+1; j++){
            out *= c.K[j];
        }
        return(out);
    }
    @Contract(pure = true)
    static double ConstantsB(int i, @NonNull Component c){
        double out = 1;
        for (int j=0; j<(i+1); j++){
            out *= c.K[j];
        }
        return(out);
    }
    @Contract(pure = true)
    private static void setUpA(@NonNull Component c){
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
    private static void setDownA(@NonNull Component c){
        double[] out = new double[c.n+1];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<(c.n+1); i++){
            out[c.n-i] += ConstantsB(i, c);
        }
        c.down = out;
    }
    private static void setUpB(@NonNull Component c){
        double[] out = new double[c.n+2];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=1; i<(c.n+1); i++){
            out[i+1] -= i * c.cu * (ConstantsB(i, c)) / Math.pow(c.kw, i);
        }
        c.up = out;
    }
    private static void setDownB(@NonNull Component c){
        double[] out = new double[c.n+1];
        for (int i = 0; i<out.length; i++){
            out[i] = 0;
        }
        for (int i=0; i<(c.n+1); i++){
            out[i] += ConstantsB(i, c) / Math.pow(c.kw, i);
        }
        c.down = out;
    }

    SpannableStringBuilder MainFunction(){
        Poly p;

        // setting up the polynomial, beginning with the permanent part
        dic = new double[n+3];
        for (int i = 0; i<dic.length; i++) dic[i] = 0;
        dic[0] = kw;
        dic[1] = -cn;
        dic[2] = -1;

        // setting up the fractions describing the Components
        for (Component C: comps) {
            if (C.acid){
                setUpA(C);
                setDownA(C);
            } else{
                setUpB(C);
                setDownB(C);
            }
        }

        // getting rid of the denominators
        for (Component C: comps) {
            dic = Poly.Multiply(dic, C.down);
        }
        for (int i = 0; i<comps.size(); i++){
            for (int j = 0; j<comps.size(); j++){
                if (i != j){
                    comps.get(i).up = Poly.Multiply(comps.get(i).up, comps.get(j).down);
                }
            }
        }

        // generating the final polynomial
        for (Component C: comps){
            dic = Poly.Add(C.up, dic);
        }
        System.out.println(465);
        for (double el: dic) System.out.println(el);

        // the freshly generated polynomial is being transposed to the Poly format
        //  in order to find the positive root that satisfies the laws of conservation
        p = new Poly(dic);
        try {
            h = -Math.log10(p.Solve(this));
        } catch(Exception e){
            h = -100;
            System.out.println(e.getMessage());
        }

        // finally, the pH is returned
        SpannableStringBuilder out = new SpannableStringBuilder(String.valueOf((double)Math.round(h * 1000d) / 1000d));
        out.setSpan(new StyleSpan(Typeface.BOLD), 0, out.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return(out);
    }

    // The code for passing Solution instances' data between the activities
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeList(comps);
        out.writeDoubleArray(dic);
        out.writeInt(n);
        out.writeInt(TitInd);
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
        TitInd = in.readInt();
        cu = in.readDouble();
        l = in.readDouble();
        V = in.readDouble();
        cn = in.readDouble();
        Entered = new ArrayList<>();
        in.readList(Entered, Integer.class.getClassLoader());
    }
}
