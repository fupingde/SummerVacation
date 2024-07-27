这是一个由卢高山和吴瑞联合开发的app。我负责的模块是：**module_login(登录注册模块) ， module_search(搜索模块)，module_broadcast(Mv播放模块),module_albums(专辑详情模块),module_singer(歌手详情模块)**,还有首页的推荐页面，还有"我的"页面的布局设计，开局的广告插入等。



![video_20240727_155609_edit 00_00_00-00_00_30](https://github.com/user-attachments/assets/50f31316-1fc7-4452-bbab-6ce7092fbc1d)


![video_20240727_160511_edit 00_00_00-00_00_30](https://github.com/user-attachments/assets/28382c74-d1e3-4b6e-974b-c20f7ecd83c3)

![video_20240727_160618_edit 00_00_00-00_00_30](https://github.com/user-attachments/assets/55f135fa-b6d8-49d4-9726-a150e26269c0)

大致的我负责的板块的展示如上。我们可以通过顶部的搜索框和”我的“界面的搜索按钮进入我负责的搜索模块，在搜索中搜索相应的内容，下面的四个栏目“单曲”，“专辑”，“歌手‘，”MV“会显示相应的数据。点击单曲的每一个item可以跳转到moduel_main的播放界面播放音乐，点击歌手的栏目可以进入查看歌手热门的音乐，点击MV的每一个item会跳转到model_broadcastr播放mv，点击评论按钮能弹出评论的界面，能通过往下滑看到更多评论。

# model的介绍

## module_app

​      通过handler和一个bool变量来控制跳转函数的启动,会跳转到module_login



![Screenshot_20240727_164224](C:\Users\86199\Downloads/Screenshot_20240727_164224.jpg)

## module_login

​      实现了手机号的验证码或者密码登录。注册功能和游客登录功能，并通过SharedPreferences保存登录的数据，下次打开应用时可以直接进入主页面。因为里面的网络请求逻辑有些许复杂，返回的数据比较简单导致我并没有写viewmodel，网络请求也直接暴露在了外面，在后面想优化代码的时候发现已经堆积得太多了。发送按钮点击一次后，当短信发送成功的时候会进入60s倒计时，防止连续进行发送短信的网络请求，这是一个通过重写CountDownTimer的两个抽象方法实现。

 



![Screenrecording_20240727_164505 00_00_00-00_00_30](D:\gifbroadcast\Screenrecording_20240727_164505 00_00_00-00_00_30.gif)

## module_search

   

![Screenrecording_20240727_165427 00_00_00-00_00_30](D:\gifbroadcast\Screenrecording_20240727_165427 00_00_00-00_00_30.gif)

   整体的布局是实现了tablayout和viewpager2+fragment的结合使用，下面是我的分包处理，在activity里面调用viewmodel中的方法进行网络请求获取数据，数据存放在livedata里面（**网络请求的具体逻辑放在repository里面，viewmodel只负责接受数据），然后这里a**ctivity和fragment的通信方法我使用的是，fragment的requireActivity（），让fragment拿到activity 的viewmodel，viewmodel绑定的是activity，应该不会造成内存泄漏

![image-20240727165930357](C:\Users\86199\AppData\Roaming\Typora\typora-user-images\image-20240727165930357.png)

实现歌曲的fragment刷新是依靠SwipeRefreshLayout包裹recycleview的视图，并写好刷新和加载逻辑。



## module_singer 



![Screenrecording_20240727_170844 00_00_00-00_00_30](D:\gifbroadcast\Screenrecording_20240727_170844 00_00_00-00_00_30.gif)

运用了协调布局和viewpager2和tablayout和fragment结合使用，能够看到歌手热门的40首歌曲，和歌手的资料。



## module_albums



![Screenrecording_20240727_170816 00_00_00-00_00_30](D:\gifbroadcast\Screenrecording_20240727_170816 00_00_00-00_00_30.gif)

里面也是一个协调布局，可以展示这个专辑的资料，可以对该专辑的介绍进行省略和展开。



## module_broadcast



![video_20240727_171138_edit 00_00_00-00_00_30](D:\gifbroadcast\video_20240727_171138_edit 00_00_00-00_00_30.gif)

这个播放mv的模块我觉得还有一些亮点，这是一个横屏播放mv的界面，在搜索界面点击mv的item后会跳转进这个模块，还实现了下滑观看更多相似mv的功能，但因为接口的问题好像返回都是那几个mv，运用的也是viewpager2和fragment结合使用。评论页面使用的是 BottomSheetDialogFragment，这样评论页面不会阻挡mv的播放，加载功能也是依靠SwipeRefreshLayout包裹recycleview来实现加载让viewmodel获得更多数据的功能。也实现了分享的功能，会把该视频的URL分享出去，让别人一同来观看





## 技术亮点



技术亮点在每个model介绍中也提到了，下面是一个比较简陋的总结。

- 利用RxJava和Retrofit进行网络请求，在后台新线程执行请求操作，并在主线程接收和处理数据。

- 通过共享ViewModel实现activity和fragment之间的通信，使用公开的LiveData进行UI更新。fragment依赖于其宿主activity的ViewModel。

- 使用MVVM架构，通过UI层处理界面展示，ViewModel层负责数据存储，将网络请求逻辑封装在utils中，采用单例模式。

- 学习了苟云东学长的课件学习ARouter，利用ARouter在不同模块不同activity和frament之间进行跳转。

  

  

## 总结和反思

 在红岩学习的这一年，我从对敲代码的无限神往到现在的无限迷茫，上学期认为读完那本《第一行代码》就可以开始随心所欲敲代码了，结果到实际操作的时候发现知识是无限的，还有Android studio的配置文件有时候不知道为啥就报红了，这一切都是开发经验的不足和基础知识的累计和基础不牢固。安卓里面的一些很重要的东西自己还没有深入学习和使用。例如协程，滑动冲突，gradle等等。可能是自己的开发经验比较少，在自己做app开发中遇到了很多的bug也没能完美的解决。不过通过这一年的学习，自己的自习能力得到了很大的提升，感谢网校为我提供了这样的一个平台。这次的考核的确让我学习到了很多知识，不过，也确实看到了很多自己的不足之处。

