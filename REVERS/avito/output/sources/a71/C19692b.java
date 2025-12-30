package a71;

import java.util.Locale;

/* compiled from: Normalizer.java */
/* renamed from: a71.b, reason: case insensitive filesystem */
/* loaded from: classes7.dex */
public final class C19692b {
    public static String a(String str) {
        return str != null ? str.toLowerCase(Locale.ENGLISH) : "";
    }

    public static String b(String str) {
        return a(str).trim();
    }
}
