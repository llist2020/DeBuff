package com.gfg.liszt.debuff_acidbasesimulator;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    private User u1;
    private Poly p1;
    private Solution s1;
    public Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RelativeLayout mainLayout = findViewById(R.id.layoutaa);
        final int choice = Integer.parseInt(getIntent().getStringExtra("buttonClicked"));
        dialog = new BottomSheetDialog(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        final EditText TitTxt = findViewById(R.id.TitTxt);
        final EditText VolTitTxt = findViewById(R.id.VolTitTxt);
        final Button TitBtn = findViewById(R.id.TitBtn);
        final RadioGroup rBtnz = findViewById(R.id.rBtnz);
        final RadioButton rBtn0 = findViewById(R.id.rBtn0);
        final Switch AcidBaseSw = findViewById(R.id.AcidBaseSw);
        final TextView pHVw = findViewById(R.id.pHVw);
        final TextView cVwx = findViewById(R.id.cVwx);
        final TextView cTxtx = findViewById(R.id.cTxtx);
        final TextView solutionVol = findViewById(R.id.solutionVol);
        final TextView cVwl1 = findViewById(R.id.cVwl1);
        final TextView cVwl2 = findViewById(R.id.cVwl2);
        final TextView cVwl3 = findViewById(R.id.cVwl3);
        final TextView cVwl4 = findViewById(R.id.cVwl4);
        final TextView cVwl5 = findViewById(R.id.cVwl5);
        final TextView cVwl6 = findViewById(R.id.cVwl6);
        final TextView cVwl7 = findViewById(R.id.cVwl7);
        final TextView cTxtl1 = findViewById(R.id.cTxtl1);
        final TextView cTxtl2 = findViewById(R.id.cTxtl2);
        final TextView cTxtl3 = findViewById(R.id.cTxtl3);
        final TextView cTxtl4 = findViewById(R.id.cTxtl4);
        final TextView cTxtl5 = findViewById(R.id.cTxtl5);
        final TextView cTxtl6 = findViewById(R.id.cTxtl6);
        final TextView cTxtl7 = findViewById(R.id.cTxtl7);
        final TextView cl1 = findViewById(R.id.cl1);
        final TextView cl2 = findViewById(R.id.cl2);
        final TextView cl3 = findViewById(R.id.cl3);
        final TextView cl4 = findViewById(R.id.cl4);
        final TextView cl5 = findViewById(R.id.cl5);
        final TextView cl6 = findViewById(R.id.cl6);
        final TextView cl7 = findViewById(R.id.cl7);
        final TextView[] Nvws = {cVwl1, cTxtl1, cl1, cVwl2, cTxtl2, cl2, cVwl3, cTxtl3, cl3, cVwl4, cTxtl4, cl4, cVwl5, cTxtl5, cl5, cVwl6, cTxtl6, cl6, cVwl7, cTxtl7, cl7, cVwx, cTxtx, solutionVol};

        u1 = new User(choice, Nvws);
        Rst(Nvws);
        if(choice==0){
            u1.ent = true;
        }
        else{
            pHVw.setText("--");
            solutionVol.setText("--");
            cTxtx.setText("--");
            AcidBaseSw.setEnabled(false);
            TitTxt.setEnabled(false);
            VolTitTxt.setEnabled(false);
            TitBtn.setEnabled(false);
            for(int i = 0; i < rBtnz.getChildCount(); i++){
                rBtnz.getChildAt(i).setEnabled(false);
            }
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Add a component");
                final Button ManBtn = dialog.findViewById(R.id.ManBtn);
                final Button AutoBtn = dialog.findViewById(R.id.AutoBtn);
                final Button SaveBtn = dialog.findViewById(R.id.SaveBtn);
                final Button NextBtn = dialog.findViewById(R.id.NextBtn);
                final Button dialogBtn = dialog.findViewById(R.id.okBtn);
                final Switch AcidBaseSwInp = dialog.findViewById(R.id.AcidBaseSwInp);
                final LinearLayout InpLayout = dialog.findViewById(R.id.InpLayout);
                final TextInputLayout nLay = dialog.findViewById(R.id.nLay);
                final EditText nTxt = dialog.findViewById(R.id.nTxt);
                final EditText cuTxt = dialog.findViewById(R.id.cuTxt);
                final EditText cnTxt = dialog.findViewById(R.id.cnTxt);
                final TextInputLayout KTxtIL = dialog.findViewById(R.id.KTxtIL);
                final EditText KTxt = dialog.findViewById(R.id.KTxt);
                final EditText VTxt = dialog.findViewById(R.id.VTxt);
                dialog.findViewById(R.id.KTxt).setEnabled(false);
                dialog.findViewById(R.id.NextBtn).setEnabled(false);
                for(int i = 0; i < InpLayout.getChildCount(); i++) InpLayout.getChildAt(i).setVisibility(View.GONE);
                if (u1.citt != 0){
                    VTxt.setEnabled(false);
                    VTxt.setText(Double.toString(u1.V));
                }

                ManBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for(int i = 0; i < InpLayout.getChildCount(); i++) InpLayout.getChildAt(i).setVisibility(View.VISIBLE);
                        u1.ent = true;
                    }
                });

                AutoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            AcidBaseSwInp.setChecked(!u1.acid);
                            KTxt.setText(Double.toString(-Math.log10(u1.K[1])));
                            nTxt.setText(Integer.toString(u1.n));
                            for(int i = 0; i < InpLayout.getChildCount(); i++) InpLayout.getChildAt(i).setVisibility(View.VISIBLE);
                            u1.ent = false;
                        } catch(Exception e){
                            System.out.println("Input error (Auto btn)");
                        }
                    }
                });

                SaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //nTxt.set
                        u1.allclr = true;
                        int n = 0;
                        float cu = 0, cn = 0, V = 0;
                        for(int i = 0; i < InpLayout.getChildCount(); i++) InpLayout.getChildAt(i).setEnabled(false);
                        SaveBtn.setEnabled(false);
                        pHVw.setText("--");
                        solutionVol.setText("--");
                        cTxtx.setText("--");
                        Rst(Nvws);
                        AcidBaseSwInp.setEnabled(false);
                        TitTxt.setEnabled(false);
                        VolTitTxt.setEnabled(false);
                        TitBtn.setEnabled(false);
                        for(int i = 0; i < rBtnz.getChildCount(); i++){
                            rBtnz.getChildAt(i).setEnabled(false);
                        }

                        try{
                            n = Integer.parseInt(nTxt.getText().toString());
                            if (n == 0) {
                                ((TextInputLayout) (nTxt.getParent()).getParent()).setError("No dissociation will be observed"); //potvrditi?; toast
                                nTxt.setText("");
                            }
                            if (n > 6 || n < 0) { // math limit;
                                u1.allclr = false;
                                ((TextInputLayout) (nTxt.getParent()).getParent()).setError("DeBuff supports n€<0,7>");
                                nTxt.setText("");
                            }
                        } catch (Exception e) {
                            u1.allclr = false;
                            if (nTxt.getText().toString().equals(".")) {
                                ((TextInputLayout) (nTxt.getParent()).getParent()).setError("Invalid input.");
                                nTxt.setText("");
                            } else {
                                ((TextInputLayout) (nTxt.getParent()).getParent()).setError("An error occurred.");
                                nTxt.setText("");
                            }
                        }

                        try {
                            cu = Float.parseFloat(cuTxt.getText().toString());
                            if (cu > 100) {
                                u1.allclr = false;
                                ((TextInputLayout) (cuTxt.getParent()).getParent()).setError("Concentrated solutions tend to differ from mathematical model.");
                                cuTxt.setText("");
                            }
                        } catch (Exception e) {
                            u1.allclr = false;
                            if (cuTxt.getText().toString().equals(".")) {
                                ((TextInputLayout) (cuTxt.getParent()).getParent()).setError("Invalid input.");
                                cuTxt.setText("");
                            } else {
                                ((TextInputLayout) (cuTxt.getParent()).getParent()).setError("An error occurred.");
                                cuTxt.setText("");
                            }
                        }

                        try {
                            cn = Float.parseFloat(cnTxt.getText().toString());
                            if (cn > 100) {
                                u1.allclr = false;
                                ((TextInputLayout) (cnTxt.getParent()).getParent()).setError("Concentrated solutions tend to differ from mathematical model.");
                                cnTxt.setText("");
                            }
                        } catch (Exception e) {
                            u1.allclr = false;
                            if (cnTxt.getText().toString().equals(".")) {
                                ((TextInputLayout) (cnTxt.getParent()).getParent()).setError("Invalid input.");
                                cnTxt.setText("");
                            } else {
                                ((TextInputLayout) (cnTxt.getParent()).getParent()).setError("An error occurred.");
                                cnTxt.setText("");
                            }
                        }

                        try {
                            V = Float.parseFloat(VTxt.getText().toString());
                            if (V > 2000) {
                                u1.allclr = false;
                                ((TextInputLayout) (VTxt.getParent()).getParent()).setError("Do you really need more than 2L?");
                                VTxt.setText("");
                            }
                        } catch (Exception e) {
                            u1.allclr = false;
                            if (VTxt.getText().toString().equals(".")) {
                                ((TextInputLayout) (VTxt.getParent()).getParent()).setError("Invalid input.");
                                VTxt.setText("");
                            } else {
                                ((TextInputLayout) (VTxt.getParent()).getParent()).setError("An error occurred.");
                                VTxt.setText("");
                            }
                        }

                        if (u1.allclr){
                            if (u1.citt == 0) u1 = new User(n, cu, cn, V, u1.ent);
                            else u1 = new User(n, cu, cn, u1);
                            dialog.findViewById(R.id.nTxt).setEnabled(false);
                            dialog.findViewById(R.id.cuTxt).setEnabled(false);
                            dialog.findViewById(R.id.cnTxt).setEnabled(false);
                            ManBtn.setEnabled(false);
                            AutoBtn.setEnabled(false);
                            AcidBaseSwInp.setEnabled(false);
                            if (u1.n>0){
                                KTxt.setEnabled(true);
                                NextBtn.setEnabled(true);
                                NextBtn.performClick();
                            } else{
                                if (u1.citt==0) s1 = new Solution(u1, AcidBaseSwInp.isChecked());
                                else if (u1.citt<4) s1.AddComp(u1, AcidBaseSwInp.isChecked()); // VAMO KOD ZA OVERWRITANJE

                                p1 = new Poly(s1.GetDic());
                                Rst(Nvws);
                                u1.PrepareOutputs(Nvws, s1.GetComps()[RButt(rBtnz.getCheckedRadioButtonId())]);
                                pHVw.setText(s1.MainFunction(p1, u1.Texts));
                                s1.GetComps()[RButt(rBtnz.getCheckedRadioButtonId())].PrintConcentrations(Nvws);
                                u1.itt = -1;
                                u1.citt++;
                                AcidBaseSw.setEnabled(true);
                                TitTxt.setEnabled(true);
                                VolTitTxt.setEnabled(true);
                                TitBtn.setEnabled(true);
                                for(int i = 0; i < rBtnz.getChildCount(); i++){
                                    try{
                                        System.out.println(s1.GetComps()[i].n);
                                        rBtnz.getChildAt(i).setEnabled(true);
                                    } catch(Exception e){
                                        rBtnz.getChildAt(i).setEnabled(false);
                                    }
                                }
                                dialog.dismiss();
                            }
                        } else{
                            for(int i = 0; i < InpLayout.getChildCount(); i++) InpLayout.getChildAt(i).setEnabled(true);
                            SaveBtn.setEnabled(true);
                        }
                    }
                });

                dialogBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                NextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (u1.itt != 0) {
                            try {
                                u1.K[u1.itt] = Math.pow(10, -Float.parseFloat(KTxt.getText().toString()));
                                if (u1.K[u1.itt] < Math.pow(10, -35) || u1.K[u1.itt] > Math.pow(10, 35)) {
                                    u1.itt--;
                                    KTxt.setError("Constant too extreme.");
                                    KTxtIL.setHint("Equilibrium constant pK" + u1.a.charAt(0) + (u1.itt) + ":");
                                }
                            } catch (Exception e) {
                                if (KTxt.getText().toString().equals(".")) {
                                    KTxt.setError("Invalid input.");
                                } else {
                                    KTxt.setError("An error occurred.");
                                }
                                u1.itt--;
                            }

                            if (u1.itt == u1.n && u1.allclr) {
                                if (u1.citt==0) s1 = new Solution(u1, AcidBaseSwInp.isChecked());
                                else if (u1.citt<4) s1.AddComp(u1, AcidBaseSwInp.isChecked()); // VAMO KOD ZA OVERWRITANJE

                                p1 = new Poly(s1.GetDic());
                                Rst(Nvws);
                                u1.PrepareOutputs(Nvws, s1.GetComps()[RButt(rBtnz.getCheckedRadioButtonId())]);
                                pHVw.setText(s1.MainFunction(p1, u1.Texts));
                                s1.GetComps()[RButt(rBtnz.getCheckedRadioButtonId())].PrintConcentrations(Nvws);
                                u1.itt = -1;
                                u1.citt++;
                                AcidBaseSw.setEnabled(true);
                                TitTxt.setEnabled(true);
                                VolTitTxt.setEnabled(true);
                                TitBtn.setEnabled(true);
                                for(int i = 0; i < rBtnz.getChildCount(); i++){
                                    try{
                                        System.out.println(s1.GetComps()[i].n);
                                        rBtnz.getChildAt(i).setEnabled(true);
                                    } catch(Exception e){
                                        rBtnz.getChildAt(i).setEnabled(false);
                                    }
                                }
                                dialog.dismiss();
                            }
                        }
                        KTxtIL.setHint("Equilibrium constant pK" + u1.a.charAt(0) + (u1.itt+1) + ":");
                        u1.itt++;
                        if (!u1.ent){
                            try{
                                KTxt.setText(Double.toString(-Math.log10(u1.K[u1.itt])));
                            }catch(Exception e){
                                System.out.println("Next: error");
                            }
                        } else KTxt.setText("");
                        if (u1.itt >= u1.n) NextBtn.setText("Go");
                    }
                });
                AcidBaseSwInp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        u1.SetAcid(!isChecked);
                        KTxtIL.setHint("Equilibrium constant pK" + u1.a.charAt(0) + (u1.itt) + ":");
                        ((TextInputLayout) (cuTxt.getParent()).getParent()).setHint("Overall " + u1.a + "  concentration:");
                        if (isChecked) ((TextInputLayout) (nTxt.getParent()).getParent()).setHint("Number of dissociable hydroxides:");
                        else ((TextInputLayout) (nTxt.getParent()).getParent()).setHint("Number of dissociable protons:");
                    }
                });

                dialog.show();
            }
        });

        TitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    s1.l = Float.parseFloat(TitTxt.getText().toString());
                    if (Math.abs(s1.l * Float.parseFloat(VolTitTxt.getText().toString()))/(Float.parseFloat(VolTitTxt.getText().toString()) + s1.V) > 50) {
                        Snackbar.make(mainLayout, "Concentrated solutions tend to differ from mathematical model.", Snackbar.LENGTH_LONG).show();
                    } else {
                        s1.tit(Float.parseFloat(VolTitTxt.getText().toString()), AcidBaseSw);
                        p1 = new Poly(s1.GetDic());
                        pHVw.setText(s1.MainFunction(p1, u1.Texts));
                        u1.PrepareOutputs(Nvws, s1.GetComps()[RButt(rBtnz.getCheckedRadioButtonId())]);
                        s1.GetComps()[RButt(rBtnz.getCheckedRadioButtonId())].PrintConcentrations(Nvws);
                    }
                } catch(Exception e){
                    if(TitTxt.getText().toString().equals(".")||VolTitTxt.getText().toString().equals(".")){
                        Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                    } else{
                        Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });

        rBtnz.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = rBtnz.getCheckedRadioButtonId();
                try {
                    Rst(Nvws);
                    u1.PrepareOutputs(Nvws, s1.GetComps()[RButt(id)]);
                    s1.GetComps()[RButt(id)].PrintConcentrations(Nvws);
                } catch (Exception e){
                    Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                    rBtn0.setChecked(true);
                }
            }
        });
        fab.performClick();
    }

    public void Rst(@NonNull TextView[] txts){
        for (TextView el : txts) {
            el.setVisibility(View.GONE); // Remove the yet-to-calculate values
        }
        for (int i=1; i<(txts.length)-3; i+=3){
            txts[i].setText("0");
        }
        for (int i=(txts.length)-3; i<txts.length; i++){
            txts[i].setVisibility(View.VISIBLE);
        }
    }
    public int RButt(int ajdi) {
        switch (ajdi) {
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