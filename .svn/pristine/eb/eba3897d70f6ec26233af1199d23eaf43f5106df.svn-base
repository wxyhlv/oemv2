package com.capitalbio.oemv2.myapplication.Utils;

import java.io.ByteArrayOutputStream;

/**
 * byte和16进制工具类
 * 
 * @author jiantao.tu
 *
 */
public class HexUtils {
	// 转化字符串为十六进制编码
	public static String toHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	/**
	 * 转化十六进制编码为字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 16进制数字字符集
	 */
	private static String hexString = "0123456789ABCDEF";

	/**
	 * 将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */
	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	public static String Bytes2HexString(byte[] b) {
		String ret = "";
		for (int i = 0; i < b.length; i++) {
			String hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret += hex.toUpperCase();
		}
		return ret;
	}

	/**
	 * byte数组转换为二进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToBinary(byte[] bytes) {
		String ZERO = "00000000";
		StringBuilder sb = new StringBuilder(16);
		for (int i = 0; i < bytes.length; i++) {
			String s = Integer.toBinaryString(bytes[i]);
			if (s.length() > 8) {
				s = s.substring(s.length() - 8);
			} else if (s.length() < 8) {
				s = ZERO.substring(s.length()) + s;
			}
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 将16进制数转byte数组
	 * 
	 * @param paramString
	 *            "AA550f038400"
	 * @return
	 */
	public static byte[] hexStr2Bytes(String paramString) {
		int i = paramString.length() / 2;
		byte[] arrayOfByte = new byte[i];
		int j = 0;
		while (true) {
			if (j >= i)
				return arrayOfByte;
			int k = 1 + j * 2;
			int l = k + 1;
			arrayOfByte[j] = (byte) (0xFF & Integer.decode(
					"0x" + paramString.substring(j * 2, k)
							+ paramString.substring(k, l)).intValue());
			++j;
		}
	}

	/**
	 * 二进制转换为十进制
	 * @param bunaryString
	 * @return
	 */
	public static int binaryStringToNum(String bunaryString) {
		int x = 0;
		for (char c : bunaryString.toCharArray())
			x = x * 2 + (c == '1' ? 1 : 0);
		return x;
	}

