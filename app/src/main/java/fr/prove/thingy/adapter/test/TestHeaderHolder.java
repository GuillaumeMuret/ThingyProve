/**
 * @file TestItemHolder.java
 * @brief Model of Test Header Holder (parent)
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
package fr.prove.thingy.adapter.test;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import fr.prove.thingy.BuildConfig;
import fr.prove.thingy.R;
import fr.prove.thingy.adapter.holder.HeaderHolder;
import fr.prove.thingy.constants.Timeouts;
import fr.prove.thingy.model.TestGrouper;
import fr.prove.thingy.model.TestItem;

public class TestHeaderHolder extends HeaderHolder {

    /**
     * Group of a test category
     */
    private TestGrouper testGrouper;

    /**
     * Item of the test category (test items)
     */
    private List<TestItemHolder> childItems;

    /**
     * Animation of the screen
     */
    private Animation fadeInAnim, fadeOutAnim;

    /**
     * Header constructor
     * @param testGrouper : the test group
     */
    public TestHeaderHolder(TestGrouper testGrouper) {
        super(testGrouper.getCategoryName());
        this.testGrouper = testGrouper;
        childItems = new ArrayList<>();

        // Create child items from model items
        for (TestItem testItem : testGrouper.getTestItems()) {
            childItems.add(new TestItemHolder(testItem));
        }

        // Remove divider of last item
        if (childItems.size() > 0)
            childItems.get(childItems.size() - 1).removeDivider();
    }

    /**
     * Item constructor, with adapter
     * @param testGrouper : the test grouper
     * @param adapter : the adapter
     */
    public TestHeaderHolder(TestGrouper testGrouper, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter,Context context) {
        super(testGrouper.getCategoryName(),adapter);
        this.testGrouper = testGrouper;
        childItems = new ArrayList<>();

        // Create child items from model items
        for (TestItem testItem : testGrouper.getTestItems()) {
            childItems.add(new TestItemHolder(testItem, adapter));
        }

        // Remove divider of last item
        if (childItems.size() > 0)
            childItems.get(childItems.size() - 1).removeDivider();

        // Init animation
        fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.view_fade_in);
        fadeOutAnim = AnimationUtils.loadAnimation(context, R.anim.view_fade_out);
    }

    /**
     * Bind of the test grouper view
     * @param vh : the view holder
     */
    @Override
    public void bind(final ViewHolder vh) {
        super.bind(vh);

        // Change the view of the test group category (depends of the statues of the test)
        switch (testGrouper.getStatus()) {

            case TestGrouper.TEST_STATUS_EDIT_MAKE:
                vh.getImgIco().setVisibility(View.VISIBLE);
                vh.getImgIco().startAnimation(fadeInAnim);
                vh.getVwCircle().setVisibility(View.VISIBLE);
                vh.getVwCircle().startAnimation(fadeInAnim);
                vh.getImgIco().setImageResource(R.drawable.ic_play);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_valid);
                break;

            case TestGrouper.TEST_STATUS_EDIT_SKIP:
                vh.getImgIco().setImageResource(R.drawable.ic_skip);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_skip);
                vh.getImgIco().setVisibility(View.VISIBLE);
                vh.getImgIco().startAnimation(fadeInAnim);
                vh.getVwCircle().setVisibility(View.VISIBLE);
                vh.getVwCircle().startAnimation(fadeInAnim);
                break;

            case TestGrouper.TEST_STATUS_LOCKED:
                vh.getImgIco().setVisibility(View.VISIBLE);
                vh.getVwCircle().setVisibility(View.VISIBLE);
                vh.getImgIco().setImageResource(R.drawable.ic_locked);
                vh.getVwCircle().setBackgroundResource(R.drawable.circle_skip);
                break;

            case TestGrouper.TEST_STATUS_OTHER:
                vh.getImgIco().setVisibility(View.INVISIBLE);
                vh.getVwCircle().setVisibility(View.INVISIBLE);
                break;

        }
        if(testGrouper.getTestItems().get(0).getStatus()==TestItem.TEST_STATUS_SKIP) {
            vh.getmArrowExpandImageView().setImageResource(R.drawable.ic_pause);
        }else{
            vh.getmArrowExpandImageView().setImageResource(R.drawable.ic_arrow);
        }
        initListener(vh);
    }

    /**
     * Called to init the listeners
     */
    private void initListener(final ViewHolder vh){
        // On click listener -> change to skip / play test
        vh.getVwCircle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BuildConfig.DEBUG) Log.d("tapOn"+testGrouper.getIDscenario(),"debug");
                // init the listeners if the test is required
                if(!testGrouper.isRequired()) {
                    if (testGrouper.getStatus() == TestGrouper.TEST_STATUS_EDIT_SKIP) {
                        testGrouper.makeAsEdit();
                    } else if (testGrouper.getStatus() == TestGrouper.TEST_STATUS_EDIT_MAKE) {
                        testGrouper.makeAsSkip();
                    }
                }else{
                    if(testGrouper.getStatus()==TestGrouper.TEST_STATUS_EDIT_MAKE) {
                        testGrouper.makeAsLocked();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                testGrouper.makeAsEdit();
                                bind(vh);
                            }
                        }, Timeouts.TW_LOCKED_ICON);
                    }
                }
                bind(vh);
            }
        });
    }

    /**
     * Getter of the child item (test items)
     * @return the child item (test items)
     */
    @Override
    public List getChildItemList() {
        return childItems;
    }
}
