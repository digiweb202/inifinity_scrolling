package com.mart.infinity_scrolling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {

    private EditText editTextSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editTextSearch = findViewById(R.id.editTextText);

        // Set a key listener to detect when the user presses Enter on the keyboard
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN)) {
                    // Handle the Enter key press
                    performSearch();
                    return true;
                }
                return false;
            }
        });
    }

    private void performSearch() {
        String searchQuery = editTextSearch.getText().toString().trim();

        // Check if the search query is not empty
        if (!searchQuery.isEmpty()) {
            // Show a toast with the entered text
            Toast.makeText(SearchActivity.this, "Search query: " + searchQuery, Toast.LENGTH_SHORT).show();


            // Pass the search query to SearchResultActivity
            // You can use Intent to pass data between activities
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
            intent.putExtra("searchQuery", searchQuery);
            startActivity(intent);
            // TODO: Add logic for searching or navigating to search results
        } else {
            // If the search query is empty, you can show a different message or handle it as needed
            Toast.makeText(SearchActivity.this, "Please enter a search query", Toast.LENGTH_SHORT).show();
        }
    }
}