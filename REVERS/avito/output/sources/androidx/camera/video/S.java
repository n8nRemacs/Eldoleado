package androidx.camera.video;

import android.content.ContentValues;
import android.net.Uri;
import androidx.core.util.InterfaceC22791e;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class S implements InterfaceC22791e {

    /* renamed from: b, reason: collision with root package name */
    public final /* synthetic */ int f24823b = 0;

    /* renamed from: c, reason: collision with root package name */
    public final /* synthetic */ C20185w f24824c;

    @Override // androidx.core.util.InterfaceC22791e
    public final void accept(Object obj) {
        Uri uri = (Uri) obj;
        switch (this.f24823b) {
            case 0:
                if (uri.equals(Uri.EMPTY)) {
                    return;
                }
                new ContentValues().put("is_pending", (Integer) 0);
                this.f24824c.getClass();
                throw null;
            default:
                if (uri.equals(Uri.EMPTY)) {
                    return;
                }
                this.f24824c.getClass();
                throw null;
        }
    }
}
