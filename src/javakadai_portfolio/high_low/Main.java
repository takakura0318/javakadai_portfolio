package javakadai_portfolio.high_low;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * high&&lowを実行するmainクラス
 *
 * @author たかくら
 */
public class Main {

	public static void main(String[] args) {

		// トランプカード(54枚)を取得する
		List<String> list=Card.createCard();

		 while(true) {

//			//配列の中身を確認する(テスト用)
//			for (String hoge : list) {
//				System.out.println(hoge);
//			}

			String firstCard = list.get(0);	// 最初の一枚を引く。(トランプカード(54枚)をランダムで1枚取得する。)
			list.remove(0);						// 引いた数を減らす処理
	        System.out.println("1枚目のカードは:" + firstCard);

	        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	        String input = null;
	        // BufferedReaderクラスのreadLine()を使用する
	        try {
					while(true) {
						System.out.println("「UP」 or 「DOWN」を選んでください");
						input = reader.readLine();
						if (isUp(input)|| isDown(input))break;
					}
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

	        String nextCard = list.get(0);	// 二枚目のカードを引く。(トランプカード(52枚)をランダムで1枚取得する。)
			list.remove(0);						// 引いた数を減らす処理

	        // 倍になるか(テスト用)
	        System.out.println("トランプのカードは"+list.size());

	        /******************分割処理。split()でString型の数字をint型で取得する *******************/

	        int firstCardNum = firstCardSplit(firstCard);

	        int nextCardNum = nextCardSplit(nextCard);

	        /********************************* 比較処理 *********************************/

	        // 削除フラグを用意する
	        boolean gameFlag = false;

	        gameFlag = highAndLow(input, firstCardNum, nextCardNum);

	        System.out.println("------------------------------------------------------------------");

	        /******************************** ループを抜ける ********************************/
	        // 「負けの場合」&& 「リストの中身が0の場合」はループを抜ける
	        if (gameFlag == true) break;

	        if(list.size() == 0) {
	        	System.out.println("すべてのカードを引き終わりました。ゲーム終了します。");
	        	break;
	        }


		}

		System.out.println("ゲームが終了しました。");
	}



	/******************************************** 以下はメソッド ********************************************/
	/**
	 * 入力チェック。DOWNかを確認
	 * @param input
	 * 		「UP」or「DOWN」or「それ以外」
	 * @return
	 * 		false
	 * 		true
	 */
	private static boolean isDown(String input) {
		// もしも「DOWN」が入力されている場合は、trueとする
		if (input.equals("DOWN"))return true;

		return false;
	}

	/**
	 * 入力チェック。UPかを確認
	 * @param input
	 * 		「UP」or「DOWN」or「それ以外」
	 * @return
	 * 		false
	 * 		true
	 */
	private static boolean isUp(String input) {
		// もしも「UP」が入力されている場合は、trueとする
		if (input.equals("UP"))return true;

		return false;
	}

	/**
	 * String型のトランプカードをsplit()メソッドで分割してint型に変換する処理
	 * @param firstCard
	 * 		トランプ2枚目(例)「ハート 5」
	 * @return トランプの数(int型)
	 * 		false
	 * 		true
	 */
	private static int firstCardSplit(String firstCard) {

        // 配列firstCardをsplit()で分割。つまり、「スペード 5」 →「スペード5」
        String[] fistCardSplit = firstCard.split(" ");
        // 分割後の数字だけをint型に変換する。つまり「スペード 5」→「5(int型)」に変換する。
        int firstCardNum = Integer.parseInt(fistCardSplit[1]);

		return firstCardNum;

		}

	/**
	 * String型のトランプカードをsplit()メソッドで分割してint型に変換する処理
	 * @param nextCard
	 * 		トランプ2枚目(例)「ハート 5」
	 * @return トランプの数(int型)
	 */
	private static int nextCardSplit(String nextCard) {

        // 配列firstCardをsplit()で分割。つまり、「スペード 5」 →「スペード5」
        String[] nextCardSplit = nextCard.split(" ");
        // 分割後の数字だけをint型に変換する。つまり「スペード 5」→「5(int型)」に変換する。
        int nextCardNum = Integer.parseInt(nextCardSplit[1]);
        System.out.println("2枚目のカードは:" + nextCard);

		return nextCardNum;

	}

	/**
	 * トランプカード(54枚)を作成るメソッド
	 * @return トランプ54枚
	 */
	private static boolean highAndLow(String input, int firstCardNum, int nextCardNum) {
		// TODO 自動生成されたメソッド・スタブ
        // 比較
        switch (input) {

		case "UP":

			if (nextCardNum > firstCardNum) {
				System.out.println("あなたの勝ちです");
			} else {
				System.out.println("あなたの負けです。");
				return true;
			}
			break;

		case "DOWN":
//			// テスト
//			nextCardNum = firstCardNum;

			if (firstCardNum >= nextCardNum) {

				System.out.println("あなたの勝ちです。");

			} else {
				System.out.println("あなたの負けです。");
				return true;
			}

			break;

		default:

		}
		return false;
	}


}
