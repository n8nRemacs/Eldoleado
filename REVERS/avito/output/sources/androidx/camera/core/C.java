package androidx.camera.core;

import androidx.annotation.RestrictTo;
import androidx.camera.core.impl.I;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* compiled from: CaptureBundles.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class C {

    /* compiled from: CaptureBundles.java */
    public static final class a implements androidx.camera.core.impl.G {

        /* renamed from: a, reason: collision with root package name */
        public final List<androidx.camera.core.impl.I> f23549a;

        public a(List<androidx.camera.core.impl.I> list) {
            if (list == null || list.isEmpty()) {
                throw new IllegalArgumentException("Cannot set an empty CaptureStage list.");
            }
            this.f23549a = Collections.unmodifiableList(new ArrayList(list));
        }

        @Override // androidx.camera.core.impl.G
        public final List<androidx.camera.core.impl.I> a() {
            return this.f23549a;
        }
    }

    @j.N
    public static androidx.camera.core.impl.G a() {
        return new a(Arrays.asList(new I.a()));
    }
}
