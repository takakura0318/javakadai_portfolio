package javakadai_portfolio.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javakadai_portfolio.blackjack.player.Cpu;
import javakadai_portfolio.blackjack.player.Dealer;
import javakadai_portfolio.blackjack.player.Guest;
import javakadai_portfolio.blackjack.player.Human;
import javakadai_portfolio.blackjack.player.Player;
import javakadai_portfolio.blackjack.util.CalculationHandUtil;
import javakadai_portfolio.blackjack.util.InputValidatorUtil;

/**
 * BlackJackを実行するクラス
 *
 * @author たかくら
 */
public class BlackJack {

	/** 標準入力 */
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/** ディーラクラス */
	public Dealer dealer;

	/** ボードクラス */
	public Board board;

	/** プレイヤー、ディーラー、Cpuを格納する人間リスト */
	public static List<Human> allHumanList = new ArrayList<>();

	/**
	 * BlackJackの実行
	 *
	 */
	public void execute() {

		System.out.println("■ブラックジャックが始まりました。\n");

		// Cpuの数を決める
		int numberOfCpu = inputNumberOfCpu();

		// プレイヤー、ディラー、Cpu(1~3人)を生成し、各インスタンスを人間リストにadd()する
		setting(numberOfCpu);

		// ゲームを開始する
		playGame();

		System.out.println();
		System.out.println("■ブラックジャックが終わりました\n");

	}

	/**
	 * ブラックジャックをプレイするメソッド
	 */
	private void playGame() {

		// プレイヤー、ディーラー、Cpuにカードを2枚ずつ配る
		dealer.dealsTwoCard(allHumanList);

		// プレイヤー、ディーラー、Cpuに最初に配ったカード2枚を表示する
		board.showAllHumanDealsCard(allHumanList);

		// プレイヤー、ディーラー、Cpuの手札を合計する
		CalculationHandUtil.sumPoint(allHumanList);

		// プレイヤー、ディーラー、Cpuの手札を合計点を表示する
		board.showHandSum(allHumanList);

		// プレイヤー、ディーラー、Cpuの3枚目以降の処理。
		for (Human human : allHumanList) {
			human.processingAfterTheThirdPages(human, dealer);
		}

		// テスト用
		// allHumanList.get(0).setHandSum(21);
		// allHumanList.get(4).setBlackJackFlag(true);
		// allHumanList.get(4).setHandSum(21);
		// allHumanList.get(4).setHandSum(20);

		// プレイヤー、Cpuの勝敗判定
		blackJackJudgment(getGuestList(allHumanList));

		// プレイヤー、ディーラー、Cpuの手札を合計点を表示する
		board.showHandSum(allHumanList);

		// プレイヤー、Cpuの結果を表示する
		board.showBlackJackResult(getGuestList(allHumanList));

		// 引き分けだった場合はコインゲームをする(特別ルール)
		specialRule(getPlayer(allHumanList));

	}

	/**
	 *
	 * ゲストリスト(Playerクラス型とCpuクラス型を格納するList)を取得<br>
	 * 人間リストからプレイヤーを取得。
	 *
	 * @param allHumanList
	 *            人間リスト
	 * @return ゲストリスト
	 */
	private List<Guest> getGuestList(List<Human> allHumanList) {

		// 方法複数検討
		// 方法1：Playerクラス、Cpuクラス、Dealerクラスに「humanTypeフィールド」を作って判別する方法
		// 方法2：for(int i = 0 ; 0 <= Humanリストの要素数; i++ ) で if(!i =
		// ディーラーのインデック)を使う方法
		// 方法3：instanceOfを使う方法
		List<Guest> guestList = new ArrayList<>();

		for (Human human : allHumanList) {
			if (human.getHumanType().equals("Player") || human.getHumanType().equals("Cpu")) {
				guestList.add((Guest) human);
			}
		}

		return guestList;
	}

	/**
	 *
	 * プレイヤー取得。 人間リストからプレイヤーを取得。
	 *
	 * @param allHumanList
	 *            人間リスト
	 * @return プレイヤー
	 */
	private Player getPlayer(List<Human> allHumanList) {

		return (Player) allHumanList.get(0);
	}

