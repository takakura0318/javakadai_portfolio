package author_hirase.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Card {
	public enum Rank {
		ACE("A"), DEUCE("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT("8"), NINE(
				"9"), TEN("10"), JACK("J"), QUEEN("Q"), KING("K");
		private String value;

		private Rank(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	}

	public enum Suit {
		CLUBS("♣"), DIAMONDS("♦"), HEARTS("♥"), SPADES("♠");
		private String suitMark;

		private Suit(String suitMark) {
			this.suitMark = suitMark;
		}

		public String getSuitMark() {
			return this.suitMark;
		}
	}

	private final Rank rank;
	private final Suit suit;

	private Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	private static List<Card> deck = new ArrayList<Card>();
	static {
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				deck.add(new Card(rank, suit));
			}
		}
	}

	public static ArrayList<Card> newDeck() {
		return new ArrayList<Card>(deck);
	}

	/**
	 * 指定されたカードを表示する
	 * @param card
	 */
	public static void openCard(Card card) {
		String trump = card.getSuit().getSuitMark() + card.getRank().getValue();
		System.out.println(trump);
	}

	/**
	 * リストに入っているカードを全て表示する
	 * @param cards カード
	 */
	public static void openCards(List<Card> cards) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cards.size(); i++) {
			sb.append(cards.get(i).getSuit().getSuitMark() + cards.get(i).getRank().getValue());
			if (cards.size() - 1 != i) {
				sb.append(" ");
			}
		}
		System.out.println(sb.toString());
	}

	/**
	 * カードがAであるか判定するメソッド
	 * @param card
	 * @return
	 */
	public static boolean isAce(Card card) {
		if (card.getRank().getValue() == "A") {
			return true;
		}
		return false;
	}

	/**
	 * Aが含まれているか判定するメソッド
	 * @param person 指定された人
	 * @return boolean
	 */
	public static boolean containsA(List<Card> cards) {
		for (Card card : cards) {
			if (isAce(card)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * カードの値を数値にするメソッド
	 * @param card 指定されたカード
	 * @return カードの数値
	 */
	public static int cardRank(List<Card> card) {
		String str = card.get(0).getRank().getValue();
		switch (str) {
		case "A":
			return 1;
		case "J":
			return 11;
		case "Q":
			return 12;
		case "K":
			return 13;
		default:
			return Integer.parseInt(str);
		}
	}

}
