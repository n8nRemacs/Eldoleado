package androidx.appcompat.view;

import android.content.Context;
import android.content.res.Configuration;
import androidx.annotation.RestrictTo;

/* compiled from: ActionBarPolicy.java */
@RestrictTo
/* loaded from: classes.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    public Context f21653a;

    public final int a() {
        Configuration configuration = this.f21653a.getResources().getConfiguration();
        int i12 = configuration.screenWidthDp;
        int i13 = configuration.screenHeightDp;
        if (configuration.smallestScreenWidthDp > 600 || i12 > 600) {
            return 5;
        }
        if (i12 > 960 && i13 > 720) {
            return 5;
        }
        if (i12 > 720 && i13 > 960) {
            return 5;
        }
        if (i12 >= 500) {
            return 4;
        }
        if (i12 > 640 && i13 > 480) {
            return 4;
        }
        if (i12 <= 480 || i13 <= 640) {
            return i12 >= 360 ? 3 : 2;
        }
        return 4;
    }
}
