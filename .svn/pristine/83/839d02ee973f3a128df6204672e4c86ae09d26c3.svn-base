package com.capitalbio.oemv2.myapplication.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class UploadFile {

	private static Log log;

	/**
	 * 上传图片 TODO This
	 * 
	 * @param params
	 *            String参数
	 * @param path
	 *            音频文件, 最多5个
	 *
	 * @param urlServer
	 *            服务器路径
	 * @return Map<String,Object> 上传返回信息 (isSuccess 是否成功 msg 返回信息)
	 */
	public static Map<String, Object> postFile(List<NameValuePair> params,
			String path, String urlServer) {
		log.i("===", "postFile Before params = " + params);
		log.i("===", "postFile Before path = " + path);
		log.i("===", "postFile Before urlServer = " + urlServer);

		Map<String, Object> retMap = new HashMap<String, Object>();

		String msg = ""; // 提示信息
		boolean isSuccess = false; // 标示是否成功

		// 通过图片路径, 获取图片后缀名
		// String pathEnd = imgPath.substring(imgPath.indexOf("."));
		// log.info("imgPath = " + imgPath + ", pathEnd = " + pathEnd);

		// ==>>> uploadfile
		// File path= Environment.getExternalStorageDirectory(); //取得SD卡的路径
		// String file = path.getPath()+File.separator+"ak.txt";
		// ContentBody cbFile = new FileBody(file);//文件传输
		// mpEntity.addPart("userfile", cbFile);
		// <input type="file" name="userfile" /> 对应的

		// 拼接json
		// JSONObject jobj = new JSONObject();
		// jobj.put("feedback", "一个很好的软件这是个神器");
		// jobj.put("email", "9123571293@qq.com");

		// // json字符串传输
		// ContentBody content = new StringBody(jobj.toString());
		// mpEntity.addPart("feedback", content);

		try {
			HttpClient httpclient = new DefaultHttpClient();
			// 设置通信协议版本
			httpclient.getParams().setParameter(
					CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			HttpPut httppost = new HttpPut(urlServer);
			// HttpPost httppost = new HttpPost(urlServer);

			// 图片文件
			// File file = new File(img);
			// 下面的两种方式都可以 传送数据 json可以直接字符串也可以
			MultipartEntity mpEntity = new MultipartEntity();

			// >>>>>> 字符串
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// content 客户咨询内容
			// ContentBody content = new StringBody(content);
			// mpEntity.addPart("content", content);
			// // userid 客户ID
			// ContentBody userid = new StringBody(userid);
			// mpEntity.addPart("userid", userid);
			// // name 客户名称
			// ContentBody name = new StringBody(name);
			// mpEntity.addPart("userid", name);
			// // name 客户电话
			// if (!StringUtils.isEmpty(phone)) {
			// ContentBody phone = new StringBody(phone);
			// mpEntity.addPart("userid", name);
			// }
			for (int i = 0; i < params.size(); i++) { // 加String
				NameValuePair pair = params.get(i);
				String paramsName = pair.getName();
				String paramsValue = pair.getValue();
				ContentBody contentBody = new StringBody(paramsValue,
						Charset.forName("UTF-8"));
				mpEntity.addPart(paramsName, contentBody);
			}

			// >>>>>> File
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// 语言文件列表（最多只能上传5个文件）
			// for (int i = 0; i < audioFiles.size(); i++) {
			// File file = audioFiles.get(i);
			// ContentBody fileBody = new FileBody(file);
			// mpEntity.addPart("audioFiles", fileBody);
			// }

			// 文件
			File file = new File(path);
			// ContentBody fileBody = new FileBody(file);
			// mpEntity.addPart("file", fileBody);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream is = new FileInputStream(file);
			int len = 0;
			byte[] b = new byte[512];
			while ((len = is.read(b, 0, b.length)) != -1) {
				baos.write(b, 0, len);
			}
			 byte[] buffer = baos.toByteArray();

			// ByteArrayBody byteBody = new ByteArrayBody(buffer,
			// file.getName());
			// mpEntity.addPart("file", byteBody);
			ByteArrayEntity byteEntity = new ByteArrayEntity(buffer); 
			baos.close();
			is.close();

			// >>>>>> 二进制
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// ContentBody imgBody = new ByteArrayBody(imgBytes, "filedata"
			// + pathEnd);
			// mpEntity.addPart("filedata", imgBody);

			httppost.setEntity(byteEntity);
			log.v("===", "executing request " + httppost.getRequestLine());
			// POST http://www.instrument.com.cn/bbsupload/bbsimgupload.ashx
			// HTTP/1.1
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity resEntity = response.getEntity();

			// >>>>>> 获取response内容
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// InputStream instreams = resEntity.getContent();
			// String str = convertStreamToString(instreams);

			// System.out.println("toasttoast"+EntityUtils.toString(httppost.getEntity()));
			String resString = EntityUtils.toString(resEntity);
			log.v("===", "server response = " + resString);
			/*
			 * java.lang.IllegalStateExceptio n: Content has been consumed
			 * 
			 * 这个问题是多次调用httpEntity.getContent()导致的， entity中的内容只能读取一次， 参考如下：
			 * 
			 * You can retrieve the content from the entity only once. If you
			 * have already extracted the content somewhere, and you try to
			 * fetch it again, it will throw this IllegalStateException. Check
			 * you code and make sure that you make this call only once.
			 */

			StatusLine statusLine = response.getStatusLine();
			log.v("===", "StatusLine = " + statusLine);// 通信Ok
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				// 成功
				isSuccess = true;
				msg = resString;
			} else {
				isSuccess = false;
				msg = "图片上传失败";
			}

			retMap.put("msg", msg);
			retMap.put("isSuccess", isSuccess);

			// if (resEntity != null) {
			// //System.out.println(EntityUtils.toString(resEntity,"utf-8"));
			// json=EntityUtils.toString(resEntity,"utf-8");
			// System.out.println("::::json"+json);
			// JSONObject p=null;
			// try{
			// p=new JSONObject(json);
			// // path=(String) p.get("path");
			// }catch(Exception e){
			// e.printStackTrace();
			// }
			// }
			// if (resEntity != null) {
			// resEntity.consumeContent();
			// }

			// 停止连接
			httpclient.getConnectionManager().shutdown();
			return retMap;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			retMap.put("isSuccess", isSuccess);
			retMap.put("msg", msg);
			return retMap;
		} catch (IOException e) {
			e.printStackTrace();
			retMap.put("isSuccess", isSuccess);
			retMap.put("msg", msg);
			return retMap;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("isSuccess", isSuccess);
			retMap.put("msg", msg);
			return retMap;
		}
	}

	// public static String convertStreamToString(InputStream is) {
	// BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	// StringBuilder sb = new StringBuilder();
	//
	// String line = null;
	// try {
	// while ((line = reader.readLine()) != null) {
	// sb.append(line + "\n");
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// } finally {
	// try {
	// is.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// return sb.toString();
	// }
	//
	// public static String newPost(String actionUrl, Map<String, String>
	// params,
	// String imagepath) {
	// String code = "0";
	// Log.i("http", actionUrl);
	// HttpParams httpParameters = new BasicHttpParams();
	// HttpConnectionParams.setConnectionTimeout(httpParameters, 1000 * 30);
	// HttpConnectionParams.setSoTimeout(httpParameters, 1000 * 30);
	// HttpConnectionParams.setTcpNoDelay(httpParameters, true);
	// HttpClient httpclient = new DefaultHttpClient(httpParameters);
	// HttpPost httppost = new HttpPost(actionUrl);
	// MultipartEntity mpEntity = new MultipartEntity();
	// try {
	// File imageFile = new File(imagepath);
	// if (!imageFile.exists()) {
	// Log.i("http", "999");
	// code = "999";
	// }
	// for (Map.Entry<String, String> entry : params.entrySet()) {
	// String key = entry.getKey().toString();
	// String value = entry.getValue().toString();
	// Log.i("Http", "KEY:" + key + ",Value:" + value);
	// mpEntity.addPart(key, new StringBody(value));
	// }
	// FileBody file = new FileBody(imageFile);
	// mpEntity.addPart("name", file);
	// httppost.setEntity(mpEntity);
	// HttpResponse response = httpclient.execute(httppost);
	// if (response.getStatusLine().getStatusCode() == 200) {
	// code = EntityUtils.toString(response.getEntity());
	// System.out.println("result:" + code);
	// Log.i("http", code);
	// }
	// } catch (Exception e) {
	// return code;
	// }
	// return code;
	// }

	// 先拼出上传的内容，各种part，然后通过HttpURLConnection写入到服务器

	// public boolean upload(String address, String username, String password,
	// String filepath) throws Exception {
	// String boundary = "---------------------------itheima"; // 分割线
	// File file = new File(filepath); // 要上传的文件
	// URL url = new URL(address); // 用来开启连接
	// StringBuilder sb = new StringBuilder(); // 用来拼装请求
	//
	// // username字段
	// sb.append("--" + boundary + "\r\n");
	// sb.append("Content-Disposition: form-data; name=\"username\"" + "\r\n");
	// sb.append("\r\n");
	// sb.append(username + "\r\n");
	//
	// // password字段
	// sb.append("--" + boundary + "\r\n");
	// sb.append("Content-Disposition: form-data; name=\"password\"" + "\r\n");
	// sb.append("\r\n");
	// sb.append(password + "\r\n");
	//
	// // 文件部分
	// sb.append("--" + boundary + "\r\n");
	// sb.append("Content-Disposition: form-data; name=\"file\"; filename=\""
	// + filepath + "\"" + "\r\n");
	// sb.append("Content-Type: application/octet-stream" + "\r\n");
	// sb.append("\r\n");
	//
	// // 将开头和结尾部分转为字节数组
	// byte[] before = sb.toString().getBytes("UTF-8");
	// byte[] after = ("\r\n--" + boundary + "--\r\n").getBytes("UTF-8");
	//
	// // 打开连接, 设置请求头
	// HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	// conn.setConnectTimeout(3000);
	// conn.setRequestMethod("POST");
	// conn.setRequestProperty("Content-Type",
	// "multipart/form-data; boundary=" + boundary);
	// conn.setRequestProperty("Content-Length", before.length + file.length()
	// + after.length + "");
	// conn.setRequestProperty("HOST", "192.168.1.240:8080");
	// conn.setDoOutput(true);
	//
	// // 获取输入输出流
	// OutputStream out = conn.getOutputStream();
	// InputStream in = new FileInputStream(file);
	//
	// // 将开头部分写出
	// out.write(before);
	//
	// // 写出文件数据
	// byte[] buf = new byte[1024];
	// int len;
	// while ((len = in.read(buf)) != -1)
	// out.write(buf, 0, len);
	//
	// // 将结尾部分写出
	// out.write(after);
	//
	// in.close();
	// out.close();
	// return conn.getResponseCode() == 200;
	// }

}
