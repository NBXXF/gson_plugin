# gson_plugin
gson_plugin是一个注解处理器，能够在编译期自动生成**兼容Kotlin特性的**、**高性能TypeAdapter**，以提升Gson的序列化与反序列化时间性能。同时也解决了kotlin默认值的问题.平均**比原始Gson快5倍**。
## 主要方向
1. 编译构建对象,零反射
2. 按需加载,json和声明字段的最小集合,包括expose.xx=false 和transient 修饰符
3. 减少类型判断和中间层遍历(Factory,Excluder)
4. 编译生成注解配置,减少运行时注解 @Expose SerializedName

## 框架特性
1. 目前测试支持kotlin 最高版本1.7.20
2. 支持gson和Moshi生成的adapter冲突
3. 支持gson自定义基本类型解析被阻断无效的场景(比如自定义BoolTypeAdapter)
4. 支持嵌套内部类,嵌套内部类名字用下划线隔离,避免不同类的嵌套类名字冲突
5. 支持别名  @SerializedName("age", alternate = ["age_int","AGE"])
6. 支持Number类及子类
7. 支持枚举类自动生成TypeAdapter @AutoGeneratedJsonAdapter (目前仅限kapt ksp还没完成)
8. 支持枚举序列化为String,Int,Long等类型(gson 默认会序列化成字符串类型 比如"1" 而不是1)
9. 自动生成的适配器 支持继承,业务可以继承了 然后再塞给gson
10. 支持字段用注解声明@JsonAdapter 共存,如果有注解,优先用注解的适配器
11. 支持@Expose注解
12. [即将支持] 对于整型枚举 用SparseArray替代hashmap 提高查询和节省内存
13. [即将支持] 模型参数没有默认值的情况 (目前必须写默认值, 可以是?=null的形式)

![](img/adapters.png)

### 注解解释
```kotlin
annotation class AutoGeneratedJsonAdapter(
    /**
     * 是否是空安全 默认true
     *
     * 如,对于int类型的声明
     * 如果是val i:Int? 将不受任何影响
     * 如果是val i:Int json里面是null 那么设置为false 将会报错,否则将会跳过这个字段
     *
     * 受影响范围 [INT, LONG, FLOAT, DOUBLE, STRING, BOOLEAN,ENUM]
     */
    val nullSafe: Boolean = true,
    /**
     * 是否强类型匹配 默认false
     *
     * 如,对于int类型的声明
     *如果是val i:Int? 将不受任何影响
     * 如果是val i:Int json里面是"" 那么设置为true 将会报错,否则继续调用gson.getAdapter 继续责任链解析(交给gson自带的或者registerTypeAdapter等来解析)
     *
     * 受影响范围 [INT, LONG, FLOAT, DOUBLE, STRING, BOOLEAN,ENUM]
     */
    val strictType: Boolean = false,

    /**
     * 生成的adapter文件 后缀名字 默认“AutoGeneratedTypeAdapter”
     */
    val adapterNameSuffix: String = "AutoGeneratedTypeAdapter",

    /**
     * 序列化最终数据类型 默认 SerializedType.STRING
     * Gson对于枚举默认序列化成String类型
     *
     * 受影响范围[ ENUM ]
     */
    val serializedType: SerializedType = SerializedType.STRING,
)

```
### 仓库权限
```groovy
//请在build.gradle中配置
allprojects {
    repositories {

        maven {
            url 'https://maven.aliyun.com/repository/public'
        }
        maven {
            credentials {
                username '654f4d888f25556ebb4ed790'
                password 'OsVOuR6WZFK='
            }
            url 'https://packages.aliyun.com/maven/repository/2433389-release-RMv0jP/'
        }
        maven {
            credentials {
                username '654f4d888f25556ebb4ed790'
                password 'OsVOuR6WZFK='
            }
            url 'https://packages.aliyun.com/maven/repository/2433389-snapshot-Kqt8ID/'
        }
    }
    configurations.all {
        // 实时检查 Snapshot 更新
        resolutionStrategy.cacheChangingModulesFor 0, 'seconds'
    }
}
```

