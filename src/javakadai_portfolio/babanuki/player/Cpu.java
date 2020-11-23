package javakadai_portfolio.babanuki.player;

import javakadai_portfolio.babanuki.GameMaster;
import javakadai_portfolio.babanuki.Table;

/**
 * @author yt031
 *
 */
public class Cpu extends AllHuman {

	public static int cpuCnt = 1;

	public Cpu(Table table, GameMaster gameMaster) {
		super(table, gameMaster);
		setName("Cpu" + cpuCnt++);

	}

	/**
	 * ババ抜きをプレイする
	 */
	@Override
	public void play(AllHuman nextPlayer) {

		// 現在の手札を表示する

		// nextPlayer に手札を出してもらう
		nextPlayer.showMeHandList();

		// nextPlayerの手札の枚数を取得する
		int nextPlayerHand = nextPlayer.myHand.getMyHand().size();

		// 乱数によって引くカードを決める
		int drawCardRansuu = (int) (Math.random() * nextPlayerHand) + 1;

		// 指定されたカードのインデックス番号を取得
		int drawIndex = drawCardRansuu - 1;

		// nextPlayer からカードを引く。引かれたカードはListから削除
		String pickCard = nextPlayer.myHand.getMyHand().remove(drawIndex);
		// 引いたカードを自分の手札に加える
		this.myHand.addCard(pickCard);

		// 同じカードがあった場合捨てる
		this.doDiscardCard();

		// 手札がゼロになったかどうを調べる
		if (isZeroHand()) {
			// もしもゼロになった場合はGameマスタに知らせる
			gameMaster.declareWin(this);
		} else {
			// それ以外なら、現在の手札を表示する
			System.out.println("【" + this.getName() + "の残り手札】\n");
			this.myHand.showHand();
		}

	}

	@Override
	public void test(AllHuman nextPlayer) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println(this.getName() + "は" + nextPlayer.getName() + "を引きます");
	}

}
