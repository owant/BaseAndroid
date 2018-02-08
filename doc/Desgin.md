## RDC Android基础构建

author： liuwang.zhong<br>
version：0.1.0

[TOC]

### **前言**
对于Android的构建流程来说，更多的是关注我们的一些所谓“规定”。这些规定不一定是需要去追随，可以由我们员工们自行选择。其中的规定也会随着工程、项目而改变。这只是一些意见，我们希望可以达到一种效应：Android的代码有着自己公司员工的共同审美，接受我们这种命名、分类、流程，进而做好构建出一个个健壮应用的准备。

### **开发工具**
一般工具有：AndroidStudio，JDK，Gradle，Git等。<br>
对于Android Application的开发工具采用AndroidStudio。当前采用的版本号为：2.3.1。下载时尽量，下载不带SDK版本的，自带SDK版本的问题比较多。JDK的JAVA版本下载：1.8.0_112版本。Gradle版本为：3.3。工具下载地址表

|工具名称|下载地址|
|-------|-------|
|AndroidStudio| http://www.androiddevtools.cn/ |
|JDK|http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html |
|Gradle|http://pan.baidu.com/s/1pLEkm4F#list/path=%2F |
|Git|https://github.com/git-for-windows/git-sdk-64 |

环境变量配置情况如下：

- java环境变量
- adb shell 环境变量
- SDK 环境变量
- gradle 环境变量

配置代码如下

```

#Java环境变量
JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_65.jdk/Contents/Home
export JAVA_HOME

#NDK环境变量
export NDK_HOME=/Users/**/Library/Android/sdk/ndk-bundle/
export PATH=$NDK_HOME:$PATH

# android工具环境变量
export ANDROID_HOME=/Users/**/Library/Android/sdk
export emulator=/Users/**/Library/Android/sdk/emulator/
export PATH=$emulator:$PATH
export PATH=$ANDROID_HOME/tools:$PATH
export SDK_MANAGER=/Users/**/Library/Android/sdk/tools/bin/
export PATH=$SDK_MANAGER:$PATH
export PATH=$ANDROID_HOME/platform-tools:$PATH

#gradle
export GRADLE=/Users/**/gradle/gradle-3.3
export PATH=$GRADLE/bin:$PATH

#**代表用户名

```

### **应用构建**

#### **默认配置**

* **gitignore配置**<br>

工程结构是一个Project非Module。工具结构和AndroidStudio默认的一样。代码使用Git库管理，Project的gitignore文件内容

```
# Gradle files
.gradle
build

# Local configuration file (sdk path, etc)
local.properties
.idea/workspace.xml
.idea/libraries

# IntelliJ project files
**.iml
.idea

# ignore the mac file
.DS_Store

#other
.externalNativeBuild
captures

```
* **projectGradle配置**<br>

工程的Project的build.gradle需要配置一下公用的配置，主要是default setting的内容，达到工程的统一配置,而且也可以避免support版本不一致导致的BUG。内容如下：

```
 // Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.1'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
    }
}


task clean(type: Delete) {
    delete rootProject.buildDir
}

//default setting
ext {

    //sdk version
    base_compile_sdk_version = 25
    //build tool version
    base_build_tools_version = '25.0.2'

    //min sdk version
    base_min_sdk_version = 15
    //target sdk version
    base_target_sdk_version = 24

    //support library version
    base_support_version = '25.3.1'
}
```
引用列子
```
apply plugin: 'com.android.application'

android {
    compileSdkVersion base_compile_sdk_version
    buildToolsVersion base_build_tools_version
    defaultConfig {
        applicationId "com.richdataco.testandroidstudio"
        minSdkVersion base_min_sdk_version
        targetSdkVersion base_sdk_version
        versionCode 1
        versionName "1.0"
        //省略
}

dependencies {
    //省略
    compile "com.android.support:appcompat-v7:$base_support_version"
    compile "com.android.support:appcompat-v4:$base_support_version"
}

```

* **兼容包开发**<br>

工程的开发必须采用兼容包开发。使用v7，v4等来构建app，不能直接使用Activity这种开发，其次，我们的测试样机必须有三个点：Android4.4，Android5.0，Android6.0。兼有问题表如下：

