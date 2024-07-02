package project1.calculator1

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var tvFormula: TextView
    private var currentNumber: String = ""
    private var currentOperator: String? = null
    private var firstNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)
        tvFormula = findViewById(R.id.tvFormula)
    }

    fun onNumberClick(view: View) {
        val button = view as Button
        currentNumber += button.text
        tvResult.text = currentNumber
        updateFormula()
    }

    fun onOperatorClick(view: View) {
        val button = view as Button
        if (currentNumber.isNotEmpty()) {
            firstNumber = currentNumber
            currentOperator = button.text.toString()
            currentNumber = ""
            tvResult.text = currentOperator
        } else {
            onEqual(view)
            currentOperator = button.text.toString()
            firstNumber = tvResult.text.toString()
            currentNumber = ""
            tvResult.text = currentOperator
        }
        updateFormula()
    }

    @SuppressLint("SetTextI18n")
    fun onClear(view: View) {
        currentNumber = ""
        firstNumber = ""
        currentOperator = null
        tvResult.text = "0"
        tvFormula.text = ""
    }

    fun onDelete(view: View) {
        if (currentNumber.isNotEmpty()) {
            currentNumber = currentNumber.dropLast(1)
            tvResult.text = if (currentNumber.isEmpty()) "0" else currentNumber
            updateFormula()
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        if (currentNumber.isNotEmpty() && firstNumber.isNotEmpty() && currentOperator != null) {
            val result = when (currentOperator) {
                "+" -> firstNumber.toDouble() + currentNumber.toDouble()
                "-" -> firstNumber.toDouble() - currentNumber.toDouble()
                "*" -> firstNumber.toDouble() * currentNumber.toDouble()
                "/" -> firstNumber.toDouble() / currentNumber.toDouble()
                else -> 0.0
            }

            val resultString = if (result % 1 == 0.0) result.toInt().toString() else result.toString()
            tvResult.text = resultString
            tvFormula.text = "$firstNumber $currentOperator $currentNumber"
            currentNumber = resultString
            firstNumber = ""
            currentOperator = null
        }
    }

    private fun updateFormula(){
        tvFormula.text = if (currentOperator == null){
            currentNumber
        } else {
            "$firstNumber $currentOperator $currentNumber"
        }
    }
}
