package com.eclipsa.fade.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eclipsa.fade.repository.BaseRepository
import com.eclipsa.fade.ui.fragment.booking_list.BookingListRepository
import com.eclipsa.fade.ui.fragment.booking_list.BookingListViewModel
import com.eclipsa.fade.ui.fragment.chat_list.ChatMessageRepository
import com.eclipsa.fade.ui.fragment.chat_list.ChatMessageViewModel
import com.eclipsa.fade.ui.fragment.dashboard.HomeRepository
import com.eclipsa.fade.ui.fragment.dashboard.HomeViewModel
import com.eclipsa.fade.ui.fragment.favourite_list.FavouriteRepository
import com.eclipsa.fade.ui.fragment.favourite_list.FavouriteViewModel
import com.eclipsa.fade.ui.fragment.new_dashboard.NewDashboardRepository
import com.eclipsa.fade.ui.fragment.new_dashboard.NewDashboardViewModel
import com.eclipsa.fade.ui.fragment.profile.ProfileFragmentRepository
import com.eclipsa.fade.ui.fragment.profile.ProfileViewModel

class ViewModelFactory(private val repository: BaseRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository as HomeRepository) as T
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> ProfileViewModel(repository as ProfileFragmentRepository) as T
            modelClass.isAssignableFrom(ChatMessageViewModel::class.java) -> ChatMessageViewModel(repository as ChatMessageRepository) as T
            modelClass.isAssignableFrom(BookingListViewModel::class.java) -> BookingListViewModel(repository as BookingListRepository) as T
            modelClass.isAssignableFrom(NewDashboardViewModel::class.java) -> NewDashboardViewModel(repository as NewDashboardRepository) as T
            modelClass.isAssignableFrom(FavouriteViewModel::class.java) -> FavouriteViewModel(repository as FavouriteRepository) as T
            else -> throw IllegalAccessException("ViewModel Class Not Found!")
        }
    }

}