package com.sairanadheer.mycalculator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.sairanadheer.mycalculator.R

class CalculatorButtonsAdapter: RecyclerView.Adapter<CalculatorButtonsAdapter.CalculatorButtonsViewHolder> {

    private var mData: List<String>
    private var mContext:Context

    constructor(context: Context, data: List<String>){
        this.mContext = context
        this.mData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalculatorButtonsViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.card_calculator_buttons, parent, false);
        return CalculatorButtonsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalculatorButtonsViewHolder, position: Int) {
        holder.buttonValue.setText(mData.get(position))
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class CalculatorButtonsViewHolder: RecyclerView.ViewHolder {

        var buttonValue: AppCompatTextView

        constructor(itemView: View) : super(itemView){
            buttonValue = itemView.findViewById(R.id.button_value)
        }

    }
}