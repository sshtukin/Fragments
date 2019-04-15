package com.sshtukin.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_a.*
import java.lang.RuntimeException

/**
 * A fragment which contains [btnRunFragmentB] to start [FragmentB].
 *
 *  @author Sergey Shtukin
 */


class FragmentA : Fragment() {

    private lateinit var listener: FragmentListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is FragmentListener){
            listener = context
        }
        else {
            throw RuntimeException(getString(R.string.not_implements_fragment_listener))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onStart() {
        super.onStart()
        btnRunFragmentB.setOnClickListener {
            listener.onFragmentButtonClicked()
        }
    }
}
