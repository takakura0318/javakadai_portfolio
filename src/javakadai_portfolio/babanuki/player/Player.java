package javakadai_portfolio.babanuki.player;

import java.util.Scanner;

import javakadai_portfolio.babanuki.GameMaster;
import javakadai_portfolio.babanuki.Table;

public class Player extends AllHuman {

	public Player(Table table, GameMaster gameMaster) {
		super(table, gameMaster);
		setName("You");
	}

	/**
	 * ババ抜きの一連動作
	 */
	@Override
	public void play(AllHuman nextPlayer) {

		// nextPlayer に手札を出してもらう
		nextPlayer.showMeHandList();

		// プレイヤーにどのカード引くか入力してもらう
		Scanner sc = new Scanner(System.in);
		int scNum = sc.nextInt();
		int drawIndex = scNum - 1;

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
			System.out.println("【" + this.getName() + "の残り手札】");
			this.myHand.showHand();
		}

	}

	@Override
	public void test(AllHuman nextPlayer) {
		// TODO 自動生成されたメソッド・スタブ
		System.out.println(this.getName() + "は" + nextPlayer.getName() + "を引きます");

	}

}
