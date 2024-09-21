package eu.tutorials.noting
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import eu.tutorials.noting.models.Notes
import eu.tutorials.noting.R
import eu.tutorials.noting.databinding.ActivityAddNotesBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNotes : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var note: Notes
    private var old_note: Notes? = null
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding
        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        // Check if it's an update or a new note
        old_note = intent.getSerializableExtra("current_note") as? Notes
        if (old_note != null) {
            binding.noteTitle.setText(old_note!!.title)
            binding.noteBody.setText(old_note!!.body)
            isUpdate = true
        }

        // Handle window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back button functionality
        val backBtn = findViewById<ImageView>(R.id.back_btn)
        val saveBtn = findViewById<ImageView>(R.id.submit_btn)

        backBtn.setOnClickListener {
            finish() // Use finish() to go back
        }

        // Save button functionality
        saveBtn.setOnClickListener {
            val title = binding.noteTitle.text.toString()
            val body = binding.noteBody.text.toString()

            if (title.isNotEmpty() || body.isNotEmpty()) {
                val currentUnixTime = System.currentTimeMillis() / 1000L
                val formattedDate = formatTimestamp(currentUnixTime)
                if (isUpdate) {
                    note = Notes(old_note!!.id, title, body, formattedDate)
                } else {
                    note = Notes(null, title, body, formattedDate)
                }
                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)

                // Show saved toast message
                Toast.makeText(this, if (isUpdate) "Updated" else "Saved", Toast.LENGTH_SHORT).show()
                finish() // Close the activity after saving
            } else {
                Toast.makeText(this, "Please enter some data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun formatTimestamp(unixTimestamp: Long): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()) // 24-hour format
        val date = Date(unixTimestamp * 1000)
        return formatter.format(date)
    }

}
