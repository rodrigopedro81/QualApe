package com.qds

import android.view.View

/**
 * Animates the view making it go away to the left after a little walk to the right.
 */
fun View.goLeft(duration: Long) {
    this.animate()
        .setDuration(duration * 3 / 10)
        .translationX(200.0f)
        .withEndAction {
            this.animate().setDuration(duration * 7 / 10).translationX(-1500.0f).start()
        }
}

/**
 * Animates the view making it go away to the left after a little walk to the right and executing the retrieved function afterwards
 */
fun View.goLeft(duration: Long, function: (() -> Unit)?) {
    this.animate()
        .setDuration(duration / 5)
        .translationX(200.0f)
        .withEndAction {
            this.animate().setDuration(duration * 4 / 5).translationX(-5000.0f).withEndAction {
                if (function != null) {
                    function()
                }
            }
        }
}

/**
 * Animates the view with a `go to the Left and come from right` animation
 */
fun View.goLeftThenComeFromRight(duration: Long) {
    this.animate()
        .setDuration(duration / 2)
        .translationX(-1000.0f)
        .withEndAction {
            this.animate().setDuration(0).translationX(1000.0f).withEndAction {
                this.animate().setDuration(duration / 2).translationX(0.0f)
            }
        }
}

/**
 * Animates the view with a `go to the Left and come from right` animation and executes the received function afterwards
 */
fun View.goLeftThenComeFromRight(duration: Long, function: (() -> Unit)?) {
    this.animate()
        .setDuration(duration / 2)
        .translationX(-1000.0f)
        .withEndAction {
            this.animate().setDuration(0).translationX(1000.0f).withEndAction {
                if (function != null) {
                    function()
                }
                this.animate().setDuration(duration / 2).translationX(0.0f)
            }
        }
}

/**
 * Animates the view with a `go to the Right and come from left` animation
 */
fun View.goRightThenComeFromLeft(duration: Long) {
    this.animate()
        .setDuration(duration / 2)
        .translationX(1000.0f)
        .withEndAction {
            this.animate().setDuration(0).translationX(-1000.0f).withEndAction {
                this.animate().setDuration(duration / 2).translationX(0.0f)
            }
        }
}

/**
 * Animates the view with a `go to the Right and come from left` animation and executes the received function afterwards
 */
fun View.goRightThenComeFromLeft(duration: Long, function: (() -> Unit)?) {
    this.animate()
        .setDuration(duration / 2)
        .translationX(1000.0f)
        .withEndAction {
            this.animate().setDuration(0).translationX(-1000.0f).withEndAction {
                function?.let { function() }
                this.animate().setDuration(duration / 2).translationX(0.0f)
            }
        }
}

/**
 * Animates the view with a fall animation and executes the received function afterwards
 */
fun View.fall(duration: Long, function: (() -> Unit)?) {
    this.animate().setDuration(duration).translationYBy(200.0f).withEndAction {
        function?.let { function() }
    }
}

/**
 * Animates the view with a fall animation and executes the received function afterwards
 */
fun View.rise(duration: Long, function: (() -> Unit)?) {
    this.animate().setDuration(duration).translationYBy(-200.0f).withEndAction {
        function?.let { function() }
    }
}

/**
 * Animates the view with a fade in animation
 */
fun View.fadeIn(duration: Long) {
    this.animate().setDuration(0).alpha(0.0f).withEndAction {
        this.animate().setDuration(duration).alpha(1.0f)
    }
}

/**
 * Animates the view with a fade in animation and executes the retrieved function
 */
fun View.fadeIn(duration: Long, function: (() -> Unit)?) {
    this.alpha = 0.0f
    this.animate().setDuration(duration).alpha(1.0f).withEndAction {
        function?.let { function() }
    }
}

/**
 * Animates the view with fade out animation and then executes the retrieved function
 */
fun View.fadeOut(duration: Long = FADE_DURATION, function: (() -> Unit)?) {
    this.animate().setDuration(duration).alpha(0.0f).withEndAction {
        function?.let { function() }
    }
}

/**
 * Animates the view with a fall and fade in animation
 */
fun View.feathersAnimation(duration: Long = FEATHERS_DURATION) {
    this.alpha = 0.0f
    this.translationY = -100.0f
    this.animate().setDuration(duration).translationY(0.0f).alpha(1.0f)
}

/**
 * Animates the button with a quick loop in its size
 */
fun View.setOnClickListenerWithAnimation(duration: Long = BUTTON_DURATION, function: () -> Unit) {
    this.setOnClickListener {
        this.animate().setDuration(duration / 2).scaleX(0.95f).scaleY(0.95f).withEndAction {
            this.animate().setDuration(duration / 2).scaleX(1.0f).scaleY(1.0f).withEndAction {
                function()
            }
        }
    }
}

fun View.animateFocus(duration: Long){
    this.setOnFocusChangeListener { view, isFocused ->
        if (isFocused) view.flick(duration)
    }
}

fun View.flick(duration: Long){
    this.animate().setDuration(duration / 2).scaleX(0.95f).scaleY(0.95f).withEndAction {
        this.animate().setDuration(duration / 2).scaleX(1.0f).scaleY(1.0f)
    }
}

/**
 * Animates the button with a quick loop in its size
 */
fun View.setOnClickListenerWithSparkyAnimation(duration: Long, function: () -> Unit) {
    this.run {
        setOnClickListener {
            animate().setDuration(duration / 3).scaleX(0.6f).scaleY(0.6f).withEndAction {
                animate().setDuration(duration / 3).scaleX(1.8f).scaleY(1.8f).withEndAction {
                    animate().setDuration(duration / 3).scaleX(1.0f).scaleY(1.0f).withEndAction {
                        function()
                    }
                }
            }
        }
    }
}

const val BUTTON_DURATION = 200L
const val FEATHERS_DURATION = 600L
const val FADE_DURATION = 600L