	/**
	 * 引き分けフラグがtrueだった場合、特別ルールを実行する
	 *
	 * @param player
	 *            プレイヤー
	 */
	private void specialRule(Player player) {

		// もしも引き分けフラグがtrueだった場合
		if (player.isDraw()) {
			// プレイヤークラスの特別ルールを実行する
			player.doSpecialRule(player);
		}

	}

	/**
	 * ブラックジャックの勝敗を決める
	 *
	 * @param guestList
	 *            ゲストリスト(Playerクラス型とCpuクラス型を格納するList)
	 */
	private void blackJackJudgment(List<Guest> guestList) {

		//
		for (Guest guest : guestList) {

			// もしもプレーヤーがバーストしていた場合は、
			if (guest.isBurstFlag()) {
				guest.setBlackJackResult("負け");
				continue;
				// return;
				// ↑バグの原因：人間リストをforEachで回している途中で早期リターンをすると早期リターン後の処理が行われないので「HumanクラスのblackJackフィールドがnullになってしまう」
			}

			// if(もしもプレイヤーがブラックジャックだった場合は)
			if (guest.isBlackJackFlag()) {
				// 勝ちをセットする
				guest.setBlackJackResult("勝ち");
				continue;
			}

			if (dealer.isBurstFlag()) { // もしもディーラがバーストしていた場合は
				// 勝ちをセットする
				guest.setBlackJackResult("勝ち");
				continue;
			}

			// もしもプレーヤーとディーラーが合計点が同じだった場合は
			if (guest.getHandSum() == dealer.getHandSum()) {

				// ディーラーがブラックジャックだった場合
				if (dealer.isBlackJackFlag())
					guest.setBlackJackResult("負け");

				// ディーラーがブラックジャックではな場合
				if (!dealer.isBlackJackFlag()) {
					// 引き分けをセットする
					guest.setBlackJackResult("引き分け");

					// もしもPlayerクラスならば、引き分けflagを立てる(あとで修正)
					if (guest.getHumanType().equals("Player"))
						getPlayer(guest).setDraw(true);
				}

				continue;
			}

			if (guest.getHandSum() > dealer.getHandSum()) {// もしもディーラの合計点よりもプレイヤーの合計点が高い場合は
				// 勝ちをセットする
				guest.setBlackJackResult("勝ち");
			} else { // それ以外の場合は(つまり、同点じゃない場合 && プレーヤーの方が合計点が低い)
					// 負けをセットする
				guest.setBlackJackResult("負け");
			}

		}

	}

	/**
	 * ゲストからPlayerに変換するメソッド
	 *
	 * @param guest
	 *            人間リスト(全プレイヤー)
	 * @return プレイヤ
	 */
	private Player getPlayer(Guest guest) {
		return (Player) guest;

	}

	/**
	 * プレイヤー、ディーラー、Cpu(1~3名)を生成し、各インスタンスを人間リストにadd()する
	 *
	 * @param player
	 *            プレイヤー
	 */
	private void setting(int numberOfCpu) {

		// プレイヤーを追加
		allHumanList.add(new Player());

		// Cpu(複数)を追加
		for (int i = 0; i < numberOfCpu; i++) {
			allHumanList.add(new Cpu());
		}

		// ディーラーを追加
		dealer = new Dealer();
		allHumanList.add(dealer);

		// ボードクラスを生成
		board = new Board();

	}

	/**
	 * Cpuの数を「1」「2」「3」のどれかか入力されるまで入力させる
	 *
	 * @return Cpuの数(「1」「2」「3」のどれか)
	 */
	private int inputNumberOfCpu() {

		// Cpuの数を初期化
		String inputNumberOfCpu = null;

		// Cpuの数「1」「2」「3」のどれかか入力されるまで入力させる
		try {
			while (true) {
				System.out.println(String.format("Cpuの数を選びなさい(1～3で選ぶ)"));
				inputNumberOfCpu = reader.readLine();
				// cpuの数が「1」「2」「3」のどれかが入力されているかチェックする。
				if (InputValidatorUtil.isCpuNum(inputNumberOfCpu)) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Cpuの数「1」「2」「3」のどれかをint型で返す
		return Integer.parseInt(inputNumberOfCpu);
	}

}
