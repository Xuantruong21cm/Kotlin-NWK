# Kotlin-NWK
**KOTLIN ANDROID NETWORKING**
- It uses OkHttp
** HOW TO USE ? **
- Add to your project -
![alt text](https://github.com/[Xuantruong21cm]/[Kotlin-NWK]/blob/[master]/Capture.PNG?raw=true)

** Add in it your roo build.gradle at the end of repositories

repositories {
     ...
     maven { url 'https://jitpack.io' }
}

** Add the dependency

dependencies {
    ...
    implementation 'com.github.Xuantruong21cm:Kotlin-NWK:1.0.0'
}

** Add internet access in the AndroidManifest

**Using Library In Your Application**
URL Exanple : https://jsonplaceholder.typicode.com/todos/1


**Execute a GET Request**
KotNetworking.get(url)
                .addPathParameter("userId","1") 
                .addQueryParameter("userId","1") 
                .addHeaders("userId", "1") 
                .setPriority(Priority.MEDIUM) 
                .build()
                .getAsString { result, error ->        
                    if (error == null){
                        // do anything with result
                    }else{
                        // do anything with erro
                    }
                }
                
  
   
**Execute a POST Request**
KotNetworking.post("https://jsonplaceholder.typicode.com/todos/1")
                .addBodyParameter("userId", "1") // example - Optional
                .setPriority(Priority.MEDIUM) // Priority Level
                .build()
                .getAsString { result, error ->         **// With String**
                    if (error == null){
                        // do anything with result
                    }else{
                        // do anything with erro
                    }
                }
                ----
                .getAsJSONArray { result, error ->    **// With JSONArray**
                    if (error == null){
                        // do anything with result
                    }else{
                        // do anything with erro
                    }
                }
                ----
                .getAsJSONObject { result, error ->   **// With JSONArray**
                    if (error == null){
                        // do anything with result
                    }else{
                        // do anything with erro
                    }
                }
                --And a few other POST methods
 
 **You can also post java object, json, file, etc in POST request like this**
 
 
 * With OBJECT
 val user: User = User()
        user.firstname = "Xuan"
        user.lastname = "Truong"
        KotNetworking.post("https://jsonplaceholder.typicode.com/todos/1")
                .addApplicationJsonBody(user)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener { timeTakenInMillis, bytesSent, bytesReceived, isFromCache ->
                    println("timeTakenInMillis ---> $timeTakenInMillis")
                    println("bytesSent ---> $bytesSent")
                    println("bytesReceived ---> $bytesReceived")
                    println("isFromCache ---> $isFromCache")
                } 
                .getAsString { response, error ->
                    if (error != null) {
                        Log.d(PostApiTestActivity.TAG, error.toString())
                    } else {
                        Log.d(PostApiTestActivity.TAG, response.toString())
                    }
                }

        KotNetworking.post("https://jsonplaceholder.typicode.com/todos/1")
                .addApplicationJsonBody(user)
                .setTag(this)
                .setPriority(Priority.MEDIUM)
                .build()
                .setAnalyticsListener { timeTakenInMillis, bytesSent, bytesReceived, isFromCache ->
                    println("timeTakenInMillis ---> $timeTakenInMillis")
                    println("bytesSent ---> $bytesSent")
                    println("bytesReceived ---> $bytesReceived")
                    println("isFromCache ---> $isFromCache")
                } 
                .getAsOkHttpResponse { response, error ->
                    response?.apply {
                        if (isSuccessful) {
                            try {
                                Log.d(PostApiTestActivity.TAG, "response : ${body().source().readUtf8()}")
                            } catch (ioe: IOException) {
                                ioe.printStackTrace()
                            }
                        }
                    }

                    error?.let {
                        Log.d(PostApiTestActivity.TAG, error.toString())
                    }
                }
                
                
 * With JSONObject
  val jsonObject: JSONObject = JSONObject()
                try {
                    jsonObject.put("Xuan", "Truong")
                    jsonObject.put("Example", "Example")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                KotNetworking.post("https://jsonplaceholder.typicode.com/todos/1")
                        .addApplicationJsonBody(jsonObject)
                        .setTag(this)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setAnalyticsListener { timeTakenInMillis, bytesSent, bytesReceived, isFromCache ->
                            println("timeTakenInMillis ---> $timeTakenInMillis")
                            println("bytesSent ---> $bytesSent")
                            println("bytesReceived ---> $bytesReceived")
                            println("isFromCache ---> $isFromCache")
                        }
                        .getAsString { response, error ->
                            if (error != null) {
                                Log.d(PostApiTestActivity.TAG, error.toString())
                            } else {
                                Log.d(PostApiTestActivity.TAG, response.toString())
                            }
                        }

                KotNetworking.post("https://jsonplaceholder.typicode.com/todos/1")
                        .addApplicationJsonBody(jsonObject)
                        .setTag(this)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .setAnalyticsListener { timeTakenInMillis, bytesSent, bytesReceived, isFromCache ->
                            println("timeTakenInMillis ---> $timeTakenInMillis")
                            println("bytesSent ---> $bytesSent")
                            println("bytesReceived ---> $bytesReceived")
                            println("isFromCache ---> $isFromCache")
                        }
                        .getAsOkHttpResponse { response, error ->
                            response?.apply {
                                if (isSuccessful) {
                                    try {
                                        Log.d(PostApiTestActivity.TAG, "response : ${body().source().readUtf8()}")
                                    } catch (ioe: IOException) {
                                        ioe.printStackTrace()
                                    }
                                }
                            }

                            error?.let {
                                Log.d(PostApiTestActivity.TAG, error.toString())
                            }
                        }
                        
                        
* Downloading a file from server
KotNetworking.download(url,dirPath,fileName)
                .setTag("downloadExample")
                .setPriority(Priority.MEDIUM)
                .build()
                .setDownloadProgressListener { bytesDownloaded, totalBytes ->
                    // do anything with progress
                }
                .startDownload {
                    erro -> {
                    if (erro == null){
                        //do something
                    }else{
                        //do something
                    }
                  }
                }
       
       
* Uploading a file to server
KotNetworking.upload(url)
                .addMultiPartFile("file",file)
                .addMultiPartParameter("key","value")
                .setTag("uploadExample")
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener { bytesDownloaded, totalBytes ->
                    // do anything with progress
                }
                .getAsJSONObject { result, error ->
                    if (error == null){
                        // do anything with response
                    }else{
                        // do anything with erro
                    }
                }


* Cancelling a request
KotNetworking.cancel("tag")  // All the requests with the given tag will be cancelled.
            KotNetworking.forceCancelAll() // All the requests will be cancelled , even if any percent threshold is
            // set , it will be cancelled forcefully.
            KotNetworking.cancelAll() // All the requests will be cancelled.

**Contributing to Kotlin-NWK**
There are a few other features, you can learn by yourself during use. Please point out mistakes and comment if possible
