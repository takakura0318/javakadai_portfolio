package javakadai_portfolio.blackjack.player;

import javakadai_portfolio.blackjack.util.CalculationHandUtil;

/**
 * CPUクラス。スーパークラスであるHumanクラス(人間クラス)を継承している
 *
 * @author たかくら
 */
public class Cpu extends Guest {

	/** CPUの人数を1で初期化 */
	static int cpuNumber = 1;

	/**
	 * デフォルトコンストラクタ
	 */
	public Cpu() {
		setName("Cpu" + cpuNumber);
		setHumanType("Cpu");
		cpuNumber++;
	}

	/**
	 * Cpuクラス3枚目以降の処理。オーバーライド。Cpuクラスの場合は、スタンドもしくはバーストするまでカードを引く
	 *
	 * @param human
	 *            人間
	 * @param dealer
	 *            ディーラー
	 */
	@Override
	public void processingAfterTheThirdPages(Human human, Dealer dealer) {

		System.out.println();
		System.out.println("■" + human.getName() + "のターン\n");

		// もしもブラックジャックを達成していた場合
		if (isBlackJackJudge()) {

			// ブラックジャックフラグを立てる
			super.processingAfterTheThirdPages(human, dealer);

		}

		if (!isBlackJackJudge()) { // もしもブラックジャックを達成してない場合

			while (true) {
				// 手札が17以上の場合ブレーク
				if (human.getHandSum() >= 17) {
					break;
				}
				// 3枚目以降のカードを引く
				dealer.dealCard(human);

				// 引いたカードを表示する
				showDrawnCard();// デバッグ用(あとで削除)

				// 現在の手持ちを合計する
				CalculationHandUtil.sumPoint(human);

				// ディーラーのバーストチェック
				if (isBursted(human.getHandSum())) {
					System.out.println(human.getName() + "がバーストしてしまいました。");
					human.setBurstFlag(true);
					break;
				}
			}

		}

	}
}
