package com.hthinyane.hannahphonebook.Persist;

import net.rehacktive.waspdb.WaspDb;
import net.rehacktive.waspdb.WaspHash;

import java.util.List;

/**
 * Created by gregl on 2016/05/17.
 */
public class WaspDBWrapper {
    private WaspDb db;

    public WaspDBWrapper(WaspDb db) {
        this.db = db;
    }

    public WaspHash getTable(String hash_name) {
        return db.openOrCreateHash(hash_name);
    }

    public boolean deleteDatabase() {
        List<String> hashes = db.getAllHashes();
        for (String s : hashes) {
            db.removeHash(s);
        }
        return true;
    }
}
