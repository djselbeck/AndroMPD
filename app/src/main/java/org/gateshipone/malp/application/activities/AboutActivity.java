/*
 * Copyright (C) 2016 Team Gateship-One
 * (Hendrik Borghorst & Frederik Luetkes)
 *
 * The AUTHORS.md file contains a detailed contributors list:
 * <https://github.com/gateship-one/malp/blob/master/AUTHORS.md>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gateshipone.malp.application.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import org.gateshipone.malp.R;
import org.gateshipone.malp.application.utils.ThemeUtils;

public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Read theme preference
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String themePref = sharedPref.getString(getString(R.string.pref_theme_key), getString(R.string.pref_theme_default));
        boolean darkTheme = sharedPref.getBoolean(getString(R.string.pref_dark_theme_key),getResources().getBoolean(R.bool.pref_theme_dark_default));
        if (darkTheme) {
            if (themePref.equals(getString(R.string.pref_indigo_key))) {
                setTheme(R.style.AppTheme_indigo);
            } else if (themePref.equals(getString(R.string.pref_orange_key))) {
                setTheme(R.style.AppTheme_orange);
            } else if (themePref.equals(getString(R.string.pref_deeporange_key))) {
                setTheme(R.style.AppTheme_deepOrange);
            } else if (themePref.equals(getString(R.string.pref_blue_key))) {
                setTheme(R.style.AppTheme_blue);
            } else if (themePref.equals(getString(R.string.pref_darkgrey_key))) {
                setTheme(R.style.AppTheme_darkGrey);
            } else if (themePref.equals(getString(R.string.pref_brown_key))) {
                setTheme(R.style.AppTheme_brown);
            } else if (themePref.equals(getString(R.string.pref_lightgreen_key))) {
                setTheme(R.style.AppTheme_lightGreen);
            } else if (themePref.equals(getString(R.string.pref_red_key))) {
                setTheme(R.style.AppTheme_red);
            }
        } else {
            if (themePref.equals(getString(R.string.pref_indigo_key))) {
                setTheme(R.style.AppTheme_indigo_light);
            } else if (themePref.equals(getString(R.string.pref_orange_key))) {
                setTheme(R.style.AppTheme_orange_light);
            } else if (themePref.equals(getString(R.string.pref_deeporange_key))) {
                setTheme(R.style.AppTheme_deepOrange_light);
            } else if (themePref.equals(getString(R.string.pref_blue_key))) {
                setTheme(R.style.AppTheme_blue_light);
            } else if (themePref.equals(getString(R.string.pref_darkgrey_key))) {
                setTheme(R.style.AppTheme_darkGrey_light);
            } else if (themePref.equals(getString(R.string.pref_brown_key))) {
                setTheme(R.style.AppTheme_brown_light);
            } else if (themePref.equals(getString(R.string.pref_lightgreen_key))) {
                setTheme(R.style.AppTheme_lightGreen_light);
            } else if (themePref.equals(getString(R.string.pref_red_key))) {
                setTheme(R.style.AppTheme_red_light);
            }
        }
        if (themePref.equals(getString(R.string.pref_oleddark_key))) {
            setTheme(R.style.AppTheme_oledDark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getWindow().setStatusBarColor(ThemeUtils.getThemeColor(this,R.attr.malp_color_primary_dark));


        String versionName = "";
        // get version from manifest
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView)findViewById(R.id.activity_about_version)).setText(versionName);

        findViewById(R.id.logo_musicbrainz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(getResources().getString(R.string.url_musicbrainz)));
                startActivity(urlIntent);
            }
        });

        findViewById(R.id.logo_lastfm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(getResources().getString(R.string.url_lastfm)));
                startActivity(urlIntent);
            }
        });

        findViewById(R.id.logo_fanarttv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(getResources().getString(R.string.url_fanarttv)));
                startActivity(urlIntent);
            }
        });
    }
}
