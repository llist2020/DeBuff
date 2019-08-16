package com.gfg.liszt.debuff_acidbasesimulator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class chooseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_choose);

    final Button btn1 = findViewById(R.id.button);
    final Button btn2 = findViewById(R.id.button2);
    final Button btn3 = findViewById(R.id.button3);
    final Button btn4 = findViewById(R.id.button4);
    final Button btn5 = findViewById(R.id.button5);
    final Button btn6 = findViewById(R.id.button6);
    final Button btn7 = findViewById(R.id.button7);
    final Button btn8 = findViewById(R.id.button8);
    final Button btn9 = findViewById(R.id.button9);
    final Button btn10 = findViewById(R.id.button10);
    final Button btn11 = findViewById(R.id.button11);
    final Button btn12 = findViewById(R.id.button12);
    final Button btn13 = findViewById(R.id.button13);
    final Button btn14 = findViewById(R.id.button14);


    btn1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "0");
        startActivity(intent);

      }
    });

    btn2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "1");
        startActivity(intent);

      }
    });

    btn3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "2");
        startActivity(intent);

      }
    });

    btn4.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "3");
        startActivity(intent);

      }
    });

    btn5.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "4");
        startActivity(intent);

      }
    });

    btn6.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "5");
        startActivity(intent);

      }
    });

    btn7.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "6");
        startActivity(intent);

      }
    });

    btn8.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "7");
        startActivity(intent);

      }
    });

    btn9.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "8");
        startActivity(intent);

      }
    });

    btn10.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "9");
        startActivity(intent);

      }
    });

    btn11.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "10");
        startActivity(intent);

      }
    });

    btn12.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "11");
        startActivity(intent);

      }
    });

    btn13.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "12");
        startActivity(intent);

      }
    });

    btn14.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("buttonClicked", "13");
        startActivity(intent);

      }
    });

  }

  // create an action bar button
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_info, menu);
    return super.onCreateOptionsMenu(menu);
  }

  // handle button activities
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.infomenubtn) {
      AlertDialog.Builder builder;
      builder = new AlertDialog.Builder(this, 0);
      builder.setTitle("Welcome to DeBuff v1.5!")
              .setMessage("DeBuff is a simple acid-base simulator.\n" +
                      "You can choose an acid and study its properties or you can manually input acid data (which is easily available on the Internet).\n" +
                      "Afterwards, titration simulation is available.\n"+
                      "To keep the math going, some logical bounds have been set. For example, too concentrated solutions do not have a negligible ionic strength, which messes with equilibrium.\n\n" +
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
}
