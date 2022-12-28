package com.example.bin_searcher_app.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bin_searcher_app.databinding.FragmentMainBinding
import com.example.bin_searcher_app.network.BinApp
import com.example.bin_searcher_app.viewmodel.MainViewModel


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState == null){
            binding = FragmentMainBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search()

        setListView()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun search(){
        binding.button.setOnClickListener{
            if (binding.searchView.query.isNotEmpty()){
                if (binding.searchView.query.length == 8){
                    viewModel = ViewModelProvider(this)[MainViewModel::class.java]
                    viewModel.searchCardInfo((activity?.application as? BinApp)?.binApi, binding.searchView.query.toString())
                    viewModel.livedataStatus.observe(viewLifecycleOwner) { e ->
                        if (e == "onSuccess"){
                            binding.listView.visibility = View.GONE
                            viewModel.addDB(requireContext(), binding.searchView.query.toString())
                            val bundle = Bundle()
                            bundle.putParcelable("DataModel", viewModel.liveData.value)
                            findNavController().navigate(com.example.bin_searcher_app.R.id.action_mainFragment_to_infoFragment, bundle)
                        } else {
                            Toast.makeText(
                                requireContext(),
                                e,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }else {
                    Toast.makeText(
                        requireContext(),
                        "Нужно 8 знаков",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }else {
                Toast.makeText(
                    requireContext(),
                    "Поле ввода пусто",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setListView(){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.readDB(requireContext())
        val arrayList = arrayListOf<String>()
        viewModel.livedataDB.observe(viewLifecycleOwner) {array -> arrayList.addAll(array)}

        view?.setOnClickListener{
            binding.listView.visibility = View.GONE
        }

        val adapter = ArrayAdapter(requireContext().applicationContext, android.R.layout.simple_list_item_1, arrayList)
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchView.clearFocus()
                if (arrayList.contains(p0)){
                    adapter.filter
                }
                return false
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                binding.listView.visibility = View.VISIBLE
                return false
            }
        })
        binding.listView.adapter = adapter

        binding.btnDelete.setOnClickListener{
            viewModel.deleteDB(requireContext())
            arrayList.clear()
            adapter.clear()
        }
    }

}

