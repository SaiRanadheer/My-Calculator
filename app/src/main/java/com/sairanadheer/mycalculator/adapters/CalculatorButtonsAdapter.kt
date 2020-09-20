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

class CalculatorButtonsAdapter(context: Context, data: List<String>, equation: MaterialTextView, result: MaterialTextView) :
    RecyclerView.Adapter<CalculatorButtonsAdapter.CalculatorButtonsViewHolder>() {

    private val mData: List<String> = data
    private val mContext: Context = context
    private val mEquation: MaterialTextView = equation
    private val mResult: MaterialTextView = result
    private var mEquationValue: StringBuilder = StringBuilder()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorButtonsViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.card_calculator_buttons, parent, false)
        return CalculatorButtonsViewHolder(view)
    }


    override fun onBindViewHolder(holder: CalculatorButtonsViewHolder, position: Int) {
        if(position == 0){
            if(Build.VERSION.SDK_INT >= 23) {
                holder.buttonValue.setTextColor(mContext.getColor(R.color.crimson))
            } else {
                holder.buttonValue.setTextColor(ContextCompat.getColor(mContext, R.color.crimson))
            }
        }
        holder.buttonValue.text = mData[position]
        holder.buttonCard.setOnClickListener {
            if(position == 0){
                mEquationValue.clear()
            } else {
                mEquationValue.append(holder.buttonValue.text as String)
            }
            mEquation.text = mEquationValue
            if(!TextUtils.isEmpty(mEquationValue)){
                val expressionValue = replaceAll()
                val expression = Expression(expressionValue.toString())
                val calculatedValue = expression.calculate()
                when(calculatedValue.isNaN()){
                    true -> mResult.text = ""
                    false -> mResult.text = calculatedValue.toString()
                }
            }
        }
    }

    private fun replaceAll(): StringBuilder {
        var index = mEquationValue.indexOf("x")
        val replacedEquation = StringBuilder(mEquationValue)
        while(index != -1){
            replacedEquation.replace(index, index + 1, "*")
            index++
            index = replacedEquation.indexOf("x", index)
        }
        return replacedEquation
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class CalculatorButtonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var buttonValue: MaterialTextView = itemView.findViewById(R.id.button_value)
        var buttonCard: MaterialCardView = itemView.findViewById(R.id.button_card)

    }
}