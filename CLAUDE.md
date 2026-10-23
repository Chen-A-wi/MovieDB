# CLAUDE.md

## 開發原則

### 架構遵循 Clean Architecture

依賴方向只能由外往內：`app`（presentation）→ `domain` ← `data` → `network`。`domain` 是最內層，不依賴任何其他層。

| 模組 | 職責 | 可依賴 |
| --- | --- | --- |
| `domain` | model、repository 介面、use case，純商業邏輯 | `common` |
| `data` | repository 實作、DTO → domain model 的 mapper | `domain`、`network`、`common` |
| `network` | Retrofit service、DTO、OkHttp 設定 | `common` |
| `app` | Compose 畫面、ViewModel、Navigation | `domain`、`common`（`data`、`network` 只供 Hilt 組裝） |
| `common` | 跨層共用工具（dispatcher、extension） | 無 |

- `domain` 不可 import Android、Retrofit、kotlinx.serialization 或 DTO。只允許 `javax.inject` 這類標準註解。
- ViewModel 只透過 use case 取得資料，不直接使用 repository 實作、service 或 DTO。
- DTO 只存在 `network` 和 `data`，到了 `data` 就用 mapper 轉成 domain model，不往外傳。
- 錯誤以 `Resource.Error` 在 domain 邊界回傳，ViewModel 不處理 `HttpException` 等網路層型別。
- 依賴一律注入介面（例如 `DispatcherProvider`、`SearchRepository`），方便測試替換成 fake。
- 新功能依序在 `domain`（model、介面、use case）→ `data`（實作、mapper）→ `app`（ViewModel、畫面）建立，每層都配合 TDD 先寫測試。

### 開發前先寫測試（TDD）

新功能和修 bug 都依照 Red → Green → Refactor 進行：

1. **Red**：先寫一個描述預期行為的測試，執行並確認它失敗，而且失敗原因是功能還沒實作或 bug 還在，不是編譯錯誤或測試本身寫錯。
2. **Green**：只寫讓這個測試通過的最少程式碼，再執行一次確認通過。
3. **Refactor**：在測試保持通過的前提下整理程式碼。

- 修 bug 時，先寫能重現 bug 的測試，看到它失敗後才修。
- 一次只推進一個行為，完成一輪再寫下一個測試。
- 測試要放在對應模組的 `src/test`，用 fake 或 MockWebServer 隔離外部依賴。
- 無法用單元測試涵蓋的部分（例如 Compose 畫面），在 commit 或回報時說明用什麼方式驗證。

### 測試遵循 3A（Arrange-Act-Assert）

每個測試分成 Arrange、Act、Assert 三段，段落之間空一行，不寫 `// Arrange` 之類的註解。

- **Arrange**：準備假資料、fake、MockWebServer 回應，以及「為了進入某個狀態」的前置動作（例如先搜尋一次、先 load more 一次）。
- **Act**：只做一個被測的動作（一次呼叫，或一個使用者操作）。回傳值存成變數，例如 `val result = search()`，不要把 Act 寫進 assert 那一行。
- **Assert**：只驗 Act 造成的結果，使用 `:testing` 的 `shouldBe` 系列。

規則：

- 一個測試只驗一個行為，測試名稱寫「情境 + 預期結果」。同一個行為可以有多個 assert。
- 不要 Act → Assert → Act → Assert。要驗中間狀態（例如請求進行中 loading 為 true）就拆成獨立測試，前一步移到 Arrange。
- 驗不同輸入的同一規則時，每種輸入一個測試，不要在一個測試裡連續呼叫多次。

範例：

```kotlin
@Test
fun `load more can retry after failure`() = runTest(mainRule.dispatcher) {
    repository.respond = { if (it.page == 1) flowOf(success(1, totalPages = 3)) else error() }
    val vm = createViewModel()
    type(vm, "batman")
    vm.loadMore()
    advanceUntilIdle()

    vm.loadMore()
    advanceUntilIdle()

    repository.requests.map { it.page } shouldBe listOf(1, 2, 2)
}
```
