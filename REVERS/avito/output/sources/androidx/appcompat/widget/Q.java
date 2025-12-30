package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;

/* compiled from: AppCompatToggleButton$InspectionCompanion.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class Q implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22244a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22245b;

    /* renamed from: c, reason: collision with root package name */
    public int f22246c;

    /* renamed from: d, reason: collision with root package name */
    public int f22247d;

    /* renamed from: e, reason: collision with root package name */
    public int f22248e;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22245b = propertyMapper.mapObject("backgroundTint", R.attr.backgroundTint);
        this.f22246c = propertyMapper.mapObject("backgroundTintMode", R.attr.backgroundTintMode);
        this.f22247d = propertyMapper.mapObject("drawableTint", R.attr.drawableTint);
        this.f22248e = propertyMapper.mapObject("drawableTintMode", R.attr.drawableTintMode);
        this.f22244a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        S s5 = (S) obj;
        if (!this.f22244a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22245b, s5.getBackgroundTintList());
        propertyReader.readObject(this.f22246c, s5.getBackgroundTintMode());
        propertyReader.readObject(this.f22247d, s5.getCompoundDrawableTintList());
        propertyReader.readObject(this.f22248e, s5.getCompoundDrawableTintMode());
    }
}
