package com.example.calculatorandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvInput : TextView? = null
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false
    var containsOpr: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }

    fun onDigit(view: View) {
        // view is the btn we call this method
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
        lastNumeric = false
        lastDot = false
        containsOpr = false
    }

    fun onDecimalPoint(view: View) {
        // we don't want another dot if a dot exists already
        if(lastNumeric && !lastDot) {
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View) {
        tvInput?.text.let{
            val curOpr = (view as Button).text
            var message = "${it.toString()} contains Operator: $containsOpr"

//            Toast.makeText(this, message, Toast.LENGTH_LONG).show()

            if(it == "" && curOpr == "-") {
                tvInput?.append(curOpr)
            }
            if(lastNumeric && !containsOpr) {
                    tvInput?.append(curOpr)
                    lastNumeric = false
                    lastDot = false
                    containsOpr = true
            }
        }
    }

    fun onEqual(view: View) {
        if(lastNumeric) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            try{
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    var two = splitValue[1]

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    var two = splitValue[1]

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                } else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    var two = splitValue[1]

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    var two = splitValue[1]

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
                containsOpr = false
            } catch(e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if(result.endsWith(".0"))
            value = result.substring(0, result.length - 1)

        return value
    }

}