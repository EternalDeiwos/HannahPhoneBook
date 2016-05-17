package com.hthinyane.hannahphonebook.Persist;

import net.rehacktive.waspdb.WaspDb;
import net.rehacktive.waspdb.WaspFactory;

import java.util.HashMap;

/**
 * Created by gregl on 2016/05/17.
 */
public class WaspDB {
    private static final String DB_NAME = "waspDB_contacts";
    private static final String DB_PASS = "abcd123$";
    private static final HashMap<String, WaspDb> dbs = new HashMap<>();

    public static WaspDb getInstance(String path) {
        if (dbs.containsKey(path)) {
            return dbs.get(path);
        } else {
            WaspDb db = WaspFactory.openOrCreateDatabase(path, DB_NAME, DB_PASS);
            dbs.put(path, db);
            return db;
        }
    }

    private WaspDB() {

    }
}
