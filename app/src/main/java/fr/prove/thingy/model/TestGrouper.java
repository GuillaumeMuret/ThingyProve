/**
 * @file TestGrouper.java
 * @brief Contains a list of test (test grouper is the parent object, like a category)
 *
 * @version 1.0
 * @date 06/05/16
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.communication.protocole.ProtocoleVocabulary;

public class TestGrouper {

    /**
     * Possible statues of the test grouper
     */
    public static final int TEST_STATUS_EDIT_SKIP = 0;
    public static final int TEST_STATUS_EDIT_MAKE = 1;
    public static final int TEST_STATUS_OTHER = 2;

    public static final int TEST_STATUS_LOCKED = 3;

    /**
     * status of the test group
     */
    private int status;

    /**
     * Our test list to execute for this category
     */
    private ArrayList<TestItem> testItems;

    /**
     * The name of this test category
     */
    private String categoryName;

    /**
     * Tell if the test is required or not
     */
    private boolean isRequired;

    /**
     * The ID of the scenrario
     */
    private String IDscenario; // MAJ 15/05/2016

    /**
     * Main constructor
     * @param categoryName the name of the category
     * @param testItems the list of test to execute
     */
    public TestGrouper(String categoryName, ArrayList<TestItem> testItems) {
        this.categoryName = categoryName;
        this.testItems = testItems;
    }

    /**
     * Constructor of the TestGrouper
     * @param obj : the json object
     * @throws JSONException
     */
    public TestGrouper(JSONObject obj) throws JSONException {
        this.categoryName = obj.getString(ProtocoleVocabulary.JSON_PARAMS_NAME);
        this.IDscenario = obj.getString(ProtocoleVocabulary.JSON_PARAMS_ID_SCENARIO);
        this.isRequired = obj.getInt(ProtocoleVocabulary.JSON_PARAMS_OBLIGATION) == 1;

        this.testItems = new ArrayList<>();

        JSONArray testArray = obj.getJSONArray(ProtocoleVocabulary.JSON_PARAMS_ITEMS);

        if (BuildConfig.DEBUG) Log.d("TestGrouper", toString());

        for (int i=0;i<testArray.length();i++) {
            testItems.add(new TestItem(testArray.getJSONObject(i)));
        }
    }

    /**
     * Constructor of the TestGrouper of the detail history
     * @param obj : the json object
     * @param typeDetail : type card history detail
     * @throws JSONException
     */
    public TestGrouper(JSONObject obj,int typeDetail) throws JSONException {
        this.categoryName = obj.getString(ProtocoleVocabulary.JSON_PARAMS_NAME);
        this.status=TEST_STATUS_OTHER;

        this.testItems = new ArrayList<>();

        JSONArray testArray = obj.getJSONArray(ProtocoleVocabulary.JSON_PARAMS_ITEMS);


        if (BuildConfig.DEBUG) Log.d("TestGrouper", toString());

        for (int i=0;i<testArray.length();i++) {
            testItems.add(new TestItem(testArray.getJSONObject(i),testArray.getJSONObject(i).getInt(ProtocoleVocabulary.JSON_PARAMS_RESULT)));
        }
    }

    /**
     * Returns the list of tests to execute
     * @return the test list for this category / grouper in an ArrayList
     */
    public ArrayList<TestItem> getTestItems() {
        return testItems;
    }

    /**
     * Sets the status as skip test
     */
    public void makeAsSkip () {
        this.status = TEST_STATUS_EDIT_SKIP;
    }

    /**
     * Sets the status as locked test
     */
    public void makeAsLocked () {
        this.status = TEST_STATUS_LOCKED;
    }

    /**
     * Sets the status as skip test
     */
    public void makeAsOther () {
        this.status = TEST_STATUS_OTHER;
    }

    /**
     * Sets the status as edition
     */
    public void makeAsEdit () {
        this.status = TEST_STATUS_EDIT_MAKE;
    }

    /**
     * Returns the category name
     * @return the test name for this category / grouper as a String
     */
    public String getCategoryName() {
        return categoryName;
    }

    public String getIDscenario() {
        return IDscenario;
    }

    public boolean hasSameID (String id) {
        return id != null && id.equalsIgnoreCase(IDscenario);
    }

    @Override
    public String toString() {
        return "TestGrouper{" +
                "testItems=" + testItems +
                ", categoryName='" + categoryName + '\'' +
                ", isRequired=" + isRequired +
                ", IDscenario=" + IDscenario +
                '}';
    }

    /**
     * Returns the status of the current test
     * @return the test's status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Getter of the test group requirement
     * @return the test group requirement
     */
    public boolean isRequired() {
        return isRequired;
    }
}
