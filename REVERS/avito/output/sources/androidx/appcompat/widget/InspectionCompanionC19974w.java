package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatImageButton$InspectionCompanion.java */
@RestrictTo
@j.X
/* renamed from: androidx.appcompat.widget.w, reason: case insensitive filesystem */
/* loaded from: classes.dex */
public final class InspectionCompanionC19974w implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22634a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22635b;

    /* renamed from: c, reason: collision with root package name */
    public int f22636c;

    /* renamed from: d, reason: collision with root package name */
    public int f22637d;

    /* renamed from: e, reason: collision with root package name */
    public int f22638e;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22635b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22636c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22637d = propertyMapper.mapObject("tint", R.attr.tint);
        this.f22638e = propertyMapper.mapObject("tintMode", R.attr.tintMode);
        this.f22634a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        AppCompatImageButton appCompatImageButton = (AppCompatImageButton) obj;
        if (!this.f22634a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22635b, appCompatImageButton.getBackgroundTintList());
        propertyReader.readObject(this.f22636c, appCompatImageButton.getBackgroundTintMode());
        propertyReader.readObject(this.f22637d, appCompatImageButton.getImageTintList());
        propertyReader.readObject(this.f22638e, appCompatImageButton.getImageTintMode());
    }
}
