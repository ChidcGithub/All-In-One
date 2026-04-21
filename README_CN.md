# All in one - Android 原生 API 全演示

一个全面的技术演示应用，系统性地展示 Android 核心原生 API、系统服务和硬件接口。基于 Jetpack Compose 和 Material 3 Expressive 构建。

## 项目概述

All in one 是一款面向 Android 开发者和技术爱好者的教育参考应用，旨在帮助理解 Android 系统能力的完整广度。它在一个组织良好的应用中提供了几乎每个主要 Android API 类别的实际演示。

## 技术规格

- **最低 SDK**: API 36 (Android 16 Baklava)
- **目标 SDK**: API 36
- **UI 框架**: 100% Jetpack Compose
- **设计系统**: Material 3 Expressive
- **开发语言**: Kotlin
- **架构模式**: MVVM + 整洁架构
- **构建系统**: Gradle + Kotlin DSL + 版本目录

## 技术栈

### 核心依赖
- Jetpack Compose (UI 框架)
- Material 3 Expressive (设计系统)
- Compose Navigation (基于 kotlinx.serialization 的类型安全导航)
- Lifecycle ViewModel (状态管理)
- Kotlin Coroutines & Flow (异步编程)

### 数据存储
- Room (SQLite ORM)
- DataStore (偏好设置)
- SharedPreferences (传统键值存储)
- KSP (Kotlin 符号处理)

### 系统集成
- CameraX (相机操作)
- WorkManager (后台任务调度)
- SensorManager (硬件传感器)
- ConnectivityManager (网络监控)
- BluetoothManager (蓝牙操作)
- Location Services (GPS 和网络定位)
- NotificationManager (系统通知)
- AlarmManager (精确定时调度)

### 权限管理
- Accompanist Permissions (运行时权限请求)

## 项目结构

```
app/src/main/java/com/allinone/
├── MainActivity.kt                    # 边到边 Compose 入口
├── AllInOneApp.kt                     # 应用类与依赖初始化
├── core/
│   ├── base/                          # 基础 ViewModel 和 UI 状态
│   ├── data/
│   │   ├── database/                  # Room 数据库和 DAO
│   │   └── repository/                # 统一数据仓库
│   ├── di/                            # 简易依赖注入
│   ├── theme/                         # M3 Expressive 主题系统
│   │   ├── Color.kt                   # 扩展调色板
│   │   ├── Typography.kt              # 30 种字体样式
│   │   ├── Shapes.kt                  # 35 种形状变体
│   │   └── Theme.kt                   # 动态配色和深色模式
│   └── ui/components/                 # 可复用 Compose 组件
├── navigation/
│   ├── DemoModules.kt                 # 模块定义和路由
│   └── NavHost.kt                     # 集中式导航宿主
└── feature/
    ├── home/                          # 主界面（搜索和筛选）
    ├── system/                        # 核心框架模块
    │   ├── NavigationScreen.kt        # Activity 生命周期和启动模式
    │   ├── ServicesScreen.kt          # 前台、后台、绑定服务
    │   ├── BroadcastScreen.kt         # 静态和动态广播接收器
    │   ├── ContentProviderScreen.kt   # 跨应用数据共享
    │   ├── LocationScreen.kt          # GPS、网络定位和地理围栏
    │   ├── NotificationScreen.kt      # 通知渠道和类型
    │   └── AccessibilityScreen.kt     # 无障碍服务
    ├── ui/                            # UI 与交互模块
    │   ├── UILayoutsScreen.kt         # 布局组件
    │   ├── M3ExpressiveScreen.kt      # M3 Expressive 组件
    │   ├── AnimationsScreen.kt        # 弹簧和过渡动画
    │   └── CustomDrawingScreen.kt     # Canvas 和形状绘制
    ├── sensors/                       # 硬件传感器演示
    ├── multimedia/                    # 相机、音频和视频
    ├── connectivity/                  # 蓝牙、NFC、Wi-Fi 和网络
    ├── storage/                       # 本地存储实现
    ├── background/                    # 后台任务调度
    ├── permissions/                   # 运行时和特殊权限
    └── android16/                     # Android 16 专属 API
```

