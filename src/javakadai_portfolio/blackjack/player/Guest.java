package javakadai_portfolio.blackjack.player;

/**
 * ゲストクラス。スーパークラスであるHumanクラス(人間クラス)を継承している
 *
 * @author たかくら
 */
public class Guest extends Human {

	/** 勝敗 */
	private String blackJackResult;

	/**
	 * 引いたカードを表示する(プレイヤーとCpu専用のメソッド。)
	 */
	public void showDrawnCard() {
		System.out.println(
				getName() + ":" + getHandList().size() + "枚目のカードは　"
						+ getHandList().get(getHandList().size() - 1));
	}

	// 以下はgetterとsetter
	public String getBlackJackResult() {
		return blackJackResult;
	}

	public void setBlackJackResult(String blackJackResult) {
		this.blackJackResult = blackJackResult;
	}

}
