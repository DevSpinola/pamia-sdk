# PAMIA SDK

Biblioteca Android mínima para validar compilação e publicação. Ainda não contém
contrato de eventos, regras de risco, modelos ou funções de detecção.

## Compilar

Requisitos: JDK 17 e Android SDK com plataforma 36 e Build Tools 35.0.0.
No PowerShell, nesta pasta (ajuste os caminhos se necessário):

```powershell
$env:JAVA_HOME = 'C:\Program Files\Java\jdk-17'
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
.\gradlew.bat assembleRelease
```

O primeiro build baixa Gradle e dependências. Resultado:
`build/outputs/aar/pamia-sdk-release.aar`. O AAR é uma biblioteca, não um APK.
No Linux/macOS: `bash ./gradlew assembleRelease`, com Java 17 e Android SDK.

## CI simples

Em PRs para `main`, pushes na `main` e execuções manuais, o Actions:

1. Baixa o código e configura Java 17.
2. Prepara o Android SDK.
3. Compila o AAR com `assembleRelease`.

O check chama-se **Build SDK**. Não inclui lint, testes, scanners ou upload de
artefatos. Build verde significa que compila, não que detecta situações de risco.

## Primeira publicação no JitPack

1. Subir esta estrutura e integrar o PR com build aprovado.
2. Criar uma release com tag `v0.1.0` no commit aprovado da `main`, indicando
   **estrutura inicial, sem detecção**.
3. Solicitar o build na [página do JitPack](https://jitpack.io/#DevSpinola/pamia-sdk)
   e conferir o log.
4. Confirmar que um consumidor Android baixa e compila com essa versão.

O `jitpack.yml` executa `publishToMavenLocal` para entregar o pacote ao JitPack.
Isso é necessário para a publicação, mas não é uma etapa extra do CI.
Criar a tag não comprova que a publicação funcionou. Não mova tags já distribuídas.

## Consumir no app

Adicione nos repositórios de dependências do `settings.gradle.kts`:

```kotlin
maven {
    url = uri("https://jitpack.io")
    content { includeGroup("com.github.DevSpinola") }
}
```

Nas dependências do módulo Android consumidor:

```kotlin
implementation("com.github.DevSpinola:pamia-sdk:v0.1.0")
```

Esse exemplo depende de uma tag publicada com sucesso. Nesta versão não há API
a chamar. O Expo precisará de uma build Android própria para incluir a biblioteca.

## Configurar no GitHub

Depois da primeira execução do CI, proteger a `main` exigindo PR, uma aprovação
de outro integrante, check **Build SDK**, branch atualizada e conversas resolvidas.
Usar squash merge e impedir push direto. Essas opções não são ativadas pelo código.

## Depois

Migrar funcionalidades Python e seus testes em mudanças separadas. CodeQL,
Dependabot, lint e cobertura foram adiados. Não implementar captura, motor de
risco ou contratos como parte deste setup.
