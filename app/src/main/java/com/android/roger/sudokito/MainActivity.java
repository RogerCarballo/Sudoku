package com.android.roger.sudokito;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int contador = 0;
    List<Integer> sudokuFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Sudoku sudoku = new Sudoku(9, 10);
        sudokuFinal = sudoku.getSudokuResueltito();
        List<Integer> tablaSudoku = sudoku.obtenerSudoku();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TableLayout tl = findViewById(R.id.tableLayout);
        String[] datos = new String[10];
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, datos);
        DisplayMetrics metrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int w = metrics.widthPixels, h = metrics.heightPixels;
        for (int i = 0; i < datos.length; i++) {
            datos[i] = i + "";
        }

        final List<Spinner> spinners = new ArrayList<Spinner>();
        for (int i = 0; i < 9; i++) {
            TableRow tr = new TableRow(getApplicationContext());
            for (int j = 0; j < 9; j++) {
                final Spinner sp = new Spinner(getApplicationContext());

                spinners.add(sp);
                sp.setLayoutParams(new TableRow.LayoutParams(w / 9, h / 10));
                sp.setPadding(5, 5, 5, 5);
                sp.setBackground(null);
                sp.setAdapter(adapter);
                sp.setSelection(tablaSudoku.get(contador));
                spinners.get(contador).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 0; i<spinners.size();i++){
                            if (spinners.get(i)==sp && !sp.getSelectedItem().equals("0")){
                                    int numero = Integer.parseInt(sp.getSelectedItem().toString());
                                    if(numero == sudokuFinal.get(i)){
                                        sp.setEnabled(false);
                                    }
                            }
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                contador++;
                tr.addView(sp);

            }

            tl.addView(tr);

        }



    }
}