# Migração para Kotlin Multiplatform (KMP) e Compose Multiplatform (CMP)

A biblioteca **4.x** expõe APIs em `commonMain` baseadas em [ScreenMetricsSnapshot](library/src/commonMain/kotlin/com/appdimens/dynamic/platform/ScreenMetrics.kt) em vez de `android.content.res.Configuration` / `Context` no núcleo de cálculo.

## Módulos do repositório

| Módulo | Papel |
|--------|--------|
| **`:library`** | Artefacto KMP publicado (Maven): Android, desktop JVM, iOS (Kotlin/Native em macOS). |
| **`:composeApp`** | Código partilhado da amostra CMP (`commonMain` + `androidMain` / `desktopMain` / `iosMain`). |
| **`:androidApp`** | APK Android de amostra; depende de `:composeApp`. |
| **`:app`** | App Android **clássica** (Views + Compose, Java/Kotlin, benchmarks e demos). Código em [`samples/legacy-app/`](samples/legacy-app/) (módulo Gradle continua a chamar-se **`:app`**). Não é KMP; para stack só CMP use **`:androidApp`** + **`:composeApp`**. |

## Web (js / wasm)

Os alvos **browser** (`js`, `wasmJs`) **não** estão incluídos nesta versão. Motivos práticos verificados no repo:

1. **`:library` / `commonMain`:** grande parte do código de dimensões e cache usa anotações e APIs orientadas a JVM (`@JvmStatic`, `@JvmOverloads`, `synchronized`, `java.lang.Math`, etc.). Compilar o artefacto completo para **Kotlin/Wasm** exigiria refactor (expect/actual, substituir sincronização, etc.) ou um recorte muito menor da API.
2. **`:composeApp`:** combinar **`compose.desktop { application { } }`** com **`wasmJs { browser() }`** no mesmo módulo Gradle levou a conflito de plugins Gradle (tarefa `clean` duplicada / `LifecycleBasePlugin`) com as versões actuais de Compose Multiplatform — uma amostra Web separada só faria sentido após esse desenho ou após evolução do plugin.

