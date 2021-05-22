package com.Fili.vamz_diar.groupieItems

import android.view.View
import androidx.navigation.findNavController
import com.Fili.vamz_diar.R
import com.Fili.vamz_diar.RemindersFragmentDirections
import com.Fili.vamz_diar.classes.reminder
import com.Fili.vamz_diar.databinding.ReminderItemBinding
import com.xwray.groupie.viewbinding.BindableItem

class ReminderGroupieItem(private val preminder: reminder): BindableItem<ReminderItemBinding>() {
    /**
     * Perform any actions required to set up the view for display.
     *
     * @param viewBinding The ViewBinding to bind
     * @param position The adapter position
     */
    override fun bind(viewBinding: ReminderItemBinding, position: Int) {
        viewBinding.reminderButton.text = preminder.reminderName
        viewBinding.reminderButton.setOnClickListener {
            val action = RemindersFragmentDirections.actionRemindersFragmentToReminderDetailFragment(reminder = preminder)
            it.findNavController().navigate(action)
        }
    }

    override fun getLayout(): Int {
        return R.layout.reminder_item
    }

    override fun initializeViewBinding(view: View): ReminderItemBinding {
        return  ReminderItemBinding.bind(view)
    }
}