package androidx.appcompat.app;

import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import androidx.appcompat.app.AlertController;

/* compiled from: AlertController.java */
/* renamed from: androidx.appcompat.app.j, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19936j implements AdapterView.OnItemClickListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ AlertController f21548b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ AlertController.b f21549c;

    public C19936j(AlertController.b bVar, AlertController alertController) {
        this.f21549c = bVar;
        this.f21548b = alertController;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public final void onItemClick(AdapterView<?> adapterView, View view, int i12, long j12) {
        AlertController.b bVar = this.f21549c;
        DialogInterface.OnClickListener onClickListener = bVar.f21371s;
        AlertController alertController = this.f21548b;
        onClickListener.onClick(alertController.f21325b, i12);
        if (bVar.f21376x) {
            return;
        }
        alertController.f21325b.dismiss();
    }
}
