package tads.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var buttons: Array<Array<Button>>
    private var isPlayer1Turn = true
    private var roundCount = 0
    private var player1Points = 0
    private var player2Points = 0
    private var draws = 0

    private lateinit var textViewPlayer1: TextView
    private lateinit var textViewPlayer2: TextView
    private lateinit var textViewDraws: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewPlayer1 = findViewById(R.id.player1Score)
        textViewPlayer2 = findViewById(R.id.player2Score)
        textViewDraws = findViewById(R.id.draws)

        buttons = arrayOf(
            arrayOf(findViewById(R.id.button1), findViewById(R.id.button2), findViewById(R.id.button3)),
            arrayOf(findViewById(R.id.button4), findViewById(R.id.button5), findViewById(R.id.button6)),
            arrayOf(findViewById(R.id.button7), findViewById(R.id.button8), findViewById(R.id.button9))
        )

        for (row in buttons) {
            for (button in row) {
                button.setOnClickListener { onButtonClick(button) }
            }
        }
    }

    private fun onButtonClick(button: Button) {
        if (button.text.isNotEmpty()) {
            return
        }

        button.text = if (isPlayer1Turn) "X" else "O"
        roundCount++

        if (checkForWin()) {
            if (isPlayer1Turn) {
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (roundCount == 9) {
            draw()
        } else {
            isPlayer1Turn = !isPlayer1Turn
        }
    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { row ->
            Array(3) { col ->
                buttons[row][col].text.toString()
            }
        }

        for (i in 0..2) {
            if ((field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0].isNotEmpty()) ||
                (field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i].isNotEmpty())) {
                return true
            }
        }

        if ((field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0].isNotEmpty()) ||
            (field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2].isNotEmpty())) {
            return true
        }

        return false
    }

    private fun player1Wins() {
        player1Points++
        Toast.makeText(this, getString(R.string.player1_wins), Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun player2Wins() {
        player2Points++
        Toast.makeText(this, getString(R.string.player2_wins), Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun draw() {
        draws++
        Toast.makeText(this, getString(R.string.draw), Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    private fun updatePointsText() {
        textViewPlayer1.text = getString(R.string.player1_score, player1Points)
        textViewPlayer2.text = getString(R.string.player2_score, player2Points)
        textViewDraws.text = getString(R.string.draws, draws)
    }

    private fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].text = ""
            }
        }
        roundCount = 0
        isPlayer1Turn = true
    }
}