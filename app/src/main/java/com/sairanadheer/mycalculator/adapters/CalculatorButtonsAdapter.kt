package com.sairanadheer.mycalculator.adapters

import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
                mEquationValue.append(holder.buttonValue.text as String)
            }
            mEquation.text = mEquationValue
            mResult.text = calculateResult()

        }
    }

    private fun calculateResult(): String {
        if (!TextUtils.isEmpty(mEquationValue)) {
            val calculatedValue = Expression(replaceAll()).calculate()
            when (calculatedValue.isNaN()) {
                true -> return ""
                false -> {
                    if (!calculatedValue.rem(1).equals(0.0)) {
                        return calculatedValue.toString()
                    } else {
                        return calculatedValue.toInt().toString()
                    }
                }
            }
        } else {
            return ""
        }
    }

    private fun parenthesisFunctionality() {
        try {
            if (TextUtils.isEmpty(mEquationValue)) {
                mEquationValue.append("(")
                isBracketOpen = true
                bracketsOpenCount++
            } else {
                Integer.parseInt(mEquationValue.get(mEquationValue.length - 1).toString())
                if (isBracketOpen) {
                    mEquationValue.append(")")
                    updateOpenBracketsCount()

                } else {
                    mEquationValue.append("x(")
                    isBracketOpen = true
                    bracketsOpenCount++
                }
            }
        } catch (e: NumberFormatException) {
            if (mEquationValue.get(mEquationValue.length - 1).toString().equals(")")) {
                if (isBracketOpen) {
                    mEquationValue.append(")")
                    updateOpenBracketsCount()
                } else {
                    mEquationValue.append("x(")
                    isBracketOpen = true
                    bracketsOpenCount++
                }
            } else {
                mEquationValue.append("(")
                isBracketOpen = true
                bracketsOpenCount++
            }
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