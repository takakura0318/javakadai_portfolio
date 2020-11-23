package javakadai_portfolio.babanuki.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javakadai_portfolio.babanuki.GameMaster;
import javakadai_portfolio.babanuki.Hand;
import javakadai_portfolio.babanuki.Table;

/**
 * @author yt031
 *
 */
public abstract class AllHuman {

	/** コンストラクタ */
	private String name;

	/** 手札 */
	protected Hand myHand;

	/** テーブル */
	protected Table table;

	protected GameMaster gameMaster;

	/**
	 * コンストラクタ
	 */
	public AllHuman(Table table, GameMaster gameMaster) {
		this.myHand = new Hand();
		this.table = table;
		this.gameMaster = gameMaster;
	}

	/**
	 * 手札に同じカードがあった場合、捨てる
	 */
	public void doDiscardCard() {

		// 現在の手札を取得する
		List<String> myHandList = myHand.getMyHand();

		// 現在の手札の数字リストを取得する
		List<Integer> handNumList = new ArrayList<>();
		for (String myHandCard : myHandList) {
			String[] myHandCardSplit = myHandCard.split(" ");
			int myHandCardNum = Integer.parseInt(myHandCardSplit[1]);
			handNumList.add(myHandCardNum);
		}
		// 現在の手札の数字リストを小さい順に並び変える
		// ここはint型じゃないとソートされない
		Collections.sort(handNumList);

		// 1～13の出た回数をカウント数を保持する変数を宣言
		int drawCnt1 = 0;
		int drawCnt2 = 0;
		int drawCnt3 = 0;
		int drawCnt4 = 0;
		int drawCnt5 = 0;
		int drawCnt6 = 0;
		int drawCnt7 = 0;
		int drawCnt8 = 0;
		int drawCnt9 = 0;
		int drawCnt10 = 0;
		int drawCnt11 = 0;
		int drawCnt12 = 0;
		int drawCnt13 = 0;

		for (Integer handNum : handNumList) {
			if (handNum == 1) {
				drawCnt1++;
			}
			if (handNum == 2) {
				drawCnt2++;
			}
			if (handNum == 3) {
				drawCnt3++;
			}
			if (handNum == 4) {
				drawCnt4++;
			}
			if (handNum == 5) {
				drawCnt5++;
			}
			if (handNum == 6) {
				drawCnt6++;
			}
			if (handNum == 7) {
				drawCnt7++;
			}
			if (handNum == 8) {
				drawCnt8++;
			}
			if (handNum == 9) {
				drawCnt9++;
			}
			if (handNum == 10) {
				drawCnt10++;
			}
			if (handNum == 11) {
				drawCnt11++;
			}
			if (handNum == 12) {
				drawCnt12++;
			}
			if (handNum == 13) {
				drawCnt13++;
			}
		}

		// トランプカードの1～13の引いた数を保持するListを宣言する
		List<Integer> drawCntList = new ArrayList<>();
		drawCntList.add(drawCnt1);
		drawCntList.add(drawCnt2);
		drawCntList.add(drawCnt3);
		drawCntList.add(drawCnt4);
		drawCntList.add(drawCnt5);
		drawCntList.add(drawCnt6);
		drawCntList.add(drawCnt7);
		drawCntList.add(drawCnt8);
		drawCntList.add(drawCnt9);
		drawCntList.add(drawCnt10);
		drawCntList.add(drawCnt11);
		drawCntList.add(drawCnt12);
		drawCntList.add(drawCnt13);

		// 削除用のインデック番号を保持するリストを宣言
		List<Integer> removeList = new ArrayList<>();
		// removeするインデック番号を保持する変数を宣言
		int targetRemoveIndex = 1;

		for (Integer drawNumCnt : drawCntList) {

			// もしもトランプカードの数字が2もしくは3の場合
			if (drawNumCnt == 2 || drawNumCnt == 3) {
				// ワンペア削除するためにインデック番号を追加
				// handNumListからtargetRemoveIndex(1～13のどれか)を検索して、そのインデック番号を追加する
				removeList.add(handNumList.indexOf(targetRemoveIndex));
				removeList.add(handNumList.indexOf(targetRemoveIndex));
			}

			// もしもトランプカードの数字が4の場合
			if (drawNumCnt == 4) {
				// ツーペア削除するためにインデック番号を追加
				// handNumListからtargetRemoveIndex(1～13のどれかに)を検索して、そのインデック番号を追加する
				removeList.add(handNumList.indexOf(targetRemoveIndex));
				removeList.add(handNumList.indexOf(targetRemoveIndex));
				removeList.add(handNumList.indexOf(targetRemoveIndex));
				removeList.add(handNumList.indexOf(targetRemoveIndex));
			}

			targetRemoveIndex++;
		}

		// 削除用のインデック番号を保持するリストを降順に並び変える
		Collections.sort(removeList, Collections.reverseOrder());

		// 手札を削除する
		for (Integer targetRemove : removeList) {
			handNumList.remove(targetRemove.intValue());
		}

		// 残った数字リストから新しい手札を作る
		List<String> newHandList = new ArrayList<>();
		for (Integer value : handNumList) {
			String handAfterDelete = String.valueOf(value);
			String newHandCard = notUseStreamAPI(myHandList, handAfterDelete);
			newHandList.add(newHandCard);
		}
		myHand.setMyHand(newHandList); // 新しい手札をセットする

	}

	// 新しいトランプカードのNumberで検索してトランプカードを取得する
	private String notUseStreamAPI(List<String> myHandList, String search) {
		for (String value : myHandList) {
			String[] handArr = value.split(" ");
			String handNum = handArr[1];

			if (handNum.equals(search)) {

				return value;
			}
		}
		return "";
	}

	/**
	 * ババ抜きをプレイする
	 */
	public abstract void play(AllHuman nextPlayer);

	/**
	 * 相手に手札を見せる
	 */
	public void showMeHandList() {
		// TODO 相手に手札を見せる
		System.out.println("【" + name + "の引けるカードです】");
		myHand.showHand();

		// もしも残り1枚の場合、Gameマスターに知らせる、break?
		if (isOnlyHand()) {
			//
			gameMaster.declareWin(this);
		}

		// もしも残り1枚以上の場合、Gameマスターに知らせる、break?
		if (!isOnlyHand() && this instanceof Cpu) {
			System.out.println("何番目のカードを引くかい?\n");
		}

	}

	/**
	 * 手札が残り1枚かどうかを判定する
	 */
	protected boolean isOnlyHand() {

		if (myHand.getMyHand().size() == 1)
			return true;

		return false;
	}

	/**
	 * 手札が残り0枚かどうかを判定する
	 */
	protected boolean isZeroHand() {

		if (myHand.getMyHand().size() == 0)
			return true;

		return false;
	}

	public abstract void test(AllHuman nextPlayer);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hand getMyHand() {
		return myHand;
	}

	public void setMyHand(Hand myHand) {
		this.myHand = myHand;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

}
