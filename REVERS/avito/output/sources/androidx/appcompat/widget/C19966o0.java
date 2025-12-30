package androidx.appcompat.widget;

import com.avito.android.beduin.common.component.input.BeduinInputModel;

/* compiled from: RtlSpacingHelper.java */
/* renamed from: androidx.appcompat.widget.o0, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19966o0 {

    /* renamed from: a, reason: collision with root package name */
    public int f22589a = 0;

    /* renamed from: b, reason: collision with root package name */
    public int f22590b = 0;

    /* renamed from: c, reason: collision with root package name */
    public int f22591c = BeduinInputModel.MIN_TEXT_VERSION;

    /* renamed from: d, reason: collision with root package name */
    public int f22592d = BeduinInputModel.MIN_TEXT_VERSION;

    /* renamed from: e, reason: collision with root package name */
    public int f22593e = 0;

    /* renamed from: f, reason: collision with root package name */
    public int f22594f = 0;

    /* renamed from: g, reason: collision with root package name */
    public boolean f22595g = false;

    /* renamed from: h, reason: collision with root package name */
    public boolean f22596h = false;

    public final void a(int i12, int i13) {
        this.f22591c = i12;
        this.f22592d = i13;
        this.f22596h = true;
        if (this.f22595g) {
            if (i13 != Integer.MIN_VALUE) {
                this.f22589a = i13;
            }
            if (i12 != Integer.MIN_VALUE) {
                this.f22590b = i12;
                return;
            }
            return;
        }
        if (i12 != Integer.MIN_VALUE) {
            this.f22589a = i12;
        }
        if (i13 != Integer.MIN_VALUE) {
            this.f22590b = i13;
        }
    }
}
