package com.example.fencecalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener,
        CompoundButton.OnCheckedChangeListener {
    EditText etNumSides, etSide1, etSide2, etSide3, etSide4, etSectionLength, etGateWidth;
    //Создаем массивы ID для цикла for
    int[] etSidesId = new int[]{R.id.etSide1, R.id.etSide2, R.id.etSide3, R.id.etSide4};
    int[] tvSidesId = new int[]{R.id.tvSide1, R.id.tvSide2, R.id.tvSide3, R.id.tvSide4};
    Button btnGetResult;
    TextView tvSide1, tvSide2, tvSide3, tvSide4, tvGateWidth;
    Switch swDoor, swGate, swClosedFence;
    boolean isClosedFence = false, hasDoor = false, hasGate = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "Установка цен");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            startActivity(new Intent(this, PriceActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Находим элементы
        etNumSides = findViewById(R.id.etNumSides);
        etNumSides.setOnFocusChangeListener(this);
        etSide1 = findViewById(R.id.etSide1);
        etSide1.setOnFocusChangeListener(this);
        etSide2 = findViewById(R.id.etSide2);
        etSide2.setOnFocusChangeListener(this);
        etSide3 = findViewById(R.id.etSide3);
        etSide3.setOnFocusChangeListener(this);
        etSide4 = findViewById(R.id.etSide4);
        etSide4.setOnFocusChangeListener(this);
        etSectionLength = findViewById(R.id.etSectionLength);
        etGateWidth = findViewById(R.id.etGateWidth);


        tvSide1 = findViewById(R.id.tvSide1);
        tvSide2 = findViewById(R.id.tvSide2);
        tvSide3 = findViewById(R.id.tvSide3);
        tvSide4 = findViewById(R.id.tvSide4);
        tvGateWidth = findViewById(R.id.tvGateWidth);


        swClosedFence = findViewById(R.id.swClosedFence);
        swClosedFence.setOnCheckedChangeListener(this);

        swDoor = findViewById(R.id.swDoor);
        swDoor.setOnCheckedChangeListener(this);

        swGate = findViewById(R.id.swGate);
        swGate.setOnCheckedChangeListener(this);


        btnGetResult = findViewById(R.id.btnGetResult);
        btnGetResult.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnGetResult) {
            Intent intent = new Intent(this, ResultActivity.class);
            intent.putExtra("numSides", Integer.parseInt(etNumSides.getText().toString()));
            try {
                intent.putExtra("side1", Float.parseFloat(etSide1.getText().toString()));
                intent.putExtra("side2", Float.parseFloat(etSide2.getText().toString()));
                intent.putExtra("side3", Float.parseFloat(etSide3.getText().toString()));
                intent.putExtra("side4", Float.parseFloat(etSide4.getText().toString()));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Введите все длины сторон", Toast.LENGTH_SHORT).show();
                return;
            }
            intent.putExtra("isClosedFence", isClosedFence);
            intent.putExtra("sectionLength", Float.parseFloat(etSectionLength.getText().toString()));
            intent.putExtra("hasDoor", hasDoor);
            intent.putExtra("hasGate", hasGate);
            intent.putExtra("gateWidth", Float.parseFloat(etGateWidth.getText().toString()));
            startActivity(intent);
        }
    }

    public void disableSide(int numberSides) {
        for (int i = numberSides; i < etSidesId.length; i++) {
            //Отключаем 2,3,4 стороны
            findViewById(etSidesId[i]).setEnabled(false);
            findViewById(etSidesId[i]).setFocusable(false);
            EditText etSide = findViewById(etSidesId[i]);
            etSide.setText("0");
            findViewById(tvSidesId[i]).setEnabled(false);
            Log.e("Mytag", "Side " + (i + 1) + " disabled");
        }
    }

    public void enableSide(int numberSides) {
        for (int i = 1; i < numberSides; i++) {
            //Включаем стороны от 2й до i
            findViewById(etSidesId[i]).setEnabled(true);
            findViewById(etSidesId[i]).setFocusableInTouchMode(true);
            findViewById(tvSidesId[i]).setEnabled(true);
            Log.e("Mytag", "Side " + (i + 1) + " enabled");
        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        try {
            //Проверяем длину сторон при потере фокуса
            if (!hasFocus) switch (view.getId()) {
                case R.id.etNumSides:
                    //Проверяем и отключаем лишние поля при заданном количестве сторон
                    switch (Integer.valueOf(etNumSides.getText().toString())) {
                        case 1:
                            disableSide(1);
                            swClosedFence.setEnabled(false);
                            break;
                        case 2:
                            disableSide(2);
                            enableSide(2);
                            swClosedFence.setEnabled(false);
                            break;
                        case 3:
                            disableSide(3);
                            enableSide(3);
                            swClosedFence.setEnabled(true);
                            break;
                        case 4:
                            enableSide(4);
                            swClosedFence.setEnabled(true);
                            break;
                        default:
                            Toast.makeText(this, "Введите количество сторон, от 1 до 4", Toast.LENGTH_SHORT).show();
                            break;
                    }
            }
            else {
                //Проферяем длины сторон при получении фокуса
                switch (view.getId()) {
                    case R.id.etSide1:
                        //очищаем поле, если длина стороны равна нулю
                        if (etSide1.getText().toString().equals("0")) {
                            etSide1.getText().clear();
                        }
                        break;
                    case R.id.etSide2:
                        if (etSide2.getText().toString().equals("0")) {
                            etSide2.getText().clear();
                        }
                        break;
                    case R.id.etSide3:
                        if (etSide3.getText().toString().equals("0")) {
                            etSide3.getText().clear();
                        }
                        break;
                    case R.id.etSide4:
                        if (etSide4.getText().toString().equals("0")) {
                            etSide4.getText().clear();
                        }
                        break;
                }
            }

        } catch (
                NumberFormatException e)

        {
            Toast.makeText(this, "Введите количество сторон, от 1 до 4", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    //Проверка состояния Switch переключателей
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            //Замкнут ли периметр
            case R.id.swClosedFence:
                isClosedFence = isChecked;
                break;
            //Есть ли калитка
            case R.id.swDoor:
                hasDoor = isChecked;
                break;
            //Есть ли ворота
            case R.id.swGate:
                hasGate = isChecked;
                etGateWidth.setFocusableInTouchMode(isChecked);
                etGateWidth.setEnabled(isChecked);
                tvGateWidth.setEnabled(isChecked);
                break;
            default:
                break;
        }
    }
}
