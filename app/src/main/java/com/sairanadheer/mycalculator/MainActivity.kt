package com.sairanadheer.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.sairanadheer.mycalculator.adapters.CalculatorButtonsAdapter
import com.sairanadheer.mycalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var calculatorButtons: RecyclerView
    private lateinit var equation: MaterialTextView
    private lateinit var result: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        configureViewElements()
        initializeData()
    }

    private fun initializeData() {
        val data = arrayListOf("C", "()", "%", "/", "7", "8", "9", "x", "4", "5", "6", "-", "1", "2", "3", "+", "+/-", "0", ".", "<-")
        val adapter = CalculatorButtonsAdapter(this, data, equation, result)
        calculatorButtons.adapter = adapter
    }

    private fun configureViewElements() {
        equation = binding.equation
        result = binding.result
        calculatorButtons = binding.calculatorButtons
        calculatorButtons.setHasFixedSize(true)
        calculatorButtons.layoutManager = GridLayoutManager(this, 4)
    }
}