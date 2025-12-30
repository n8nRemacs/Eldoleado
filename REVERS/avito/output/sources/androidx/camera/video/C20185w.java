package androidx.camera.video;

import VY0.c;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import androidx.camera.video.AbstractC20186x;

/* compiled from: MediaStoreOutputOptions.java */
@j.X
/* renamed from: androidx.camera.video.w, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class C20185w extends AbstractC20186x {

    /* compiled from: MediaStoreOutputOptions.java */
    /* renamed from: androidx.camera.video.w$a */
    public static final class a extends AbstractC20186x.a<C20185w, a> {
    }

    /* compiled from: MediaStoreOutputOptions.java */
    @VY0.c
    /* renamed from: androidx.camera.video.w$b */
    public static abstract class b extends AbstractC20186x.b {

        /* compiled from: MediaStoreOutputOptions.java */
        @c.a
        /* renamed from: androidx.camera.video.w$b$a */
        public static abstract class a extends AbstractC20186x.b.a<a> {
        }

        @j.N
        public abstract Uri d();

        @j.N
        public abstract ContentResolver e();

        @j.N
        public abstract ContentValues f();
    }

    static {
        new ContentValues();
    }

    public final boolean equals(@j.P Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof C20185w)) {
            return false;
        }
        ((C20185w) obj).getClass();
        throw null;
    }

    public final int hashCode() {
        throw null;
    }

    @j.N
    public final String toString() {
        throw null;
    }
}
