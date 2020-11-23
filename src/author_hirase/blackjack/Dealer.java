package author_hirase.blackjack;

import java.util.List;
import java.util.Random;

public class Dealer extends Person {
	public Dealer(){
		this.setName("ディーラー");
	}

	/**
	 * カードを配る
	 * @param card
	 * @param allCpu
	 * @param cpu
	 * @param player
	 */
	public void giveOutCards(List<Card> card, List<Person> personList){
		for(int i = 0; i < 2; i++){
			for(Person person : personList){
				person.getCards().add(getCard(card));
			}
		}
	}

	/**
	 * カードを1枚引く
	 * @param cards
	 * @param card
	 */
	public static void drawCard(List<Card> cards, List<Card> card){
		cards.add(getCard(card));
	}

	/**
	 * カードを1枚取得
	 * @param card
	 * @return
	 */
	public static Card getCard(List<Card> card){
		Random random = new Random();
		return card.get(random.nextInt(card.size()));
	}

	/**
	 * アップカードを表示するメソッド
	 */
	public void showCard(){
		System.out.println(getName());
		Card.openCard(getCards().get(0));
	}

	/**
	 * 16以上になるまでカードを引く
	 */
	public void doAction(List<Card> card){
		while(CountUtil.countNumber(getCards()) <= 16){
			super.doAction(card);
		}
	}

	@Override
	public void notDealer(List<Card> card) {
		return;
	}

}
