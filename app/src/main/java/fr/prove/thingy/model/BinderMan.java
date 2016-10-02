/**
 * @file BinderMan.java
 * @brief Communication service, contains model instances, and make possible data management bewteen Activities
 *
 * @version 2.2
 * @date 01/05/16
 * @author François LEPAROUX
 * @collab Guillaume MURET
 * @copyright
 *	Copyright (c) 2016, PRØVE
 * 	All rights reserved.
 * 	Redistribution and use in source and binary forms, with or without
 * 	modification, are permitted provided that the following conditions are met:
 *
 * 	* Redistributions of source code must retain the above copyright
 * 	  notice, this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright
 * 	  notice, this list of conditions and the following disclaimer in the
 * 	  documentation and/or other materials provided with the distribution.
 * 	* Neither the name of PRØVE, Angers nor the
 * 	  names of its contributors may be used to endorse or promote products
 *   	derived from this software without specific prior written permission.
 *
 * 	THIS SOFTWARE IS PROVIDED BY PRØVE AND CONTRIBUTORS ``AS IS'' AND ANY
 * 	EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * 	WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * 	DISCLAIMED. IN NO EVENT SHALL PRØVE AND CONTRIBUTORS BE LIABLE FOR ANY
 * 	DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * 	(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * 	LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * 	ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * 	(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * 	SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package fr.prove.thingy.model;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.bus.BusRequest;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.bus.BusProtocol;
import fr.prove.thingy.communication.Communication;
import fr.prove.thingy.communication.Postman;
import fr.prove.thingy.communication.protocole.ProcessOut;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;
import fr.prove.thingy.constants.Timeouts;
import fr.prove.thingy.utils.JsonUtils;

public class BinderMan extends Service {

    /**
     * the binder of this class
     */
    private final IBinder mBinder = new MyBinder();

    /**
     * The event bus
     */
    private EventBus bus = EventBus.getDefault();

    /**
     * the communication used
     */
    private Communication myCommunication;

    /**
     * the handler for the timer
     */
    private Handler handlerTimer;

    /**
     * the runnable of the timer (we post an ALARM_UI if time is over)
     */
    private Runnable myTimer;

    /**
     * The thread which ask to the communication to ask to the postman to read a message on the socket
     */
    private ReadThread readThread;

    /**
     * letterbox of the binder man
     */
    private Handler handlerService = new Handler() {
        @Override
        synchronized public void handleMessage(Message msg) {
            //bus.post(myCommunication.distributorUI.dispatch(msg.getData().getString(BusProtocol.BINDER_MAN_RECEIVE_MESSAGE));
            try {
                resetTimer();
                JSONObject mainObj = JsonUtils.strToJson(msg.getData().getString(BusProtocol.BINDER_MAN_RECEIVE_MESSAGE));
                JSONObject params = mainObj.getJSONObject(ProtocoleVocabulary.JSON_PARAMS);
                bus.post(myCommunication.distributor.dispatch(mainObj.getString(ProtocoleVocabulary.JSON_PROCESS),params));
            } catch (JSONException e) {
                e.printStackTrace();
                bus.post(new BusResponse(BusType.ALARM_UI));
            }
        }
    };

    /**
     * Called when the user launch the binder man
     * @param intent : the card intent
     * @param flags : the flags
     * @param startId : the start ID
     * @return : service's state
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        // Create the communication
        myCommunication = new Communication();

        // Create the timer handler
        handlerTimer = new Handler();

        // Create the thread of the reading
        readThread = new ReadThread();

        if (BuildConfig.DEBUG) Log.d("BMAN", "onStartCommand");

        // Prevents from multiple registration if the service is recreated
        if (!bus.isRegistered(this)) {
            bus.register(this);
        }
        return Service.START_NOT_STICKY;
    }

    /**
     * Bind of the binder man
     * @param intent : the intent
     * @return the binder
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (BuildConfig.DEBUG) Log.d("BMAN", "onBind");
        return mBinder;
    }

    /**
     * nested class : Binder man class
     */
    public class MyBinder extends Binder {
        BinderMan getService() {
            if (BuildConfig.DEBUG) Log.d("BMAN", "getService");
            return BinderMan.this;
        }
    }

    /**
     * Called when a bus request arrive
     * @param request : the bus request
     */
    @Subscribe
    public void onEvent(BusRequest request) {

        if (BuildConfig.DEBUG) Log.d("BMAN", " --> " + request.getRequestType());

        switch (request.getRequestType()) {

            // The operator want to connect
            case CONNECT_OPERATOR:
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP, BusType.ALARM_UI);
                sendAction(ProcessOut.TESTER_GUARD_ASK_CHECK_LOG_TESTER, request.getData());
                break;

            // Create the socket
            case SET_UP_CONNEXION:
                if (BuildConfig.DEBUG) Log.d("setUpConnexion", "debug");
                new AsyncConnect().execute(BusType.SET_UP_CONNEXION);
                break;

            // Create a new communication
            case NEW_COMMUNICATION:
                if (BuildConfig.DEBUG) Log.d("newCommunication", "debug");
                readThread = new ReadThread();
                myCommunication = new Communication();
                break;

            // Ask recent history to the historian
            case ASK_RECENT_HISTORY:
                if (BuildConfig.DEBUG) Log.d("askRecentHistory", "debug");
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                sendAction(ProcessOut.HISTORIAN_ASK_RECENT_HISTORY, null);
                break;

            // Ask if the card exist or not
            case ASK_EXISTANT_CARD:
                if (BuildConfig.DEBUG) Log.d("askCheckCardID", "debug");
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                sendAction(ProcessOut.CARD_GUARD_ASK_CHECK_CARD_ID, request.getData());
                break;

            // Ask card history to the historian
            case ASK_CARD_HISTORY:
                if (BuildConfig.DEBUG) Log.d("askCardHistory", "debug");
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                sendAction(ProcessOut.HISTORIAN_ASK_CARD_HISTORY, request.getData());
                break;

            // Ask to create a new card
            case NEW_CARD:
                if (BuildConfig.DEBUG) Log.d("newCard", "debug");
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                sendAction(ProcessOut.CARD_GUARD_NEW_CARD,null);
                break;

            // Ask the nominal list for the test
            case ASK_NOMINAL_LIST_TEST:
                if (BuildConfig.DEBUG) Log.d("askNominalListTest","debug");
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                sendAction(ProcessOut.SCHOLAR_TEST_ASK_NOMINAL_LIST_TEST, null);
                break;

            // Ask save validation of the result of the test
            case ASK_SAVE_VALIDATION_RESULT:
                if (BuildConfig.DEBUG) Log.d("askValidSave","debug");
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                sendAction(ProcessOut.HISTORIAN_ASK_VALID_SAVE, request.getData());
                break;

            // Ask to stop the current running test
            case SET_STOP:
                resetTimer();
                if (BuildConfig.DEBUG) Log.d("setStop","debug");
                sendAction(ProcessOut.TEST_MANAGER_SET_STOP, request.getData());
                break;

            // Ask to run the test
            case RUN_TESTS:
                if (BuildConfig.DEBUG) Log.d("runTests","debug");
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                sendAction(ProcessOut.TESTER_RUN_TESTS, null);
                break;

            // Ask to save the campaign
            case ASK_SAVE_CAMPAIGN:
                if (BuildConfig.DEBUG) Log.d("askSaveCampaign","debug");
                launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                sendAction(ProcessOut.TEST_MANAGER_SET_CAMPAIGN, request.getData());
                break;

            // Ask to close the socket
            case BUS_CLOSE_SOCKET:
                if (BuildConfig.DEBUG) Log.d("closeSocket","debug");
                myCommunication.closeSocket();
                break;

            // Ask to start the time of the splash screen
            case START_TIMER_TIMEOUT_SPLASH:
                if (BuildConfig.DEBUG) Log.d("startTimer","debug");
                launchTimer(Timeouts.TIMEOUT_SPLASH,BusType.DISPLAY_LOGIN_ACTIVITY);
                break;

            // Ask to start the time of the splash screen
            case START_TIMER_TIMEOUT_UPDATING_TEST:
                if (BuildConfig.DEBUG) Log.d("startTimer","debug");
                launchTimer(Timeouts.TIMEOUT_UPDATING_TEST,BusType.ALARM_UI);
                break;

            case START_TIMER_TIMEOUT_LOGIN:
                if (BuildConfig.DEBUG) Log.d("startTimer","debug");
                launchTimer(Timeouts.TIMEOUT_UPDATING_TEST,BusType.ALARM_UI);
                break;
        }
    }

    /**
     * nested class to set up the connection
     */
    private class AsyncConnect extends AsyncTask<BusType, String, BusType> {

        /**
         * Called after the execution of "doInBackground"
         * @param requestType : the request type
         */
        @Override
        protected void onPostExecute(BusType requestType) {
            if (requestType == BusType.ALARM_UI) {
                bus.post(new BusResponse(requestType));
            }
        }

        /**
         * Create the connection or report an error (ALARM_UI)
         * @param requestTypes : the request type
         * @return the request type
         */
        @Override
        protected BusType doInBackground(BusType... requestTypes) {
            if (BuildConfig.DEBUG) Log.d("Start Comm", "debug");
            // Wait a change of the state of the postman
            while (myCommunication.getPostman().stateSocket == Postman.CONNEXION) {}
            if (myCommunication.getPostman().stateSocket == Postman.CONNECTED) {
                if (BuildConfig.DEBUG) Log.d("no error Connexion", "debug");
                try {
                    readThread.start();
                    myCommunication.processManager(ProcessOut.BRAIN_ASK_READY,null);
                    launchTimer(Timeouts.TW_TIMER_WAIT_PROCESS_FROM_RASP,BusType.ALARM_UI);
                } catch (IllegalThreadStateException e) {
                    myCommunication = new Communication();
                    e.printStackTrace();
                }
            } else if (myCommunication.getPostman().stateSocket == Postman.ERROR){
                if (BuildConfig.DEBUG) Log.d("errorConnexion", "debug");
                myCommunication = new Communication();
                // Report an error
                requestTypes[0] = BusType.ALARM_UI;
            }
            return requestTypes[0];
        }
    }

    /**
     * Set the bus event
     * @param bus : the event bus
     */
    public void setBus(EventBus bus) {
        this.bus = bus;
    }

    /**
     * Process which tell the communication to send a message for the raspberry
     * @param procedureOut : the process to send
     * @param data : the data to send
     */
    private void sendAction(ProcessOut procedureOut, Bundle data) {
        if (myCommunication != null) {
            if (readThread.getState() == Thread.State.RUNNABLE) {
                // Send the message
                myCommunication.processManager(procedureOut, data);
            } else {
                // If there is a problem on the reading thread
                bus.post(new BusResponse(BusType.ALARM_UI));
            }
        } else {
            // Report a problem if there is no communication
            bus.post(new BusResponse(BusType.ALARM_UI));
        }
    }

    /**
     * Reading thread class called to read a message
     */
    private class ReadThread extends Thread {

        /**
         * The received message from the socket
         */
        String receivedMessage;

        /**
         * Called to read a message and send this message to the binder man's letter box
         * @throws IOException
         * @throws NullPointerException
         */
        private void manageReading() throws IOException, NullPointerException{


            // loop which read the buffer and block the task when nothing is received
            while ((receivedMessage = myCommunication.readComMessage()) == null) ;

            if (BuildConfig.DEBUG) Log.d("MESSAGE RECU >", "debug"+ receivedMessage);
            // Reset the timer -> we have a response from the raspberry ! All clear !
            resetTimer();
            // Send message to the BinderMan letter box
            Message m = new Message();
            Bundle b = new Bundle();
            b.putString(BusProtocol.BINDER_MAN_RECEIVE_MESSAGE, receivedMessage);
            m.setData(b);
            handlerService.sendMessage(m);
        }

        /**
         * Process called when a "start" of the thread occur
         */
        @Override
        public void run() {

            try {
                // infinite loop. If a network problem occur : the infinite loop die
                while (true) {
                    // manage the message read
                    manageReading();
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                if (BuildConfig.DEBUG) Log.d("Pas de socket...", "debug");
            } catch (IOException ioe){
                ioe.printStackTrace();
                if (BuildConfig.DEBUG) Log.d("Socket fermée...", "debug");
            }
        }
    }

    /**
     * Launch the timer -> if time is over, the BusType will be post.
     * @param time : the elapsed time
     * @param busType : the bus to post after the elapsed time
     */
    private void launchTimer(int time, final BusType busType){
        Log.d("TIMER LAUNCHED","debug");
        handlerTimer.postDelayed(myTimer=new Runnable() {
            @Override
            public void run() {
                // post a message when time is over
                Log.d("END OF THE TIMER","debug");
                bus.post(new BusResponse(busType));
            }
        }, time);
    }

    /**
     * Reset the timer (launch previously) : the bus wil not be post (cf launchTimer)
     */
    private void resetTimer(){
        handlerTimer.removeCallbacks(myTimer);
    }

    /**
     * Called when the service is destroyed
     */
    @Override
    public void onDestroy() {
        bus.unregister(this);
        super.onDestroy();
    }
}
