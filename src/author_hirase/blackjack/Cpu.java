package author_hirase.blackjack;

import java.util.List;

public class Cpu extends Person {
	public static int cpuCount = 0;

	public Cpu() {
		cpuCount++;
		this.setName("CPU" + cpuCount);
	}

	/**
	 * 16以上になるまでカードを引く
	 */
	public void doAction(List<Card> card) {
		while (CountUtil.countNumber(getCards()) <= 16) {
			super.doAction(card);
		}
	}

	@Override
	public void notDealer(List<Card> card) {
		this.doAction(card);
	}

}
