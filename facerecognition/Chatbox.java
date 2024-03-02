package com.ranjesh.facerecognition;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class Chatbox extends AppCompatActivity {

    private List<Message> messages = new ArrayList<>();
    private EditText editTextMessage;
    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatbox);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("messages");

        editTextMessage = findViewById(R.id.editTextMessage);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

        Button buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String userMessage = editTextMessage.getText().toString().trim();
        if (!userMessage.isEmpty()) {
            // Add user message to the list
            Message userMessageObj = new Message("You: " + userMessage, false);
            messages.add(userMessageObj);

            // Save user message to Firebase
            String userMessageKey = databaseReference.push().getKey();
            databaseReference.child(userMessageKey).setValue(userMessageObj);

            // Update the RecyclerView
            adapter.notifyDataSetChanged();
            recyclerView.smoothScrollToPosition(messages.size() - 1);

            // Simulate typing before bot responds
            simulateTyping(userMessageKey);

            // Process user message and generate bot reply
            String botReply = generateBotReply(userMessage);

            // Delay the bot's response for a short period (adjust as needed)
            new Handler().postDelayed(() -> {
                // Add bot reply to the list
                Message botReplyObj = new Message("Bot: " + botReply, true);
                messages.add(botReplyObj);
                // Save bot reply to Firebase
                databaseReference.child(userMessageKey).child("botReply").setValue(botReplyObj);
                // Update the RecyclerView
                adapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messages.size() - 1);
            }, 1500);
            // Clear the input field
            editTextMessage.getText().clear();
        }
    }
    private void simulateTyping(String userMessageKey) {
        // Add a typing indicator to the list
        Message typingMessage = new Message("Bot is typing...", true);
        messages.add(typingMessage);
        // Update the RecyclerView
        adapter.notifyDataSetChanged();
        recyclerView.smoothScrollToPosition(messages.size() - 1);

        // Save typing indicator to Firebase
        databaseReference.child(userMessageKey).child("botReply").setValue(typingMessage);
        new Handler().postDelayed(() -> {
            // Remove the typing indicator from both the local list and Firebase
            int typingMessageIndex = messages.indexOf(typingMessage);
            if (typingMessageIndex != -1) {
                messages.remove(typingMessageIndex);
                adapter.notifyItemRemoved(typingMessageIndex);
                databaseReference.child(userMessageKey).child("botReply").removeValue();
            }
        }, 1500);
    }

    // Method to generate bot replies based on user input
    private String generateBotReply(String userMessage) {
        // Simple example: Bot responds differently based on user input
        if (userMessage.equalsIgnoreCase("hi") || userMessage.equalsIgnoreCase("hello")) {
            return "Hello! How can I help you?";
        } else if (userMessage.equalsIgnoreCase("missing person") || userMessage.equalsIgnoreCase("lost")) {
            return "I'm sorry to hear that. Please provide more details about the missing person, such as their name, age, and last known location.";
        } else if (userMessage.equalsIgnoreCase("help") || userMessage.equalsIgnoreCase("emergency")) {
            return "If this is an emergency, please contact your local authorities immediately.";
        } else if (userMessage.equalsIgnoreCase("bye") || userMessage.equalsIgnoreCase("goodbye")) {
            return "Goodbye! If you ever need assistance, don't hesitate to return.";
        }else if (userMessage.equalsIgnoreCase("thank you") || userMessage.equalsIgnoreCase("thanks")) {
            return "You're welcome! If you have more questions, feel free to ask.";
        }else if (userMessage.equalsIgnoreCase("who are you") || userMessage.equalsIgnoreCase("what is your purpose")) {
            return "I'm a virtual assistant here to help with information and support.";
        }else if (userMessage.equalsIgnoreCase("weather") || userMessage.equalsIgnoreCase("temperature")) {
            return "I'm sorry, I don't have real-time weather information. You can check a weather website or app for the current conditions.";
        }else if (userMessage.equalsIgnoreCase("recommend") || userMessage.equalsIgnoreCase("suggestions")) {
            return "Sure, I can help with recommendations! What type of recommendations are you looking for?";
        }else if (userMessage.equalsIgnoreCase("your age") || userMessage.equalsIgnoreCase("how old are you")) {
            return "I don't have an age. I'm here to assist you!";
        }else if (userMessage.equalsIgnoreCase("joke") || userMessage.equalsIgnoreCase("funny")) {
            return "Why did the computer catch a cold? Because it left its Windows open!";
        }else if (userMessage.equalsIgnoreCase("learn") || userMessage.equalsIgnoreCase("study")) {
            return "Learning is a great way to grow! What subject or skill are you interested in?";
        }else if (userMessage.equalsIgnoreCase("movie") || userMessage.equalsIgnoreCase("recommend a movie")) {
            return "Sure, I recommend watching 'The Shawshank Redemption.' It's a classic!";
        }
        else if (userMessage.equalsIgnoreCase("easy app hai") || userMessage.equalsIgnoreCase("faltu app h") || userMessage.equalsIgnoreCase("bekar bana h")
                || userMessage.equalsIgnoreCase("chutiya") || userMessage.equalsIgnoreCase("pagal")) {
            return "chal chal apne baap ko mat sikha chutiye!";
        }
        else {
            return "I'm here to help. If you have a specific question or concern, feel free to ask.";
        }
    }
}
