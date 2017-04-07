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

package org.gateshipone.malp.mpdservice.handlers;


import android.os.Handler;
import android.os.Message;

public abstract class MPDConnectionStateChangeHandler extends Handler {
    public enum CONNECTION_STATE_CHANGE {
        CONNECTED,
        DISCONNECTED
    }


    /**
     * Handles the change of the connection of the MPDConnection. Can be used
     * to get notified on connect & disconnect.
     * @param msg Message object
     */
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        CONNECTION_STATE_CHANGE stateChange = (CONNECTION_STATE_CHANGE)msg.obj;

        switch ( stateChange ) {
            case CONNECTED:
                onConnected();
                break;
            case DISCONNECTED:
                onDisconnected();
                break;
        }

    }

    abstract public void onConnected();

    abstract public void onDisconnected();
}
