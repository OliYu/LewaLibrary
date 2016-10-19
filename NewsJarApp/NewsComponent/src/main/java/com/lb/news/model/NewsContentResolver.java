package com.lb.news.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Created by oli on 16-8-31.
 */
public class NewsContentResolver {
    private static final String TAG = "NewsContentResolver";
    /**Authority*/
    private static final String AUTHORITY = "com.lb.news.provider";
    public static final String KEY_INDIA_TOKEN = "token_india";
    public static final String KEY_ENGLISH_TOKEN = "token_english";

    public static String queryByKey(ContentResolver contentResolver, String key){
        String value = "";
        Log.d(TAG, "queryByKey("+contentResolver+","+key+")");
        Uri queryUri = Uri.parse("content://"+AUTHORITY+"/"+key);
        try{
            Cursor cursor = contentResolver.query(queryUri, null, null,null, null);
            while(cursor != null && cursor.moveToNext()) {
                value = cursor.getString(cursor.getColumnIndex("Value"));
            }
            if(cursor != null) {
                cursor.close();
            }
        }catch(Exception e) {
            Log.e(TAG, "queryByKey :"+e.getMessage());
        }
        Log.d(TAG,"queryByKey() return: "+value);
        return value;
    }

    public static boolean insertKeyValue(ContentResolver contentResolver, String key,String value){
        Log.d(TAG, "insertKeyValue("+contentResolver+","+key+","+value+")");
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("Key",key);
            contentValues.put("Value",value);
            Uri insertUri = Uri.parse("content://"+AUTHORITY+"/"+key);
            Uri returnUri = contentResolver.insert(insertUri, contentValues);
            if(returnUri != null && Integer.parseInt(returnUri.getPathSegments().get(1)) > 0){
                Log.d(TAG,"insertKeyValue success!!!");
                return true;
            }
            return false;
        }catch(Exception e) {
            Log.e(TAG, "insertKeyValue :"+e.getMessage());
            return false;
        }
    }
}
