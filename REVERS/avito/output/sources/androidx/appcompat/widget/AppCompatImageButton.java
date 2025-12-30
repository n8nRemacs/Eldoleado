package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.RestrictTo;
import com.avito.android.R;
import j.InterfaceC42165v;

/* loaded from: classes.dex */
public class AppCompatImageButton extends ImageButton implements androidx.core.view.Y, androidx.core.widget.A {
    private final C19955j mBackgroundTintHelper;
    private boolean mHasLevel;
    private final C19975x mImageHelper;

    public AppCompatImageButton(@j.N Context context, @j.P AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.imageButtonStyle);
    }

    @Override // android.widget.ImageView, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.a();
        }
        C19975x c19975x = this.mImageHelper;
        if (c19975x != null) {
            c19975x.a();
        }
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportBackgroundTintList() {
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            return c19955j.b();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            return c19955j.c();
        }
        return null;
    }

    @RestrictTo
    @j.P
    public ColorStateList getSupportImageTintList() {
        B0 b02;
        C19975x c19975x = this.mImageHelper;
        if (c19975x == null || (b02 = c19975x.f22659b) == null) {
            return null;
        }
        return b02.f22095a;
    }

    @RestrictTo
    @j.P
    public PorterDuff.Mode getSupportImageTintMode() {
        B0 b02;
        C19975x c19975x = this.mImageHelper;
        if (c19975x == null || (b02 = c19975x.f22659b) == null) {
            return null;
        }
        return b02.f22096b;
    }

    @Override // android.widget.ImageView, android.view.View
    public boolean hasOverlappingRendering() {
        return !(this.mImageHelper.f22658a.getBackground() instanceof RippleDrawable) && super.hasOverlappingRendering();
    }

    @Override // android.view.View
    public void setBackgroundDrawable(@j.P Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.e();
        }
    }

    @Override // android.view.View
    public void setBackgroundResource(@InterfaceC42165v int i12) {
        super.setBackgroundResource(i12);
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.f(i12);
        }
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        C19975x c19975x = this.mImageHelper;
        if (c19975x != null) {
            c19975x.a();
        }
    }

    @Override // android.widget.ImageView
    public void setImageDrawable(@j.P Drawable drawable) {
        C19975x c19975x = this.mImageHelper;
        if (c19975x != null && drawable != null && !this.mHasLevel) {
            c19975x.f22660c = drawable.getLevel();
        }
        super.setImageDrawable(drawable);
        C19975x c19975x2 = this.mImageHelper;
        if (c19975x2 != null) {
            c19975x2.a();
            if (this.mHasLevel) {
                return;
            }
            C19975x c19975x3 = this.mImageHelper;
            ImageView imageView = c19975x3.f22658a;
            if (imageView.getDrawable() != null) {
                imageView.getDrawable().setLevel(c19975x3.f22660c);
            }
        }
    }

    @Override // android.widget.ImageView
    public void setImageLevel(int i12) {
        super.setImageLevel(i12);
        this.mHasLevel = true;
    }

    @Override // android.widget.ImageView
    public void setImageResource(@InterfaceC42165v int i12) {
        this.mImageHelper.c(i12);
    }

    @Override // android.widget.ImageView
    public void setImageURI(@j.P Uri uri) {
        super.setImageURI(uri);
        C19975x c19975x = this.mImageHelper;
        if (c19975x != null) {
            c19975x.a();
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintList(@j.P ColorStateList colorStateList) {
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.h(colorStateList);
        }
    }

    @RestrictTo
    public void setSupportBackgroundTintMode(@j.P PorterDuff.Mode mode) {
        C19955j c19955j = this.mBackgroundTintHelper;
        if (c19955j != null) {
            c19955j.i(mode);
        }
    }

    @RestrictTo
    public void setSupportImageTintList(@j.P ColorStateList colorStateList) {
        C19975x c19975x = this.mImageHelper;
        if (c19975x != null) {
            if (c19975x.f22659b == null) {
                c19975x.f22659b = new B0();
            }
            B0 b02 = c19975x.f22659b;
            b02.f22095a = colorStateList;
            b02.f22098d = true;
            c19975x.a();
        }
    }

    @RestrictTo
    public void setSupportImageTintMode(@j.P PorterDuff.Mode mode) {
        C19975x c19975x = this.mImageHelper;
        if (c19975x != null) {
            if (c19975x.f22659b == null) {
                c19975x.f22659b = new B0();
            }
            B0 b02 = c19975x.f22659b;
            b02.f22096b = mode;
            b02.f22097c = true;
            c19975x.a();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppCompatImageButton(@j.N Context context, @j.P AttributeSet attributeSet, int i12) {
        super(context, attributeSet, i12);
        A0.a(context);
        this.mHasLevel = false;
        y0.a(getContext(), this);
        C19955j c19955j = new C19955j(this);
        this.mBackgroundTintHelper = c19955j;
        c19955j.d(attributeSet, i12);
        C19975x c19975x = new C19975x(this);
        this.mImageHelper = c19975x;
        c19975x.b(attributeSet, i12);
    }
}
