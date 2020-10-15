package com.example.doasamagra.surahplayer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class SurahPlayerViewModel : ViewModel() {
    private val surahStorageReference: StorageReference = FirebaseStorage.getInstance().reference
    private val surahCollection =
        FirebaseFirestore.getInstance().collection("surah")
    private val _surahs = MutableLiveData<List<Surah>>()
    val surahs: LiveData<List<Surah>> = _surahs

    fun loadData() {
        surahCollection.orderBy("title").get()
            .addOnSuccessListener { documents ->
                Log.d("SurahViewModel", "Successfully get data")
                Log.d("SurahViewModel", "Document Size ${documents.size()} ")
                _surahs.value = documents.toObjects(Surah::class.java)
                val list = _surahs.value
                list?.forEach {surah->
                    Log.d("Surah", surah.title)
                    Log.d("Surah", surah.gsURL)
                }
            }.addOnFailureListener { e ->
                Log.d("SurahViewModel", "There is some problems loading the data")
            }
    }
}
