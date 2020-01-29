package com.gfg.liszt.debuff_acidbasesimulator;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private User u1;
    private Poly p1;
    private Solution s1;
    private TextView[] ConcentrationTextViews;
    private EditText TitTxt, VolTitTxt;
    private TextView pHVw, SelectedVw;
    private Button TitBtn, GraphBtn, EditBtn;
    private ToggleButton CustomTitBtn;
    private RadioGroup rBtnGrp;
    private Switch AcidBaseSw;
    private ArrayList<BarEntry> Entries, oldEntries;
    private BarChart barChart;
    private XAxis bChartX;
    private Dialog GraphShow;
    private AlertDialog dialogInd;
    private String[] species;

    private final static int REQUEST_CODE_Manual = 1;
    private final static int REQUEST_CODE_Auto = 2;
    private final static int REQUEST_CODE_Titrant = 3;
    private final static int REQUEST_CODE_Titrant_edit = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        TitTxt = findViewById(R.id.TitTxt);
        VolTitTxt = findViewById(R.id.VolTitTxt);
        TitBtn = findViewById(R.id.TitBtn);
        CustomTitBtn = findViewById(R.id.CustomTitBtn);
        GraphBtn = findViewById(R.id.GraphBtn);
        EditBtn = findViewById(R.id.EditBtn);
        rBtnGrp = findViewById(R.id.rBtnGrp);
        AcidBaseSw = findViewById(R.id.AcidBaseSw);
        pHVw = findViewById(R.id.pHVw);
        barChart = findViewById(R.id.ConcentrationChart);
        SelectedVw = findViewById(R.id.SelectedView);
        final TextView cVwx = findViewById(R.id.cVwx);
        final TextView cTxtX = findViewById(R.id.cTxtX);
        final TextView solutionVol = findViewById(R.id.solutionVol);
        final TextView cVw1 = findViewById(R.id.cVw1);
        final TextView cVw2 = findViewById(R.id.cVw2);
        final TextView cVw3 = findViewById(R.id.cVw3);
        final TextView cVw4 = findViewById(R.id.cVw4);
        final TextView cVw5 = findViewById(R.id.cVw5);
        final TextView cVw6 = findViewById(R.id.cVw6);
        final TextView cVw7 = findViewById(R.id.cVw7);
        final TextView cTxt1 = findViewById(R.id.cTxt1);
        final TextView cTxt2 = findViewById(R.id.cTxt2);
        final TextView cTxt3 = findViewById(R.id.cTxt3);
        final TextView cTxt4 = findViewById(R.id.cTxt4);
        final TextView cTxt5 = findViewById(R.id.cTxt5);
        final TextView cTxt6 = findViewById(R.id.cTxt6);
        final TextView cTxt7 = findViewById(R.id.cTxt7);
        final TextView m1 = findViewById(R.id.m1);
        final TextView m2 = findViewById(R.id.m2);
        final TextView m3 = findViewById(R.id.m3);
        final TextView m4 = findViewById(R.id.m4);
        final TextView m5 = findViewById(R.id.m5);
        final TextView m6 = findViewById(R.id.m6);
        final TextView m7 = findViewById(R.id.m7);
        ConcentrationTextViews = new TextView[] {cVw1, cTxt1, m1, cVw2, cTxt2, m2, cVw3, cTxt3, m3, cVw4, cTxt4, m4, cVw5, cTxt5, m5, cVw6, cTxt6, m6, cVw7, cTxt7, m7, cVwx, cTxtX, solutionVol};
        u1 = new User(0, 0);
        s1 = new Solution();
        barChart.setScaleYEnabled(false);
        barChart.getAxisLeft().setAxisMaximum(1f);
        barChart.getAxisLeft().setAxisMinimum(0f);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.setDescription(null);
        bChartX = barChart.getXAxis();
        bChartX.setPosition(XAxis.XAxisPosition.BOTTOM);
        bChartX.setDrawGridLines(false);
        bChartX.setGranularity(1f);


        findViewById(R.id.ManFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddComponentActivity.class);
                intent.putExtra("req_c", REQUEST_CODE_Manual);
                intent.putExtra("Solution", s1);
                startActivityForResult(intent, REQUEST_CODE_Manual);
            }
        });

        findViewById(R.id.AutoFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddComponentActivity.class);
                intent.putExtra("req_c", REQUEST_CODE_Auto);
                intent.putExtra("Solution", s1);
                startActivityForResult(intent, REQUEST_CODE_Auto);
            }
        });

        TitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ((TextInputLayout) (TitTxt.getParent()).getParent()).setError(null);
                    ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError(null);
                    int id = rBtnGrp.getCheckedRadioButtonId();
                    s1.l = Double.parseDouble(TitTxt.getText().toString());
                    if (Math.abs(s1.l) > 50) {
                        ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Value out of range!");
                        if (Double.parseDouble(VolTitTxt.getText().toString())>2000){
                            ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("Value out of range!");
                        }
                    } else {
                        if (Double.parseDouble(VolTitTxt.getText().toString())>2000){
                            ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("Value out of range!");
                        } else{
                            s1.Titrate(Double.parseDouble(VolTitTxt.getText().toString()), AcidBaseSw, CustomTitBtn.isChecked());
                            p1 = new Poly(s1.getDic());
                            pHVw.setText(s1.MainFunction(p1));
                            u1.PrepareOutputs(ConcentrationTextViews, s1.getComponentByBtnId(id));
                            s1.getComponentByBtnId(id).PrintConcentrations(ConcentrationTextViews, s1.getCn());

                            getData(s1.getComponentByBtnId(id));
                            barChart.clearAnimation();
                            AnimateDataSetChanged changer = new AnimateDataSetChanged(200, barChart, oldEntries, Entries);changer.setInterpolator(new AccelerateInterpolator()); // optionally set the Interpolator
                            changer.run();
                        }
                    }
                } catch(Exception e) {
                    if (TitTxt.getText().toString().matches(".") || (TitTxt.getText().toString().matches(""))){
                        ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Invalid input!");
                    }
                    if (VolTitTxt.getText().toString().matches(".") || (VolTitTxt.getText().toString().matches(""))){
                        ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("Invalid input!");
                    }
                }
            }
        });

        CustomTitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CustomTitBtn.isChecked()){
                    if (s1.NoTitrantAssigned()){
                        AcidBaseSw.setEnabled(false);
                        TitTxt.setEnabled(false);
                        EditBtn.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(MainActivity.this, AddComponentActivity.class);
                        intent.putExtra("req_c", REQUEST_CODE_Titrant);
                        intent.putExtra("Solution", s1);
                        startActivityForResult(intent, REQUEST_CODE_Titrant);
                    }
                } else{
                    AcidBaseSw.setEnabled(true);
                    TitTxt.setEnabled(true);
                    EditBtn.setVisibility(View.GONE);
                    if (AcidBaseSw.isChecked()) SelectedVw.setText(getResources().getString(R.string.sodium_hydroxide));
                    else SelectedVw.setText(getResources().getString(R.string.hydrogen_chloride));
                }
            }
        });

        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddComponentActivity.class);
                intent.putExtra("req_c", REQUEST_CODE_Titrant_edit);
                intent.putExtra("Solution", s1);
                startActivityForResult(intent, REQUEST_CODE_Titrant_edit);
            }
        });

        GraphBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GraphShow = new BottomSheetDialog(MainActivity.this);
                GraphShow.setContentView(R.layout.dialog);
                GraphShow.setTitle("Titration diagram");
                CheckBox CBEqPts = GraphShow.findViewById(R.id.CheckBoxEqPts);
                //CheckBox CBHalfEqPts = GraphShow.findViewById(R.id.CheckBoxHalfEqPts);
                final LineChart TitChart = GraphShow.findViewById(R.id.TitChart);
                TitChart.getAxisRight().setEnabled(false);
                final XAxis TitX = TitChart.getXAxis();
                GraphShow.findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GraphShow.dismiss();
                    }
                });
                GraphShow.findViewById(R.id.indBtn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // setup the alert builder
                        AlertDialog.Builder builderA = new android.app.AlertDialog.Builder(MainActivity.this);
                        builderA.setTitle("Choose a slot to write on");

                        // add a radio button list
                        String[] indicators = {"Phenolphthalein", "Methyl Orange", "none"};
                        builderA.setSingleChoiceItems(indicators, u1.indicator, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                u1.indicator = which;
                            }
                        });
                        builderA.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TitChart.setData(PrepareGraphData(u1.indicator));
                                TitChart.invalidate();
                            }
                        });
                        builderA.setNegativeButton("Cancel", null);

                        // create and show the alert dialog
                        dialogInd = builderA.create();
                        dialogInd.show();
                    }
                });

                CBEqPts.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        if (checked) {
                            s1.GenerateEquivalencePtsTags(TitX, MainActivity.this);
                        } else {
                            TitX.removeAllLimitLines();
                        }
                        TitChart.invalidate();
                    }
                });

                try {
                    ((TextInputLayout) (TitTxt.getParent()).getParent()).setError(null);
                    ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError(null);
                    s1.l = Double.parseDouble(TitTxt.getText().toString());
                    if (Math.abs(s1.l) > 50 || String.valueOf(s1.l).matches("0.0")) {
                        ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Value out of range!");
                        if (Double.parseDouble(VolTitTxt.getText().toString()) > 2000) {
                            ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("Value out of range!");
                        } else if ((String.valueOf(Double.parseDouble(VolTitTxt.getText().toString())).matches("0.0"))) {
                            ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("No titration?");
                        }
                    } else {
                        if (Double.parseDouble(VolTitTxt.getText().toString()) > 2000) {
                            ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("Value out of range!");
                        } else if ((String.valueOf(Double.parseDouble(VolTitTxt.getText().toString())).matches("0.0"))) {
                            ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("No titration?");
                        } else {
                            LineData lineData = PrepareGraphData(u1.indicator);
                            GraphShow.show();

                            TitChart.getDescription().setText("");
                            TitChart.getDescription().setTextSize(12);
                            TitChart.setDrawMarkers(true);
                            // TitChart.setMarker(markerView(MainActivity.this));
                            // TitChart.getAxisLeft().addLimitLine(lowerLimitLine(2,"Lower Limit",2,12, getColor("defaultOrange"),getColor("defaultOrange")));
                            // TitChart.getAxisLeft().addLimitLine(upperLimitLine(5,"Upper Limit",2,12, getColor("defaultGreen"),getColor("defaultGreen")));

                            TitX.setPosition(XAxis.XAxisPosition.BOTTOM);
                            TitChart.animateX(600);
                            TitX.setGranularityEnabled(true);
                            TitX.setDrawGridLines(false);
                            TitX.setGranularity(Float.parseFloat(String.valueOf(
                                    s1.V * (s1.GetEq()[0] - s1.GetEq()[1]) / s1.l / 5)));
                            TitX.setLabelCount(6);

                            TitChart.setData(lineData);
                        }
                    }
                } catch (Exception e) {
                    if (TitTxt.getText().toString().matches(".") || (TitTxt.getText().toString().matches(""))) {
                        ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Invalid input!");
                    }
                    if (VolTitTxt.getText().toString().matches(".") || (VolTitTxt.getText().toString().matches(""))) {
                        ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("Invalid input!");
                    }
                }
            }
        });

        AcidBaseSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) SelectedVw.setText(getResources().getString(R.string.sodium_hydroxide));
                else SelectedVw.setText(getResources().getString(R.string.hydrogen_chloride));
            }
        });

        rBtnGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id!=-1){
                    try {
                        if (s1.Entered.contains(u1.RButt(id))){
                            Rst(ConcentrationTextViews);
                            u1.PrepareOutputs(ConcentrationTextViews, s1.getComponentByBtnId(id));
                            s1.getComponentByBtnId(id).PrintConcentrations(ConcentrationTextViews, s1.getCn());

                            BarDataSet barDataSet = new BarDataSet(getData(s1.getComponentByBtnId(id)), "Species composition percent");
                            barDataSet.setBarBorderWidth(0.9f);
                            barDataSet.setColors(getColorSet(s1.n));
                            BarData barData = new BarData(barDataSet);
                            barData.setHighlightEnabled(false);
                            IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(species);
                            bChartX.setValueFormatter(formatter);
                            barChart.setData(barData);
                            barChart.setFitBars(true);
                            barChart.animateY(500);
                            barChart.invalidate();
                        }
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        rBtnGrp.clearCheck();
                        ((RadioButton) rBtnGrp.getChildAt(s1.Entered.get(0))).setChecked(true);
                    }
                }
            }
        });
        ((FloatingActionsMenu) findViewById(R.id.FabMenu)).expand();
    }

    // This method is invoked when target activity return result data back.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.
        // This request code is set by startActivityForResult(intent, REQUEST_CODE_1) method.
        // String messageReturn = dataIntent.getStringExtra("message_return");

        if (requestCode == REQUEST_CODE_Auto || requestCode == REQUEST_CODE_Manual) {
            ((FloatingActionsMenu) findViewById(R.id.FabMenu)).collapse();
            if (resultCode == RESULT_OK) {
                s1 = dataIntent.getParcelableExtra("Solution");
                p1 = new Poly(s1.getDic());
            }
            AcidBaseSw.setEnabled(true);
            TitTxt.setEnabled(true);
            VolTitTxt.setEnabled(true);
            TitBtn.setEnabled(true);
            GraphBtn.setEnabled(true);
            CustomTitBtn.setEnabled(true);

            int ch;
            try{
                ch = s1.Entered.get(s1.Entered.size()-1);
            } catch(Exception e){
                ch = -1;
            }
            for (int i = rBtnGrp.getChildCount() - 1; i > -1; i--) {
                try {
                    if (s1.Entered.contains(i)) {
                        rBtnGrp.getChildAt(i).setEnabled(true);
                    } else rBtnGrp.getChildAt(i).setEnabled(false);
                } catch (Exception e) {
                    rBtnGrp.getChildAt(i).setEnabled(false);
                }
            }
            if (resultCode == RESULT_OK) {
                Rst(ConcentrationTextViews);
                pHVw.setText(s1.MainFunction(p1));
            }
            rBtnGrp.clearCheck();
            if (ch != -1) {
                rBtnGrp.getChildAt(ch).setEnabled(true);
                rBtnGrp.check(rBtnGrp.getChildAt(ch).getId());
            }
        } else if (requestCode == REQUEST_CODE_Titrant || requestCode == REQUEST_CODE_Titrant_edit){
            if (resultCode == RESULT_OK) {
                s1 = dataIntent.getParcelableExtra("Solution");
                p1 = new Poly(s1.getDic());

                Rst(ConcentrationTextViews);
                pHVw.setText(s1.MainFunction(p1));

                try{
                    SelectedVw.setText(u1.AssignConcentrations(s1.getComps().get(s1.TitInd).n, s1.getComps().get(s1.TitInd), true), TextView.BufferType.SPANNABLE);
                    VolTitTxt.setText(String.valueOf(s1.getComps().get(s1.TitInd).vl));
                    TitTxt.setText(String.valueOf(s1.getComps().get(s1.TitInd).ul));
                } catch (Exception e){
                    // no titrant
                }
                for (int i = rBtnGrp.getChildCount() - 1; i > -1; i--) {
                    try {
                        if (s1.Entered.contains(i)) {
                            rBtnGrp.getChildAt(i).setEnabled(true);
                        } else rBtnGrp.getChildAt(i).setEnabled(false);
                    } catch (Exception e) {
                        rBtnGrp.getChildAt(i).setEnabled(false);
                    }
                }
            } else CustomTitBtn.setChecked(false);
        }
    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.infomenubtn) {
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(this, 0);
            // SLIKU PRIMJERA INPUTA ??
            builder.setTitle("Welcome to DeBuff v69!")
                    .setMessage("DeBuff is an acid/base solution simulator.\n" +
                            "You can choose an acid and study its properties or you can manually input acid or base data (which is available on the Internet).\n" +
                            "Afterwards, addition of another solution component (tap the plus button), along with titration simulation is available.\n" +
                            "*Some logical bounds have been set, so you will be warned if you try to cross them. For more info google 'ionic strength'.\n\n" +
                            "Developed by Luka List\nDesign by Tin Prvčić\n© 2019")
                    .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    void Rst(@NonNull TextView[] Texts){
        for (TextView el : Texts) {
            el.setVisibility(View.GONE); // Remove the yet-to-calculate values
        }
        for (int i=1; i<(Texts.length)-3; i+=3){
            Texts[i].setText("0");
        }
        for (int i=(Texts.length)-3; i<Texts.length; i++){
            Texts[i].setVisibility(View.VISIBLE);
        }
    }
    private ArrayList getData(@NotNull Component Comp){
        oldEntries = Entries;
        Entries = new ArrayList<>();
        species = new String[Comp.n+1];
        for (int i = 0; i<Comp.n+1; i++){
            Entries.add(new BarEntry(i, Float.parseFloat(String.valueOf(Comp.GetConcentrations()[i]/Comp.cu))));
            species[Comp.n-i] = String.valueOf(u1.AssignConcentrations(i, Comp, false));
        }
        return(Entries);
    }
    static int manipulateColor(int color, float factor) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] = 1.0f - factor * (1.0f - hsv[2]);
        color = Color.HSVToColor(hsv);
        return(color);
    }
    int[] getColorSet(int n){
        int[] out = new int[n+1];
        for (int i = 0; i<n+1; i++){
            out[i] = manipulateColor(MainActivity.this.getResources().getColor(R.color.colorPrimary), (float) 3*i/(n+1)+0.1f);
        }
        return(out);
    }
    LineData PrepareGraphData(int Indicator){
        ArrayList<LineDataSet> DataSets = s1.GenerateTitrationGraphData(AcidBaseSw, Double.parseDouble(VolTitTxt.getText().toString()), Indicator, MainActivity.this, false);
        LineDataSet LDSa = DataSets.get(0);
        LineDataSet LDSb = DataSets.get(1);

        for (LineDataSet el: new LineDataSet[] {LDSa, LDSb}){
            el.setAxisDependency(YAxis.AxisDependency.LEFT);
            el.setHighlightEnabled(true);
            el.setLineWidth(2);
            // lineDataSet.setColor(getColor("defaultBlue"));
            el.setDrawHighlightIndicators(true);
            el.setHighLightColor(Color.RED);
            el.setValueTextSize(12);
            // lineDataSet.setValueTextColor(getColor("primaryDark"));
            el.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            el.setDrawFilled(true);
            el.setDrawValues(false);
            // el.setFillColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent));
            // boju ispod crte ne diramo - odrazava boju indikatora
            el.setColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
            el.setDrawCircles(false);
        }

        return(new LineData(LDSa, LDSb));
    }
}
