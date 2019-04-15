package com.sshtukin.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_b.*

/**
 * A fragment implements FragmentSettable and
 * contains [tvCounter].
 *
 *  @author Sergey Shtukin
 */

class FragmentB : Fragment(), FragmentSettable{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onStart() {
        super.onStart()
        arguments?.getString(COUNTER_KEY)?.let {
            setText(it)
        }
    }

    override fun setText(text: String){
        tvCounter.text = text
    }

    companion object {
        private val COUNTER_KEY = "1002"

        fun newInstance(count: String) = FragmentB().apply {
            arguments = Bundle().apply {
                putString(COUNTER_KEY, count)
            }
        }
    }
}
