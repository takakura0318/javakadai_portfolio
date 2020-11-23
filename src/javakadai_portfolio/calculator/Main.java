package javakadai_portfolio.calculator;

import javakadai_portfolio.calculator.calc.Calculator;

/**
 * 電卓を実行するMainクラス
 *
 * @author たかくら
 */
public class Main {

	/**
	 * 電卓を実行するmainメソッド
	 */
	public static void main(String[] args) {

		// Java課題クラスを初期化
		JavaKadai javaKadai = null;

		// 電卓クラスのオブジェクトをJava課題クラスに代入する
		javaKadai = new Calculator();

		// Java課題クラス(電卓クラスを実行)
		javaKadai.execute();

	}

}
