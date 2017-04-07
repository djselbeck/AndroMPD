/*
 *  Copyright (C) 2017 Team Gateship-One
 *  (Hendrik Borghorst & Frederik Luetkes)
 *
 *  The AUTHORS.md file contains a detailed contributors list:
 *  <https://github.com/gateship-one/malp/blob/master/AUTHORS.md>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package org.gateshipone.malp.application.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;

import org.gateshipone.malp.R;
import org.gateshipone.malp.application.callbacks.FABFragmentCallback;
import org.gateshipone.malp.application.callbacks.ProfileManageCallbacks;
import org.gateshipone.malp.mpdservice.profilemanagement.MPDServerProfile;

public class EditProfileFragment extends Fragment {
    public final static String TAG = EditProfileFragment.class.getSimpleName();
    public static final String EXTRA_PROFILE = "profile";


    private String mProfilename;
    private String mHostname;
    private String mPassword;
    private int mPort;

    private String mStreamingURL;
    private boolean mStreamingEnabled;


    private TextInputEditText mProfilenameView;
    private TextInputEditText mHostnameView;
    private TextInputEditText mPasswordView;
    private TextInputEditText mPortView;

    private Switch mStreamingEnabledView;
    private TextInputEditText mStreamingURLView;


    private MPDServerProfile mOldProfile;

    private ProfileManageCallbacks mCallback;

    private FABFragmentCallback mFABCallback = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);


        mProfilenameView = (TextInputEditText) rootView.findViewById(R.id.fragment_profile_profilename);
        mHostnameView = (TextInputEditText) rootView.findViewById(R.id.fragment_profile_hostname);
        mPasswordView = (TextInputEditText) rootView.findViewById(R.id.fragment_profile_password);
        mPortView = (TextInputEditText) rootView.findViewById(R.id.fragment_profile_port);

        mStreamingURLView = (TextInputEditText) rootView.findViewById(R.id.fragment_profile_streaming_url);
        mStreamingEnabledView = (Switch) rootView.findViewById(R.id.fragment_profile_streaming_enabled);

        // Set to maximum tcp port
        InputFilter portFilter = new PortNumberFilter();

        mPortView.setFilters(new InputFilter[]{portFilter});


        /* Check if an artistname/albumame was given in the extras */
        Bundle args = getArguments();
        if (null != args) {
            mOldProfile = args.getParcelable(EXTRA_PROFILE);
            if (mOldProfile != null) {
                mProfilename = mOldProfile.getProfileName();
                mHostname = mOldProfile.getHostname();
                mPassword = mOldProfile.getPassword();
                mPort = mOldProfile.getPort();

                mStreamingURL = mOldProfile.getStreamingURL();
                mStreamingEnabled = mOldProfile.getStreamingEnabled();

                mProfilenameView.setText(mProfilename);
            } else {
                mHostname = "";
                mProfilename = "";
                mPassword = "";
                mPort = 6600;

                mStreamingEnabled = false;
                mStreamingURL = "";

                mProfilenameView.setText(getString(R.string.fragment_profile_default_name));
            }
        }

        mHostnameView.setText(mHostname);
        mPasswordView.setText(mPassword);
        mPortView.setText(String.valueOf(mPort));

        mStreamingEnabledView.setChecked(mStreamingEnabled);
        mStreamingEnabledView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if ( mStreamingURLView.getText().toString().isEmpty()) {
                        // Check if a text was already set otherwise show an example
                        mStreamingURL = "http://" + mHostnameView.getText().toString() + ":8080";
                        mStreamingURLView.setText(mStreamingURL);
                    }
                    mStreamingURLView.setVisibility(View.VISIBLE);
                } else {
                    mStreamingURLView.setVisibility(View.GONE);
                }

            }
        });

        if (!mStreamingEnabled) {
            mStreamingURLView.setVisibility(View.GONE);
        }
        mStreamingURLView.setText(mStreamingURL);


        mProfilenameView.setSelectAllOnFocus(true);


        // Return the ready inflated and configured fragment view.
        return rootView;
    }

    /**
     * Called when the fragment is first attached to its context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (ProfileManageCallbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArtistSelectedListener");
        }
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mFABCallback = (FABFragmentCallback) context;
        } catch (ClassCastException e) {
            mFABCallback = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        boolean profileChanged = false;
        if (!mProfilenameView.getText().toString().equals(mProfilename)) {
            profileChanged = true;
            mProfilename = mProfilenameView.getText().toString();
        }
        if (!mHostnameView.getText().toString().equals(mHostname)) {
            profileChanged = true;
            mHostname = mHostnameView.getText().toString();
        }
        if (!mPasswordView.getText().toString().equals(mPassword)) {
            profileChanged = true;
            mPassword = mPasswordView.getText().toString();
        }
        if (Integer.parseInt(mPortView.getText().toString()) != mPort) {
            profileChanged = true;
            mPort = Integer.parseInt(mPortView.getText().toString());
        }
        if (!mStreamingURLView.getText().toString().equals(mStreamingURL)) {
            profileChanged = true;
            mStreamingURL = mStreamingURLView.getText().toString();
        }
        if (mStreamingEnabledView.isChecked() != mStreamingEnabled) {
            profileChanged = true;
            mStreamingEnabled = mStreamingEnabledView.isChecked();
        }

        if (profileChanged) {
            if (null != mOldProfile) {
                mCallback.removeProfile(mOldProfile);
            } else {
                mOldProfile = new MPDServerProfile(mProfilename, true);
            }
            mOldProfile.setProfileName(mProfilename);
            mOldProfile.setHostname(mHostname);
            mOldProfile.setPassword(mPassword);
            mOldProfile.setPort(mPort);
            mOldProfile.setStreamingURL(mStreamingURL);
            mOldProfile.setStreamingEnabled(mStreamingEnabled);
            mCallback.addProfile(mOldProfile);
        }

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getView();
        if (null != view) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (null != mFABCallback) {
            mFABCallback.setupFAB(false, null);
            mFABCallback.setupToolbar(getString(R.string.fragment_profile_title), false, false, false);
        }
    }

    private class PortNumberFilter implements InputFilter {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            if (end > start) {
                String destTxt = dest.toString();
                String resultingTxt = destTxt.substring(0, dstart) +
                        source.subSequence(start, end) +
                        destTxt.substring(dend);
                try {
                    int port = Integer.parseInt(resultingTxt);
                    if (port > 65535) {
                        return "";
                    }
                    if (port < 1) {
                        return "";
                    }
                } catch (NumberFormatException e) {
                    return "";
                }
            }
            return null;
        }
    }

    ;
}
