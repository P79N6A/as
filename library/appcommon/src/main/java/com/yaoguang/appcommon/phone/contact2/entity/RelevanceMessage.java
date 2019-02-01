package com.yaoguang.appcommon.phone.contact2.entity;

import android.os.Parcel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by zhongjh on 2018/4/25.
 */

@MessageTag(
        value = "YG:RelevanceMessage",
        flag = MessageTag.ISCOUNTED | MessageTag.ISPERSISTED
)
public class RelevanceMessage extends MessageContent {
    private static final String TAG = "RelevanceMessage";
    private String content;
    protected Extra extra;
    public static final Creator<RelevanceMessage> CREATOR = new Creator<RelevanceMessage>() {
        public RelevanceMessage createFromParcel(Parcel source) {
            return new RelevanceMessage(source);
        }

        public RelevanceMessage[] newArray(int size) {
            return new RelevanceMessage[size];
        }
    };

    public Extra getExtra() {
        return this.extra;
    }

    public void setExtra(Extra extra) {
        this.extra = extra;
    }

    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();

        try {
            jsonObj.put("content", this.getEmotion(this.getContent()));
            if(this.getJSONObjectExtra() != null) {
                jsonObj.putOpt("extra", this.getJSONObjectExtra());
            }
            if (this.getJSONUserInfo() != null) {
                jsonObj.putOpt("user", this.getJSONUserInfo());
            }

            if (this.getJsonMentionInfo() != null) {
                jsonObj.putOpt("mentionedInfo", this.getJsonMentionInfo());
            }
        } catch (JSONException var4) {
            RLog.e("RelevanceMessage", "JSONException " + var4.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    private String getEmotion(String content) {
        Pattern pattern = Pattern.compile("\\[/u([0-9A-Fa-f]+)\\]");
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            int inthex = Integer.parseInt(matcher.group(1), 16);
            matcher.appendReplacement(sb, String.valueOf(Character.toChars(inthex)));
        }

        matcher.appendTail(sb);
        return sb.toString();
    }

    protected RelevanceMessage() {
    }

    public static RelevanceMessage obtain(String text, Extra extra) {
        RelevanceMessage model = new RelevanceMessage();
        model.setContent(text);
        model.setExtra(extra);
        return model;
    }

    public RelevanceMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            var5.printStackTrace();
        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if (jsonObj.has("content")) {
                this.setContent(jsonObj.optString("content"));
            }

            if(jsonObj.has("extra")) {
                this.setExtra(this.parseExtra(jsonObj.getJSONObject("extra")));
            }

            if (jsonObj.has("user")) {
                this.setUserInfo(this.parseJsonToUserInfo(jsonObj.getJSONObject("user")));
            }

            if (jsonObj.has("mentionedInfo")) {
                this.setMentionedInfo(this.parseJsonToMentionInfo(jsonObj.getJSONObject("mentionedInfo")));
            }
        } catch (JSONException var4) {
            RLog.e("RelevanceMessage", "JSONException " + var4.getMessage());
        }

    }

    public void setContent(String content) {
        this.content = content;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, this.getExtra());
        ParcelUtils.writeToParcel(dest, this.content);
        ParcelUtils.writeToParcel(dest, this.getUserInfo());
        ParcelUtils.writeToParcel(dest, this.getMentionedInfo());
    }

    public RelevanceMessage(Parcel in) {
        this.setExtra(ParcelUtils.readFromParcel(in, Extra.class));
        this.setContent(ParcelUtils.readFromParcel(in));
        this.setUserInfo(ParcelUtils.readFromParcel(in, UserInfo.class));
        this.setMentionedInfo(ParcelUtils.readFromParcel(in, MentionedInfo.class));
    }

    public RelevanceMessage(String content) {
        this.setContent(content);
    }

    public String getContent() {
        return this.content;
    }

    public List<String> getSearchableWord() {
        List<String> words = new ArrayList();
        words.add(this.content);
        return words;
    }

    private Extra parseExtra(JSONObject jsonObj) {
        Extra info = new Extra();
        String driverID = jsonObj.optString("driverID");
        String txt = jsonObj.optString("txt");

        info.setDriverID(driverID);
        info.setTxt(txt);

        return info;
    }


    private JSONObject getJSONObjectExtra() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("driverID", this.getExtra().getDriverID());
            jsonObject.put("txt", this.getExtra().getTxt());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
