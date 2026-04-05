# Amostra iOS (Xcode + Compose Multiplatform)

O módulo Kotlin [`:composeApp`](../composeApp) expõe o framework **`ComposeApp`** (estático) com [`MainViewController()`](../composeApp/src/iosMain/kotlin/com/appdimens/dynamic/sample/cmp/MainViewController.kt), que pode ser integrado numa app iOS nativa.

## Abrir o projeto de exemplo no Xcode

1. Em **macOS**, abre **`iosApp/AppDimensSample.xcodeproj`** no Xcode.
2. Escolhe um simulador (ex.: iPhone 16) e **Run** (⌘R). Na primeira compilação, a fase **Gradle embed ComposeApp** executa `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode` a partir da raiz do repositório (pode demorar).
3. Se o signing falhar, define uma **Team** em *Signing & Capabilities* do target **AppDimensSample** (conta Apple gratuita chega para simulador).
4. Confirma o símbolo Swift em `ComposeApp-Swift.h` / autocompletar: costuma ser **`MainViewControllerKt.MainViewController()`**.

## Pré-requisitos

- macOS com **Xcode** e **JDK 17**
- **Gradle wrapper** na raiz do repo (`../gradlew` relativamente a `iosApp` quando `SRCROOT` é a pasta `iosApp`)

## Integração manual (projeto Xcode próprio)

Se preferires criar a app no Xcode em vez de usar `AppDimensSample.xcodeproj`:

1. No Xcode, cria um novo projeto **App** (SwiftUI ou UIKit).
2. Na raiz do repositório, configura o **Run Script** de build conforme [Direct integration](https://kotlinlang.org/docs/multiplatform/multiplatform-direct-integration.html#connect-the-framework-to-your-ios-project), por exemplo:

```bash
if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
  echo "Skipping Gradle embed (IDE override)"
  exit 0
fi
cd "$SRCROOT/.."
./gradlew :composeApp:embedAndSignAppleFrameworkForXcode
```

3. Em **Frameworks, Libraries, and Embedded Content**, adicione **`ComposeApp.framework`** (Embed & Sign).
4. Copie os ficheiros Swift de exemplo desta pasta [`swift/`](swift/) para o target iOS e ajuste o nome do módulo Swift se necessário.
5. O símbolo Kotlin gerado para `fun MainViewController()` em `MainViewController.kt` costuma aparecer em Swift como **`MainViewControllerKt.MainViewController()`** (confirme no header `ComposeApp-Swift.h` / autocompletar do Xcode).

## Ficheiros de referência

- [`swift/ComposeRootView.swift`](swift/ComposeRootView.swift) — `UIViewControllerRepresentable` que hospeda o Compose.
- [`swift/AppDimensComposeApp.swift`](swift/AppDimensComposeApp.swift) — entrada `@main` SwiftUI mínima.

Se o linker não encontrar `ComposeApp`, confirme que `baseName = "ComposeApp"` está definido em `composeApp/build.gradle.kts` nos blocos `binaries.framework { }` dos alvos iOS.
