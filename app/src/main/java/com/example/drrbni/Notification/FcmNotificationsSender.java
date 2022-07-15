package com.example.drrbni.Notification;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmNotificationsSender {

     String recipientToken, senderName, adsId ;
     Activity mActivity;
     FirebaseUser firebaseUser;

    private RequestQueue requestQueue;
    private final String postUrl = "https://fcm.googleapis.com/fcm/send";
    private final String fcmServerKey = "AAAAyXR_2Eg:APA91bFYCAG39OqbR55m90JlcUahrhOtaJ4tXJlkHzZwE399nNdmWU1lScFEhko4isWpOrd471sEasFDPtmCfyhr1AKZUUHQezUjkAbDn0MUS14Dkl82OYgM1HfI6-aUQ_PAZK9Eg9b7";


    public FcmNotificationsSender(String recipientToken, String senderName, String adsId,
                                  Activity activity) {
        this.recipientToken = recipientToken;
        this.mActivity = activity;
        this.senderName = senderName;
        this.adsId = adsId;
    }

    public void SendNotifications() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String senderUid = firebaseUser.getUid();

        requestQueue = Volley.newRequestQueue(mActivity);

        JSONObject mainObject = new JSONObject();
        try {
            mainObject.put("to", recipientToken);

            JSONObject dataObject = new JSONObject();
            dataObject.put("title", "طلب فرصة تدريب");
            dataObject.put("body", "لقد تقدم "+senderName+" بطلب تدريب");
            dataObject.put("senderUid" , senderUid);
            dataObject.put("adsId" , adsId);

            mainObject.put("data", dataObject);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, postUrl, mainObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + fcmServerKey);
                    return header;
                }
            };
            requestQueue.add(request);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
