package author_hirase.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 結果を表示するクラス
 * @author STS-20021
 *
 */
public class Result {

	public static void printWin() {
		System.out.println("勝ちです。");
	}

	public static void printLose() {
		System.out.println("負けです。");
	}

	/**
	 * 勝負結果を判定するメソッド
	 * @param dealer ディーラー
	 * @param person 指定された人
	 * @param card カード
	 */
	public static void judgeCount(List<Person> personList, List<Card> card) {

		for (int i = 1; i < personList.size(); i++) {
			System.out.println(personList.get(i).getName());
			// バーストした場合
			if (CountUtil.isBust(CountUtil.countNumber(personList.get(i).getCards()))) {
				printLose();
				continue;
			}

			// ディーラーがバーストした場合
			if (CountUtil.isBust(CountUtil.countNumber(personList.get(0).getCards()))) {
				printWin();
				continue;
			}

			// ディーラーのカウントより小さい場合
			if (CountUtil.countNumber(personList.get(i).getCards()) < CountUtil
					.countNumber(personList.get(0).getCards())) {
				printLose();
				continue;
			}

			// ディーラーのカウントより大きい場合
			if (CountUtil.countNumber(personList.get(0).getCards()) < CountUtil
					.countNumber(personList.get(i).getCards())) {
				printWin();
				continue;
			}

			// カウントが同じ場合

			if (CountUtil.countNumber(personList.get(i).getCards()) == 21) {
				if (personList.get(i).getCards().size() == 2
						&& personList.get(0).getCards().size() != 2) {
					printWin();
					continue;
				}

				if (personList.get(i).getCards().size() != 2
						&& personList.get(0).getCards().size() == 2) {
					printWin();
					continue;
				}
			}

			System.out.println("カウントが同じため、カードを1枚引きます。");
			System.out.println("ディーラーよりも数字が大きければ勝ちです。");
			while (true) {
				List<Card> d = new ArrayList<>();
				List<Card> p = new ArrayList<>();
				Random r = new Random();

				d.add(card.get(r.nextInt(card.size())));
				p.add(card.get(r.nextInt(card.size())));

				int dRank = Card.cardRank(d);
				int pRank = Card.cardRank(p);

				System.out.print(personList.get(0).getName() + ":");
				Card.openCard(d.get(0));
				System.out.print(personList.get(i).getName() + ":");
				Card.openCard(p.get(0));

				if (dRank == pRank) {
					continue;
				}
				if (dRank < pRank) {
					printWin();
					break;
				}
				printLose();
				break;
			}

		}
	}
}
