import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.module.search.bean.Search
import com.example.module.search.bean.Song
import com.example.module.search.repository.SongRepository

class SongViewModel : ViewModel() {

    private val _songData: MutableLiveData<List<Song>> = MutableLiveData()
    val songData: LiveData<List<Song>>
        get() = _songData


    //得到单曲的信息
    fun getSongInfo(keyword: String) {
      SongRepository.SearchSongs(keyword,_songData)

    }


}