# Android Sample + Base Project
BaseFragment.kt
- DataBindingUtil을 사용 하는 Fragment abstract class

BindingViewHolder.kt
- DataBindingUtil을 사용 하는 RecyclerView.ViewHolder abstract class

Progress.kt
- LoadingProgress Utils

SharedPreferenceUtil.kt
- SharedPreference 사용 Utils
```kotlin
object SharedData : SharedPreferenceUtil(Define.TAG) {
    var tempValue by stringPref("tempValue", "")
}
SharedData.tempValue = "bar foo"
```

SingleLiveEvent.kt
- [LiveData with SnackBar, Navigation and other events (the SingleLiveEvent case)](https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150)

Toast.kt
- Toast Utils

/extension
- kotlin extensions
