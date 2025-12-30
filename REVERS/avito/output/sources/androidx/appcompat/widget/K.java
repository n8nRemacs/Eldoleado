package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatSpinner$InspectionCompanion.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class K implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22183a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22184b;

    /* renamed from: c, reason: collision with root package name */
    public int f22185c;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22184b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22185c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22183a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        AppCompatSpinner appCompatSpinner = (AppCompatSpinner) obj;
        if (!this.f22183a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22184b, appCompatSpinner.getBackgroundTintList());
        propertyReader.readObject(this.f22185c, appCompatSpinner.getBackgroundTintMode());
    }
}
