package com.example.module.broadcast.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.broadcast.bean.Mdata
import com.example.module.broadcast.bean.MvData
import com.example.module.broadcast.bean.MvUrl
import com.example.module.broadcast.repository.GetMvdate
import com.example.module.broadcast.repository.Getsongurl

class MvdataViewModel:ViewModel() {

    private val _mvdata:MutableLiveData <List<Mdata>> = MutableLiveData()
     val mdata: LiveData<List<Mdata>>
         get() = _mvdata
fun getMvdata(id:Long){
    GetMvdate.getMvdata(id,_mvdata)


}
}