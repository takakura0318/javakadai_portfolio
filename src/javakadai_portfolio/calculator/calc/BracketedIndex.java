package javakadai_portfolio.calculator.calc;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 左括弧のインデックスの番号と右括弧のインデックスの番号を取得する為のクラス
 */
public class BracketedIndex {

	/**
	 * すべての左括弧に対応する右括弧を検索し、HashMapのkeyに「左括弧が見つかったインデックスの番号」、
	 * valueに「左括弧に対応する右括弧のインデックスの番号」を格納したものを取得するメソッド
	 *
	 * @param formattedList
	 *            すべての演算子とオペランドのリスト
	 * @return HashMap キー=インデックス 左括弧の値、値=右括弧のインデックス。
	 */
	public static HashMap<Integer, Integer> getBracketedIndex(List<String> formattedInput) {

		// スタックを宣言
		Stack<Integer> openingParenthesis = new Stack<>();

		// HashMapを宣言
		HashMap<Integer, Integer> relationships = new HashMap<>();

		for (int i = 0; i < formattedInput.size(); i++) {
			// もしも入力値リストの中に左括弧「(」があった場合
			if (formattedInput.get(i).equals("(")) {
				// 入力値リストの左括弧が見つかったインデックスの番号をスタックにpush
				openingParenthesis.push(i);
			} else if (formattedInput.get(i).equals(")") && openingParenthesis.size() > 0) {
				// HashMapの「 key:左括弧が見つかったインデックスの番号 => value:
				// 右括弧が見つかったインデックスの番号」を追加する
				relationships.put(openingParenthesis.pop(), i);
			}
		}

		// もしもスタックが空の場合、(空が正常の動き)
		if (openingParenthesis.empty())
			return relationships;
		else {
			// スタックが空でない場合は、左括弧を持っています
			// ペアがないため、空のマップを返します
			return new HashMap<Integer, Integer>();
		}

	}

}
