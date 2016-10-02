/**
 * @file TestItem.java
 * @brief Describes a test item, with its name and its attributes (decription, value ...)
 *
 * @version 1.0
 * @date 30/03/16
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

import org.json.JSONException;
import org.json.JSONObject;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;
import fr.prove.thingy.constants.Number;

public class TestItem {

    /**
     * Possible statues of the test items
     */
    public static final int TEST_STATUS_PENDING = 0;
    public static final int TEST_STATUS_DONE = 1;
    public static final int TEST_STATUS_FAILED = 2;
    public static final int TEST_STATUS_RUNNING = 5;
    public static final int TEST_STATUS_SKIP = 6;

    /**
     * name of the test item
     */
    private String name;

    /**
     * description of the test item
     */
    private String description;

    /**
     * id of the test item
     */
    private String id;

    /**
     * status of the test item
     */
    private int status;

    /**
     * Constructor of the test item (for the history detail)
     * @param name the name of the test
     * @param description the description of the test
     * @param status the status of the test
     */
    public TestItem(String name, String description, int status) {
        this.name = name;
        this.description = description;
        this.status = status;

        if (BuildConfig.DEBUG) Log.d("TestItem", toString());
    }

    /**
     * Constructor of the Test Item of the TestActivity
     * @param obj the json object
     * @throws JSONException
     */
    public TestItem(JSONObject obj) throws JSONException {
        this.name = obj.getString(ProtocoleVocabulary.JSON_PARAMS_NAME);
        this.description = obj.getString(ProtocoleVocabulary.JSON_PARAMS_DESCRIPTION);
        this.id = obj.getString(ProtocoleVocabulary.JSON_PARAMS_ID);
        this.status = TEST_STATUS_PENDING;
    }

    /**
     * Constructor of the detail of the cardHistory
     * @param obj the json object
     * @param result the result of the test item
     * @throws JSONException
     */
    public TestItem(JSONObject obj,int result) throws JSONException {
        this.name = obj.getString(ProtocoleVocabulary.JSON_PARAMS_NAME);
        this.description = obj.getString(ProtocoleVocabulary.JSON_PARAMS_DESCRIPTION);
        this.status = result == Number.STATUS_VALIDATION ? TEST_STATUS_DONE : TEST_STATUS_FAILED;
    }

    /**
     * Returns the name of the current test
     * @return the test's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the current test
     * @return the test's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the status of the current test
     * @return the test's status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status as waiting
     */
    public void makeAsTestWaiting () {
        this.status = TEST_STATUS_PENDING;
    }

    /**
     * Sets the status as success
     */
    public void makeAsSuccess () {
        this.status = TEST_STATUS_DONE;
    }

    /**
     * Sets the status as skip
     */
    public void makeAsSkip () {
        this.status = TEST_STATUS_SKIP;
    }


    /**
     * Sets the test as failed
     */
    public void makeAsFailed () {
        this.status = TEST_STATUS_FAILED;
    }

    /**
     * Sets the test as running
     */
    public void makeAsRunning () {
        this.status = TEST_STATUS_RUNNING;
    }

    public boolean hasSameID (String idToCompare) {
        return idToCompare != null && idToCompare.equalsIgnoreCase(id);
    }

    /**
     * toString implementation, use it for tests or debug with if (BuildConfig.DEBUG) Log.d (...)
     * @return the description of the test object as String
     */
    @Override
    public String toString() {
        return "TestItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