| 常见兼有问题| 常见兼容详情|解决方案|
|:----:|:-----|:----|
|键盘兼容|对于目前的手机，有点手机采用的是软菜单键和物理按键，所以要防止，UI界面出现的软键盘挡住了，无法点击Tab|设置软键盘在UI之下，测试可以用华为的软键盘测试|
|AsyncTask兼容|对于AsyncTask尽量不用使用来处理大任务，要不会出现异常|采用Thread任务队列进行处理|
|打开网络兼容|对于低版本的手机是可以通过反射等操作进行打开移动网络,其实这些操作是危险的，而且不必要的|不要实现这些功能，是伪需求|
|动态权限的兼容|对于Android6.0的机器都是采用动态权限了，我们必须进行兼容，要不然，会出现在Android6.0上运行无缘无故打不开app,一安装就shutdown等问题|添加动态权限处理|
|动画兼容|对于使用到属性动画的技术，必须要进行兼容，因为这是在Android3.0加进来的独立动画框架|采用com.nineoldandroids:library兼容包开发|
|support包兼容|由于采用的support兼容包得版本不一致也会导致问题|按照我们队project的Gradle配置进行，就可达到统一版本|
|对于状态栏兼容|由于需要实现沉淀式的状态栏，这时候需要进行兼容,要不会出现setContentView异常|尽可能得进行多个Android版本的手机测试|
|向后兼用|对于向后兼容，可以看到google的support包的努力，采用这些包开发会大大减少我们向后兼容的工作|使用Support进行开发|
|SO库兼容|对于SO库来说，这是基于芯片进行编译的，所以一定程度上是削弱了兼容性|要拆开来看看so库提供的情况进行判断，最好可以提供arm64-v8a,x86_64,x86,armeabi,armeabi-v7a|
|WebView兼容|对于WebView的问题说不清，但是要有意识去兼容，因为它是出现bug的交通事故多发地段|多测试，多思考|



#### **应用打包**

应用打包采用Gradle进行打包。下面有一个Gradle打包模板，模板如下：

```
import java.text.SimpleDateFormat

apply plugin: 'com.android.application'

android {
    compileSdkVersion base_compile_sdk_version
    buildToolsVersion base_build_tools_version
    defaultConfig {
        applicationId "com.richdataco.base"
        minSdkVersion base_min_sdk_version
        targetSdkVersion base_target_sdk_version
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
        externalNativeBuild {
            cmake {
                cppFlags ""
            }
        }
    }

    signingConfigs {
        config {
            storeFile file('./keys/BaseKey.jks')
            keyAlias 'BaseKey'
            keyPassword 'rdc@2016'
            storePassword 'rdc@2016'
        }
    }

    buildTypes {

        debug {
            //配置是打印log
            buildConfigField "boolean", "LOG_DEBUG", "true"
            minifyEnabled false
            //关联签名
            signingConfig signingConfigs.config
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }

        release {
            //配置不打log
            buildConfigField "boolean", "LOG_DEBUG", "false"
            minifyEnabled false
            //关联签名
            signingConfig signingConfigs.config
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
    //打包类型
    productFlavors {

        //debug版本,注意applicationId最好不要和release版本一致，方便测试，
        // 而且可以防止测试人员用debug版本去发布
        run_debug {
            applicationId "com.richdataco.demo_bug"
        }

        //release版本
        app_release {
            applicationId "com.richdataco.demo"
        }

    }
    externalNativeBuild {
        cmake {
            path "CMakeLists.txt"
        }
    }
    lintOptions {
        abortOnError false
    }

    //输出文件
    applicationVariants.all { main ->
        main.outputs.each { output ->
            def file = output.outputFile
            output.outputFile = new File(file.parent,
                    "comm_demo_" + main.buildType.name +
                            "_v_" + main.versionName + "_"
                            + buildTime() + ".apk");
        }
    }
}

def buildTime() {
    def df = new SimpleDateFormat("yyyyMMdd")
    df.setTimeZone(TimeZone.getDefault())
    return df.format(new Date())
}

dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile "com.android.support:appcompat-v7:$base_support_version"
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    testCompile 'junit:junit:4.12'
    compile project(':richdatacommon')
}

```
打包命令的使用，打开Terminal进行一下命令
`gradle build`或者直接运行期release版本 `gradle assembleRelease`既可以。

#### **工程框架**

工程采用MVC或者MVP模式。这里对MVC模式忽略不讲。对于MVP模式的简要结构如下：

```sequence
View->Presenter:View持有Presenter
Presenter->Model:Presenter持有Model
Presenter->View:Presenter将信息发给View绘制
Model-->View:Model只能通过Presenter进行View的交互
View-->Model:View只通过Presenter进行Model的交互
Presenter->Presenter:进行事务流程，保存，刷新等View
View->View:暴露所有的绘制接口给Presenter进行依赖
```
在MVP模式中，Presenter和Model是可以拖离SDK的，这样更加的方便单元测试。View层的测试可以通过SDK进行测试（纯UI测试）。

