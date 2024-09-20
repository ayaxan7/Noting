package eu.tutorials.noting

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddNotes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_notes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var backBtn = findViewById<ImageView>(R.id.back_btn)
        var saveBtn = findViewById<ImageView>(R.id.submit_btn)
        backBtn.setOnClickListener {
            onBackPressedDispatcher
            finish()
        }
        saveBtn.setOnClickListener {
            val toast=Toast.makeText(this, "Saved", Toast.LENGTH_SHORT)
            toast.show()
            // Use a Handler to hide the Toast after 500 milliseconds
            Handler(Looper.getMainLooper()).postDelayed({
                toast.cancel()
            }, 500) // 500 milliseconds
            finish()
        }

    }
}