package javakadai_portfolio.babanuki;

import java.util.ArrayList;

public class Table {

	// 捨てられたカードを保持するためにリスト
	private ArrayList<String> disposedCards = new ArrayList<>();

	// カード捨てる
	public void disposeCard(String card) {
		disposedCards.add(card);
	}

	// 捨てられたカードを表示する
	public void showDisposedCards() {
		for (String string : disposedCards) {
			System.out.println(string);

		}
	}

}
