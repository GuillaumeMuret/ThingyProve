/**
 * @file DataStore.java
 * @brief Big local singleton database
 *
 * @version 1.0
 * @date 10/05/16
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

import android.util.Log;

import fr.prove.thingy.BuildConfig;

public class DataStore {

    /**
     * Singleton management
     */
    private static DataStore instance;

    /**
     * Getter of the instance DataStore
     * @return the instance DataStore
     */
    public static DataStore getInstance () {
        if (instance == null)
            instance = new DataStore();
        return instance;
    }

    /**
     * Object Operator
     */
    private Operator operator;

    /**
     * Setter of the operator
     * @param operator : the operator
     */
    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    /**
     * Getter of the operator
     * @return the operator
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Object History Card ID
     */
    private String historyCardID;

    /**
     * Setter of the card ID
     * @param historyCardID : the card id of the card history
     */
    public void setHistoryCardID(String historyCardID){
        this.historyCardID=historyCardID;
    }

    /**
     * Getter of the card ID
     * @return : the card id of the card history
     */
    public String getHistoryCardID(){
        return historyCardID;
    }

    /**
     * The card id of the test
     */
    private String testCardID;

    /**
     * Setter of the card ID
     * @param testCardID : card id of the test
     */
    public void setTestCardID(String testCardID){
        this.testCardID=testCardID;
    }

    /**
     * Getter of the card ID
     * @return : the card id of the test
     */
    public String getTestCardID(){
        return testCardID;
    }

    /**
     * The recent History
     */
    private History recentHistory;

    /**
     * Setter of the recent history
     * @param recentHistory : the recent history
     */
    public void setRecentHistory(History recentHistory){
        this.recentHistory=recentHistory;
    }

    /**
     * Getter of the recent history
     * @return : the recent history
     */
    public History getRecentHistory(){
        return recentHistory;
    }

    /**
     * Object TestList
     */
    private TestList testList;

    /**
     * Getter of the test list
     * @return the test list
     */
    public TestList getTestList() {
        return testList;
    }

    /**
     * Setter of the test list
     * @param testList : the test list
     */
    public void setTestList(TestList testList) {
        this.testList = testList;
    }

    /**
     * Local connection (host gateway) IP
     */
    private String hostIPAddress;

    /**
     * Getter of the ip host address
     * @return the ip host address
     */
    public String getHostIPAddress() {

        // Force address (debug)
        if (BuildConfig.DEBUG) {
            // rasp -> 192.168.42.1
            hostIPAddress = "192.168.5.189";
            //hostIPAddress = "192.168.42.1";
        }
        return hostIPAddress;
    }

    /**
     * Setter of the ip host address
     * @param hostIPAddress : the ip host address
     */
    public void setHostIPAddress(String hostIPAddress) {

        if (BuildConfig.DEBUG) Log.d("IP_HOST", "Set host IP : " + hostIPAddress);

        this.hostIPAddress = hostIPAddress;
    }

    /**
     * Device default orientation
     */
    private boolean isDeviceTablet;

    /**
     * Getter if the device is a tablet or not
     * @return if the device is a tablet
     */
    public boolean isDeviceTablet() {
        return isDeviceTablet;
    }

    /**
     * Setter if the device is a tablet or not
     * @param deviceTablet : boolean if the device is a tablet or not
     */
    public void setDeviceTablet(boolean deviceTablet) {
        isDeviceTablet = deviceTablet;
    }
}
