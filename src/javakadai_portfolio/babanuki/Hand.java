package javakadai_portfolio.babanuki;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Hand {

	/** 手札 */
	private List<String> myHand = new ArrayList<>();

	/** 引いたカードを手札に加える */
	public void addCard(String card) {

		// 同じカードがない場合は手札に引いたカードを加える
		myHand.add(card);

	}

	/** 手札をシャッフルする */
	public void shuffle() {
		Collections.shuffle(myHand);
	}

	/** 現在の手札を表示する */
	public void showHand() {
		int myHandCnt = 1;
		for (String string : myHand) {
			System.out.println(myHandCnt++ + "枚目:" + string);
		}
	}

	/** getter、setter */
	public List<String> getMyHand() {
		return myHand;
	}

	public void setMyHand(List<String> myHand) {
		this.myHand = myHand;
	}

}
