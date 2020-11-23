package javakadai_portfolio.blackjack.util;

import java.util.List;

import javakadai_portfolio.blackjack.player.Human;

/**
 * 手札の合計を計算する専用のクラス
 *
 * @author たかくら
 */
public class CalculationHandUtil {

	// staticしかないくらすなのでインスタンス化を防ぐ
	private CalculationHandUtil() {
	}

	/**
	 * 人間リスト(全プレイヤー)の手札を合計するメソッド名。
	 * 手札のトランプカード「J」「Q」「K」を10に変換→手札のトランプカード「A」を21に近い「1」or「11」に変換する→
	 * 手札のトランプカードを合計する→もしも合計が21より大きい &&「A」を引いている場合は合計を-10する
	 *
	 * @param allHumanList
	 *            人間リスト(全プレイヤー)
	 */
	public static void sumPoint(List<Human> allHumanList) {

		// 人間リストを展開して各人間ごとに集計する
		for (Human human : allHumanList) {
			sumPoint(human);
		}

	}

	/**
	 * 人間(プレイヤー、ディーラー、Cpuのいずれか)の手札を合計する
	 *
	 * @param human
	 *            人間
	 */
	public static void sumPoint(Human human) {

		// 手札のトランプカード「J」「Q」「K」を10に変換→手札のトランプカード「A」を21に近い「1」or「11」に変換する→手札のトランプカードを合計する
		getSum(human);

		// もしも人間がPlayerクラスの場合
		if (human.getHumanType().equals("Player")) {
			// 現在の手札のトランプカードを合計を取得する
			int sum = human.getHandSum();

			// 過去にトランプカードのAを11に変換した回数を取得する
			int aceCnt = human.getAceCnt();

			// 手札の合計が21よりも大きい、尚且つ、過去にAceを11に変換していた場合は
			if (sum > 21 && aceCnt > 0) {
				sum -= 10;
				// player.setAceCnt(aceCnt);
				human.setHandSum(sum);
			}
		}

	}

	/**
	 * 手札のトランプカード「J」「Q」「K」を10に変換→手札のトランプカード「A」を21に近い「1」or「11」に変換する→
	 * 手札のトランプカードを合計する
	 *
	 * @param human
	 *            人間
	 */
	public static void getSum(Human human) {

		// トランプカードのAを引いた場合にカウントする
		int aceCnt = human.getAceCnt();

		// 合計を格納する変数sumを初期化
		int sum = 0;

		// 手札の合計
		int handSum = human.getHandSum();

		// 手札を格納する
		List<String> handList = human.getHandList();

		// トランプカードの「J」「Q」「K」を10に変換、トランプカードの「A」を21に近い「1」or「11」に変換する
		for (int i = 0; i < handList.size(); i++) {
			// トランプカードを半角スペース区切りで分割する。(例)「ハート A」(文字列) →「ハート、A」(配列)
			String[] cardSplit = handList.get(i).split(" ");

			switch (cardSplit[1]) {
			case "J":
			case "Q":
			case "K":
				handList.set(i, cardSplit[0] + " " + 10);
				break;
			case "A":
				if (handSum <= 10) { // もしも手札の合計が10以下の場合はトランプカードのAを11に変換する
					handList.set(i, cardSplit[0] + " " + 11);
					aceCnt++;
					handSum += 11;
					human.setAceCnt(aceCnt);
				} else { // もしも手札の合計が10以下ではない場合は
					handList.set(i, cardSplit[0] + " " + 1);
				}

				break;
			default:
				break;
			}
		}

		// 手札のトランプカードを合計する
		for (String str : handList) {
			String[] cardSplit = str.split(" ");
			int cardNum = Integer.parseInt(cardSplit[1]);
			sum += cardNum;
		}

		human.setHandList(handList); // Humanクラスを継承したのHandListフィールドに再代入
		human.setHandSum(sum); // Humanクラスを継承したのsumフィールドに手札の合計をセットする
	}

}
