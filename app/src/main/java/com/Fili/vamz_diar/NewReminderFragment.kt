package com.Fili.vamz_diar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.Fili.vamz_diar.databinding.FragmentNewReminderBinding
import java.util.*


private const val ARG_PARAM1 = "param1"


/**
 * A simple [Fragment] subclass.
 * Use the [NewReminderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewReminderFragment : Fragment() {

    private var param1: String? = null
    private var _binding: FragmentNewReminderBinding? = null
    private val binding get() = _binding!!


    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewReminderBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupOnClicks()
        setDateTime()
        if(viewModel.FirebaseAuthInstance.currentUser == null) {
            val action = NotesFragmentDirections.actionNotesFragmentToLoginFragment() // TODO: 22.05.2021
            // Navigate using that action
            findNavController().navigate(action)
        }
    }

    private fun setDateTime() {
        val calendar = Calendar.getInstance()
        binding.newReminderDate.text = calendar.get(Calendar.DATE).toString().plus(".").plus(calendar.get(Calendar.MONTH).toString()).plus(".").plus(calendar.get(Calendar.YEAR).toString())
        binding.newReminderTime.text = calendar.get(Calendar.HOUR).toString().plus(":").plus(calendar.get(Calendar.MINUTE).toString())
    }

    private fun setupOnClicks() {
        binding.newReminderDate.setOnClickListener {
            val dateView = binding.newReminderDate
            DatePickerFragment(dateView).show(childFragmentManager,"datePircker")
        }
        binding.newReminderTime.setOnClickListener {
            TimePickerFragment(binding.newReminderTime).show(childFragmentManager,"timePircker")
        }
        binding.newReminderSavebtn.setOnClickListener { viewModel.saveNewReminder(view,binding.newReminderName.text.toString(),binding.newReminderDesc.text.toString(), binding.newReminderDate.text.toString(), binding.newReminderTime.text.toString()) }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment NewReminderFragment.
         * */
        @JvmStatic
        fun newInstance(param1: String) =
            NewReminderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}