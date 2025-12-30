package androidx.camera.core.impl;

import android.util.Size;
import androidx.camera.core.InterfaceC20142t;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: CameraInfoInternal.java */
@j.X
/* loaded from: classes.dex */
public interface B extends InterfaceC20142t {
    @j.N
    Set<androidx.camera.core.F> a();

    void c(@j.N AbstractC20109p abstractC20109p);

    @j.N
    Q d();

    @j.N
    w0 g();

    @j.N
    Timebase i();

    @j.N
    String k();

    void l(@j.N Executor executor, @j.N AbstractC20109p abstractC20109p);

    @j.N
    List<Size> o(int i12);

    @j.N
    default B p() {
        return this;
    }
}
