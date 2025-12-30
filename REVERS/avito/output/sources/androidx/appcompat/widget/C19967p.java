package androidx.appcompat.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import androidx.annotation.RestrictTo;

/* compiled from: AppCompatCheckedTextViewHelper.java */
@RestrictTo
/* renamed from: androidx.appcompat.widget.p, reason: case insensitive filesystem */
/* loaded from: classes.dex */
class C19967p {

    /* renamed from: a, reason: collision with root package name */
    @j.N
    public final AppCompatCheckedTextView f22597a;

    /* renamed from: b, reason: collision with root package name */
    public ColorStateList f22598b = null;

    /* renamed from: c, reason: collision with root package name */
    public PorterDuff.Mode f22599c = null;

    /* renamed from: d, reason: collision with root package name */
    public boolean f22600d = false;

    /* renamed from: e, reason: collision with root package name */
    public boolean f22601e = false;

    /* renamed from: f, reason: collision with root package name */
    public boolean f22602f;

    public C19967p(@j.N AppCompatCheckedTextView appCompatCheckedTextView) {
        this.f22597a = appCompatCheckedTextView;
    }

    public final void a() {
        AppCompatCheckedTextView appCompatCheckedTextView = this.f22597a;
        Drawable checkMarkDrawable = appCompatCheckedTextView.getCheckMarkDrawable();
        if (checkMarkDrawable != null) {
            if (this.f22600d || this.f22601e) {
                Drawable drawableMutate = checkMarkDrawable.mutate();
                if (this.f22600d) {
                    drawableMutate.setTintList(this.f22598b);
                }
                if (this.f22601e) {
                    drawableMutate.setTintMode(this.f22599c);
                }
                if (drawableMutate.isStateful()) {
                    drawableMutate.setState(appCompatCheckedTextView.getDrawableState());
                }
                appCompatCheckedTextView.setCheckMarkDrawable(drawableMutate);
            }
        }
    }
}
