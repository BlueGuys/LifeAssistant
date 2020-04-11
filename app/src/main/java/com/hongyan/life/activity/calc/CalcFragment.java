package com.hongyan.life.activity.calc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hongyan.life.R;
import com.hongyan.life.activity.BaseFragment;

import java.util.Stack;

public class CalcFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private LinearLayout roorLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_calc, container, false);
            roorLayout = view.findViewById(R.id.linearLayout);
            initView();
        }
        return view;
    }

    private void initView() {
        view.findViewById(R.id.fragment_calc_id_0).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_1).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_2).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_3).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_4).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_5).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_6).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_7).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_8).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_9).setOnClickListener(this);

        view.findViewById(R.id.fragment_calc_id_ac).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_equals).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_plus).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_minus).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_multiply).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_divide).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_dot).setOnClickListener(this);

        view.findViewById(R.id.fragment_calc_id_left).setOnClickListener(this);
        view.findViewById(R.id.fragment_calc_id_right).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int buttonId = v.getId();
        TextView textView = view.findViewById(R.id.fragment_calc_expression);//表达式窗口

        switch (buttonId) {
            case R.id.fragment_calc_id_ac:
                textView.setText("");
                break;
            case R.id.fragment_calc_id_equals:
                try {
                    if (textView.getText().toString().equals("")) {
                        textView.setText("0");
                    } else {
                        double answer = calculate(textView.getText().toString());
                        textView.setText(Double.toString(answer));
                    }
                } catch (Exception e) {
                    textView.setText("您输入的表达式有误！");
                }
                break;
            case R.id.fragment_calc_id_plus:
            case R.id.fragment_calc_id_minus:
            case R.id.fragment_calc_id_multiply:
            case R.id.fragment_calc_id_divide:
            case R.id.fragment_calc_id_dot:
                String[] symbles = new String[]{"＋", "－", "×", "÷", "."};
                boolean isSymble = false;
                for (String symble : symbles) {
                    if (textView.getText().toString().endsWith(symble)) {
                        isSymble = true;
                        break;
                    }
                }
                if (isSymble) {
                    String buttonText = ((Button) v).getText().toString();
                    String newText = textView.getText().toString();
                    newText = newText.substring(0, newText.length() - 1);
                    newText = newText + buttonText;
                    textView.setText(newText);
                } else {
                    String buttonText = ((Button) v).getText().toString();
                    String newText = textView.getText() + buttonText;
                    textView.setText(newText);
                }
                break;
            default:
                String buttonText = ((Button) v).getText().toString();
                String newText = textView.getText() + buttonText;
                textView.setText(newText);
                break;
        }

    }

    //计算带括号的表达式，从里往外计算
    private double calculate(String expression) throws Exception {
        Stack<Character> stack = new Stack<>();
        for (char ch : expression.toCharArray()) {
            if (ch != ')') {
                stack.push(ch);
            } else {
                StringBuffer simpleExpression = new StringBuffer("");
                while (stack.peek() != '(') {
                    simpleExpression.append(stack.pop());
                }
                //移除括号
                stack.pop();
                //计算不带括号的表达式
                double simpleAnswer = calculateWithoutBrackets(simpleExpression.reverse().toString());
                //将结果放进栈
                for (char temp : Double.toString(simpleAnswer).toCharArray()) {
                    stack.push(temp);
                }
            }
        }

        StringBuffer lastExpression = new StringBuffer("");
        for (char lastCh : stack) {
            lastExpression.append(lastCh);
        }

        double answer = calculateWithoutBrackets(lastExpression.toString());
        return answer;
    }

    //计算不带括号的表达式
    private double calculateWithoutBrackets(String expression) throws Exception {
        int multiplyIndex = expression.indexOf("×");
        int divideIndex = expression.indexOf("÷");
        int plusIndex = expression.indexOf("＋");
        int minusIndex = expression.indexOf("－");

        String newExpression = "";

        //当表达式中没有运算符了，就直接返回
        if (multiplyIndex == -1
                && divideIndex == -1
                && plusIndex == -1
                && minusIndex == -1) {
            return Double.parseDouble(expression);
        }

        //计算乘法，乘法除法都有的情况下从左往右
        if (multiplyIndex != -1
                && (divideIndex == -1 || multiplyIndex < divideIndex)) {
            //第一个乘法二目表达式
            String simpleExpression = findSimpleExpression(expression, multiplyIndex);
            //第一个乘法二目表达式的结果
            double simpleAnswer = simpleCalculate(simpleExpression);

            //将表达式替换为结果，继续往下计算
            newExpression = expression.replaceFirst(simpleExpression, Double.toString(simpleAnswer));
            return calculateWithoutBrackets(newExpression);
        }

        //计算除法，乘法除法都有的情况下从左往右
        if (divideIndex != -1
                && (multiplyIndex == -1 || divideIndex < multiplyIndex)) {
            //第一个除法二目表达式
            String simpleExpression = findSimpleExpression(expression, divideIndex);
            //第一个除法二目表达式的结果
            double simpleAnswer = simpleCalculate(simpleExpression);

            //将表达式替换为结果，继续往下计算
            newExpression = expression.replaceFirst(simpleExpression, Double.toString(simpleAnswer));
            return calculateWithoutBrackets(newExpression);
        }

        //计算加法，加法减法都有的情况下从左往右
        if (plusIndex != -1
                && (minusIndex == -1 || plusIndex < minusIndex)) {
            //第一个加法二目表达式
            String simpleExpression = findSimpleExpression(expression, plusIndex);
            //第一个加法二目表达式的结果
            double simpleAnswer = simpleCalculate(simpleExpression);

            //将表达式替换为结果，继续往下计算
            newExpression = expression.replaceFirst(simpleExpression, Double.toString(simpleAnswer));
            return calculateWithoutBrackets(newExpression);
        }

        //计算减法，加法减法都有的情况下从左往右
        if (minusIndex != -1
                && (plusIndex == -1 || minusIndex < plusIndex)) {
            //第一个减法二目表达式
            String simpleExpression = findSimpleExpression(expression, minusIndex);
            //第一个减法二目表达式的结果
            double simpleAnswer = simpleCalculate(simpleExpression);

            //将表达式替换为结果，继续往下计算
            newExpression = expression.replaceFirst(simpleExpression, Double.toString(simpleAnswer));
            return calculateWithoutBrackets(newExpression);
        }

        throw new Exception("计算错误！");
    }

    //计算二目运算
    private double simpleCalculate(String expression) {
        double answer = 0;
        String firstNum = "";
        String operator = "";
        String lastNum = "";

        if (expression.indexOf("＋") != -1) {
            operator = "＋";
            firstNum = expression.substring(0, expression.indexOf("＋"));
            lastNum = expression.substring(expression.indexOf("＋") + 1);
        } else if (expression.indexOf("－") != -1) {
            operator = "－";
            firstNum = expression.substring(0, expression.indexOf("－"));
            lastNum = expression.substring(expression.indexOf("－") + 1);
        } else if (expression.indexOf("×") != -1) {
            operator = "×";
            firstNum = expression.substring(0, expression.indexOf("×"));
            lastNum = expression.substring(expression.indexOf("×") + 1);
        } else if (expression.indexOf("÷") != -1) {
            operator = "÷";
            firstNum = expression.substring(0, expression.indexOf("÷"));
            lastNum = expression.substring(expression.indexOf("÷") + 1);
        }

        switch (operator) {
            case "＋":
                answer = Double.parseDouble(firstNum) + Double.parseDouble(lastNum);
                break;
            case "－":
                answer = Double.parseDouble(firstNum) - Double.parseDouble(lastNum);
                break;
            case "×":
                answer = Double.parseDouble(firstNum) * Double.parseDouble(lastNum);
                break;
            case "÷":
                answer = Double.parseDouble(firstNum) / Double.parseDouble(lastNum);
                break;
        }


        return answer;
    }

    private String findSimpleExpression(String oldExpression, int center) {
        int left = center, right = center;
        boolean findLeft = false, findRight = false;
        while (!findLeft || !findRight) {
            if (!findLeft) {
                left--;
            }
            if (!findRight) {
                right++;
            }
            if (left == 0
                    || (oldExpression.charAt(left) == '＋' || oldExpression.charAt(left) == '－'
                    || oldExpression.charAt(left) == '×' || oldExpression.charAt(left) == '÷')) {
                findLeft = true;
                //不能包括运算符
                if (left != 0) {
                    left++;
                }
            }
            if (right == oldExpression.length()
                    || (oldExpression.charAt(right) == '＋' || oldExpression.charAt(right) == '－'
                    || oldExpression.charAt(right) == '×' || oldExpression.charAt(right) == '÷')) {
                findRight = true;

                //不能包括运算符，因为右边时开区间，所以不需要判断
//                if(right != oldExpression.length() - 1){
//                    right--;
//                }

            }
        }

        return oldExpression.substring(left, right);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != view) {
            ((ViewGroup) view.getParent()).removeView(view);
        }
    }


}
