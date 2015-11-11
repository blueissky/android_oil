package com.example.util;

import java.math.BigDecimal;

public class DecimalArithmetic {
	/**
	 * @param x 除数
	 * @param y 被除数
	 * @param scale 保留位数
	 * 四舍五入
	 * 2015-11-3 段彬彬  首次创建
	 */
	public static double  setDivide(double x, double y, int scale) {
		BigDecimal x1 = new BigDecimal(x);
		BigDecimal y1 = new BigDecimal(y);
		return x1.divide(y1, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
