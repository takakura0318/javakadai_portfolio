package author_hirase.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BlackJack {
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	public void startGame() {
		try {
			System.out.println("ブラックジャックを始めます。");
			System.out.println("CPUの人数を入力してください。");
			int allCpu;
			while (true) {
				try {
					String str = reader.readLine();
					allCpu = Integer.parseInt(str);
					break;
				} catch (NumberFormatException e) {
					System.out.println("整数を入力してください。");
				}
			}
			play(allCpu);
		} catch (IOException e) {
			System.out.println("エラーが発生しました。");
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("ゲームを終了します。");
		}
	}

	public void play(int allCpu) {
		try {
			// トランプを生成
			List<Card> card = Card.newDeck();

			// ディーラーと全てのプレイヤーを生成
			Dealer dealer = new Dealer();
			Player player = new Player();
			List<Cpu> cpu = new ArrayList<>();

			List<Person> personList = new ArrayList<>();
			personList.add(dealer);
			personList.add(player);

			for (int i = 0; i < allCpu; i++) {
				cpu.add(new Cpu());
				personList.add(cpu.get(i));
			}

			int allPlayer = allCpu + 1;
			int allBust = 0;

			// カードを配る
			dealer.giveOutCards(card, personList);

			// カードを表示
			for (Person person : personList) {
				person.showCard();
			}

			// HIT or STAND
			for (Person person : personList) {
				person.notDealer(card);
				if (CountUtil.isBust(CountUtil.countNumber(person.getCards()))) {
					allBust++;
				}
			}

			// プレイヤー、Cpuがすべてバーストした場合はゲーム終了
			if (allBust == allPlayer) {
				for (Person person : personList) {
					if (person instanceof Dealer) {
						continue;
					}
					System.out.println(person.getName());
					Result.printLose();
				}
				return;
			}

			// ディーラーのカードを表示
			getDealer(personList).openCardAndCount(getDealer(personList).getCards());

			// ディーラーがHIT or STANDか
			getDealer(personList).doAction(card);

			// 最終カードを表示
			System.out.println("\n--最終結果--");
			showAll(personList);

			// 勝負結果
			Result.judgeCount(personList, card);

		} catch (Exception e) {
			System.out.println("エラーが発生しました。");
		}
	}

	/**
	 * ディーラー、プレイヤー、CPUの情報を表示するメソッド
	 * @param personList
	 */
	public void showAll(List<Person> personList) {
		for (Person person : personList) {
			person.openCardAndCount(person.getCards());
		}
	}

	/**
	 * ディーラーの情報を取得するメソッド
	 * @param personList
	 * @return ディーラーの情報
	 */
	public Person getDealer(List<Person> personList) {
		return personList.get(0);
	}

	/**
	 * カードを引くか判定するメソッド
	 * @param str 入力値
	 * @return boolean
	 */
	public boolean isDrawCard(String str) {
		return str.equals("y");
	}

}
