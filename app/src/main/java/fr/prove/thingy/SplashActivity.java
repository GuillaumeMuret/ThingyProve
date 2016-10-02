/**
 * @file SplashActivity.java
 * @brief Splash activity used to check connectivity, init network and show credits
 * @version 1.0
 * @date 23/03/16
 * @author François LEPAROUX
 * @collab Guillaume MURET
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
package fr.prove.thingy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import fr.prove.thingy.constants.Fonts;
import fr.prove.thingy.constants.Number;
import fr.prove.thingy.constants.Timeouts;
import fr.prove.thingy.model.BinderMan;
import fr.prove.thingy.bus.BusRequest;
import fr.prove.thingy.bus.BusResponse;
import fr.prove.thingy.bus.BusType;
import fr.prove.thingy.model.DataStore;
import fr.prove.thingy.utils.NetworkUtils;

public class SplashActivity extends AppCompatActivity {

    /**
     * Views
     */
    private TextView tvName, tvCredits;

    /**
     * Activity context
     */
    private Context context;

    /**
     * Event Bus
     */
    private EventBus bus = EventBus.getDefault();

    /**
     * The font
     */
    private Typeface fontThin;

    /**
     * Number of attempt to connect
     */
    private int tryToConnect;

    /**
     * Flag used to check if we have to unregister from BusEvent before destroying an activity
     */
    private boolean orientationHasChanged;

    /**
     * Process called when the activity is created
     * @param savedInstanceState : the saved instance
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if the device is a tablet or not (to force loayout orientation later)
        DataStore.getInstance().setDeviceTablet(getResources().getBoolean(R.bool.isTablet));

        // TODO, fix bug ... or maybe propose something like two APK, one for tablets, one for phones
        // Set device orientation (depends if its a tablet or not)
        // ScreenUtils.setDeviceOrientation(this);

        // Set orientation flag
        if (savedInstanceState == null) {
            orientationHasChanged = true;
        }

        // Init number of attempt
        tryToConnect = 0;

        // Set Layout
        setContentView(R.layout.activity_splash);

        // Get context
        context = SplashActivity.this;

        // Get Views
        tvName = (TextView) findViewById(R.id.tvSplashName);
        tvCredits = (TextView) findViewById(R.id.tvSplashCredits);

        // Get fonts
        fontThin = Typeface.createFromAsset(getAssets(), Fonts.ROBOTO_LIGHT);

        // Init Views
        tvName.setTypeface(fontThin);
        tvCredits.setTypeface(fontThin);

        if (savedInstanceState == null) {

            // Register to event
            bus.register(this);

            // Start main (model) service
            startService(new Intent(this, BinderMan.class));

            // Set IP Host Address (should be the address of the Raspberry Pi in the future)
            DataStore.getInstance().setHostIPAddress(NetworkUtils.getHostIPAddress(context));

            // attempt to connect
            tryConnection();
        }
    }

    /**
     * Process called to set up the communication between the rasp and the app
     */
    private void tryConnection() {
        tryToConnect++;
        bus.post(new BusRequest(BusType.BUS_CLOSE_SOCKET));
        // Wait the creation of the binder and the socket
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create the socket
                bus.post(new BusRequest(BusType.SET_UP_CONNEXION));
            }
        }, Timeouts.TW_CREATE_COMMUNICATION);
    }


    /**
     * Dialog -> Ask to reconnect or quit the app
     */
    private void askReconnectOrQuit() {
        new MaterialDialog.Builder(context)
                .title(R.string.dialog_splash_error_title)
                .content(R.string.dialog_splash_error_content)
                .cancelable(false)
                .negativeText(R.string.dialog_quitter)
                .positiveText(R.string.dialog_reconnecter)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        tryToConnect = 0;
                        bus.post(new BusRequest(BusType.NEW_COMMUNICATION));
                        tryConnection();

                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finish();
                    }
                })
                .show();
    }

    /**
     * Process called when a bus response has been send
     * @param response : the response of the bus event
     */
    @Subscribe
    public void onEvent(BusResponse response) {

        if (BuildConfig.DEBUG) Log.d("TACT", " <-- " + response.getRequestType());

        switch (response.getRequestType()) {

            // The connexion is established and the rasp is ready
            case READY_OK:
                bus.post(new BusRequest(BusType.START_TIMER_TIMEOUT_SPLASH));
                break;

            // Network error
            case ALARM_UI:
                if (tryToConnect < Number.NB_MAX_ATTEMPT) {
                    if (BuildConfig.DEBUG) Log.d("tryToConnect = " + Integer.valueOf(tryToConnect).toString(), "debug");
                    tryConnection();
                } else {
                    if (BuildConfig.DEBUG) Log.d("DBG", "Erreur connexion");
                    askReconnectOrQuit();
                }
                break;

            // Display the login activity
            case DISPLAY_LOGIN_ACTIVITY:

                // Unregister to events
                if (bus.isRegistered(this)) bus.unregister(this);

                // Start Login activity
                Intent i = new Intent(context, LoginActivity.class);
                startActivityForResult(i,Number.REQUEST_CODE_ACTIVITY_LOGIN);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }
    }

    /**
     * Process called when an activity launched is finished
     * @param requestCode the request code of the finished activity
     * @param resultCode the result code of the finished activity
     * @param data the data code of the finished activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bus.isRegistered(this)) bus.register(this);
        try {
            switch(Integer.parseInt(data.getData().toString())){

                // The LoginActivity has finish(). Start HomeActivity
                case Number.FINISH_LOGIN_AND_START_HOME:
                    // Unregister to events
                    Log.d("FINISH LOGIN","FINISH_LOGIN");
                    if (bus.isRegistered(this)) bus.unregister(this);
                    Intent home = new Intent(context, HomeActivity.class);
                    startActivityForResult(home,Number.REQUEST_CODE_ACTIVITY_HOME);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                // The HomeActivity has finish(). Start HomeActivity LoginActivity
                case Number.FINISH_HOME_AND_START_LOGIN:
                    // Unregister to events
                    Log.d("FINISH_HOME_ETC","FINISH_HOME_ETC");
                    if (bus.isRegistered(this)) bus.unregister(this);
                    Intent login = new Intent(context, LoginActivity.class);
                    startActivityForResult(login,Number.REQUEST_CODE_ACTIVITY_LOGIN);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    break;

                // Exit the application
                case Number.FINISH_ACTIVITY_EXIT:
                    bus.post(new BusRequest(BusType.BUS_CLOSE_SOCKET));
                    finish();
                    break;

                // Network problem from a launched activity
                case Number.FINISH_ACTIVITY_ALARM_UI:
                    bus.post(new BusRequest(BusType.BUS_CLOSE_SOCKET));

                    // process called after reset the connection
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            bus.post(new BusRequest(BusType.NEW_COMMUNICATION));
                            tryToConnect = 0;
                            tryConnection();
                        }
                    }, Timeouts.TW_CLOSE_SOCKET);
                    break;
                // default -> ALARM_UI
                default:
                    bus.post(new BusResponse(BusType.ALARM_UI));
                    break;

            }
        }catch(NullPointerException npe){
            // if no there is no data in the param data
            npe.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
