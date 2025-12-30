package androidx.camera.extensions.internal.sessionprocessor;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import androidx.camera.core.C20140q0;
import androidx.camera.core.impl.A0;
import androidx.camera.core.impl.C0;
import androidx.camera.core.impl.t0;
import androidx.camera.core.impl.y0;
import androidx.camera.extensions.impl.PreviewExtenderImpl;
import j.N;
import j.P;
import j.S;
import j.X;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: BasicExtenderSessionProcessor.java */
@S
@X
/* loaded from: classes.dex */
public class d extends v {

    /* renamed from: a, reason: collision with root package name */
    public volatile y0 f24638a;

    /* compiled from: BasicExtenderSessionProcessor.java */
    public class a implements p {
    }

    static {
        new AtomicInteger(0);
    }

    @Override // androidx.camera.core.impl.C0
    public final void a() {
        throw null;
    }

    @Override // androidx.camera.extensions.internal.sessionprocessor.v, androidx.camera.core.impl.C0
    @N
    @A0.a
    public final /* bridge */ /* synthetic */ Set c() {
        return null;
    }

    @Override // androidx.camera.core.impl.C0
    public final void d(@N D.h hVar, @N C0.a aVar) {
        C20140q0.d(3, "BasicSessionProcessor");
        throw null;
    }

    @Override // androidx.camera.core.impl.C0
    public final void e(@N y0 y0Var) {
        this.f24638a = y0Var;
        new ArrayList();
        PreviewExtenderImpl previewExtenderImpl = null;
        previewExtenderImpl.onEnableSession();
        throw null;
    }

    @Override // androidx.camera.core.impl.C0
    public final void g(@N androidx.camera.camera2.impl.b bVar) {
        throw null;
    }

    @Override // androidx.camera.extensions.internal.sessionprocessor.v
    public final void h() {
        C20140q0.a("BasicSessionProcessor");
        PreviewExtenderImpl previewExtenderImpl = null;
        previewExtenderImpl.onDeInit();
        throw null;
    }

    @Override // androidx.camera.extensions.internal.sessionprocessor.v
    @N
    public final m i(@N String str, @N LinkedHashMap linkedHashMap, @N t0 t0Var, @N t0 t0Var2, @P t0 t0Var3) {
        C20140q0.a("BasicSessionProcessor");
        PreviewExtenderImpl previewExtenderImpl = null;
        previewExtenderImpl.onInit(str, (CameraCharacteristics) linkedHashMap.get(str), (Context) null);
        throw null;
    }
}
