package androidx.camera.core.impl.utils;

import androidx.camera.core.impl.utils.ExifData;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ExifData.java */
/* loaded from: classes.dex */
class k implements Enumeration<Map<String, j>> {

    /* renamed from: b, reason: collision with root package name */
    public final Enumeration<Map<String, j>> f24288b;

    public k(ExifData.b bVar) {
        this.f24288b = Collections.enumeration(bVar.f24193a);
    }

    @Override // java.util.Enumeration
    public final boolean hasMoreElements() {
        return this.f24288b.hasMoreElements();
    }

    @Override // java.util.Enumeration
    public final Map<String, j> nextElement() {
        return new HashMap(this.f24288b.nextElement());
    }
}
