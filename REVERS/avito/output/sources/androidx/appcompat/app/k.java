package androidx.appcompat.app;

import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AlertController;

/* compiled from: AlertController.java */
/* loaded from: classes.dex */
class k implements AdapterView.OnItemClickListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AlertController.RecycleListView f21550b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AlertController f21551c;

    /* renamed from: d, reason: collision with root package name */
    public final /* synthetic */ AlertController.b f21552d;

    public k(AlertController.b bVar, AlertController.RecycleListView recycleListView, AlertController alertController) {
        this.f21552d = bVar;
        this.f21550b = recycleListView;
        this.f21551c = alertController;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView<?> adapterView, View view, int i12, long j12) {
        AlertController.b bVar = this.f21552d;
        boolean[] zArr = bVar.f21374v;
        AlertController.RecycleListView recycleListView = this.f21550b;
        if (zArr != null) {
            zArr[i12] = recycleListView.isItemChecked(i12);
        }
        bVar.f21378z.onClick(this.f21551c.f21325b, i12, recycleListView.isItemChecked(i12));
    }
}
