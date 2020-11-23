package javakadai_portfolio.calculator.calc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 計算結果に関するクラス
 *
 * @author たかくら
 *
 */
public class Response {

	/** 演算結果 */
	private BigDecimal CalculatorResultNum;

	/** ゼロ除算フラグ */
	private boolean zeroDivisionLabelFlag = false;

	public BigDecimal getCalculatorResultNum() {
		return CalculatorResultNum;
	}

	public List<String> formattedInput;

	public List<String> getFormattedInput() {
		formattedInput = new ArrayList<>();
		return formattedInput;
	}

	public void setFormattedInput(List<String> formattedInput) {
		this.formattedInput = formattedInput;
	}

	public void setCalculatorResultNum(BigDecimal calculatorResultNum) {
		this.CalculatorResultNum = calculatorResultNum;
	}

	public boolean isZeroDivisionLabelFlag() {
		return zeroDivisionLabelFlag;
	}

	public void setZeroDivisionLabelFlag(boolean zeroDivisionLabelFlag) {
		this.zeroDivisionLabelFlag = zeroDivisionLabelFlag;
	}

}
