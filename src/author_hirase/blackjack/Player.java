package author_hirase.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Player extends Person {
	public Player() {
		this.setName("プレイヤー");
	}

	/**
	 * カードを引くか引かないかを選択し、処理を行う
	 */
	public void doAction(List<Card> card) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			while (CountUtil.countNumber(getCards()) <= 20) {
				System.out.println("カードを引きますか。(y/n)");
				String input = reader.readLine();
				if (input.equals("n")) {
					break;
				}
				if (input.equals("y")) {
					super.doAction(card);
				}
			}
		} catch (IOException e) {
			System.out.println("エラーが発生しました。");
		}
	}

	@Override
	public void notDealer(List<Card> card) {
		this.doAction(card);
	}

}
