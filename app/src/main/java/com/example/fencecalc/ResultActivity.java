package com.example.fencecalc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import static com.example.fencecalc.PriceActivity.PREFERENCES;
import static com.example.fencecalc.PriceActivity.PRICE_40x20;
import static com.example.fencecalc.PriceActivity.PRICE_60x60;
import static com.example.fencecalc.PriceActivity.PRICE_80x80;
import static com.example.fencecalc.PriceActivity.PRICE_PROF_SHEET;


public class ResultActivity extends AppCompatActivity {
    SharedPreferences preferences;

    TextView tvNum60x60, tvNum80x80, tvNum40x20, tvNumProfSheet, tvSum60x60, tvSum80x80, tvSum40x20,
            tvSumProfSheet, tvSum, tvHeader;

    private int numSides, num60x60 = 0, num80x80 = 0, num40x20 = 0, numProfSheet = 0;
    protected float side1, side2, side3, side4, sectionLength, length40x20, fenceLength, sum60x60, sum80x80, sum40x20,
            sumProfSheet, gateWidth;
    boolean isClosedFence, hasDoor, hasGate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //находим элементы
        tvNum60x60 = findViewById(R.id.tvNum60x60);
        tvNum80x80 = findViewById(R.id.tvNum80x80);
        tvNum40x20 = findViewById(R.id.tvNum40x20);
        tvNumProfSheet = findViewById(R.id.tvNumProfSheet);

        tvSum60x60 = findViewById(R.id.tvSum60x60);
        tvSum80x80 = findViewById(R.id.tvSum80x80);
        tvSum40x20 = findViewById(R.id.tvSum40x20);
        tvSumProfSheet = findViewById(R.id.tvSumProfSheet);
        tvSum = findViewById(R.id.tvSum);

        tvHeader = findViewById(R.id.tvHeader);

        //Получаем Extra из Intent
        Intent intent = getIntent();
        numSides = intent.getIntExtra("numSides", 1);
        side1 = intent.getFloatExtra("side1", 0);
        side2 = intent.getFloatExtra("side2", 0);
        side3 = intent.getFloatExtra("side3", 0);
        side4 = intent.getFloatExtra("side4", 0);
        isClosedFence = intent.getBooleanExtra("isClosedFence", false);
        sectionLength = intent.getFloatExtra("sectionLength", 3);
        hasDoor = intent.getBooleanExtra("hasDoor", false);
        hasGate = intent.getBooleanExtra("hasGate", false);
        gateWidth = intent.getFloatExtra("gateWidth", 0);

        // Устанавливаем заголовок
        fenceLength = side1 + side2 + side3 + side4;
        tvHeader.setText("Расчёт ограждения из профнастила длиной "+fenceLength+" м.");

        preferences = getSharedPreferences(PREFERENCES, MODE_PRIVATE);


        tvNum60x60.setText(calc60х60() + " шт.");
        tvNum80x80.setText(calc80х80() + " шт.");
        tvNum40x20.setText(calc40х20() + " шт.");
        tvNumProfSheet.setText(calcProfSheet() + " шт.");

        sum60x60 = calc60х60() * Float.parseFloat(preferences.getString(PRICE_60x60, "1"));
        sum80x80 = calc80х80() * Float.parseFloat(preferences.getString(PRICE_80x80, "1"));
        sum40x20 = calc40х20() * Float.parseFloat(preferences.getString(PRICE_40x20, "1"));
        sumProfSheet = calcProfSheet() * Float.parseFloat(preferences.getString(PRICE_PROF_SHEET, "1"));

        tvSum60x60.setText(String.valueOf(sum60x60) + " руб.");
        tvSum80x80.setText(String.valueOf(sum80x80) + " руб.");
        tvSum40x20.setText(String.valueOf(sum40x20) + " руб.");
        tvSumProfSheet.setText(String.valueOf(sumProfSheet) + " руб.");
        tvSum.setText(String.valueOf(sum60x60 + sum80x80 + sum40x20 + sumProfSheet) + " руб.");
    }

    private int calcProfSheet() {
        numProfSheet = 0;
        if (hasDoor) {
            numProfSheet = numProfSheet + 1;
        }
        if (hasGate) {
            numProfSheet = numProfSheet + (int) Math.ceil(gateWidth / 2 / 1.15) * 2;
        }
        switch (numSides) {
            case 1:
                numProfSheet += (int) Math.ceil(side1 / 1.15);
                return numProfSheet;
            case 2:
                numProfSheet += (int) Math.ceil(side1 / 1.15) + (int) Math.ceil(side2 / 1.15);
                return numProfSheet;
            case 3:
                numProfSheet += (int) Math.ceil(side1 / 1.15) + (int) Math.ceil(side2 / 1.15) +
                        (int) Math.ceil(side3 / 1.15);
                return numProfSheet;
            case 4:
                numProfSheet += (int) Math.ceil(side1 / 1.15) + (int) Math.ceil(side2 / 1.15) +
                        (int) Math.ceil(side3 / 1.15) + (int) Math.ceil(side4 / 1.15);
                return numProfSheet;
            default:
                return 0;
        }
    }

    private int calc40х20() {
        length40x20 = ((side1 + side2 + side3 + side4) * 2);
        if (hasDoor) {
            length40x20 = length40x20 + 7;
        }
        if (hasGate) {
            length40x20 = (length40x20 + 8 + gateWidth * 2 + 2 * (float) Math.ceil(Math.sqrt(4 + Math.pow(gateWidth / 2, 2)))); //Расчёт для высоты 2м
        }
        num40x20 = (int) Math.ceil(length40x20 / 3);
        return num40x20;
    }

    private int calc60х60() {  //Производит расчёт столбов
        if (side1 <= 0) return 0;
        else {
            switch (numSides) {
                case 1:
                    num60x60 = (int) Math.ceil(side1 / sectionLength) + 1;
                    return num60x60;
                case 2:
                    num60x60 = (int) Math.ceil(side1 / sectionLength) + 1 + (int) Math.ceil(side2 / sectionLength);
                    return num60x60;
                case 3:
                    num60x60 = (int) Math.ceil(side1 / sectionLength) + (int) Math.ceil(side2 / sectionLength) +
                            (int) Math.ceil(side3 / sectionLength);
                    num60x60 = isClosedFence ? num60x60 : num60x60 + 1; //Если участок не замкнут, +1 столб
                    return num60x60;
                case 4:
                    num60x60 = (int) Math.ceil(side1 / sectionLength) + (int) Math.ceil(side2 / sectionLength) +
                            (int) Math.ceil(side3 / sectionLength) + (int) Math.ceil(side4 / sectionLength);
                    num60x60 = isClosedFence ? num60x60 : num60x60 + 1; //Если участок не замкнут, +1 столб
                    return num60x60;
                default:
                    return 0;
            }
        }
    }

    private int calc80х80() {
        if (hasDoor & hasGate) {
            num80x80 = 3;
        } else if (hasDoor | hasGate) {
            num80x80 = 2;
        } else num80x80 = 0;
        return num80x80;
    }

}
