package org.fireking.app.imagelib;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

/**
 * 公共类
 * 
 * @author wanggang
 * 
 */
public class CommonsUitls {

	private static final String DATA = "yyyy-MM-dd";

	private static final String DATA2 = "yyyy-MM";

	/**
	 * 字符串非空验证
	 * 
	 * @param string
	 *            需要被验证的字符串
	 * @return false:表示不为空，true：表示空
	 */
	public static boolean isEmpty(String string) {
		if (string == null || "".equals(string))
			return true;
		return false;
	}

	/**
	 * 将文件从一个地方copy到另一个地方(本地文件copy使用)
	 * 
	 * @param srcFile
	 *            元文件地址
	 * @param distFile
	 *            目标文件地址
	 */
	public static void copyFile(String srcFile, String distFile) {
		File src = new File(srcFile);
		File dist = new File(distFile);
	}

	/**
	 * 检测sdcard是否可用
	 * 
	 * @return
	 */
	public static boolean sdCardIsAvailable() {
		return false;
	}

	/**
	 * 检测sdcard容量是否可用 
	 * 
	 * @return
	 */
	public static boolean enoughSpaceOnSdcard() {
		return false;
	}

	/**
	 * 检测sdcard可用大小
	 * 
	 * @return
	 */
	public static long getRealSizeOnSdcard() {
		return 0;
	}


	// 判断是否为邮箱
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// 判断字符串是不是数字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	// 验证手机号码
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		SimpleDateFormat mFormat = new SimpleDateFormat(DATA);
		return mFormat.format(date);
	}

	/**
	 * 解析日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String date) throws ParseException {
		SimpleDateFormat mFormat = new SimpleDateFormat(DATA);
		return mFormat.parse(date);
	}
}
