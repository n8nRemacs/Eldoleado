package Ae0;

import android.view.View;

/* compiled from: R8$$SyntheticClass */
/* renamed from: Ae0.a, reason: case insensitive filesystem */
/* loaded from: classes16.dex */
public final /* synthetic */ class ViewOnClickListenerC13025a implements View.OnClickListener {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f542b;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ com.avito.android.publish.scanner_v2.c f543c;

    public /* synthetic */ ViewOnClickListenerC13025a(com.avito.android.publish.scanner_v2.c cVar, int i12) {
        this.f542b = i12;
        this.f543c = cVar;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.f542b) {
            case 0:
                this.f543c.f239341c.p();
                break;
            case 1:
                this.f543c.f239341c.f();
                break;
            case 2:
                this.f543c.f239341c.c();
                break;
            case 3:
                this.f543c.f239341c.c();
                break;
            default:
                this.f543c.f239341c.p();
                break;
        }
    }
}
