package androidx.camera.core.impl;

import android.hardware.camera2.CaptureRequest;
import androidx.camera.core.impl.Config;

/* compiled from: AutoValue_Config_Option.java */
/* renamed from: androidx.camera.core.impl.d, reason: case insensitive filesystem */
/* loaded from: classes.dex */
final class C20087d<T> extends Config.a<T> {

    /* renamed from: a, reason: collision with root package name */
    public final String f24090a;

    /* renamed from: b, reason: collision with root package name */
    public final Class<T> f24091b;

    /* renamed from: c, reason: collision with root package name */
    public final CaptureRequest.Key f24092c;

    public C20087d(String str, Class cls, @j.P CaptureRequest.Key key) {
        if (str == null) {
            throw new NullPointerException("Null id");
        }
        this.f24090a = str;
        if (cls == null) {
            throw new NullPointerException("Null valueClass");
        }
        this.f24091b = cls;
        this.f24092c = key;
    }

    @Override // androidx.camera.core.impl.Config.a
    @j.N
    public final String c() {
        return this.f24090a;
    }

    @Override // androidx.camera.core.impl.Config.a
    @j.P
    public final Object d() {
        return this.f24092c;
    }

    @Override // androidx.camera.core.impl.Config.a
    @j.N
    public final Class<T> e() {
        return this.f24091b;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Config.a)) {
            return false;
        }
        Config.a aVar = (Config.a) obj;
        if (this.f24090a.equals(aVar.c()) && this.f24091b.equals(aVar.e())) {
            CaptureRequest.Key key = this.f24092c;
            if (key == null) {
                if (aVar.d() == null) {
                    return true;
                }
            } else if (key.equals(aVar.d())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        int iHashCode = (((this.f24090a.hashCode() ^ 1000003) * 1000003) ^ this.f24091b.hashCode()) * 1000003;
        CaptureRequest.Key key = this.f24092c;
        return iHashCode ^ (key == null ? 0 : key.hashCode());
    }

    public final String toString() {
        return "Option{id=" + this.f24090a + ", valueClass=" + this.f24091b + ", token=" + this.f24092c + "}";
    }
}
