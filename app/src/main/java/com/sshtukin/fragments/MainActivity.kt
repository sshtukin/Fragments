package com.sshtukin.fragments

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import java.lang.RuntimeException

/**
 * Activity which contains [FragmentA] and [FragmentB]
 *
 * @author Sergey Shtukin
 */

class MainActivity : AppCompatActivity(), FragmentListener{

    private var counter = 0
    private var isFragmentBWasCalled = false

    private fun isPortrait() = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    private fun setCount(id: Int){
        val fragmentB = supportFragmentManager.findFragmentById(id)
        if (fragmentB is FragmentSettable) {
            fragmentB.setText(counter.toString())
        }
        else{
            throw RuntimeException(getString(R.string.not_implements_fragment_settable))
        }
    }

    private fun setFragment(holderId: Int,
                            fragment: Fragment,
                            backStack: Boolean = false){
        val transaction =  supportFragmentManager.beginTransaction()
        transaction.replace(holderId, fragment)
        if (backStack){
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isPortrait()) {
            setFragment(R.id.fragment_holder, FragmentA())
        }else{
            setFragment(R.id.fragment_holder_a, FragmentA())
            setFragment(R.id.fragment_holder_b, FragmentB.newInstance(counter.toString()))
        }
    }

    override fun onFragmentButtonClicked() {
        counter++
        if (isPortrait()){
            setFragment(R.id.fragment_holder, FragmentB.newInstance(counter.toString()), true)
        }else{
            setCount(R.id.fragment_holder_b)
        }
        isFragmentBWasCalled = true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        isFragmentBWasCalled = false
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState?.getString(COUNTER_KEY)?.toInt()?: 0
        isFragmentBWasCalled = savedInstanceState?.getBoolean(FRAGMENT_STATE)?: false
        if (!isPortrait()) {
           setCount(R.id.fragment_holder_b)
        }
        else {
            if (isFragmentBWasCalled){
                setFragment(R.id.fragment_holder, FragmentB.newInstance(counter.toString()), true)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(COUNTER_KEY, counter.toString())
        outState?.putBoolean(FRAGMENT_STATE, isFragmentBWasCalled)
        super.onSaveInstanceState(outState)
    }

    private companion object {
        private val COUNTER_KEY = "1001"
        private val FRAGMENT_STATE = "1003"
    }
}
