package com.delaroystudios.vpd.users

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.delaroystudios.domain.model.Users
import com.delaroystudios.vpd.MainActivity
import com.delaroystudios.vpd.databinding.UsersDetailsBinding
import com.delaroystudios.vpd.databinding.UsersListFragmentBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

private const val PERMISSIONS_REQUEST_CODE = 10
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE
)

@AndroidEntryPoint
class UsersDetails : Fragment(), DialogInterface.OnClickListener {

    private lateinit var binding: UsersDetailsBinding
    var selected = 0
    private lateinit var mProfileUri: Uri
    private lateinit var mCameraUri: Uri
    private var pictureUri: String = ""
    private var usersId = 0
    private val viewModel: UsersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = UsersDetailsBinding.inflate(inflater, container, false)

        if (arguments != null) {
            val value = arguments?.getParcelable<Users>("data")
            binding.name.text = value!!.name
            binding.username.text = value.username
            binding.email.text = value.email
            binding.phone.text = value.phone
            binding.website.text = value.website
            usersId = value.id

            if (value.imageUri.isNotEmpty()) {
                Glide.with(requireActivity())
                    .load(value.imageUri)
                    .into(binding.profileImage)
            }

        }

        binding.profileImage.setOnClickListener {
            if (!hasPermissions(requireContext())) {
                // Request camera-related permissions
                requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
            } else {
                // If permissions have already been granted, proceed
                openDialog()
            }
        }

        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

        return binding.root

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
                // Take the user to the success fragment when permission is granted
                Toast.makeText(context, "Permission request granted", Toast.LENGTH_LONG).show()
                openDialog()
            } else {
                Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        private const val PROFILE_IMAGE_REQ_CODE = 101
        private const val CAMERA_IMAGE_REQ_CODE = 103
        const val CUSTOMER = "customer"
        /** Convenience method used to check if all permissions required by this app are granted */
        fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun openDialog() {
        val alertDialog =
            AlertDialog.Builder(context)
        alertDialog.setTitle("Take a Photo")
        val items = arrayOf(
            "Camera",
            "Gallery"
        )
        val checkedItem = 0
        selected = 0
        alertDialog.setSingleChoiceItems(items, checkedItem, this)
        alertDialog.setPositiveButton("Ok", this)
        alertDialog.setNegativeButton("Cancel", this)
        val alert = alertDialog.create()
        alert.setCanceledOnTouchOutside(false)
        alert.show()
    }

    override fun onClick(dialog: DialogInterface?, id: Int) {
        when {
            id == 0 -> {
                selected = 0
            }
            id == 1 -> {
                selected = 1
            }
            id == DialogInterface.BUTTON_POSITIVE -> {
                if (selected == 0) {
                   pickCameraImage()
                } else if (selected == 1) {
                    pickProfileImage()
                }
            }
            id != DialogInterface.BUTTON_NEGATIVE -> {
                // There are only four buttons, this should not happen
            }
        }
    }

    @Suppress("UNUSED_PARAMETER")
    fun pickProfileImage() {
        ImagePicker.with(this)
            // Crop Square image
            .galleryOnly()
            .cropSquare()
            .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
                Log.d("ImagePicker", "Selected ImageProvider: " + imageProvider.name)
            }
            .setDismissListener {
                Log.d("ImagePicker", "Dialog Dismiss")
            }
            // Image resolution will be less than 512 x 512
            .maxResultSize(512, 512)
            .start(PROFILE_IMAGE_REQ_CODE)
    }

    @Suppress("UNUSED_PARAMETER")
    fun pickCameraImage() {
        ImagePicker.with(this)
            // User can only capture image from Camera
            .cameraOnly()
            .cropSquare()
            // Image size will be less than 1024 KB
            .compress(500)
            .saveDir(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
            .start(CAMERA_IMAGE_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            // Uri object will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            when (requestCode) {
                PROFILE_IMAGE_REQ_CODE -> {
                    mProfileUri = uri
                    //Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show()
                    pictureUri = uri.toString()
                    Glide.with(this)
                        .load(pictureUri)
                        .into(binding.profileImage)
                    viewModel.updateImageuri(imageUri = pictureUri, id = usersId )
                }

                CAMERA_IMAGE_REQ_CODE -> {
                    Toast.makeText(context, "Image is compressing, please wait till finished", Toast.LENGTH_LONG).show()
                    mCameraUri = uri
                    //binding.progress.setVisibility(View.VISIBLE)
                    pictureUri = uri.toString()
                    Glide.with(this)
                        .load(pictureUri)
                        .into(binding.profileImage)
                    viewModel.updateImageuri(imageUri = pictureUri, id = usersId )
                }
            }

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else  {
            Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()

        }
    }
}