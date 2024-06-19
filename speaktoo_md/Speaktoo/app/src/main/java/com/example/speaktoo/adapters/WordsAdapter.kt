package com.example.speaktoo.adapters

// WordsAdapter.kt
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.speaktoo.R
import com.example.speaktoo.models.Word
import com.example.speaktoo.pages.Game
import android.content.Context

class WordsAdapter(private val words: List<Word>, private val context: Context) : RecyclerView.Adapter<WordsAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words[position]
        holder.wordTextView.text = word.word
        holder.completedImageView.setImageResource(if (word.completed == 1) R.drawable.baseline_check_box_outline_blank_24 else R.drawable.baseline_check_box_24)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, Game::class.java)
            intent.putExtra("WORD", word.word)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = words.size

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTextView: TextView = itemView.findViewById(R.id.wordTextView)
        val completedImageView: ImageView = itemView.findViewById(R.id.completedImageView)
    }
}
