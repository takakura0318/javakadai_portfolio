package javakadai_portfolio.babanuki;

import java.util.ArrayList;
import java.util.List;

import javakadai_portfolio.babanuki.player.AllHuman;
import javakadai_portfolio.babanuki.player.Cpu;
import javakadai_portfolio.babanuki.player.Player;

public class GameMaster {

	private List<AllHuman> allHumanList = new ArrayList<>();

	private List<String> deck;

	/**
	 * ゲームの設定を行う
	 */
	public void prepareGame(int numberOfCpu, List<String> deck) {

		// 手札を捨てるテーブルを生成する
		Table table = new Table();

		// プレイヤーリストにPlayerを追加する
		Player player = new Player(table, this);
		allHumanList.add(player);

		// プレイヤーリストにCpuを追加する
		for (int i = 0; i < numberOfCpu; i++) {
			allHumanList.add(new Cpu(table, this));
		}

		// トランプカードをセットする
		this.deck = deck;

		// トランプカード53枚を各プレイヤーに順番に配る
		prepareBabanuki(this.deck);

		// 同じカードがあった場合はテーブルにカードを捨てる
		for (AllHuman allPlayer : allHumanList) {
			allPlayer.doDiscardCard();
		}

	}

	/**
	 * ババ抜きの準備を行う トランプカード53枚を参加者に配る。
	 */
	private void prepareBabanuki(List<String> deck) {

		int targetPlayer = 0;

		// トランプカード53枚を時計周りで各プレイヤーにに配る
		for (String card : deck) {
			// トランプカード1枚を手札に加える
			allHumanList.get(targetPlayer++).getMyHand().addCard(card);

			// ターゲットとプレイヤーの数が一致した場合
			if (targetPlayer == allHumanList.size()) {
				targetPlayer = 0;
			}
		}

	}

	/**
	 * ババ抜きを開始する
	 */
	public void startGame() {
		// 現在ターンのプレイヤーのインデックス番号を保持する変数を宣言
		int targetPlayerIndex = 0;
		// 隣プレイヤーのインデックス番号を保持する変数を宣言
		int nextPlayerIndex = targetPlayerIndex + 1;

		// 最後の一人になるまでループする
		while (allHumanList.size() != 1) {

			// ターゲット(現在のターン)のプレイヤーを取得する
			AllHuman targetPlayer = allHumanList.get(targetPlayerIndex++);

			// 隣のプレイヤーを取得する
			AllHuman nextPlayer = allHumanList.get(nextPlayerIndex++);

			// ババ抜きの一連の流れを実行する
			targetPlayer.play(nextPlayer);

			if (targetPlayerIndex == allHumanList.size()) {
				targetPlayerIndex = 0;
			}

			if (nextPlayerIndex == allHumanList.size()) {
				nextPlayerIndex = 0;
			}

			// for (AllHuman human : allHumanList) {
			// if (nextPlayer == allHumanList.size()) {
			// nextPlayer = 0;
			// }
			//
			// // 同じカードがあった場合は、捨てる
			// human.play(allHumanList.get(nextPlayer++));
			//
			// }
		}

		System.out.println("ババ抜きが終了しました。");
	}

	/**
	 * 上がりを宣言する
	 */
	public void declareWin(AllHuman winnerPlayer) {

		// 上がったプレイヤー名を表示する
		System.out.println(winnerPlayer.getName() + "が上がりました。");

		// 上がったプレイヤーを削除する
		allHumanList.remove(winnerPlayer);

	}

}