## 功能模块

### 一、核心框架

| 模块 | 演示内容 |
|------|---------|
| Activity 与导航 | Activity 生命周期、启动模式（Standard、SingleTop、SingleTask、SingleInstance）、Intent 标志、Compose 类型安全导航 |
| 服务 (Service) | 带通知的前台服务、后台服务、基于 IBinder 的绑定服务 |
| 广播接收器 | 动态注册、静态清单注册、系统广播监控、有序广播 |
| 内容提供器 | 自定义 Provider 实现、CRUD 操作、系统 Provider 查询（通讯录、媒体库） |

### 二、UI 与交互

| 模块 | 演示内容 |
|------|---------|
| UI 与布局 | Column、Row、Box、LazyColumn、LazyRow、ScrollView、Tabs、Buttons、Sliders、动画内容 |
| M3 Expressive | ButtonGroup、FABMenu、LoadingIndicator、SplitButton、VerticalFloatingToolbar、三种速度弹簧动画、35 种形状变体 |
| 动画与过渡 | AnimatedVisibility、无限过渡、手势驱动动画、弹性弹簧、路径运动 |
| 自定义绘制 | Canvas 基础、贝塞尔曲线、线性渐变、柱状图、环形图 |

### 三、硬件与服务

| 模块 | 演示内容 |
|------|---------|
| 传感器 | 加速度计、陀螺仪、磁力计、光线传感器、接近传感器、计步器、传感器枚举 |
| 多媒体 | CameraX 预览和拍照、MediaRecorder 录音、MediaPlayer 播放、MediaSession 集成、CameraX 扩展 |
| 定位服务 | GPS 定位、网络定位、持续位置跟踪、地理围栏、粗略定位（Android 16） |
| 蓝牙与 NFC | BLE 可用性检查、设备扫描、GATT 连接、NFC 标签读写、Android Beam 弃用说明 |
| Wi-Fi 与网络 | 网络回调监控、Wi-Fi 连接信息、TCP/UDP Socket 演示、网络能力检测 |

### 四、数据与存储

| 模块 | 演示内容 |
|------|---------|
| 本地存储 | SharedPreferences、DataStore（Preferences）、Room 数据库（SQLite）、内部/外部文件存储 |
| 密钥库 | 加密存储和密钥管理 |

### 五、高级系统集成

| 模块 | 演示内容 |
|------|---------|
| 通知 | 通知渠道组、基础通知、可操作通知、进度通知、富媒体通知（BigPictureStyle） |
| 后台任务 | WorkManager（一次性、周期性、链式）、AlarmManager（精确、重复）、JobScheduler 约束条件 |
| 权限管理 | 运行时权限请求（相机、位置、麦克风、通知）、特殊权限（系统设置、悬浮窗、电池优化）、权限说明展示 |
| 无障碍服务 | AccessibilityManager、TalkBack 检测、内容描述、触摸目标尺寸、对比度比率、字体缩放 |

### 六、Android 16 专属 API

| 模块 | 演示内容 |
|------|---------|
| Android 16 API | 隐私沙盒概述、粗略定位改进、后台 Activity 启动限制、前台服务类型细化、SDK_INT_FULL 版本检查、边到边强制渲染、路径遍历保护、照片选择器更新 |

## 设计系统

### Material 3 Expressive

本应用实现了随 Android 16 推出的 Material 3 Expressive 设计系统：

- **动态配色**: 基于系统壁纸的配色提取，自动适配深色主题
- **弹簧动画**: 基于物理运动的效果，提供三种速度配置（默认、快速、慢速）
- **表现力排版**: 30 种字体样式，优化情感连接和可读性
- **形状系统**: 35 种形状变体，扩展 M3 基础形状令牌
- **组件更新**: 增强的按钮、进度指示器、导航栏、应用栏、菜单和滑块

