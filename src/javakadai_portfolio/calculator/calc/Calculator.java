package javakadai_portfolio.calculator.calc;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javakadai_portfolio.calculator.JavaKadai;

/**
 * Calculatorクラス(JavaKadaiクラスを継承した具象クラス)。 電卓を実行するクラス。
 *
 * @author たかくら
 */
public class Calculator extends JavaKadai {

	/** 演算子リスト */
	private final char[] operatorList = { '+', '-', '*', '/', '^', '(', ')', '<' };

	/** 演算子リスト */
	private String[] operatorList2 = { "+", "-", "*", "/", "^" };

	/** 演算子リスト */
	private String[] operatorList3 = { "+", "-", "*", "/" };

	/** ユーザの入力形式 */
	private List<String> formattedInput;

	@Override
	public void startProcess() {
		System.out.println("始まりました。");
	}

	@Override
	public void endProcess() {
		System.out.println("終わりました。");
	}

	@Override
	public void middleProcess() {

		// 入力値を格納する変数「dentakInput」を初期化
		String dentakInput = null;
		// インスタンス化
		Calculator calc = new Calculator();

		// ゼロ除算がされた場合に備えてラベルブロックを付与。ゼロ除算がされた場合はLabelを辿って(Labelは全部で3つ)ここのLabelに戻って来る
		// 正しい値が入力されるまでループを回す
		Label1: while (true) {
			try {
				while (true) {
					System.out.println("数字を入力してください");
					dentakInput = reader.readLine();
					// 「0除算しているかどうか」と「数式かどうか」を確認する
					if (isNumOrOperator(dentakInput)) {
						break;
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 電卓を実行する
			Response res = calc.calcExecute(dentakInput);

			// もしもラベルフラグがある場合
			if (res.isZeroDivisionLabelFlag()) {
				res.setZeroDivisionLabelFlag(false);
				continue Label1; // Label1ブロックをスキップする
			}

			// BigDecimalをカンマ区切りするための前準備
			DecimalFormat df1 = new DecimalFormat("###,###.###");

			// 計算結果
			System.out.println(df1.format(res.getCalculatorResultNum()));

		}

	}

	/**
	 * 数式かどうかを判定する。「0～9」もしくは「+,-,*,/,^」のどれかだった場合trueを返す
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return true:正しい値 flase:間違った値
	 */
	private static boolean isNumOrOperator(String dentakInput) {

		// 入力値の長さだけループを回す
		for (int i = 0; i < dentakInput.length(); i++) {
			// 入力値の先頭から一文字取得する
			String oneCharacter = String.valueOf(dentakInput.charAt(i));
			// 入力文字が以下のいずれかだった場合はtrue
			if (oneCharacter.equals("0") || oneCharacter.equals("1") || oneCharacter.equals("2")
					|| oneCharacter.equals("3") || oneCharacter.equals("4")
					|| oneCharacter.equals("5")
					|| oneCharacter.equals("6") || oneCharacter.equals("7")
					|| oneCharacter.equals("8")
					|| oneCharacter.equals("9") || oneCharacter.equals("+")
					|| oneCharacter.equals("-")
					|| oneCharacter.equals("*") || oneCharacter.equals("/")
					|| oneCharacter.equals("^")
					|| oneCharacter.equals("(") || oneCharacter.equals(")")) {

				return true;
			}
		}
		return false;

	}

	/**
	 * 電卓処理を実行する。「入力チェック→括弧の中身を評価→演算→計算結果を返す」の流れ
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return res Responseクラス(計算結果に関するクラス)
	 */
	private Response calcExecute(String dentakInput) {
		Response res = new Response();

		// ゼロ除算がされた場合に備えてラベルブロックを付与。
		Label2: {
			/*** 入力チェック ***/
			// 入力値のフォーマットしたものを取得する
			this.formattedInput = isFormattedInput(dentakInput);

			/*** 括弧がある場合の処理 ***/
			for (int i = 0; i < formattedInput.size() - 1; i++) {
				// もしも入力値リストに左括弧がある場合
				if (formattedInput.get(i).equals("(")) {
					// EvaluateParenthese.condense(入力値リスト , 左括弧があるインデックスの番号)
					res = evaluationParenthese(formattedInput, i);
					i--;
				}
			}

			/*** 計算 ***/
			final char[][] orderOfOperations = new char[][] { { '^' }, { '*', '/' }, { '+', '-' } };

			for (char[] operators : orderOfOperations) {
				for (int i = 1; i < formattedInput.size(); i++) {
					char inputOp = formattedInput.get(i).charAt(0);
					for (char op : operators) {
						// もしも入力された演算子と電卓の演算子(^、*、/、+、-、^)のどれかと一致していた場合
						if (op == inputOp) {
							// 計算を実行したものをResponse型の変数resに格納する
							res = condenseExpression(op, i);

							// もしもラベルフラグがある場合
							if (res.isZeroDivisionLabelFlag()) {
								break Label2; // Label2ブロックを抜ける
							}

							// オペランドの右側を削除する
							formattedInput.remove(i + 1);
							// 演算子を削除します
							formattedInput.remove(i);
							// Listの先頭に計算結果を再代入する
							formattedInput.set(i - 1,
									res.getCalculatorResultNum().toString());

							i--; // これがないとバグ発生する
						}

					}
				}
			}

			/*** 計算結果をセットする ***/
			// もしも入力値リストのサイズが1の場合(計算した後のリストのサイズが1になる場合||そもそもオペランドが1つしか入力されていない場合に備える)
			if (formattedInput.size() == 1) {
				BigDecimal result = null;
				try {
					result = new BigDecimal(formattedInput.get(0));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

				res.setCalculatorResultNum(result);

			}

		}

		return res; // 計算結果を返す

	}

	/**
	 * 入力値リストに括弧があった場合、括弧の値を評価し、評価後の値を入力値リストに再代入するメソッド
	 *
	 * @param formattedInput
	 *            入力値リスト
	 * @param leftParenthesis
	 *            入力値リストの左括弧のがあるインデックス番号
	 * @return res Responsクラス
	 */
	private Response evaluationParenthese(List<String> formattedInput, int leftParenthesis) {
		// HashMapの[key:左括弧が見つかったインデックスを格納 value:右括弧が見つかったインデックスを格納
		HashMap<Integer, Integer> bracketedIndex = BracketedIndex.getBracketedIndex(formattedInput);

		// HashMapのvalue(右括弧が見つかったインデックス番号を取得する)
		int end = bracketedIndex.get(leftParenthesis);

		// 左括弧から右括弧までのインデックスを切り抜いた部分を取得する
		String dentakuInput = formattedInput.subList(leftParenthesis + 1, end).toString();

		// Calculator(電卓クラス)をインスタンス生成
		Calculator newCalculator = new Calculator();

		// 左括弧から右括弧までのインデックスを切り抜いた部分を計算し、計算結果を変数resに格納
		Response res = newCalculator
				.calcExecute(dentakuInput.replace(",", "").replace("[", "").replace("]", ""));

		// IndexOutOfBoundsエラーを回避する為に、後ろから要素を削除します
		for (int i = end; i > leftParenthesis; i--) {
			this.formattedInput.remove(i);
		}

		// 括弧の中を評価した数値をリストに再代入する
		this.formattedInput.set(leftParenthesis, res.getCalculatorResultNum().toString());

		return res; // 計算結果に関するクラスを返す

	}

	/**
	 * 四則演算とべき乗の演算を実行するメソッド
	 *
	 * @param op
	 *            演算子
	 * @param i
	 *            演算子がのインデックス番号
	 * @return res Responsクラス
	 */
	private Response condenseExpression(char op, int i) {
		// Responseクラス(計算結果に関するクラス)のインスタンスを生成
		Response res = new Response();

		// ゼロ除算がされた場合に備えてラベルブロックを付与
		LABEL3: {
			// 演算子の左オペランドを取得する
			Response x = catchNumberException(formattedInput.get(i - 1));
			// 演算子の右オペランドを取得する
			Response y = catchNumberException(formattedInput.get(i + 1));

			BigDecimal resultNum = null;

			// 四則演算とべき乗の計算
			if (op == '^') {
				// もしも右オペランドが負の値だった場合(べき乗計算の右オペランドに負の数が指定された場合を想定)
				if (isNegativeValue(y.getCalculatorResultNum().intValue())) {
					// 計算結果を取得する
					resultNum = getCalculation(x.getCalculatorResultNum().intValue(),
							y.getCalculatorResultNum().intValue(), resultNum);
					res.setCalculatorResultNum(resultNum);
					return res;
				}

				resultNum = x.getCalculatorResultNum().pow(y.getCalculatorResultNum().intValue());

			}

			if (op == '/') {
				// もしもゼロ除算がされた場合は
				if (y.getCalculatorResultNum().toString().equals("0")) {
					System.out.println("ゼロ除算しています");
					res.setZeroDivisionLabelFlag(true);
					break LABEL3;
				}
				resultNum = x.getCalculatorResultNum().divide(y.getCalculatorResultNum());

			}

			if (op == '*') {
				resultNum = x.getCalculatorResultNum().multiply(y.getCalculatorResultNum());

			}
			if (op == '-') {
				resultNum = x.getCalculatorResultNum().subtract(y.getCalculatorResultNum());

			}
			if (op == '+') {
				resultNum = x.getCalculatorResultNum().add(y.getCalculatorResultNum());

			}
			res.setCalculatorResultNum(resultNum);
			return res;
		}
		return res;
	}

	/**
	 * べき乗の右オペランドがマイナスだった場合の計算をする
	 *
	 * @param x
	 *            左オペランド
	 * @param y
	 *            右オペランド
	 * @param resultNum
	 *            計算結果を格納する箱
	 * @return resultNum べき乗の右オペランドがマイナスだった場合計算結果
	 */
	private BigDecimal getCalculation(int x, int y, BigDecimal resultNum) {

		int n = Math.abs(y);
		int result = 1;
		double result2 = 0;

		for (int j = 1; j <= n; j++) {
			result = result * x;
		}
		result2 = (double) 1 / result;
		resultNum = BigDecimal.valueOf(result2);
		return resultNum;
	}

	/**
	 * 右オペランドが負の値かどうか
	 *
	 * @param rightOperand
	 *            右オペランド
	 * @return true:負の値である false:正の値である
	 */
	private boolean isNegativeValue(int rightOperand) {

		// もしも負の数だった場合はtrueを返す
		if (Math.signum(rightOperand) == -1)
			return true;

		return false;
	}

	/**
	 * オペランドをResponseクラスのCalculatorResultNumフィールドにセットするメソッド
	 *
	 * @param operand
	 *            オペランド
	 * @return res Responsクラス(計算結果に関するクラス)
	 */
	private Response catchNumberException(String operand) {
		Response res = new Response();
		BigDecimal result = null;
		try {
			result = new BigDecimal(operand);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// 値をセットする
		res.setCalculatorResultNum(result);

		return res;
	}

	/**
	 * ユーザの入力値をチェックするメソッド
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return formattedList 入力値リスト(すべての演算子とオペランドのリスト)
	 */
	private List<String> isFormattedInput(String dentakInput) {

		// コンマを無くす
		dentakInput = dentakInput.replaceAll(",", "");
		// スペース(空白)を無くす
		dentakInput = dentakInput.replaceAll("\\s", "");
		// 行の末尾のスペース(空白)を無くす
		dentakInput = dentakInput.replace("$", "");
		// 閉じ括弧の直前の文字が演算子の場合の処理
		dentakInput = caseClosingParenthesisLeftIsOperator(dentakInput);
		// 入力値の末尾が演算子だった場合の処理
		dentakInput = caseEndsWithoperator(dentakInput);
		// 閉じかっこなしで入力された場合は、閉じかっこを補完する
		dentakInput = createForClosingParenthesis(dentakInput);

		// 空のLinkedListを宣言
		LinkedList<String> formattedList = new LinkedList<>();

		// 右オペランドのインデックスを取得する変数「targetRightOperand」を初期化
		int targetRightOperandIdx = 0;

		for (int i = 0; i < dentakInput.length(); i++) {
			for (char operator : operatorList) {
				// もしも「電卓の演算子【「+」,「-」,「*」,「/」,「^」,「(」,「)」,「<」】」と「入力された演算子」が一致した場合
				if (operator == dentakInput.charAt(i)) {
					// 演算子の前の値を変数「leftOperand」に格納。(例)「15+5」が入力された場合は15が代入される
					String leftOperand = dentakInput.substring(targetRightOperandIdx, i);
					// もしも上記の変数「leftOperand」の長さが0文字以上だった場合
					if (leftOperand.length() > 0)
						formattedList.add(leftOperand);
					// 演算子の部分を「formattedList」に追加。
					formattedList.add(Character.toString(operator));
					targetRightOperandIdx = i + 1;
					break;
				}
			}
		}

		// 変数「rightOperand」に右オペランドを取得する
		String rightOperand = dentakInput.substring(targetRightOperandIdx);

		// もしも上記の変数が空ではない場合
		if (!rightOperand.equals("")) {
			// formattedListに右オペランドを追加
			formattedList.add(rightOperand);
		}

		// リストの最初に「＋」がある場合は削除する
		if (formattedList.get(0).equals("+"))
			formattedList.remove(0);

		// 入力値リストの接頭辞にマイナス演算子がある場合はこのメソッドが動く
		operatorFormatting(formattedList);

		return formattedList;
	}

	/**
	 * 閉じ括弧の直前の文字が演算子だった場合、左括弧の隣のオペランドから閉じ括弧の隣の演算子の隣のオペランドまで計算したものを閉じ括弧の直前に補完する
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return dentakInput
	 *         左括弧の隣のオペランドから閉じ括弧の隣の演算子の隣のオペランドまで計算したものを閉じ括弧の直前に補完された入力値
	 */
	private String caseClosingParenthesisLeftIsOperator(String dentakInput) {

		Response res = null;
		int rightBrancketIdx = 0;
		int leftBrancketIdx = 0;
		int opIdx = 0;

		String kirinuki = null;

		// 入力値の長さだけループを回す
		for (int i = 0; i < dentakInput.length(); i++) {
			// 入力値の先頭から一文字取得する
			String oneCharacter = String.valueOf(dentakInput.charAt(i));

			// もしも右括弧がの場合
			if (oneCharacter.equals(")")) {

				rightBrancketIdx = i;

				for (String op : operatorList2) {
					// もしも右括弧の直前の文字が演算子だった場合
					if (String.valueOf(dentakInput.charAt(i - 1)).equals(op)) {
						opIdx = i - 1; // 右括弧の直前の文字が演算子だった場合の演算子のインデックス番号を取得する
						// lastIndexOf()メソッドで右括弧のインデックスの番号から左括弧が存在するインデックス番号を取得する
						leftBrancketIdx = dentakInput.lastIndexOf("(", rightBrancketIdx);

						// 左括弧の隣の文字から閉じ括弧の直前の文字である演算子までを切り抜く
						kirinuki = dentakInput.substring(leftBrancketIdx + 1, opIdx);
						// System.out.println("切り抜き部分@" + kirinuki);

						Calculator calculator = new Calculator();

						// 切り抜いた部分だけを計算させる
						res = calculator.calcExecute(kirinuki);

						// System.out.println("切り抜き部分の計算結果" +
						// res.getCalculatorResultNum());
						StringBuilder sb = new StringBuilder(dentakInput);
						// 切り抜いた部分の計算結果を右括弧の直前に補完する
						sb.insert(rightBrancketIdx, res.getCalculatorResultNum().toString());
						dentakInput = sb.toString();
						return dentakInput;
					}

				}
			}
		}

		return dentakInput;

	}

	/**
	 * 入力値の演算子の末尾が「+」「-」「*」「/」「^」の場合は、仕様書に沿って補完する
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return dentakInput 入力値が「^」演算子で終わる場合、末尾の直前のオペランドを入力値の末尾に補完された状態の入力値
	 */
	private String caseEndsWithoperator(String dentakInput) {

		for (String op : operatorList2) {
			// もしも入力値の末尾が演算子だった場合は、
			if (dentakInput.endsWith(op)) {
				// もしも演算時の末尾が「+」もしくは「-」だった場合
				if (dentakInput.endsWith("+") || dentakInput.endsWith("-")) {
					//
					String substringEndsWith = dentakInput.substring(0, dentakInput.length() - 1);
					Calculator newCalculator = new Calculator();
					Response res = newCalculator.calcExecute(substringEndsWith);
					System.out.println("「＋」もしくは「-」の末尾までの計算結果は" + res.getCalculatorResultNum());
					dentakInput += res.getCalculatorResultNum().toString();
					// System.out.println("電卓アプリの解釈値" + dentakInput);
					return dentakInput;
				}

				// もしも入力値の最初の演算子が「*」もしくは「/」だった場合
				if (dentakInput.endsWith("*") || dentakInput.endsWith("/")) {
					// 「+」もしくは「-」の右隣のオペランド～末尾の演算子の左オペランドまでの計算結果を末尾に補完したものを取得する
					dentakInput = caseEndsWithMultOrDivision(dentakInput);
				}

				// 末尾が「^」演算子の場合は、補完する
				if (dentakInput.endsWith("^")) {
					// 入力値が「^」演算子で終わる場合、末尾の直前のオペランドを入力値の末尾に補完したものを取得する
					dentakInput = casePowerOperatorGetDentakInput(dentakInput);
				}

			}
		}
		return dentakInput;
	}

	/**
	 * 入力値が「*」もしくは「/」演算子で終わる場合、「+」もしくは「-」の右隣のオペランド～
	 * 末尾の演算子の左オペランドまでの計算結果を末尾に補完したものを取得する
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return dentakInput 入力値が「^」演算子で終わる場合、末尾の直前のオペランドを入力値の末尾に補完された状態の入力値
	 */
	private String caseEndsWithMultOrDivision(String dentakInput) {
		// 三項演算子
		// 「+」もしくは「ー」から末尾の直前の文字までを切り抜いたものを変数「substringEndsWith」に格納する
		String substringEndsWith = dentakInput.lastIndexOf("+") != -1
				? dentakInput.substring(dentakInput.lastIndexOf("+") + 1, dentakInput.length() - 1)
				: dentakInput.substring(dentakInput.lastIndexOf("-") + 1, dentakInput.length() - 1);
		// System.out.println("末尾が「*」もしくは「/」から「+」もしくは「-」までを切り抜いたら" +
		// substringEndsWith);
		Calculator newCalculator = new Calculator();
		// 切り抜いた部分を計算する
		Response res = newCalculator.calcExecute(substringEndsWith);
		// 切り抜いた部分を補完する
		dentakInput += res.getCalculatorResultNum().toString();
		// System.out.println("電卓アプリの解釈値は" + dentakInput);
		return dentakInput;
	}

	/**
	 * 入力値が「^」演算子で終わる場合、末尾の直前のオペランドを入力値の末尾に補完する
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return dentakInput 入力値が「^」演算子で終わる場合、末尾の直前のオペランドを入力値の末尾に補完された状態の入力値
	 */
	private String casePowerOperatorGetDentakInput(String dentakInput) {

		int bekizyouIdx = 0;

		for (String opr : operatorList3) {
			// もしも演算子がある場合は
			if (dentakInput.lastIndexOf(opr, dentakInput.length()) != -1) {
				bekizyouIdx = dentakInput.lastIndexOf(opr, dentakInput.length());
			}
		}

		// 三項演算子
		dentakInput += bekizyouIdx > 0 ? dentakInput.substring(bekizyouIdx + 1, dentakInput.length() - 1)
				: dentakInput.substring(bekizyouIdx, dentakInput.length() - 1);

		return dentakInput;
	}

	/**
	 * 閉じかっこなしで入力された場合は、閉じかっこを補完する
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return dentakInput 閉じ括弧が補完された電卓の入力値
	 */
	private String createForClosingParenthesis(String dentakInput) {
		int leftBracketCnt = 0; // 左括弧の数を数えるカウント変数
		int rightBracketCnt = 0;// 右括弧の数を数えるカウント変数

		String[] ar = dentakInput.split("");

		// 左括弧と右括弧の数を数える
		for (String str : ar) {
			// もしも左括弧がだった場合
			if (str.equals("("))
				leftBracketCnt++;
			// もしも右括弧がだった場合
			if (str.equals(")"))
				rightBracketCnt++;
		}

		// もしも入力値に左括弧がある場合 && 左括弧の数が右括弧の数が同じではない場合
		if (dentakInput.contains("(") && leftBracketCnt != rightBracketCnt) {
			// 括弧の差 = 左括弧 - 右括弧
			int parenthesisDifference = leftBracketCnt - rightBracketCnt;
			// 括弧の差だけ右括弧を補完する
			for (int i = 0; i < parenthesisDifference; i++) {
				dentakInput += ")";
			}
		}
		return dentakInput;
	}

	/**
	 * ユーザの入力値にマイナスがある場合は調整する
	 *
	 * @param dentakInput
	 *            電卓の入力値
	 * @return formattedList 入力値リスト(すべての演算子とオペランドのリスト)
	 */
	private void operatorFormatting(LinkedList<String> formattedList) {
		for (int i = 0; i < formattedList.size(); i++) {
			String item = formattedList.get(i);

			// もしも入力値にマイナス演算子がある場合
			if (item.equals("-")) {
				handleNegative(formattedList, i - 1, i, i + 1);
			}
		}
	}

	/**
	 * 接頭辞、演算子、および接尾辞の位置を指定すると、ネガティブがフォーマットされます。与えられた formattedList [..、 "x"、
	 * "-"、 "num"、..]は[..、 "x"、 "+"、 "-num"、..]を返します where x = ")" or a number.
	 * For everything else removes "-" and concatinates "-" to post.
	 *
	 * @param formattedList
	 * @param pre
	 *            左側のオペランド
	 * @param op
	 *            インデックスの値が「-」であると仮定します
	 * @param post
	 *            右側のオペランド
	 * @return if processed correctly true, else false 正しく処理された場合は@return
	 *         true、それ以外の場合はfalse
	 */
	private boolean handleNegative(LinkedList<String> formattedList, int pre, int op, int post) {

		// もしも入力値のサイズがが0以下 又は 入力値のサイズが1以下の場合
		if (op >= formattedList.size() || post >= formattedList.size())
			return false;

		if (pre >= 0 && Character.isDigit(formattedList.get(pre).charAt(0))
				&& Character.isDigit(formattedList.get(post).charAt(0)))
			return true;

		// pre> = 0で、pre == "）"のときの値
		if (pre >= 0 && formattedList.get(pre).equals(")")) {
			formattedList.set(op, "+");
			formattedList.add(op + 1, "-1");
			formattedList.add(op + 2, "*");
			return true;
		}

		// opをpostと連結し、opを削除します
		if (op >= 0 && post >= 1 && post < formattedList.size()) {
			if (Character.isDigit(formattedList.get(post).charAt(0))) {
				formattedList.set(post, '-' + formattedList.get(post)); // この瞬間で[-,15,+,5]→[-15,+,5]に置き換わる
				formattedList.remove(op);
				return true;
			}
			// ポストが数値でない場合、それはオープニングパレシスまたは関数である可能性があります
			// この場合、前に[..、 "-1"、 "*"、..]を追加します
			if (pre >= 0) {
				formattedList.set(op, "+");
				formattedList.add(op + 1, "-1");
				formattedList.add(op + 2, "*");
			} else {
				formattedList.set(op, "-1");
				formattedList.add(op + 1, "*");
			}
			return true;
		}
		return false;

	}

}
