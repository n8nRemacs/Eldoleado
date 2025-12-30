package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatImageView$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.y, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19976y implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22675a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22676b;

    /* renamed from: c, reason: collision with root package name */
    public int f22677c;

    /* renamed from: d, reason: collision with root package name */
    public int f22678d;

    /* renamed from: e, reason: collision with root package name */
    public int f22679e;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22676b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22677c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22678d = propertyMapper.mapObject("tint", R.attr.tint);
        this.f22679e = propertyMapper.mapObject("tintMode", R.attr.tintMode);
        this.f22675a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        AppCompatImageView appCompatImageView = (AppCompatImageView) obj;
        if (!this.f22675a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22676b, appCompatImageView.getBackgroundTintList());
        propertyReader.readObject(this.f22677c, appCompatImageView.getBackgroundTintMode());
        propertyReader.readObject(this.f22678d, appCompatImageView.getImageTintList());
        propertyReader.readObject(this.f22679e, appCompatImageView.getImageTintMode());
    }
}
