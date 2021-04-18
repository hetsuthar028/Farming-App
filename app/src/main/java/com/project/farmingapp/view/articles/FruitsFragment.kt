package com.project.farmingapp.view.articles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.RotateAnimation
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.project.farmingapp.R
import com.project.farmingapp.utilities.hide
import com.project.farmingapp.utilities.show
import com.project.farmingapp.viewmodel.ArticleListener
import com.project.farmingapp.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_fruits.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FruitsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class FruitsFragment : Fragment(), ArticleListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private lateinit var viewModel: ArticleViewModel
    private var param2: String? = null
    private var param3: String? = null
    val desc = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString("name")
        }

        viewModel = ViewModelProviders.of(requireActivity())
            .get<ArticleViewModel>(ArticleViewModel::class.java)


//        viewModel.message1.observe(viewLifecycleOwner, Observer {
//            Log.d("FruitFrag1", it.toString())
//        })

        Toast.makeText(activity!!.applicationContext, "To Load " + tag, Toast.LENGTH_SHORT)
            .show()

        val tag = this.tag.toString()
//
//        if (viewModel.message1.value.isNullOrEmpty()){
//            Log.d("I'm Called", "No")
//        }

//        viewModel.getMyArticle("${tag.toLowerCase()}")

        Log.d("I'm called 2", viewModel.message3.value.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel.getMyArticle("${this.tag}")

//        val progressBar = findViewById(R.id.progressArticle);

//        val newData = viewModel.message3.value
//
//        Log.d("New data length", newData!!.size.toString())
//        val newDataLength = newData!!.size
//
//        for (a in 0 until newDataLength){
//            if (newData[a].data!!.get("title") == this.tag){
//
//
//
//                var data = newData[a].data
//
//
//                val attributes: Map<String, String> = data!!.get("attributes") as Map<String, String>
//                val desc = data!!.get("description").toString()
//                Log.d("I'm Called3", attributes.get("Temperature").toString())
//
//                Log.d("I'm Called", "Yes")
//                val diseases: List<String> = data!!.get("diseases") as List<String>
////            Log.d("Diseases", diseases.toString())
//                Log.d("Diseases2", data!!.get("diseases").toString())
//                tempTextFruitFragArt.text = "s"
//                monthTextFruitFragArt.text = attributes.get("Time").toString()
//
//                titleTextFruitFragArt.text = data!!.get("title").toString()
//                descTextValueFruitFragArt.text = desc
//                processTextValueFruitFragArt.text = data!!.get("process").toString()
//                soilTextValueFruitFragArt.text = data!!.get("soil").toString()
//                stateTextValueFruitFragArt.text = data!!.get("state").toString()
//
//                val images: List<String> = data!!.get("images") as List<String>
//                Glide.with(this)
//                    .load(images[0])
//                    .into(imageFruitFragArt)
//
//                attr1ValueFruitFragArt.text = attributes.get("Weight").toString()
//                attr2ValueFruitFragArt.text = attributes.get("Vitamins").toString()
//                attr3ValueFruitFragArt.text = attributes.get("Tree Height").toString()
//                attr4ValueFruitFragArt.text = attributes.get("growthTime").toString()
//
//                diseaseTextValueFruitFragArt.text = ""
//                for (i in 0..diseases.size - 1) {
//
//                    diseaseTextValueFruitFragArt.text =
//                        diseaseTextValueFruitFragArt.text.toString() +
//                                (i + 1).toString() + ". " + diseases[i].toString() + "\n"
//                }
//
//
//
//
//            }
//        }

        viewModel.message1.observe(viewLifecycleOwner, Observer {
            progressArticle.show()
//            Log.d("FruitFrag1", it.toString())
            val attributes: Map<String, String> = it.get("attributes") as Map<String, String>
            val desc = it.get("description").toString()


            Log.d("I'm Called", "Yes")
            val diseases: List<String> = it.get("diseases") as List<String>
//            Log.d("Diseases", diseases.toString())
            Log.d("Diseases2", it.get("diseases").toString())
            tempTextFruitFragArt.text = attributes.get("Temperature").toString()
            monthTextFruitFragArt.text = attributes.get("Time").toString()

            titleTextFruitFragArt.text = it.get("title").toString()
            descTextValueFruitFragArt.text = desc
            processTextValueFruitFragArt.text = it.get("process").toString()
            soilTextValueFruitFragArt.text = it.get("soil").toString()
            stateTextValueFruitFragArt.text = it.get("state").toString()

            val images: List<String> = it.get("images") as List<String>
            Glide.with(this)
                .load(images[0])
                .into(imageFruitFragArt)

            attr1ValueFruitFragArt.text = attributes.get("Weight").toString()
            attr2ValueFruitFragArt.text = attributes.get("Vitamins").toString()
            attr3ValueFruitFragArt.text = attributes.get("Tree Height").toString()
            attr4ValueFruitFragArt.text = attributes.get("growthTime").toString()

            diseaseTextValueFruitFragArt.text = ""
            for (i in 0..diseases.size - 1) {

                diseaseTextValueFruitFragArt.text =
                    diseaseTextValueFruitFragArt.text.toString() +
                            (i + 1).toString() + ". " + diseases[i].toString() + "\n"
            }
//            diseaseTextValueFruitFragArt.text = "\n" + diseases[diseases.size - 1].toString()
            progressArticle.hide()
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fruits, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FruitsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FruitsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Articles"

        val params = descTextTitleFruitFragArt.layoutParams

        var toggle = 0

//        progressArticle.indeterminateDrawable.setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);

        descToggleBtnFruitFragArt.setOnClickListener {

            if (toggle == 0) {
                descTextValueFruitFragArt.maxLines = Integer.MAX_VALUE
                toggle = 1
//                descToggleBtnFruitFragArt.rotation = 180f
                val rotateAnim = RotateAnimation(
                    0.0f, 180f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f
                )
                rotateAnim.duration = 2
                rotateAnim.fillAfter = true
                descToggleBtnFruitFragArt.startAnimation(rotateAnim)
            } else if (toggle == 1) {
                descTextValueFruitFragArt.maxLines = 3
                toggle = 0
//                descToggleBtnFruitFragArt.rotation = 0f
                val rotateAnim = RotateAnimation(
                    180f, 0f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f
                )
                rotateAnim.duration = 2
                rotateAnim.fillAfter = true
                descToggleBtnFruitFragArt.startAnimation(rotateAnim)

            }
        }


        // New

        val newData = viewModel.message3.value

        Log.d("New data length", newData!!.size.toString())
        val newDataLength = newData!!.size

        for (a in 0 until newDataLength) {
            if (newData[a].data!!.get("title") == this.tag) {


                var data = newData[a].data


                val attributes: Map<String, String> =
                    data!!.get("attributes") as Map<String, String>
                val desc = data!!.get("description").toString()
                Log.d("I'm Called3", attributes.get("Temperature").toString())

                Log.d("I'm Called", "Yes")
                val diseases: List<String> = data!!.get("diseases") as List<String>
//            Log.d("Diseases", diseases.toString())
                Log.d("Diseases2", data!!.get("diseases").toString())
                tempTextFruitFragArt.text = attributes.get("Temperature").toString()
                monthTextFruitFragArt.text = attributes.get("Time").toString()

                titleTextFruitFragArt.text = data!!.get("title").toString()
                descTextValueFruitFragArt.text = desc
                processTextValueFruitFragArt.text = data!!.get("process").toString()
                soilTextValueFruitFragArt.text = data!!.get("soil").toString()
                stateTextValueFruitFragArt.text = data!!.get("state").toString()

                val images: List<String> = data!!.get("images") as List<String>
                Glide.with(this)
                    .load(images[0])
                    .into(imageFruitFragArt)

                attr1ValueFruitFragArt.text = attributes.get("Weight").toString()
                attr2ValueFruitFragArt.text = attributes.get("Vitamins").toString()
                attr3ValueFruitFragArt.text = attributes.get("Tree Height").toString()
                attr4ValueFruitFragArt.text = attributes.get("growthTime").toString()

                diseaseTextValueFruitFragArt.text = ""
                for (i in 0..diseases.size - 1) {

                    diseaseTextValueFruitFragArt.text =
                        diseaseTextValueFruitFragArt.text.toString() +
                                (i + 1).toString() + ". " + diseases[i].toString() + "\n"
                }
                progressArticle.hide()
            }
        }


//        randomFruit.setOnClickListener {
//            val hash = hashMapOf<String, Any>(
//                "ss" to "ss"
//            )
////            viewModel.getMyArticle("apple")
//        }
    }

    override fun onStarted() {
        TODO("Not yet implemented")
    }

    override fun onSuccess(authRepo: LiveData<String>) {
        authRepo.observe(viewLifecycleOwner, Observer {
            Log.d("Fruit", "Success")
        })
    }

    override fun onFailure(message: String) {
        TODO("Not yet implemented")
    }
}