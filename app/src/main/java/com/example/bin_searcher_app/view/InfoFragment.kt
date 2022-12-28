package com.example.bin_searcher_app.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.bin_searcher_app.R
import com.example.bin_searcher_app.databinding.FragmentInfoBinding
import com.example.bin_searcher_app.model.DataModel


class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private  lateinit var data: DataModel
    private lateinit var intent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true ) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_infoFragment_to_mainFragment)
                }
            }

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setData()

        clickIntent()

        binding.buttonBack.setOnClickListener{
            findNavController().navigate(R.id.action_infoFragment_to_mainFragment)
        }

        super.onViewCreated(view, savedInstanceState)
    }


    private fun setData(){
        data = arguments?.getParcelable("DataModel")!!
        binding.systemChild.text = data.scheme
        binding.cardTypeChild.text = data.type
        binding.brandChild.text = data.brand
        binding.prepaidChild.text = data.prepared.toString()
        binding.lengthChild.text = data.number.length.toString()
        binding.luhnChild.text = data.number.luhn.toString()
        binding.countryNameChild.text = data.country.name
        binding.numericChild.text = data.country.numeric
        binding.alfa2Child.text = data.country.alpha2
        binding.emojiChild.text = data.country.emoji
        binding.currencyChild.text = data.country.currency
        binding.latitudeChild.text = data.country.latitude.toString()
        binding.longitudeChild.text = data.country.longitude.toString()
        binding.bankNameChild.text = data.bank.name
        binding.urlChild.text = data.bank.url
        binding.phoneChild.text = data.bank.phone
        binding.cityChild.text = data.bank.city
    }

    private fun clickIntent(){

        binding.phoneChild.setOnClickListener {
            intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:" + binding.phoneChild.text)
            startActivity(intent)
        }

        binding.cityChild.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("geo:" + binding.latitudeChild.text + ","+ binding.longitudeChild.text )
            startActivity(intent)
        }

        binding.urlChild.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://${binding.urlChild.text}"))
            startActivity(intent)
        }

    }


}

