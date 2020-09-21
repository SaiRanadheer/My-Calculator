package com.sairanadheer.mycalculator.adapters

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.sairanadheer.mycalculator.R
import org.mariuszgromada.math.mxparser.Expression
import java.lang.NumberFormatException

class CalculatorButtonsAdapter(
    context: Context,
    data: List<String>,
    equation: MaterialTextView,
    result: MaterialTextView
) :
    RecyclerView.Adapter<CalculatorButtonsAdapter.CalculatorButtonsViewHolder>() {

    private val mData: List<String> = data
    private val mContext: Context = context
    private val mEquation: MaterialTextView = equation
    private val mResult: MaterialTextView = result
    private var mEquationValue: StringBuilder = StringBuilder()

    private var isBracketOpen = false
    private var bracketsOpenCount = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorButtonsViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.card_calculator_buttons, parent, false)
        return CalculatorButtonsViewHolder(view)
    }


    override fun onBindViewHolder(holder: CalculatorButtonsViewHolder, position: Int) {
        if (position == 0) {
            configureClearButton(holder)
        }
        holder.buttonValue.text = mData[position]
        holder.buttonCard.setOnClickListener {
            if (position == 0) {
                clearButtonFunctionality()
            } else if (position == 1) {
                parenthesisFunctionality()
            } else if (position == mData.size - 1) {
                if (!TextUtils.isEmpty(mEquationValue)) {
                    mEquationValue.deleteCharAt(mEquationValue.length - 1)
                }
            } else {
                if(formatIsValid(position)){
                    mEquationValue.append(holder.buttonValue.text as String)
                }

            }
            mEquation.text = mEquationValue
            mResult.text = calculateResult()

        }
    }

    private fun formatIsValid(position: Int): Boolean {
        val addSubOperators = arrayListOf("+", "-")
        val divMulOperators = arrayListOf("x", "%", "/")
        if(divMulOperators.contains(mData[position])) {
            return divMulOperatorsFormat()

        } else if(addSubOperators.contains(mData[position])) {
            return addSubOperatorsFormat()
        }
        return true
    }

    private fun addSubOperatorsFormat(): Boolean {
        val operators = arrayListOf("+", "-", "x", "/", "%")
        return if (!TextUtils.isEmpty(mEquationValue)) {
            val lastCharacter = mEquationValue[mEquationValue.length - 1]
            if(operators.contains(lastCharacter.toString())) {
                mEquationValue.deleteCharAt(mEquationValue.length - 1)
            }
            true
        } else {
            invalidFormatToast()
        }
    }

    private fun divMulOperatorsFormat(): Boolean {
        val operators = arrayListOf("+", "-", "x", "/", "%")
        return if (!TextUtils.isEmpty(mEquationValue)) {
            val lastCharacter = mEquationValue[mEquationValue.length - 1]
            if (lastCharacter == '(') {
                return invalidFormatToast()
            } else if(operators.contains(lastCharacter.toString())) {
                mEquationValue.deleteCharAt(mEquationValue.length - 1)
                if(mEquationValue[mEquationValue.length - 1] == '('){
                    return invalidFormatToast()
                }
            }
            true
        } else {
            invalidFormatToast()
        }
    }

    private fun invalidFormatToast(): Boolean {
        Toast.makeText(mContext, "Invalid format", Toast.LENGTH_LONG).show()
        return false
    }

    private fun calculateResult(): String {
        return if (!TextUtils.isEmpty(mEquationValue)) {
            val calculatedValue = Expression(replaceAll()).calculate()
            when (calculatedValue.isNaN()) {
                true -> ""
                false -> {
                    if (!calculatedValue.rem(1).equals(0.0)) {
                        calculatedValue.toString()
                    } else {
                        calculatedValue.toInt().toString()
                    }
                }
            }
        } else {
            ""
        }
    }

    private fun parenthesisFunctionality() {
        try {
            if (TextUtils.isEmpty(mEquationValue)) {
                mEquationValue.append("(")
                isBracketOpen = true
                bracketsOpenCount++
            } else {
                Integer.parseInt(mEquationValue[mEquationValue.length - 1].toString())
                openParenthesisCheck()

            }
        } catch (e: NumberFormatException) {
            if (mEquationValue[mEquationValue.length - 1] == ')') {
                openParenthesisCheck()
            } else {
                mEquationValue.append("(")
                isBracketOpen = true
                bracketsOpenCount++
            }
        }
    }

    private fun openParenthesisCheck() {
        if (isBracketOpen) {
            mEquationValue.append(")")
            updateOpenBracketsCount()

        } else {
            mEquationValue.append("x(")
            isBracketOpen = true
            bracketsOpenCount++
        }
    }

    private fun updateOpenBracketsCount() {
        if (--bracketsOpenCount == 0) {
            isBracketOpen = false
        }
    }

    private fun clearButtonFunctionality() {
        mEquationValue.clear()
        mResult.text = ""
        isBracketOpen = false
        bracketsOpenCount = 0
    }

    private fun configureClearButton(holder: CalculatorButtonsViewHolder) {
        if (Build.VERSION.SDK_INT >= 23) {
            holder.buttonValue.setTextColor(mContext.getColor(R.color.crimson))
        } else {
            holder.buttonValue.setTextColor(ContextCompat.getColor(mContext, R.color.crimson))
        }
    }

    private fun replaceAll(): String {
        var index = mEquationValue.indexOf("x")
        val replacedEquation = StringBuilder(mEquationValue)
        while (index != -1) {
            replacedEquation.replace(index, index + 1, "*")
            index++
            index = replacedEquation.indexOf("x", index)
        }
        return replacedEquation.toString()
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class CalculatorButtonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var buttonValue: MaterialTextView = itemView.findViewById(R.id.button_value)
        var buttonCard: MaterialCardView = itemView.findViewById(R.id.button_card)

    }
}