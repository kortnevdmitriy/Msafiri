/*
 *
 *  * Copyright (C) 2017 Safaricom, Ltd.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package ai.kortnevdmitriy.msafiri.mpesa.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created  on 7/4/2017.
 */

public class StoreKey {
    private static final String SHARED_PREFER_FILE_NAME = "keys";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int PRIVATE_MODE = 0;

    public StoreKey(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(SHARED_PREFER_FILE_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createKey(String key_name) {
        editor.putString("key_name", key_name);
        editor.commit();
    }

    public String getTableName() {
        String table_name = pref.getString("key_name", null);
        return table_name;
    }
}
