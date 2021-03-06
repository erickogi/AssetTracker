package com.assettrack.assettrack.Adapters

import android.content.Context
import android.os.Bundle
import android.support.annotation.IntRange
import android.support.v4.app.FragmentManager

import com.assettrack.assettrack.Views.Engineer.NewAsset.InstallationSteps.InstallationStepFour
import com.assettrack.assettrack.Views.Engineer.NewAsset.InstallationSteps.InstallationStepOne
import com.assettrack.assettrack.Views.Engineer.NewAsset.InstallationSteps.InstallationStepThree
import com.assettrack.assettrack.Views.Engineer.NewAsset.InstallationSteps.InstallationStepTwo
import com.stepstone.stepper.Step
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter
import com.stepstone.stepper.viewmodel.StepViewModel

class InstallationStepsAdapter(fm: FragmentManager, context: Context) : AbstractFragmentStepAdapter(fm, context) {

    override fun createStep(position: Int): Step? {
        when (position) {
            0 -> {
                val step1 = InstallationStepOne()
                val b1 = Bundle()
                b1.putInt(CURRENT_STEP_POSITION_KEY, position)

                step1.arguments = b1
                return step1
            }
            1 -> {
                val step2 = InstallationStepTwo()
                val b2 = Bundle()
                b2.putInt(CURRENT_STEP_POSITION_KEY, position)

                step2.arguments = b2
                return step2
            }
            2 -> {
                val step3 = InstallationStepThree()
                val b3 = Bundle()
                b3.putInt(CURRENT_STEP_POSITION_KEY, position)


                return step3
            }

            3 -> {
                val step4 = InstallationStepFour()
                val b4 = Bundle()
                b4.putInt(CURRENT_STEP_POSITION_KEY, position)


                return step4
            }
        }
        return null
    }

    override fun getCount(): Int {
        return 4
    }

    override fun getViewModel(@IntRange(from = 0) position: Int): StepViewModel {
        //Override this method to set Step title for the Tabs, not necessary for other stepper types
        when (position) {
            0 -> return StepViewModel.Builder(context)

                    .setTitle("Asset Details ") //can be a CharSequence instead
                    .create()
            1 -> return StepViewModel.Builder(context)
                    .setTitle("Customer Details") //can be a CharSequence instead
                    .create()
            2 -> return StepViewModel.Builder(context)
                    .setTitle("Location Details") //can be a CharSequence instead
                    .create()
            3 -> return StepViewModel.Builder(context)
                    .setTitle("Final Step") //can be a CharSequence instead
                    .create()
        }
        return StepViewModel.Builder(context).create()
    }

    companion object {
        private val CURRENT_STEP_POSITION_KEY = "messageResourceId"
    }


}
