package com.example.analogueclock.activity


import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.analogueclock.databinding.ActivitySettingsBinding
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape


class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private var mDefaultColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Settings"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#F57C00")))

        var sp: SharedPreferences? = null
        if (getSharedPreferences("myPref", MODE_PRIVATE) != null) {
            sp = getSharedPreferences("myPref", MODE_PRIVATE)
        }
        val editor = getSharedPreferences("myPref", MODE_PRIVATE).edit()

        if (sp != null) {
            try {
                binding.previewSelectedColorHour.setBackgroundColor(
                    Color.parseColor(
                        sp.getString(
                            "hourColor",
                            "#303F9F"
                        )
                    )
                )
            } catch (e: IllegalArgumentException) {
                binding.previewSelectedColorHour.setBackgroundColor(
                    sp.getString(
                        "hourColor",
                        "#303F9F"
                    )!!.toInt()
                )
            }
            try {
                binding.previewSelectedColorMinutes.setBackgroundColor(
                    Color.parseColor(
                        sp.getString(
                            "minutesColor",
                            "#FF4081"
                        )
                    )
                )
            } catch (e: IllegalArgumentException) {
                binding.previewSelectedColorMinutes.setBackgroundColor(
                    sp.getString("minutesColor", "#FF4081")!!.toInt()
                )
            }
            try {
                binding.previewSelectedColorSeconds.setBackgroundColor(
                    Color.parseColor(
                        sp.getString(
                            "secondsColor",
                            "#5D6CBC"
                        )
                    )
                )
            } catch (e: IllegalArgumentException) {
                binding.previewSelectedColorSeconds.setBackgroundColor(
                    sp.getString("secondsColor", "#5D6CBC")!!.toInt()
                )
            }
        }


        binding.setColorHourButton.setOnClickListener {
            ColorPickerDialog
                .Builder(this)                        // Pass Activity Instance
                .setTitle("Pick Theme")            // Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                .setDefaultColor(mDefaultColor)     // Pass Default Color
                .setColorListener { color, _ ->
                    mDefaultColor = color
                    binding.previewSelectedColorHour.setBackgroundColor(mDefaultColor)
                    editor.putString("hourColor", mDefaultColor.toString())
                    editor.apply()
                }
                .show()
        }

        binding.setColorButtonMinutes.setOnClickListener {
            ColorPickerDialog
                .Builder(this)                        // Pass Activity Instance
                .setTitle("Pick Theme")            // Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                .setDefaultColor(mDefaultColor)     // Pass Default Color
                .setColorListener { color, _ ->
                    mDefaultColor = color
                    binding.previewSelectedColorMinutes.setBackgroundColor(mDefaultColor)
                    editor.putString("minutesColor", mDefaultColor.toString())
                    editor.apply()
                }
                .show()
        }


        binding.setColorSecondsButton.setOnClickListener {
            ColorPickerDialog
                .Builder(this)                        // Pass Activity Instance
                .setTitle("Pick Theme")            // Default "Choose Color"
                .setColorShape(ColorShape.SQAURE)   // Default ColorShape.CIRCLE
                .setDefaultColor(mDefaultColor)     // Pass Default Color
                .setColorListener { color, _ ->
                    mDefaultColor = color
                    binding.previewSelectedColorSeconds.setBackgroundColor(mDefaultColor)
                    editor.putString("secondsColor", mDefaultColor.toString())
                    editor.apply()
                }
                .show()
        }
    }
}