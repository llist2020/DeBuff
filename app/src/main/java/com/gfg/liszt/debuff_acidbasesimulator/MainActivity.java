package com.gfg.liszt.debuff_acidbasesimulator;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.Contract;

import java.util.Locale;

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
        dialog = new BottomSheetDialog(this);
        final RelativeLayout mainLayout = findViewById(R.id.activity_main);
        final int choice = Integer.parseInt(getIntent().getStringExtra("buttonClicked"));
        final FloatingActionButton fab = findViewById(R.id.fab);
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
                dialog = new BottomSheetDialog(MainActivity.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Add a component");
                dialog.show();
                final Button ManBtn = dialog.findViewById(R.id.ManBtn);
                final Button AutoBtn = dialog.findViewById(R.id.AutoBtn);
                final Button SaveBtn = dialog.findViewById(R.id.SaveBtn);
                final Button NextBtn = dialog.findViewById(R.id.NextBtn);
                final Button dialogBtn = dialog.findViewById(R.id.okBtn);
                final Switch AcidBaseSwInp = dialog.findViewById(R.id.AcidBaseSwInp);
                final EditText nTxt = dialog.findViewById(R.id.nTxt);
                final EditText cuTxt = dialog.findViewById(R.id.cuTxt);
                final EditText cnTxt = dialog.findViewById(R.id.cnTxt);
                final EditText VTxt = dialog.findViewById(R.id.VTxt);
                final TextInputLayout KTxtIL = dialog.findViewById(R.id.KTxtIL);
                final EditText KTxt = dialog.findViewById(R.id.KTxt);
                final EditText[] ETs = {nTxt, cuTxt, cnTxt, VTxt};
                if (u1.citt != 0){
                    VTxt.setEnabled(false);
                    VTxt.setText(String.format(Locale.US, "%.2f", u1.V));
                } else if (choice == 0) AutoBtn.setEnabled(false);
                SaveBtn.setEnabled(false); // inace bi se mogla samo promjenit boja il nekj

                ManBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (EditText el: ETs) el.setVisibility(View.VISIBLE);
                        SaveBtn.setVisibility(View.VISIBLE);
                        u1.ion = "A";
                        u1.ent = true;
                        nTxt.setEnabled(true);
                        nTxt.setText("");
                        if (!VTxt.getText().toString().matches("")) u1.Valid[3] = 1;
                    }
                });

                AutoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (EditText el: ETs) el.setVisibility(View.VISIBLE);
                        SaveBtn.setVisibility(View.VISIBLE);
                        nTxt.setEnabled(false);
                        AcidBaseSwInp.setChecked(!u1.acid);
                        KTxt.setText(String.format(Locale.US, "%.2f", -Math.log10(u1.K[1])));
                        nTxt.setText(String.format(Locale.US, "%s", u1.n));
                        u1.ent = false;
                    }
                });

                SaveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double[] Inputs = ValidateInputs();
                        int n;
                        double cu, cn, V;
                        if (Inputs.length != 0){ // if all inputs are valid
                            n = ((Double) Inputs[0]).intValue();
                            cu = Inputs[1];
                            cn = Inputs[2];
                            V = Inputs[3];
                            for (EditText el: ETs) el.setEnabled(false);
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

                            if (u1.citt == 0){
                                if(!u1.ent) {
                                    u1.V = V;
                                    u1 = new User(n, cu, cn, u1);
                                } else u1 = new User(n, cu, cn, V, "Mjau", true);
                            } else u1 = new User(n, cu, cn, u1);
                            for (EditText el: ETs){
                                el.setEnabled(false);
                                el.setError(null);
                            }
                            ManBtn.setEnabled(false);
                            AutoBtn.setEnabled(false);
                            AcidBaseSwInp.setEnabled(false);
                            if (u1.n>0){
                                KTxt.setVisibility(View.VISIBLE);
                                NextBtn.setVisibility(View.VISIBLE);
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
                            for (EditText el: ETs) el.setEnabled(true);
                            if (u1.citt != 0) {
                                VTxt.setEnabled(false);
                                VTxt.setText(String.format(Locale.US, "%.2f", u1.V));
                            }
                            SaveBtn.setEnabled(true);
                            KTxt.setVisibility(View.GONE);
                            NextBtn.setVisibility(View.GONE);
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
                        KTxtIL.setError(null);
                        if (u1.itt != 0) {
                            try {
                                u1.K[u1.itt] = Math.pow(10, -Float.parseFloat(KTxt.getText().toString()));
                                if (u1.K[u1.itt] < Math.pow(10, -35) || u1.K[u1.itt] > Math.pow(10, 35)) {
                                    u1.itt--;
                                    KTxtIL.setError("Constant too extreme.");
                                    KTxtIL.setHint("Equilibrium constant pK" + u1.a.charAt(0) + (u1.itt) + ":");
                                }
                            } catch (Exception e) {
                                if (KTxt.getText().toString().equals(".")) {
                                    KTxtIL.setError("Invalid input.");
                                } else {
                                    KTxtIL.setError("An error occurred.");
                                }
                                u1.itt--;
                            }

                            if (u1.itt == u1.n) {
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
                                KTxt.setText(String.format(Locale.US, "%.2f", -Math.log10(u1.K[u1.itt])));
                            }catch(Exception e){
                                System.out.println("An error occurred at loading a constant");
                            }
                        } else KTxt.setText("");
                        if (u1.itt >= u1.n) NextBtn.setText(R.string.Go);
                    }
                });

                AcidBaseSwInp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        u1.SetAcid(!isChecked);
                        KTxtIL.setHint("Equilibrium constant pK" + u1.a.charAt(0) + (u1.itt) + ":");
                        ((TextInputLayout) (cuTxt.getParent()).getParent()).setHint("Overall " + u1.a + " concentration:");
                        if (isChecked) ((TextInputLayout) (nTxt.getParent()).getParent()).setHint("Number of dissociable hydroxides:");
                        else ((TextInputLayout) (nTxt.getParent()).getParent()).setHint("Number of dissociable protons:");
                    }
                });

                nTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (nTxt.getText().toString().matches("")){
                            SaveBtn.setEnabled(false);
                            u1.Valid[0] = 0;
                        } else u1.Valid[0] = 1;
                        if (u1.AllInputsValid()) SaveBtn.setEnabled(true);
                    }
                    @Override
                    public void afterTextChanged(Editable editable) { }
                });
                cuTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (cuTxt.getText().toString().matches("") && cuTxt.getText().toString().matches(".")){
                            SaveBtn.setEnabled(false);
                            u1.Valid[1] = 0;
                        } else u1.Valid[1] = 1;
                        if (u1.AllInputsValid()) SaveBtn.setEnabled(true);
                    }
                    @Override
                    public void afterTextChanged(Editable editable) { }
                });
                cnTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (cnTxt.getText().toString().matches("") && cnTxt.getText().toString().matches(".")){
                            SaveBtn.setEnabled(false);
                            u1.Valid[2] = 0;
                        } else u1.Valid[2] = 1;
                        if (u1.AllInputsValid()) SaveBtn.setEnabled(true);
                    }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }
                    @Override
                    public void afterTextChanged(Editable editable) { }
                });
                VTxt.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (cuTxt.getText().toString().matches("") && cuTxt.getText().toString().matches(".")){
                            SaveBtn.setEnabled(false);
                            u1.Valid[3] = 0;
                        } else u1.Valid[3] = 1;
                        if (u1.AllInputsValid()) SaveBtn.setEnabled(true);
                    }
                    @Override
                    public void afterTextChanged(Editable editable) {
                    }
                });

                cnTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            System.out.println("nextkeyboard");
                            if (u1.citt != 0){
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            } else{
                                VTxt.requestFocus();
                            }
                            return true;
                        }
                        return false;
                    }
                });
            }

            @NonNull
            @Contract(" -> new")
            private double[] ValidateInputs(){
                final EditText[] ETs = new EditText[] {dialog.findViewById(R.id.nTxt), dialog.findViewById(R.id.cuTxt), dialog.findViewById(R.id.cnTxt), dialog.findViewById(R.id.VTxt)};
                for (EditText el: ETs) ((TextInputLayout) (el.getParent()).getParent()).setError(null);
                double[] out = new double[4];

                for (int i = 0; i<4; i++){
                    try{
                        out[i] = Double.parseDouble(ETs[i].getText().toString());
                        if (i == 0){
                            if (out[i] == 0.0) {
                                ((TextInputLayout) (ETs[0].getParent()).getParent()).setError("No dissociation will be observed"); //potvrditi?; toast
                            }
                            if (out[i] > 6 || out[i] < 0) { // math limit;
                                ((TextInputLayout) (ETs[0].getParent()).getParent()).setError("DeBuff supports n€<0,7>");
                                ETs[0].setText("");
                                return(new double[0]);
                            }
                        } else if (i == 3){
                            if (out[3] > 2000) {
                                ((TextInputLayout) (ETs[3].getParent()).getParent()).setError("Do you really need more than 2L?");
                                ETs[3].setText("");
                                return(new double[0]);
                            }
                        } else{
                            if (out[i] > 100) {
                                ((TextInputLayout) (ETs[i].getParent()).getParent()).setError("Concentrated solutions tend to differ from mathematical model.");
                                ETs[i].setText("");
                                return(new double[0]);
                            }
                        }
                    } catch (Exception e) {
                        ((TextInputLayout) (ETs[i].getParent()).getParent()).setError("An error occurred.");
                        ETs[i].setText("");
                        return(new double[0]);
                    }
                }
                return(out);
            }
        });

        TitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ((TextInputLayout) (TitTxt.getParent()).getParent()).setError(null);
                    ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError(null);
                    s1.l = Float.parseFloat(TitTxt.getText().toString());
                    if (Math.abs(s1.l * Float.parseFloat(VolTitTxt.getText().toString()))/(Float.parseFloat(VolTitTxt.getText().toString()) + s1.V) > 50) {
                        ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Too high!");
                        Snackbar.make(mainLayout, "Concentrated solutions tend to differ from mathematical model.", Snackbar.LENGTH_LONG).show();
                    } else {
                        s1.Titrate(Float.parseFloat(VolTitTxt.getText().toString()), AcidBaseSw);
                        p1 = new Poly(s1.GetDic());
                        pHVw.setText(s1.MainFunction(p1, u1.Texts));
                        u1.PrepareOutputs(Nvws, s1.GetComps()[RButt(rBtnz.getCheckedRadioButtonId())]);
                        s1.GetComps()[RButt(rBtnz.getCheckedRadioButtonId())].PrintConcentrations(Nvws);
                    }
                } catch(Exception e) {
                    if (TitTxt.getText().toString().matches(".") || (TitTxt.getText().toString().matches(""))){
                        ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Invalid input.");
                    }
                    if (VolTitTxt.getText().toString().matches(".") || (VolTitTxt.getText().toString().matches(""))){
                        ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError("Invalid input.");
                    }
                    Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
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
                    System.out.println("rButts error");
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
    public int RButt(int id) {
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