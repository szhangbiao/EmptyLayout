# EmptyLayout
基于LinearLayout和FrameLayout的两套切换不同的数据状态布局，包括加载中、空数据和出错重试状态。
# 特点
* 可以像正常的FrameLayout和LinearLayout一样使用，不会增加布局层数
* 各种状态布局都是动态添加，不会带来额外的性能损耗
* 在错误重试页面添加了重试按钮，方便添加自定义重试事件
* 在各种状态页面需要额外显示头部View（LinearEmptyLayout暂不支持）<br>
# 效果预览
![FrameEmptyLayout](https://github.com/szhangbiao/EmptyLayout/blob/master/gif/FrameEmptyLayout.gif) 
![LinearEmptyLayout](https://github.com/szhangbiao/EmptyLayout/blob/master/gif/LinearEmptyLayout.gif) 
# 下载
1.在项目的 `build.gradle` 中添加：
```java
allprojects {
    repositories {
	    ...
	    maven { url 'https://jitpack.io' }
    }
}
```
2.添加依赖
```java
dependencies {
    compile 'com.custom.emptylayout:emptylayout:1.0.0'
}
```
# 使用
1.在xml中
```xml
<com.custom.emptylayout.FrameEmptyLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fel_parent"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    />
```
2.在代码适当的时机调用
```java
// 加载中
emptyLayout.showLoading();
//正常状态
emptyLayout.showContent();
// 空数据
emptyLayout.showEmpty(R.drawable.ic_launcher_foreground,"暂无数据！",skipId);
//带有重试按钮的错误页
emptyLayout.showError(R.drawable.net_error,"不知道什么原因加载出错了！","点击重试");
//不带重试按钮只显示错误页
emptyLayout.showError(R.drawable.net_error,"不知道什么原因加载出错了！",null);
```
3.LinearEmptyLayout用法类似具体可参考sample中的代码
# 后续改进
* 支持在Theme中设置默认的图片和提示语
* 支持在style中修改一些样式
# 支持版本
支持 API Level 14+。
# Author
szhangbiao, szhangbiao@gmail.com
