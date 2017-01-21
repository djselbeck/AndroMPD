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

package org.gateshipone.malp.application.loaders;


import android.content.Context;
import android.support.v4.content.Loader;

import java.util.List;

import org.gateshipone.malp.mpdservice.handlers.serverhandler.MPDQueryHandler;
import org.gateshipone.malp.mpdservice.handlers.responsehandler.MPDResponseFileList;
import org.gateshipone.malp.mpdservice.mpdprotocol.mpdobjects.MPDFileEntry;

/**
 * Loader class for albumtracks and artist album tracks
 */
public class AlbumTracksLoader extends Loader<List<MPDFileEntry>> {
    /**
     * Response handler used for the asynchronous callback of the networking thread
     */
    private MPDResponseFileList pTrackResponseHandler;

    /**
     * Artist name of this album. Can be left empty
     */
    private String mArtistName;

    /**
     * Name of the album to retrieve
     */
    private String mAlbumName;

    private String mAlbumMBID;

    /**
     * Creates the loader that retrieves the information from the MPD server
     * @param context Context used
     * @param albumName Name of the album to retrieve
     * @param artistName Name of the artist of the album to retrieve (can be left empty)
     */
    public AlbumTracksLoader(Context context, String albumName, String artistName, String albumMBID) {
        super(context);

        // Create a new Handler for asynchronous callback
        pTrackResponseHandler = new TrackResponseHandler();

        // Set the album properties
        mArtistName = artistName;
        mAlbumName = albumName;
        mAlbumMBID = albumMBID;
    }


    /**
     * Private class for the response handler.
     */
    private class TrackResponseHandler extends MPDResponseFileList {
        @Override
        public void handleTracks(List<MPDFileEntry> trackList, int start, int end) {
            deliverResult(trackList);
        }
    }


    /**
     * Starts the loading process
     */
    @Override
    public void onStartLoading() {
        forceLoad();
    }

    /**
     * When the loader is stopped
     */
    @Override
    public void onStopLoading() {

    }

    /**
     * Start the actual laoding process. Check if an artistname was provided.
     * If fetch the artistalbumtracks otherwise fetch all tracks for a specific album.
     */
    @Override
    public void onForceLoad() {
        if ( (null == mArtistName) || mArtistName.equals("") ) {
            MPDQueryHandler.getAlbumTracks(pTrackResponseHandler,mAlbumName,mAlbumMBID);
        } else {
            MPDQueryHandler.getArtistAlbumTracks(pTrackResponseHandler,mAlbumName,mArtistName,mAlbumMBID);
        }
    }
}
