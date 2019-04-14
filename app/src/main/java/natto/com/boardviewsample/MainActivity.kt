package natto.com.boardviewsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val frame: FrameLayout by lazy { findViewById<FrameLayout>(R.id.board) }

    val list: ArrayList<BlockEntity> by lazy { ArrayList<BlockEntity>() }
    val point: Point by lazy { Point() }
    val centerPoint: Point by lazy { Point() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        frame.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> startTouch(motionEvent)
                MotionEvent.ACTION_UP -> endTouch(motionEvent)
                MotionEvent.ACTION_MOVE -> moveTouch(motionEvent)
                else -> true
            }
        }

        for (i in 0..10) {
            for (j in 0..10) {
                create((i * 500).toFloat(), (j * 500).toFloat(), "$i,$j")
            }
        }

    }

    fun startTouch(motionEvent: MotionEvent): Boolean {
        point.x = motionEvent.x
        point.y = motionEvent.y
        return true
    }

    fun endTouch(motionEvent: MotionEvent): Boolean {
        return true
    }

    fun moveTouch(motionEvent: MotionEvent): Boolean {
        draw(motionEvent.x - point.x, motionEvent.y - point.y)
        point.x = motionEvent.x
        point.y = motionEvent.y
//        Log.d("POINT_LOG","x:${point.x} , y:${point.y}")
        return true
    }

    fun draw(x: Float, y: Float) {
        list.forEach {
            if (it.block.text.equals("0,0")) {
                Log.d("POINT_LOG", "x:${it.view.x} , y:${it.view.y}")
            }
            if (it.view.x < 1500 &&
                it.view.x > -500f &&
                it.view.y < 3000 &&
                it.view.y > -500f
            ) {
                it.isVisible = true
                it.view.visibility = View.VISIBLE
                if (it.isVisible) {

                }
            } else {
                it.isVisible = false
                it.view.visibility = View.GONE
//                frame.removeView(it.view)
            }
            it.view.translationX += x
            it.view.translationY += y
        }
    }

    fun create(x: Float, y: Float, text: String) {
        val blockView = BlockView(this)
        blockView.findViewById<TextView>(R.id.block_text).text = text
        blockView.translationX = x
        blockView.translationY = y
        frame.addView(blockView)

        val block = Block(text, Point(x, y))
        list.add(BlockEntity(block, blockView, true))
    }
}
