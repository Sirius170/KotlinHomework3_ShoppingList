Lab07 - Coroutine 水果展示 App

原有功能：
1. Spinner 顯示購買數量選項。
2. GridView 以三欄顯示水果圖片及名稱。
3. ListView 顯示水果圖片、名稱及價格。

本次新增：
1. 使用 lifecycleScope.launch { } 啟動 Coroutine。
2. 使用 delay(1000) 模擬非同步載入。
3. 載入期間 Spinner 顯示「水果載入中...」並暫時停用。
4. 載入完成後建立水果資料並更新 GridView、ListView、Spinner。
5. 加入 ViewHolder，減少重複呼叫 findViewById。
6. 將水果資料建立流程獨立成 createFruitList()，並以 try/finally 確保 TypedArray.recycle()。

使用方式：
以 Android Studio 開啟 Lab7 資料夾，等待 Gradle Sync 完成後執行 app。

Coroutine 說明：
launch 用來啟動協程；delay 暫停目前協程指定時間，不會阻塞主執行緒。
lifecycleScope 會在 Activity 生命週期結束時取消其協程。
