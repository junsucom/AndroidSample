package com.junsu.sample.di

import androidx.databinding.ObservableArrayList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.storage.FirebaseStorage
import com.junsu.sample.ui.list.GalleryViewModel
import kr.co.mtcom.iclip.Define
import kr.co.mtcom.iclip.manager.RecordingManager
import kr.co.mtcom.iclip.model.ContentHelper
import kr.co.mtcom.iclip.model.DeviceHelper
import kr.co.mtcom.iclip.model.NoticeHelper
import kr.co.mtcom.iclip.model.RedeemsHelper
import kr.co.mtcom.iclip.model.UploadContent
import kr.co.mtcom.iclip.model.UserHelper
import kr.co.mtcom.iclip.model.UserInfoHelper
import kr.co.mtcom.iclip.model.UsersHelper
import kr.co.mtcom.iclip.store.ContentList
import kr.co.mtcom.iclip.ui.audioPreview.AudioPreviewViewModel
import kr.co.mtcom.iclip.ui.bookmark.BookmarkEditViewModel
import kr.co.mtcom.iclip.ui.detail.DetailViewModel
import kr.co.mtcom.iclip.ui.imageeditor.ImageEditorModel
import kr.co.mtcom.iclip.ui.kakologin.KaKaoLoginViewModel
import kr.co.mtcom.iclip.ui.main.MainViewModel
import kr.co.mtcom.iclip.ui.record.RecordViewModel
import kr.co.mtcom.iclip.ui.resetpassword.ResetPasswordViewModel
import kr.co.mtcom.iclip.ui.setting.SettingMainViewModel
import kr.co.mtcom.iclip.ui.setting.display.DisplayViewModel
import kr.co.mtcom.iclip.ui.setting.howtouse.UseViewModel
import kr.co.mtcom.iclip.ui.setting.individual.ChangeNewPasswordViewModel
import kr.co.mtcom.iclip.ui.setting.individual.ChangePasswordResetViewModel
import kr.co.mtcom.iclip.ui.setting.individual.ChangePasswordViewModel
import kr.co.mtcom.iclip.ui.setting.individual.IndividualViewModel
import kr.co.mtcom.iclip.ui.setting.individual.UnRegisterViewModel
import kr.co.mtcom.iclip.ui.setting.inform.InformDetailViewModel
import kr.co.mtcom.iclip.ui.setting.inform.InformViewModel
import kr.co.mtcom.iclip.ui.setting.inquiry.InquiryListViewModel
import kr.co.mtcom.iclip.ui.setting.inquiry.InquiryViewModel
import kr.co.mtcom.iclip.ui.setting.notification.NotificationViewModel
import kr.co.mtcom.iclip.ui.setting.storage.PurchaseViewModel
import kr.co.mtcom.iclip.ui.setting.storage.RedeemViewModel
import kr.co.mtcom.iclip.ui.setting.storage.StorageViewModel
import kr.co.mtcom.iclip.ui.setting.version.VersionViewModel
import kr.co.mtcom.iclip.ui.signin.SignInViewModel
import kr.co.mtcom.iclip.ui.signup.SignUpViewModel
import kr.co.mtcom.iclip.ui.splash.PreloadModel
import kr.co.mtcom.iclip.ui.tag.TagSharedViewModel
import kr.co.mtcom.iclip.ui.tag.TagViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single { () }
    single { FirebaseFirestore.getInstance() }
    single(named(Define.UPLOAD_CONTENTS_ID)) { ObservableArrayList<UploadContent>() }
    single { RecordingManager() }

    viewModel { GalleryViewModel() }
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
