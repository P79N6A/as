/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance withRounded the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yaoguang.lib.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
	/**
	 * name of preference
	 */
	public static final String PREFERENCE_NAME = "saveInfo";
	private static SharedPreferences mSharedPreferences;
	private static PreferenceManager mPreferencemManager;
	private static SharedPreferences.Editor editor;

	private final String SHARED_KEY_PHONE = "shared_key_phone"; // 用户手机
	private final String SHARED_KEY_SETTING_ALIAS = "shared_key_setting_alias";  //是否已经注册alias
	private final String SHARED_KEY_SETTING_OPENPUSH = "shared_key_setting_push";

	private static String SHARED_KEY_SETTING_CHATROOM_OWNER_LEAVE = "shared_key_setting_chatroom_owner_leave";
	private String SHARED_KEY_SETTING_NOTIFICATION = "shared_key_setting_notification";


	@SuppressLint("CommitPrefEdits")
	private PreferenceManager(Context cxt) {
		mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
	}

	public static synchronized void init(Context cxt){
	    if(mPreferencemManager == null){
	        mPreferencemManager = new PreferenceManager(cxt);
	    }
	}

	/**
	 * get instance of PreferenceManager
	 *
	 * @param
	 * @return
	 */
	public synchronized static PreferenceManager getInstance() {
		if (mPreferencemManager == null) {
			throw new RuntimeException("please init first!");
		}

		return mPreferencemManager;
	}

	public void setPhone(String param) {
		editor.putString(SHARED_KEY_PHONE, param);
		editor.apply();
	}
	public String getPhone() {
		return mSharedPreferences.getString(SHARED_KEY_PHONE, null);
	}

	public void setRegAlias(Boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_ALIAS, paramBoolean);
		editor.apply();
	}
	public boolean isRegAlias() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_ALIAS, false);
	}

	public void setOpenPush(boolean paramBoolean) {
		editor.putBoolean(SHARED_KEY_SETTING_OPENPUSH, paramBoolean);
		editor.apply();
	}
	public boolean isOpenPush() {
		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_OPENPUSH, false);
	}

	//	public void setSettingMsgNotification(boolean paramBoolean) {
//		editor.putBoolean(SHARED_KEY_SETTING_NOTIFICATION, paramBoolean);
//		editor.apply();
//	}
//
//	public boolean getSettingMsgNotification() {
//		return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_NOTIFICATION, true);
//	}



}