### **网络接口设计**

#### Response基本格式

对于http的请求的返回格式采用这个基本格式，default_json
```json
//重要数据在result里
{
   "isError":false,
   "errorType":0,
   "errorMessage":"",
   "result":{
         "cinemaId":1,
         "cinemaName":"小明"
   }
}
//异常的类型
{
   "isError":true,
   "errorType":1,
   "errorMessage":"网络异常",
   "result":""
}
```
isError是调用MobileAPI成功与否，errorType是错误类型（如果成功为0），errorMessage是错误消息（如果成功则为空），result是请求返回的数据结果（如果失败则为空）。这样的统一方便我们队请求数据的进行统一处理。

#### 虚拟远程服务

对于Application的开发来说。一般都是要应用后台的接口工作，如果有完善的接口文档的话，在文档提供详细的回调格式，并且后台写一些假的数据给我前端开发，这样我们的Application还是可以前行无阻的，但是会陷入一个怪圈，Application的人在等接口，或相符沟通接口的变化，之后，还要等后台人员修改锁定的假数据。这里我参考了以往的经历和网上的资源采用了一个MockService来解决这个问题，只需要Application这端进行模拟数据。要实现的这个设计的前提是：

*  提供良好接口文档，包括放回数据（json结构）；
*  数据返回的结构符合BaseResponse的结构；

之后在代码实现MockService既可。例子如下

```
//在res/xml/url.xml里添加接口
//其中MockClass为虚拟的请求数据返回模型

<?xml version="1.0" encoding="utf-8"?>
<!--
<url>
    <Node
        BaseJSON="true"
        Key="getMusic2"
        MockClass=""
        NetType="post"
        ShouldCache="true"
        Url="http://v5.pc.duomi.com/search-ajaxsearch-searchall"/>

        说明介绍
        BaseJSON:是否采用规定格式下的JSON格式
        Key:用于查找API的接口
        MockClass：是否采用虚拟的返回数据
        NetType：是请求类型post/get
        ShouldCache:是否缓存
        Url:请求的路径
</url>

-->

<url>
    <Node
        Key="getWeatherInfo"
        NetType="get"
        MockClass="com.youngheart.mockdata.MockWeatherInfo"
        Url="http://www.weather.com.cn/data/sk/101010100.html" />

    <Node
        Key="login"
        NetType="post"
        MockClass=""
        Url="http://www.weather.com.cn/data/sk/101010100.html" />
</url>

//虚拟数据
public class MockWeatherInfo extends MockService {
	@Override
	public String getJsonData() {
		WeatherInfo weather = new WeatherInfo();
		weather.setCity("Shenghai");
		weather.setCityid("10203");

		Response response = getSuccessResponse();
		response.setResult(JSON.toJSONString(weather));
		return JSON.toJSONString(response);
	}
}

//进行请求
RemoteService.getInstance().invoke("getMusic", targetContext, "getMusic2", params, null, requestCallback);

```
主要是在MockClass进行管理需要模拟的数据，如果MockClass为空即走真实的请求。

### **代码规范与规定**

代码规范的原则是简单易懂，不罗嗦，也可以参考google的java编程规范风格。

#### JAVA基本命名规约

* 1.代码中的命均不能以**下划线或者美元符号**开始，也不能以**下划线和美元号**结束。
       反例:  `_name  /  __name  /  \$Object /  name_  / name$`

*  2.**类名**使用UpperCamelCase风格，必须遵从驼峰形式
        正例： `UserDO /  XmlService  / LoginActivity / TcpUdpDeal`

* 3.**方法名、参数名、成员变量、局部变量**都统一使用lowerCameCase风格，必须遵从驼峰形式。
        正例： `localValue / getHttpMessage / inputUserId`

* 4.**常量名**全部大写，单纯间使用下划线隔开，力求语义表达清楚，不要嫌名字长。
       正例：`MAX_STOCK_COUNT`
       反例：`MAX_COUNT`

* 5.**包名**统一小写，点分隔符直径仅有一个自然语义的英文单词。包名统一使用单数形式，但是类名如果有复数含义，可以使用复数形式，而且报名一般是和公司网址为开头的格式为：com.company_name.project_name。
      正例： `com.richdataco.iot.util ` 类名为`MessageUtils`

* 6.**m**前缀的成员变量具有广泛性，如果实现了一个类它没有涉及事务，即可用m作为前缀。
      正例：`com.richdataco.view.MyView `里的成员变量`mPaint,mHeight,mFlag`等

#### Android内的规约

