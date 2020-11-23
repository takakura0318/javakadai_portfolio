package javakadai_portfolio.blackjack.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * トランプカード52枚を生成するクラス
 *
 * @author たかくら
 */
public class Card {

	// インスタンス化させない
	private Card() {

	}

	/**
	 * トランプカード(52枚)を作成るメソッド
	 *
	 * @return トランプ52枚
	 */
	public static List<String> createCard() {

		// トランプの種類を宣言
		String[] mark = { "スペード", "ハート", "クローバー", "ダイヤ" };
		List<String> list = new ArrayList<String>();

		// トランプカード(52枚)作成
		for (int i = 0; i < 4; i++) {
			// 1~13回ループを回す
			for (int j = 1; j <= 13; j++) {

				if (j == 1) {
					list.add(mark[i] + " " + "A");
				} else if (j == 11) {
					list.add(mark[i] + " " + "J");
				} else if (j == 12) {
					list.add(mark[i] + " " + "Q");
				} else if (j == 13) {
					list.add(mark[i] + " " + "K");
				} else {
					list.add(mark[i] + " " + j);// トランプカードの数字が2~10を生成する
				}

			}
		}

		// for (String string : list) {
		// System.out.println(string);
		// }

		// トランプカード(52枚)をシャッフル
		Collections.shuffle(list);

		return list;

	}
}
