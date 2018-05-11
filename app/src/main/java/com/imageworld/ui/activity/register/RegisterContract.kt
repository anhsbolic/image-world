package com.imageworld.ui.activity.register

interface RegisterContract {

    interface View {
        fun showProgress()

        fun hideProgress()

        fun backToLogin()
    }

    interface Presenter {
        fun register()

        fun login()
    }

}