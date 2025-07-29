package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.content.ContentValues;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editName = findViewById(R.id.editTextName);
        EditText editGrade = findViewById(R.id.editTextGrade);
        ListView listView = findViewById(R.id.listView);

        ArrayList<String> studentsList = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                studentsList
        );
        listView.setAdapter(adapter);

        findViewById(R.id.buttonAdd).setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String grade = editGrade.getText().toString().trim();

            if (name.isEmpty() || grade.isEmpty()) {
                Toast.makeText(this, "Please enter both name and grade",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            ContentValues values = new ContentValues();
            values.put(StudentsProvider.NAME, name);
            values.put(StudentsProvider.GRADE, grade);

            try {
                Uri uri = getContentResolver().insert(
                        StudentsProvider.CONTENT_URI,
                        values);

                if (uri != null) {
                    Toast.makeText(this, "Student added!",
                            Toast.LENGTH_SHORT).show();
                    editName.setText("");
                    editGrade.setText("");
                    refreshStudentsList(adapter, studentsList);
                }
            } catch (Exception e) {
                Toast.makeText(this, "Error adding student",
                        Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.buttonDelete).setOnClickListener(v -> {
            try {
                int count = getContentResolver().delete(
                        StudentsProvider.CONTENT_URI, null, null);

                Toast.makeText(this,
                        count + " student(s) deleted",
                        Toast.LENGTH_SHORT).show();

                refreshStudentsList(adapter, studentsList);
            } catch (Exception e) {
                Toast.makeText(this,
                        "Error deleting students",
                        Toast.LENGTH_SHORT).show();
            }
        });

        refreshStudentsList(adapter, studentsList);
    }

    private void refreshStudentsList(ArrayAdapter<String> adapter,
                                     ArrayList<String> studentsList) {
        studentsList.clear();
        try (Cursor c = getContentResolver().query(
                StudentsProvider.CONTENT_URI,
                null, null, null, "_id")) {

            if (c != null && c.moveToFirst()) {
                do {
                    String id = c.getString(c.getColumnIndexOrThrow(StudentsProvider._ID));
                    String name = c.getString(c.getColumnIndexOrThrow(StudentsProvider.NAME));
                    String grade = c.getString(c.getColumnIndexOrThrow(StudentsProvider.GRADE));

                    studentsList.add("ID: " + id + " | Name: " + name + " | Grade: " + grade);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error loading students",
                    Toast.LENGTH_SHORT).show();
        }
        adapter.notifyDataSetChanged();
    }
}