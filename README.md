# MovieDB
About MovieDB sample

## How to use?
1. 進入 TheMovieDB 官網註冊賬號
2. 進入 API Subscription，在 Settings 內部的 API 區塊會顯示 `API Read Access Token` & `API Key` 的區塊，可以先複製起來
3. 找到 local.properties 並加入 `API_KEY` 及 `API_TOKEN` 兩個參數，這時就會用到剛剛複製的 `Token` 及 `Key` 了

```properties
sdk.dir=/Users/user/Library/Android/sdk
API_KEY="12345678"
API_TOKEN="qwerqwtjakhsfla1234lkjlh"
```
4. 重新 Sync project 即可
