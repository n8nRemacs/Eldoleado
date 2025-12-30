package androidx.appcompat.app;

import android.R;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AlertController;

/* compiled from: AlertController.java */
/* renamed from: androidx.appcompat.app.h, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19934h extends ArrayAdapter<CharSequence> {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AlertController.RecycleListView f21546b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AlertController.b f21547c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C19934h(AlertController.b bVar, ContextThemeWrapper contextThemeWrapper, int i12, CharSequence[] charSequenceArr, AlertController.RecycleListView recycleListView) {
        super(contextThemeWrapper, i12, R.id.text1, charSequenceArr);
        this.f21547c = bVar;
        this.f21546b = recycleListView;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public final View getView(int i12, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i12, view, viewGroup);
        boolean[] zArr = this.f21547c.f21374v;
        if (zArr != null && zArr[i12]) {
            this.f21546b.setItemChecked(i12, true);
        }
        return view2;
    }
}
