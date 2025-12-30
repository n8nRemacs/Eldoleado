package androidx.camera.core.imagecapture;

import android.graphics.Matrix;
import android.graphics.Rect;
import androidx.camera.core.C20056e0;
import androidx.camera.core.impl.AbstractC20109p;
import j.X;
import java.util.List;
import java.util.concurrent.Executor;
import ru.cyberity.cbr.core.domain.camera.CameraX;

/* compiled from: TakePictureRequest.java */
@VY0.c
@X
/* loaded from: classes.dex */
public abstract class O {

    /* renamed from: a, reason: collision with root package name */
    public int f23807a;

    /* compiled from: TakePictureRequest.java */
    public interface a {
    }

    public O() {
        this.f23807a = new androidx.camera.core.internal.compat.workaround.a().f24341a == null ? 0 : 1;
    }

    @j.N
    public static O k(@j.N Executor executor, @j.P C20056e0.j jVar, @j.P CameraX.d dVar, @j.P C20056e0.l lVar, @j.N Rect rect, @j.N Matrix matrix, int i12, int i13, int i14, @j.N List list) {
        androidx.core.util.z.a("onDiskCallback and outputFileOptions should be both null or both non-null.", (dVar == null) == (lVar == null));
        androidx.core.util.z.a("One and only one on-disk or in-memory callback should be present.", (jVar == null) ^ (dVar == null));
        return new C20072h(executor, jVar, dVar, lVar, rect, matrix, i12, i13, i14, list);
    }

    @j.N
    public abstract Executor a();

    public abstract int b();

    @j.N
    public abstract Rect c();

    @j.P
    public abstract C20056e0.j d();

    @j.F
    public abstract int e();

    @j.P
    public abstract C20056e0.k f();

    @j.P
    public abstract C20056e0.l g();

    public abstract int h();

    @j.N
    public abstract Matrix i();

    @j.N
    public abstract List<AbstractC20109p> j();
}
