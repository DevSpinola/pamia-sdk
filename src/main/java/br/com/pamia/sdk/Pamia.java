package br.com.pamia.sdk;

/** Entrada mínima da biblioteca; ainda não realiza detecção. */
public final class Pamia {
    private Pamia() {}

    public static String obterVersao() {
        return BuildConfig.SDK_VERSION;
    }
}
