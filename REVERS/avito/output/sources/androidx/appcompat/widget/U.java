package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Window;
import androidx.annotation.RestrictTo;
import androidx.appcompat.view.menu.o;

/* compiled from: DecorToolbar.java */
@RestrictTo
/* loaded from: classes.dex */
public interface U {
    boolean a();

    boolean b();

    boolean c();

    void collapseActionView();

    boolean d();

    boolean e();

    void f(androidx.appcompat.view.menu.h hVar, o.a aVar);

    void g();

    Context getContext();

    CharSequence getTitle();

    void h(int i12);

    void i(boolean z12);

    void j();

    void k();

    int l();

    androidx.core.view.A0 m(int i12, long j12);

    void n(int i12);

    boolean o();

    void p(int i12);

    void setIcon(int i12);

    void setIcon(Drawable drawable);

    void setTitle(CharSequence charSequence);

    void setVisibility(int i12);

    void setWindowCallback(Window.Callback callback);

    void setWindowTitle(CharSequence charSequence);
}
