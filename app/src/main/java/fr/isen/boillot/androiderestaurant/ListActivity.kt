package fr.isen.boillot.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val message = intent.getStringExtra("category_key")
        title = message
        val messageTextView: TextView = findViewById(R.id.textView)
        messageTextView.text = message
    }
}