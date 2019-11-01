package com.gfg.liszt.debuff_acidbasesimulator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{
    public transient User u1;
    private Poly p1;
    private Solution s1;
    private TextView[] Nvws;
    private FloatingActionButton fab;
    private EditText TitTxt;
    private EditText VolTitTxt;
    private Button TitBtn;
    private RadioGroup rBtnGrp;
    private Switch AcidBaseSw;
    private TextView pHVw;
    private final static int REQUEST_CODE_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        //final int choice = Integer.parseInt(getIntent().getStringExtra("buttonClicked"));
        fab = findViewById(R.id.fab);
        TitTxt = findViewById(R.id.TitTxt);
        VolTitTxt = findViewById(R.id.VolTitTxt);
        TitBtn = findViewById(R.id.TitBtn);
        rBtnGrp = findViewById(R.id.rBtnGrp);
        AcidBaseSw = findViewById(R.id.AcidBaseSw);
        pHVw = findViewById(R.id.pHVw);
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
        Nvws = new TextView[] {cVw1, cTxt1, m1, cVw2, cTxt2, m2, cVw3, cTxt3, m3, cVw4, cTxt4, m4, cVw5, cTxt5, m5, cVw6, cTxt6, m6, cVw7, cTxt7, m7, cVwx, cTxtX, solutionVol};
        u1 = new User(0, 0);
        s1 = new Solution();

        /*u1 = new User(choice, Nvws);
        Rst(Nvws);
        if(choice==0){
            u1.ent = true;
        }
        else{
            pHVw.setText("--");
            solutionVol.setText("--");
            cTxtX.setText("--");
            AcidBaseSw.setEnabled(false);
            TitTxt.setEnabled(false);
            VolTitTxt.setEnabled(false);
            TitBtn.setEnabled(false);
            for(int i = 0; i < rBtnGrp.getChildCount(); i++){
                rBtnGrp.getChildAt(i).setEnabled(false);
            }
        }*/

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddComponentActivity.class);
                intent.putExtra("Solution", s1);
                startActivityForResult(intent, REQUEST_CODE_1);
            }
        });

        TitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ((TextInputLayout) (TitTxt.getParent()).getParent()).setError(null);
                    ((TextInputLayout) (VolTitTxt.getParent()).getParent()).setError(null);
                    int id = rBtnGrp.getCheckedRadioButtonId();
                    s1.l = Float.parseFloat(TitTxt.getText().toString());
                    if (Math.abs(s1.l) > 50) {
                        ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Value out of range!");
                        if (Float.parseFloat(VolTitTxt.getText().toString())>2000) ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Value out of range!");
                    } else {
                        if (Float.parseFloat(VolTitTxt.getText().toString())>2000) ((TextInputLayout) (TitTxt.getParent()).getParent()).setError("Value out of range!");
                        else{
                            s1.Titrate(Float.parseFloat(VolTitTxt.getText().toString()), AcidBaseSw);
                            p1 = new Poly(s1.GetDic());
                            pHVw.setText(s1.MainFunction(p1));
                            u1.PrepareOutputs(Nvws, s1.GetComps().get(s1.Entered.indexOf(RButt(id))));
                            s1.GetComps().get(s1.Entered.indexOf(RButt(id))).PrintConcentrations(Nvws, s1.GetCn());
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

        rBtnGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                if (id!=-1){
                    try {
                        if (s1.Entered.contains(RButt(id))){
                            Rst(Nvws);
                            u1.PrepareOutputs(Nvws, s1.GetComps().get(s1.Entered.indexOf(RButt(id))));
                            s1.GetComps().get(s1.Entered.indexOf(RButt(id))).PrintConcentrations(Nvws, s1.GetCn());
                        }
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                        rBtnGrp.clearCheck();
                        ((RadioButton) rBtnGrp.getChildAt(s1.Entered.get(0))).setChecked(true);
                    }
                }
            }
        });
        fab.performClick();
    }

    // This method is invoked when target activity return result data back.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.
        // This request code is set by startActivityForResult(intent, REQUEST_CODE_1) method.
        // String messageReturn = dataIntent.getStringExtra("message_return");

        if (requestCode == REQUEST_CODE_1) {
            System.out.println("Resumed");
            if (resultCode == RESULT_OK) {
                s1 = dataIntent.getParcelableExtra("Solution");
                System.out.println("Entered indexes");
                for (int el : s1.Entered) System.out.println(el);
                p1 = new Poly(s1.GetDic());
            }
            AcidBaseSw.setEnabled(true);
            TitTxt.setEnabled(true);
            VolTitTxt.setEnabled(true);
            TitBtn.setEnabled(true);
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
                Rst(Nvws);
                pHVw.setText(s1.MainFunction(p1));
            }
            rBtnGrp.clearCheck();
            if (ch != -1) {
                rBtnGrp.getChildAt(ch).setEnabled(true);
                rBtnGrp.check(rBtnGrp.getChildAt(ch).getId());
            }
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