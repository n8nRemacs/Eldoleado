package androidx.appcompat.widget;

import android.view.View;

/* compiled from: SearchView.java */
/* loaded from: classes.dex */
class r0 implements View.OnFocusChangeListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ SearchView f22618b;

    public r0(SearchView searchView) {
        this.f22618b = searchView;
    }

    @Override // android.view.View.OnFocusChangeListener
    public final void onFocusChange(View view, boolean z12) {
        SearchView searchView = this.f22618b;
        View.OnFocusChangeListener onFocusChangeListener = searchView.f22266N;
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(searchView, z12);
        }
    }
}