### 深色模式

完整的深色主题支持：
- 跟随系统主题设置
- 动态配色兼容（Android 12+）
- 平滑的主题过渡动画
- 符合无障碍标准的对比度比率

## 架构设计

### MVVM 模式

每个功能模块遵循 Model-View-ViewModel 模式：

- **Model**: 数据层，包含 Room、DataStore 和系统 API
- **View**: 观察状态流的 Compose UI 界面
- **ViewModel**: 使用 Kotlin Flow 和协程进行状态管理

### 依赖注入

通过 `AppModule` 对象实现简易服务定位器模式，提供：
- 应用上下文
- SharedPreferences 实例
- DataStore 实例
- Room 数据库实例
- Repository 实例

### 整洁架构层次

- **表现层**: Compose 界面和 ViewModel
- **领域层**: 用例和业务逻辑（Repository 接口）
- **数据层**: Repository 实现、数据源、数据库

## 权限说明

应用在清单文件中声明了以下权限：

### 普通权限（自动授予）
- INTERNET（网络访问）
- ACCESS_NETWORK_STATE（网络状态）
- ACCESS_WIFI_STATE（Wi-Fi 状态）
- VIBRATE（震动）
- WAKE_LOCK（唤醒锁）
- RECEIVE_BOOT_COMPLETED（开机启动）

### 危险权限（需运行时请求）
- ACCESS_COARSE_LOCATION（粗略位置）
- ACCESS_FINE_LOCATION（精确位置）
- ACCESS_BACKGROUND_LOCATION（后台位置）
- BLUETOOTH_SCAN（蓝牙扫描）
- BLUETOOTH_CONNECT（蓝牙连接）
- BLUETOOTH_ADVERTISE（蓝牙广播）
- CAMERA（相机）
- RECORD_AUDIO（麦克风录音）
- READ_MEDIA_IMAGES（图片读取）
- READ_MEDIA_VIDEO（视频读取）
- READ_MEDIA_AUDIO（音频读取）
- POST_NOTIFICATIONS（发送通知）
- BODY_SENSORS（身体传感器）
- ACTIVITY_RECOGNITION（活动识别）

### 特殊权限
- FOREGROUND_SERVICE 及类型（camera、location、microphone、dataSync）
- SCHEDULE_EXACT_ALARM（精确闹钟）
- SYSTEM_ALERT_WINDOW（悬浮窗）
- NFC（近场通信）

## 构建与运行

### 前置要求
- Android Studio Ladybug 或更高版本
- JDK 21
- Android SDK Platform 36
- Android SDK Build-Tools 36

### 构建调试版
```bash
./gradlew assembleDebug
```

### 运行测试
```bash
./gradlew test
./gradlew connectedAndroidTest
```

### 构建发布版
```bash
./gradlew assembleRelease
```

## API 覆盖范围

本应用演示了以下 Android API 类别：

1. 应用框架（Activity、Service、BroadcastReceiver、ContentProvider）
2. 用户界面（Compose 布局、M3 Expressive 组件、动画、Canvas）
3. 硬件访问（传感器、相机、麦克风、蓝牙、NFC、Wi-Fi）
4. 定位服务（GPS、网络定位、地理围栏）
5. 数据存储（SharedPreferences、DataStore、Room、文件系统、KeyStore）
6. 系统集成（通知、后台任务、权限、无障碍）
7. 网络通信（连接监控、Socket）
8. 多媒体（相机、录音、媒体播放、MediaSession）
9. Android 16 平台 API（隐私沙盒、粗略定位、前台服务类型）

## 目标用户

- 学习平台能力的 Android 开发者
- 技术面试准备
- API 参考和快速原型设计
- 教育演示
- 架构模式示例

## 许可证

本项目仅供教育用途。