* 1.**四大组件Activity，Service，Content Provider，Broadcast Receiver**的实现必须带对应的后缀。

* 2.**控件ID命名**规范，控件的Id必须按照以下规则命名： **`模块+类型+功能`**。<br>
   正例：`login_btn_enter / user_space_iv_photo`
   反例：`btn_login / iv_user_photo `(因为用户头像在很多地方都有，而且AndroidStudio的提示时可以进行快速定位，你需要的控件是在user_space下的类型为ImageView(iv)功能为photo的图片)<br>

* 3.**控件自定义属性**，除了layout相关的可以使用下划线隔开，其他的都是采用lowerCameCase风格，不用下划线。正例如下：

```
    <com.companyname.view.DivView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="10dp"
        android:layout_weight="0"
        android:clickable="true"
        android:enabled="true"
        android:onClick="intentToMain"
        android:text="crop image"
        android:textAllCaps="false"
        android:textColor="@color/colorAccent"/>

```
* 4.**layout**下布局命名必须采用小写加下划线的方式，而且对于Activity和Fragment使用的需要加`activity_`和`fragment_`作为前缀；ListView的item使用要带`item_`;
     正例：`activity_main` `fragment_home` `fragment_profile` `item_my_favorites_food`

* 5.**values**下面的colors，dimens，strings都是采用全部小写加下划线的命名方式，但是对于styles的命名需要采用UpperCamelCase风格，也可以这样认为，对于Style是一个类，Style的属性是方法。正列如下：

```

    //---------------颜色的-------------------
    <color name="mid_night">#22313f</color>
    <color name="bon_jour">#E4E2E4</color>
    <color name="charade">#3C3F41</color>
    <color name="gallery">#EFEFEF</color>
    <color name="cornell_red">#BB0F23</color>
    <color name="cornell_red2">#bb0f45</color>
    <color name="jaguar">#2B2B2B</color>

    //---------------单位的-----------------------
    <dimen name="text_size_button">14sp</dimen>
    <dimen name="text_size_menu">14sp</dimen>
    <dimen name="text_size_iv_tell">12sp</dimen>
    <dimen name="text_size_body_1">14sp</dimen>
    <dimen name="text_size_body_2">16sp</dimen>
    <dimen name="text_size_sub_head">16sp</dimen>
    <dimen name="text_size_title">20sp</dimen>
    <dimen name="text_size_head_line">24sp</dimen>
    <dimen name="text_size_display_1">34sp</dimen>
    <dimen name="text_size_display_2">45sp</dimen>
    <dimen name="text_size_display_3">56sp</dimen>
    <dimen name="text_size_display_4">112sp</dimen>

    //--------------------styles------------------
    <style name="SplashStyle" parent="AppTheme">
        <item name="colorPrimary">@color/athens_gray</item>
        <item name="colorPrimaryDark">@color/athens_gray</item>
        <item name="android:background">@color/athens_gray</item>
        <item name="android:windowBackground">@null</item>
    </style>
```

### **工具类**

**AndroidLog**

是app调试log的工具，可以通过gradle进行配置log的开关。默认release版本是关闭，debug版本为打开，不用在代码中写BuildConfig,因为编译器已经默认添加了一个BuildConfig。

**DensityUtils**

给类是实现android的各种屏幕适配的单位转化。

**BindViewTools.jar**
在之前说过了android控件命名的规范后，下面简单介绍一下公共类中写的一个BindView工具的使用，使用例子如下

```
//默认是Activity下的findViewById
java -jar BindViewTool.jar -p $layout_file_path
//Fragment下的
java -jar BindViewTool.jar -f true -p $layout_file_path
//添加忽略标识命令,对于m开头的进行忽略
java -jar BindViewTool.jar -f true -i m -p $layout_file_path

//返回结果示例
private LinearLayout testBindLyContainer;
private TextView testBindTvInfo;
private Button testBindBtnLogin;
private Button testBindBtnUser;
private ListView testBindLvNews;



public void bindViews(){

        testBindLyContainer = (LinearLayout)getView().findViewById(R.id.test_bind_ly_container);
        testBindTvInfo = (TextView)getView().findViewById(R.id.test_bind_tv_info);
        testBindBtnLogin = (Button)getView().findViewById(R.id.test_bind_btn_login);
        testBindBtnUser = (Button)getView().findViewById(R.id.test_bind_btn_user);
        testBindLvNews = (ListView)getView().findViewById(R.id.test_bind_lv_news);
}

```
**其他的工具类**

都可以通过java的名字推测得知。

### **其他**
更多的信息可以通过查看commonlib库的情况。

