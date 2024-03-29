package com.example.tests.storage

import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tests.R
import com.example.tests.databinding.GalleryBinding
import com.example.tests.fundamentos.recycle_view.ExternalStorageAdapter
import com.example.tests.fundamentos.recycle_view.InternalStorageAdapter
import com.example.tests.fundamentos.recycle_view.OnItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.UUID

class Gallery : AppCompatActivity(), OnItemClickListener {
    private val binding: GalleryBinding by lazy { GalleryBinding.inflate(layoutInflater) }

    private lateinit var internalStorageAdapter: InternalStorageAdapter
    private lateinit var externalStorageAdapter: ExternalStorageAdapter

    private fun hasCameraPermissionGranted() =
        ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private val cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted->
        if (isGranted)
            takePicture.launch(null)
    }

    inner class RecyclerViewMarginPhoto: RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.apply {
                top = 8
                right = 8
                bottom = 8
                left = 8
            }
        }
    }

    //@RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ibTakePhoto.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !binding.switchPrivatePublic.isChecked) {
                permissions.launch(
                    arrayOf(
                        android.Manifest.permission.READ_MEDIA_IMAGES,
                        android.Manifest.permission.READ_MEDIA_AUDIO,
                        android.Manifest.permission.READ_MEDIA_VIDEO,
                        android.Manifest.permission.CAMERA
                    )
                )
            }else {
                cameraPermission.launch(android.Manifest.permission.CAMERA)
            }
        }

        lifecycleScope.launch {
            loadInternalStoragePhotosIntoRecyclerView()
            loadExternalStoragePhotoIntoRecyclerView()
        }

        val ivLayout = LayoutInflater.from(this).inflate(R.layout.gallery_item, null)
        ivLayout.findViewById<ImageView>(R.id.ivPhotoTest)
        registerForContextMenu(ivLayout)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menu?.apply {
            add(R.string.share).setOnMenuItemClickListener {
                Toast.makeText(this@Gallery, "Shared", Toast.LENGTH_LONG).show()
                true
            }

            add(R.string.delete).setOnMenuItemClickListener {
                Toast.makeText(this@Gallery, "Deleted", Toast.LENGTH_LONG).show()
                true
            }
        }
    }
    /*
        Permissions
     */

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
        val isPrivate = binding.switchPrivatePublic.isChecked

        val isSavedSuccessfully = when{
            isPrivate -> savePhotoToInternalStorage(UUID.randomUUID().toString(), it!!)
            hasCameraPermissionGranted() -> savePhotoToExternalStorage(UUID.randomUUID().toString(), it!!)
            else -> false
        }

        lifecycleScope.launch {
            if (isPrivate)
                loadInternalStoragePhotosIntoRecyclerView()
            else
                loadExternalStoragePhotoIntoRecyclerView()
        }

        if (isSavedSuccessfully)
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "Failed to Save", Toast.LENGTH_LONG).show()
    }

    private val permissions = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permission ->
        permission.entries.forEach {
            val permissionName = it.key
            val isGranted = it.value

            if (permissionName == android.Manifest.permission.CAMERA && isGranted){
                takePicture.launch(null)
            }
        }
    }

    private suspend fun loadInternalStoragePhotosIntoRecyclerView(){
        withContext(Dispatchers.IO) {
            lifecycleScope.launch {

                internalStorageAdapter = InternalStorageAdapter(loadPhotoFromInternalStorage(), this@Gallery, this@Gallery)

                binding.rvPrivatePhotos.apply {
                    this.adapter = internalStorageAdapter
                    layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
                    addItemDecoration(RecyclerViewMarginPhoto())
                }
            }
        }
    }

    private fun loadExternalStoragePhotoIntoRecyclerView(){
        lifecycleScope.launch {
            externalStorageAdapter = ExternalStorageAdapter(loadExternalPhotos(), this@Gallery, this@Gallery)

            binding.rvSharedPhotos.apply {
                this.adapter = externalStorageAdapter
                layoutManager = StaggeredGridLayoutManager(3,RecyclerView.VERTICAL)
                addItemDecoration(RecyclerViewMarginPhoto())
            }
        }
    }

    private fun savePhotoToInternalStorage(fileName: String, bitmap: Bitmap): Boolean{
        return try {
            openFileOutput("$fileName.jpg", MODE_PRIVATE).use {
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 98, it))
                    throw IOException("Could not save the photo!")
            }
            true
        }catch (e: IOException){
            e.printStackTrace()
            false
        }
    }

    private suspend fun loadPhotoFromInternalStorage(): MutableList<InternalStoragePhoto>{
        return withContext(Dispatchers.IO){
            val files = filesDir.listFiles()
            files?.filter { it.canRead() && it.isFile && it.path.endsWith(".jpg") }?.map { it1->
                val bytes = it1.readBytes()
                val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it1.name, bitmap)
            }?.toMutableList() ?: mutableListOf()
        }
    }

    private fun deletePhotoFromInternalStorage(fileName: String): Boolean{
        return try {
            deleteFile(fileName)
        }catch (e: IOException){
            e.printStackTrace()
            false
        }
    }

    /*
    External Storage
     */

    private fun savePhotoToExternalStorage(displayName: String, bitmap: Bitmap): Boolean {
        val imageCollection = sdk29AndUp {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        }?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "$displayName.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.WIDTH, bitmap.width)
            put(MediaStore.Images.Media.HEIGHT, bitmap.height)
        }

        return try {
            contentResolver.insert(imageCollection, contentValues).also {
                contentResolver.openOutputStream(it!!).use { os->
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, os!!))
                        throw IOException("Could not save Image To External Storage")
                }
            }?:false
            true
        }catch (e: IOException){
            e.printStackTrace()
            false
        }
    }

    private suspend fun loadExternalPhotos(): MutableList<ExternalStorage> {
        return withContext(Dispatchers.IO){
            val collection = sdk29AndUp {
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
            }?: MediaStore.Images.Media.EXTERNAL_CONTENT_URI

            val projection = arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT
            )

            val photos = mutableListOf<ExternalStorage>()

            contentResolver.query(
                collection,
                projection,
                null,
                null,
                "${MediaStore.Images.Media._ID} DESC"
            )?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val displayNameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val widthColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
                val heightColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)

                while (it.moveToNext()){
                    val id = it.getLong(idColumn)
                    val displayName = it.getString(displayNameColumn)
                    val width = it.getInt(widthColumn)
                    val height = it.getInt(heightColumn)
                    val contentUris = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id
                    )
                    photos.add(ExternalStorage(id, displayName, width, height, contentUris))
                }
                photos
            }?: mutableListOf()
        }
    }

    override fun showUri(position: Int) {
        lifecycleScope.launch {
            val uri = loadExternalPhotos()[position].contentUri.toString()

            Toast.makeText(this@Gallery, uri, Toast.LENGTH_LONG).show()
        }
    }

    override fun sharePhoto(position: Int) {
        lifecycleScope.launch {
            val path = loadExternalPhotos()[position].contentUri

            val share = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, path)
                putExtra(Intent.EXTRA_SUBJECT, "Receive the Photo")
                type = "image/*"
            }
            startActivity(Intent.createChooser(share, "Share"))
        }
    }

    override fun onImageClicked(position: Int) {
        lifecycleScope.launch {
            val clickedImage = loadPhotoFromInternalStorage()[position]
            val isDeleted = deletePhotoFromInternalStorage(clickedImage.fileName)
            if (isDeleted) {
                Toast.makeText(this@Gallery, "Deleted", Toast.LENGTH_LONG).show()
            }else
                Toast.makeText(this@Gallery, "Impossible to delete", Toast.LENGTH_LONG).show()
        }
    }
}