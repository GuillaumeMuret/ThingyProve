/**
 * @file StatsActivity.java
 * @brief Activity used to show stats about tested products (valid and refused)
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
package fr.prove.thingy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import fr.prove.thingy.constants.Fonts;
import fr.prove.thingy.constants.Number;
import fr.prove.thingy.constants.Stats;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.model.faker.StatsFaker;

public class StatsActivity extends AppCompatActivity {

    /**
     * The title of the screen
     */
    private TextView tvAppName;

    /**
     * The subtitle of the screen
     */
    private TextView tvAppDesc;

    /**
     * the pie chart
     */
    private PieChart pieChart;

    /**
     * The activity context
     */
    private Context context;

    /**
     * The resources of the app
     */
    private Resources resources;

    /**
     * the material dialog when a network error occur
     */
    private MaterialDialog netErrorDialog;

    /**
     * The font
     */
    private Typeface fontThin;

    /**
     * The bus event (initialisation)
     */
    private EventBus bus = EventBus.getDefault();

    /**
     * Flag used to check if we have to unregister from BusEvent before destroying an activity
     */
    private boolean orientationHasChanged;

    /**
     * Called when the activity is starting
     * @param savedInstanceState : the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set device orientation (depends if its a tablet or not)
        //ScreenUtils.setDeviceOrientation(this);

        // Set orientation flag
        if (savedInstanceState == null) {
            orientationHasChanged = true;
        }

        // Set Layout
        setContentView(R.layout.activity_stats);

        // Get context
        context = StatsActivity.this;

        // Init resources
        resources = getResources();

        // Init toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar toolbar = (ActionBar) getSupportActionBar();
        LinearLayout actionBarLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.layout_toolbar_two, null);
        toolbar.setDisplayShowHomeEnabled(false);
        toolbar.setDisplayShowTitleEnabled(false);
        toolbar.setDisplayShowCustomEnabled(true);
        toolbar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.LEFT);
        toolbar.setCustomView(actionBarLayout, params);

        // Get Views
        tvAppName = (TextView) actionBarLayout.findViewById(R.id.tvToolbarTitle);
        tvAppDesc = (TextView) actionBarLayout.findViewById(R.id.tvToolbarDesc);
        pieChart = (PieChart) findViewById(R.id.statsPieChart);

        if (savedInstanceState == null) {
            // Register to events
            bus.register(this);
        }

        // Get fonts
        fontThin = Typeface.createFromAsset(getAssets(), Fonts.ROBOTO_LIGHT);

        // Init Views
        tvAppName.setTypeface(fontThin);
        tvAppDesc.setTypeface(fontThin);
        tvAppName.setText(R.string.stats_title);
        tvAppDesc.setText(R.string.stats_subtitle);

        // Init pie chart view
        pieChart.setDescription(resources.getString(R.string.stats_pie_description));

        // enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(Stats.PIE_HOLE_RADIUS);
        pieChart.setTransparentCircleRadius(Stats.PIE_CIRCLE_RADIUS);

        // Add chart data
        ArrayList<Entry> yVals1 = StatsFaker.getFakeStatsValues();

        ArrayList<String> xVals = new ArrayList<>();
        xVals.add(resources.getString(R.string.stats_pie_valid));
        xVals.add(resources.getString(R.string.stats_pie_refused));

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, resources.getString(R.string.stats_pie_title));
        dataSet.setSliceSpace(Stats.PIE_SLICE_SPACE);
        dataSet.setSelectionShift(Stats.PIE_SELECTION_SHIFT);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorCircleValid));
        colors.add(getResources().getColor(R.color.colorTextError));

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new DefaultValueFormatter(0));
        data.setValueTextColor(Color.LTGRAY);

        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();
    }

    /**
     * Called when a network error occur
     */
    private void networkErrorDialog () {
        netErrorDialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_title_error)
                .content(R.string.dialog_error_network)
                .cancelable(false)
                .negativeText(R.string.dialog_cancel)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finishWithResult(Number.FINISH_ACTIVITY_ALARM_UI);
                    }
                })
                .show();
    }

    /**
     * Process called when a bus response arrive
     * @param response : the response of the bus event
     */
    @Subscribe
    public void onEvent(BusResponse response) {

        if (BuildConfig.DEBUG) Log.d("TACT", " <-- " + response.getRequestType());

        switch (response.getRequestType()) {
            case ALARM_UI:
                networkErrorDialog();
                break;
        }
    }

    /**
     * Process called when the user tap on the back button
     */
    @Override
    public void onBackPressed() {
        finishWithResult(Number.FINISH_STAT);
    }


    /**
     * Process called to finish an activity
     * @param number the number to send at the SplashActivity
     */
    private void finishWithResult(int number)
    {
        Intent data = new Intent();
        //set the data to pass back
        data.setData(Uri.parse(Integer.valueOf(number).toString()));
        setResult(RESULT_OK, data);
        //close the activity
        finish();
    }

    /**
     * Process called when the activity is destroyed
     */
    @Override
    protected void onDestroy() {
        /*if (orientationHasChanged) {
            orientationHasChanged = false;
        } else {
            bus.unregister(this);
        }*/
        bus.unregister(this);
        super.onDestroy();
    }
}
