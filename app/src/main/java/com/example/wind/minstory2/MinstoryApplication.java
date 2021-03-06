package com.example.wind.minstory2;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class MinstoryApplication extends Application {
	/**
	 * SDK初始化也可以放到Application中
	 * 46c730e7e33eabeb3ec790b3fb0a02d7
	 * 3124f50157a5df138aba77a85e1d8909
	 */
	public static String APPID ="884554bc484ee10c9a775aa61fc68d55";

	@Override
	public void onCreate() {
		super.onCreate();
		//提供以下两种方式进行初始化操作：
//		//第一：设置BmobConfig，允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)
//		BmobConfig config =new BmobConfig.Builder(this)
//		//设置appkey
//		.setApplicationId(APPID)
//		//请求超时时间（单位为秒）：默认15s
//		.setConnectTimeout(30)
//		//文件分片上传时每片的大小（单位字节），默认512*1024
//		.setUploadBlockSize(1024*1024)
//		//文件的过期时间(单位为秒)：默认1800s
//		.setFileExpiration(5500)
//		.build();
//		Bmob.initialize(config);
		//第二：默认初始化
		Bmob.initialize(this,APPID);
	}

}