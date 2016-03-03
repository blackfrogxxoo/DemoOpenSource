package com.bitsmelody.demoopensource;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.bitsmelody.demoopensource.DaoMaster;
import com.bitsmelody.demoopensource.DaoSession;
import com.bitsmelody.demoopensource.Note;
import com.bitsmelody.demoopensource.NoteDao;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.greenrobot.dao.AbstractDaoSession;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;

    private EditText editText;
    private Button btnAdd;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private NoteDao noteDao;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.list_view);
        editText = (EditText) findViewById(R.id.edit_text);
        btnAdd = (Button) findViewById(R.id.btn_add);

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();

        String textColumn = NoteDao.Properties.Text.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = db.query(noteDao.getTablename(), noteDao.getAllColumns(), null, null, null, null, orderBy);
        String[] from = {textColumn, NoteDao.Properties.Comment.columnName};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, from, to);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                noteDao.deleteByKey(id);
                cursor.requery();
            }
        });

        addUiListeners();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });

    }


    protected void addUiListeners() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });

        final View button = findViewById(R.id.btn_add);
        button.setEnabled(false);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                button.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String comment = "Added on " + sdf.format(new Date());
        Note note = new Note(null, noteText, comment, new Date());
        noteDao.insert(note);

        cursor.requery();
    }
}
