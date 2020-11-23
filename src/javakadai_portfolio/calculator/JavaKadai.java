package javakadai_portfolio.calculator;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * JavaKadaiクラス(抽象クラス)。 Java課題の大枠のクラス。デザインパターン「Template Method」を使用しています。
 *
 * @author たかくら
 */
public abstract class JavaKadai {
	/** 標準入力 */
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

	/**
	 *
	 * 抽象メソッド：始まりの処理
	 *
	 */
	public abstract void startProcess();

	/**
	 *
	 * 抽象メソッド：終わりの処理
	 *
	 */
	public abstract void endProcess();

	/**
	 *
	 * 抽象メソッド：中間処理(電卓のメイン処理)
	 *
	 */
	public abstract void middleProcess();

	/**
	 *
	 * 抽象メソッド：JavaKadaiクラスを実行する 具体的な処理は継承先で実装する
	 *
	 */
	public void execute() {
		// はじまりの処理
		startProcess();

		// メイン処理
		middleProcess();

		// 終わりの処理
		endProcess();
	}
}
