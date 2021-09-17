package com.church.punithaanthoniyar.home.view

import android.R.attr
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.about.AboutActivity
import com.church.punithaanthoniyar.base.BaseFragment
import com.church.punithaanthoniyar.databinding.HomeFragmentBinding
import com.church.punithaanthoniyar.home.IHomeContract
import com.church.punithaanthoniyar.home.presenter.HomeFragmentPresenter
import com.church.punithaanthoniyar.todayprayer.TodayPrayerActivity
import com.opensooq.pluto.listeners.OnItemClickListener
import java.util.*
import android.speech.tts.Voice
import android.R.attr.x
import com.church.punithaanthoniyar.masstiming.MassTimingActivity
import com.church.punithaanthoniyar.saintofday.SaintOfDayActivity
import com.church.punithaanthoniyar.verseofday.VerseOfDayActivity
import java.lang.Exception


class HomeFragment : BaseFragment(),IHomeContract.IHomeViewContract,TextToSpeech.OnInitListener {

    private lateinit var binding:HomeFragmentBinding

    private var imageList = arrayListOf<String>()

    private lateinit var presenter : HomeFragmentPresenter

    private lateinit var adapter : SliderAdapter

    private lateinit var tts : TextToSpeech

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = HomeFragmentBinding.inflate(inflater,container,false)

        setScreenTitle(getString(R.string.home))

        presenter = HomeFragmentPresenter()

        presenter.setScreenView(this)

        tts = TextToSpeech(context,this,"com.google.android.tts")

        return binding.root
    }

    override fun init(view: View?) {
    }

    override fun initializeDi() {
    }

    override fun getMessageFromAliens() {

    }

    private val MY_DATA_CHECK_CODE = 1119

    override fun setUpViews() {
        val pluto = binding.sliderView
        adapter = SliderAdapter(imageList, object : OnItemClickListener<String> {
            override fun onItemClicked(item: String?, position: Int) {
            }
        })

        pluto.create(adapter, lifecycle = lifecycle)

        pluto.setCustomIndicator(binding.customIndicator)

        binding.prayerLayout.setOnClickListener {
            val mIntent = Intent(activity, TodayPrayerActivity::class.java)
            startActivity(mIntent)
        }

        binding.aboutLayout.setOnClickListener {
            val mIntent = Intent(activity, AboutActivity::class.java)
            startActivity(mIntent)
        }

        binding.verseLayout.setOnClickListener {
            val mIntent = Intent(activity, VerseOfDayActivity::class.java)
            startActivity(mIntent)
        }

        binding.saintLayout.setOnClickListener {
            val mIntent = Intent(activity, SaintOfDayActivity::class.java)
            startActivity(mIntent)
        }

        binding.massLayout.setOnClickListener {
            val mIntent = Intent(activity, MassTimingActivity::class.java)
            startActivity(mIntent)

//            speakOut()
//
//            for (tmpVoice in tts.voices) {
//                println("tmpVoice = $tmpVoice")
//            }
        }

        presenter.getImageListFromFirestore()
    }

    private fun getImages(): MutableList<Int> {
        val items = mutableListOf<Int>()
        items.add(R.drawable.img_a)
        items.add(R.drawable.img_b)
        items.add(R.drawable.img_c)
        return items
    }

    override fun updateImage(imageList: ArrayList<String>) {
        this.imageList.addAll(imageList)

        adapter.notifyDataSetChanged()
    }

    override fun onInit(status: Int) {
        if (status === TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.ENGLISH)

            if (result == TextToSpeech.LANG_MISSING_DATA
                || result == TextToSpeech.LANG_NOT_SUPPORTED
            ) {
                Log.e("TTS", "This Language is not supported")
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    private fun speakOut() {
        var text: String = getString(R.string.text_to_speech_dummy)

        tts.setPitch(0.8f)
        tts.setSpeechRate(0.8f)

        val voiceObj = Voice(
            "ta-IN-language",
            Locale.getDefault(), 400, 200, false, null
        )
        tts.voice = voiceObj

        text = text.replace("\\d".toRegex(), "")

        println("text = $text")

        tts.speak(text, TextToSpeech.QUEUE_ADD, null,TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID)
    }

    private fun getLang() : String{
        val sharedPrefFile = "languageSharedPref"
        val sharedPreferences: SharedPreferences = activity?.getSharedPreferences(sharedPrefFile,Context.MODE_PRIVATE)!!

        val lang = sharedPreferences.getString("Language","en")

        return lang.toString()
    }

}