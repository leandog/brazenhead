#BigShitFancy / gametel-server Spike

###Command To Execute
`adb shell am instrument -e packageName com.example.android.apis -e fullLauncherName com.example.android.apis.ApiDemos -e class com.example.gametel_server.TheTest com.example.gametel_server/com.example.gametel_server.ServerInstrumentation`

Then...
`adb forward tcp:<local port> tcp:54767`

###Command To Kill
Browse to `http://127.0.0.1:<local port>/kill`
