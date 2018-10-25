package bangali.baba.mvvmrecyclerview;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public  static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .addCallback(callback)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDatabase(instance).execute();
        }
    };

    public static class PopulateDatabase extends AsyncTask<Void, Void, Void> {

        private NoteDao noteDao;

        public PopulateDatabase(NoteDatabase noteDatabase) {
            this.noteDao = noteDatabase.noteDao();
        }

        @Override
        protected Void doInBackground(Void... notes) {
            noteDao.insert(new Note("india","is a greate counry", 10));
            noteDao.insert(new Note("africa","is a greate counry", 11));
            noteDao.insert(new Note("ameria","is a greate counry", 12));
            noteDao.insert(new Note("china","is a greate counry", 13));
            return null;
        }
    }
}
