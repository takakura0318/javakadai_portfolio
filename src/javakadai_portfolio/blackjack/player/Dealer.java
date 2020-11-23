package javakadai_portfolio.blackjack.player;

import java.util.List;

import javakadai_portfolio.blackjack.card.Card;
import javakadai_portfolio.blackjack.util.CalculationHandUtil;

/**
 * ディーラークラス。スーパークラスであるHumanクラス(人間クラス)を継承している
 *
 * @author たかくら
 */
public class Dealer extends Human {

	/** トランプカード52枚を取得する */
	public List<String> deck = Card.createCard();

	/** 手札の枚数 */
	private int pagesIdx = 1;

	/**
	 * デフォルトコンストラクタ
	 */
	public Dealer() {
		setHumanType("Dealer");
		setName("ディーラー");
	}

	/**
	 * プレイヤー、ディーラー、Cpu(1~3名)にカード2枚ずつ配る
	 *
	 * @param allHumanList
	 *            人間リスト(全プレイヤー)
	 */
	public void dealsTwoCard(List<Human> allHumanList) {

		System.out.println("■プレイヤー、ディーラー、Cpuにカードを2枚ずつ配ります\n");

		// カード2枚ずつを配る
		for (int i = 1; i <= 2; i++) {
			// プレイヤーにトランプカードを1枚配り、一度引いたカードは削除する
			dealsOneCard(allHumanList);

		}
	}

	/**
	 * プレイヤー、ディーラー、Cpu(1~3名)にトランプカードを1枚配り、一度引いたカードは削除する
	 *
	 * @param allHumanList
	 *            人間リスト(全プレイヤー)
	 */
	private void dealsOneCard(List<Human> allHumanList) {
		for (Human human : allHumanList) {
			dealCard(human); // カードを1枚配り、1度引いたカードは削除する
		}
	}

	/**
	 * 各プレイヤーの手札にカードを追加し、一度引いたカードをデッキから削除する
	 *
	 * @param human
	 *            人間
	 */
	public void dealCard(Human human) {
		// デッキから1枚ひいて手札に追加する
		human.getHandList().add(deck.get(0));
		// 引いたカードをデッキから削除する
		deck.remove(0);
	}

	/**
	 * Dealerクラス3枚目以降の処理。オーバーライド。ディーラークラスの場合は、スタンドもしくはバーストするまでカードを引く
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

				// 手札が17以上の場合ブレーク
				if (human.getHandSum() >= 17) {
					break;
				}

				// 3枚目以降のカードを引く
				dealCard(human);

				// 引いたカードを表示する(あとで消す)
				System.out.println(
						getName() + getHandList().size() + "枚目のカードは"
								+ getHandList().get(getHandList().size() - 1));

				// 現在の手持ちを合計する
				CalculationHandUtil.sumPoint(human);

				// ディーラーのバーストチェック
				if (isBursted(human.getHandSum())) {
					System.out.println("ディーラーがバーストしてしまいました。");
					human.setBurstFlag(true);
					break;
				}
			}

		}
	}

	public int getPagesIdx() {
		return pagesIdx++;
	}

	public void setPagesIdx(int pagesIdx) {
		this.pagesIdx = pagesIdx;
	}

}
