/**
 * @file LoginActivityTest.java
 * @brief Test of checkOperatorFormat(String strUsername,String strPassword) : Boolean
 * @version 1.0
 * @date 14/05/2016
 * @author Clarisse Girault (première version : Guillaume Muret)
 * @see fr.prove.thingy.communication.Communication
 * @copyright :
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
 */

package fr.prove.thingy.test;

import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.prove.thingy.LoginActivity;
import fr.prove.thingy.R;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest extends InstrumentationTestCase{

    final int nbDT = 20;
    final String[] jt = new String[nbDT]; // datas

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jt[0] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT0); //We get data
        jt[1] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT1);
        jt[2] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT2);
        jt[3] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT3);
        jt[4] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT4);
        jt[5] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT5);
        jt[6] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT6);
        jt[7] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT7);
        jt[8] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT8);
        jt[9] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT9);
        jt[10] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT10);
        jt[11] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT11);
        jt[12] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT12);
        jt[13] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT13);
        jt[14] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT14);
        jt[15] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT15);
        jt[16] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT16);
        jt[17] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT17);
        jt[18] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT18);
        jt[19] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.operatorIDDT19);
    }


    /**
     * Test of checkOperatorFormat(String strUsername,String strPassword) : Boolean
     * The 17 first data (operatorIDDT0 à operatorIDDT16) have a valid format and
     * the 3 last ones (operatorIDDT17 à operatorIDDT19) have an invalid format.
     *
     * <p>concerned method: public Boolean checkCardFormat(String strUsername,String strPassword) </p>
     *
     */

    /**
     * Test good OperatorID Format
     * <p>concerned method : public Boolean checkCardFormat(String cardID) </p>
     *
     */
    @Test
    public void testCheckOperatorFormatValid() {

        for (int valid=17 ; valid<20; valid++) {

            final Boolean resultObtained1 = LoginActivity.checkOperatorFormat(jt[valid],"password");

            assertTrue("Format valide pour dt[" + valid + "], " , resultObtained1);
        }

    }

    /**
     * Test invalid OperatorID Format
     * <p>concerned method : public Boolean checkCardFormat(String cardID) </p>
     *
     */
    @Test
    public void testCheckOperatorFormatInvalid() {

        for (int invalid=0 ;invalid<17; invalid++) {

            final Boolean resultObtained = 	LoginActivity.checkOperatorFormat(jt[invalid],"password");

            assertFalse("Format invalide pour dt[" + invalid + "], ", resultObtained);
        }
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

}