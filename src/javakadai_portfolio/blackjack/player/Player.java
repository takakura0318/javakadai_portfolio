package javakadai_portfolio.blackjack.player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javakadai_portfolio.blackjack.util.CalculationHandUtil;
import javakadai_portfolio.blackjack.util.InputValidatorUtil;

/**
 * プレイヤークラス。スーパークラスであるHumanクラス(人間クラス)を継承している
 *
 * @author たかくら
 */
public class Player extends Guest {

	/** 標準入力 */
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/** 引き分けになっているかどうか */
	private boolean draw;

	/**
	 * デフォルトコンストラクタ
	 */
	public Player() {
		setName("あなた");
		setHumanType("Player");
	}

	/**
	 * プレイヤークラス3枚目以降の処理。オーバーライド。プレイヤークラスの場合は、スタンドもしくはバーストするまでカードを引く
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

		// もしもブラックジャックを達成してない場合
		if (!isBlackJackJudge()) {

			while (true) {
				System.out.println("ヒットの場合は1を、スタンドの場合は2を入力してください。");
				// 入力させる
				String hitOrStand = null;
				try {
					hitOrStand = reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// もしも「2」が入力された場合は、ループを抜ける
				if (InputValidatorUtil.isStand(hitOrStand)) {
					break;
				}

				if (InputValidatorUtil.isHit(hitOrStand)) {

					// 3枚目のカードを引く
					dealer.dealCard(human);
					// 引いたカードを表示
					showDrawnCard();

					// 現在の手持ちを合計する
					CalculationHandUtil.sumPoint(human);

					// 合計点を表示する
					System.out.println(human.getHandSum());

					// プレイヤーのバーストチェック
					if (isBursted(human.getHandSum())) {
						System.out.println("残念、あなたはバーストしてしまいました。");
						human.setBurstFlag(true);
						break;
					}
				}
			}

		}
	}

	/**
	 * プレイヤーとディーラーが引き分けだった場合、特別ルールの実行するメソッド。<br>
	 * 特別ルールの内容は、プレイヤーとディーラーが引き分けだった場合は、コインゲームで勝敗を決める。<br>
	 * プレイヤーに「表」or「裏」を決めてもらう。 コインの「表」が出る確率は50％、「裏」が出る確率は50％である。<br>
	 * 予想が当たっていたらプレーヤーの勝ち、外れていた場合は、プレーヤーの負け
	 *
	 * @param player
	 *            プレイヤー
	 */
	public void doSpecialRule(Player player) {

		// スペシャルルールに関する表示
		showSpecialRule();

		// 「表」もしくは「裏」入力されるまで入力させる
		String coinInput = null;
		coinInput = SpecialRuleInput(coinInput);

		// コインを取得する。コインの「表」が出る確率を50％、「裏」が出る確率を50％である。
		String coinResult = null;
		coinResult = coinProcess(coinResult);

		// コインの予想が当たっているかどうか判定し、予想が当たっていれば、勝ちを表示する
		specialRuleJudgment(coinInput, coinResult);

	}

	/**
	 * コインの予想が当たっているかどうか判定し、予想が当たっていれば、勝ちを表示する
	 *
	 * @param coinInput
	 *            プレイヤー
	 * @param coinResult
	 *            コインの結果
	 */
	private void specialRuleJudgment(String coinInput, String coinResult) {

		// 予想が当たってるか比較
		if (coinInput.equals(coinResult)) {
			this.setBlackJackResult("勝ち");
		} else {
			this.setBlackJackResult("負け");
		}

		// プレイヤーの勝敗を表示する
		System.out.println();
		System.out.println("■特別ルール(コインゲーム)の結果\n");
		System.out.println("あなたの勝敗は" + this.getBlackJackResult());

	}

	/**
	 * コインの処理。コインの「表」が出る確率を50％、「裏」が出る確率を50％である。
	 *
	 * @param coinResult
	 *            コインの結果
	 * @return コインの「表」かコインの「裏」のどちらか
	 */
	private String coinProcess(String coinResult) {

		// 「0」か「1」のどちらかを決める。確率はそれぞれ50％。
		int coin = (int) (Math.random() * 2);

		if (coin == 0) {
			coinResult = "表";
		} else {
			coinResult = "裏";
		}

		System.out.println("コインの結果:" + coinResult);

		return coinResult;
	}

	/**
	 * スペシャルルールに関する表示
	 *
	 * @param coinResult
	 *            コインの結果
	 * @return コインの予想(「表」か「裏」のどらか)
	 */
	private String SpecialRuleInput(String coinInput) {

		try {
			while (true) {
				System.out.println("「表」か「裏」を選びなさい");
				coinInput = reader.readLine();
				// 「表」か「裏」のどちらかが入力されているかをチェックする
				if (InputValidatorUtil.isHeadsOrTails(coinInput))
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return coinInput;
	}

	/**
	 * System.out.println専用のメソッド。
	 *
	 * @param coinResult
	 *            コインの結果
	 * @return コインの予想(「表」か「裏」のどらか)
	 */
	private void showSpecialRule() {

		System.out.println();
		System.out.println("■特別ゲーム\n");

		// System.out.println(player.getName() + "と" + dealer.getName() +
		// "は引き分けなのでコインゲームで勝敗を決めます。");

	}

	// 以下はgetterとsetter

	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

}
