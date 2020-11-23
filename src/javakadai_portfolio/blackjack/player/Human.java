package javakadai_portfolio.blackjack.player;

import java.util.ArrayList;
import java.util.List;

/**
 * 人間クラス(抽象クラス)
 *
 * @author たかくら
 */
public class Human {

	/** 名前(あなたorディラーorCPU) */
	private String name;

	/** 手札リスト */
	private List<String> handList = new ArrayList<>();

	/** 手札合計 */
	private int handSum;

	/** バーストをしているかどうか */
	private boolean burstFlag = false;

	/** ブラックジャックになったかどうか */
	private boolean blackJackFlag = false;

	/** Aを引いた回数だけカウントする */
	private int aceCnt = 0;

	/** 人間のタイプ */
	private String humanType;

	/**
	 * 3枚目以降の処理
	 *
	 * @param human
	 *            人間
	 * @param dealer
	 *            ディーラー
	 */
	public void processingAfterTheThirdPages(Human human, Dealer dealer) {
		human.setBlackJackFlag(true);
		System.out.println("ブラックジャックを達成しました。\n");
	}

	/**
	 * ブラックジャックかどうか
	 */
	public boolean isBlackJackJudge() {
		// もしも、21と等しい場合は、
		if (handSum == 21)
			return true;

		return false;

	}

	/**
	 * バーストしているかどうか
	 *
	 * @param handPoint
	 *            手札の合計
	 */
	public boolean isBursted(int handPoint) {
		// もしも、21以下だった場合は
		if (handPoint <= 21) {
			return false;
		} else {
			return true;
		}

	}

	// 以下はgetterとsetter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getHandList() {
		return handList;
	}

	public void setHandList(List<String> handList) {
		this.handList = handList;
	}

	public int getHandSum() {
		return handSum;
	}

	public void setHandSum(int handSum) {
		this.handSum = handSum;
	}

	public int getAceCnt() {
		return aceCnt;
	}

	public void setAceCnt(int aceCnt) {
		this.aceCnt = aceCnt;
	}

	public boolean isBurstFlag() {
		return burstFlag;
	}

	public void setBurstFlag(boolean burstFlag) {
		this.burstFlag = burstFlag;
	}

	public boolean isBlackJackFlag() {
		return blackJackFlag;
	}

	public void setBlackJackFlag(boolean blackJackFlag) {
		this.blackJackFlag = blackJackFlag;
	}

	public String getHumanType() {
		return humanType;
	}

	public void setHumanType(String humanType) {
		this.humanType = humanType;
	}

}
