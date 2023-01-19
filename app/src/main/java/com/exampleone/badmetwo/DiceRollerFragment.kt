package com.exampleone.badmetwo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


class DiceRollerFragment : Fragment() {
    
    lateinit var scope: CoroutineScope

    var delayTime = 20
    var rollAnimations = 40

    var diceImages = intArrayOf(
        R.drawable.d1,
        R.drawable.d2,
        R.drawable.d3,
        R.drawable.d4,
        R.drawable.d5,
        R.drawable.d6
    )

    var tvHelp: TextView? = null
    var die1: ImageView? = null
    var die2: ImageView? = null
    lateinit var testButton:Button
    lateinit var diceContainer: LinearLayout
    var mp: MediaPlayer? = null
    val random = java.util.Random()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_dice_roller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvHelp = view.findViewById(R.id.tvHelp);
        diceContainer = view.findViewById(R.id.diceContainer);
        die1 = view.findViewById(R.id.die1);
        die2 = view.findViewById(R.id.die2);
        // Instantiate the MediaPlayer object
        mp = MediaPlayer.create(requireContext(), R.raw.app_src_main_res_raw_roll)
        testButton = view.findViewById(R.id.test_bt)

        diceContainer.setOnClickListener {
            rollDice()
        }
        testButton.setOnClickListener {
           activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main, SpinningGameFragment())?.commit()
        }
    }

    private fun rollDice() {
        scope = CoroutineScope(Dispatchers.Main)
        scope.launch {

            for (i in 0 until rollAnimations) {
                val dice1 = random.nextInt(6).plus(1)
                val dice2 = random.nextInt(6).plus(1)
                die1!!.setImageResource(diceImages[dice1 - 1])
                die2!!.setImageResource(diceImages[dice2 - 1])
                delay(5)
            }

            YoYo.with(Techniques.Tada)
                .duration(700)
                .repeat(0)
                .playOn(die1);

            YoYo.with(Techniques.Tada)
                .duration(200)
                .repeat(0)
                .playOn(die2);

            mp?.start()
        }
    }

}