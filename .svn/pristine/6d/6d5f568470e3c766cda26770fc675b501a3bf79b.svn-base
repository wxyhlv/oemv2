package com.capitalbio.oemv2.myapplication.Devices.BloodPressDevice;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;

/**
 * 数据类型转换工具
 * @author haowr
 *
 */
public class ByteUtil {

	  public static String toHex(byte b) {  
	        String result = Integer.toHexString(b & 0xFF);  
	        if (result.length() == 1) {  
	            result = '0' + result;  
	        }  
	        return result;  
	    }  
	  
	  public static int changeByteArrayToInt(byte[] source) {
	        int andValue = 0x00FF;
	        int destination = 0;
	        for (int i = 0; i < source.length; i++) {
	            destination = destination + ((source[i] & andValue) << (8 * (source.length - i - 1)));
	        }
	        return destination;
	    }
	    

	public static int ByteToInt(byte[] b) {
		int targets = (b[0] & 0xff) | ((b[1] << 8) & 0xff00)
				| ((b[2] << 24) >>> 8) | (b[3] << 24);

		return targets;

	}

	public static float ByteToFloat(byte[] b) {
		return Float.intBitsToFloat(ByteToInt(b));
	}

	public static int FloatToInt(float f) {
		return Float.floatToIntBits(f);
	}

	public static byte[] FloatToByte(float f) {
		return IntToByte(Float.floatToIntBits(f));
	}

	public static float IntToFloat(int i) {
		return Float.intBitsToFloat(i);
	}

	public static byte[] IntToByte(int i) {
		byte[] result = new byte[4];
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);
		return result;
	}

	public static byte[] getPartByte(byte[] buffer, int length, int srcPos) {
		byte[] b = new byte[length];
		System.arraycopy(buffer, srcPos, b, 0, length);
		return b;
	}

	public static Bitmap byteToBitmap(byte[] imgByte) {
		InputStream input = null;
		Bitmap bitmap = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;
		input = new ByteArrayInputStream(imgByte);
		SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
				input, null, options));
		bitmap = (Bitmap) softRef.get();
		if (imgByte != null) {
			imgByte = null;
		}

		try {
			if (input != null) {
				input.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	public static byte[] bitmapToByte(Bitmap bitmap){

		ByteArrayOutputStream output = new ByteArrayOutputStream();//初始化一个流对象
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);//把bitmap100%高质量压缩 到 output对象里

		bitmap.recycle();//自由选择是否进行回收


		byte[] result = output.toByteArray();//转换成功了
		try {
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//获得指定文件的byte数组
	public static byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
}
