package com.carsales.covid.Activitys

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.carsales.covid.Functions.OwnFunctions
import com.carsales.covid.R
import com.carsales.covid.ViewModels.ViewModel
import com.carsales.covid.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //contains all the observers in the activity
        observersActivity()

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        //get year,month and day
        val y = calendar.get(Calendar.YEAR)
        val m = calendar.get(Calendar.MONTH)
        val d = calendar.get(Calendar.DAY_OF_MONTH)-1

        setFechaText(calendar)

        //loading visible until first load (day-1)
        viewModel.appLoadingLiveData.postValue(View.VISIBLE)
        viewModel.getApiCovidByDate(OwnFunctions().fixDateCalendar(y,m,d))

        binding.btnDate.setOnClickListener {
            viewModel.appLoadingLiveData.postValue(View.VISIBLE)
            val pickerDialog = DatePickerDialog(this)
            pickerDialog.show()

            //get date and call the service covid
            pickerDialog.setOnDateSetListener { datePicker, y, m, d ->
                val calen : Calendar = Calendar.getInstance()
                calen.set(y,m,d)
                setFechaText(calen)
                viewModel.getApiCovidByDate(OwnFunctions().fixDateCalendar(y,m,d-1))
            }

            //cancel button loadingBackground hide
            pickerDialog.setOnCancelListener(DialogInterface.OnCancelListener {
                viewModel.appLoadingLiveData.postValue(View.GONE)
            })

        }

    }

    fun setFechaText(calendar:Calendar){
        val simpleDate = SimpleDateFormat("d 'de' MMMM 'del' yyyy")
        binding.tvFecha.text = simpleDate.format(calendar.time)

    }

    private fun observersActivity(){
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        //change the visibility of the appLoading
        viewModel.appLoadingLiveData.observe(this) {
            val alpha = if(it==View.GONE) 0.0f else 1.0f
            OwnFunctions().animationVisibleGone(binding.appLoading.layout,it,alpha,400)
        }

        //change the values of the textViews
        viewModel.covidDateLiveData.observe(this, androidx.lifecycle.Observer { covidDateModel ->

            viewModel.appLoadingLiveData.postValue(View.GONE)
            covidDateModel?.let {

                binding.tvFecha.visibility = View.GONE
                binding.tvCasosConfirmados.visibility = View.GONE
                binding.tvCantidadFallecidos.visibility = View.GONE

                binding.tvFecha.visibility = View.VISIBLE
                binding.tvFecha.animation = AnimationUtils.loadAnimation(this, R.anim.item_animation_fall_down)

                binding.tvCasosConfirmados.text = String.format("%s: %s",getString(R.string.casos_confirmados),it.data.confirmed.toString())
                binding.tvCasosConfirmados.visibility = View.VISIBLE
                binding.tvCasosConfirmados.animation = AnimationUtils.loadAnimation(this, R.anim.item_animation_fall_down)

                binding.tvCantidadFallecidos.text =String.format("%s: %s",getString(R.string.cantidad_de_personas_fallecidas),it.data.deaths.toString())
                binding.tvCantidadFallecidos.visibility = View.VISIBLE
                binding.tvCantidadFallecidos.animation = AnimationUtils.loadAnimation(this, R.anim.item_animation_fall_down)

            }?: run {
                Toast.makeText(this@MainActivity,"Fecha sin datos",Toast.LENGTH_LONG)
            }

        })
    }
}