/**
 * @file TestList.java
 * @brief Manage the test model and his creation from the proxy
 *
 * @version 1.0
 * @date 13/05/16
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestList {

    /**
     * test groupers list
     */
    private ArrayList<TestGrouper> testList;

    /**
     * Constructor of the test list
     */
    public TestList() {
        testList = new ArrayList<>();
    }

    /**
     * Setter of the test list with the received json
     * @param testListJson the received json
     */
    public void setTestList (String testListJson) {

        testList.clear();

        try {
            JSONObject obj = new JSONObject(testListJson);
            JSONArray jsonTests = obj.getJSONArray("list");

            // Create the test list
            for (int i=0;i<jsonTests.length();i++) {
                TestGrouper tg = new TestGrouper(jsonTests.getJSONObject(i));
                tg.makeAsOther();
                testList.add(tg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter of the test list
     * @return the test list
     */
    public ArrayList<TestGrouper> getTestList() {
        return testList;
    }
}
