package com.google.cloud.android.speech;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Network{

    //static으로 선언한 이유는 thread함수 안에 있는 값을 갖고오기위해
    //json data받은거
    public static String static_response_data=null;
    //json data받은거 확인용
    public static boolean static_isJsonDataReceived=false;

    //JSON 데이터를 서버로 보내는 함수
    public void sendPost(final String url_, final JSONObject to_send_json_data_) throws JSONException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(url_);
                    Log.i("sendPost시작","sendPost시작");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //여기서 보내는듯
                    Log.i("보낸 json", to_send_json_data_.toString());
                    os.writeBytes(to_send_json_data_.toString());

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

                        //여기서 json response 받음
                        String response = sb.toString();
                        //HERE YOU HAVE THE VALUE FROM THE SERVER
                        Log.d("서버에서 받은 JSON", response);
                        //static 함수에 JSON저장 -try문이라 이렇게 함

                        static_response_data=response;
                        Log.i("static_response_data",response);

                    } catch (Exception e) {
                        Log.e("Buffer Error", "Error converting result " + e.toString());
                    }

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                static_isJsonDataReceived=true;

            }
        });

        thread.start();

    }

    //한국어 String을 utf-8로 인코딩하는 함수
    public String koreanEncoding(String korean_){
        String encoded_korean=null;
        try {
            encoded_korean = URLEncoder.encode(korean_,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encoded_korean;
    }

    //받은 JSON형태의 Node data를 ArrayList<Node>로 바뀐 뒤 리턴하는 함수
    public ArrayList<Node> convertNodeJsontoNodeList(String static_response_data_)
    {

        ArrayList<Node> node_list_tmp=new ArrayList<Node>();
        JSONArray json_node_array;
        try {
            JSONObject response_json_data_=new JSONObject(static_response_data_);

            json_node_array=response_json_data_.getJSONArray("Node");
            Log.i("실제 JSON 데이터",String.valueOf(json_node_array));
            for(int i=0; i<json_node_array.length(); ++i)
            {
//                public Node(int node_id_,String node_name_, double pos_x_, double pos_y_, String indoor_, String floor_, String node_type_, ArrayList<Integer> node_neighbors_)
                JSONObject json_node=json_node_array.getJSONObject(i);
                Node node=new Node(json_node.getInt("node_id"),
                        json_node.getString("node_name"),
                        json_node.getDouble("pos_x"),
                        json_node.getDouble("pos_y"),
                        json_node.getString("indoor"),
                        json_node.getString("floor"),
                        json_node.getString("node_type")
                );

                JSONArray json_node_neighbors=json_node.getJSONArray("node_neighbors");


                for(int j=0; j<json_node_neighbors.length(); ++j)
                {
                    node.addNodeNeighbors(json_node_neighbors.getInt(j));
                }

                node_list_tmp.add(node);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return node_list_tmp;
    }

    public LinkedHashSet<Integer> convertPathJsontoList(String static_response_data_)
    {
        LinkedHashSet<Integer> node_neighbors_tmp=new LinkedHashSet<Integer>();
        JSONArray json_node_array;
        try {
            JSONObject response_json_data_=new JSONObject(static_response_data_);

            json_node_array=response_json_data_.getJSONArray("Path");
            Log.i("실제 JSON데이터",String.valueOf(json_node_array));
            for(int i=0; i<json_node_array.length(); ++i)
            {
//                public Node(int node_id_,String node_name_, double pos_x_, double pos_y_, String indoor_, String floor_, String node_type_, ArrayList<Integer> node_neighbors_)
                node_neighbors_tmp.add(json_node_array.getInt(i));
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        return node_neighbors_tmp;
    }

    //node_neighbors String으로 바꾸는 함수
    public String convertNodeNeighborsListToString(HashSet<Integer> node_neighbors)
    {
        String node_neighbors_string="";
        Iterator it=node_neighbors.iterator();
        while(it.hasNext())
        {
            node_neighbors_string=node_neighbors_string+String.valueOf(it.next())+"/";
        }

        node_neighbors_string=node_neighbors_string.substring(0,node_neighbors_string.length()-1);

        return node_neighbors_string;
    }


    //여기서부터 각 서버 요청 url마다 보낼 데이터 JSON으로 넘는 함수
    public ArrayList<Node> requestGetAllNodes(String identification_, String phone_origin_number_){

        //보낼 데이터 JSON에 담기
        //JSON data에 들어가는 값중 한국어는 koreanEncoding함수로 인코딩 해주고 넣어야함
        JSONObject to_send_json_data=null;
        try {
            to_send_json_data = new JSONObject();
            to_send_json_data.put("identification",identification_);
            to_send_json_data.put("phone_origin_number",phone_origin_number_);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //요청할 서버url
        String url= "http://13.125.251.226:5000/getAllNodes";
        //서버로 보낼정보, 인자로 url, json obejct
        try {
            sendPost(url,to_send_json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //서버에서 데이터 받는 thread가 도는 동안 기다리기위한 코드
        while(static_isJsonDataReceived==false)
        {
            Log.i("쓰레드 확인용","데이터받아오는중");
        }
        static_isJsonDataReceived=false;

        //서버에서 모든 노드를 받아서 List에 담음
        return convertNodeJsontoNodeList(static_response_data);
    }

    public LinkedHashSet<Integer> requestPath(String identification_, String phone_origin_number_, int departure_node_id_, int arrival_node_id_){

        //보낼 데이터 JSON에 담기
        //JSON data에 들어가는 값중 한국어는 koreanEncoding함수로 인코딩 해주고 넣어야함
        JSONObject to_send_json_data = null;
        try {
            to_send_json_data=new JSONObject();
            to_send_json_data.put("identification",identification_);
            to_send_json_data.put("phone_origin_number",phone_origin_number_);
            to_send_json_data.put("departure_node_id",departure_node_id_);
            to_send_json_data.put("arrival_node_id",arrival_node_id_);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //요청할 서버url
        String url= "http://13.125.251.226:5000/searchPath";
        //서버로 보낼정보, 인자로 url, json obejct
        try {
            sendPost(url,to_send_json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //서버에서 데이터 받는 thread가 도는 동안 기다리기위한 코드
        while(static_isJsonDataReceived==false)
        {
            Log.i("쓰레드 확인용","데이터받아오는중");
        }
        static_isJsonDataReceived=false;

        //서버에서 모든 노드를 받아서 List에 담음
        return convertPathJsontoList(static_response_data);
    }

    public boolean requestAddNewNode(String node_type_, String node_name_, double pos_x_, double pos_y_, HashSet<Integer> node_neighbors_, String indoor_, String floor_)
    {
        //보낼 데이터 JSON에 담기
        //JSON data에 들어가는 값중 한국어는 koreanEncoding함수로 인코딩 해주고 넣어야함
        JSONObject to_send_json_data=null;
        try {
            to_send_json_data=new JSONObject();
            to_send_json_data.put("node_type",koreanEncoding(node_type_));
            to_send_json_data.put("node_name",koreanEncoding(node_name_));
            to_send_json_data.put("pos_x",pos_x_);
            to_send_json_data.put("pos_y",pos_y_);
            to_send_json_data.put("node_neighbors",convertNodeNeighborsListToString(node_neighbors_));
            to_send_json_data.put("indoor",indoor_);
            to_send_json_data.put("floor",floor_);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //요청할 서버url
        String url= "http://13.125.251.226:5000/addNewNode";
        //서버로 보낼정보, 인자로 url, json obejct
        try {
            sendPost(url,to_send_json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //서버에서 데이터 받는 thread가 도는 동안 기다리기위한 코드
        while(static_isJsonDataReceived==false)
        {
            Log.i("쓰레드 확인용","데이터받아오는중");
        }
        static_isJsonDataReceived=false;

        return true;


    }

    public boolean requestModifyNode(int node_id_,String node_type_, String node_name_,HashSet<Integer> node_neighbors_, String indoor_, String floor_)
    {
        //보낼 데이터 JSON에 담기
        //JSON data에 들어가는 값중 한국어는 koreanEncoding함수로 인코딩 해주고 넣어야함
        JSONObject to_send_json_data=null;
        try {
            to_send_json_data=new JSONObject();
            to_send_json_data.put("node_id",node_id_);
            to_send_json_data.put("node_type",koreanEncoding(node_type_));
            to_send_json_data.put("node_name",koreanEncoding(node_name_));
            to_send_json_data.put("node_neighbors",convertNodeNeighborsListToString(node_neighbors_));
            to_send_json_data.put("indoor",indoor_);
            to_send_json_data.put("floor",floor_);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //요청할 서버url
        String url= "http://13.125.251.226:5000/modifyNode";
        //서버로 보낼정보, 인자로 url, json obejct
        try {
            sendPost(url,to_send_json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //서버에서 데이터 받는 thread가 도는 동안 기다리기위한 코드
        while(static_isJsonDataReceived==false)
        {
            Log.i("쓰레드 확인용","데이터받아오는중");
        }
        static_isJsonDataReceived=false;

        return true;
    }

    public boolean requestRegisterOwnNodeName(int node_id_,String phone_origin_number_,String own_node_name_)
    {
        //보낼 데이터 JSON에 담기
        //JSON data에 들어가는 값중 한국어는 koreanEncoding함수로 인코딩 해주고 넣어야함
        JSONObject to_send_json_data=null;
        try {
            to_send_json_data=new JSONObject();
            to_send_json_data.put("node_id",node_id_);
            to_send_json_data.put("phone_origin_number",phone_origin_number_);
            to_send_json_data.put("own_node_name",koreanEncoding(own_node_name_));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //요청할 서버url
        String url= "http://13.125.251.226:5000/registerOwnNodeName";
        //서버로 보낼정보, 인자로 url, json obejct
        try {
            sendPost(url,to_send_json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //서버에서 데이터 받는 thread가 도는 동안 기다리기위한 코드
        while(static_isJsonDataReceived==false)
        {
            Log.i("쓰레드 확인용","데이터받아오는중");
        }
        static_isJsonDataReceived=false;

        return true;
    }

    public boolean requestRemoveNode(int node_id_)
    {
        //보낼 데이터 JSON에 담기
        //JSON data에 들어가는 값중 한국어는 koreanEncoding함수로 인코딩 해주고 넣어야함
        JSONObject to_send_json_data=null;
        try {
            to_send_json_data=new JSONObject();
            to_send_json_data.put("node_id",node_id_);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //요청할 서버url
        String url= "http://13.125.251.226:5000/removeNode";
        //서버로 보낼정보, 인자로 url, json obejct
        try {
            sendPost(url,to_send_json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //서버에서 데이터 받는 thread가 도는 동안 기다리기위한 코드
        while(static_isJsonDataReceived==false)
        {
            Log.i("쓰레드 확인용","데이터받아오는중");
        }
        static_isJsonDataReceived=false;

        return true;

    }

    public boolean requestRegisterUser(String phone_origin_number_)
    {
        //보낼 데이터 JSON에 담기
        //JSON data에 들어가는 값중 한국어는 koreanEncoding함수로 인코딩 해주고 넣어야함
        JSONObject to_send_json_data=null;
        try {
            to_send_json_data=new JSONObject();
            to_send_json_data.put("phone_origin_number",phone_origin_number_);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //요청할 서버url
        String url= "http://13.125.251.226:5000/registerUser";
        //서버로 보낼정보, 인자로 url, json obejct
        try {
            sendPost(url,to_send_json_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //서버에서 데이터 받는 thread가 도는 동안 기다리기위한 코드
        while(static_isJsonDataReceived==false)
        {
            Log.i("쓰레드 확인용","데이터받아오는중");
        }
        static_isJsonDataReceived=false;

        return true;
    }
}

//Log.i("프로그램시작","프로그램시작");
//
//        //1.전체 노드 불러오기 test
//        String identification="User";
//        String phone_origin_number="0xx0";
//        node_list=requestGetAllNodes(identification,phone_origin_number);
//
//2.전체 노드 불러온다음 경로 요청 test
//JSON 데이터 보낼거
//                        String identification="User";
//                        String phone_origin_number="0xx0";
//                        int departure_node_id=1;
//                        int arrival_node_id=4;
//                        //경로 요청하고 경로순서대로 node_id 갖고있는 list받음
//                        LinkedHashSet<Integer> path_list=requestPath(identification,phone_origin_number,departure_node_id,arrival_node_id);



//3.새로운 노드 추가 요청 test
//                    String node_type="일반";
//                    String node_name="우리집";
//                    double pos_x=3.001;
//                    double pos_y=3.002;
//                    HashSet<Integer> node_neighbors=new HashSet<Integer>();
//                    node_neighbors.add(1);
//                    String indoor="False";
//                    String floor="";
//                    boolean isReqeustSuccess=requestAddNewNode(node_type,node_name,pos_x,pos_y,node_neighbors,indoor,floor);



//4.새로운 노드 추가 요청 test
//                    String node_type="일반";
//                    String node_name="집";
//                    int node_id=1;
//                    HashSet<Integer> node_neighbors=new HashSet<Integer>();
//                    node_neighbors.add(2);
//                    node_neighbors.add(3);
//                    String indoor="False";
//                    String floor="";
//                    boolean isReqeustSuccess=requestModifyNode(node_id,node_type,node_name,node_neighbors,indoor,floor);

//5.기존노드에 자기만의 노드이름등록 test
//                    String own_node_name="우리집입니다";
//                    int node_id=1;
//                    String phone_origin_number="0xx0";
//                    boolean isRequestSuccess=requestRegisterOwnNodeName(node_id,phone_origin_number,own_node_name);

//6.기존노드 삭제 test
//                    int node_id=5;
//                    boolean isRequestSuccess=requestRemoveNode(node_id);

//7.처음에 사용자 등록 test
//                    String phone_origin_number="0xx0";
//                    boolean isRequestSuccess=requestRegisterUser(phone_origin_number);

//전체 노드 확인 test
//        String identification="User";
//        String phone_origin_number="0xx0";
//        node_list = network.requestGetAllNodes(identification,phone_origin_number);
//
//        String temp = "";
//        Iterator<Node> iter = node_list.iterator();
//        while(iter.hasNext()){
//            Node n = iter.next();
//            temp += n.getNode_id() + " " + n.getPos_x() + " " + n.getPos_y() + "\n";
//        }
//        progress.setText(temp);
