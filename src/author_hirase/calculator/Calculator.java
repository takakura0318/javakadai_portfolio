package author_hirase.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 電卓クラス
 * @author STS-20021
 *
 */
public class Calculator {
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/**
	 * 実行メソッド
	 */
	public void execute() {
		try {
			while (true) {
				calculator();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 電卓の処理を行うメソッド
	 * 入力値を取得
	 * 入力された式をが逆ポーランド記法に変換
	 * 計算をし、計算結果を表示
	 */
	public void calculator() {
		try {
			String input = reader.readLine();

			CheckUtil cu = new CheckUtil();

			while (true) {
				input = input.replace(" ", "");
				input = input.replace(",", "");

				if (cu.isInput(input)) {
					break;
				} else {
					System.out.println("正しい形式で入力してください");
				}

				if (input.contains("=")) {
					System.out.println(" = の左辺のみを入力してください。");
				}
				input = reader.readLine();
			}

			// 入力値を配列に格納
			String[] inputArray = input.split("");

			int addCount = cu.addBracketCount(inputArray);

			List<String> list = Arrays.asList(inputArray);
			List<String> correctArray = new ArrayList<>(list);

			cu.addBracket(correctArray, addCount);
			cu.checkOperator(correctArray);

			Stack<String> numStack = new Stack<String>();
			Stack<String> operatorStack = new Stack<String>();

			StringBuilder sb = new StringBuilder();

			// 数値と演算子をそれぞれのstackに格納
			for (String s : correctArray) {
				// 数値の場合
				if (cu.isNumber(s) || s.equals(".") || s.equals("x")) {
					sb.append(s);
					continue;
				}

				if (sb.length() != 0 && !cu.isNumber(s) && !s.equals(".")) {
					numStack.push(sb.toString());
					sb.setLength(0);
				}

				// "(", ")"の場合
				if (cu.isBracket(s)) {
					if (s.equals("(")) {
						operatorStack.push(s);
						continue;
					}
					while (!operatorStack.peek().equals("(")) {
						numStack.push(operatorStack.pop());
					}
					operatorStack.pop();
					continue;
				}

				// 演算子の場合
				if (cu.isOperator(s)) {
					// 演算子stackに要素がない場合
					if (operatorStack.isEmpty() || operatorStack.peek().equals("(")) {
						operatorStack.push(s);
						continue;
					}

					// 演算子stackに要素がある場合

					// 現在の文字列と演算子stackの先頭が一致していた場合
					if (s.equals(operatorStack.peek())) {
						// +,* の場合、演算子stackに入れる
						if (s.equals("+") || s.equals("*")) {

							numStack.push(operatorStack.pop());
							operatorStack.push(s);
							continue;
						}
						// -,/ の場合、演算子stackのトップを取り出して数式stackに入れる
						// 演算子stackに入れる
						numStack.push(operatorStack.pop());
						operatorStack.push(s);
						continue;
					}

					// 現在の文字列と演算子stackの先頭と一致していなかった場合

					// べき乗の場合
					if (s.equals("^")) {
						operatorStack.push(s);
						continue;
					}

					// 演算子stackのトップがべき乗の場合、取り出して数式stackに入れる
					if (operatorStack.peek().equals("^")) {
						numStack.push(operatorStack.pop());
					}
					// +,- の場合、演算子stackのトップを取り出して数式stackに入れる
					if (s.equals("+") || s.equals("-")) {
						numStack.push(operatorStack.pop());
						operatorStack.push(s);
						continue;
					}

					// *,/ の場合、演算子stackのトップが +,- になるまで数式stackに入れる
					while (!operatorStack.isEmpty()) {
						if (operatorStack.peek().equals("+")
								|| operatorStack.peek().equals("-")) {
							break;
						}
						numStack.push(operatorStack.pop());
					}
					operatorStack.push(s);
				}
			}
			if (sb.length() != 0) {
				numStack.push(sb.toString());
			}

			// 演算子のstackに数値のstackを取り出し格納
			while (false == numStack.isEmpty()) {
				operatorStack.push(numStack.pop());
			}

			// 計算処理を行う
			if (operatorStack != null) {
				while (!operatorStack.isEmpty()) {
					String str = (String) operatorStack.pop();

					if (cu.isNumber(str) || str.equals("x")) {
						numStack.push(str);
						continue;
					}

					String strb = numStack.pop().toString();
					String stra = numStack.pop().toString();
					if (strb.equals("x")) {
						strb = stra;
					}

					double a = Double.parseDouble(stra);
					double b = Double.parseDouble(strb);

					switch (str) {
					case "+":
						numStack.push(String.valueOf(a + b));
						break;
					case "-":
						numStack.push(String.valueOf(a - b));
						break;
					case "*":
						numStack.push(String.valueOf(a * b));
						break;
					case "/":
						numStack.push(String.valueOf(a / b));
						if (b == 0) {
							int intA = Integer.parseInt(stra);
							int intB = Integer.parseInt(strb);
							intA = intA / intB;
						}
						break;
					case "^":
						numStack.push(String.valueOf(Math.pow(a, b)));
						break;
					}
				}
			}

			// 計算結果の表示
			String strAns = numStack.pop().toString();
			double ans = Double.parseDouble(strAns);
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(10);
			System.out.println("ans:" + nf.format(ans));

		} catch (IOException e) {
			System.out.println("エラーが発生しました。");
		} catch (ArithmeticException e) {
			System.out.println("0で割ることはできません。");
		} catch (Exception e) {
			System.out.println("計算することができません。");
		}
	}
}
