package com.Fili.vamz_diar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.Fili.vamz_diar.databinding.FragmentLoginBinding



/**
 * A simple [Fragment] subclass for Logging in to the aplication or registration.
 *
 */
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    /**
     * Setups onClick listeners for buttons
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.loginBtn.setOnClickListener { loginUser() }
        binding.registrationBtn.setOnClickListener { registerUser() }


//        viewModel.registered.observe(viewLifecycleOwner, Observer { registered->
//            if (registered == true){
//                Toast.makeText(context, R.string.register_succes, Toast.LENGTH_SHORT).show()
////                val action = LoginFragmentDirections.actionLoginFragmentToNotesFragment()
////                // Navigate using that action
////                findNavController().navigate(action)
//                findNavController().navigateUp()
//            }
//        })
//        viewModel.logedIn.observe(viewLifecycleOwner, Observer { loggedIn->
//            if (loggedIn == true){
//                Toast.makeText(context, R.string.login_succes, Toast.LENGTH_SHORT).show()
////                val action = LoginFragmentDirections.actionLoginFragmentToNotesFragment()
//////                // Navigate using that action
//////                findNavController().navigate(action)
//                  findNavController().navigateUp()
//            }
//        })

    }

    /**
     * method checks user input and register him trough viewModel method registerUser.
     */
    private fun registerUser() {
        if(binding.authLoginEmail.text.isBlank() || binding.authLoginPassword.text.isBlank())
            Toast.makeText(context, R.string.authError, Toast.LENGTH_SHORT).show()
        else {
            val userEmail = binding.authLoginEmail.text.toString().trim()
            val userPassword = binding.authLoginPassword.text.toString().trim()
            viewModel.registerNewUser(userEmail,userPassword,view)
        }
    }
    /**
     * method checks user input and sing him in trough viewModel method logInUser.
     */
    private fun loginUser() {
        if(binding.authLoginEmail.text.isBlank() || binding.authLoginPassword.text.isBlank())
            Toast.makeText(context, R.string.authError, Toast.LENGTH_SHORT).show()
        else {
            val userEmail = binding.authLoginEmail.text.toString().trim()
            val userPassword = binding.authLoginPassword.text.toString().trim()
            viewModel.LogInUser(userEmail,userPassword,view)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}