### Download
<details open>
  <summary>Gradle</summary>

```groovy
plugins {
    id 'kotlin-kapt'
}

dependencies {
    implementation("com.NBXXF.gson_plugin:lib_gson_annotation:1.2.0-SNAPSHOT")
    kapt("com.NBXXF.gson_plugin:lib_gson_plugin_kapt:1.2.0-SNAPSHOT")
}

kapt {
    arguments {
        // 指定生成TypeAdapterFactory的全限定名，不指定则不生成
        arg("factory", "com.xxf.json.gson.plugin.AutoTypeAdapterFactory")
    }
}
```
</details>

<details close>
  <summary>Kotlin-DSL</summary>

```kotlin
plugins {
    kotlin("kapt")
}

dependencies {
    implementation("com.NBXXF.gson_plugin:lib_gson_annotation:1.2.0-SNAPSHOT")
    kapt("com.NBXXF.gson_plugin:lib_gson_plugin_kapt:1.2.0-SNAPSHOT")
}

kapt {
    arguments {
        // 指定生成TypeAdapterFactory的全限定名，不指定则不生成
        arg("factory", "com.xxf.json.gson.plugin.AutoTypeAdapterFactory")
    }
}
```
</details>

### Usage

```kotlin
// 增加注解
@AutoGeneratedJsonAdapter
data class Foo(
    val string: String = ""
)

// 将生成的TypeAdapterFactory注册到gson实例中
val gson = GsonBuilder()
    .registerTypeAdapterFactory(AutoTypeAdapterFactory())
    .create()
```

### 平均五倍以上的提速 比moshi 自动生成adapter 更快

![](img/compare.png)

### KSP-Support
[KSP(Kotlin Symbol Processing)](https://github.com/google/ksp)是Google推出的更高性能、源码级的注解处理器，gson_plugin也对KSP作了支持。

<details open>
  <summary>Gradle</summary>

根目录build.gradle

```groovy
plugins {
    id('com.google.devtools.ksp') version "$kotlin_version-1.0.0"
}
```

app模块build.gradle

```groovy
plugins {
    id('com.google.devtools.ksp')
}

android {
    // KSP生成的代码不能被IDE自动识别到，需要手动添加到sourceSets中
    buildTypes {
        debug {
            sourceSets.main {
                java.srcDir("build/generated/ksp/debug/kotlin")
            }
        }
        release {
            sourceSets.main {
                java.srcDir("build/generated/ksp/release/kotlin")
            }
        }
    }
}

dependencies {
    implementation('com.NBXXF.gson_plugin:lib_gson_annotation:1.2.0-SNAPSHOT')
    ksp('com.NBXXF.gson_plugin:lib_gson_plugin_ksp:1.2.0-SNAPSHOT')
}

ksp {
    // 指定生成TypeAdapterFactory的全限定名，不指定则不生成
    arg("factory", "com.xxf.json.gson.plugin.AutoTypeAdapterFactory")
}
```

</details>

<details close>
  <summary>Kotlin DSL</summary>

根目录下的build.gradle

```kotlin
plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "$kotlin_version-1.0.0"
}
```

app模块build.gradle

```kotlin
plugins {
    id("com.google.devtools.ksp")
}

android {
    // KSP生成的代码不能被IDE自动识别到，需要手动添加到sourceSets中
    buildTypes {
        getByName("debug") {
            sourceSets.getByName("main") {
                java.srcDir("build/generated/ksp/debug/kotlin")
            }
        }
        getByName("release") {
            sourceSets.getByName("main") {
                java.srcDir("build/generated/ksp/release/kotlin")
            }
        }
    }
}

dependencies {
    implementation('com.NBXXF.gson_plugin:lib_gson_annotation:1.2.0-SNAPSHOT')
    ksp('com.NBXXF.gson_plugin:lib_gson_plugin_ksp:1.2.0-SNAPSHOT')
}

ksp {
    // 指定生成TypeAdapterFactory的全限定名，不指定则不生成
    arg("factory", "com.xxf.json.gson.plugin.AutoTypeAdapterFactory")
}
```
</details>