	/**
	 * 二进制转16进制
	 * 
	 * @param bString
	 *            二进制连续在一起
	 * @return 返回的16进制也连续在一起
	 */
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0)
			return null;
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * 16进制转换为2进制
	 * 
	 * @param hexString
	 *            AA554F
	 * @return
	 */
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"
					+ Integer.toBinaryString(Integer.parseInt(
							hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 十六进制解析成二进制格式字符串数组
	 * 
	 * @param hexStr
	 *            十六进制FF429A
	 * @return 二进制字符串数组
	 */
	public static String[] analyzeHexToBinaryArray(String hexStr) {
		String binaryStr = HexUtils.hexString2binaryString(hexStr);
		int size = binaryStr.length() / 8;
		String[] arrayHex = new String[size];
		for (int i = 0; i < size; i++) {
			arrayHex[i] = binaryStr.substring(i * 8, i * 8 + 8);
		}
		return arrayHex;
	}

	public static String hexStr2Str(String paramString) {
		char[] arrayOfChar = paramString.toCharArray();
		byte[] arrayOfByte = new byte[paramString.length() / 2];
		int i = 0;
		while (true) {
			if (i >= arrayOfByte.length)
				return new String(arrayOfByte);
			arrayOfByte[i] = (byte) (0xFF & 16
					* "0123456789ABCDEF".indexOf(arrayOfChar[(i * 2)])
					+ "0123456789ABCDEF".indexOf(arrayOfChar[(1 + i * 2)]));
			++i;
		}
	}

	/**
	 * bytes转换成十六进制字符串
	 * 
	 * @param  b byte数组
	 * @return String 每个Byte值之间空格分隔
	 */
	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	public static String byte2HexStr(byte[] b,String split) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			sb.append(split);
		}
		return sb.toString().toUpperCase().trim();
	}

	/**
	 * 字符串转换成十六进制字符串
	 * 
	 * @param
	 * str 待转换的ASCII字符串
	 * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
	 */
	public static String str2HexStr(String str) {

		char[] chars = "0123456789ABCDEF".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit;

		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0) >> 4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
			sb.append(' ');
		}
		return sb.toString().trim();
	}

	/**
	 * 16进制string空格隔开字符串和校验，无进位
	 * 
	 * @param str
	 *            "02 02 01 AA 19 05";
	 * @return 和校验值
	 */
	public static String sexStringsum(String str) {
		byte[] a = HexString2Bytes(str.replace(" ", ""));
		int result = 0;
		for (int i = 0; i < a.length; i++) {
			result += a[i];
		}
		return Integer.toHexString(result).substring(
				Integer.toHexString(result).length() - 2);
	}

	/**
	 * 将16进制字符串转成byte[]
	 * 
	 * @param src
	 *            ef
	 * @return
	 */
	public static byte[] HexString2Bytes(String src) {
		byte[] ret = new byte[src.length() / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < ret.length; i++) {
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	/**
	 * 将两个ASCII字符合成一个字节； 如："EF"--> 0xEF
	 * 
	 * @param src0
	 *            byte
	 * @param src1
	 *            byte
	 * @return byte
	 */
	public static byte uniteBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	/**
	 * 取小数点后一位不进制
	 * 
	 * @param str
	 *            12.36=12.3
	 * @return
	 */
	public static String pointAfterOne(String str) {
		if (str != null && !str.equals("")) {
			int num = str.indexOf(".");
			if (num > 0) {
				str = str.substring(0, num + 2);
			}
		}
		return str;
	}

	/**
	 * 16进制高低位求值，高位在前，低位在后；或者16进制转换为10进制
	 * 
	 * @return 返回10进制的数据 c1,b2
	 */
	public static String Handl16value(String... args) {
		StringBuffer sbf = new StringBuffer("");
		for (int i = 0; i < args.length; i++) {
			sbf.append(args[i]);
		}
		return Integer.valueOf(sbf.toString(), 16) + "";
	}

	/**
	 * 把16进制数转换成2进制数
	 * 
	 * @param str
	 *            "ef c1 b2"或"ef" 如"01"=00000001;"0f"=00001111
	 * @return
	 */
	public static String S16to2(String str) {
		String[] args;
		StringBuffer sbf = new StringBuffer("");
		if (str.length() > 2) {
			args = str.split(" ");
			for (int i = 0; i < args.length; i++) {
				if (i == args.length - 1) {
					String a = Integer.toBinaryString(Integer.valueOf(args[i],
							16));
					if (a.length() < 8) {
						// 补全八位
						a = fullbit8(a);
					}

					sbf.append(a + ",");
				} else {
					String a = Integer.toBinaryString(Integer.valueOf(args[i],
							16));
					if (a.length() < 8) {
						// 补全八位
						a = fullbit8(a);
					}
					sbf.append(a);
				}
			}
		} else {
			sbf.append(Integer.toBinaryString(Integer.valueOf(str, 16)));
		}

		return sbf.toString();
	}

	/**
	 * 不足八位的补齐，如 '101'="00000101"
	 * 
	 * @return
	 */
	public static String fullbit8(String str) {
		if (str.length() < 8) {
			StringBuffer sbf = new StringBuffer("");
			for (int i = 0; i < 8 - str.length(); i++) {
				sbf.append("0");
			}
			sbf.append(str);
			return sbf.toString();
		}
		return str;
	}
	
	/**
	 * 异或校验值异或校验值BBC编码，转换为16进制
	 * @param data
	 * @return
	 */
	public static String getBCC(byte[] data) {

		String ret = "";
		byte BCC[]= new byte[1];
		for(int i=0;i<data.length;i++){
			BCC[0]=(byte) (BCC[0] ^ data[i]);
		}
		String hex = Integer.toHexString(BCC[0] & 0xFF);
		if (hex.length() == 1) {
			hex = '0' + hex;
		}
		ret += hex.toUpperCase();
		return ret;
	}
}
