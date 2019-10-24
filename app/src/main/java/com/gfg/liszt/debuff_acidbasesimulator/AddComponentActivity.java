package com.gfg.liszt.debuff_acidbasesimulator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.Contract;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddComponentActivity extends AppCompatActivity {
    private Solution s1;
    private User u2;
    AlertDialog dialogA;
    int checkedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);
        System.out.println("AddCompAct");
        final Button ManBtn = findViewById(R.id.ManBtn);
        final Button AutoBtn = findViewById(R.id.AutoBtn);
        final Button SaveBtn = findViewById(R.id.SaveBtn);
        final Button NextBtn =  findViewById(R.id.NextBtn);
        final Button SlotBtn = findViewById(R.id.button15);
        final Switch AcidBaseSwInp = findViewById(R.id.AcidBaseSwInp);
        final EditText nTxt =  findViewById(R.id.nTxt);
        final EditText cuTxt =  findViewById(R.id.cuTxt);
        final EditText cnTxt =  findViewById(R.id.cnTxt);
        final EditText VTxt = findViewById(R.id.VTxt);
        final TextInputLayout KTxtIL = findViewById(R.id.KTxtIL);
        final EditText KTxt = findViewById(R.id.KTxt);
        final EditText[] ETs = {nTxt, cuTxt, cnTxt, VTxt};

        try{
            s1 = getIntent().getParcelableExtra("Solution");
            u2 = new User(0, s1.Slot());
            if (s1.Slot() != 0) {
                VTxt.setEnabled(false);
                VTxt.setText(String.format(Locale.US, "%.2f", s1.V));
            }// else if (choice == 0) AutoBtn.setEnabled(false);
            SaveBtn.setEnabled(false); // inace bi se mogla samo promjenit boja il nekaj
            AcidBaseSwInp.setEnabled(false);
            if (s1.Slot() != 0) u2.Valid[3] = 1;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //final int choice = Integer.parseInt(getIntent().getStringExtra("buttonClicked"));

        ManBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if (s1.Slot() != 0) {
                        VTxt.setEnabled(false);
                        VTxt.setText(String.format(Locale.US, "%.2f", s1.V));
                    }// else if (choice == 0) AutoBtn.setEnabled(false);
                    SaveBtn.setEnabled(false); // inace bi se mogla samo promjenit boja il nekaj
                    AcidBaseSwInp.setEnabled(false);
                } catch (Exception e){
                    System.out.println("eroor");
                    System.out.println(e.getMessage());
                }

                for (EditText el : ETs) el.setVisibility(View.VISIBLE);
                SaveBtn.setVisibility(View.VISIBLE);
                u2.ion = "A";
                u2.ent = true;
                nTxt.setEnabled(true);
                AcidBaseSwInp.setEnabled(true);
                AutoBtn.setEnabled(false);
                nTxt.setText("");
                if (!VTxt.getText().toString().matches("")) u2.Valid[3] = 1;
            }
        });

        AutoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // KOD ZA OTVARANJE DIALOGA VAMO
                for (EditText el : ETs) el.setVisibility(View.VISIBLE);
                SaveBtn.setVisibility(View.VISIBLE);
                nTxt.setEnabled(false);
                ManBtn.setEnabled(false);
                // privremeno
                AcidBaseSwInp.setChecked(!u2.acid);
                KTxt.setText(String.format(Locale.US, "%.2f", -Math.log10(u2.K[1])));
                nTxt.setText(String.format(Locale.US, "%s", u2.n));
                u2.ent = false;
            }
        });

        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] Inputs = ValidateInputs();
                int n;
                double cu, cn, V;
                if (Inputs.length != 0) { // if all inputs are valid
                    n = ((Double) Inputs[0]).intValue();
                    cu = Inputs[1];
                    cn = Inputs[2];
                    V = Inputs[3];
                    for (EditText el : ETs) el.setEnabled(false);
                    SaveBtn.setEnabled(false);
                    AcidBaseSwInp.setEnabled(false);

                    if (s1.Slot() == 0) {
                        if (!u2.ent) {
                            u2.V = V;
                            u2 = new User(n, cu, cn, u2);
                        } else u2 = new User(n, cu, cn, V, "Mjau", u2.slot);
                    } else {
                        if (u2.ent) u2 = new User(n, cu, cn, u2.V, "Mjau", u2.slot);
                        else u2 = new User(n, cu, cn, u2);
                    }
                    u2.SetAcid(!AcidBaseSwInp.isChecked());
                    for (EditText el : ETs) {
                        el.setEnabled(false);
                        el.setError(null);
                    }
                    ManBtn.setEnabled(false);
                    AutoBtn.setEnabled(false);
                    AcidBaseSwInp.setEnabled(false);
                    if (u2.n > 0) {
                        KTxt.setVisibility(View.VISIBLE);
                        NextBtn.setVisibility(View.VISIBLE);
                        NextBtn.performClick();
                    } else {
                        if (s1.Slot() == 0) s1 = new Solution(u2, AcidBaseSwInp.isChecked());
                        else if (s1.Slot() < 4)
                            s1.AddComp(u2, AcidBaseSwInp.isChecked()); // VAMO KOD ZA OVERWRITANJE

                        u2.itt = -1;

                        Intent intent = new Intent();
                        u2.ent = false;
                        intent.putExtra("Solution", s1);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                } else {
                    for (EditText el : ETs) el.setEnabled(true);
                    if (s1.Slot() != 0) {
                        VTxt.setEnabled(false);
                        VTxt.setText(String.format(Locale.US, "%.2f", u2.V));
                    }
                    SaveBtn.setEnabled(true);
                    KTxt.setVisibility(View.GONE);
                    NextBtn.setVisibility(View.GONE);
                }
            }
        });

        NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KTxtIL.setError(null);
                if (u2.itt != 0) {
                    try {
                        u2.K[u2.itt] = Math.pow(10, -Float.parseFloat(KTxt.getText().toString()));
                        if (u2.K[u2.itt] < Math.pow(10, -35) || u2.K[u2.itt] > Math.pow(10, 35)) {
                            u2.itt--;
                            KTxtIL.setError("Constant too extreme.");
                            KTxtIL.setHint("Equilibrium constant pK" + u2.a.charAt(0) + (u2.itt) + ":");
                        }
                    } catch (Exception e) {
                        if (KTxt.getText().toString().equals(".")) {
                            KTxtIL.setError("Invalid input.");
                        } else {
                            KTxtIL.setError("An error occurred.");
                        }
                        u2.itt--;
                    }

                    if (u2.itt == u2.n) {
                        if (s1.Slot() == 0) s1 = new Solution(u2, AcidBaseSwInp.isChecked());
                        else if (s1.Slot() < 4)
                            s1.AddComp(u2, AcidBaseSwInp.isChecked()); // VAMO KOD ZA OVERWRITANJE

                        u2.itt = -1;
                        Intent intent = new Intent();
                        u2.ent = false;
                        intent.putExtra("Solution", s1);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
                KTxtIL.setHint("Equilibrium constant pK" + u2.a.charAt(0) + (u2.itt + 1) + ":");
                u2.itt++;
                if (!u2.ent) {
                    try {
                        KTxt.setText(String.format(Locale.US, "%.2f", -Math.log10(u2.K[u2.itt])));
                    } catch (Exception e) {
                        // couldn't load a constant
                    }
                } else KTxt.setText("");
                if (u2.itt >= u2.n) NextBtn.setText(R.string.Go);
            }
        });

        AcidBaseSwInp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                u2.SetAcid(!isChecked);
                KTxtIL.setHint("Equilibrium constant pK" + u2.a.charAt(0) + (u2.itt) + ":");
                ((TextInputLayout) (cuTxt.getParent()).getParent()).setHint("Overall " + u2.a + " concentration:");
                if (isChecked)
                    ((TextInputLayout) (nTxt.getParent()).getParent()).setHint("Number of dissociable hydroxides:");
                else
                    ((TextInputLayout) (nTxt.getParent()).getParent()).setHint("Number of dissociable protons:");
            }
        });

        nTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (nTxt.getText().toString().matches("")) {
                    SaveBtn.setEnabled(false);
                    u2.Valid[0] = 0;
                } else u2.Valid[0] = 1;
                if (u2.AllInputsValid()) SaveBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        cuTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (cuTxt.getText().toString().matches("") && cuTxt.getText().toString().matches(".")) {
                    SaveBtn.setEnabled(false);
                    u2.Valid[1] = 0;
                } else u2.Valid[1] = 1;
                if (u2.AllInputsValid()) SaveBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        cnTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (cnTxt.getText().toString().matches("") && cnTxt.getText().toString().matches(".")) {
                    SaveBtn.setEnabled(false);
                    u2.Valid[2] = 0;
                } else u2.Valid[2] = 1;
                if (u2.AllInputsValid()) SaveBtn.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        VTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (cuTxt.getText().toString().matches("") && cuTxt.getText().toString().matches(".")) {
                    SaveBtn.setEnabled(false);
                    u2.Valid[3] = 0;
                } else {
                    u2.Valid[3] = 1;
                }
                if (u2.AllInputsValid()) SaveBtn.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        /*cnTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (s1.Slot() != 0) {
                        InputMethodManager inputManager = (InputMethodManager) AddComponentActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(cuTxt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    } else {
                        VTxt.requestFocus();
                    }
                    return true;
                }
                return false;
            }
        });*/

        SlotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // setup the alert builder
                AlertDialog.Builder builderA = new AlertDialog.Builder(AddComponentActivity.this);
                builderA.setTitle("Choose a slot to write on");

                // add a radio button list
                String[] slots = {"Slot 1", "Slot 2", "Slot 3", "Slot 4"};
                checkedItem = 0; // Slot 1
                builderA.setSingleChoiceItems(slots, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkedItem = which;
                    }
                });

                // add OK and Cancel buttons
                builderA.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            if (s1.Entered.contains(checkedItem)){
                                // setup the alert builder
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddComponentActivity.this);
                                builder.setTitle("Notice");
                                builder.setMessage("There is a component at the selected slot. Do you want to overwrite?");

                                builder.setPositiveButton("Overwrite", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        System.out.println("B "+checkedItem);
                                        u2.slot = checkedItem;
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialogA.show();
                                    }
                                });

                                // create and show the alert dialog
                                AlertDialog alert = builder.create();
                                alert.show();
                            } else{
                                u2.slot = checkedItem;
                            }
                        } catch(Exception e){
                            // nekej ne valja
                        }
                    }
                });
                builderA.setNegativeButton("Cancel", null);

                // create and show the alert dialog
                dialogA = builderA.create();
                dialogA.show();
            }
        });
    }

    @NonNull
    @Contract(" -> new")
    private double[] ValidateInputs() {
        final EditText[] ETs = new EditText[]{findViewById(R.id.nTxt), findViewById(R.id.cuTxt), findViewById(R.id.cnTxt), findViewById(R.id.VTxt)};
        for (EditText el : ETs) ((TextInputLayout) (el.getParent()).getParent()).setError(null);
        double[] out = new double[4];

        for (int i = 0; i < 4; i++) {
            try {
                out[i] = Double.parseDouble(ETs[i].getText().toString());
                if (i == 0) {
                    if (out[i] == 0.0) {
                        ((TextInputLayout) (ETs[0].getParent()).getParent()).setError("No dissociation will be observed!"); //potvrditi?; toast
                    }
                    if (out[i] > 6 || out[i] < 0) { // math limit;
                        ((TextInputLayout) (ETs[0].getParent()).getParent()).setError("DeBuff supports n€<0,7>!");
                        ETs[0].setText("");
                        return (new double[0]);
                    }
                } else if (i == 3) {
                    if (out[3] > 2000) {
                        ((TextInputLayout) (ETs[3].getParent()).getParent()).setError("Do you really need more than 2L?");
                        ETs[3].setText("");
                        return (new double[0]);
                    }
                } else {
                    if (out[i] > 100) {
                        ((TextInputLayout) (ETs[i].getParent()).getParent()).setError("Concentrated solutions tend to differ from the mathematical model!");
                        ETs[i].setText("");
                        return (new double[0]);
                    }
                }
            } catch (Exception e) {
                ((TextInputLayout) (ETs[i].getParent()).getParent()).setError("An error occurred!");
                ETs[i].setText("");
                return (new double[0]);
            }
        }
        return (out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
