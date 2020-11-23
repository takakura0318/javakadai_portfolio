package author_hirase.blackjack;

import java.util.ArrayList;
import java.util.List;

public abstract class Person {
	String name;
	List<Card> cards = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	/**
	 * それぞれのプレイヤーによって処理を行う抽象メソッド
	 * @param card
	 */
	public abstract void notDealer(List<Card> card);

	/**
	 * プレイヤー名、カード、カウントを表示する
	 * @param cards
	 */
	public void openCardAndCount(List<Card> cards) {
		System.out.println(getName());
		Card.openCards(cards);
		CountUtil.count(cards);
	}

	/**
	 * Player Cpu Dealerで共通な処理
	 * @param card
	 */
	public void doAction(List<Card> card) {
		Dealer.drawCard(getCards(), card);
		openCardAndCount(getCards());
	}

	public void showCard() {
		this.openCardAndCount(this.getCards());
	}
}
