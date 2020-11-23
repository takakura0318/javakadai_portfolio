package javakadai_portfolio.blackjack;

import java.util.List;

import javakadai_portfolio.blackjack.player.Guest;
import javakadai_portfolio.blackjack.player.Human;

public class Board {

	/** 手札の枚数 */
	private int pagesIdx = 1;

	/**
	 * プレイヤー、ディーラー、Cpuに配った最初のカード2枚を表示する
	 *
	 * @param allHumanList
	 *            人間リスト(全プレイヤー)
	 */
	public void showAllHumanDealsCard(List<Human> allHumanList) {

		// プレイヤー、ディーラー、Cpuに配った最初のカード2枚を表示する
		for (Human human : allHumanList) {

			// もしも人間リストがディーラー型ではない場合
			if (human.getHumanType().equals("Player") || human.getHumanType().equals("Cpu")) {
				for (String str : human.getHandList()) {
					System.out.println(human.getName() + "の" + this.getPagesIdx() + "枚目は　" + str);
				}
			}

			// もしも人間リストがディーラーの場合
			if (human.getHumanType().equals("Dealer")) {
				System.out.println(human.getName() + "の" + this.getPagesIdx() + "枚目は　" + human.getHandList().get(0));
				System.out.println(human.getName() + "の" + this.getPagesIdx() + "枚目は　" + human.getHandList().get(1)
						+ "(デバッグ用、本来なら表示させない)");
				// System.out.println(human.getName() + "の" +
				// human.getPagesIdx() + "枚目は: 秘密です。");
			}

		}
	}

	/**
	 * 手札の合計点を表示する
	 *
	 * @param allHumanList
	 *            人間リスト(全プレイヤー)
	 */
	public void showHandSum(List<Human> allHumanList) {

		System.out.println();
		System.out.println("■合計点\n");

		for (Human human : allHumanList) {

			if (human.getHumanType().equals("Player") || human.getHumanType().equals("Cpu")) {
				System.out.println(human.getName() + ":　" + human.getHandSum() + "点");
			}

			if (human.getHumanType().equals("Dealer")) {
				System.out.println(human.getName() + ":　" + human.getHandSum() + "点(デバッグ用、本来なら表示させない。結果の時だけ" + "表示)");
			}

		}
	}

	/**
	 * ブラックジャックの勝敗を表示する
	 *
	 * @param guestList
	 *            ゲストリスト(Playerクラス型とCpuクラス型を格納するList)
	 */
	public void showBlackJackResult(List<Guest> guestList) {

		System.out.println();
		System.out.println("■結果\n");

		for (Guest guest : guestList) {
			System.out.println(guest.getName() + ":　" + guest.getBlackJackResult());
		}

	}

	/** ディーラの手札を表示する 1枚目のみ表示し、2枚目は非表示 */

	public int getPagesIdx() {
		return pagesIdx++;
	}

	public void setPagesIdx(int pagesIdx) {
		this.pagesIdx = pagesIdx;
	}

}
