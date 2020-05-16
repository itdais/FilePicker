# FilePicker
文件选择器

未兼容Androidx，后续版本为改为兼容androidx版本

gradle引入
``` gradle
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```
当前版本
``` gradle
dependencies {
    implementation 'com.github.itdais:FilePicker:1.0.0'
}
```

## 所需权限
``` xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

## 配置FileProvider
注意：如果您想兼容Android N或者以上的设备，必须要在AndroidManifest.xml文件中配置FileProvider来访问共享路径的文件
```xml
 <!-- 解决provider部分在库manifest文件merge时的问题 -->
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="${applicationId}.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true"
            tools:replace="name,authorities,exported,grantUriPermissions">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/provider_paths"
                tools:replace="name,resource" />
        </provider>
```
如果第三方库也配置了同样的FileProvider, 可以通过继承FileProvider类来解决合并冲突的问题，示例如下：
```xml
<provider
    android:name=".MyFileProvider"
    android:authorities="${applicationId}.fileProvider"
    android:exported="false"
    android:grantUriPermissions="true"
    tools:replace="name,authorities,exported,grantUriPermissions">
    <meta-data
        android:name="android.support.FILE_PROVIDER_PATHS"
        android:resource="@xml/provider_paths"
        tools:replace="name,resource"/>
</provider>
```
对应的provider_paths.xml文件
```xml
<paths>
    <external-path
        name="external_files"
        path="." />
</paths>
```
## 使用说明

``` java
int requestCode = 1006;
FilePicker.from(MainActivity.this).chooseFile()
          .setFileType(".txt")
          .start(requestCode);
```

设置默认路径
```java
int requestCode = 1006;
FilePicker.from(MainActivity.this).chooseFile()
          .setFileType(".txt")
          .setTargetPath(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Mypda/test")
          .start(requestCode);
```

设置样式
```java
int requestCode = 1006;
FilePicker.from(MainActivity.this).chooseFile()
          .setFileType(".txt")
          .setThemeId(R.style.Mystyle)
          .start(requestCode);
```

如需使用请白底黑字的toolbar样式,需要自定义Style继承FilePicker.Default,修改toolbar的样式为
``` xml
    <style name="Mystyle" parent="FilePicker.Default">
        <item name="fp.toolbar">@style/Toolbar.Light</item>
    </style>
```
内部定义了白底黑字的toolbar样式,如下

### 内部定义的属性和样式
定义的属性有
```xml
    <attr name="fp.toolbar" format="reference" />
    <attr name="fp.placeholder" format="reference|color" />
    <attr name="fp.emptyView" format="reference" />
    <attr name="fp.emptyView.textColor" format="reference|color" />
    <attr name="fp.pageBg" format="reference|color" />
    <attr name="fp.item.textcolor" format="reference|color" />
    <attr name="fp.item.hintcolor" format="reference|color" />
    <attr name="fp.item.pathcolor" format="reference|color" />
```
系统对应的样式
```xml
    <!-- ===================================== 默认模式 ===================================== -->
    <style name="FilePicker.Default" parent="Theme.AppCompat.Light.NoActionBar">
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="fp.toolbar">@style/Toolbar.Default</item>
        <item name="fp.emptyView">@drawable/fp_empty_default</item>
        <item name="fp.emptyView.textColor">@color/default_album_empty_view</item>
        <item name="fp.placeholder">@color/default_item_placeholder</item>
        <item name="fp.pageBg">@color/default_page_bg</item>
        <item name="fp.item.textcolor">@color/default_item_textcolor</item>
        <item name="fp.item.hintcolor">@color/default_item_hintcolor</item>
        <item name="fp.item.pathcolor">@color/default_item_pathcolor</item>
    </style>
    <!-- ===================================== 暗黑模式 ===================================== -->
    <style name="FilePicker.Dracula" parent="Theme.AppCompat.NoActionBar">
            <item name="colorPrimary">@color/dracula_primary</item>
            <item name="colorPrimaryDark">@color/dracula_primary_dark</item>
            <item name="fp.toolbar">@style/Toolbar.Dracula</item>
            <item name="fp.emptyView">@drawable/fp_empty_dracula</item>
            <item name="fp.emptyView.textColor">@color/dracula_album_empty_view</item>
            <item name="fp.placeholder">@color/dracula_item_placeholder</item>
            <item name="fp.pageBg">@color/dracula_page_bg</item>
            <item name="fp.item.textcolor">@color/dracula_item_textcolor</item>
            <item name="fp.item.hintcolor">@color/dracula_item_hintcolor</item>
            <item name="fp.item.pathcolor">@color/dracula_item_pathcolor</item>
    </style>
    <!-- ================================== 白底黑字toolbar ================================== -->
    <style name="Toolbar.Light" parent="@style/ThemeOverlay.AppCompat.ActionBar"></style>
```
## Thanks
#### https://github.com/imLibo/FilePicker
#### https://github.com/leonHua/LFilePicker
