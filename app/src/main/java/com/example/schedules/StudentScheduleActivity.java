package com.example.schedules;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;

public class StudentScheduleActivity extends AppCompatActivity {

    Spinner classSpinner;
    TableLayout tableLayout;
    String[] classList = {"Select Class", "Grade 1", "Grade 2", "Grade 3", "Grade 4", "Grade 5", "Grade 6", "Grade 7", "Grade 8", "Grade 9"};
    String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"};
    int maxLectures = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_schedule);

        classSpinner = findViewById(R.id.classSpinner);
        tableLayout = findViewById(R.id.tableLayout);

        setupClassSpinner();
    }

    private void setupClassSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        classSpinner.setAdapter(adapter);

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedClass = classList[position];

                if (!selectedClass.equals("Select Class")) {
                    try {
                        String encodedClass = URLEncoder.encode(selectedClass, "UTF-8");
                        fetchScheduleData(encodedClass);
                    } catch (Exception e) {
                        Toast.makeText(StudentScheduleActivity.this, "Encoding error", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    clearTableWithMessage("Please select a class");
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void fetchScheduleData(String className) {
        String url = "http://10.0.2.2/API/get_student_schedule.php?class_name=" + className;
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        if (response.getString("status").equals("ok")) {
                            JSONArray data = response.getJSONArray("data");

                            if (data.length() == 0) {
                                clearTableWithMessage("No schedule found for this class");
                                return;
                            }

                            populateTable(data);
                        } else {
                            clearTableWithMessage("Failed to fetch schedule");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        clearTableWithMessage("Error while parsing data");
                    }
                },
                error -> clearTableWithMessage("Connection to server failed"));

        queue.add(request);
    }

    private void clearTableWithMessage(String message) {
        tableLayout.removeAllViews();
        createTableHeader();
        for (String day : days) {
            tableLayout.addView(createDayRow(day, new HashMap<>(), true));
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void populateTable(JSONArray data) throws JSONException {
        tableLayout.removeAllViews();
        HashMap<String, HashMap<Integer, String>> scheduleMap = new HashMap<>();
        for (String day : days) scheduleMap.put(day, new HashMap<>());

        for (int i = 0; i < data.length(); i++) {
            JSONObject row = data.getJSONObject(i);
            String day = row.getString("day");
            int lecture = row.getInt("lecture_number");
            String subject = row.getString("subject_name");
            scheduleMap.get(day).put(lecture, subject);
        }

        createTableHeader();

        for (String day : days) {
            tableLayout.addView(createDayRow(day, scheduleMap.get(day), false));
        }
    }

    private void createTableHeader() {
        TableRow header = new TableRow(this);
        header.addView(createHeaderCell("Day \\ Lecture"));
        for (int i = 1; i <= maxLectures; i++) {
            header.addView(createHeaderCell("Lecture " + i));
        }
        tableLayout.addView(header);
    }

    private TableRow createDayRow(String day, HashMap<Integer, String> lectures, boolean empty) {
        TableRow row = new TableRow(this);

        TextView dayCell = new TextView(this);
        dayCell.setText(day);
        dayCell.setPadding(12, 12, 12, 12);
        dayCell.setBackgroundColor(Color.LTGRAY);
        dayCell.setTextColor(Color.BLACK);
        dayCell.setTypeface(null, Typeface.BOLD);
        dayCell.setGravity(Gravity.CENTER);
        row.addView(dayCell);

        for (int i = 1; i <= maxLectures; i++) {
            TextView cell = new TextView(this);
            cell.setText(empty ? "—" : lectures.getOrDefault(i, ""));
            cell.setPadding(12, 12, 12, 12);
            cell.setTextColor(Color.BLACK);
            cell.setTextSize(15);
            cell.setTypeface(null, Typeface.BOLD);
            cell.setGravity(Gravity.CENTER);
            cell.setBackgroundResource(android.R.drawable.editbox_background);
            row.addView(cell);
        }

        return row;
    }

    private TextView createHeaderCell(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(12, 12, 12, 12);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setBackgroundColor(Color.DKGRAY);
        tv.setTextColor(Color.WHITE);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