Para Compose na Web a médio prazo, siga a [documentação Compose Multiplatform](https://kotlinlang.org/docs/multiplatform/compose-multiplatform.html) e trate Web como módulo ou recorte de API dedicado.

## Consumidores Android

- Forneça métricas com `Configuration.toScreenMetrics()` (extensão em `androidMain`) ou monte um `ScreenMetricsSnapshot` manualmente.
- Em Compose, envolva a árvore com o provider de plataforma (por exemplo `AppDimensProvider` no Android) para preencher `LocalScreenMetrics` e contexto de dimensões.
- Chamadas na camada `code/` usam `DimenCallContext` / extensões que agrupam métricas, escala e persistência de cache quando aplicável.

## Consumidores Desktop (JVM)

- Use o alvo `desktop` publicado pelo artefato KMP.
- Métricas vêm da janela Compose / ambiente desktop; consulte os `actual` em `jvm`/`desktopMain` do módulo library.
- Persistência de cache em ficheiro: [`createDesktopCachePersistence()`](library/src/desktopMain/kotlin/com/appdimens/dynamic/platform/DesktopCachePersistence.kt) — grava em `~/.appdimens-dynamic/dimen_cache.bin` por omissão (mesmo formato binário que iOS: 4 bytes `sw` big-endian + payload). Inicialização:

```kotlin
val persistence = createDesktopCachePersistence()
val metrics = /* ScreenMetricsSnapshot da janela / LocalDensity */
DimenCache.init(persistence, metrics)
```

## iOS

- Os alvos `iosArm64` / `iosSimulatorArm64` são habilitados **apenas em macOS** no [`library/build.gradle.kts`](library/build.gradle.kts) (evita falha em Linux/Windows sem Xcode).
- **`iosMain`** inclui:
  - [`AppDimensProvider`](library/src/iosMain/kotlin/com/appdimens/dynamic/core/CompositionLocals.ios.kt) — preenche `LocalScreenMetrics` com [`rememberIosScreenMetrics()`](library/src/iosMain/kotlin/com/appdimens/dynamic/platform/IosScreenMetrics.kt) (`LocalWindowInfo` + `LocalDensity`) e `LocalUiModeType` (heurística tablet com `UIUserInterfaceIdiomPad` → `UiModeType.DESK`).
  - [`createIosCachePersistence()`](library/src/iosMain/kotlin/com/appdimens/dynamic/platform/IosCachePersistence.kt) — ficheiro em **Caches** (`URLForDirectory` + `NSCachesDirectory`), formato `[4 bytes big-endian sw][payload]`, adequado ao volume do cache (evitar NSUserDefaults).
- Inicialização típica do cache (fora do primeiro frame Compose, quando já tiver um `ScreenMetricsSnapshot`):

```kotlin
val persistence = createIosCachePersistence()
val metrics = /* snapshot atual ou rememberIosScreenMetrics() dentro de @Composable */
DimenCache.init(persistence, metrics)
```

- Entrada Compose Multiplatform (na **app** iOS, não na library): expor um `ComposeUIViewController` cuja raiz chame `AppDimensProvider { … }` (o mesmo padrão da amostra Android/desktop).
- Amostra [`:composeApp`](composeApp/build.gradle.kts): alvos iOS condicionais a macOS; framework **`ComposeApp`** (`binaries.framework { baseName = "ComposeApp" }`) para Xcode; [`MainViewController.kt`](composeApp/src/iosMain/kotlin/com/appdimens/dynamic/sample/cmp/MainViewController.kt) expõe `ComposeUIViewController { SampleApp() }`. Integração Swift: [`iosApp/README.md`](iosApp/README.md), **`iosApp/AppDimensSample.xcodeproj`** (scheme **AppDimensSample**), Swift em `iosApp/swift/`. O CI macOS compila `:composeApp:compileKotlinIosSimulatorArm64` e, em `main`/`master`, corre `xcodebuild` sobre esse projeto.

### CI macOS

- Workflow [`.github/workflows/ios-compile.yml`](.github/workflows/ios-compile.yml) executa em `macos-latest`: `./gradlew :library:compileKotlinIosSimulatorArm64` e `:composeApp:compileKotlinIosSimulatorArm64`; em `main`/`master` também **ios-sample-xcodebuild** (`xcodebuild` do `AppDimensSample.xcodeproj` para simulador genérico).
- Workflow [`.github/workflows/validate-kmp-ios.yml`](.github/workflows/validate-kmp-ios.yml) (Ubuntu) executa [`scripts/validate_kmp_ios.py`](scripts/validate_kmp_ios.py) — checagens estáticas em `**/iosMain/**/*.kt` e `swiftc -typecheck` se existir `.swift` e toolchain.

## Cache e persistência

- `DimenCache` usa **kotlinx-atomicfu** (`AtomicLongArray` / `AtomicIntArray` em common). Em `gradle.properties` estão `kotlinx.atomicfu.enableNativeIrTransformation=true` e `kotlinx.atomicfu.enableJvmIrTransformation=true` para IR em **Kotlin/Native (iOS)** e **JVM**.
- A persistência em disco é abstraída por `CachePersistence`: **Android** DataStore ([`AndroidCachePersistence.kt`](library/src/androidMain/kotlin/com/appdimens/dynamic/platform/AndroidCachePersistence.kt)); **iOS** ficheiro em Caches (acima); **desktop** ficheiro no diretório de utilizador ([`DesktopCachePersistence.kt`](library/src/desktopMain/kotlin/com/appdimens/dynamic/platform/DesktopCachePersistence.kt)).

## AGP 9 e estrutura CMP

- **`:library`** usa [`com.android.kotlin.multiplatform.library`](https://developer.android.com/kotlin/multiplatform/plugin) com bloco `kotlin { android { … } }` (sem `com.android.library` legado). Testes unitários Android estão em `src/androidHostTest`; instrumentados em `src/androidDeviceTest`.
- **Amostra CMP:** módulo partilhado **`:composeApp`** (KMP + mesmo plugin Android) e entrada Android **`:androidApp`** (`com.android.application`), conforme [guia Kotlin AGP 9](https://kotlinlang.org/docs/multiplatform/multiplatform-project-agp-9-migration.html). Namespace Android da partilha: `com.appdimens.dynamic.sample.cmp.shared`; a app mantém `applicationId` `com.appdimens.dynamic.sample.cmp`.
- `gradle.properties` não usa `android.builtInKotlin=false` / `android.newDsl=false`; apps Android usam Kotlin integrado no AGP 9+.
- **Compose Multiplatform** do projeto está em **1.10.1** (alinhar plugin Gradle com o plugin Android KMP).

## Publicação e Dokka

- Coordenadas Maven: `io.github.bodenberg:appdimens-dynamic` (KMP).
- Geração Dokka: `./gradlew :library:dokkaGenerate` (saída configurada em `library/build.gradle.kts`).

## Verificação local sugerida

```bash
./gradlew :library:testAndroidHostTest :library:desktopTest
./gradlew :app:assembleDebug
./gradlew :androidApp:assembleDebug
python3 scripts/validate_kmp_ios.py
# macOS + Xcode:
./gradlew :library:compileKotlinIosSimulatorArm64
# opcional amostra iOS:
# ./gradlew :composeApp:compileKotlinIosSimulatorArm64
```
