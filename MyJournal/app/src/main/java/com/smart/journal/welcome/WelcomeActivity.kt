package com.smart.journal.welcome

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.media.session.PlaybackStateCompat
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.RotateAnimation
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import com.smart.journal.MainActivity
import com.smart.journal.R
import com.smart.journal.base.BaseActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {
    override fun initData() {
    }

    override fun initView() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        var anim = RotateAnimation(0f,720f,Animation.RELATIVE_TO_SELF,.5f,Animation.RELATIVE_TO_SELF,.5f)
        anim.duration = 2000
        anim.interpolator = FastOutSlowInInterpolator()
        anim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                startActivity(Intent(context, MainActivity::class.java))
                finish()
            }

            override fun onAnimationStart(p0: Animation?) {

            }


        })
        logoImageView.startAnimation(anim)


    }
}
