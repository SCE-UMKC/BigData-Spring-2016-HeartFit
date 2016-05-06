/*
 * Copyright 2015 Dejan Djurovski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.wearablemessageapiexample;

import android.app.Activity;
import android.content.DialogInterface;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

public class MainActivity extends Activity {
    private final String MESSAGE1_PATH = "/message1";
    private final String MESSAGE2_PATH = "/message2";

    private EditText receivedMessagesEditText;
    private View message1Button;
    private View message2Button;
    private GoogleApiClient apiClient;
    private NodeApi.NodeListener nodeListener;
    private String remoteNodeId;
    private MessageApi.MessageListener messageListener;
    private Handler handler;
    String X,Y,Z;
    String constant="";
    String next;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        handler = new Handler();

        receivedMessagesEditText = (EditText) findViewById(R.id.receivedMessagesEditText);
        message1Button = findViewById(R.id.message1Button);
        message2Button = findViewById(R.id.message2Button);


        // Set message1Button onClickListener to send message 1
        message1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wearable.MessageApi.sendMessage(apiClient, remoteNodeId, MESSAGE1_PATH, null).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                    @Override
                    public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                        if (sendMessageResult.getStatus().isSuccess())
                            Toast.makeText(getApplication(), getString(R.string.message1_sent), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplication(), getString(R.string.error_message1), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Set message2Button onClickListener to send message 2
        message2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Wearable.MessageApi.sendMessage(apiClient, remoteNodeId, MESSAGE2_PATH, null).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
                    @Override
                    public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                        if (sendMessageResult.getStatus().isSuccess())
                            Toast.makeText(getApplication(), getString(R.string.message2_sent), Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getApplication(), getString(R.string.error_message2), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Create NodeListener that enables buttons when a node is connected and disables buttons when a node is disconnected
        nodeListener = new NodeApi.NodeListener() {
            @Override
            public void onPeerConnected(Node node) {
                remoteNodeId = node.getId();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        message1Button.setEnabled(true);
                        message2Button.setEnabled(true);
                        Toast.makeText(getApplication(), getString(R.string.peer_connected), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onPeerDisconnected(Node node) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        message1Button.setEnabled(false);
                        message2Button.setEnabled(false);
                        Toast.makeText(getApplication(), getString(R.string.peer_disconnected), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        // Create MessageListener that receives messages sent from a wearable
        messageListener = new MessageApi.MessageListener() {
            @Override
            public void onMessageReceived(final MessageEvent messageEvent) {
                if (messageEvent.getPath().equals(MESSAGE1_PATH)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            byte[] bytes =messageEvent.getData();
                            String res=new String(bytes);
                            if(res!=null)
                            receivedMessagesEditText.append("\n rate: " +res);
                            else
                                receivedMessagesEditText.append("\n no data received");
                            Calendar cal = Calendar.getInstance();
                            int hour =cal.get(Calendar.HOUR_OF_DAY);
                            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
                            Date todayDate = new Date();
                            String thisDate = currentDate.format(todayDate);
//                            String date=getDate();
                            String urlstring="http://heartfitapp.netne.net/insert_heart.php?heart_rate="+res+"&date="+thisDate+"&hour="+hour;
                            new Httprequestclass().execute(urlstring);

                        }
                    });
                }

                if (messageEvent.getPath().equals(MESSAGE2_PATH)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            byte[] bytes = messageEvent.getData();
                            String res = new String(bytes);
                            if (res != null)
                                receivedMessagesEditText.append("\n" + res);
                            else
                                receivedMessagesEditText.append("\n no data received");


                            StringTokenizer st2 = new StringTokenizer(res);
                            while (st2.hasMoreElements()) {
                                X = (String) st2.nextElement();
                                Y = (String) st2.nextElement();
                                Z = (String) st2.nextElement();
                            }
//                            X=X.substring(2, 2);
//                            Y=Y.substring(2, 2);
//                            Z=Z.substring(2, 2);
//                            next=X+Y+Z;

//                            next = Integer.toString(x) + Integer.toString(y) + Integer.toString(z);
                            if(constant==null) {
                                constant=next;
                                count++;
                            }
                            if(constant!=next)
                            {
                                constant=next;
//                                count++;

                            }



                            Calendar cal = Calendar.getInstance();
                            int minute = cal.get(Calendar.MINUTE);
                            int second= cal.get(Calendar.SECOND);

                                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
                                Date todayDate = new Date();
                                String thisDate = currentDate.format(todayDate);
                                int hour = cal.get(Calendar.HOUR_OF_DAY);
//                                int minute = cal.get(Calendar.MINUTE);
                                String urlstring = "http://heartfitapp.netne.net/insert_accelerometer.php?rate_x=" + X + "&rate_y=" + Y + "&rate_z=" + Z + "&read_date=" + thisDate + "&read_hour=" + hour + "&read_min=" + minute;
                                new Httprequestclass().execute(urlstring);
////                                Log.i("count",Integer.toString(count));
//                                Log.i("accel:",next);


                        }
                    });
                }
            }
        };

        // Create GoogleApiClient
        apiClient = new GoogleApiClient.Builder(getApplicationContext()).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                // Register Node and Message listeners
                Wearable.NodeApi.addListener(apiClient, nodeListener);
                Wearable.MessageApi.addListener(apiClient, messageListener);
                // If there is a connected node, get it's id that is used when sending messages
                Wearable.NodeApi.getConnectedNodes(apiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
                    @Override
                    public void onResult(NodeApi.GetConnectedNodesResult getConnectedNodesResult) {
                        if (getConnectedNodesResult.getStatus().isSuccess() && getConnectedNodesResult.getNodes().size() > 0) {
                            remoteNodeId = getConnectedNodesResult.getNodes().get(0).getId();
                            message1Button.setEnabled(true);
                            message2Button.setEnabled(true);
                        }
                    }
                });
            }

            @Override
            public void onConnectionSuspended(int i) {
                message1Button.setEnabled(false);
                message2Button.setEnabled(false);
            }
        }).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                if (connectionResult.getErrorCode() == ConnectionResult.API_UNAVAILABLE)
                    Toast.makeText(getApplicationContext(), getString(R.string.wearable_api_unavailable), Toast.LENGTH_LONG).show();
            }
        }).addApi(Wearable.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check is Google Play Services available
        int connectionResult = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (connectionResult != ConnectionResult.SUCCESS) {
            // Google Play Services is NOT available. Show appropriate error dialog
            GooglePlayServicesUtil.showErrorDialogFragment(connectionResult, this, 0, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
        } else {
            apiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        // Unregister Node and Message listeners, disconnect GoogleApiClient and disable buttons
        Wearable.NodeApi.removeListener(apiClient, nodeListener);
        Wearable.MessageApi.removeListener(apiClient, messageListener);
        apiClient.disconnect();
        message1Button.setEnabled(false);
        message2Button.setEnabled(false);
        super.onPause();
    }



    //new class for pushing the data to server


   public class Httprequestclass extends AsyncTask<String, Void, String> {
        String res;
        String name;
//            private Context context;

        @Override
        protected String doInBackground(String... params) {
            String urlString=params[0];
            try {
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
//                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
                InputStream stream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(
                        stream);
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();

                String bufferedStrChunk = null;

                while ((bufferedStrChunk = bufferedReader.readLine()) != null) {
                    stringBuilder.append(bufferedStrChunk);
                }
                res=stringBuilder.toString();
                try
                {
//                    JSONObject json_data = new JSONObject(res);
////                    name=(json_data.getString("name"));
////                    Toast.makeText(getBaseContext(), "Name : "+name,
////                            Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    Log.e("Fail 3", e.toString());
                }
//            Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
            }
            catch(Exception e)
            {
//                    Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }


            return res;
        }

        @Override
        protected void onPostExecute(String result) {

        }


    }


}
