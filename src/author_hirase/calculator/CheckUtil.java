package author_hirase.calculator;

import java.util.List;

public class CheckUtil {

	/**
	 * 半角数字、半角記号で入力されているか判定するメソッド
	 * @param input 入力値
	 * @return boolean
	 */
	public boolean isInput(String input) {
		return input.matches("^[0-9+\\-*/\\^()\\.,]+$");
	}

	/**
	 * 数字であるか判定するメソッド
	 * @param num 文字列
	 * @return true:数字である , false:数字ではない
	 */
	public boolean isNumber(String num) {
		try {
			Double.parseDouble(num);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 演算子であるか判定するメソッド
	 * @param operator 文字列
	 * @return true:演算子である , false:演算子ではない
	 */
	public boolean isOperator(String operator) {
		return operator.matches("[+\\-*/\\^]");
	}

	/**
	 * 括弧であるか判定するメソッド
	 * @param bracket 文字列
	 * @return ture:括弧である , false:括弧ではない
	 */
	public boolean isBracket(String bracket) {
		return bracket.matches("[()]");
	}

	/**
	 * ")"の足りない数をカウントするメソッド
	 * @param inputArray 入力された文字列の配列
	 * @return ")"の足りない数
	 */
	public int addBracketCount(String[] inputArray) {
		int leftBracket = 0;
		int rightBracket = 0;

		for (String s : inputArray) {
			if (s.equals("(")) {
				leftBracket++;
			}
			if (s.equals(")")) {
				rightBracket++;
			}
		}

		return leftBracket - rightBracket;
	}

	/**
	 * ")"を追加するメソッド
	 * @param correctArray 入力された文字列のリスト
	 * @param count ")"の足りない数
	 * @return ")"の足りない分を加えた文字列のリスト
	 */
	public List<String> addBracket(List<String> correctArray, int count) {

		if (0 < count) {
			for (int i = 0; i < count; i++) {
				correctArray.add(")");
			}
			return correctArray;
		}

		return correctArray;
	}

	/**
	 * 計算ができるよう x,0 を追加するメソッド
	 * @param correctArray 文字列のリスト
	 * @return x,0 を加えた文字列のリスト
	 */
	public List<String> checkOperator(List<String> correctArray) {

		if (isOperator(correctArray.get(0))) {
			correctArray.add(0, "0");
		}

		for (int i = 1; i < correctArray.size(); i++) {
			if (isOperator(correctArray.get(i - 1))
					&& !(isNumber(correctArray.get(i)) || correctArray.get(i).equals("("))) {
				correctArray.add(i, "x");
			}
			if (isOperator(correctArray.get(i)) && !(isNumber(correctArray.get(i - 1))
					|| correctArray.get(i - 1).equals(")"))) {
				correctArray.add(i, "0");
			}
		}

		if (isOperator(correctArray.get(correctArray.size() - 1))) {
			correctArray.add("x");
		}

		return correctArray;
	}
}
