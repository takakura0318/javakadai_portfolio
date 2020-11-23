package author_hirase.blackjack;

import java.util.List;

public class CountUtil {

	/**
	 * インスタンス化されないようにするためのコンストラクタ
	 */
	private CountUtil() {
	}

	/**
	 * カウントを行うメソッド
	 * J,Q,Kを10、Aを1か11としてカウントする
	 * @param person 指定された人
	 * @return カウント
	 */
	public static int countNumber(List<Card> card) {
		int count = 0;
		for (int i = 0; i < card.size(); i++) {
			String str = card.get(i).getRank().getValue();
			int number = 0;
			switch (str) {
			case "A":
				if (count <= 10) {
					number = 11;
				} else {
					number = 1;
				}
				break;
			case "J":
				number = 10;
				break;
			case "Q":
				number = 10;
				break;
			case "K":
				number = 10;
				break;
			default:
				number = Integer.parseInt(str);
				break;
			}
			count += number;
		}
		if (22 <= count && Card.containsA(card)) {
			count = countMini(card);
		}
		return count;
	}

	/**
	 * Aを1としてカウントするメソッド
	 * @param person 指定された人
	 * @return count カウント
	 */
	public static int countMini(List<Card> card) {
		int count = 0;
		for (int i = 0; i < card.size(); i++) {
			String str = card.get(i).getRank().getValue();
			int number = 0;
			switch (str) {
			case "A":
				number = 1;
				break;
			case "J":
				number = 10;
				break;
			case "Q":
				number = 10;
				break;
			case "K":
				number = 10;
				break;
			default:
				number = Integer.parseInt(str);
				break;
			}
			count += number;
		}
		return count;
	}

	/**
	 * カウントを表示するメソッド
	 * @param cards
	 */
	public static void count(List<Card> cards) {
		System.out.println(countNumber(cards));
	}

	/**
	 * バーストしているかを判定するメソッド
	 * @param count カウント
	 * @return boolean
	 */
	public static boolean isBust(int count) {
		return 22 <= count;
	}
}
