/**
 * @file TestItemHolder.java
 * @brief Model of Test Item Holder (child)
 *
 * @version 1.0
 * @date 06/05/16
 * @author François LEPAROUX
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
package fr.prove.thingy.adapter.test;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import fr.prove.thingy.R;
import fr.prove.thingy.adapter.holder.ItemHolder;
import fr.prove.thingy.model.TestItem;

public class TestItemHolder extends ItemHolder {

    /**
     * The test item
     */
    private TestItem testItem;

    /**
     * Item constructor
     * @param testItem : the test item
     */
    public TestItemHolder(TestItem testItem) {
        super(testItem.getName(), testItem.getDescription());
        this.testItem = testItem;
    }

    /**
     * Item constructor with adapter
     * @param testItem : the test items
     * @param adapter : the adapter
     */
    public TestItemHolder(TestItem testItem, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        super(testItem.getName(), testItem.getDescription(), adapter);
        this.testItem = testItem;
    }

    /**
     * bind of the test item view (depends on the test item statues)
     * @param vh : the view holder
     */
    @Override
    public void bind(ViewHolder vh) {
        super.bind(vh);

        switch (testItem.getStatus()) {

            case TestItem.TEST_STATUS_PENDING:
                vh.getImgIco().setImageResource(R.drawable.ic_pending);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_pending);
                break;

            case TestItem.TEST_STATUS_RUNNING:
                vh.getImgIco().setImageResource(R.drawable.ic_refresh);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_pending);
                break;

            case TestItem.TEST_STATUS_FAILED:
                vh.getImgIco().setImageResource(R.drawable.ic_delete);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_error);
                break;

            case TestItem.TEST_STATUS_DONE:
                vh.getImgIco().setImageResource(R.drawable.ic_valid);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_valid);
                break;

            case TestItem.TEST_STATUS_SKIP:
                vh.getImgIco().setImageResource(R.drawable.ic_skip);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_skip);
                break;

        }
    }
}
