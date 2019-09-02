package com.gfg.liszt.debuff_acidbasesimulator;

import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity{
    private User u1;
    private Poly p1;
    private Solution s1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText nTxt = findViewById(R.id.nTxt);
        final EditText TitTxt = findViewById(R.id.TitTxt);
        final EditText VolTitTxt = findViewById(R.id.VolTitTxt);
        final Button NextBtn = findViewById(R.id.NextBtn);
        final Button TitBtn = findViewById(R.id.TitBtn);
        final Button AddBtn = findViewById(R.id.AddBtn);
        final Button RstBtn = findViewById(R.id.RstBtn);
        final RadioGroup rBtnz = findViewById(R.id.rBtnz);
        final RadioButton rBtn1 = findViewById(R.id.rBtn1);
        final RadioButton rBtn2 = findViewById(R.id.rBtn2);
        final RadioButton rBtn3 = findViewById(R.id.rBtn3);
        final RadioButton rBtn0 = findViewById(R.id.rBtn0);
        final Switch AcidBaseSw = findViewById(R.id.AcidBaseSw);
        final Switch AcidBaseSwInp = findViewById(R.id.AcidBaseSwInp);
        final TextView pHVw = findViewById(R.id.pHVw);
        final TextView cVw1 = findViewById(R.id.cVw1);
        final TextView cVw2 = findViewById(R.id.cVw2);
        final TextView cVw3 = findViewById(R.id.cVw3);
        final TextView cVw4 = findViewById(R.id.cVw4);
        final TextView cVw5 = findViewById(R.id.cVw5);
        final TextView cVw6 = findViewById(R.id.cVw6);
        final TextView cVw7 = findViewById(R.id.cVwy);
        final TextView cVwx = findViewById(R.id.cVwx);
        final TextView cTxt1 = findViewById(R.id.cTxt1);
        final TextView cTxt2 = findViewById(R.id.cTxt2);
        final TextView cTxt3 = findViewById(R.id.cTxt3);
        final TextView cTxt4 = findViewById(R.id.cTxt4);
        final TextView cTxt5 = findViewById(R.id.cTxt5);
        final TextView cTxt6 = findViewById(R.id.cTxt6);
        final TextView cTxt7 = findViewById(R.id.cTxty);
        final TextView cTxtx = findViewById(R.id.cTxtx);
        final TextView solutionVol = findViewById(R.id.solutionVol);
        final TextView c1 = findViewById(R.id.c1);
        final TextView c2 = findViewById(R.id.c2);
        final TextView c3 = findViewById(R.id.c3);
        final TextView c4 = findViewById(R.id.c4);
        final TextView c5 = findViewById(R.id.c5);
        final TextView c6 = findViewById(R.id.c6);
        final TextView c7 = findViewById(R.id.cy);
        final TextView Unit = findViewById(R.id.unit);
        final TextView InpHead = findViewById(R.id.proton);
        //final TextView[] Nvws = {cVw1, cTxt1, c1, cVw2, cTxt2, c2, cVw3, cTxt3, c3, cVw4, cTxt4, c4, cVw5, cTxt5, c5, cVw6, cTxt6, c6, cVw7, cTxt7, c7, cVwx, cTxtx, solutionVol};
        final ConstraintLayout mainLayout = findViewById(R.id.layoutaa);
        final int choice = Integer.parseInt(getIntent().getStringExtra("buttonClicked"));

        //LISTA IDE TU DOLE
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

        //i sad kao napravis varijablu za broj itema na jednoj stranici(u jednom objektu jel)
        //private var list-length ili kak vec
        //iz nje odredis kolko bus prikazal tih text-viewa i samo promjenis cl(i) text-view u [i] iz liste
        //a broj objekta povuci iz broja stranice(gumba jel)
        //napisal bi ja al neznam javu
        // btw gumbe mozes isto u xml visibility: gone i dok izracunas sve stavis ih na visible
        //DO TUD

        ///evo i gumba
        final Button openMe = findViewById(R.id.openme);
        openMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                otvoriPopup();
            }
        });




        u1 = new User(choice, Nvws);
        Rst(Nvws);
        Unit.setVisibility(View.INVISIBLE);
        if(choice==0){
            u1.ent=true;
        } else{
            InpHead.setText("Overall acid concentration:");
            Unit.setVisibility(View.VISIBLE);
            pHVw.setText("--");
            solutionVol.setText("--");
            cTxtx.setText("--");
            nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            AcidBaseSw.setEnabled(false);
            TitTxt.setEnabled(false);
            VolTitTxt.setEnabled(false);
            TitBtn.setEnabled(false);
            for(int i = 0; i < rBtnz.getChildCount(); i++){
                rBtnz.getChildAt(i).setEnabled(false);
            }
        }

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // A menu with iterator User.itt; takes inputs (depending on the iterator) and displays the results
                if(u1.ent) {
                    switch (u1.itt) {
                        case 0: // saving n; prepare for cu
                            System.out.println(129);
                            InpHead.setText("Overall acid concentration:");
                            pHVw.setText("--");
                            solutionVol.setText("--");
                            cTxtx.setText("--");
                            nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            AcidBaseSw.setEnabled(false);
                            TitTxt.setEnabled(false);
                            VolTitTxt.setEnabled(false);
                            TitBtn.setEnabled(false);
                            for(int i = 0; i < rBtnz.getChildCount(); i++){
                                rBtnz.getChildAt(i).setEnabled(false);
                            }
                            try {
                                u1.n = Integer.parseInt(nTxt.getText().toString());
                                Rst(Nvws);
                                if (u1.n == 0) {
                                    Snackbar.make(mainLayout, "Your acid is not acidic.", Snackbar.LENGTH_LONG).show();
                                }
                                if (u1.n > 6 || u1.n < 0) { // math limit; no point to calculate polyprotic acid solutions
                                    Snackbar.make(mainLayout, "DeBuff supports n€<0,7>", Snackbar.LENGTH_LONG).show();
                                    u1.itt--;
                                    nTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    InpHead.setText("Number of acidic protons:");
                                } else {
                                    Unit.setVisibility(View.VISIBLE);
                                    Unit.setText("M");
                                }
                            } catch (Exception e) {
                                InpHead.setText("Number of acidic protons:");
                                nTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                                if (nTxt.getText().toString().equals(".")) {
                                    Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                                }
                                u1.itt--;
                            }
                            break;
                        case 1: // saving cu; prepare for cn
                            System.out.println(166);
                            InpHead.setText("Overall cation concentration:");
                            nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            try {
                                u1.cu = Float.parseFloat(nTxt.getText().toString());
                                if (u1.cu > 100) {
                                    Snackbar.make(mainLayout, "Concentrated solutions tend to differ from mathematical model.", Snackbar.LENGTH_LONG).show();
                                    nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                    u1.itt--;
                                    InpHead.setText("Overall acid concentration:");
                                }
                            } catch (Exception e) {
                                InpHead.setText("Overall acid concentration:");
                                nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                if (nTxt.getText().toString().equals(".")) {
                                    Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                                }
                                u1.itt--;
                            }
                            break;
                        default:
                            System.out.println(189);
                            InpHead.setText("Equilibrium constant pKa" + String.valueOf(u1.itt - 1) + ":");
                            if (u1.itt == 2) {  // saving cn; prepare for pKas
                                try {
                                    u1 = new User(u1.cu, Float.parseFloat(nTxt.getText().toString()), u1.n, 2, u1.citt);
                                    if (u1.cn > 100 || u1.cn<-1) {
                                        Snackbar.make(mainLayout, "Concentrated solutions tend to differ from mathematical model.", Snackbar.LENGTH_LONG).show();
                                        u1.itt--;
                                        InpHead.setText("Overall cation concentration:");
                                        break;
                                    } else {
                                        Unit.setVisibility(View.INVISIBLE);
                                    }
                                } catch (Exception e) {
                                    InpHead.setText("Overall cation concentration:");
                                    if (nTxt.getText().toString().equals(".")) {
                                        Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                                    } else {
                                        Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                                    }
                                    u1.itt--;
                                }
                            } else {
                                if (u1.itt == u1.n + 3) {
                                    System.out.println(213);
                                    InpHead.setText("Number of acidic protons:");
                                    nTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                                    Unit.setVisibility(View.INVISIBLE);
                                    try {
                                        u1.V = Float.parseFloat(nTxt.getText().toString());
                                        if (u1.V > 2000) {
                                            Snackbar.make(mainLayout, "Do you really need more than 2L?", Snackbar.LENGTH_LONG).show();
                                            u1.itt--;
                                            InpHead.setText("Solution volume:");
                                            break;
                                        } else {
                                            Unit.setVisibility(View.INVISIBLE);
                                        }
                                    } catch (Exception e) {
                                        InpHead.setText("Solution volume:");
                                        Unit.setVisibility(View.VISIBLE);
                                        Unit.setText("ml");
                                        nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                        u1.itt--;
                                        if (nTxt.getText().toString().equals(".")) {
                                            Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                                        }
                                        break;
                                    }
                                    s1 = new Solution(u1);
                                    p1 = new Poly(s1.GetDic());
                                    u1.Outs(Nvws, s1.GetComps()[0]);
                                    pHVw.setText(s1.mainfunc(p1, u1.txts));
                                    u1.itt = -1;
                                    u1.citt++;
                                    Unit.setVisibility(View.INVISIBLE);
                                    AcidBaseSw.setEnabled(true);
                                    TitTxt.setEnabled(true);
                                    VolTitTxt.setEnabled(true);
                                    TitBtn.setEnabled(true);
                                    for(int i = 0; i < rBtnz.getChildCount(); i++){
                                        rBtnz.getChildAt(i).setEnabled(true);
                                    }
                                } else { // saving a pKa; prepare for next one/v
                                    if (u1.itt == u1.n + 2){
                                        System.out.println(253);
                                        try {
                                            u1.K[u1.itt - 2] = Math.pow(10, -Float.parseFloat(nTxt.getText().toString()));
                                            if (u1.K[u1.itt - 2] < Math.pow(10, -35) || u1.K[u1.itt - 2] > Math.pow(10, 35)) {
                                                Snackbar.make(mainLayout, "Constant too extreme.", Snackbar.LENGTH_LONG).show();
                                                u1.itt--;
                                                InpHead.setText("Equilibrium constant pKa" + String.valueOf(u1.itt - 1) + ":");
                                            }
                                        } catch (Exception e) {
                                            if (nTxt.getText().toString().equals(".")) {
                                                Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                                            } else {
                                                Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                                            }
                                            u1.itt--;
                                        }
                                        if (u1.citt == 0){
                                            System.out.println(270);
                                            InpHead.setText("Solution volume:");
                                            Unit.setVisibility(View.VISIBLE);
                                            Unit.setText("ml");
                                            nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);}
                                        else{
                                            System.out.println(276);
                                            InpHead.setText("Number of acidic protons:");
                                            nTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                                            Unit.setVisibility(View.INVISIBLE);

                                            s1.AddComp(u1);
                                            p1 = new Poly(s1.GetDic());
                                            u1.Outs(Nvws, s1.GetComps()[0]);
                                            pHVw.setText(s1.mainfunc(p1, u1.txts));
                                            u1.itt = -1;
                                            u1.citt++;
                                            Unit.setVisibility(View.INVISIBLE);
                                            AcidBaseSw.setEnabled(true);
                                            TitTxt.setEnabled(true);
                                            VolTitTxt.setEnabled(true);
                                            TitBtn.setEnabled(true);
                                            for(int i = 0; i < rBtnz.getChildCount(); i++){
                                                rBtnz.getChildAt(i).setEnabled(true);
                                            }
                                        }
                                    }
                                }
                            }
                            break;
                    }
                    u1.itt++;
                    nTxt.setText("");

                } else{
                    switch (u1.itt) {
                        case 0: // saving cu; prepare for cn
                            System.out.println(304);
                            InpHead.setText("Overall cation concentration:");
                            nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            try {
                                u1.cu = Float.parseFloat(nTxt.getText().toString());
                                if (u1.cu > 100) {
                                    Snackbar.make(mainLayout, "Concentrated solutions tend to differ from mathematical model.", Snackbar.LENGTH_LONG).show();
                                    nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                    u1.itt--;
                                    InpHead.setText("Overall acid concentration:");
                                }
                            } catch (Exception e) {
                                InpHead.setText("Overall acid concentration:");
                                nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                if (nTxt.getText().toString().equals(".")) {
                                    Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                                }
                                u1.itt--;
                            }
                            break;
                        case 1:
                            System.out.println(327);
                            try {
                                u1.cn = Float.parseFloat(nTxt.getText().toString());
                                if (u1.cn > 100 && u1.cn<-1) {
                                    Snackbar.make(mainLayout, "Concentrated solutions tend to differ from mathematical model.", Snackbar.LENGTH_LONG).show();
                                    u1.itt--;
                                    InpHead.setText("Overall cation concentration:");
                                    nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                } else {
                                    Unit.setText("ml");
                                }
                            } catch (Exception e) {
                                InpHead.setText("Overall cation concentration:");
                                u1.itt--;
                                nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                if (nTxt.getText().toString().equals(".")) {
                                    Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                                }
                            }
                            if (u1.citt == 0) {
                                System.out.println(349);
                                InpHead.setText("Solution volume:");
                                nTxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                                Unit.setVisibility(View.VISIBLE);
                                Unit.setText("ml");
                            } else{
                                System.out.println(355);
                                s1.AddComp(u1);
                                p1 = new Poly(s1.GetDic());
                                u1.Outs(Nvws, s1.GetComps()[0]);
                                pHVw.setText(s1.mainfunc(p1, u1.txts));
                                u1.itt = -1;
                                u1.ent = true;
                                u1.citt++;
                                AcidBaseSw.setEnabled(true);
                                TitTxt.setEnabled(true);
                                VolTitTxt.setEnabled(true);
                                TitBtn.setEnabled(true);
                                for(int i = 0; i < rBtnz.getChildCount(); i++){
                                    rBtnz.getChildAt(i).setEnabled(true);
                                }
                                break;
                            }

                            break;
                        case 2:
                            System.out.println(372);
                            InpHead.setText("Number of acidic protons:");
                            nTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                            try {
                                u1.V = Float.parseFloat(nTxt.getText().toString());
                                if (u1.V > 2000) {
                                    Snackbar.make(mainLayout, "Do you really need more than 2L?", Snackbar.LENGTH_LONG).show();
                                    u1.itt--;
                                    InpHead.setText("Solution volume:");
                                    break;
                                } else {
                                    Unit.setVisibility(View.INVISIBLE);
                                }
                            } catch (Exception e) {
                                InpHead.setText("Solution volume:");
                                u1.itt--;
                                if (nTxt.getText().toString().equals(".")) {
                                    Snackbar.make(mainLayout, "Invalid input.", Snackbar.LENGTH_LONG).show();
                                } else {
                                    Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                                }
                                break;
                            }
                            s1 = new Solution(u1);
                            p1 = new Poly(s1.GetDic());
                            u1.Outs(Nvws, s1.GetComps()[0]);
                            pHVw.setText(s1.mainfunc(p1, u1.txts));
                            u1.itt = -1;
                            u1.ent = true;
                            u1.citt++;
                            AcidBaseSw.setEnabled(true);
                            TitTxt.setEnabled(true);
                            VolTitTxt.setEnabled(true);
                            TitBtn.setEnabled(true);
                            for(int i = 0; i < rBtnz.getChildCount(); i++){
                                rBtnz.getChildAt(i).setEnabled(true);
                            }
                            break;
                    }
                    u1.itt++;
                    nTxt.setText("");
                }
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
                        pHVw.setText(s1.mainfunc(p1, u1.txts));
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
                System.out.println("kliklik");
                int id = rBtnz.getCheckedRadioButtonId();
                try {
                    switch(id){
                        case  R.id.rBtn0:
                            Rst(Nvws);
                            u1.Outs(Nvws, s1.GetComps()[0]);
                            s1.GetComps()[0].ConcPrt(Nvws);
                            break;
                        case  R.id.rBtn1:
                            Rst(Nvws);
                            u1.Outs(Nvws, s1.GetComps()[1]);
                            s1.GetComps()[1].ConcPrt(Nvws);
                            break;
                        case  R.id.rBtn2:
                            Rst(Nvws);
                            u1.Outs(Nvws, s1.GetComps()[2]);
                            s1.GetComps()[2].ConcPrt(Nvws);
                            break;
                        case  R.id.rBtn3:
                            Rst(Nvws);
                            u1.Outs(Nvws, s1.GetComps()[3]);
                            s1.GetComps()[3].ConcPrt(Nvws);
                            break;
                    }
                } catch (Exception e){
                    Snackbar.make(mainLayout, "An error occurred.", Snackbar.LENGTH_LONG).show();
                    rBtn0.setChecked(true);
                }
            }
        });
    }


    public void Rst(TextView[] txts){
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

        //OVDI POCINJE DIALOG

    public void otvoriPopup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Title...");

        Button dialogButton = (Button) dialog.findViewById(R.id.okbtn);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}