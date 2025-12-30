package androidx.appcompat.widget;

import android.content.Context;
import android.view.View;
import android.view.Window;

/* compiled from: ToolbarWidgetWrapper.java */
/* loaded from: classes.dex */
class I0 implements View.OnClickListener {

    /* renamed from: b, reason: collision with root package name */
    public final androidx.appcompat.view.menu.a f22161b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ J0 f22162c;

    public I0(J0 j02) {
        this.f22162c = j02;
        Context context = j02.f22165a.getContext();
        CharSequence charSequence = j02.f22172h;
        androidx.appcompat.view.menu.a aVar = new androidx.appcompat.view.menu.a();
        aVar.f21758e = 4096;
        aVar.f21760g = 4096;
        aVar.f21765l = null;
        aVar.f21766m = null;
        aVar.f21767n = false;
        aVar.f21768o = false;
        aVar.f21769p = 16;
        aVar.f21762i = context;
        aVar.f21754a = charSequence;
        this.f22161b = aVar;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        J0 j02 = this.f22162c;
        Window.Callback callback = j02.f22175k;
        if (callback == null || !j02.f22176l) {
            return;
        }
        callback.onMenuItemSelected(0, this.f22161b);
    }
}
