package androidx.camera.core.impl;

import android.content.Context;
import android.util.Pair;
import android.util.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/* compiled from: CameraDeviceSurfaceManager.java */
@j.X
/* renamed from: androidx.camera.core.impl.x, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public interface InterfaceC20116x {

    /* compiled from: CameraDeviceSurfaceManager.java */
    /* renamed from: androidx.camera.core.impl.x$a */
    public interface a {
        @j.N
        androidx.camera.camera2.internal.L a(@j.N Context context, @j.P Object obj, @j.N Set set);
    }

    @j.N
    Pair a(int i12, @j.N String str, @j.N ArrayList arrayList, @j.N HashMap map);

    @j.P
    SurfaceConfig b(int i12, @j.N String str, int i13, @j.N Size size);
}
