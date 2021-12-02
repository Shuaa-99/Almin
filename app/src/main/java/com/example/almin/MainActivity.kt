package com.example.almin

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    val myMessage = ArrayList<String>()
    private lateinit var mathProblemTv: TextView
    var score = 0
    var highScore = 0
    private lateinit var tvHS: TextView
    private lateinit var scoreTV:TextView
    private lateinit var button_click: Button
    private lateinit var txt_Enter: EditText
    var firstRandomNum = Random.nextInt(10)
    var secondRandomNum = Random.nextInt(20)
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Math Study App")
        builder.setMessage("Welcome to the Math Study App! "+"\n"+"How many equation can you solve?")
        builder.setNegativeButton("START", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
        sharedPreferences = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
        highScore = sharedPreferences.getInt("HighScore", 0)
        tvHS = findViewById(R.id.tvHS)
       scoreTV = findViewById(R.id.scoreTV)
        tvHS.text = "High Score: $highScore"
        txt_Enter = findViewById(R.id.numGuessed)
        button_click = findViewById(R.id.bttn_click)
        mathProblemTv = findViewById(R.id.mathProblemTV)
        mathProblemTv.text = "$firstRandomNum + $secondRandomNum ="

        button_click.setOnClickListener {
            mathProblemFun()
        }
        val myRV = findViewById<RecyclerView>(R.id.rvGuesses)
        myRV.adapter = RecyclerViewAdapter(myMessage)
        myRV.layoutManager = LinearLayoutManager(this)
    }

    fun mathProblemFun() {
        val result = firstRandomNum + secondRandomNum
        if (txt_Enter.text.toString().toInt() == result ) {
            score += 1
            save()
            scoreTV.text = "Score: $score"
            myMessage.add("$firstRandomNum + $secondRandomNum = ${txt_Enter.text.toString()} correct :)")
            txt_Enter.text = null
            firstRandomNum = Random.nextInt(20)
            secondRandomNum = Random.nextInt(100)
            mathProblemTv.text = "$firstRandomNum + $secondRandomNum ="
        } else if (txt_Enter.text.toString().toInt() != result ) {
            val builder2 = AlertDialog.Builder(this)
            myMessage.add("$firstRandomNum + $secondRandomNum = ${txt_Enter.text.toString()} wrong :(")
            myMessage.add("correct answer is $result")
            tvHS.text = "HighScore $highScore"
            txt_Enter.text = null
            bttn_click.isEnabled = false
            txt_Enter.isEnabled = false
            save()
            builder2.setTitle("Math Study App")
            builder2.setMessage("Play again?")
            builder2.setNegativeButton("No", { dialogInterface: DialogInterface, i: Int ->
                myMessage.add("GAME OVER")})
            builder2.setPositiveButton("Yes", { dialogInterface: DialogInterface, i: Int ->
                bttn_click.isEnabled = true
                txt_Enter.isEnabled = true
            })
            builder2.show()
        }
        val myRV = findViewById<RecyclerView>(R.id.rvGuesses)
        myRV.adapter = RecyclerViewAdapter(myMessage)
        myRV.layoutManager = LinearLayoutManager(this)
    }
    fun save() {

        if (score >= highScore) {
            highScore = score
            myMessage.add(" New High Score!")
            with(sharedPreferences.edit()) {
                putInt("HighScore", highScore)
                apply()
            }
        }
    }
}
