/**
 * @file PostmanUI.java
 * @brief create the socket, read and write on this socket
 * @version 1.0
 * @date 14/04/2016
 * @author Guillaume Muret
 * @copyright Copyright (c) 2016, PRØVE
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of PRØVE, Angers nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY PRØVE AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL PRØVE AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package fr.prove.thingy.communication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.constants.Timeouts;
import fr.prove.thingy.model.DataStore;

public class Postman {

    /**
     * State of socket's postman
     */
    public static final int CONNEXION = 0;
    public static final int CONNECTED = 1;
    public static final int ERROR = 2;
    public static final int KILL = 3;

    /**
     * State of the socket
     */
    public int stateSocket;

    /**
     * Port use for the communication
     */
    public static final int SERVER_PORT = 50000;

    /**
     * type of encodage.
     */
    private final static String ENCODAGE = "UTF-8";

    /**
     * The socket used for the connexion
     */
    private Socket mySocket;

    /**
     * the buffer used to read a message on the socket
     */
    private BufferedReader bufferedReader;

    /**
     * the buffer used to write a message on the socket
     */
    private BufferedWriter bufferedWriter;

    /**
     * Builder of the Postman
     */
    public Postman() {
        stateSocket = CONNEXION;
        SetUpConnexion setUpConnexion = new SetUpConnexion();
        setUpConnexion.execute();
    }

    /**
     * write the message on the socket
     * @param message
     */
    public void writeMessage(String message) {
        new Write().execute(message);
    }

    /**
     * read a message on the socket and return the message
     * @return the message
     * @throws IOException
     */
    public String readMessage() throws IOException, NullPointerException {
        return bufferedReader.readLine();
    }

    /**
     * process to connect the application to the raspberry by the socket
     * @return mySocket
     */
    private class SetUpConnexion extends AsyncTask<Void, Void, Void> {
        /**
         * Called when the client is executed
         * @param arg0
         * @return
         */
        @Override
        protected Void doInBackground(Void... arg0) {
            if (BuildConfig.DEBUG) Log.d("Creation du socket", "debug");

            stateSocket = ERROR; // default error : connection not available / has failed

            // Set up the socket
            try {

                mySocket = new Socket();
                mySocket.connect(new InetSocketAddress(DataStore.getInstance().getHostIPAddress(), SERVER_PORT), Timeouts.TW_CONNECTION_SOCKET);
                //mySocket.connect(new InetSocketAddress(NetworkUtils.getHostIPAddress(), SERVER_PORT), Timeouts.TW_CONNECTION_SOCKET);

                if (BuildConfig.DEBUG) Log.d("Socket Created", "debug");

                bufferedReader = new BufferedReader(new InputStreamReader(mySocket.getInputStream(), ENCODAGE));
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(mySocket.getOutputStream(), ENCODAGE));
                stateSocket = CONNECTED;


            } catch (SocketTimeoutException ste) {

                // appears when the socket reach timeout limit
                Log.e("SocketError", "SocketTimeoutException : Raspberry not found");

            } catch (ConnectException ce) {

                // appears when the network is not available (wifi may be disabled)
                Log.e("SocketError", "ConnectException : Raspberry not found / Wifi interface disabled");

            } catch (IOException e) {

                // appears when the socket is already connected
                Log.e("SocketError", "debug");
                e.printStackTrace();
            }
            return null;
        }

        /**
         *  Called when the task is finished
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    /**
     * Write on the BufferWriter to send a message to the raspberry
     */
    private class Write extends AsyncTask<String, Void, Void> {
        /** Called when the client is executed */
        @Override
        protected Void doInBackground(String... mess) {
            if (mySocket != null) {
                if (!mySocket.isClosed()) {
                    try {
                        if (BuildConfig.DEBUG) Log.d("ECRITURE...", "debug");
                        bufferedWriter.write(mess[0], 0, mess[0].length());
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        if (BuildConfig.DEBUG) Log.d("FIN ECRITURE", "debug");
                    } catch (IOException e) {
                        e.printStackTrace();
                        stateSocket=ERROR;
                        if (BuildConfig.DEBUG) Log.d("ECRITURE ERROR","debug");
                    }
                }else{
                    stateSocket=ERROR;
                }
            }
            return null;
        }

        /**
         * Called when the task is finished
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    /**
     * Process called to close the socket
     */
    public void closeSocket() {
        if (this.mySocket != null) {
            if (this.mySocket.isConnected()) {
                try {
                    this.mySocket.close();
                    this.stateSocket=KILL;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Getter of the socket state
     * @return the state's socket
     */
    public int getStateSocket(){
        return stateSocket;
    }
}