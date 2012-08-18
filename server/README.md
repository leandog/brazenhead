#gametel-driver

###Command To Execute
`adb shell am instrument -e packageName com.example.android.apis -e fullLauncherName com.example.android.apis.ApiDemos -e class com.leandog.gametel.driver.TheTest com.leandog.gametel.driver/com.leandog.gametel.driver.GametelInstrumentation`

###Then...
`adb forward tcp:<local port> tcp:54767`

###Command To Kill
Browse to `http://127.0.0.1:<local port>/kill`
