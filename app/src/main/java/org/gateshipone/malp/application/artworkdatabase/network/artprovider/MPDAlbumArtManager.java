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
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.gateshipone.malp.application.artworkdatabase.network.artprovider;


import android.util.Log;

import com.android.volley.Response;

import org.gateshipone.malp.application.artworkdatabase.network.responses.AlbumFetchError;
import org.gateshipone.malp.application.artworkdatabase.network.responses.AlbumImageResponse;
import org.gateshipone.malp.mpdservice.mpdprotocol.MPDConnection;
import org.gateshipone.malp.mpdservice.mpdprotocol.mpdobjects.MPDAlbum;
import org.gateshipone.malp.mpdservice.mpdprotocol.mpdobjects.MPDFileEntry;

import java.util.List;

public class MPDAlbumArtManager implements AlbumImageProvider {
    private static final String TAG = MPDAlbumArtManager.class.getSimpleName();

    private static MPDAlbumArtManager mInstance;

    private MPDAlbumArtManager() {
    }

    public static synchronized MPDAlbumArtManager getInstance() {
        if (mInstance == null) {
            mInstance = new MPDAlbumArtManager();
        }
        return mInstance;
    }

    @Override
    public void fetchAlbumImage(final MPDAlbum album,
                                final Response.Listener<AlbumImageResponse> listener,
                                final AlbumFetchError errorListener) {

        Log.v(TAG,"Fetch MPD album art for album: " + album.getName());

        MPDFileEntry albumTrack = readOneAlbumTrack(album);
        if (albumTrack == null) {
            return;
        }

        // read album art
        AlbumImageResponse response = new AlbumImageResponse();
        response.album = album;
        response.image = MPDConnection.getInstance().getAlbumArt(albumTrack.getPath());

        if (response.image != null) {
            listener.onResponse(response);
        }
    }

    private MPDFileEntry readOneAlbumTrack(final MPDAlbum album) {
        List<MPDFileEntry> fileList = MPDConnection.getInstance().getArtistAlbumTracks(album.getName(), album.getArtistName(), album.getMBID());
        if (fileList != null && !fileList.isEmpty()) {
            return fileList.get(0);
        } else {
            return null;
        }
    }
}
