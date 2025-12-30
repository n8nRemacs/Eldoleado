package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import androidx.core.view.C22823h0;
import l.C43521a;
import m.C43852a;

/* compiled from: AppCompatCompoundButtonHelper.java */
/* renamed from: androidx.appcompat.widget.q, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19969q {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final CompoundButton f22603a;

    /* renamed from: b, reason: collision with root package name */
    public ColorStateList f22604b = null;

    /* renamed from: c, reason: collision with root package name */
    public PorterDuff.Mode f22605c = null;

    /* renamed from: d, reason: collision with root package name */
    public boolean f22606d = false;

    /* renamed from: e, reason: collision with root package name */
    public boolean f22607e = false;

    /* renamed from: f, reason: collision with root package name */
    public boolean f22608f;

    public C19969q(@j.N CompoundButton compoundButton) {
        this.f22603a = compoundButton;
    }

    public final void a() {
        CompoundButton compoundButton = this.f22603a;
        Drawable buttonDrawable = compoundButton.getButtonDrawable();
        if (buttonDrawable != null) {
            if (this.f22606d || this.f22607e) {
                Drawable drawableMutate = buttonDrawable.mutate();
                if (this.f22606d) {
                    drawableMutate.setTintList(this.f22604b);
                }
                if (this.f22607e) {
                    drawableMutate.setTintMode(this.f22605c);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(compoundButton.getDrawableState());
                }
                compoundButton.setButtonDrawable(drawableMutate);
            }
        }
    }

    public final void b(@j.P AttributeSet attributeSet, int i12) {
        int resourceId;
        int resourceId2;
        CompoundButton compoundButton = this.f22603a;
        Context context = compoundButton.getContext();
        int[] iArr = C43521a.m.f413364m;
        D0 d0E = D0.e(context, attributeSet, iArr, i12);
        TypedArray typedArray = d0E.f22121b;
        C22823h0.B(compoundButton, compoundButton.getContext(), iArr, attributeSet, d0E.f22121b, i12);
        try {
            if (typedArray.hasValue(1) && (resourceId2 = typedArray.getResourceId(1, 0)) != 0) {
                try {
                    compoundButton.setButtonDrawable(C43852a.a(compoundButton.getContext(), resourceId2));
                } catch (Resources.NotFoundException unused) {
                }
            } else if (typedArray.hasValue(0) && (resourceId = typedArray.getResourceId(0, 0)) != 0) {
                compoundButton.setButtonDrawable(C43852a.a(compoundButton.getContext(), resourceId));
            }
            if (typedArray.hasValue(2)) {
                compoundButton.setButtonTintList(d0E.a(2));
            }
            if (typedArray.hasValue(3)) {
                compoundButton.setButtonTintMode(V.c(typedArray.getInt(3, -1), null));
            }
        } finally {
            d0E.f();
        }
    }
}
