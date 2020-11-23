package javakadai_portfolio.babanuki;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

		List<String> list = new ArrayList<String>();

		// トランプの種類を宣言
		String[] mark = { "スペード", "ハート", "クローバー", "ダイヤ" };

		// トランプカード(52枚)作成
		for (int i = 0; i < 4; i++) {
			// 1~13回ループを回す
			for (int j = 1; j <= 13; j++) {
				list.add(mark[i] + " " + j);// トランプカードの数字が1~13を生成する
			}
		}

		list.add("ジョーカー 0");
		// for (String string : list) {
		// System.out.println(string);
		// }

		// トランプカード(53枚)をシャッフル
		Collections.shuffle(list);

		return list;

	}

}
