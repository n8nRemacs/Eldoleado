package a0;

import com.avito.android.R;
import kotlin.enums.c;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* renamed from: a0.a, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class EnumC19660a {

    /* renamed from: c, reason: collision with root package name */
    public static final EnumC19660a f20697c;

    /* renamed from: d, reason: collision with root package name */
    public static final EnumC19660a f20698d;

    /* renamed from: e, reason: collision with root package name */
    public static final EnumC19660a f20699e;

    /* renamed from: f, reason: collision with root package name */
    public static final EnumC19660a f20700f;

    /* renamed from: g, reason: collision with root package name */
    public static final /* synthetic */ EnumC19660a[] f20701g;

    /* renamed from: b, reason: collision with root package name */
    public final int f20702b;

    static {
        EnumC19660a enumC19660a = new EnumC19660a("ERROR", 0, R.string.versions_error_message_format);
        f20697c = enumC19660a;
        EnumC19660a enumC19660a2 = new EnumC19660a("WARNING", 1, R.string.versions_warning_message_format);
        f20698d = enumC19660a2;
        EnumC19660a enumC19660a3 = new EnumC19660a("INFO", 2, R.string.versions_info_message_format);
        f20699e = enumC19660a3;
        EnumC19660a enumC19660a4 = new EnumC19660a("CURRENT", 3, 0);
        f20700f = enumC19660a4;
        EnumC19660a[] enumC19660aArr = {enumC19660a, enumC19660a2, enumC19660a3, enumC19660a4};
        f20701g = enumC19660aArr;
        c.a(enumC19660aArr);
    }

    public EnumC19660a(String str, int i12, int i13) {
        this.f20702b = i13;
    }

    public static EnumC19660a valueOf(String str) {
        return (EnumC19660a) Enum.valueOf(EnumC19660a.class, str);
    }

    public static EnumC19660a[] values() {
        return (EnumC19660a[]) f20701g.clone();
    }
}
