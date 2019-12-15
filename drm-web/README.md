## Web环境

### 主配置
```groovy
app('web', {
    port = 100
    name = '测试Web应用'
    path = '/test'
}, {
    get '/join', {}
})
include("./test.groovy")
```

- 脚本文件 - `./test.groovy`

```groovy
app.get "/hi", { 
    println("test, hi") 
}

app.with {
    get "/hello", { 
        println("test, hello") 
    }
}
```