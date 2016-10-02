/**
 * @file LoginActivityTest.java
 * @brief Test of Boolean checkCardFormat(String strCardID)
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

import fr.prove.thingy.HomeActivity;

import fr.prove.thingy.R;


@RunWith(AndroidJUnit4.class)
public class HomeActivityTest extends InstrumentationTestCase{

    final int nbDT = 28;
    final String[] jt = new String[nbDT];

    @Before
    public void setUp() throws Exception {
        super.setUp();
        jt[0] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT0);
        jt[1] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT1);
        jt[2] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT2);
        jt[3] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT3);
        jt[4] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT4);
        jt[5] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT5);
        jt[6] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT6);
        jt[7] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT7);
        jt[8] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT8);
        jt[9] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT9);
        jt[10] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT10);
        jt[11] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT11);
        jt[12] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT12);
        jt[13] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT13);
        jt[14] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT14);
        jt[15] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT15);
        jt[16] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT16);
        jt[17] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT17);
        jt[18] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT18);
        jt[19] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT19);
        jt[20] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT20);
        jt[21] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT21);
        jt[22] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT22);
        jt[23] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT23);
        jt[24] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT24);
        jt[25] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT25);
        jt[26] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT26);
        jt[27] = this.getInstrumentation().getTargetContext().getResources().getString(R.string.cardIDDT27);
    }


    /**
     * Test the right Card Format
     * The 15 first data (CardIDDI0 à CardIDDT14) have an invalid Format
     * the 13 last one data (CardIDDT15 à CardIDDT27) have a valid format
     *
     * <p>concerned method : public Boolean checkCardFormat(String cardID) </p>
     *
     */

    /**
     * Test good IDString
     * <p>concerned method : public Boolean checkCardFormat(String cardID) </p>
     *
     */
    @Test
    public void testCheckCardFormatValid()
    {
        for (int valid=15 ; valid<28; valid++) {

            final Boolean resultObtained1 = HomeActivity.checkCardFormat(jt[valid]);

            assertTrue("Format valide pour dt[" + valid + "], "+jt[valid], resultObtained1);
        }

    }

    /**
     * Test wrong IDString
     * <p>concerned method : public Boolean checkCardFormat(String cardID) </p>
     *
     */
    @Test
    public void testCheckCardFormatInvalid()
    {
        // Jeu de test.

        for (int invalid=0 ;invalid<15; invalid++) {

            final Boolean resultObtained = 	HomeActivity.checkCardFormat(jt[invalid]);

            assertFalse("Format invalide pour dt[" + invalid + "], "+jt[invalid], resultObtained);
        }
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

}