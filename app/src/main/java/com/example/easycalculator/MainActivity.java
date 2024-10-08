package com.example.easycalculator;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.Stack;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resulTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonEqual, buttonMinus;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        resulTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);


        assignId(buttonC, R.id.buttons_c);
        assignId(buttonBrackOpen, R.id.buttons_open_bracket);
        assignId(buttonBrackClose, R.id.buttons_close_bracket);
        assignId(buttonDivide, R.id.buttons_divide);
        assignId(buttonMultiply, R.id.buttons_multiply);
        assignId(buttonPlus, R.id.buttons_plus);
        assignId(buttonEqual, R.id.buttons_equal);
        assignId(buttonMinus, R.id.buttons_minus);
        assignId(button0, R.id.buttons_0);
        assignId(button1, R.id.buttons_1);
        assignId(button2, R.id.buttons_2);
        assignId(button3, R.id.buttons_3);
        assignId(button4, R.id.buttons_4);
        assignId(button5, R.id.buttons_5);
        assignId(button6, R.id.buttons_6);
        assignId(button7, R.id.buttons_7);
        assignId(button8, R.id.buttons_8);
        assignId(button9, R.id.buttons_9);
        assignId(buttonAC, R.id.buttons_ac);
        assignId(buttonDot, R.id.buttons_dot);


    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resulTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTv.setText(resulTv.getText());
            return;
        }
        if (buttonText.equals("C")) {
            dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);

        } else {
            dataToCalculate = dataToCalculate + buttonText;
        }
        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Err")) {
            resulTv.setText(finalResult);
        }
        solutionTv.setText(dataToCalculate);

    }

    private String getResult(String data) {
        try {
            if (data.isEmpty()) {
                return "Err";
            }

            Stack<Double> numbers = new Stack<>();
            Stack<Character> operators = new Stack<>();

            int i = 0;
            while (i < data.length()) {
                char c = data.charAt(i);


                if (c == ' ') {
                    i++;
                    continue;
                }


                if (Character.isDigit(c)) {
                    StringBuilder sb = new StringBuilder();
                    while (i < data.length() && (Character.isDigit(data.charAt(i)) || data.charAt(i) == '.')) {
                        sb.append(data.charAt(i++));
                    }
                    numbers.push(Double.parseDouble(sb.toString()));
                    continue;
                }

                if (c == '+' || c == '-' || c == '*' || c == '/') {
                    while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                        numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                    }
                    operators.push(c);
                }
                i++;
            }


            while (!operators.isEmpty()) {
                numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
            }


            double result = numbers.pop();
            return String.valueOf(result);

        } catch (Exception e) {
            return "Err";
        }
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }


    private double applyOperation(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new ArithmeticException("Cannot divide by zero");
                }
                return a / b;
            default:
                return 0;
        }
    }


}