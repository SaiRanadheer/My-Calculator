package com.sairanadheer.mycalculator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.sairanadheer.mycalculator.R

class CalculatorButtonsAdapter(context: Context, data: List<String>, equation: AppCompatTextView) :
    RecyclerView.Adapter<CalculatorButtonsAdapter.CalculatorButtonsViewHolder>() {

    private val mData: List<String> = data
    private val mContext: Context = context
    private val mEquation: AppCompatTextView = equation
    private var mEquationValue: StringBuilder = StringBuilder()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorButtonsViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.card_calculator_buttons, parent, false);
        return CalculatorButtonsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalculatorButtonsViewHolder, position: Int) {
        holder.buttonValue.setText(mData.get(position))
        holder.buttonCard.setOnClickListener {
            mEquationValue.append(holder.buttonValue.text)
            mEquation.setText(mEquationValue)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class CalculatorButtonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var buttonValue: AppCompatTextView
        var buttonCard: CardView

        init {
            buttonValue = itemView.findViewById(R.id.button_value)
            buttonCard = itemView.findViewById(R.id.button_card)
        }

    }
}