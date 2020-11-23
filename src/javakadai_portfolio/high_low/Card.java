package javakadai_portfolio.high_low;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * トランプカード(54枚)を作成するクラス
 *
 * @author たかくら
 */
public class Card {

	/**
	 * トランプカード(54枚)を作成るメソッド
	 * @return トランプ54枚
	 */
	public static List<String> createCard() {

		// トランプの種類を宣言
		String[] mark = { "スペード", "ハート", "クローバー", "ダイヤ" };
		List<String> list = new ArrayList<String>();

		// トランプカード(52枚)作成
        for(int i = 0; i < 4; i++){
        	// 1~13回ループを回す
            for(int j = 1; j <= 13; j++){
                list.add(mark[i] + " " + j );// 52枚のカード

            }
        }

        // トランプカード(ジョーカー2枚追加)
        list.add("ジョーカー(1枚目) 14");
        list.add("ジョーカー(2枚目) 14");

        // トランプカード(54枚)をシャッフル
        Collections.shuffle(list);

        return list;

	}

}
