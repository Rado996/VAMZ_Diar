package com.Fili.vamz_diar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Fili.vamz_diar.classes.reminder
import com.Fili.vamz_diar.databinding.FragmentReminderDetailBinding
import com.Fili.vamz_diar.databinding.FragmentRemindersBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder


private const val ARG_PARAM1 = "reminder"


/**
 * A simple [Fragment] subclass.
 * Use the [ReminderDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReminderDetailFragment : Fragment() {

    private var param1: reminder? = null

    private var _binding: FragmentReminderDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable<reminder>(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReminderDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupOnClicks()
        binding.reminderDetailName.text = param1!!.reminderName
        binding.reminderDetailDescription.text = param1!!.reminderDesc
        binding.reminderDetailDatetime.text = param1!!.reminderDate.plus("  ").plus(param1!!.reminderTime)
        if(viewModel.FirebaseAuthInstance.currentUser == null) {
            val action = NotesFragmentDirections.actionNotesFragmentToLoginFragment() // TODO: 22.05.2021
            // Navigate using that action
            findNavController().navigate(action)
        }


    }

    private fun setupOnClicks() {

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
         * @return A new instance of fragment ReminderDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: reminder) =
            ReminderDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}