package com.sairanadheer.mycalculator.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.sairanadheer.mycalculator.R

class CalculatorButtonsAdapter(context: Context, data: List<String>, equation: MaterialTextView) :
    RecyclerView.Adapter<CalculatorButtonsAdapter.CalculatorButtonsViewHolder>() {

    private val mData: List<String> = data
    private val mContext: Context = context
    private val mEquation: MaterialTextView = equation
    private var mEquationValue: StringBuilder = StringBuilder()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorButtonsViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.card_calculator_buttons, parent, false);
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
        holder.buttonValue.setText(mData.get(position))
        holder.buttonCard.setOnClickListener {
            if(position == 0){
                mEquationValue.clear();
            } else {
                mEquationValue.append(holder.buttonValue.text)
            }
            mEquation.setText(mEquationValue)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class CalculatorButtonsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var buttonValue: MaterialTextView
        var buttonCard: MaterialCardView

        init {
            buttonValue = itemView.findViewById(R.id.button_value)
            buttonCard = itemView.findViewById(R.id.button_card)
        }

    }
}