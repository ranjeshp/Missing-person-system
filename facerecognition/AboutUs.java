package com.ranjesh.facerecognition;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);

        // Get references to TextViews
        TextView textMission = findViewById(R.id.textMission);
        TextView textMissionContent = findViewById(R.id.textMissionContent);
        TextView textBackground = findViewById(R.id.textBackground);
        TextView textBackgroundContent = findViewById(R.id.textBackgroundContent);
        TextView textTeam = findViewById(R.id.textTeam);
        TextView textTeamContent = findViewById(R.id.textTeamContent);
        TextView textContactUs = findViewById(R.id.textContactUs);
        TextView textContactUsContent = findViewById(R.id.textContactUsContent);
        TextView textPrivacySecurity = findViewById(R.id.textPrivacySecurity);
        TextView textPrivacySecurityContent = findViewById(R.id.textPrivacySecurityContent);
        TextView textStayConnected = findViewById(R.id.textStayConnected);
        TextView textStayConnectedContent = findViewById(R.id.textStayConnectedContent);
        TextView textLegalInformation = findViewById(R.id.textLegalInformation);
        TextView textLegalInformationContent = findViewById(R.id.textLegalInformationContent);
        TextView textAppVersion = findViewById(R.id.textAppVersion);
        TextView textAppVersionContent = findViewById(R.id.textAppVersionContent);
        TextView textCredits = findViewById(R.id.textCredits);
        TextView textCreditsContent = findViewById(R.id.textCreditsContent);

        // Set content for each TextView
        textMissionContent.setText("Our mission is to leverage technology to assist in locating missing persons and reuniting them with their families and loved ones.");
        textBackgroundContent.setText("The Missing Person App was developed with the goal of providing a centralized platform for reporting and tracking missing individuals. We believe in the power of community and technology to make a positive impact in times of distress.");
        textTeamContent.setText("Meet the passionate individuals behind the app who are dedicated to making a difference.");
        textContactUsContent.setText("Have questions, feedback, or need support? Reach out to us at [contact@missingpersonapp.com](mailto:contact :- ranjeshpatel13503@gmail.com).");
        textPrivacySecurityContent.setText("We take user privacy seriously. Learn more about how we handle your data in our [Privacy Policy](link-to-privacy-policy).");
        textStayConnectedContent.setText("Follow us on [Instagram - RanjeshPatel] for updates and news about the app.");
        textLegalInformationContent.setText("Read our kya bol the public for information about app usage and responsibilities.");
        textAppVersionContent.setText("Current App Version: 1.0.0");
        textCreditsContent.setText("We would like to thank Ranjesh and Zafar for their contribution to our app.");

        // Make links clickable
        textContactUsContent.setMovementMethod(LinkMovementMethod.getInstance());
        textPrivacySecurityContent.setMovementMethod(LinkMovementMethod.getInstance());
        textStayConnectedContent.setMovementMethod(LinkMovementMethod.getInstance());
        textLegalInformationContent.setMovementMethod(LinkMovementMethod.getInstance());
        textCreditsContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
}