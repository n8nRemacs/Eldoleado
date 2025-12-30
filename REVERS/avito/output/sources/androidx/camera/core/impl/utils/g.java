package androidx.camera.core.impl.utils;

import android.util.Size;
import j.N;
import j.X;
import java.util.Comparator;

/* compiled from: CompareSizesByArea.java */
@X
/* loaded from: classes.dex */
public final class g implements Comparator<Size> {

    /* renamed from: b, reason: collision with root package name */
    public final boolean f24275b;

    public g() {
        this(false);
    }

    @Override // java.util.Comparator
    public final int compare(@N Size size, @N Size size2) {
        Size size3 = size;
        Size size4 = size2;
        int iSignum = Long.signum((size3.getWidth() * size3.getHeight()) - (size4.getWidth() * size4.getHeight()));
        return this.f24275b ? iSignum * (-1) : iSignum;
    }

    public g(boolean z12) {
        this.f24275b = z12;
    }
}
