package com.google.cloud.android.speech;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Network{

    //통신 Code
    public void sendPost(final String url_, final double latitude, final  double longitude) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    URL url = new URL(url_);
                    Log.e("hey","hey");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

//                    JSONObject jsonParam = new JSONObject();
//                    jsonParam.put("node_id",'1');
//                    jsonParam.put("node_type","CORNER");
//                    jsonParam.put("node_name","HOME");
//                    jsonParam.put("node_longitude",15);
//                    jsonParam.put("node_latitute",10);
//                    jsonParam.put("node_neighbors","1/2/3");

                    //노드 json넣기
                    Node node1=new Node(1,latitude, longitude, "CORNER", "1/2/3", "BUILDING");
                    //Node node2=new Node(2,1.5, 2.1, "GREENLIGHT", "1/2/3", "HOME");
                    //Node node3=new Node(3,1.3, 2.3, "CORNER", "1/2/3", "TOILET");

                    ArrayList<Node> nodeList=new ArrayList<>();
                    nodeList.add(node1);
                    //nodeList.add(node2);
                    //nodeList.add(node3);

                    JSONObject sendJsonObject=new JSONObject();

                    JSONArray jArray=new JSONArray();
                    for(int i=0; i<nodeList.size(); ++i)
                    {
                        JSONObject nodeJsonObject=new JSONObject();
                        //nodeJsonObject.put("node_id",nodeList.get(i).getNode_id());
                        nodeJsonObject.put("pos_x",nodeList.get(i).getPos_x());
                        nodeJsonObject.put("pos_y",nodeList.get(i).getPos_y());
                        //nodeJsonObject.put("node_type",nodeList.get(i).getNode_type());
                        //nodeJsonObject.put("node_neighbors",nodeList.get(i).getNode_neighbors());
                        //nodeJsonObject.put("node_name",nodeList.get(i).getNode_name());
                        jArray.put(nodeJsonObject);
                    }

                    //User 휴대폰 고유넘버
                    JSONObject userJsonObject=new JSONObject();
                    //userJsonObject.put("user_phonenumber","0001");

                    //sendJsonObject.put("User",userJsonObject);
                    sendJsonObject.put("Node",jArray);

                    ////

                    Log.i("JSON", sendJsonObject.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));

                    //여기서 보내는듯
                    os.writeBytes(sendJsonObject.toString());

                    os.flush();
                    os.close();

                    //여기서부터 Response Code
                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    InputStream is = null;

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        is = conn.getInputStream();// is is inputstream
                    } else {
                        is = conn.getErrorStream();
                    }


                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                                is, "UTF-8"), 8);
                        StringBuilder sb = new StringBuilder();
                        String line = null;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        is.close();
                        String response = sb.toString();
                        //HERE YOU HAVE THE VALUE FROM THE SERVER
                        Log.d("Your Data", response);
                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }


                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
