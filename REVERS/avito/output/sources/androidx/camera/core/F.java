package androidx.camera.core;

import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: DynamicRange.java */
@j.X
/* loaded from: classes.dex */
public final class F {

    /* renamed from: c, reason: collision with root package name */
    @j.N
    public static final F f23561c = new F(0, 0);

    /* renamed from: d, reason: collision with root package name */
    @j.N
    public static final F f23562d = new F(1, 8);

    /* renamed from: e, reason: collision with root package name */
    @j.N
    public static final F f23563e = new F(3, 10);

    /* renamed from: f, reason: collision with root package name */
    @j.N
    public static final F f23564f = new F(4, 10);

    /* renamed from: g, reason: collision with root package name */
    @j.N
    public static final F f23565g = new F(5, 10);

    /* renamed from: h, reason: collision with root package name */
    @j.N
    public static final F f23566h = new F(6, 10);

    /* renamed from: i, reason: collision with root package name */
    @j.N
    public static final F f23567i = new F(6, 8);

    /* renamed from: a, reason: collision with root package name */
    public final int f23568a;

    /* renamed from: b, reason: collision with root package name */
    public final int f23569b;

    /* compiled from: DynamicRange.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface a {
    }

    /* compiled from: DynamicRange.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    public @interface b {
    }

    public F(int i12, int i13) {
        this.f23568a = i12;
        this.f23569b = i13;
    }

    @RestrictTo
    public final boolean a() {
        return b() && this.f23568a != 1 && this.f23569b == 10;
    }

    @RestrictTo
    public final boolean b() {
        int i12 = this.f23568a;
        return (i12 == 0 || i12 == 2 || this.f23569b == 0) ? false : true;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof F)) {
            return false;
        }
        F f12 = (F) obj;
        return this.f23568a == f12.f23568a && this.f23569b == f12.f23569b;
    }

    public final int hashCode() {
        return this.f23569b ^ ((this.f23568a ^ 1000003) * 1000003);
    }

    @j.N
    public final String toString() {
        String str;
        StringBuilder sb2 = new StringBuilder("DynamicRange@");
        sb2.append(Integer.toHexString(System.identityHashCode(this)));
        sb2.append("{encoding=");
        switch (this.f23568a) {
            case 0:
                str = "UNSPECIFIED";
                break;
            case 1:
                str = "SDR";
                break;
            case 2:
                str = "HDR_UNSPECIFIED";
                break;
            case 3:
                str = "HLG";
                break;
            case 4:
                str = "HDR10";
                break;
            case 5:
                str = "HDR10_PLUS";
                break;
            case 6:
                str = "DOLBY_VISION";
                break;
            default:
                str = "<Unknown>";
                break;
        }
        sb2.append(str);
        sb2.append(", bitDepth=");
        return AK.c.i(this.f23569b, "}", sb2);
    }
}
