package androidx.appcompat.widget;

import Ba1.C13127e;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import androidx.annotation.RestrictTo;
import com.avito.android.R;
import com.avito.android.remote.model.messenger.context.ChannelContext;

/* compiled from: Toolbar$InspectionCompanion.java */
@RestrictTo
@j.X
/* loaded from: classes.dex */
public final class H0 implements InspectionCompanion {

    /* renamed from: a, reason: collision with root package name */
    public boolean f22134a = false;

    /* renamed from: b, reason: collision with root package name */
    public int f22135b;

    /* renamed from: c, reason: collision with root package name */
    public int f22136c;

    /* renamed from: d, reason: collision with root package name */
    public int f22137d;

    /* renamed from: e, reason: collision with root package name */
    public int f22138e;

    /* renamed from: f, reason: collision with root package name */
    public int f22139f;

    /* renamed from: g, reason: collision with root package name */
    public int f22140g;

    /* renamed from: h, reason: collision with root package name */
    public int f22141h;

    /* renamed from: i, reason: collision with root package name */
    public int f22142i;

    /* renamed from: j, reason: collision with root package name */
    public int f22143j;

    /* renamed from: k, reason: collision with root package name */
    public int f22144k;

    /* renamed from: l, reason: collision with root package name */
    public int f22145l;

    /* renamed from: m, reason: collision with root package name */
    public int f22146m;

    /* renamed from: n, reason: collision with root package name */
    public int f22147n;

    /* renamed from: o, reason: collision with root package name */
    public int f22148o;

    /* renamed from: p, reason: collision with root package name */
    public int f22149p;

    /* renamed from: q, reason: collision with root package name */
    public int f22150q;

    /* renamed from: r, reason: collision with root package name */
    public int f22151r;

    /* renamed from: s, reason: collision with root package name */
    public int f22152s;

    /* renamed from: t, reason: collision with root package name */
    public int f22153t;

    /* renamed from: u, reason: collision with root package name */
    public int f22154u;

    public final void mapProperties(@j.N PropertyMapper propertyMapper) {
        this.f22135b = propertyMapper.mapObject("collapseContentDescription", R.attr.collapseContentDescription);
        this.f22136c = propertyMapper.mapObject("collapseIcon", R.attr.collapseIcon);
        this.f22137d = propertyMapper.mapInt("contentInsetEnd", R.attr.contentInsetEnd);
        this.f22138e = propertyMapper.mapInt("contentInsetEndWithActions", R.attr.contentInsetEndWithActions);
        this.f22139f = propertyMapper.mapInt("contentInsetLeft", R.attr.contentInsetLeft);
        this.f22140g = propertyMapper.mapInt("contentInsetRight", R.attr.contentInsetRight);
        this.f22141h = propertyMapper.mapInt("contentInsetStart", R.attr.contentInsetStart);
        this.f22142i = propertyMapper.mapInt("contentInsetStartWithNavigation", R.attr.contentInsetStartWithNavigation);
        this.f22143j = propertyMapper.mapObject("logo", R.attr.logo);
        this.f22144k = propertyMapper.mapObject("logoDescription", R.attr.logoDescription);
        this.f22145l = propertyMapper.mapObject(ChannelContext.MENU, R.attr.menu);
        this.f22146m = propertyMapper.mapObject("navigationContentDescription", R.attr.navigationContentDescription);
        this.f22147n = propertyMapper.mapObject("navigationIcon", R.attr.navigationIcon);
        this.f22148o = propertyMapper.mapResourceId("popupTheme", R.attr.popupTheme);
        this.f22149p = propertyMapper.mapObject("subtitle", R.attr.subtitle);
        this.f22150q = propertyMapper.mapObject("title", R.attr.title);
        this.f22151r = propertyMapper.mapInt("titleMarginBottom", R.attr.titleMarginBottom);
        this.f22152s = propertyMapper.mapInt("titleMarginEnd", R.attr.titleMarginEnd);
        this.f22153t = propertyMapper.mapInt("titleMarginStart", R.attr.titleMarginStart);
        this.f22154u = propertyMapper.mapInt("titleMarginTop", R.attr.titleMarginTop);
        this.f22134a = true;
    }

    public final void readProperties(@j.N Object obj, @j.N PropertyReader propertyReader) {
        Toolbar toolbar = (Toolbar) obj;
        if (!this.f22134a) {
            throw C13127e.o();
        }
        propertyReader.readObject(this.f22135b, toolbar.getCollapseContentDescription());
        propertyReader.readObject(this.f22136c, toolbar.getCollapseIcon());
        propertyReader.readInt(this.f22137d, toolbar.getContentInsetEnd());
        propertyReader.readInt(this.f22138e, toolbar.getContentInsetEndWithActions());
        propertyReader.readInt(this.f22139f, toolbar.getContentInsetLeft());
        propertyReader.readInt(this.f22140g, toolbar.getContentInsetRight());
        propertyReader.readInt(this.f22141h, toolbar.getContentInsetStart());
        propertyReader.readInt(this.f22142i, toolbar.getContentInsetStartWithNavigation());
        propertyReader.readObject(this.f22143j, toolbar.getLogo());
        propertyReader.readObject(this.f22144k, toolbar.getLogoDescription());
        propertyReader.readObject(this.f22145l, toolbar.getMenu());
        propertyReader.readObject(this.f22146m, toolbar.getNavigationContentDescription());
        propertyReader.readObject(this.f22147n, toolbar.getNavigationIcon());
        propertyReader.readResourceId(this.f22148o, toolbar.getPopupTheme());
        propertyReader.readObject(this.f22149p, toolbar.getSubtitle());
        propertyReader.readObject(this.f22150q, toolbar.getTitle());
        propertyReader.readInt(this.f22151r, toolbar.getTitleMarginBottom());
        propertyReader.readInt(this.f22152s, toolbar.getTitleMarginEnd());
        propertyReader.readInt(this.f22153t, toolbar.getTitleMarginStart());
        propertyReader.readInt(this.f22154u, toolbar.getTitleMarginTop());
    }
}
