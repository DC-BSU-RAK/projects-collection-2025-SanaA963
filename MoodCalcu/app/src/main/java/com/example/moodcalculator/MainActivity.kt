package com.example.moodcalculator

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Animate emoji
        val emojiImage: ImageView = findViewById(R.id.imageView2)
        val bounceAnimator = ObjectAnimator.ofFloat(emojiImage, "translationY", 0f, -30f).apply {
            duration = 800
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
        }
        bounceAnimator.start()

        // Match IDs with your XML
        val moodSpinner: Spinner = findViewById(R.id.Spinner1)
        val reasonSpinner: Spinner = findViewById(R.id.Spinner2)
        val showButton: Button = findViewById(R.id.showButton)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        // Populate spinner data - moods and reasons
        val moods = arrayOf("Happy", "Sad", "Angry")
        val reasons = arrayOf("Work", "Family", "Health", "Friends")

        moodSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, moods)
        reasonSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, reasons)

        // Map of advice based on mood and reason
        val adviceMap = mapOf(
            "Happy" to mapOf(
                "Work" to "Keep up the great work spirit! ",
                "Family" to "Cherish your family moments! ",
                "Health" to "Good health brings happiness! Stay active! ️",
                "Friends" to "Enjoy time with your friends! "
            ),
            "Sad" to mapOf(
                "Work" to "Take breaks and don't overwork yourself. ",
                "Family" to "Talk to your family, they care about you. ",
                "Health" to "Focus on self-care and rest. ",
                "Friends" to "Reach out to a friend for support. "
            ),
            "Angry" to mapOf(
                "Work" to "Take deep breaths and approach calmly. ",
                "Family" to "Give yourself some space before reacting. ",
                "Health" to "Exercise can help release anger. ",
                "Friends" to "Talk it out instead of bottling up feelings. "
            )
        )

        showButton.setOnClickListener {
            val selectedMood = moodSpinner.selectedItem.toString()
            val selectedReason = reasonSpinner.selectedItem.toString()
            val advice = adviceMap[selectedMood]?.get(selectedReason) ?: "No advice available."
            resultTextView.text = advice
        }

        val instructButton: ImageButton = findViewById(R.id.instrucButton)
        instructButton.setOnClickListener {
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView = inflater.inflate(R.layout.popup, null)
            val instructWindow = PopupWindow(popupView, 900, 1400, true)

            // Show popup in center of screen
            instructWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0)

            val closeButton: ImageView = popupView.findViewById(R.id.closeButton)
            closeButton.setOnClickListener {
                instructWindow.dismiss()
            }
        }
    }
}
