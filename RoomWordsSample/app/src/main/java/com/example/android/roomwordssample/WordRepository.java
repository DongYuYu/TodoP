package com.example.android.roomwordssample;

/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

/**
 * Abstracted Repository as promoted by the Architecture Guide.
 * https://developer.android.com/topic/libraries/architecture/guide.html
 */

public class WordRepository implements DataSource{
    private List<Word> allWords;
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();


        new ReadAsyncTask().execute();

    }
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
    List<Word> getStaticAllWords() {
        return allWords;
    }

    private class ReadAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(final Void... params) {
            allWords = mWordDao.getStaticAlphabetizedWords();
            Log.i("retrieve", "done");




            return null;
        }
    }
    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Word word, OnInsertCallBack callback) {
        new insertAsyncTask(mWordDao, callback).execute(word);
    }

    class insertAsyncTask extends AsyncTask<Word, Void, List<Word>> {
        private WordDao mAsyncTaskDao;
        private OnInsertCallBack callback;
        insertAsyncTask(WordDao dao, OnInsertCallBack callback) {
            mAsyncTaskDao = dao;
            this.callback = callback;
        }

        @Override
        protected List<Word> doInBackground(final Word... params) {

            mAsyncTaskDao.insert(params[0]);
            return mAsyncTaskDao.getStaticAlphabetizedWords();
        }

        @Override
        protected void onPostExecute(List<Word> params) {
            callback.onWordInserted(params);
            return;
        }


    }
}
