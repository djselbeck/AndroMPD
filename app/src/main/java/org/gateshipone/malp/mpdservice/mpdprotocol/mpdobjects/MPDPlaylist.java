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

package org.gateshipone.malp.mpdservice.mpdprotocol.mpdobjects;


public class MPDPlaylist extends MPDFileEntry implements MPDGenericItem {



    public MPDPlaylist(String path) {
        super(path);
    }



    @Override
    public String getSectionTitle() {
        return mPath;
    }

    public int compareTo(MPDPlaylist another) {
        if ( another == null ) {
            return -1;
        }

        String title = mPath;
        String[] pathSplit = title.split("/");
        if ( pathSplit.length > 0 ) {
            title = pathSplit[pathSplit.length - 1];
        }


        String titleAnother = another.mPath;
        String[] pathSplitAnother = titleAnother.split("/");
        if ( pathSplitAnother.length > 0 ) {
            titleAnother = pathSplitAnother[pathSplitAnother.length - 1];
        }
        return title.toLowerCase().compareTo(titleAnother.toLowerCase());
    }
}
