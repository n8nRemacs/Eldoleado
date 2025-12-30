package androidx.appcompat.widget;

import android.view.View;
import android.widget.AdapterView;

/* compiled from: ListPopupWindow.java */
/* renamed from: androidx.appcompat.widget.e0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19946e0 implements AdapterView.OnItemSelectedListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ C19948f0 f22502b;

    public C19946e0(C19948f0 c19948f0) {
        this.f22502b = c19948f0;
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onItemSelected(AdapterView<?> adapterView, View view, int i12, long j12) {
        W w12;
        if (i12 == -1 || (w12 = this.f22502b.f22508d) == null) {
            return;
        }
        w12.setListSelectionHidden(false);
    }

    @Override // android.widget.AdapterView.OnItemSelectedListener
    public final void onNothingSelected(AdapterView<?> adapterView) {
    }
}
