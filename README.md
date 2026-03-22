# 金融资讯 App

一个基于 Jetpack Compose 的 Android 金融新闻应用，支持查看最近一周的金融资讯，并提供分类筛选功能。

## 功能特性

- 📰 新闻列表展示
- 🔍 分类筛选（商业、科技、市场、经济、综合）
- 🔎 搜索功能
- 🖼️ 图片加载
- 📱 Material Design 3 界面
- 🌐 新闻原文链接跳转

## 技术栈

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose
- **架构**: MVVM
- **网络**: Retrofit + OkHttp
- **图片加载**: Coil
- **API**: NewsAPI (免费版)

## 项目结构

```
app/src/main/java/com/example/financenews/
├── data/
│   ├── api/          # Retrofit API 接口
│   ├── model/        # 数据模型
│   └── repository/   # 数据仓库
├── ui/
│   ├── components/   # 可复用组件
│   ├── screens/      # 界面页面
│   └── theme/        # 主题配置
├── viewmodel/        # ViewModel
└── MainActivity.kt   # 主入口
```

## API 配置

应用使用 NewsAPI 免费版接口。需要修改 `NewsApiService.kt` 中的 `API_KEY`：

```kotlin
const val API_KEY = "your_api_key_here"
```

获取免费 API Key：[https://newsapi.org/register](https://newsapi.org/register)

**免费版限制**：
- 100 次请求/天
- 仅限开发环境

## 构建 APK

### 方式一：使用 Android Studio（推荐）

1. 打开 Android Studio
2. 选择 `Open an existing project`
3. 选择 `FinanceNewsApp` 文件夹
4. 等待 Gradle 同步完成
5. 点击 `Build` → `Build Bundle(s) / APK(s)` → `Build APK(s)`
6. APK 文件将生成在 `app/build/outputs/apk/debug/` 目录

### 方式二：使用命令行

确保已安装 Android SDK 和 Gradle：

```bash
# 进入项目目录
cd FinanceNewsApp

# 构建 Debug APK
./gradlew assembleDebug

# 构建 Release APK（需要签名配置）
./gradlew assembleRelease
```

APK 输出路径：
- Debug: `app/build/outputs/apk/debug/app-debug.apk`
- Release: `app/build/outputs/apk/release/app-release-unsigned.apk`

### 方式三：使用 Docker（无 Android Studio 环境）

```bash
# 使用包含 Android SDK 的 Docker 镜像
docker run --rm -v $(pwd):/app -w /app thyrlian/android-sdk bash -c "./gradlew assembleDebug"
```

## 安装 APK

### 方式一：通过 ADB

```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### 方式二：手动安装

1. 将 APK 文件传输到手机
2. 在手机上允许"未知来源"安装
3. 点击 APK 文件安装

## 分类说明

| 分类 | 搜索关键词 |
|------|-----------|
| 全部 | finance OR business OR economy |
| 商业 | business |
| 科技 | technology |
| 市场 | stock market OR trading |
| 经济 | economy |
| 综合 | finance |

## 注意事项

1. **API 限制**: 免费版每天限制 100 次请求
2. **网络权限**: 应用需要网络权限获取新闻数据
3. **图片加载**: 使用 Coil 库异步加载新闻图片
4. **错误处理**: API 失败时会显示模拟数据

## 截图预览

应用界面包含：
- 顶部搜索栏
- 分类筛选标签
- 新闻卡片列表（带图片、标题、描述、来源、时间）
- 下拉刷新和上拉加载更多

## GitHub Actions 自动构建

已配置 GitHub Actions 工作流，自动构建 APK。

### 触发构建

**方式 1：推送到主分支**
```bash
git push origin main
```

**方式 2：手动触发**
1. 进入 GitHub 仓库页面
2. 点击 Actions 标签
3. 选择 "Build APK" 工作流
4. 点击 "Run workflow"

### 下载 APK

构建完成后，APK 文件可在以下位置下载：
- **Artifacts**: Actions 页面 → 最新运行 → Artifacts → `FinanceNewsApp-debug`
- **Releases**: 如果提交信息包含 `[release]`，会自动创建 Release 并上传 APK

### 配置 GitHub 仓库

1. 创建新仓库：https://github.com/new
2. 推送代码：
```bash
cd FinanceNewsApp
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/FinanceNewsApp.git
git push -u origin main
```

3. GitHub Actions 会自动运行，构建 APK

## 许可

MIT License
