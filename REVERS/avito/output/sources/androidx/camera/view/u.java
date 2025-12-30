package androidx.camera.view;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Size;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import androidx.camera.core.C20140q0;
import androidx.camera.core.K0;
import com.google.common.util.concurrent.D0;
import j.N;
import j.P;
import j.X;

/* compiled from: PreviewViewImplementation.java */
@X
/* loaded from: classes.dex */
abstract class u {

    /* renamed from: a, reason: collision with root package name */
    @P
    public Size f25473a;

    /* renamed from: b, reason: collision with root package name */
    @N
    public final FrameLayout f25474b;

    /* renamed from: c, reason: collision with root package name */
    @N
    public final r f25475c;

    /* renamed from: d, reason: collision with root package name */
    public boolean f25476d = false;

    /* compiled from: PreviewViewImplementation.java */
    public interface a {
    }

    public u(@N FrameLayout frameLayout, @N r rVar) {
        this.f25474b = frameLayout;
        this.f25475c = rVar;
    }

    @P
    public abstract View a();

    @P
    public abstract Bitmap b();

    public abstract void c();

    public abstract void d();

    public abstract void e(@N K0 k02, @P n nVar);

    public final void f() {
        View viewA = a();
        if (viewA == null || !this.f25476d) {
            return;
        }
        FrameLayout frameLayout = this.f25474b;
        Size size = new Size(frameLayout.getWidth(), frameLayout.getHeight());
        int layoutDirection = frameLayout.getLayoutDirection();
        r rVar = this.f25475c;
        rVar.getClass();
        if (size.getHeight() == 0 || size.getWidth() == 0) {
            size.toString();
            C20140q0.d(5, "PreviewTransform");
            return;
        }
        if (rVar.f()) {
            if (viewA instanceof TextureView) {
                ((TextureView) viewA).setTransform(rVar.d());
            } else {
                Display display = viewA.getDisplay();
                boolean z12 = false;
                boolean z13 = (!rVar.f25466g || display == null || display.getRotation() == rVar.f25464e) ? false : true;
                boolean z14 = rVar.f25466g;
                if (!z14) {
                    if ((!z14 ? rVar.f25462c : -androidx.camera.core.impl.utils.e.b(rVar.f25464e)) != 0) {
                        z12 = true;
                    }
                }
                if (z13 || z12) {
                    C20140q0.b("PreviewTransform");
                }
            }
            RectF rectFE = rVar.e(size, layoutDirection);
            viewA.setPivotX(0.0f);
            viewA.setPivotY(0.0f);
            viewA.setScaleX(rectFE.width() / rVar.f25460a.getWidth());
            viewA.setScaleY(rectFE.height() / rVar.f25460a.getHeight());
            viewA.setTranslationX(rectFE.left - viewA.getLeft());
            viewA.setTranslationY(rectFE.top - viewA.getTop());
        }
    }

    @N
    public abstract D0<Void> g();
}
