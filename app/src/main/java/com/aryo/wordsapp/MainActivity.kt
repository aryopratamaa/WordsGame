package com.aryo.wordsapp

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private lateinit var img: ImageView
    private lateinit var txtFeedback: TextView
    private lateinit var txtSoalIndicator: TextView
    private lateinit var txtSkor: TextView
    private lateinit var btnNext: Button
    private lateinit var btnRestart: Button
    private lateinit var cardImage: CardView

    private lateinit var option1: Button
    private lateinit var option2: Button
    private lateinit var option3: Button
    private lateinit var option4: Button

    data class Soal(
        val gambar: Int,
        val jawaban: String,
        val opsi: List<String>
    )

    private val soalList = listOf(
        Soal(R.drawable.apel, "Apel", listOf("Apel", "Jeruk", "Mangga", "Pisang")),
        Soal(R.drawable.pisang, "Pisang", listOf("Apel", "Semangka", "Pisang", "Anggur")),
        Soal(R.drawable.jeruk, "Jeruk", listOf("Mangga", "Jeruk", "Melon", "Nanas")),
        Soal(R.drawable.anggur, "Anggur", listOf("Pisang", "Anggur", "Apel", "Pepaya")),
        Soal(R.drawable.mangga, "Mangga", listOf("Mangga", "Nanas", "Semangka", "Durian"))
    )

    private var currentSoalIndex = 0
    private var skor = 0
    private var answered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        img = findViewById(R.id.img)
        txtFeedback = findViewById(R.id.txtFeedback)
        txtSoalIndicator = findViewById(R.id.txtSoalIndicator)
        txtSkor = findViewById(R.id.txtSkor)
        btnNext = findViewById(R.id.btnNext)
        btnRestart = findViewById(R.id.btnRestart)
        cardImage = findViewById(R.id.cardImage)

        option1 = findViewById(R.id.option1)
        option2 = findViewById(R.id.option2)
        option3 = findViewById(R.id.option3)
        option4 = findViewById(R.id.option4)

        tampilkanSoal()

        option1.setOnClickListener { cekJawaban(option1.text.toString()) }
        option2.setOnClickListener { cekJawaban(option2.text.toString()) }
        option3.setOnClickListener { cekJawaban(option3.text.toString()) }
        option4.setOnClickListener { cekJawaban(option4.text.toString()) }

        btnNext.setOnClickListener {
            currentSoalIndex++
            tampilkanSoal()
        }

        btnRestart.setOnClickListener {
            currentSoalIndex = 0
            skor = 0
            tampilkanSoal()
        }
    }

    private fun tampilkanSoal() {
        if (currentSoalIndex < soalList.size) {
            val soal = soalList[currentSoalIndex]
            img.setImageResource(soal.gambar)
            txtSoalIndicator.text = "Soal ${currentSoalIndex + 1} dari ${soalList.size}"
            txtSkor.text = "Skor: $skor"

            option1.text = soal.opsi[0]
            option2.text = soal.opsi[1]
            option3.text = soal.opsi[2]
            option4.text = soal.opsi[3]

            txtFeedback.visibility = View.GONE
            btnNext.visibility = View.GONE
            answered = false

            option1.isEnabled = true
            option2.isEnabled = true
            option3.isEnabled = true
            option4.isEnabled = true

            btnRestart.visibility = View.GONE
            cardImage.visibility = View.VISIBLE

            option1.visibility = View.VISIBLE
            option2.visibility = View.VISIBLE
            option3.visibility = View.VISIBLE
            option4.visibility = View.VISIBLE
        } else {
            txtFeedback.visibility = View.VISIBLE
            txtFeedback.text = "SELESAI!\nSkor akhir kamu: $skor"
            txtFeedback.setTextColor(Color.BLUE)
            txtFeedback.textSize = 20f
            txtFeedback.textAlignment = View.TEXT_ALIGNMENT_CENTER

            cardImage.visibility = View.GONE
            option1.visibility = View.GONE
            option2.visibility = View.GONE
            option3.visibility = View.GONE
            option4.visibility = View.GONE
            btnNext.visibility = View.GONE

            btnRestart.visibility = View.VISIBLE
        }
    }

    private fun cekJawaban(jawabanUser: String) {
        if (answered) return
        answered = true

        val soal = soalList[currentSoalIndex]
        txtFeedback.visibility = View.VISIBLE

        if (jawabanUser.equals(soal.jawaban, ignoreCase = true)) {
            txtFeedback.text = "Jawaban Benar!"
            txtFeedback.setTextColor(Color.GREEN)
            skor++
        } else {
            txtFeedback.text = "Jawaban Salah!\nYang benar: ${soal.jawaban}"
            txtFeedback.setTextColor(Color.RED)
        }

        txtSkor.text = "Skor: $skor"

        option1.isEnabled = false
        option2.isEnabled = false
        option3.isEnabled = false
        option4.isEnabled = false

        btnNext.visibility = View.VISIBLE
    }
}
