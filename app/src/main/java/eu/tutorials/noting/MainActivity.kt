package eu.tutorials.noting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eu.tutorials.noting.adapter.NotesAdapter
import eu.tutorials.noting.database.NoteDB
import eu.tutorials.noting.models.NoteViewModel
import eu.tutorials.noting.models.Notes
import eu.tutorials.noting.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NotesAdapter.NotesClickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NotesAdapter
    private lateinit var selectedNote: Notes

    // Log tag
    private val TAG = "MainActivity"

    private val addNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val note = result.data?.getSerializableExtra("note") as? Notes
            if (note != null) {
                Log.d(TAG, "Adding note: $note")
                viewModel.insertNote(note)
            } else {
                Log.e(TAG, "Failed to retrieve note from intent")
            }
        } else {
            Log.e(TAG, "Failed to add note, result code: ${result.resultCode}")
        }
    }

    private val updateNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val note = result.data?.getSerializableExtra("note") as? Notes
            if (note != null) {
                Log.d(TAG, "Updating note: $note")
                viewModel.updateNote(note)
            } else {
                Log.e(TAG, "Failed to retrieve updated note from intent")
            }
        } else {
            Log.e(TAG, "Failed to update note, result code: ${result.resultCode}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "MainActivity created")

        initUI()
        setupViewModel()

        // Setting insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        adapter = NotesAdapter(this, this)
        binding.recyclerView.adapter = adapter

        // FAB to add new notes
        binding.floatingActionButton.setOnClickListener {
            Log.d(TAG, "Floating action button clicked")
            val intent = Intent(this, AddNotes::class.java)
            addNoteLauncher.launch(intent)
        }

        // Search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    Log.d(TAG, "Searching notes with query: $it")
                    adapter.filterList(it)
                }
                return true
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Observing the list of notes
        viewModel.allNotes.observe(this) { list ->
            Log.d(TAG, "Observing notes list, size: ${list.size}")
            list?.let { adapter.UpdateList(it) }
        }
    }

    override fun onItemClicked(note: Notes) {
        Log.d(TAG, "Note clicked: $note")
        val intent = Intent(this, AddNotes::class.java).apply {
            putExtra("current_note", note)
        }
        updateNoteLauncher.launch(intent)
    }

    override fun onLongItemClicked(note: Notes, cardView: CardView) {
        Log.d(TAG, "Long-clicked note: $note")
        selectedNote = note
        showPopupMenu(cardView)
    }

    private fun showPopupMenu(cardView: CardView) {
        val popup = PopupMenu(this, cardView).apply {
            setOnMenuItemClickListener(this@MainActivity)
            inflate(R.menu.popup_menu)
        }
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_note) {
            Log.d(TAG, "Deleting note: $selectedNote")
            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }
}
