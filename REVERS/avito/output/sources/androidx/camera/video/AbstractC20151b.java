package androidx.camera.video;

import androidx.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* compiled from: AudioStats.java */
@VY0.c
@j.X
/* renamed from: androidx.camera.video.b, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public abstract class AbstractC20151b {

    /* renamed from: a, reason: collision with root package name */
    public static final Set<Integer> f24860a = Collections.unmodifiableSet(new HashSet(Arrays.asList(2, 3, 4)));

    /* compiled from: AudioStats.java */
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo
    /* renamed from: androidx.camera.video.b$a */
    public @interface a {
    }

    @RestrictTo
    public abstract double a();

    public abstract int b();

    @j.P
    public abstract Throwable c();
}
