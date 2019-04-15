package com.sshtukin.fragments

import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.lang.RuntimeException

/**
 * Activity which contains [FragmentA] and [FragmentB]
 *
 * @author Sergey Shtukin
 */

class MainActivity : AppCompatActivity(), FragmentListener{

    private var counter = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (isPortrait()) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, FragmentA())
                .commit()
        }else{
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_holder_a, FragmentA())
                .add(R.id.fragment_holder_b, FragmentB())
                .commit()
        }
    }

    override fun onFragmentButtonClicked() {
        counter++
        if (isPortrait()){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, FragmentB.newInstance(counter.toString()))
                .addToBackStack(null)
                .commit()
        }else{
            setCount(R.id.fragment_holder_b)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        counter = savedInstanceState?.getString(COUNTER_KEY)?.toInt()?: 0
        if (!isPortrait()) {
           setCount(R.id.fragment_holder_b)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(COUNTER_KEY, counter.toString())
        super.onSaveInstanceState(outState)
    }

    private companion object {
        private val COUNTER_KEY = "1001"
    }
}
